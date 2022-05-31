package nano.StoreProject.controller;

import lombok.AllArgsConstructor;
import nano.StoreProject.mapper.ItemListMapper;
import nano.StoreProject.service.ItemService;
import nano.StoreProject.vo.ItemVo;
import nano.StoreProject.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
public class ItemController {
    @Autowired
    ItemService itemService;

    @GetMapping("/")
    public String main(Model model) {
        List<ItemVo> itemList = itemService.getItems();

        model.addAttribute("itemList", itemList);

        return "index";
    }

    @GetMapping("items")
    public String itemDetail(Model model, @RequestParam("id") Integer id){
        ItemVo itemVo = itemService.getItemInfo(id);
        model.addAttribute("itemInfo", itemVo);

        return "single-product";
    }

    @GetMapping("/register-item")
    public String addItem() {
        return "additem";
    }

    @PostMapping("/register-item")
    @ResponseBody
    public String addItem(ItemVo itemVo, HttpSession session) {
        String alertmsg = "/";
        if (session.getId() == null) {
            System.out.println("need login");
            alertmsg = "<script>alert('로그인이 필요한 작업입니다');location.href='/'";
        } else {
            System.out.println("itemVo = " + itemVo + ", session = " + session);
            if (itemVo != null) {
                itemVo.setSellerId(session.getId());
                itemService.addItem(itemVo);
                alertmsg = "<script>alert('성공적으로 등록되었습니다');location.href='/'";
            } else {
                System.out.println("itemvo is null");
            }
        }
        return alertmsg;
    }
}
