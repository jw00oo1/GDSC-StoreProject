package nano.StoreProject.service;

import nano.StoreProject.repository.MemberRepository;
import nano.StoreProject.repository.MemoryMemberRepository;
import nano.StoreProject.vo.UserVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;


    @Test
    public void join() {
        UserVo userVo = new UserVo();
        userVo.setUserId("nano_test");
        userVo.setUserPw("qweqwe1");
        userVo.setUserName("Nano");

        memberService.join(userVo);

        Optional<UserVo> findUser = memberRepository.findById("nano_test");
        System.out.println(findUser.get());
    }

    @Test
    public void login() {
        String user_id = "nano", user_pw = "qweqwe1";

        UserVo userVo = new UserVo();
        userVo.setUserId(user_id);
        userVo.setUserPw(user_pw);
        userVo.setUserName("Nano");

        memberService.join(userVo);

//        Optional<UserVo> findUser = memberRepository.findById(user_id);
//        System.out.println(memberService.passwordEncoder.matches(user_pw, findUser.get().getUserPw()));
//        System.out.println(findUser.get());

        Optional<UserVo> presentUserVo = memberService.login(user_id, user_pw);
        presentUserVo
                .ifPresentOrElse(presentUser -> System.out.println(presentUser.getUserId() + presentUser.getUserPw() + presentUser.getUserName()),
                        () -> System.out.println("null"));
    }
}
