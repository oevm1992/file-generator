package com.rindus.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class PetitionService {

    @Autowired
    private RestTemplate restTemplate;

    public Object post(String url, Object body, Class c) throws RestClientException {
        return restTemplate.postForObject(url, body, c);
    }

    public Object get(String url, Class c) throws RestClientException {
        return restTemplate.getForObject(url, c);
    }

    public Object exchange(String url, Object body, Class c, HttpEntity http, HttpMethod method) throws RestClientException {
        return restTemplate.exchange(url, method, http, c, body).getBody();
    }

    public void delete(String url) throws RestClientException {
        restTemplate.delete(url);
    }


}
