package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * 
 * 
 * @author LinHai
 * @date 2021-05-26 10:26
 */
public class TestWebService1 {
    public static void main(String[] args) {
        OutputStream out = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try {
            String wsUrl = "http://cloud.qazhis.cn:8088/QAZ/services/ICommonMobileAPIService?wsdl";

            URL url = new URL(wsUrl);

            conn = (HttpURLConnection)url.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-type", "text/xml;charset=UTF-8");

            String keyCode = "gxmQZQskCZX+GR9DIm2xR4Jd2GtpDUFn";
            String tranCode = "0002";
            String idCardNo = "110101198003076435";

            String requestBody = "<KYBusiness><KeyCode>"
                    + keyCode + "</KeyCode><TranCode>"
                    + tranCode + "</TranCode><IDCardNo>"
                    + idCardNo + "</IDCardNo></KYBusiness>";

            out = conn.getOutputStream();
            out.write(requestBody.getBytes());

            int respCode = conn.getResponseCode();
            System.out.println("respCode>>>:" + respCode);


            String respMsg = conn.getResponseMessage();
            System.out.println("respMsg>>>:" + respMsg);

            inputStream = conn.getInputStream();

            byte[] b = new byte[1024];
            StringBuilder sb = new StringBuilder();
            int len;
            while ((len = inputStream.read(b)) != -1) {
                sb.append(new String(b, 0, len, StandardCharsets.UTF_8));
            }

            System.out.println("respBody>>>:" + sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
