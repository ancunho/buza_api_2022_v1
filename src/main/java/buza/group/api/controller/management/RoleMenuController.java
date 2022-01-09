package buza.group.api.controller.management;

import buza.group.api.common.ServerResponse;
import buza.group.api.model.SysMenuVo;
import buza.group.api.model.SysRoleVo;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/mgt/system")
@Api(value = "权限菜单管理", tags = "权限菜单管理")
@ApiSupport(order = 2)
public class RoleMenuController {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private SysUserService sysUserService;

    @GetMapping(value = "/menu/list")
    @ApiOperation(value = "菜单列表", notes = "获取全部菜单列表")
    @ApiOperationSupport(order = 200)
    @PreAuthorize("hasRole('ADMIN')")
    public ServerResponse<List<SysMenuVo>> getAllSysMenuList() {
        List<SysMenuVo> lstSysMenuVo = roleMenuService.getAllSysMenuList();
        return ServerResponse.createBySuccess(lstSysMenuVo);
    }

    @PostMapping(value = "/menu/add")
    @ApiOperation(value = "新增菜单", notes = "新增菜单")
    @ApiOperationSupport(order = 201)
    @PreAuthorize("hasRole('ADMIN')")
    public ServerResponse insertNewMenu(@RequestBody SysMenuVo sysMenuVo) {
        // 1. name,type,perms,status
        if (StringUtils.isEmpty(sysMenuVo.getName())
            || StringUtils.isEmpty(sysMenuVo.getType())
            || StringUtils.isEmpty(sysMenuVo.getPerms())
            || StringUtils.isEmpty(sysMenuVo.getStatus())
        ) {
            return ServerResponse.createByErrorMessage("菜单名,类型,权限Code,状态不能为空");
        }

        // 2. menu명 && perms중복체크
        boolean checkCountFlag = roleMenuService.checkMenuAndPerms(sysMenuVo);
        if (!checkCountFlag) {
            return ServerResponse.createByErrorMessage("菜单名或权限Code重复，请确认");
        }

        // 3. 저장
        int insertCount = roleMenuService.insertNewMenu(sysMenuVo);
        if (insertCount > 0) {
            return ServerResponse.createBySuccessMessage("新建成功");
        }
        return ServerResponse.createByErrorMessage("新建菜单失败");
    }

    @PostMapping(value = "/menu/modify")
    @ApiOperation(value = "메뉴수정", notes = "메뉴수정")
    @ApiOperationSupport(order = 202)
    @PreAuthorize("hasRole('ADMIN')")
    public ServerResponse modifyMenu(@RequestBody SysMenuVo sysMenuVo) {
        // 1. menuId유무체크
        if (sysMenuVo.getId() == null
                || StringUtils.isEmpty(sysMenuVo.getName())
                || StringUtils.isEmpty(sysMenuVo.getType())
                || StringUtils.isEmpty(sysMenuVo.getPerms())
                || StringUtils.isEmpty(sysMenuVo.getStatus())
        ) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        // 2. menu명 && perms중복체크
        boolean checkCountFlag = roleMenuService.checkMenuAndPermsExceptSelf(sysMenuVo);
        if (!checkCountFlag) {
            return ServerResponse.createByErrorMessage("菜单名或权限Code重复，请确认");
        }

        int updateCount = roleMenuService.modifyMenu(sysMenuVo);
        if (updateCount > 0) {
            return ServerResponse.createBySuccessMessage("修改成功");
        }

        return ServerResponse.createByErrorMessage("修改失败");
    }

    /**
     * @TODO 전체권한리스트 가져오기
     */
    @GetMapping(value = "/role/list")
    @ApiOperation(value = "权限列表", notes = "获取全部权限列表")
    @ApiOperationSupport(order = 300)
    @PreAuthorize("hasRole('ADMIN')")
    public ServerResponse<List<SysRoleVo>> getSysRoleList() {
        return ServerResponse.createBySuccess();
    }


    // TODO 권한추가 path: /role/add
    @GetMapping(value = "/role/add")
    @ApiOperation(value = "권한추가", notes = "권한추가")
    @ApiOperationSupport(order = 301)
    public ServerResponse<List<SysRoleVo>> insertSysRole() {
        return null;
    }
    // TODO 권한수정 path: /role/edit/{roleId}
    // TODO 권한삭제 path: /role/delete/{roleId}


    public ServerResponse<List<SysMenuVo>> getSysMenuListByRoleId(@RequestParam("asdf") Integer roleId) {
        return null;
    }


}
