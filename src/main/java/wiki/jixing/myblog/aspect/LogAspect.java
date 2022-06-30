package wiki.jixing.myblog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import wiki.jixing.myblog.utils.Log;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LogAspect {
    @Resource
    private Log log;
    @Resource
    HttpServletRequest request;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* wiki.jixing.myblog.controller..*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        String ip = request.getRemoteAddr();
        String url = String.valueOf(request.getRequestURL());
        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.setIp(ip);
        log.setUrl(url);
        log.setMethod(method);
        log.setArgs(args);
    }

    @AfterReturning(pointcut = "pointcut()", returning = "result")
    public void afterReturn(Object result) {
        log.setResult(result);
    }

    @After("pointcut()")
    public void after() {
        logger.info(log.toString());
    }
}
