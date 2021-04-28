package com.hao.scientificresearch.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;

public class SendSmsUtil {

    public static void main(String[] args) {
        sendSms();
    }
    public static void sendSms(){
        SmsSingleSenderResult result =null;
        try{
            SmsSingleSender sender = new SmsSingleSender(1400514739,"59c0a25b73dd0e8d49e466650f937a94");
            result = sender.sendWithParam("86", "15521161105", 939135, new String[]{"123456"}, null, "", "");
            String errMsg = result.errMsg;
            int i = result.result;
            System.out.println("发送短信返回的状态码:"+i);
            System.out.println("发送短信返回的信息:"+errMsg);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("发送短信失败");
        }
    }



}
