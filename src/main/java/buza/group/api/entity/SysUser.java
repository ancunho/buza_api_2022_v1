package buza.group.api.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 회원정보테이블
 * </p>
 *
 * @author Cunho
 * @since 2021-05-26
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Integer userSeq;
    private String openid;
    private String unionid;
    private String username;
    private String password;
    private String roleNo;
    private String role;
    private String userType;
    private String realname;
    private String company;
    private String companyType;
    private String mobileNo;
    private String email;
    private String sex;
    private String birthday;
    private String wechat;
    private String provinceCode;
    private String cityCode;
    private String districtCode;
    private String address;
    private String question;
    private String answer;
    private String imagePhoto;
    private Integer useYn;

    private String roles;
    private String permissions;

    public SysUser(String username, String password, String roles, String permissions) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.permissions = permissions;
        this.useYn = 1;
    }

    public List<String> getRolesList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList() {
        if (this.permissions.length() > 0) {
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }


}
