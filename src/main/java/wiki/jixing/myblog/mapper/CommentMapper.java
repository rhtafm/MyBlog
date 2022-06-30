package wiki.jixing.myblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import wiki.jixing.myblog.entity.Comment;

import java.util.List;

@Mapper
public interface CommentMapper {
    Integer count();

    List<Comment> listAll();

    Comment selectById(Integer id);

    Integer insert(Comment comment);

    Integer deleteById(Integer id);
}
