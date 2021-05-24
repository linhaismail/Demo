package com.example.demo;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TestRequestApi {

    public static String testGet() {
        String url = "http://api.hnzwxt.cn/bhc-manager-api-v2/token?appid=system&password=123";
        String result =  null;
        try {
            result = HttpUtil.sendGet(url, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("GET响应>>>:" + result);
        return result;
    }

    public static void main(String[] args) {
        String cookie = testGet();
//
//        System.out.println("-------------");

//        String token = "QM5qVunFfIT3H16Zw8dNLqNwnxCp3HoQt7mOnnyEYH7wKnt4fEG0IZhYma5dyJUootnissiBAx34yYg4-N2JAZ0MbUvuiV_XxaK4UOmxuFFY8hM9CgQVwIJSPmuAKgLUjElEtZVaFkzUWczUuBV63AWmNQifw1ZIGnRVSKdhCtSYTZOR089l5LdluQ9AxTnWANVydLW8eZxIpCD62XBSnQ";
//        String cookie = "zwat-id=XeAH_92vGU1R4WyxzI9DRR99sx7lvFkujwcG0qHIMEtrtCQ-Dv_kwr9HmTf5gdcD0w5AmUgRawKjjiLuHx8qqhD1gjOokGjWAAGGk0oqwNlY8hM9CgQVwIJSPmuAKgLUjElEtZVaFkzUWczUuBV63OyG15wDFU4FraFdAXdHxvktsi1FfAjW_dI8ge5ErqafANVydLW8eZxIpCD62XBSnQ; Max-Age=1800; Expires=Mon, 24-May-2021 06:21:33 GMT; Path=/, zwat-id=XeAH_92vGU1R4WyxzI9DRR99sx7lvFkujwcG0qHIMEtrtCQ-Dv_kwr9HmTf5gdcD0w5AmUgRawKjjiLuHx8qqhD1gjOokGjWAAGGk0oqwNlY8hM9CgQVwIJSPmuAKgLUjElEtZVaFkzUWczUuBV63OyG15wDFU4FraFdAXdHxvktsi1FfAjW_dI8ge5ErqafANVydLW8eZxIpCD62XBSnQ";
//        String cookie = "zwat-id=XeAH_92vGU1R4WyxzI9DRR99sx7lvFkujwcG0qHIMEtrtCQ-Dv_kwr9HmTf5gdcD0w5AmUgRawKjjiLuHx8qqhD1gjOokGjWAAGGk0oqwNlY8hM9CgQVwIJSPmuAKgLUjElEtZVaFkzUWczUuBV63OyG15wDFU4FraFdAXdHxvktsi1FfAjW_dI8ge5ErqafANVydLW8eZxIpCD62XBSnQ; Max-Age=1800; Expires=Mon, 24-May-2021 06:21:33 GMT; Path=/, zwat-id=XeAH_92vGU1R4WyxzI9DRR99sx7lvFkujwcG0qHIMEtrtCQ-Dv_kwr9HmTf5gdcD0w5AmUgRawKjjiLuHx8qqhD1gjOokGjWAAGGk0oqwNlY8hM9CgQVwIJSPmuAKgLUjElEtZVaFkzUWczUuBV63OyG15wDFU4FraFdAXdHxvktsi1FfAjW_dI8ge5ErqafANVydLW8eZxIpCD62XBSnQ";
        testPost(null, cookie);
    }

    public static void testPost(String token, String cookie) {
        String url = "http://api.hnzwxt.cn/bhc-manager-api-v2/his/proxy";
        JSONObject paramJson = new JSONObject();
//        paramJson.put("token", token);
        paramJson.put("tranCode", "8001");
        paramJson.put("hospitalId", "1023");
        paramJson.put("areaId", "2");
        paramJson.put("patId", "234361");
        paramJson.put("accountType", "0");
        paramJson.put("beginDate", "2015-02-20");
        paramJson.put("endDate", "2017-06-28");
        String respStr = null;
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("Accept", "*/*");
            headers.put("User-Agent", "PostmanRuntime/7.28.0");
            headers.put("Cookie", cookie);
//            respStr = HttpUtil.sendPostJson(url, headers, paramJson.toJSONString());
            respStr = HttpUtil.sendPost(url, paramJson.toJSONString(), headers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("POST响应>>>:" + respStr);
    }

}
