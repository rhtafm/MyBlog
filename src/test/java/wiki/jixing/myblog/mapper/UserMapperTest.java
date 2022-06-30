package wiki.jixing.myblog.mapper;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wiki.jixing.myblog.entity.User;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@SpringBootTest
class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void count() {
        Integer count = userMapper.count();
        System.out.println(count);
    }

    @Test
    void selectById() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

    @Test
    void selectByUsername() {
        User user = userMapper.selectByUsername("admin");
        System.out.println(user);
    }

    @Test
    void selectByUsernameAndPassword() {
        User user = userMapper.selectByUsernameAndPassword("admin", "admin");
        System.out.println(user);
    }

    @Test
    void selectByEmail() {
    }

    @Test
    void listAll() {
        for (User user : userMapper.listAll()) {
            System.out.println(user);
        }

    }

    @Test
    void insert() {
        User user = new User();
        user.setUsername("admin2");
        user.setEmail("jixing2@gmail.com");
        user.setPassword("admin2");
        user.setType(1);
        user.setAvatar("https://pic.imgdb.cn/item/6200c3c52ab3f51d91baca08.jpg");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        Integer count = userMapper.insert(user);
        System.out.println(count);
        System.out.println(user);
    }

    @Test
    void deleteById() {
        Integer count = userMapper.deleteById(4);
        System.out.println(count);
    }

    @Test
    void update() {
        User user = new User();
        user.setId(1);
        user.setAvatar("https://pic.imgdb.cn/item/6200c3c52ab3f51d91baca08.jpg");
        user.setUpdateTime(LocalDateTime.now());
        Integer count = userMapper.update(user);
        System.out.println(count);
        System.out.println(user);
    }
}