package buza.group.api.handler;

import buza.group.api.common.ResponseCode;
import buza.group.api.common.ServerResponse;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class LogoutSeccussHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.error("----------> 登出成功");
        response.setContentType("application/json;charset=UTF-8");

        ServletOutputStream outputStream = response.getOutputStream();
        ServerResponse serverResponse = ServerResponse.createBySuccessMessage("退出成功");
        outputStream.write(JSONUtil.toJsonStr(serverResponse).getBytes(StandardCharsets.UTF_8));

        outputStream.flush();
        outputStream.close();
    }

}
