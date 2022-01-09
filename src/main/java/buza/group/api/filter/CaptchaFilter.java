package buza.group.api.filter;

import buza.group.api.common.Const;
import buza.group.api.exception.CaptchaException;
import buza.group.api.handler.LoginFailureHandler;
import buza.group.api.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 图片验证码校验过滤器，在登录过滤器前
 * */
@Slf4j
@Component
public class CaptchaFilter extends OncePerRequestFilter {

    private final String loginUrl = "/login";

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();

        if (loginUrl.equals(url) && request.getMethod().equals("POST")) {
            //1. 校验验证码
            try {
                this.validateCaptcha(request);
            } catch (CaptchaException e) {
                //交给认证失败处理器
                loginFailureHandler.onAuthenticationFailureForCaptchaKey(request, response, e);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void validateCaptcha(HttpServletRequest request) {
        String captchaCode = request.getParameter("captchaCode");
        String captchaKey = request.getParameter("captchaKey"); //captchaKey

        if (StringUtils.isBlank(captchaCode) || StringUtils.isBlank(captchaKey)) {
            throw new CaptchaException("验证码不能为空");
        }

        if (!captchaCode.equals(redisUtil.hget(Const.CAPTCHA_KEY, captchaKey))) {
            throw new CaptchaException("验证码错误");
        }

        redisUtil.hdel(Const.CAPTCHA_KEY, captchaKey);
    }
}
