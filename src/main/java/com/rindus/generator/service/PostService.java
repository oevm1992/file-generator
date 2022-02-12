package com.rindus.generator.service;

import com.google.gson.Gson;
import com.rindus.generator.model.PostModel;
import com.rindus.generator.model.PostModelList;
import com.thoughtworks.xstream.XStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Value("${jsonplaceholder.base.url}")
    private String jsonPlaceBaseUrl;

    @Value("${posts.path}")
    private String postPath;
    
    @Autowired
    private RestTemplate restTemplate;

    public byte[] getAllPostsAsJson() throws IOException {
        return createJsonFileByte(restTemplate.getForObject(jsonPlaceBaseUrl.concat(postPath), List.class));
    }

    public byte[] getAllPostsAsXml() throws IOException {
        return createXmlFileByte(restTemplate.getForObject(jsonPlaceBaseUrl.concat(postPath), PostModelList.class));
    }

    private byte[] createJsonFileByte(Object data) throws IOException {
        Gson gson = new Gson();
        return gson.toJson(data).getBytes(StandardCharsets.UTF_8);
    }

    private byte[] createXmlFileByte(Object data) throws IOException {
        XStream xstream = new XStream();
        return xstream.toXML(data).getBytes(StandardCharsets.UTF_8);
    }

}
