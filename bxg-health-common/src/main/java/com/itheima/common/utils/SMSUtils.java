package com.itheima.common.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 短信发送工具类
 */
public class SMSUtils {
    public static final String VALIDATE_CODE = "SMS_175475144";// 发送短信验证码 模板CODE
    public static final String ORDER_NOTICE = "SMS_175490483";// 体检预约成功通知 模板CODE
    public static final String ACCESSKEYID = "";// accessKeyId
    public static final String ACCESSKEYSECRET = "";// accessKeySecret
    public static final String SIGNNAME = "";// signName签名
    /**
     * 发送短信
     * @param templateCode 模板
     * @param phoneNumbers 手机号
     * @param code 参数
     */
    public static void sendMessage(String templateCode,String phoneNumbers,String code){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESSKEYID, ACCESSKEYSECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setSysRegionId("cn-hangzhou");
        request.setPhoneNumbers(phoneNumbers);
        request.setSignName(SIGNNAME);
        request.setTemplateCode(templateCode);
        request.setTemplateParam("{\"code\":\""+code+"\"}");
        try {
            SendSmsResponse response = client.getAcsResponse(request);
            System.out.println(response.getMessage());
        }catch (ClientException e) {
            e.printStackTrace();
        }
    }

}