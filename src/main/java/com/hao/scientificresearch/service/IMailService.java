package com.hao.scientificresearch.service;

import com.hao.scientificresearch.model.param.MailParam;

/**
 * 邮件发送接口
 */
public interface IMailService {
    void sendMail(MailParam param);
}
