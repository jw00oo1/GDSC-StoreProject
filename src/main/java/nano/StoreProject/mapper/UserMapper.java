package nano.StoreProject.mapper;

import nano.StoreProject.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    void saveUser(UserVo userVo);
    List<UserVo> getUsers();
    UserVo selectUserById(String userId);
    UserVo selectUserByName(String userName);
}
