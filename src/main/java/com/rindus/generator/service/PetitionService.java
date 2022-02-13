package com.rindus.generator.service;

import com.rindus.generator.model.PostModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PetitionService {

    @Autowired
    private RestTemplate restTemplate;

    public Object post(String url, Object body, Class c) throws Exception {
        return restTemplate.postForObject(url, body, c);
    }

    public Object get(String url, Class c) throws Exception {
        return restTemplate.getForObject(url, c);
    }

    public Object exchange(String url, Object body, Class c, HttpEntity http, HttpMethod method) throws Exception {
        return restTemplate.exchange(url, method, http, PostModel.class).getBody();
    }

    public void delete(String url) throws Exception {
        restTemplate.delete(url);
    }


}
