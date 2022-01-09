package buza.group.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@ApiModel(description = "菜单对象")
public class SysMenuVo implements Serializable {

    @ApiModelProperty(value = "菜单ID")
    private Integer id;

    @ApiModelProperty(value = "父菜单ID")
    private Integer parentId;

    @ApiModelProperty(value = "菜单名")
    private String name;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "权限code")
    private String perms;

    @ApiModelProperty(value = "component")
    private String component;

    @ApiModelProperty(value = "类型", example = "0:目录， 1: 菜单， 2: 按钮")
    private String type;

    @ApiModelProperty(value = "类型", example = "0:目录， 1: 菜单， 2: 按钮")
    private String typeName;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "title")
    private String title;

    @ApiModelProperty(value = "顺序")
    private String orderNum;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "状态名")
    private String statusName;

    @ApiModelProperty(value = "生成日期")
    private String createtime;

    @ApiModelProperty(value = "更新日期")
    private String updatetime;

    @ApiModelProperty(value = "子菜单List")
    List<SysMenuVo> children = new ArrayList<>();

}
