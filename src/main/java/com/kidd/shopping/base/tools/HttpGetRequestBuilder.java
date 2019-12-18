package com.kidd.shopping.base.tools;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

public class HttpGetRequestBuilder {
    public static final int HTTP = 0;
    public static final int HTTPS = 1;

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private int protocol;
    private String url;
    private int method;
    private Map<String, Object> params;

    public HttpGetRequestBuilder(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.protocol = HTTP;
        this.headers = new HttpHeaders();
        this.params = new HashMap<>();
    }

    /**
     * Set param cho request là HTTP hay HTTPS, mặc định là HTTP
     *
     * @param protocol loại protocol HTTP hoặc HTTPS
     * @return Đối tượng HttpPostRequestBuilder
     */
    public HttpGetRequestBuilder withProtocol(int protocol) {
        switch (protocol) {
            case HTTP:
            case HTTPS: {
                this.protocol = protocol;
            }
            break;

            default: {
                this.protocol = HTTP;
                break;
            }
        }
        return this;
    }

    public HttpGetRequestBuilder withParam(String key, Object value) {
        params.put(key, value);
        return this;
    }

    public HttpGetRequestBuilder withParam(String key, String... values) {
        if (values.length >= 1) {
            StringBuilder stringBuilder = new StringBuilder(values[0]);
            for (int i = 1; i < values.length; i++) {
                stringBuilder.append(",").append(values[i]);
            }
            params.put(key, stringBuilder.toString());
        }
        return this;
    }

    /**
     * Set url của request
     *
     * @param url url cần execute
     * @return Đối tượng HttpPostRequestBuilder
     */
    public HttpGetRequestBuilder withUrl(String url) {
        if (url != null) {
            this.url = url;
        }
        return this;
    }

    /**
     * Thêm thông tin vào request header
     *
     * @param key   tên của trường
     * @param value giá trị của trường
     * @return Đối tượng HttpPostRequestBuilder
     */
    public HttpGetRequestBuilder addToHeader(String key, String value) {
        if (key != null && value != null) {
            headers.add(key, value);
        }
        return this;
    }

    public <T> T execute(Class<T> responseClass) {
        String fullUrl = getProtocol(protocol) + url;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(fullUrl);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<T> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                entity,
                responseClass);
        return response.getBody();
    }

    private static String getProtocol(int id) {
        switch (id) {
            case HTTP: {
                return "http://";
            }


            case HTTPS: {
                return "https://";
            }

            default: {
                return "http://";
            }
        }
    }
}
