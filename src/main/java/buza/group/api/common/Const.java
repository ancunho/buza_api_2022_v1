package buza.group.api.common;

/**
 * @author : Cunho
 * @date : 2020/3/22
 */
public class Const {

    public final static String CAPTCHA_KEY = "captcha";
    public final static String CURRENT_USER = "currentUser";
    public final static String EMAIL = "email";
    public final static String USERNAME = "username";
    public final static String CUSTOMER_NAME = "customerName";
    public final static String CUSTOMER_CD = "customerCd";
    public final static String RedirectLoginPage = "login";
    public final static String DEFAULT_PASSWORD = "123456";

    /**
     * 上传图片的最大大小： 500K
     * 1KB = 1024Byte
     */
    public final static Integer UPLOAD_IMAGE_MAX_SIZE = 20 * 1024;

    public interface RoleNo{
        String ROLE_USER        = "0";     //普通用户
        String ROLE_ADMIN       = "1";        //管理员
        String ROLE_EDITOR      = "2";      //组长？
    }


    public interface Role{
        String ROLE_USER        = "USER";           //普通用户
        String ROLE_ADMIN       = "ADMIN";          //管理员
        String ROLE_EDITOR      = "EDITOR";         //组长？
        String ROLE_NORMAL      = "NORMAL";         //NORMAL
    }

    public interface Status {
        String ACTIVE       = "1"; //已激活
        String NOT_ACTIVE   = "0"; //未激活
    }

    public interface Cart{
        int CHECKED         = 1;//即购物车选中状态
        int UN_CHECKED      = 0;//购物车中未选中状态

        String LIMIT_NUM_FAIL       = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS    = "LIMIT_NUM_SUCCESS";
    }

    public interface Gender {
        String male     = "1"; // 男
        String female   = "2"; // 女
    }

    public interface ProjectStatus {
        String KICKOFF              = "0";
        String AGREEMENT            = "1";
        String EXPORT_PREPARATION   = "2";
        String CONTRACT_ORDER       = "3";
        String PRODUCTION_EXPORT    = "4";
        String EXPORT_AS            = "5";
        String REJECT               = "99";
    }

    public interface Message {
        String SAVE_OK          = "数据保存成功";
        String SAVE_ERROR       = "数据保存失败";
        String UPDATE_OK        = "更新成功";
        String UPDATE_ERROR     = "更新失败";
        String SELECT_OK        = "查询成功";
        String SELECT_ERROR     = "查询失败";
        String PARAMETER_ERROR  = "参数错误";
        String USERNAME_OR_PASSWORD_NOT_FOUND = "用户名或密码不正确";
        String CAPTCHAR_KEY_ERROR = "验证码错误";
    }

    public interface FileType {
        String AVATAR           = "avatar"; //用户头像
        String PROJECT_FILE     = "pfile"; //项目模块中用的附件지
        String CUSTOMER_AVATAR  = "customerAvatar"; //고객사 아바자이미
    }

    public interface UserAction {
        String CREATE_PROJECT = "创建项目";
        String UPDATE_PROJECT = "修改项目内容";
        String NEW_USER = "加入会员";
    }

}
