package nano.StoreProject.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserVo {
    private Integer userNo;
    private String userId;
    private String userPw;
    private String userName;
    private String userAuth;
    private Timestamp createDate;
    private Timestamp updateDate;
}
