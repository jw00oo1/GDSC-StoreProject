package nano.StoreProject.service;

import nano.StoreProject.repository.MemberRepository;
import nano.StoreProject.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    MemberRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
    Date time = new Date();
    String localTime = formatter.format(time);

//    @Transactional
    public boolean join(UserVo userVo) {
        Optional<UserVo> presentUserId = repository.findById(userVo.getUserId());
        Optional<UserVo> presentUserName = repository.findByName(userVo.getUserName());

        if (presentUserId.isPresent() || presentUserName.isPresent()) {
            return false;
        }

        userVo.setUserPw(passwordEncoder.encode(userVo.getUserPw()));
        userVo.setUserAuth("USER");
//        userVo.setAppendDate(localTime);
//        userVo.setUpdateDate(localTime);

        repository.save(userVo);
        return true;
    }

    public Optional<UserVo> login(String user_id, String user_pw) {
        Optional<UserVo> findUserVo = repository.findById(user_id);

        return findUserVo
                .map(userVo -> {
                    String findUserPw = userVo.getUserPw();
                    if (passwordEncoder.matches(user_pw, userVo.getUserPw())) {
                        Optional<UserVo> user = Optional.of(userVo);
                        return user;
                    } else {
                        Optional<UserVo> nullUser = Optional.empty();
                        return null;
                    }
                }).orElseGet(Optional::empty);

    }

    public boolean deleteUser(UserVo userVo) {
        repository.deleteById(userVo.getUserId());

        return repository.findById(userVo.getUserId()).isEmpty();
    }

    public List<UserVo> findMembers() {
        return repository.findAll();
    }
}
