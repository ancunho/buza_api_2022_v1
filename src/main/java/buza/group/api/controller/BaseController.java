package buza.group.api.controller;

import buza.group.api.util.RedisUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Getter
@Setter
public class BaseController {

    @Autowired
    public RedisUtil redisUtil;

//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;

//    protected CustomUserDetails getCustomUserDetail() {
//        String token = null;
//        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
//        HttpServletRequest request = sra.getRequest();
//
//        token = Util.nullempty(request.getHeader("token"));
//        if ("".equals(token)) {
//            Cookie[] cookies = request.getCookies();
//            if (cookies != null && cookies.length > 0) {
//                for (Cookie cookie : cookies) {
//                    if ("token".equalsIgnoreCase(cookie.getName())) {
//                        token = cookie.getValue();
//                    }
//                }
//            }
//        }
//        if ("".equals(token)) {
//            token = Util.nullempty(request.getParameter("token"));
//        }
//        System.out.println(">>token : " + token);
//        return (CustomUserDetails) getJwtTokenProvider().getAuthentication(token).getPrincipal();
//    }

    protected HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }

    protected HttpServletResponse getResponse() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletResponse response = sra.getResponse();
        return response;
    }

}
