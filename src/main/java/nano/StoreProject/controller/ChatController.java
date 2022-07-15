package nano.StoreProject.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import nano.StoreProject.service.ChatService;
import nano.StoreProject.service.ItemService;
import nano.StoreProject.vo.ItemVo;
import nano.StoreProject.vo.chat.ChatMessage;
import nano.StoreProject.vo.chat.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class ChatController {
    @Autowired
    ChatService chatService;
    @Autowired
    ItemService itemService;

    // 채팅방 생성
//    @ResponseBody
    @GetMapping("createchat")
    public String createChatRoom(HttpServletRequest request, @RequestParam("id") Integer itemId) {
        HttpSession session = request.getSession();

        if (session.getAttribute("userId") == null) {
            String alertmsg = "<script>alert('로그인이 필요한 작업입니다');location.href='/'";
            request.setAttribute("msg", alertmsg);
            request.setAttribute("url", request.getHeader("Referer"));

            return "alert";
        }

        String userId = (String) session.getAttribute("userId");

        if (chatService.getChatRoom_id(userId, itemId).isEmpty()) {
            ItemVo itemVo = itemService.getItemInfo(itemId);
            String sellerId = itemVo.getSellerId();

            if (!sellerId.equals(userId)) {
                ChatRoom chatRoom = new ChatRoom();
                chatRoom.setItemNo(Integer.toString(itemId));
                chatRoom.setSellerId(sellerId);
                chatRoom.setBuyerId(userId);

                System.out.println(chatRoom);

            }
        }

        return "redirect:/chat";
    }

    // 채팅방 메세지 불러오기
    @RequestMapping(value="{roomNo}")
    public void getChatMessageList(@PathVariable String roomNo, HttpServletResponse response)throws JsonIOException, IOException {

        List<ChatMessage> chatMessageList = chatService.getChatMessage(roomNo);
        response.setContentType("application/json; charset=utf-8");

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        gson.toJson(chatMessageList, response.getWriter());
    }

    // 채팅방 목록 불러오기
    @RequestMapping("chatlist")
    public void createChatRoom(HttpServletResponse response, HttpServletRequest request) throws JsonIOException, IOException {
        String userId = (String) request.getSession().getAttribute("userId");

        List<ChatRoom> chatRoomList = chatService.getChatRoomList(userId);

        response.setContentType("application/json; charset=utf-8");

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        gson.toJson(chatRoomList, response.getWriter());
    }

    @GetMapping("chat")
    public String mainChat(){
        return "chat_bs";
    }
}
