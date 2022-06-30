package wiki.jixing.myblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import wiki.jixing.myblog.entity.Tag;

import java.util.List;

@Mapper
public interface TagMapper {
    Integer count();

    Tag selectById(Integer id);

    List<Tag> selectLikeName(String name);

    List<Tag> listAll();

    List<Integer> selectArticleAndTagsTagIdsByArticleId(Integer articleId);

    List<Tag> listByIds(List<Integer> tagIds);

    Integer insert(Tag tag);

    Integer deleteById(Integer id);

    Integer update(Tag tag);
}
