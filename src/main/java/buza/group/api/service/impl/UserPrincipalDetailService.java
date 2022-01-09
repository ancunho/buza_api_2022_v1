package buza.group.api.service.impl;

import buza.group.api.common.Const;
import buza.group.api.entity.SysMenu;
import buza.group.api.entity.SysRole;
import buza.group.api.entity.SysUser;
import buza.group.api.model.UserPrincipal;
import buza.group.api.service.SysUserService;
import buza.group.api.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserPrincipalDetailService implements UserDetailsService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SqlSession sqlSession;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Get SysUser By username
        SysUser sysUser = sqlSession.selectOne("Buza.User.selectSysUserByUsername", username);
        // 2. If null , Then Exception
        if (sysUser == null) {
            throw new UsernameNotFoundException(Const.Message.USERNAME_OR_PASSWORD_NOT_FOUND);
        }

        // 3.1 If In Redis, then return itself
        if (redisUtil.hasKey("GrantedRoles:" + sysUser.getUsername())) {
            sysUser.setRoles((String) redisUtil.get("GrantedRoles:" + sysUser.getUsername()));
        } else {
            List<SysRole> sysRoleList = sqlSession.selectList("Buza.User.getSysRoleListByUserSeq", sysUser.getUserSeq());
            if (sysRoleList.size() > 0) {
                String roles = sysRoleList.stream().map(role -> "ROLE_" + role.getCode()).collect(Collectors.joining(","));
                sysUser.setRoles(roles);
                redisUtil.set("GrantedRoles:" + sysUser.getUsername(), roles, 60 * 60);
            }
        }

        if (redisUtil.hasKey("GrantedPermissions:" + sysUser.getUsername())) {
            sysUser.setPermissions((String) redisUtil.get("GrantedPermissions:" + sysUser.getUsername()));
        } else {
            // 3.3 메뉴리스트가져오기 및 처리
            List<SysMenu> sysMenuList = sqlSession.selectList("Buza.User.getMenuListByUserSeq" ,sysUser.getUserSeq());
            if (sysMenuList.size() > 0) {
                String permissions = sysMenuList.stream().map(menu -> menu.getPerms()).collect(Collectors.joining(","));
                sysUser.setPermissions(permissions);
                redisUtil.set("GrantedPermissions:" + sysUser.getUsername(), permissions, 60 * 60);
            }
        }

        UserPrincipal userPrincipal = new UserPrincipal(sysUser);

        return userPrincipal;
    }
}
