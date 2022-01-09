package buza.group.api.dao;

import buza.group.api.entity.SysUser;
import buza.group.api.model.UserInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMenuMapper {

    List<UserInfoVo> selectSysUserByUserSeq(Integer userSeq);

}
