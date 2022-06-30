package wiki.jixing.myblog.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiki.jixing.myblog.entity.Article;
import wiki.jixing.myblog.entity.Category;
import wiki.jixing.myblog.mapper.ArticleMapper;
import wiki.jixing.myblog.service.ArticleService;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Transactional(rollbackFor = Exception.class)
@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Integer countByStatus(Integer status) {
        return articleMapper.countByStatus(status);
    }

    @Override
    public List<Article> getListByCategoryId(Integer id) {
        return articleMapper.listByCategoryId(id);
    }

    @Override
    public List<Article> getListByCategoryIdAndStatus(Integer id, Integer status) {
        return articleMapper.listByCategoryIdAndStatus(id, status);
    }

    @Override
    public List<Article> getListByTagId(Integer id) {
        List<Integer> ids = articleMapper.listArticleIdByTagId(id);
        if (ids != null && ids.size() > 0) {
            return articleMapper.listByIds(ids);
        }
        return null;
    }

    @Override
    public List<Article> getList() {
        return articleMapper.listAll();
    }

    @Override
    public List<Article> getList(Integer status) {
        return articleMapper.listByStatus(status);
    }

    @Override
    public Article getById(Integer id) {
        return articleMapper.selectById(id);
    }

    @Override
    public List<Article> getListOnCondition(String title, Integer categoryId) {
        if (title == null) {
            title = "";
        }
        return articleMapper.selectListOnCondition("%" + title + "%", categoryId);
    }

    @Override
    public List<Article> getListOnCondition(String text) {
        if (text != null && !"".equals(text)) {
            return articleMapper.selectListByText("%" + text + "%");
        }
        return null;
    }

    @Override
    public Integer add(Article article, Integer categoryId, String tagIds) {
        Category category = new Category();
        category.setId(categoryId);
        article.setCategory(category);
        article.setViews(0);
        article.setCreateTime(LocalDate.now());
        article.setUpdateTime(LocalDate.now());
        if (article.getSummary() == null || "".equals(article.getSummary())) {
            String content = article.getContent();
            String summary = content.substring(0, Math.min(200, content.length()));
            article.setSummary(summary);
        }
        if (article.getCoverImg() == null || "".equals(article.getCoverImg())) {
            String coverImgPath = "/static/img/cover/";
            Random random = new Random();
            int num = random.nextInt(10);
            String coverImg = coverImgPath + num + ".jpg";
            article.setCoverImg(coverImg);
        }
        Integer count1 = articleMapper.insert(article);
        if (tagIds != null && tagIds.length() > 0) {
            List<Integer> tags = new ArrayList<>();
            tagIds = tagIds.replace("[", "").replace("]", "").replace("\"", "");
            String[] tagIdsArray = tagIds.split(",");
            if (tagIdsArray.length > 0) {
                for (String s : tagIdsArray) {
                    if (s != null && s.length() > 0) {
                        tags.add(Integer.valueOf(s));
                    }
                }
            }
            if (tags.size() > 0) {
                Integer count2 = articleMapper.batchInsertArticleAndTags(article.getId(), tags);
                if (count1 == 1 && count2 == tags.size()) {
                    return 1;
                }
                return 0;
            }
        }
        return count1;
    }

    @Override
    public Integer removeById(Integer id) {
        return articleMapper.deleteById(id);
    }

    @Override
    public Integer modify(Article article, Integer categoryId, String tagIds) {
        Category category = new Category();
        category.setId(categoryId);
        article.setCategory(category);
        article.setUpdateTime(LocalDate.now());
        Integer count1 = articleMapper.update(article);
        if (tagIds != null && tagIds.length() > 0) {
            articleMapper.deleteArticleAndTagsByArticleId(article.getId());
            List<Integer> tags = new ArrayList<>();
            tagIds = tagIds.replace("[", "").replace("]", "").replace("\"", "");
            String[] tagIdsArray = tagIds.split(",");
            if (tagIdsArray.length > 0) {
                for (String s : tagIdsArray) {
                    if (s != null && s.length() > 0) {
                        tags.add(Integer.valueOf(s));
                    }
                }
            }
            if (tags.size() > 0) {
                Integer count2 = articleMapper.batchInsertArticleAndTags(article.getId(), tags);
                if (count1 == 1 && count2 == tags.size()) {
                    return 1;
                }
                return 0;
            }
        }
        return count1;
    }

    @Override
    public Integer modify(Article article) {
        return articleMapper.update(article);
    }
}
