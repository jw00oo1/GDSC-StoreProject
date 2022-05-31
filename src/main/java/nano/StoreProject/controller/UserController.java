package nano.StoreProject.controller;

import lombok.RequiredArgsConstructor;
import nano.StoreProject.service.UserService;
import nano.StoreProject.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/logout")
    public String logout(HttpSession session) throws Exception{
        userService.logout(session);

        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signUpForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(UserVo userVo) {
        boolean isSignup = userService.joinUser(userVo);

        return "redirect:/"; //로그인 구현 예정
    }

    @GetMapping("/members")
    public String userList(Model model) {
        List<UserVo> userList = userService.getUsers();

        model.addAttribute("userList", userList);
        return "/members/memberList";
    }

    @PostMapping("/")
    public String mainLogin(UserVo userVo, HttpSession session) {
        String userName = userService.loginUser(userVo, session);

        return "redirect:/";
    }
}