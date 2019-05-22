package com.app.api.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author Anish Panthi
 */
@Component
@Log4j2
public class ApiRequestResponseInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        log.info("================================================================================================");
        log.info("Request Started Time :: " + new Date(startTime));
        log.info("Request Ended Time :: " + new Date(endTime));
        log.info("Request Completed Time :: " + (System.currentTimeMillis() - startTime) + " millis");
        log.info("================================================================================================");
    }
}
