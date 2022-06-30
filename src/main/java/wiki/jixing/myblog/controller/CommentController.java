package wiki.jixing.myblog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import wiki.jixing.myblog.entity.Comment;
import wiki.jixing.myblog.entity.User;
import wiki.jixing.myblog.service.CommentService;
import wiki.jixing.myblog.service.UserService;
import wiki.jixing.myblog.utils.Permission;
import wiki.jixing.myblog.utils.Result;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;
    @Resource
    private Permission permission;

    @GetMapping("/pg")
    public Result listAll(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Comment> commentList = commentService.getList();
        if (commentList != null && commentList.size() > 0) {
            PageInfo<Comment> pageInfo = new PageInfo<>(commentList);
            return new Result(1, "success", pageInfo);
        }
        return new Result(0, "error", null);
    }

    @GetMapping
    public Result listAll() {
        List<Comment> commentList = commentService.getList();
        if (commentList != null && commentList.size() > 0) {
            List<Comment> comments = new ArrayList<>();
            for (Comment comment : commentList) {
                User user = comment.getUser();
                if (user.getId() != null) {
                    String email = user.getEmail();
                    int d = email.indexOf("@");
                    StringBuilder emailBuilder = new StringBuilder();
                    if (d <= 3) {
                        emailBuilder = new StringBuilder(email.substring(0, 1));
                        emailBuilder.append("*".repeat(d - 1)).append(email.substring(d));
                    } else {
                        emailBuilder = new StringBuilder(email.substring(0, 3));
                        emailBuilder.append("*".repeat(d - 3)).append(email.substring(d));
                    }
                    email = emailBuilder.toString();
                    user.setEmail(email);
                    comment.setUser(user);
                }
                comments.add(comment);
            }
            return new Result(1, "success", comments);
        }
        return new Result(0, "error", null);
    }

    @PostMapping
    public Result add(Comment comment, HttpSession session) {
        Object obj = session.getAttribute("user");
        if (obj == null) {
            return new Result(0, "error", null);
        }
        User user = (User) obj;
        comment.setUser(user);
        Integer count = commentService.add(comment);
        if (count == 1) {
            return new Result(1, "success", null);
        }
        return new Result(0, "error", null);
    }

    @GetMapping("/{id}")
    public Result query(@PathVariable Integer id) {
        Comment comment = commentService.getById(id);
        if (comment != null) {
            return new Result(1, "success", comment);
        }
        return new Result(0, "error", null);
    }

    @GetMapping("/count")
    public Result count() {
        Integer count = commentService.count();
        if (count != null) {
            return new Result(1, "success", count);
        }
        return new Result(0, "error", null);
    }

    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Integer id) {
        if (!permission.allow()) {
            return new Result(0, "没有权限", null);
        }
        Integer count = commentService.remove(id);
        if (count == 1) {
            return new Result(1, "删除成功", null);
        }
        return new Result(0, "删除失败", null);
    }
}
