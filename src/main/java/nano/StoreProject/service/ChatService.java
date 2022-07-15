package nano.StoreProject.service;

import nano.StoreProject.mapper.ChatMapper;
import nano.StoreProject.vo.chat.ChatMessage;
import nano.StoreProject.vo.chat.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ChatService {
    @Autowired
    ChatMapper chatMapper;

    public void createChatRoom(ChatRoom chatRoom) {
        chatMapper.createChatRoom(chatRoom);
    }

    // get a specific chat room from chatroom list
    public Optional<ChatRoom> getChatRoom_id(String userId, Integer itemId) {
        List<ChatRoom> chatRoomList = getChatRoomList(userId);
        return chatRoomList.stream()
                .filter(room -> room.getItemNo().equals(Integer.toString(itemId))).findAny();
    }

    public ChatRoom getChatRoom_pk(String roomNo) { return chatMapper.getChatRoom(roomNo); }

    // one's own chatroom list
    public List<ChatRoom> getChatRoomList(String userId) {
        return chatMapper.chatRoomList(userId);
    }

    public List<ChatMessage> getChatMessage(String roomNo) {
        return chatMapper.messageList(roomNo);
    }

    public void createChatMessage(ChatMessage chatMessage) {
        chatMapper.createChatMessage(chatMessage);
    }
}
