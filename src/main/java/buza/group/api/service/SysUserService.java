package buza.group.api.service;

import buza.group.api.entity.SysUser;

/**
 * <p>
 * 회원정보테이블 服务类
 * </p>
 *
 * @author Cunho
 * @since 2021-05-26
 */
public interface SysUserService {

    SysUser selectSysUserByUsername(String username);

    String getUserAuthority(Integer userSeq);

    void clearUserAuthorityInfo(String username);

    void clearUserAuthorityInfoByRoleId(Integer roleId);

    void clearUserAuthorityInfoByMenuId(Integer menuId);
}
