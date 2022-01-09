package buza.group.api.service;

import buza.group.api.common.ServerResponse;
import buza.group.api.entity.SysMenu;
import buza.group.api.entity.SysUser;
import buza.group.api.model.SysMenuVo;
import buza.group.api.model.SysRoleVo;
import buza.group.api.model.UserInfoVo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RoleMenuService {

    SysUser selectSysUserByUsername(String username);
    List<UserInfoVo> selectSysUserByUserSeq(Integer userSeq);
    List<SysMenuVo> getMenuListByUserSeq(Integer userSeq);

    List<SysMenuVo> getAllSysMenuList();
    boolean checkMenuAndPerms(SysMenuVo sysMenuVo);
    boolean checkMenuAndPermsExceptSelf(SysMenuVo sysMenuVo);
    int insertNewMenu(SysMenuVo sysMenuVo);
    int modifyMenu(SysMenuVo sysMenuVo);


    List<SysRoleVo> getSysRoleList();

    List<SysMenuVo> getSysMenuListByRoleId(@RequestParam("roleId") Integer roleId);
}
