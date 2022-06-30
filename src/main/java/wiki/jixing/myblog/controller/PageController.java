package wiki.jixing.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wiki.jixing.myblog.entity.Article;
import wiki.jixing.myblog.entity.Category;
import wiki.jixing.myblog.entity.Tag;
import wiki.jixing.myblog.enums.ErrorEnum;
import wiki.jixing.myblog.exception.CustomException;
import wiki.jixing.myblog.service.ArticleService;
import wiki.jixing.myblog.service.CategoryService;
import wiki.jixing.myblog.service.TagService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {
    @Resource
    private ArticleService articleService;

    @Resource
    private TagService tagService;

    @Resource
    private CategoryService categoryService;

    @RequestMapping
    public String index() {
        return "index";
    }

    @RequestMapping("/category")
    public String category() {
        return "category";
    }

    @RequestMapping("/archive")
    public String archive() {
        return "archive";
    }

    @RequestMapping("/comment")
    public String comment() {
        return "comment";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/admin")
    public String adminIndex() {
        return "admin/admin-index";
    }

    @RequestMapping("/admin/login")
    public String login() {
        return "admin/login";
    }

    @RequestMapping("/admin/register")
    public String register() {
        return "admin/register";
    }

    @RequestMapping("/admin/forget")
    public String forget() {
        return "admin/forget";
    }

    @RequestMapping("/admin/article")
    public String article() {
        return "admin/admin-article";
    }

    @RequestMapping("/admin/comment")
    public String AdminComment() {
        return "admin/admin-comment";
    }

    @RequestMapping("/admin/user")
    public String AdminUser() {
        return "admin/admin-user";
    }

    @RequestMapping("/admin/edit")
    public String edit() {
        return "admin/admin-edit";
    }

    @RequestMapping("/articles/m/{id}")
    public String modify(@PathVariable Integer id, Model model) {
        Article article = articleService.getById(id);
        List<Integer> tagIds = new ArrayList<>();
        List<Tag> tags = tagService.getListByArticleId(article.getId());
        if (tags != null && tags.size() > 0) {
            for (Tag tag : tags) {
                tagIds.add(tag.getId());
            }
        }
        model.addAttribute("article", article);
        model.addAttribute("tagIds", tagIds.toString());
        return "admin/admin-modify";
    }


    @RequestMapping("/article-tag/{id}")
    public String articleTag(@PathVariable Integer id, Model model) {
        Tag tag = tagService.getById(id);
        if (tag == null) {
            throw new CustomException(ErrorEnum.PAGE_NOT_FOUND);
        }
        List<Article> articleList = articleService.getListByTagId(id);
        List<Article> articles = new ArrayList<>();
        if (articleList != null) {
            for (Article article : articleList) {
                if (article.getStatus() == 1) {
                    articles.add(article);
                }
            }
        }
        model.addAttribute("tag", tag);
        model.addAttribute("articleList", articles);
        return "article-tag";
    }

    @RequestMapping("/article-category/{id}")
    public String articleCategory(@PathVariable Integer id, Model model) {
        Category category = categoryService.getById(id);
        if (category == null) {
            throw new CustomException(ErrorEnum.PAGE_NOT_FOUND);
        }
        List<Article> articleList = articleService.getListByCategoryIdAndStatus(id, 1);
        model.addAttribute("category", category);
        model.addAttribute("articleList", articleList);
        return "article-category";
    }

    @RequestMapping("/p/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Article article = articleService.getById(id);
        if (article == null || article.getStatus() != 1) {
            throw new CustomException(ErrorEnum.PAGE_NOT_FOUND);
        }
        model.addAttribute("article", article);
        article.setViews(article.getViews() + 1);
        articleService.modify(article);
        return "detail";
    }
}
