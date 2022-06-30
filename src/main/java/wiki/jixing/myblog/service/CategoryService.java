package wiki.jixing.myblog.service;

import wiki.jixing.myblog.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    Integer count();

    Category getById(Integer id);

    List<Category> getLikeName(String name);

    List<Category> getList();

    List<Map<String, String>> getCategoryAndArticleCountList();

    Integer add(Category category);

    Integer removeById(Integer id);

    Integer modify(Category category);
}
