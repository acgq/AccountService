package com.github.accounting.converter.p2c;

import com.github.accounting.model.commom.Record;
import com.github.accounting.model.commom.Tag;
import com.github.accounting.model.persistence.RecordInPersistence;
import com.github.accounting.model.persistence.TagInPersistence;

import com.google.common.base.Converter;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecordConverterP2C extends Converter<RecordInPersistence, Record> {
    private static final String ENABLE = "ENABLE";
    private static final String DISABLE = "DISABLE";
    private static final String INCOME = "INCOME";
    private static final String OUTCOME = "OUTCOME";

    private TagConverterP2C tagConverter;

    @Autowired
    public RecordConverterP2C(TagConverterP2C tagConverter) {
        this.tagConverter = tagConverter;
    }

    @Override
    protected Record doForward(RecordInPersistence recordInPersistence) {
        ImmutableList<Tag> tagList = ImmutableList.copyOf(tagConverter.convertAll(recordInPersistence.getTagList()));
        return Record.builder()
                .id(recordInPersistence.getId())
                .userId(recordInPersistence.getUserId())
                .amount(recordInPersistence.getAmount())
                .note(recordInPersistence.getNote())
                .status(recordInPersistence.getStatus() != 1 ? DISABLE : ENABLE)
                .category(recordInPersistence.getCategory() != 1 ? OUTCOME : INCOME)
                .tagList(tagList)
                .build();
    }

    @Override
    protected RecordInPersistence doBackward(Record record) {
        ImmutableList<TagInPersistence> tagList = ImmutableList.copyOf(tagConverter.reverse().convertAll(record.getTagList()));
        return RecordInPersistence.builder()
                .id(record.getId())
                .userId(record.getUserId())
                .category(INCOME.equals(record.getCategory()) ? 1 : 0)
                .status(ENABLE.equals(record.getStatus()) ? 1 : 0)
                .amount(record.getAmount())
                .note(record.getNote())
                .tagList(tagList)
                .build();
    }
}
