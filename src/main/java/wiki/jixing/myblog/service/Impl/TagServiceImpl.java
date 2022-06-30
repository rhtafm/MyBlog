package wiki.jixing.myblog.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiki.jixing.myblog.entity.Tag;
import wiki.jixing.myblog.mapper.ArticleMapper;
import wiki.jixing.myblog.mapper.TagMapper;
import wiki.jixing.myblog.service.TagService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagMapper tagMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Integer count() {
        return tagMapper.count();
    }

    @Override
    public Tag getById(Integer id) {
        return tagMapper.selectById(id);
    }

    @Override
    public List<Tag> getLikeName(String name) {
        return tagMapper.selectLikeName("%" + name.trim() + "%");
    }

    @Override
    public List<Tag> getList() {
        return tagMapper.listAll();
    }

    @Override
    public List<Tag> getListByArticleId(Integer articleId) {
        List<Integer> tagIds = tagMapper.selectArticleAndTagsTagIdsByArticleId(articleId);
        if (tagIds != null && tagIds.size() > 0) {
            return tagMapper.listByIds(tagIds);
        }
        return null;
    }

    @Override
    public Integer add(Tag tag) {
        tag.setCreateTime(LocalDateTime.now());
        tag.setUpdateTime(LocalDateTime.now());
        return tagMapper.insert(tag);
    }

    @Override
    public Integer removeById(Integer id) {
        Integer count = articleMapper.countByTagId(id);
        if (count != 0) {
            return 0;
        }
        return tagMapper.deleteById(id);
    }

    @Override
    public Integer modify(Tag tag) {
        tag.setUpdateTime(LocalDateTime.now());
        return tagMapper.update(tag);
    }
}
