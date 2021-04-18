package com.hao.scientificresearch.aspect;

import com.hao.scientificresearch.entity.OperationLog;
import com.hao.scientificresearch.entity.Researcher;
import com.hao.scientificresearch.exception.NoLoginException;
import com.hao.scientificresearch.service.IOperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
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
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Component
@Aspect
public class OperationLogAspect {

    @Autowired
    private IOperationLogService operationLog;

    private static final Logger logger = LoggerFactory.getLogger(OperationLogAspect.class);

    @Pointcut("@annotation(com.hao.scientificresearch.aspect.OperationLogAnno)")
    public void point() {
    }

    ;

    //方法正常结束进行操作日志的入库
    @AfterReturning("point()")
    public void doAfter(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpSession session = request.getSession();
        Researcher loginUser = (Researcher) session.getAttribute("loginUser");
        if (loginUser == null) throw new NoLoginException("未登录");
        try {
            OperationLog log = new OperationLog();
            log.setOperateTime(LocalDateTime.now());
            log.setUserId(loginUser.getId());
            log.setOperation(getAnnoDesc(joinPoint));
            log.setUserName(loginUser.getUsername());
            System.out.println("操作日志");
            operationLog.save(log);
        } catch (Exception e) {
            logger.error("异常信息: {}", e.getMessage());
            logger.error("登录异常，用户: {}", loginUser);
        }


    }


    //获取注解中的属性信息
    public static String getAnnoDesc(JoinPoint joinPoint) throws Exception {
        //该对象的类名
        String targetName = joinPoint.getTarget().getClass().getName();
        //方法名
        String methodName = joinPoint.getSignature().getName();
        //方法参数数组
        Object[] arguments = joinPoint.getArgs();

        //获取该类的所有方法
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();

        System.out.println("对象类名:" + targetName);
        System.out.println("方法名:" + methodName);
        System.out.println("参数:" + Arrays.toString(arguments));
        System.out.println("方法:" + Arrays.toString(methods));
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                //获取方法的参数类型数组
                Class[] clazzs = method.getParameterTypes();
                System.out.println("类对象:" + Arrays.toString(clazzs));
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(OperationLogAnno.class).desc();
                    break;
                }
            }
        }
        //获取参数，组装成返回的具体操作的数据的字符串
        Object argument = arguments[0];
        String subArg = description.substring(2);
        if (argument instanceof Integer) {
            description = description + "--" + subArg + "id:" + argument;
        } else {
            //执行对象的getName方法，获取操作的数据名
            Object name = getGetMethod(argument, "name");
            description = description + "--" + subArg + "名:" + name;
        }
        return description;
    }


    /**
     * 根据属性，获取get方法
     *
     * @param ob   对象
     * @param name 属性名
     * @return
     * @throws Exception
     */
    public static Object getGetMethod(Object ob, String name) throws Exception {
        Method[] m = ob.getClass().getMethods();
        for (int i = 0; i < m.length; i++) {
            if (("get" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
                return m[i].invoke(ob);
            }
        }
        return null;
    }


}
