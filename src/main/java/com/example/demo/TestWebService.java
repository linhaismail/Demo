package com.example.demo;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import javax.xml.namespace.QName;
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
//            String endpoint = "http://cloud.qazhis.cn:8088/QAZ/services/ICommonMobileAPIService?wsdl";

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            // WSDL里面描述的接口名称(要调用的方法)
            call.setOperationName("KYBusiness");
//            QName qName = new QName("http://webxml.com.cn/", "KYBusiness");
//            call.setOperationName(qName);
            // 接口方法的参数名, 参数类型,参数模式  IN(输入), OUT(输出) or INOUT(输入输出)
            call.addParameter("KeyCode", XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter("TranCode", XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter("IDCardNo", XMLType.XSD_STRING, ParameterMode.IN);
            // 设置被调用方法的返回值类型
            call.setReturnType(XMLType.XSD_STRING);
            //设置方法中参数的值
            Object[] paramValues = new Object[] {"gxmQZQskCZX+GR9DIm2xR4Jd2GtpDUFn","0002","110101198003076435"};
            // 给方法传递参数，并且调用方法
            String result = (String) call.invoke(paramValues);

            System.out.println("result is " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
