package buza.group.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class SysRoleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer roleId;
    private String roleName;
    private String roleCode;
    private String roleRemark;
    private String roleStatus;
    private String roleStatusName;
    private String roleCreatetime;
    private String roleUpdatetime;

    private List<SysMenuVo> sysMenuVoList;

}
