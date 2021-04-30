package com.hao.scientificresearch.service.serviceImpl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hao.scientificresearch.model.param.MailParam;
import com.hao.scientificresearch.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MailServiceImpl implements IMailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMail(MailParam param) {
        try {
            //设置邮件属性
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            messageHelper.setFrom(param.getFrom());
            messageHelper.setTo(param.getTo());
            messageHelper.setSubject(param.getSubject());
            messageHelper.setText(param.getText());
            messageHelper.setSentDate(param.getDate());
            if(StringUtils.isNotBlank(param.getAcs())){
                messageHelper.setBcc(param.getAcs());
            }
            if(StringUtils.isNotBlank(param.getCs())){
                messageHelper.setCc(param.getCs());
            }
            //调用spring邮件服务API发送邮件
            mailSender.send(messageHelper.getMimeMessage());
            System.out.println("发送邮件成功:"+param.getFrom()+"------>"+ Arrays.toString(param.getTo()));
        } catch (MessagingException e) {
            System.out.println("发送邮件异常");
            e.printStackTrace();
        }

    }

    /**
     *
     * @param to 邮箱字符串数组
     * @return  是否符合邮箱格式
     */
    public static boolean checkMail(String[] to){
        boolean b;
        Pattern pattern=Pattern.compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        for(String str:to){
           b = pattern.matcher(str).matches();
           if(!b){
               return false;
           }
        }
        return true;
    }

    public static void main(String[] args) {
        String[] to =new String[]{"123@qq.com","3084921312@qq.com"};
        System.out.println("邮箱格式:"+checkMail(to));
    }
}
