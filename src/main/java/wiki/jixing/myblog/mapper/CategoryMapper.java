package wiki.jixing.myblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import wiki.jixing.myblog.entity.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    Integer count();

    Category selectById(Integer id);

    List<Category> selectLikeName(String name);

    List<Category> listAll();

    Integer insert(Category category);

    Integer deleteById(Integer id);

    Integer update(Category category);
}
