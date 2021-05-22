package com.example.demo;

import com.alibaba.fastjson.JSONObject;

public class TestRequestApi {

    public static void main(String[] args) {
        testGet();

        System.out.println("-------------");

        testPost();
    }

    public static void testGet() {
        String url = "http://api.hnzwxt.cn/bhc-manager-api-v2/token?appid=system&password=123";
        String respStr = null;
        try {
            respStr = HttpUtil.sendGet(url, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("GET响应>>>:" + respStr);
    }

    public static void testPost() {
        String url = "http://api.hnzwxt.cn/bhc-manager-api-v2/his/proxy/";
        JSONObject paramJson = new JSONObject();
        paramJson.put("tranCode", "1001");
        paramJson.put("hospitalld", "1023");
        paramJson.put("areaId", "2");
        paramJson.put("cardType", "1");
        paramJson.put("cardValue", "8014709");
        String respStr = null;
        try {
            respStr = HttpUtil.sendPost(url, paramJson.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("POST响应>>>:" + respStr);
    }

}
