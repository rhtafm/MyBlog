package wiki.jixing.myblog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import wiki.jixing.myblog.entity.User;
import wiki.jixing.myblog.service.UserService;
import wiki.jixing.myblog.utils.RandomCheckCode;
import wiki.jixing.myblog.utils.Result;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private JavaMailSender mailSender;

    @GetMapping
    public Result listAll(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<User> userList = userService.list();
        if (userList != null && userList.size() > 0) {
            PageInfo<User> pageInfo = new PageInfo<>(userList);
            return new Result(1, "success", pageInfo);
        }
        return new Result(0, "error", null);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id, HttpSession session) {
        Object obj = session.getAttribute("user");
        if (obj == null) {
            return new Result(0, "没有权限", null);
        }
        User user = (User) obj;
        String username = user.getUsername();
        if (!"admin".equals(username)) {
            return new Result(0, "没有权限", null);
        }
        Integer count = userService.remove(id);
        if (count == 1) {
            return new Result(1, "删除成功", null);
        }
        return new Result(0, "删除失败", null);
    }

    @PutMapping("/{id}")
    public Result modify(@PathVariable Integer id, Integer type, HttpSession session) {
        Object obj = session.getAttribute("user");
        if (obj == null) {
            return new Result(0, "没有权限", null);
        }
        User user = (User) obj;
        String username = user.getUsername();
        if (!"admin".equals(username)) {
            return new Result(0, "没有权限", null);
        }
        User user1 = new User();
        user1.setId(id);
        user1.setType(type);
        Integer count = userService.modify(user1);
        if (count == 1) {
            return new Result(1, "更新成功", null);
        }
        return new Result(0, "更新失败", null);
    }

    @PutMapping("/r")
    public Result modify(String email, String password, String checkCode, HttpSession session) {
        Object obj = session.getAttribute(email);
        if (obj == null) {
            return new Result(0, "验证码错误", null);
        }
        String code = (String) obj;
        if (!code.equals(checkCode)) {
            return new Result(0, "验证码错误", null);
        }
        User user = userService.getByEmail(email);
        user.setPassword(password);
        Integer count = userService.modify(user);
        if (count == 1) {
            return new Result(1, "修改成功", null);
        }
        return new Result(0, "修改失败", null);
    }

    @PostMapping("/login")
    public Result login(User user, HttpSession session) {
        User loginUser = userService.login(user);
        if (loginUser != null) {
            session.setAttribute("user", loginUser);
            return new Result(1, "登录成功", null);
        }
        return new Result(0, "用户名或密码错误", null);
    }

    @PostMapping("/register")
    public Result register(User user, String checkCode, HttpSession session) {
        Object obj = session.getAttribute(user.getEmail());
        if (obj == null) {
            return new Result(0, "验证码错误", null);
        }
        String code = (String) obj;
        if (!code.equals(checkCode)) {
            return new Result(0, "验证码错误", null);
        }
        Integer count = userService.register(user);
        if (count == 1) {
            return new Result(1, "注册成功", null);
        }
        return new Result(0, "注册失败", null);
    }

    @PostMapping("/logout")
    public Result logout(HttpSession session) {
        session.removeAttribute("user");
        if (session.getAttribute("user") == null) {
            return new Result(1, "成功注销", null);
        }
        return new Result(0, "退出失败", null);
    }

    @GetMapping("/username/{username}")
    public Result getUserByUsername(@PathVariable String username) {
        User user = userService.getByUsername(username);
        if (user == null) {
            return new Result(1, "用户名可用", null);
        }
        return new Result(0, "用户名已存在", null);
    }

    @GetMapping("/email/{email}")
    public Result getUserByEmail(@PathVariable String email) {
        User user = userService.getByEmail(email);
        if (user == null) {
            return new Result(1, "该邮箱可用", null);
        }
        return new Result(0, "该邮箱已注册", null);
    }

    @GetMapping("/current")
    public Result getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("username", user.getUsername());
            map.put("type", user.getType());
            map.put("avatar", user.getAvatar());
            return new Result(1, "成功获取当前登录用户", map);
        }
        return new Result(0, "获取失败", null);
    }

    @PutMapping("/checkcode/{email}")
    public Result updateCheckCode(HttpSession session, @PathVariable String email) {
        String checkCode = RandomCheckCode.getCheckCode();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("******");
        message.setTo(email);
        message.setSubject("From jixing'blog");
        message.setText("您的验证码为: " + checkCode + ", 有效时间为60秒");
        try {
            mailSender.send(message);
            session.setAttribute(email, checkCode);
            session.setMaxInactiveInterval(60);
            return new Result(1, "success", null);
        } catch (MailException e) {
            e.printStackTrace();
            return new Result(0, "error", null);
        }
    }
}
