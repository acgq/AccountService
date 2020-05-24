package com.github.accounting.dao.impl;

import com.github.accounting.dao.TagDao;
import com.github.accounting.dao.mapper.TagMapper;
import com.github.accounting.model.persistence.TagInPersistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagDaoImpl implements TagDao {

    private final TagMapper tagMapper;

    @Autowired
    public TagDaoImpl(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public void createTag(TagInPersistence tag) {
        tagMapper.insertTag(tag);
    }

    @Override
    public TagInPersistence getTagById(Long tagId) {
        return tagMapper.getTagById(tagId);
    }

    @Override
    public TagInPersistence getTagByDescription(String description, Long userId) {
        return tagMapper.getTagByDescription(description, userId);
    }

    @Override
    public void updateTag(TagInPersistence tag) {
        tagMapper.updateTag(tag);
    }
}
