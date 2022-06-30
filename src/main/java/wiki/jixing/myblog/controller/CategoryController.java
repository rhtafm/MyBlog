package wiki.jixing.myblog.controller;

import org.springframework.web.bind.annotation.*;
import wiki.jixing.myblog.entity.Category;
import wiki.jixing.myblog.entity.User;
import wiki.jixing.myblog.service.CategoryService;
import wiki.jixing.myblog.utils.Permission;
import wiki.jixing.myblog.utils.Result;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private Permission permission;

    @GetMapping
    public Result listAll() {
        List<Category> categoryList = categoryService.getList();
        if (categoryList != null && categoryList.size() > 0) {
            return new Result(1, "查询成功", categoryList);
        }
        return new Result(0, "查询失败", null);
    }

    @GetMapping("/{categoryName}")
    public Result query(@PathVariable String categoryName) {
        List<Category> categoryList = categoryService.getLikeName(categoryName);
        if (categoryList != null && categoryList.size() > 0) {
            return new Result(1, "查询成功", categoryList);
        }
        return new Result(0, "查询失败", null);
    }

    @GetMapping("/count")
    public Result count() {
        Integer count = categoryService.count();
        if (count != null) {
            return new Result(1, "success", count);
        }
        return new Result(0, "error", null);
    }

    @GetMapping("/statistics")
    public Result statistics() {
        List<Map<String, String>> categoryList = categoryService.getCategoryAndArticleCountList();
        if (categoryList != null) {
            return new Result(1, "success", categoryList);
        }
        return new Result(0, "error", null);
    }

    @PostMapping
    public Result add(String categoryName) {
        if (!permission.allow()) {
            return new Result(0, "没有权限", null);
        }
        try {
            if (categoryName != null && categoryName.trim().length() > 0) {
                Category category = new Category();
                category.setName(categoryName);
                Integer count = categoryService.add(category);
                if (count == 1) {
                    return new Result(1, "添加成功", category);
                }
            }
            return new Result(0, "添加失败", null);
        } catch (Exception e) {
            return new Result(0, "添加失败", null);
        }
    }

    @PutMapping("/{id}")
    public Result modify(@PathVariable Integer id, String categoryName) {
        if (!permission.allow()) {
            return new Result(0, "没有权限", null);
        }
        try {
            if (categoryName != null && categoryName.trim().length() > 0) {
                Category category = new Category();
                category.setId(id);
                category.setName(categoryName);
                Integer count = categoryService.modify(category);
                if (count == 1) {
                    return new Result(1, "修改成功", null);
                }
            }
            return new Result(0, "修改失败", null);
        } catch (Exception e) {
            return new Result(0, "修改失败", null);
        }
    }

    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Integer id) {
        if (!permission.allow()) {
            return new Result(0, "没有权限", null);
        }
        Integer count = categoryService.removeById(id);
        if (count == 1) {
            return new Result(1, "删除成功", null);
        }
        return new Result(0, "删除失败", null);
    }
}
