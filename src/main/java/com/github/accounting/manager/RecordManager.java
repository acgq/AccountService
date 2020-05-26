package com.github.accounting.manager;

import com.github.accounting.model.commom.Record;

public interface RecordManager {
    Record createRecord(Record record);

    void deleteRecord(Long recordId);

    Record getRecordByRecordId(Long recordId);

    Record updateRecord(Record record);
}
