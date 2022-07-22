package nano.StoreProject.vo.chat;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ChatMessage {
    private Integer roomNo;
    private Integer messageNo;
    private String message;
    private String senderId;
    private Timestamp createDate;
}
