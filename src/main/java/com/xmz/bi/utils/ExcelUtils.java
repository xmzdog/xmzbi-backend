package com.xmz.bi.utils;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel 相关工具类
 * @author xmz
 * @date 2023-11-16
 */
@Slf4j
public class ExcelUtils {

    public static String  excelToCsv(MultipartFile multipartFile)  {
//        File file = ResourceUtils.getFile("classpath:xmzData.xlsx");
        //读取 excel数据
        List<Map<Integer, String>> list = null;
        try {
            list = EasyExcel.read(multipartFile.getInputStream())
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();
        } catch (IOException e) {
            log.error("表格处理错误 ");
        }

        if(CollUtil.isEmpty(list)){
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        //转换为csv
        //读取表头
        LinkedHashMap<Integer,String> headerMap = (LinkedHashMap) list.get(0);
        stringBuilder.append(StringUtils.join(headerMap.values(),",")).append("\n");
        //读取数据
        for (int i = 1; i < list.size(); i++) {
            LinkedHashMap<Integer,String> dataMap = (LinkedHashMap) list.get(i);
            stringBuilder.append(StringUtils.join(dataMap.values(),",")).append("\n");
        }
        return stringBuilder.toString();
    }


}
