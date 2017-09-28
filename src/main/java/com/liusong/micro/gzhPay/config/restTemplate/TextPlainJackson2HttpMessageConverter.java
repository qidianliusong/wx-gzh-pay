package com.liusong.micro.gzhPay.config.restTemplate;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

public class TextPlainJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public TextPlainJackson2HttpMessageConverter(){
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        setSupportedMediaTypes(mediaTypes);
    }

}
