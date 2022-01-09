package buza.group.api.handler;

import buza.group.api.common.ServerResponse;
import buza.group.api.util.JwtTokenUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //生成token
        final String realToken = jwtTokenUtil.generateToken(authentication.getName());
        HashMap<String,Object> map = new HashMap<>();
        map.put("token", realToken);

        //将生成的authentication放入容器中，生成安全的上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String json =  JSON.toJSONString(ServerResponse.createBySuccess("登录成功", map));
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(json);
    }
}
