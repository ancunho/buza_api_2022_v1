package buza.group.api.service.impl;

import buza.group.api.common.ServerResponse;
import buza.group.api.dao.RoleMenuMapper;
import buza.group.api.entity.SysMenu;
import buza.group.api.entity.SysRole;
import buza.group.api.entity.SysUser;
import buza.group.api.model.SysMenuVo;
import buza.group.api.model.SysRoleVo;
import buza.group.api.model.UserInfoVo;
import buza.group.api.service.RoleMenuService;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    private SqlSession sqlSession;

    public SysUser selectSysUserByUsername(String username) {
        return sqlSession.selectOne("Buza.User.selectSysUserByUsername", username);
    }

    public List<UserInfoVo> selectSysUserByUserSeq(Integer userSeq) {
        List<UserInfoVo> lstSysUser = roleMenuMapper.selectSysUserByUserSeq(userSeq);
        return lstSysUser;
    }

    public List<SysMenuVo> getMenuListByUserSeq(Integer userSeq) {
        List<SysMenu> lstSysMenu = sqlSession.selectList("Buza.User.getMenuListByUserSeq", userSeq);
        List<SysMenu> menuTree = buildTreeMenu(lstSysMenu);
        return convertMenuDto(menuTree);
    }

    public List<SysRoleVo> getSysRoleList() {
        return sqlSession.selectList("Buza.User.getSysRoleList");
    }

    public List<SysMenuVo> getAllSysMenuList() {
        return sqlSession.selectList("Buza.User.getAllSysMenuList");
    }

    public boolean checkMenuAndPerms(SysMenuVo sysMenuVo) {
        int checkCount = sqlSession.selectOne("Buza.User.checkMenuAndPerms", sysMenuVo);
        if (checkCount > 0) {
            return false;
        }
        return true;
    }

    public boolean checkMenuAndPermsExceptSelf(SysMenuVo sysMenuVo) {
        int checkCount = sqlSession.selectOne("Buza.User.checkMenuAndPermsExceptSelf", sysMenuVo);
        if (checkCount > 0) {
            return false;
        }
        return true;
    }

    public int insertNewMenu(SysMenuVo sysMenuVo) {
        if (sysMenuVo.getParentId() == null) {
            sysMenuVo.setParentId(0);
        }
        return sqlSession.insert("Buza.User.insertNewMenu", sysMenuVo);
    }

    public int modifyMenu(SysMenuVo sysMenuVo) {
        if (sysMenuVo.getParentId() == null) {
            sysMenuVo.setParentId(0);
        }

        return sqlSession.update("Buza.User.updateSysMenu", sysMenuVo);
    }

    public List<SysMenuVo> getSysMenuListByRoleId(Integer roleId) {
        return null;
    }

    private List<SysMenuVo> convertMenuDto(List<SysMenu> menuTree) {
        List<SysMenuVo> menuDtos = new ArrayList<>();

        menuTree.forEach(m -> {
            SysMenuVo dto = new SysMenuVo();

            dto.setId(m.getId());
            dto.setName(m.getPerms());
            dto.setTitle(m.getName());
            dto.setComponent(m.getComponent());
            dto.setPath(m.getPath());
            dto.setIcon(m.getIcon());
            dto.setParentId(m.getParentId());
            dto.setOrderNum(String.valueOf(m.getOrderNum()));

            if (m.getChildren().size() > 0) {
                // 子节点调用当前方法进行再次转换
                dto.setChildren(convertMenuDto(m.getChildren()));
            }

            menuDtos.add(dto);
        });

        return menuDtos;
    }

    private List<SysMenu> buildTreeMenu(List<SysMenu> menus) {
        List<SysMenu> finalMenus = new ArrayList<>();

        // 先各自寻找到各自的孩子
        for (SysMenu menu : menus) {
            for (SysMenu e : menus) {
                if (menu.getId() == e.getParentId()) {
                    menu.getChildren().add(e);
                }
            }

            // 提取出父节点
            if (menu.getParentId() == 0L) {
                finalMenus.add(menu);
            }
        }

        log.info("finalMenus: {}", JSONUtil.toJsonStr(finalMenus));
        return finalMenus;
    }

}
