package buza.group.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserInfoVo extends BaseVo {

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


}
