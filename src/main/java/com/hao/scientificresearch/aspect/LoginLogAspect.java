package com.hao.scientificresearch.aspect;


import cn.hutool.core.util.ObjectUtil;
import com.hao.scientificresearch.entity.Administrator;
import com.hao.scientificresearch.entity.LoginLog;
import com.hao.scientificresearch.entity.Researcher;
import com.hao.scientificresearch.exception.NoLoginException;
import com.hao.scientificresearch.service.ILoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class LoginLogAspect {

    @Autowired
    private ILoginLogService loginLogService;
    //日志对象
    private static final Logger logger = LoggerFactory.getLogger(LoginLogAspect.class);


    @Pointcut("@annotation(com.hao.scientificresearch.aspect.LoginLogAnno)")
    public void point(){};

    //切面后置通知，登陆后记录登录日志,@AfterReturning方法正常返回值才记录
    @AfterReturning("point()")
    public void doAfter(){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpSession session = request.getSession();
        Object user = session.getAttribute("loginUser");
        if(ObjectUtil.isEmpty(user)) throw new NoLoginException("未登录");
        try{
            LoginLog log = new LoginLog();
            Researcher loginUser = null;
            Administrator admin = null;
            if(user instanceof Researcher){
                 loginUser = (Researcher) user;
                log.setUserId(loginUser.getId());
                log.setUserName(loginUser.getUsername());
            }
            if(user instanceof Administrator){
                admin = (Administrator) user;
                log.setUserId(admin.getId());
                log.setUserName(admin.getUsername());
            }
            log.setLoginIp(request.getRemoteAddr());
            log.setLoginTime(LocalDateTime.now());

            loginLogService.save(log);
            logger.info("登录用户: {}",loginUser!=null?loginUser:admin);
        }catch (Exception e){
            logger.error("异常信息: {}",e.getMessage());
//            logger.error("登录异常，用户: {}",loginUser!=null?);
        }
    }





}
