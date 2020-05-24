package com.github.accounting.manager.impl;

import com.github.accounting.converter.p2c.TagConverterP2C;
import com.github.accounting.dao.TagDao;
import com.github.accounting.exception.InvalidParameterException;
import com.github.accounting.exception.ResourceNotFoundException;
import com.github.accounting.manager.TagManager;
import com.github.accounting.model.commom.Tag;
import com.github.accounting.model.persistence.TagInPersistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class TagManagerImpl implements TagManager {

    private final TagDao tagDao;
    private final TagConverterP2C tagConverter;

    @Autowired
    public TagManagerImpl(TagDao tagDao, TagConverterP2C tagConverter) {
        this.tagDao = tagDao;
        this.tagConverter = tagConverter;
    }

    @Override
    public Tag getTagByTagId(Long id) {
        return Optional.ofNullable(tagDao.getTagById(id))
                .map(tagConverter::convert)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("the related tag id [%s] not found", id)));
    }

    @Override
    public Tag createTag(String description, Long userId) {
        Optional.ofNullable(tagDao.getTagByDescription(description, userId))
                .ifPresent(tag -> {
                    throw new InvalidParameterException(
                            String.format("The related tag with [%s] has been created", description));
                });

        TagInPersistence tagToBeCreate = TagInPersistence.builder()
                .userId(userId)
                .description(description)
                .createTime(LocalDate.now())
                .status(1)
                .build();
        tagDao.createTag(tagToBeCreate);
        return tagConverter.convert(tagToBeCreate);
    }

    @Override
    public Tag updateTag(Tag tag) {
        TagInPersistence convert = tagConverter.reverse().convert(tag);
        TagInPersistence tagInDB = Optional.ofNullable(tagDao.getTagById(convert.getId()))
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format("the related tag with id [%s] not exist", tag.getId())));

        if (!tagInDB.getUserId().equals(tag.getUserId())) {
            throw new InvalidParameterException(String.format("the tag id %s do not belong to user %s", tag.getId(), tag.getUserId()));
        }

        tagDao.updateTag(convert);
        return getTagByTagId(tag.getId());

    }
}
