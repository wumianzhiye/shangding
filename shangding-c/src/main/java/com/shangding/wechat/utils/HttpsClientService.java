package com.shangding.wechat.utils;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 1. Httpclient请求都可以使用此类
 * 2. 简单封装了get/post的方法，支持自定义编码，自定义header
 * 3. 使用实例可参考测试用例中的代码: HttpsClientServiceTest
 * <p>
 */
@Service
public class HttpsClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpsClientService.class);

    /**
     * 每次创建一个新的 httpsClient，不共用使用连接池的 httpsClient
     * 避免在同一个route上请求卡死的情况
     */
    private CloseableHttpClient getHttpsClient() {
        return HttpClients.custom()
                .setConnectionManager(new BasicHttpClientConnectionManager())
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(3000)
                        .setSocketTimeout(15000)
                        .build())
                .build();
    }

    private static final ThreadLocal<Map<String, String>> MAP_THREAD_LOCAL = ThreadLocal.withInitial(() -> new HashMap<>());

    public HttpsClientService setHeaders(Map<String, String> headers) {
        MAP_THREAD_LOCAL.set(headers);
        return this;
    }

    public Map<String, String> getHeaders() {
        return MAP_THREAD_LOCAL.get();
    }

    public String get(String url) {
        return get(url, Charsets.UTF_8);
    }

    public String get(String url, Charset charset) {
        try {
            HttpGet httpGet = new HttpGet(url);
            return execute(httpGet, charset);
        } catch (Exception e) {
            LOGGER.error("httpclient get request error for url : {}", url, e);
        }

        LOGGER.info("httpclient get null for url : {}", url);
        return null;
    }

    public String post(String url, Map<String, String> paramsMap) {
        return post(url, Charsets.UTF_8, paramsMap);
    }

    public String post(String url, Charset charset, Map<String, String> paramsMap) {
        LOGGER.info("httpclient post url {} with params : {}", url, paramsMap);

        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, charset));
            }

            return execute(method, charset);
        } catch (Exception e) {
            LOGGER.error("httpclient post error for url : {}", url, e);
        }

        return null;
    }

    public String postJSON(String url, String json) {
        Map<String, String> headers = getHeaders();
        headers.put("Content-Type", "application/json");
        setHeaders(headers);
        return post(url, json);
    }

    /**
     * default post JSON with plain/text media content type and using utf-8 encoding
     */
    public String post(String url, String body) {
        LOGGER.info("httpclient post url {} with body {}", url, body);
        try {
            HttpPost method = new HttpPost(url);
            if (!Strings.isNullOrEmpty(body)) {
                method.setEntity(new StringEntity(body, Charsets.UTF_8));
            }
            return execute(method, Charsets.UTF_8);
        } catch (Exception e) {
            LOGGER.error("httpclient post error for url : {}", url, e);
        }

        return null;
    }

    /**
     * 支持普通参数与文件的混合提交
     *
     * @param params  普通参数
     * @param files   文件参数
     */
    public String post(String url, Charset charset, Map<String, String> params, Map<String, File> files) {
        LOGGER.info("httpclient post url {} with params : {}, files : {}", url, params, files);
        try {
            HttpPost method = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            if (params != null) {
                for (Map.Entry<String, String> param : params.entrySet()) {
                    StringBody value = new StringBody(param.getValue(), ContentType.create("text/plain", charset));
                    builder.addPart(param.getKey(), value);
                }
            }

            if (files != null) {
                for (Map.Entry<String, File> param : files.entrySet()) {
                    builder.addPart(param.getKey(), new FileBody(param.getValue()));
                }
            }

            method.setEntity(builder.build());

            return execute(method, Charsets.UTF_8);
        } catch (Exception e) {
            LOGGER.error("httpclient post error for url : {}", url, e);
        }

        return null;
    }

    private String execute(final HttpUriRequest request, final Charset charset) {
        RetryTemplate template = new RetryTemplate();
        template.setRetryPolicy(new SimpleRetryPolicy(2));
        try {
            return template.execute(getRetryCallback(request, charset));
        } catch (Exception e) {
            LOGGER.error("httpclient execute error : {}", request.getURI(), e);
        }

        return null;
    }

    private RetryCallback<String, Exception> getRetryCallback(HttpUriRequest request, Charset charset) {
        return new RetryCallback<String, Exception>() {
            public String doWithRetry(RetryContext context) throws Exception {
                try (CloseableHttpClient httpsClient = getHttpsClient()) {
                    setHttpHeaders(request);
                    CloseableHttpResponse response = httpsClient.execute(request);

                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == HttpStatus.SC_OK) {
                        HttpEntity entity = response.getEntity();
                        if (entity != null) {
                            return EntityUtils.toString(entity, charset);
                        }
                    } else {
                        HttpEntity entity = response.getEntity();
                        String body = EntityUtils.toString(entity);
                        LOGGER.error("httpclient statusCode is {} and body : {}", statusCode, body);
                    }
                    return null;
                } finally {
                    MAP_THREAD_LOCAL.remove();
                }
            }
        };
    }

    private void setHttpHeaders(HttpUriRequest request) {
        Map<String, String> map = MAP_THREAD_LOCAL.get();
        if (map != null) {
            LOGGER.debug("httpclient add request headers is {}", map);
            Set<String> keys = map.keySet();
            for (String key : keys) {
                String value = map.get(key);
                LOGGER.debug("header key and value is : {} = {}", key, value);
                request.setHeader(key, value);
            }
        }
    }

}
