package wiki.jixing.myblog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import wiki.jixing.myblog.entity.Article;
import wiki.jixing.myblog.entity.Tag;
import wiki.jixing.myblog.entity.User;
import wiki.jixing.myblog.service.ArticleService;
import wiki.jixing.myblog.service.TagService;
import wiki.jixing.myblog.utils.Permission;
import wiki.jixing.myblog.utils.Result;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private TagService tagService;
    @Resource
    private Permission permission;

    @GetMapping
    public Result list(@RequestParam(required = false) Integer page,
                       @RequestParam(required = false) Integer pageSize,
                       @RequestParam(required = false) Integer status) {
        if (page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        List<Article> articleList;
        if (status == null) {
            articleList = articleService.getList();
        } else {
            articleList = articleService.getList(status);
        }
        if (articleList != null && articleList.size() > 0) {
            List<Article> articles = new ArrayList<>();
            for (Article article : articleList) {
                List<Tag> tags = tagService.getListByArticleId(article.getId());
                if (tags != null && tags.size() > 0) {
                    article.setTags(tags);
                }
                articles.add(article);
            }
            PageInfo<Article> pageInfo = new PageInfo<>(articleList);
            pageInfo.setList(articles);
            return new Result(1, "success", pageInfo);
        }
        return new Result(0, "error", null);
    }

    @GetMapping("/s")
    public Result searchArticle(Integer page, Integer pageSize,
                                @RequestParam(required = false) String title,
                                @RequestParam(required = false) Integer categoryId) {
        PageHelper.startPage(page, pageSize);
        List<Article> articleList = articleService.getListOnCondition(title, categoryId);
        if (articleList != null && articleList.size() > 0) {
            List<Article> articles = new ArrayList<>();
            for (Article article : articleList) {
                List<Tag> tags = tagService.getListByArticleId(article.getId());
                article.setTags(tags);
                articles.add(article);
            }
            PageInfo<Article> pageInfo = new PageInfo<>(articleList);
            pageInfo.setList(articles);
            return new Result(1, "success", pageInfo);
        }
        return new Result(0, "error", null);
    }

    @GetMapping("/ss")
    public Result listArticle(@RequestParam(required = false) String text) {
        List<Article> articleList = articleService.getListOnCondition(text);
        if (articleList != null && articleList.size() > 0) {
            return new Result(1, "success", articleList);
        }
        return new Result(0, "error", null);
    }

    @GetMapping("/count/{status}")
    public Result count(@PathVariable Integer status) {
        Integer count = articleService.countByStatus(status);
        if (count != null) {
            return new Result(1, "success", count);
        }
        return new Result(0, "error", null);
    }

    @PutMapping
    public Result modifyArticle(Article article, Integer categoryId, String tagIds) {
        if (!permission.allow()) {
            return new Result(0, "没有权限", null);
        }
        Integer count = articleService.modify(article, categoryId, tagIds);
        if (count == 1) {
            return new Result(1, "修改成功", null);
        }
        return new Result(0, "修改失败", null);
    }

    @PostMapping
    public Result add(Article article, Integer categoryId, String tagIds) {
        if (!permission.allow()) {
            return new Result(0, "没有权限", null);
        }
        int status = article.getStatus();
        Integer count = articleService.add(article, categoryId, tagIds);
        if (count == 1) {
            return new Result(1, status == 1 ? "发布成功" : "保存成功", null);
        }
        return new Result(0, status == 1 ? "发布失败" : "保存失败", null);
    }

    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Integer id) {
        if (!permission.allow()) {
            return new Result(0, "没有权限", null);
        }
        Integer count = articleService.removeById(id);
        if (count == 1) {
            return new Result(1, "删除成功", null);
        }
        return new Result(0, "删除失败", null);
    }
}
