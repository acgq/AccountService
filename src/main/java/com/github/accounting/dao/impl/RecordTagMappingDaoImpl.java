package com.github.accounting.dao.impl;

import com.github.accounting.dao.RecordTagMappingDao;
import com.github.accounting.dao.mapper.RecordTagMapper;
import com.github.accounting.model.persistence.RecordTagMapping;
import com.github.accounting.model.persistence.TagInPersistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RecordTagMappingDaoImpl implements RecordTagMappingDao {
    private final RecordTagMapper recordTagMapper;

    @Autowired
    public RecordTagMappingDaoImpl(RecordTagMapper recordTagMapper) {
        this.recordTagMapper = recordTagMapper;
    }

    @Override
    public void insertRecordTagMapping(RecordTagMapping recordTagMapping) {
        recordTagMapper.insertMapping(recordTagMapping);
    }


    @Override
    public void deleteRecordTagMappingByRecordId(Long recordId) {
        recordTagMapper.deleteMapping(recordId);
    }


    @Override
    public List<RecordTagMapping> getRecordTagMappingByRecordId(Long recordId) {
        return recordTagMapper.getMappingByRecordId(recordId);
    }

    @Override
    public List<TagInPersistence> getTagsByRecordId(Long recordId) {
        return recordTagMapper.getTagListByRecordId(recordId);
    }

    @Override
    public void batchInsertRecordTagMapping(List<TagInPersistence> tagList, Long recordId) {
        List<RecordTagMapping> list = tagList.stream()
                .map(tag -> RecordTagMapping.builder()
                        .tagId(tag.getId())
                        .recordId(recordId)
                        .build())
                .collect(Collectors.toList());
        int rows = recordTagMapper.batchInsertMapping(list);
        log.debug("The row inserted {}", rows);
    }
}
