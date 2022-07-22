package nano.StoreProject.controller;

import lombok.AllArgsConstructor;
import nano.StoreProject.mapper.ItemListMapper;
import nano.StoreProject.service.ChatService;
import nano.StoreProject.service.ItemService;
import nano.StoreProject.vo.ItemVo;
import nano.StoreProject.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
public class ItemController {
    @Autowired
    ItemService itemService;
    @Autowired
    ChatService chatService;

    @GetMapping("/")
    public String main(Model model) {
        List<ItemVo> itemList = itemService.getItems();

        model.addAttribute("itemList", itemList);

        return "index";
    }

    @GetMapping("items")
    public String itemDetail(Model model, @RequestParam("id") Integer itemNo){

        ItemVo itemVo = itemService.getItemInfo(itemNo);
        if (itemVo == null) {
            return "redirect:/";
        }
        model.addAttribute("itemInfo", itemVo);

        return "single-product";
    }

    @GetMapping("register-item")
    public String addItem() {
        return "additem";
    }

    @PostMapping("register-item")
    @ResponseBody
    public String addItem(ItemVo itemVo, HttpSession session) {
        String alertmsg = "/";
        if (session.getAttribute("userId") == null) {
            alertmsg = "<script>alert('로그인이 필요한 작업입니다');location.href='/'";
        } else {
            System.out.println("itemVo = " + itemVo + ", session = " + (String)session.getAttribute("userId"));
            if (itemVo != null) {
                itemVo.setSellerId((String)session.getAttribute("userId"));
                itemService.addItem(itemVo);
                alertmsg = "<script>alert('성공적으로 등록되었습니다');location.href='/'";
            } else {
                System.out.println("item is null");
            }
        }
        return alertmsg;
    }

//    @GetMapping("deleteitem")
//    public String deleteItem() {
//        return "redirect:items";
//    }

    @PostMapping("deleteitem")
    public String deleteItem(Integer itemNo) {
//        String alertmsg;

        chatService.removeItemAllChat(itemNo);
        itemService.deleteItem(itemNo);

        ItemVo itemVo = itemService.getItemInfo(itemNo);

//        if (itemVo != null) {
//            alertmsg = "<script>alert('물건 삭제에 실패하였습니다 :(');location.href='/'";
//        } else {
//            alertmsg = "<script>alert('물건이 성공적으로 삭제되었습니다 :D');location.href='/'";
//        }

        return "redirect:/";
    }
}
