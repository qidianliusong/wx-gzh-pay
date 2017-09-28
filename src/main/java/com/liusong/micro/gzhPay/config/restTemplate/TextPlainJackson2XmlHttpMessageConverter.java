package com.liusong.micro.gzhPay.config.restTemplate;

import org.springframework.http.MediaType;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

public class TextPlainJackson2XmlHttpMessageConverter extends MappingJackson2XmlHttpMessageConverter {
    public TextPlainJackson2XmlHttpMessageConverter(){
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mediaTypes.add(MediaType.TEXT_HTML);
        setSupportedMediaTypes(mediaTypes);
    }

}
