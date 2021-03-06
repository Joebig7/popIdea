package com.mamba.popidea.handler;

import com.mamba.popidea.exception.ErrorCodes;
import com.mamba.popidea.exception.RestException;
import com.mamba.popidea.model.common.project.Audience;
import com.mamba.popidea.utils.JwtUtil;
import com.mamba.popidea.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mamba.popidea.constant.ConstantEnum.USERID;

/**
 * @version 1.0
 * @author: JoeBig7
 * @date: 2019/5/21 12:37
 */
@Component
public class AuthorizationHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    private Audience audience;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        final String authHeader = request.getHeader("Authorization");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        } else {
            inspectToken(authHeader, request);
            return true;
        }
    }

    private void inspectToken(final String headerInfo, HttpServletRequest request) {
        if (headerInfo == null || !headerInfo.startsWith("Bearer ")) {
            throw RestException.newInstance(ErrorCodes.TOKEN_CHECKED_ERROR);
        }
        final String token = headerInfo.substring(7);
        try {
            final Claims claims = JwtUtil.parseJWT(token, audience.getBase64Secret());
            if (claims != null && request.getAttribute("userId") == null) {
                request.setAttribute(USERID.field(), JwtUtil.getUserId(claims));
            }
        } catch (Exception e) {
            throw RestException.newInstance(ErrorCodes.TOKEN_CHECKED_ERROR);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }


}