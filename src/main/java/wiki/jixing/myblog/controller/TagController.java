package wiki.jixing.myblog.controller;

import org.springframework.web.bind.annotation.*;
import wiki.jixing.myblog.entity.Tag;
import wiki.jixing.myblog.entity.User;
import wiki.jixing.myblog.service.TagService;
import wiki.jixing.myblog.utils.Permission;
import wiki.jixing.myblog.utils.Result;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Resource
    private TagService tagService;

    @Resource
    private Permission permission;

    @GetMapping
    public Result listAll() {
        List<Tag> tagList = tagService.getList();
        if (tagList != null && tagList.size() > 0) {
            return new Result(1, "查询成功", tagList);
        }
        return new Result(0, "查询失败", null);
    }

    @GetMapping("/{tagName}")
    public Result query(@PathVariable String tagName) {
        List<Tag> tagList = tagService.getLikeName(tagName);
        if (tagList != null && tagList.size() > 0) {
            return new Result(1, "查询成功", tagList);
        }
        return new Result(0, "查询失败", null);
    }

    @GetMapping("/count")
    public Result count() {
        Integer count = tagService.count();
        if (count != null) {
            return new Result(1, "success", count);
        }
        return new Result(0, "error", null);
    }

    @PostMapping
    public Result add(String tagName) {
        if (!permission.allow()) {
            return new Result(0, "没有权限", null);
        }
        try {
            if (tagName != null && tagName.trim().length() > 0) {
                Tag tag = new Tag();
                tag.setName(tagName);
                Integer count = tagService.add(tag);
                if (count == 1) {
                    return new Result(1, "添加成功", tag);
                }
            }
            return new Result(0, "添加失败", null);
        } catch (Exception e) {
            return new Result(0, "添加失败", null);
        }
    }

    @PutMapping("/{id}")
    public Result modify(@PathVariable Integer id, String tagName) {
        if (!permission.allow()) {
            return new Result(0, "没有权限", null);
        }
        try {
            if (tagName != null && tagName.trim().length() > 0) {
                Tag tag = new Tag();
                tag.setId(id);
                tag.setName(tagName);
                Integer count = tagService.modify(tag);
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
        Integer count = tagService.removeById(id);
        if (count == 1) {
            return new Result(1, "删除成功", null);
        }
        return new Result(0, "删除失败", null);
    }
}
