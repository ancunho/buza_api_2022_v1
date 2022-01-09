package buza.group.api.service.impl;

import buza.group.api.entity.SysMenu;
import buza.group.api.entity.SysRole;
import buza.group.api.entity.SysUser;
import buza.group.api.service.SysUserService;
import buza.group.api.util.RedisUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private SqlSession sqlSession;

    @Override
    public SysUser selectSysUserByUsername(String username) {
        return sqlSession.selectOne("Buza.User.selectSysUserByUsername", username);
    }

    @Override
    public String getUserAuthority(Integer userSeq) {
        SysUser sysUser = sqlSession.selectOne("Buza.User.selectSysUserByUserSeq", userSeq);

        String authority = "";

        if (redisUtil.hasKey("GrantedAuthority:" + sysUser.getUsername())) {
            authority = (String) redisUtil.get("GrantedAuthority:" + sysUser.getUsername());
        } else {
            // 1.获取角色
            List<SysRole> sysRoleList = sqlSession.selectList("Buza.User.getSysRoleListByUserSeq", userSeq);
            if (sysRoleList.size() > 0) {
                String roleCodes = sysRoleList.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
                authority = roleCodes.concat(",");
            }

            // 2. 获取菜单操作权限
            List<SysMenu> sysMenuList = sqlSession.selectList("Buza.User.getMenuListByUserSeq" ,userSeq);
            if (sysMenuList.size() > 0) {
                String menus = sysMenuList.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));
                authority = authority.concat(menus);
            }

            redisUtil.set("GrantedAuthority:" + sysUser.getUsername(), authority, 60 * 60);
        }

        return authority;
    }

    @Override
    public void clearUserAuthorityInfo(String username) {
        redisUtil.del("GrantedAuthority:" + username);
    }

    @Override
    public void clearUserAuthorityInfoByRoleId(Integer roleId) {
        List<SysUser> sysUserList = sqlSession.selectList("Buza.User.getSysUserListByRoleId", roleId);

        sysUserList.forEach(sysUser -> {
            this.clearUserAuthorityInfo(sysUser.getUsername());
        });
    }

    @Override
    public void clearUserAuthorityInfoByMenuId(Integer menuId) {
        List<SysUser> sysUserList = sqlSession.selectList("Buza.User.getSysUserListByMenuId", menuId);

        sysUserList.forEach(sysUser -> {
            this.clearUserAuthorityInfo(sysUser.getUsername());
        });
    }
}
