package com.github.accounting.controller;

import com.github.accounting.converter.c2s.TagConverterC2S;
import com.github.accounting.exception.InvalidParameterException;
import com.github.accounting.manager.TagManager;
import com.github.accounting.manager.UserInfoManager;
import com.github.accounting.model.commom.Tag;
import com.github.accounting.model.commom.UserInfo;
import com.github.accounting.model.service.TagInService;
import com.github.pagehelper.PageInfo;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/tags")
public class TagController {
    private static final String APPLICATION_JSON_VALUE = "application/json";
    private final TagManager tagManager;
    private final UserInfoManager userInfoManager;
    private final TagConverterC2S tagConverter;

    /**
     * Constructor of tag controller.
     *
     * @param tagManager      tag manager
     * @param userInfoManager userinfo manager
     * @param tagConverter    tag converter from common to service
     */
    @Autowired
    public TagController(TagManager tagManager, UserInfoManager userInfoManager, TagConverterC2S tagConverter) {
        this.tagManager = tagManager;
        this.userInfoManager = userInfoManager;
        this.tagConverter = tagConverter;
    }

    /**
     * Get tag information by tag id.
     *
     * @param id the specific tag id.
     * @return the related tag information.
     */
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public TagInService getTagByTagId(@PathVariable("id") Long id) {
        if (id == null || id <= 0L) {
            throw new InvalidParameterException("the tagId must be positive");
        }
        Tag tagByTagId = tagManager.getTagByTagId(id);
        return tagConverter.convert(tagByTagId);
    }

    /**
     * Get tags with specific page number and page size.
     *
     * @param pageNum  page number
     * @param pageSize page size
     * @return tag info
     */
    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    public PageInfo<Tag> getTags(@RequestParam("pageNum") int pageNum,
                                 @RequestParam("pageSize") int pageSize) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfo userInfo = userInfoManager.getUserInfoByUsername(username);
        PageInfo<Tag> tags = tagManager.getTags(userInfo.getId(), pageNum, pageSize);
        return tags;
    }

    /**
     * Create tag with related information.
     *
     * @param tag tag information to create.
     * @return tag created.
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public TagInService createTag(@RequestBody TagInService tag) {
        if (tag.getDescription() == null
                || tag.getDescription().isEmpty()
                || tag.getUserId() == null
                || tag.getUserId() <= 0L) {
            throw new InvalidParameterException("The description and user id must be not null or empty");
        }
        Tag resource = tagManager.createTag(tag.getDescription(), tag.getUserId());
        return tagConverter.convert(resource);
    }

    /**
     * Update tag information for specific tag.
     *
     * @param tagId the specific tag id.
     * @param tag   the tag information.
     * @return the updated tag information.
     */
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public TagInService updateTag(@PathVariable("id") Long tagId, @RequestBody TagInService tag) {
        if (tagId == null || tagId <= 0L) {
            throw new InvalidParameterException("The tag id must be not empty and positive");
        }
        if (tag.getUserId() == null || tag.getUserId() <= 0L) {
            throw new InvalidParameterException("The user id is empty or invalid");
        }
        String status = tag.getStatus();
        if (status != null && !"ENABLE".equals(status) && !"DISABLE".equals(status)) {
            throw new InvalidParameterException(String.format("the status [%s] is invalid", status));
        }
        tag.setId(tagId);
        Tag tagInCommon = tagConverter.reverse().convert(tag);
        Tag resource = tagManager.updateTag(tagInCommon);
        return tagConverter.convert(resource);

    }
}
