package com.github.accounting.manager;

import com.github.accounting.model.commom.Tag;

public interface TagManager {

    Tag getTagByTagId(Long id);

    Tag createTag(String description, Long userId);

    Tag updateTag(Tag tag);

}
