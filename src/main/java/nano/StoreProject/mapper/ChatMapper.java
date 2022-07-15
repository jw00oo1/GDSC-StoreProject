package nano.StoreProject.mapper;

import nano.StoreProject.vo.chat.ChatMessage;
import nano.StoreProject.vo.chat.ChatRoom;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ChatMapper {
    // create new chat room if there is no other chat before
    void createChatRoom(ChatRoom chatroom);

    //search chatroom with seller name, buyer name, item no
    List<ChatRoom> chatRoomList(String chatroom);

    // list all chat messages in the chat room
    List<ChatMessage> messageList(String roomNo);

    void createChatMessage(ChatMessage chatMessage);

    ChatRoom getChatRoom(String roomNo);
}
