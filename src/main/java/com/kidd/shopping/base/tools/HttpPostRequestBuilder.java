package com.kidd.shopping.base.tools;

import com.kidd.shopping.utils.HeaderConstant;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Util giúp tạo và execute các http request
 */
public class HttpPostRequestBuilder {
    public static final int HTTP = 0;
    public static final int HTTPS = 1;

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private int protocol;
    private String url;
    private int method;
    private Map<String, Object> params;

    public HttpPostRequestBuilder(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.protocol = HTTP;
        this.headers = new HttpHeaders();
        this.params = new HashMap<>();
    }

    public HttpPostRequestBuilder setContentType(MediaType mediaType) {
        headers.setContentType(mediaType);
        return this;
    }

    /**
     * Set param cho request là HTTP hay HTTPS, mặc định là HTTP
     *
     * @param protocol loại protocol HTTP hoặc HTTPS
     * @return Đối tượng HttpPostRequestBuilder
     */
    public HttpPostRequestBuilder withProtocol(int protocol) {
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

    public HttpPostRequestBuilder withParam(String key, Object value) {
        params.put(key, value);
        return this;
    }

    /**
     * Set url của request
     *
     * @param url url cần execute
     * @return Đối tượng HttpPostRequestBuilder
     */
    public HttpPostRequestBuilder withUrl(String url) {
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
    public HttpPostRequestBuilder addToHeader(String key, String value) {
        if (key != null && value != null) {
            headers.add(key, value);
        }
        return this;
    }

    /**
     * Set form data cho request request_body
     *
     * @param map data cần set vào request_body
     * @return Đối tượng HttpPostRequestBuilder
     */
    public FormDataBodyRequestExecutor setFormDataBody(LinkedMultiValueMap<String, String> map) {
        return new FormDataBodyRequestExecutor(map);
    }

    public class FormDataBodyRequestExecutor {
        private MultiValueMap<String, String> body;

        FormDataBodyRequestExecutor(MultiValueMap<String, String> body) {
            this.body = body;
        }

        public <T> T execute(Class<T> responseClazz) {
            String fullUrl = getProtocol(protocol) + url;
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, headers);
            return restTemplate.postForObject(fullUrl, httpEntity, responseClazz);
        }
    }

    /**
     * Set json data cho request request_body. Tự động thêm content-type: application/json vào request header
     *
     * @param object object cần set vào request_body
     * @return Đối tượng HttpPostRequestBuilder
     */
    public JsonBodyRequestExecutor setJsonBody(Object object) {
        if (object != null) {
            this.headers.add(HeaderConstant.CONTENT_TYPE, HeaderConstant.APPLICATION_JSON);
        }
        return new JsonBodyRequestExecutor(object);
    }

    public class JsonBodyRequestExecutor {
        private Object body;

        JsonBodyRequestExecutor(Object body) {
            this.body = body;
        }

        public <T> T execute(Class<T> responseClazz) {
            String fullUrl = getProtocol(protocol) + url;
            HttpEntity<Object> httpEntity = new HttpEntity<>(body, headers);
            return restTemplate.postForObject(fullUrl, httpEntity, responseClazz);
        }
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
