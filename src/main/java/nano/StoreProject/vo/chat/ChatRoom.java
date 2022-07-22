package nano.StoreProject.vo.chat;

import lombok.Data;

@Data
public class ChatRoom {
    private Integer roomNo;

    private String sellerId;
    private String buyerId;
    private Integer itemNo;
}
