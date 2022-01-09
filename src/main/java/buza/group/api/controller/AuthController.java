package buza.group.api.controller;

import buza.group.api.common.Const;
import buza.group.api.common.ServerResponse;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Slf4j
@RestController
@Api(value = "获取验证码23", tags = "获取验证码1")
@ApiSupport(order = 1)
public class AuthController extends BaseController {

    @Autowired
    private Producer producer;

    /**
     * 图片验证码
     */
    @GetMapping("/captcha")
    @ApiOperation(value = "获取验证码2", notes = "获取验证码")
    public ServerResponse captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String captchaCode = producer.createText();
        String captchaKey = UUID.randomUUID().toString();

        // For Test
//        code = "code111";
//        captchaKey="key111";

        BufferedImage image = producer.createImage(captchaCode);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";
        String base64Img = str + encoder.encode(outputStream.toByteArray());

        // Redis에 저장
        redisUtil.hset(Const.CAPTCHA_KEY, captchaKey, captchaCode, 120);
        log.info("验证码 -- {} - {}", captchaKey, captchaCode);

        return ServerResponse.createBySuccess(
                MapUtil.builder()
                        .put("captchaKey", captchaKey)
                        .put("captchaCode", captchaCode)
                        .put("base64Img", base64Img)
                        .build()
        );
    }


}
