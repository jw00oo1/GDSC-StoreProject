package nano.StoreProject.repository;

import nano.StoreProject.vo.UserVo;
import java.util.List;
import java.util.Optional;


public interface MemberRepository {
    void save(UserVo userVo);
    Optional<UserVo> findById(String user_id);
    Optional<UserVo> findByName(String user_name);
    void deleteById(String user_id);
    void updateById(UserVo userVo);
    List<UserVo> findAll();
}
