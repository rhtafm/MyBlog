package wiki.jixing.myblog.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiki.jixing.myblog.entity.Article;
import wiki.jixing.myblog.entity.Category;
import wiki.jixing.myblog.mapper.ArticleMapper;
import wiki.jixing.myblog.mapper.CategoryMapper;
import wiki.jixing.myblog.service.CategoryService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Integer count() {
        return categoryMapper.count();
    }

    @Override
    public Category getById(Integer id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public List<Category> getLikeName(String name) {
        return categoryMapper.selectLikeName("%" + name.trim() + "%");
    }

    @Override
    public List<Category> getList() {
        return categoryMapper.listAll();
    }

    @Override
    public List<Map<String, String>> getCategoryAndArticleCountList() {
        List<Map<String, String>> categories = new ArrayList<>();
        List<Category> categoryList = categoryMapper.listAll();
        if (categoryList != null && categoryList.size() > 0) {
            for (Category category : categoryList) {
                if (category != null && category.getId() != null) {
                    Map<String, String> categoryMap = new HashMap<>();
                    categoryMap.put("name", category.getName());
                    categoryMap.put("value", String.valueOf(category.getArticleCount()));
                    categories.add(categoryMap);
                }
            }
        }
        return categories;
    }

    @Override
    public Integer add(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        return categoryMapper.insert(category);
    }

    @Override
    public Integer removeById(Integer id) {
        Integer count = articleMapper.countByCategoryId(id);
        if (count != 0) {
            return 0;
        }
        return categoryMapper.deleteById(id);
    }

    @Override
    public Integer modify(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        return categoryMapper.update(category);
    }
}
