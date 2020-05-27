package com.github.accounting.manager;

import com.github.accounting.model.commom.Tag;
import com.github.pagehelper.PageInfo;

public interface TagManager {

    Tag getTagByTagId(Long id);

    Tag createTag(String description, Long userId);

    Tag updateTag(Tag tag);

    PageInfo<Tag> getTags(Long userId, int pageNum, int pageSize);

}
