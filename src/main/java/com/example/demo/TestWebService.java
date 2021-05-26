package com.example.demo;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import javax.xml.rpc.ParameterMode;

/**
 * 测试WebService调用
 *
 * @author LinHai
 * @date 2021-05-25 15:24
 */
public class TestWebService {

    public static void main(String[] args) {
        try {
            String endpoint = "http://cloud.qazhis.cn:8088/QAZ/services/ICommonMobileAPIService";

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);

            // 方法名
            call.setOperationName("KYBusiness");
            // 参数名，参照 WSDL 返回的描述
            call.addParameter("in0", XMLType.XSD_STRING, ParameterMode.IN);
            // 返回值类型
            call.setReturnType(XMLType.XSD_STRING);
            // 参数值
            Object[] paramValues = new Object[] {"<Request>\n" +
                    "    <KeyCode>gxmQZQskCZX+GR9DIm2xR4Jd2GtpDUFn</KeyCode>\n" +
                    "<TranCode>0002</TranCode>\n" +
                    "<IDCardNo>110101198003076435</IDCardNo>\n" +
                    "</Request>"};

            // 给方法传递参数，并且调用方法
            String result = (String) call.invoke(paramValues);

            System.out.println("result is " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
