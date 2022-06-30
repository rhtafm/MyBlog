package wiki.jixing.myblog.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiki.jixing.myblog.entity.Comment;
import wiki.jixing.myblog.entity.User;
import wiki.jixing.myblog.mapper.CommentMapper;
import wiki.jixing.myblog.mapper.UserMapper;
import wiki.jixing.myblog.service.CommentService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public Integer count() {
        return commentMapper.count();
    }

    @Override
    public Comment getById(Integer id) {
        Comment comment = commentMapper.selectById(id);
        if (comment.getUser() == null) {
            User user = new User();
            user.setUsername("用户已注销");
            user.setEmail("******");
            comment.setUser(user);
        }
        return comment;
    }

    @Override
    public List<Comment> getList() {
        List<Comment> comments = commentMapper.listAll();
        List<Comment> commentList = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getUser() == null) {
                User user = new User();
                user.setUsername("[用户已注销]");
                user.setEmail("******");
                comment.setUser(user);
            }
            commentList.add(comment);
        }
        return commentList;
    }

    @Override
    public Integer add(Comment comment) {
        comment.setCreateTime(LocalDateTime.now());
        return commentMapper.insert(comment);
    }

    @Override
    public Integer remove(Integer id) {
        return commentMapper.deleteById(id);
    }
}
