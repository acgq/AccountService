package com.github.accounting.dao;

import com.github.accounting.model.persistence.RecordTagMapping;
import com.github.accounting.model.persistence.TagInPersistence;

import java.util.List;

public interface RecordTagMappingDao {
    void insertRecordTagMapping(RecordTagMapping recordTagMapping);

    void deleteRecordTagMappingByRecordId(Long recordId);

    List<RecordTagMapping> getRecordTagMappingByRecordId(Long recordId);

    List<TagInPersistence> getTagsByRecordId(Long recordId);

    void batchInsertRecordTagMapping(List<TagInPersistence> tagList, Long id);
}
