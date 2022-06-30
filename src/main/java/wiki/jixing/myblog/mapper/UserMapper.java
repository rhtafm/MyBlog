package wiki.jixing.myblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import wiki.jixing.myblog.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

    Integer count();

    User selectById(Integer id);

    User selectByUsername(String username);

    User selectByUsernameAndPassword(String username, String password);

    User selectByEmail(String email);

    List<User> listAll();

    Integer insert(User user);

    Integer deleteById(Integer id);

    Integer update(User user);
}
