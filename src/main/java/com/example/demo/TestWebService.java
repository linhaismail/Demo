package com.example.demo;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

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
            Call call = (Call)service.createCall();
            call.setTargetEndpointAddress(endpoint);
            call.setOperation("KYBusiness");

            call.addParameter("KeyCode", XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter("TranCode", XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter("IDCardNo", XMLType.XSD_STRING, ParameterMode.IN);

            call.setReturnType(XMLType.XSD_STRING);

            String result = (String)call.invoke(new Object[]{"gxmQZQskCZX+GR9DIm2xR4Jd2GtpDUFn", "0002", "110101198003076435"});

            System.out.println("result is " + result);

        } catch (ServiceException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
