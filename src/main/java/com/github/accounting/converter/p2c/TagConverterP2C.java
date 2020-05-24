package com.github.accounting.converter.p2c;
import com.github.accounting.model.commom.Tag;
import com.github.accounting.model.persistence.TagInPersistence;

import com.google.common.base.Converter;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode(callSuper = true)
public class TagConverterP2C extends Converter<TagInPersistence, Tag> {
    private static final String ENABLE = "ENABLE";
    private static final String DISABLE = "DISABLE";

    @Override
    protected Tag doForward(TagInPersistence tag) {
        return Tag.builder()
                .id(tag.getId())
                .userId(tag.getUserId())
                .description(tag.getDescription())
                .status(tag.getStatus() == 1 ? ENABLE : DISABLE)
                .build();
    }

    @Override
    protected TagInPersistence doBackward(Tag tag) {
        TagInPersistence tagInPersistence = TagInPersistence.builder()
                .id(tag.getId())
                .userId(tag.getUserId())
                .description(tag.getDescription())
                .build();
        if (tag.getStatus() != null) {
            tagInPersistence.setStatus(ENABLE.equals(tag.getStatus()) ? 1 : 0);
        }
        return tagInPersistence;
    }
}
