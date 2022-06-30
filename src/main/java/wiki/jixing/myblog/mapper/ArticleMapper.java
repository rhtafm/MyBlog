package wiki.jixing.myblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import wiki.jixing.myblog.entity.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {
    Integer count();

    Integer countByStatus(Integer status);

    Integer countByCategoryId(Integer id);

    Integer countByTagId(Integer id);

    List<Article> listAll();

    List<Article> listByStatus(Integer status);

    List<Article> listByIds(List<Integer> ids);

    List<Article> listByCategoryId(Integer id);

    List<Article> listByCategoryIdAndStatus(Integer id, Integer status);

    Article selectById(Integer id);

    List<Article> selectListOnCondition(String title, Integer categoryId);

    List<Article> selectListByText(String text);

    Integer insert(Article article);

    Integer deleteById(Integer id);

    Integer update(Article article);

    List<Integer> listArticleIdByTagId(Integer id);

    Integer batchInsertArticleAndTags(Integer articleId, List<Integer> tagIds);

    Integer deleteArticleAndTagsByArticleId(Integer id);

    Integer deleteArticleAndTagsByTagId(Integer id);
}
