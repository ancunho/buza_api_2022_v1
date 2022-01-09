package buza.group.api.controller.management;

import buza.group.api.common.ResponseCode;
import buza.group.api.common.ServerResponse;
import buza.group.api.entity.SysUser;
import buza.group.api.model.SysMenuVo;
import buza.group.api.service.RoleMenuService;
import buza.group.api.service.SysUserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/mgt/user")
@Api(value = "用户管理", tags = "用户管理(系统使用者)")
@ApiSupport(order = 3)
public class SysUserController {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 로그인유저에 따른 메뉴 및 권한 가져오기
     * @param principal
     * @return
     */
    @GetMapping(value = "/menu")
    @ApiOperation(value = "权限菜单列表", notes = "根据登录用户获取权限及菜单列表")
    @ApiOperationSupport(order = 100)
    public ServerResponse<Map<String, Object>> backend_menu(Principal principal) {
        String username = principal.getName();
        SysUser sysUser = roleMenuService.selectSysUserByUsername(username);
        String[] authorities = org.springframework.util.StringUtils.tokenizeToStringArray(sysUserService.getUserAuthority(sysUser.getUserSeq()), ",");
        List<SysMenuVo> lstMenu = roleMenuService.getMenuListByUserSeq(sysUser.getUserSeq());
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("menuList", lstMenu);
        returnMap.put("authorities", authorities);

        return ServerResponse.createBySuccess(returnMap);
    }

    @RequestMapping(value="/list", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户列表", notes = "获取用户列表")
    @ApiOperationSupport(order = 1)
    public ServerResponse<List<SysUser>> getAllUser() {

        return null;
    }

    @RequestMapping(value="/info/{userSeq}", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户详细信息", notes = "用户详细信息")
    @ApiOperationSupport(order = 2)
    public ServerResponse<List<SysUser>> getUserInfoByUserSeq(@PathVariable("userSeq") String userSeq) {
        if (StringUtils.isEmpty(userSeq)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        return null;
    }

}
