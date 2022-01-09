package buza.group.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class BaseEntity implements Serializable {

    private String status;
    private String referer;
    private String option01;
    private String option02;
    private String option03;
    private String option04;
    private String option05;
    private String createtime;
    private String updatetime;

}
