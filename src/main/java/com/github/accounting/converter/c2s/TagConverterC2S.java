package com.github.accounting.converter.c2s;

import com.github.accounting.model.commom.Tag;
import com.github.accounting.model.service.TagInService;

import com.google.common.base.Converter;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode(callSuper = true)
public class TagConverterC2S extends Converter<Tag, TagInService> {

    @Override
    protected TagInService doForward(Tag tag) {
        TagInService tagInService = TagInService.builder()
                .id(tag.getId())
                .userId(tag.getUserId())
                .description(tag.getDescription())
                .build();
        return tagInService;
    }

    @Override
    protected Tag doBackward(TagInService tagInService) {
        return Tag.builder()
                .id(tagInService.getId())
                .userId(tagInService.getUserId())
                .description(tagInService.getDescription())
                .status(tagInService.getStatus())
                .build();
    }
}
