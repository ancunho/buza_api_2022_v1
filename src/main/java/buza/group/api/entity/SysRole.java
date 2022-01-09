package buza.group.api.entity;

import lombok.*;

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
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String code;
    private String remark;


}
