package nano.StoreProject.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ItemVo {
    private Integer itemNo;
    private String sellerId;
    private String itemName;
    private String tradeName;
    private Timestamp createDate;
    private String content;
}
