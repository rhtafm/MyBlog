package wiki.jixing.myblog.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiki.jixing.myblog.entity.User;
import wiki.jixing.myblog.mapper.UserMapper;
import wiki.jixing.myblog.service.UserService;
import wiki.jixing.myblog.utils.EncryptUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.listAll();
    }

    @Override
    public Integer remove(Integer id) {
        return userMapper.deleteById(id);
    }

    @Override
    public Integer modify(User user) {
        user.setUpdateTime(LocalDateTime.now());
        String password = user.getPassword();
        if (password != null) {
            password = EncryptUtils.md5Encode(user.getPassword());
            user.setPassword(password);
        }
        return userMapper.update(user);
    }

    @Override
    public User login(User loginUser) {
        String username = loginUser.getUsername();
        String password = EncryptUtils.md5Encode(loginUser.getPassword());
        return userMapper.selectByUsernameAndPassword(username, password);
    }

    @Override
    public Integer register(User user) {
        String password = user.getPassword();
        user.setPassword(EncryptUtils.md5Encode(password));
        user.setType(0);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.insert(user);
    }

    @Override
    public User getByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public User getByEmail(String email) {
        return userMapper.selectByEmail(email);
    }
}
