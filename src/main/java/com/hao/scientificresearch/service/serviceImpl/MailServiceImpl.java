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
}
