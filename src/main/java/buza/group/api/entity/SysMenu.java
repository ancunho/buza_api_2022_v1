package buza.group.api.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Cunho
 * @since 2021-05-26
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer parentId;
    private String name;
    private String path;
    private String perms;
    private String component;
    private Integer type;
    private String icon;
    private Integer orderNum;
    private List<SysMenu> children = new ArrayList<>();


}
