package nano.StoreProject.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import nano.StoreProject.service.ChatService;
import nano.StoreProject.vo.chat.ChatMessage;
import nano.StoreProject.vo.chat.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler {

    @Autowired
    ChatService chatService;

    private Map<String, ArrayList<WebSocketSession>> RoomList = new ConcurrentHashMap<String, ArrayList<WebSocketSession>>();
    private Map<WebSocketSession, String> sessionList = new ConcurrentHashMap<WebSocketSession, String>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    // message
    // if the client send message using ws.send()
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        // sending time
        LocalDateTime currentTime = LocalDateTime.now();
        String time = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // message data
        String payload = message.getPayload();

        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        chatMessage.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        ChatRoom chatRoom = chatService.getChatRoom_pk(chatMessage.getRoomNo());
        String roomNo = Integer.toString(chatRoom.getRoomNo());

        // 채팅 세션 목록에 채팅방이 존재하지 않고, 처음 들어왔고, DB에서의 채팅방이 있을 때
        // 채팅방 생성
        if(RoomList.get(roomNo) == null && chatMessage.getMessage().equals("ENTER-CHAT") && chatRoom != null) {

            // 채팅방에 들어갈 session들
            ArrayList<WebSocketSession> sessionTwo = new ArrayList<>();
            // session 추가
            sessionTwo.add(session);
            // sessionList에 추가
            sessionList.put(session, roomNo);
            // RoomList에 추가
            RoomList.put(roomNo, sessionTwo);
            // 확인용
            System.out.println("채팅방 생성");
        }

        // 채팅방이 존재 할 때
        else if(RoomList.get(roomNo) != null && chatMessage.getMessage().equals("ENTER-CHAT") && chatRoom != null) {

            // RoomList에서 해당 방번호를 가진 방이 있는지 확인.
            RoomList.get(roomNo).add(session);
            // sessionList에 추가
            sessionList.put(session, roomNo);
            // 확인용
            System.out.println("생성된 채팅방으로 입장");
        }

        // 채팅 메세지 입력 시
        else if(RoomList.get(roomNo) != null && !chatMessage.getMessage().equals("ENTER-CHAT") && chatRoom != null) {

            // 메세지에 이름, 이메일, 내용을 담는다.
            TextMessage textMessage = new TextMessage(chatMessage.getSenderId() + "," + chatMessage.getMessage());

            // 해당 채팅방의 session에 뿌려준다.
            for(WebSocketSession sess : RoomList.get(roomNo)) {
                sess.sendMessage(textMessage);
            }

            chatService.createChatMessage(chatMessage);
        }

    }

    // connection established
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    // connection closed
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if(sessionList.get(session) != null) {
            // 해당 session의 방 번호를 가져와서, 방을 찾고, 그 방의 ArrayList<session>에서 해당 session을 지운다.
            RoomList.get(sessionList.get(session)).remove(session);
            sessionList.remove(session);
        }

        String senderId = (String) session.getAttributes().get("userId");
        Map<String, Object> data = new HashMap<>();
        data.put("message", senderId + "님이 퇴장하셨습니다.");
        TextMessage msg = new TextMessage(objectMapper.writeValueAsString(data));
        handleMessage(session, msg);

        log.info(session + "client disconnected");
    }
}
