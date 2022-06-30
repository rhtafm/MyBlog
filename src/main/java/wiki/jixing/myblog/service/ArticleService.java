package wiki.jixing.myblog.service;

import wiki.jixing.myblog.entity.Article;

import java.util.List;

public interface ArticleService {
    Integer countByStatus(Integer status);

    List<Article> getListByCategoryId(Integer id);

    List<Article> getListByCategoryIdAndStatus(Integer id, Integer status);

    List<Article> getListByTagId(Integer id);

    List<Article> getList();

    List<Article> getList(Integer status);

    Article getById(Integer id);

    List<Article> getListOnCondition(String title, Integer categoryId);

    List<Article> getListOnCondition(String text);

    Integer add(Article article, Integer categoryId, String tagIds);

    Integer removeById(Integer id);

    Integer modify(Article article, Integer categoryId, String tagIds);

    Integer modify(Article article);
}
