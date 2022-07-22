package nano.StoreProject.service;

import lombok.RequiredArgsConstructor;
import nano.StoreProject.mapper.UserMapper;
import nano.StoreProject.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

//    SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:sss");
//    Date time = new Date();
//    String localTime = format.format(time);

    @Transactional
    public boolean joinUser(UserVo userVo){
        //// id, name 중복 체크
        UserVo findUser = userMapper.selectUserById(userVo.getUserId());
        if (findUser != null) {
            return false;
        }
//        findUser = userMapper.selectUserByName(userVo.getUserName());
//        if (findUser != null) {
//            return false;
//        }

        userVo.setUserPw(passwordEncoder.encode(userVo.getUserPw()));
        userVo.setUserAuth("USER");
//        userVo.setCreateDate(localTime);
//        userVo.setUpdateDate(localTime);
        userMapper.saveUser(userVo);

        return true;
    }

    public String loginUser(UserVo userVo, HttpSession session) {
        UserVo findUser = userMapper.selectUserById(userVo.getUserId());
        if (findUser != null) {
            String findUserPw = findUser.getUserPw();
            if (passwordEncoder.matches(userVo.getUserPw(), findUserPw)){
                session.setAttribute("userId", userVo.getUserId());
                session.setAttribute("userName", findUser.getUserName());

                return findUser.getUserName();
            }
        }

        session.setAttribute("userId", null);
        session.setAttribute("userName", null);

        return null;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    @Transactional
    public List<UserVo> getUsers() {
        return userMapper.getUsers();
    }
}
