package com.github.accounting.dao;

import com.github.accounting.model.persistence.TagInPersistence;

public interface TagDao {
    void createTag(TagInPersistence tag);

    TagInPersistence getTagById(Long tagId);

    TagInPersistence getTagByDescription(String description, Long userId);

    void updateTag(TagInPersistence tag);
}
