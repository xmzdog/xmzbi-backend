package com.xmz.bi.bimq;

import com.rabbitmq.client.Channel;
import com.xmz.bi.common.ErrorCode;
import com.xmz.bi.constant.BiMqConstant;
import com.xmz.bi.constant.CommonConstant;
import com.xmz.bi.exception.BusinessException;
import com.xmz.bi.manager.AiManager;
import com.xmz.bi.model.entity.Chart;
import com.xmz.bi.model.enums.ChartStatusEnum;
import com.xmz.bi.service.impl.ChartServiceImpl;
import com.xmz.bi.utils.ExcelUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.RegEx;
import javax.annotation.Resource;

/**
 * @author xmz
 * @date 2024-03-27
 */
@Component
@Slf4j
public class BiMessageConsumer {

    @Resource
    private ChartServiceImpl chartService;
    @Resource
    private AiManager aiManager;


    @SneakyThrows
    @RabbitListener(queues = {BiMqConstant.BI_QUEUES},ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliverTag){
        log.info("receiveMessage message = {}",message);
        if(StringUtils.isBlank(message)){
            // 如果更新失败，拒绝当前消息，让消息重新进入队列
            channel.basicNack(deliverTag,false,false);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"消息为空");
        }
        long chartId = Long.parseLong(message);
        Chart chart = chartService.getById(chartId);

        if(chart == null){
            // 如果图表为空，拒绝消息并抛出业务异常
            channel.basicNack(deliverTag,false,false);
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"图表为空");
        }

        Chart updateChart = new Chart();
        updateChart.setId(chart.getId());
        updateChart.setStatus(ChartStatusEnum.RUNNING.getValue());
        boolean b = chartService.updateById(updateChart);
        if(!b){
            // 如果更新图表执行中状态失败，拒绝消息并处理图表更新错误
            channel.basicNack(deliverTag,false,false);
            chartService.handleChartUpdateError(chart.getId(),"更新图表状态为执行中失败");
            return;
        }

        //调用AI
        String result = aiManager.doChat(CommonConstant.AI_MODEL_ID, buildUserInput(chart));
        String[] splits = result.split("【【【【【");
        if (splits.length < 3) {
            channel.basicNack(deliverTag,false,false);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "AI 生成错误");
        }
        String genChart = splits[1].trim();
        String genResult = splits[2].trim();

        Chart updateChartResult = new Chart();
        updateChartResult.setId(chart.getId());
        updateChartResult.setStatus(ChartStatusEnum.SUCCEED.getValue());
        updateChartResult.setGenChart(genChart);
        updateChartResult.setGenResult(genResult);
        boolean updateResult = chartService.updateById(updateChartResult);
        if(!updateResult){
            // 如果更新图表成功状态失败，拒绝消息并处理图表更新错误
            channel.basicNack(deliverTag,false,false);
            chartService.handleChartUpdateError(chart.getId(), "更新图表状态为成功失败");
            return;
        }
        //消息确认
        channel.basicAck(deliverTag,false);
    }

    public String buildUserInput(Chart chart){
        //用户输入
        StringBuilder userInput = new StringBuilder();
        userInput.append("分析需求：").append("\n");

        //拼接分析目标
        String userGoal = chart.getGoal();
        if (StringUtils.isNotBlank(chart.getChartType())) {
            userGoal += ",请使用" + chart.getChartType();
        }
        userInput.append("分析目标:").append(userGoal).append("\n");

        // 获取压缩后得数据
        String csvData = chart.getChartData();
        userInput.append("原始数据：").append(csvData).append("\n");
        return userInput.toString();
    }
}
