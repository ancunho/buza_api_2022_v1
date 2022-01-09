package buza.group.api.controller;

import buza.group.api.service.HelloService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@RestController
@RequestMapping(value = "/hello")
@Api(value = "入口测试用", tags = "入口测试用")
@ApiSupport(order = 0)
public class HelloController extends BaseController{

    @Autowired
    private HelloService helloService;


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ApiOperation(value = "admin权限", notes = "admin权限")
    public String index() {
        return "Hello world";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ApiOperation(value = "user权限", notes = "user权限")
    public ResponseEntity<List<Map<String, Object>>> getAllSysUser(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> getAllSysUsers = helloService.getAllSysUser();

        return ResponseEntity.ok().body(getAllSysUsers);
    }

}
