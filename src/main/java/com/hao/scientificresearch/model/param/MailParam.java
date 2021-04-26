package com.hao.scientificresearch.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailParam {
    private String id;
    //发件人邮箱
    private String from;
    //收件人邮箱
    private String[] to;
    //邮件主题
    private String subject;
    //邮件内容
    private String text;
    //抄送
    private String cs;
    //暗抄送
    private String acs;
    //时间
    private Date date;

}
