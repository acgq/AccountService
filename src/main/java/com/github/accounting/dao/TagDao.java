package com.github.accounting.dao;

import com.github.accounting.model.persistence.TagInPersistence;

import java.util.List;

public interface TagDao {
    void createTag(TagInPersistence tag);

    TagInPersistence getTagById(Long tagId);

    TagInPersistence getTagByDescription(String description, Long userId);

    void updateTag(TagInPersistence tag);

    List<TagInPersistence> getTagList(List<Long> ids);

    List<TagInPersistence> getTagListByUserId(Long userId, int pageNum, int pageSize);
}
