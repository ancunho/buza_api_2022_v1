package buza.group.api.handler;

import buza.group.api.common.Const;
import buza.group.api.common.ServerResponse;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 自定义认证失败处理类
 * @author anchunhao
 */
@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("----------> 认证失败");
        response.setContentType("application/json;charset=UTF-8");

        ServletOutputStream outputStream = response.getOutputStream();
        ServerResponse serverResponse = ServerResponse.createByErrorMessage("认证失败");
        outputStream.write(JSONUtil.toJsonStr(serverResponse).getBytes(StandardCharsets.UTF_8));

        outputStream.flush();
        outputStream.close();
    }

    public void onAuthenticationFailureForCaptchaKey(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        ServerResponse serverResponse = ServerResponse.createByErrorMessage(Const.Message.CAPTCHAR_KEY_ERROR);
        outputStream.write(JSONUtil.toJsonStr(serverResponse).getBytes(StandardCharsets.UTF_8));

        outputStream.flush();
        outputStream.close();
    }
}
