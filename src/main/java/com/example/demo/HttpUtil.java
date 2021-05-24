package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HTTP发送请求的工具类
 */
@Slf4j
public class HttpUtil {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     */
    public static String sendGet(String url, String referer) throws Exception {
        String result = "";
//        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Referer", referer);
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            log.info("输出响应头...");
            for (String key : map.keySet()) {
                log.info(key + "--->" + map.get(key));
            }
//            // 定义 BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String line;
//            StringBuilder sb = new StringBuilder();
//            while ((line = in.readLine()) != null) {
//                sb.append(line);
//            }
//            result = sb.toString();

            // 只获取cookie
            result = map.get("zwat-id").get(0);

        } catch (Exception e) {
            log.warn("发送GET请求出现异常:", e);
            throw e;
        } finally {// 使用finally块来关闭输入流
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (Exception e2) {
//                log.warn("关闭网络连接流异常：", e2);
//            }
        }
        return "zwat-id" + "=" + result;
    }

    public static String sendPostWithCert(String url, String data, InputStream certStream, String password) throws Exception {
        return sendPost(url, data, null, 5000, 10000, Boolean.TRUE, certStream, password);
    }

    public static String sendPost(String url, String data, Map<String, String> headers) throws Exception {
        return sendPost(url, data, headers, 5000, 10000, Boolean.FALSE, null, null);
    }

    private static String sendPost(String url, String data, Map<String, String> headers, int connectTimeoutMs, int readTimeoutMs, boolean useCert, InputStream certStream, String password) throws Exception {
        BasicHttpClientConnectionManager connManager;
        if (useCert) {
            // 证书
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(certStream, password.toCharArray());

            // 实例化密钥库 & 初始化密钥工厂
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, password.toCharArray());

            // 创建 SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    new DefaultHostnameVerifier());

            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", sslConnectionSocketFactory)
                            .build(),
                    null,
                    null,
                    null
            );
        }
        else {
            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", SSLConnectionSocketFactory.getSocketFactory())
                            .build(),
                    null,
                    null,
                    null
            );
        }

        org.apache.http.client.HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();

        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeoutMs).setConnectTimeout(connectTimeoutMs).build();
        httpPost.setConfig(requestConfig);

        StringEntity postEntity = new StringEntity(data, ContentType.APPLICATION_JSON);
        for (String key : headers.keySet()) {
            httpPost.addHeader(key, headers.get(key));
        }
        httpPost.setEntity(postEntity);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        Header[] allHeaders = httpResponse.getAllHeaders();
        for (Header header : allHeaders) {
            log.info(header.getName() + "--->" + header.getValue());
        }
        return EntityUtils.toString(httpEntity, "UTF-8");

    }

    public static String sendPostJson(String url, String param) throws Exception {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Content-Type", "application/json");
        return sendPostJson(url, headMap, param);
    }

    public static String sendPostJson(String url, Map<String, String> headMap, String param) throws Exception {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        InputStream responseStream = null;
        BufferedReader rd;
        String str;
        try {
            RequestEntity requestEntity = new StringRequestEntity(param, headMap.get("Content-Type"), "UTF-8");
            if (!headMap.isEmpty()) {
                for (String s : headMap.keySet()) {
                    postMethod.setRequestHeader(s, headMap.get(s));
                }
            }
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            org.apache.commons.httpclient.Header[] headers = postMethod.getResponseHeaders();
            for (org.apache.commons.httpclient.Header header : headers) {
                log.info(header.getName() + "--->" + header.getValue());
            }
            responseStream = postMethod.getResponseBodyAsStream();
            rd = new BufferedReader(new InputStreamReader(responseStream, "utf-8"));
            String tempLine = rd.readLine();
            StringBuilder tempStr = new StringBuilder();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                tempStr.append(tempLine);
                tempStr.append(crlf);
                tempLine = rd.readLine();
            }
            str = tempStr.toString();
        } catch (Exception e) {
            log.info("http post fail:", e);
            throw e;
        } finally {
            postMethod.releaseConnection();
            if (responseStream != null) {
                try {
                    responseStream.close();
                } catch (IOException e) {
                    log.error("请求发送异常:", e);
                }
            }
        }
        return str;
    }
}
