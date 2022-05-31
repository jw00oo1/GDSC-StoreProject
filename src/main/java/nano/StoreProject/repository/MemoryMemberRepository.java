package nano.StoreProject.repository;

import nano.StoreProject.vo.UserVo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static int memberNumber = 1;
    private static Map<Integer, UserVo> repository = new HashMap<>();

    @Override
    public void save(UserVo userVo) {
        userVo.setUserNo(memberNumber);
        repository.put(memberNumber, userVo);
        memberNumber++;
    }

    @Override
    public Optional<UserVo> findById(String user_id) {
        return repository.values().stream()
                .filter(userVo -> userVo.getUserId().equals(user_id))
                .findAny();
    }

    @Override
    public Optional<UserVo> findByName(String user_name) {
        return repository.values().stream()
                .filter(userVo -> userVo.getUserName().equals(user_name))
                .findAny();
    }

    @Override
    public void deleteById(String user_id) {
        Optional<UserVo> targetUser = findById(user_id);
        targetUser.ifPresent(userVo -> repository.remove(userVo.getUserNo()));
    }

    @Override
    public void updateById(UserVo userVo) {
        repository.put(userVo.getUserNo(), userVo);
    }

    @Override
    public List<UserVo> findAll() {
        return new ArrayList<UserVo>(repository.values());
    }
}
