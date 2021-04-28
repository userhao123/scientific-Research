package com.hao.scientificresearch.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号,开发时使用沙箱提供的APPID，生产环境改成自己的APPID
    public static String APP_ID = "2021000117647304";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCXuAVyrD4iR+m0ZUnT2SeTFNjq7PNfK1ceoePTZv1WUoVvoSzbKN6keKc0jlOEwuosvaKzML3jzE4hR7c8cKeNpaWGwzUP9d/9P19WHSHr9p66zUQieST1Fy3sZS1SukdsdnEwhPLXmdyxyQeMHU+88sM0gtEQmO+pmqRjViMrOonL1GhNg+lcti/G5T2zs+PdaWOdvVFpWxbGb5lTKnOpf4tO0zCtR5RpmRSGU0mMLXBX+GCKqTOdHv1TSxBs35+Bg1tMUTAJoDwsWUxFWkrR6DxAo/5hE9tVCHkj9IXtWEU7lwsVTiTiicPuFNKWlr31EYOWaRRI9bcwRr1F+4mpAgMBAAECggEAGxCV0DYPw3fGpgqnl+YWHDPM2mO4qpKFsQ46IR0vNjX0XcyHvvTUesyXL1Xwai+HLMILgyG/ziJFEcTEWEDUcv1IwPiTv6wh8ABdFnSN95UADdzOOcQYpqwi4hfOdQKL+TpLyNb+px1odEKjWQhlkBRSZEyt1OMjZcMTbjEQhwZc+YQLxf/CsvEmCeyL+SfQ777iRqgULXPqSHKuz63W/cQv6YaA1RdJh3NzyS9wug2pYauRM/Gh1L5J7u2qaD1KEZ6fconfcUsyPhT5CMl+N7t+QIML9kxiwvKKaRmr2+EsFYuSaCBQNBBT8hl4O5rhkarySyH011Wt61QSPKoOgQKBgQDIeRVhAZDXZNAaY96uV+tmducVjmmiOfdxI88I4k9VKTUiKW6w+KRFEeuvT83K4xi/lwKaQx3NLGd3NmkpX9KgwezWHwINSR6ccfPq7Lv1O9AbmfIw8ojHlVWghEOjC2UCpAf4gnLzFU6NFN0/7PU5hUQiF+WC7Uo7unCo91QbUQKBgQDBve622G4d+XqMAJW/eSFH6kpKnDSjQutkuVtzdoNXLf9EQW8LozSEu8qqrcTB9iwcZJ7pQo461bQ/Etckx/5iCD/ku5cL6NlPeAVbyh84lukojlEBq8Kbap8WS0wjYTGwlni3cbXD2QAXdeHhxohgJB1DOwV2ZXJNm3/mye3C2QKBgDe+8+L1vIEo13V/L/zrFdp4Ven4oAcsd1JO0tN1mvnja5MEUnODsKpEQXWOi3tPiyI7q82ZvD/BiUPtLXED5F4vjtw74yYunwSB64H4MowPBd+m/2H9D2vwas8n+GbuVDhXxJ+mJrDRy/YPRzbvfYFxPXD9lCTEN1BEl+9kV08BAoGBAIMXOMaPezLc84Z3ftun4lT+unXxa8xsI0psAO0Ha6c5gFd2zuRY8dg+GMuT+GYlY4NysfOzDPnST7u9y8UfbYh3fIQQSgvM6f1TgIyzHpzzv5+pbDZtOahfZHV5TXNC+JQ7vEKjthFmayF0qfpSk0Q6K3KqJPAPhF/ur4RBbZdBAoGBAL01U/lVm9mbU3xNxOoqQpQwUj5ZaCb0I0w4O5oYvkmIzmqX8SkjfZ0XcGdEBL9dcZ3Q09NuMmZjr5JTMZUS7vsIXUYpwmU3fuZETN46M1dwpCnaiijISdPkTsMSkZigece09AlnMJE+QhEVjS+UO7jPT4nj40WYVGWs+fdz52+2";


    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr57vVOE3GcX3R87seZjVg1YLxlqtm5PCudYPDZzLMsgakP2ur943kfcQ9x4l7MMOYDmBpe0lz9pZcQIVM8xTYFhRz6TeOMgnsA8Pijxm9IsPPBE7PIPAgCul76fj7JgKJVOCHex0fzXh5MoC9Xhww/BtieD8AogskOYfy1OZEHlTcH4p5vTKgyR5n9Ltq42dplamjKPHzpRgByPNayIlklyZYdM8ELSK/RNkhtcaVxbPa0ZOQMYq8G83RUllcpiRDsAnFVIXqv/W3I5DaZd0PkgE5qboZZ4r8nbLw0x4X1lY6/y8Eekcw71uKNY6D1lbtY6yFROKqpVFrmNQB+it1QIDAQAB";


    // 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8080/Alipay/notifyUrl";


    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问(其实就是支付成功后返回的页面)
    public static String return_url = "http://localhost:8080/Alipay/returnUrl";


    // 签名方式
    public static String sign_type = "RSA2";


    // 字符编码格式
    public static String CHARSET = "utf-8";


    // 支付宝网关，这是沙箱的网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";


    // 日志路径
    public static String log_path = "D:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */

    public static void logResult(String sWord) {

        FileWriter writer = null;

        try {

            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
