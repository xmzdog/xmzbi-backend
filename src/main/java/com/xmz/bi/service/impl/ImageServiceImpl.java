package com.xmz.bi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xmz.bi.mapper.ImageMapper;
import com.xmz.bi.model.entity.Image;
import com.xmz.bi.service.ImageService;
import org.springframework.stereotype.Service;


@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image>
    implements ImageService {

}




