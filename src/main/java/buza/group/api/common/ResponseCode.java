package buza.group.api.common;

/**
 * @author : Cunho
 * @date : 2020/3/22
 */
public enum ResponseCode {

    SUCCESS(200,"SUCCESS"),
    ERROR(400,"ERROR"),
    UNAUTHORIZED(401,"无权限"),
    ILLEGAL_ARGUMENT(402,"参数错误"),
    TOKEN_EXPIRED(403,"token错误");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }

}
