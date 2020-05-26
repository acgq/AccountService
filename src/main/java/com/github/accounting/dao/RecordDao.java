package com.github.accounting.dao;

import com.github.accounting.model.persistence.RecordInPersistence;

public interface RecordDao {
    void insertRecord(RecordInPersistence record);

    RecordInPersistence getRecordById(Long id);

    void updateRecord(RecordInPersistence record);
}
