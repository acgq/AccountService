package com.github.accounting.converter.c2s;

import com.github.accounting.model.commom.Record;
import com.github.accounting.model.commom.Tag;
import com.github.accounting.model.service.RecordInService;
import com.github.accounting.model.service.TagInService;

import com.google.common.base.Converter;
import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode(callSuper = true)
public class RecordConverterC2S extends Converter<Record, RecordInService> {
    private final TagConverterC2S tagConverterC2S;

    @Autowired
    public RecordConverterC2S(TagConverterC2S tagConverterC2S) {
        this.tagConverterC2S = tagConverterC2S;
    }

    @Override
    protected RecordInService doForward(Record record) {
        ImmutableList<TagInService> tagList = ImmutableList.copyOf(tagConverterC2S.convertAll(record.getTagList()));
        RecordInService recordInService = RecordInService.builder()
                .id(record.getId())
                .amount(record.getAmount())
                .category(record.getCategory())
                .note(record.getNote())
                .tagList(tagList)
                .build();
        return recordInService;
    }

    @Override
    protected Record doBackward(RecordInService recordInService) {
        ImmutableList<Tag> tagList = ImmutableList.copyOf(tagConverterC2S.reverse().convertAll(recordInService.getTagList()));
        return Record.builder()
                .id(recordInService.getId())
                .userId(recordInService.getUserId())
                .amount(recordInService.getAmount())
                .tagList(tagList)
                .category(recordInService.getCategory())
                .note(recordInService.getNote())
                .build();
    }
}
