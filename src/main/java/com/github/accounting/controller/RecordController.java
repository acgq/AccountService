package com.github.accounting.controller;

import com.github.accounting.converter.c2s.RecordConverterC2S;
import com.github.accounting.exception.InvalidParameterException;
import com.github.accounting.manager.RecordManager;
import com.github.accounting.model.commom.Record;
import com.github.accounting.model.service.RecordInService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/records")
@Slf4j
public class RecordController {
    private static final String APPLICATION_JSON_VALUE = "application/json";

    private final RecordConverterC2S recordConverter;
    private final RecordManager recordManager;


    @Autowired
    public RecordController(RecordConverterC2S recordConverter, RecordManager recordManager) {
        this.recordConverter = recordConverter;
        this.recordManager = recordManager;
    }

    /**
     * Get Record by record id.
     *
     * @param id record id.
     * @return record information.
     */
    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    public RecordInService getRecordByRecordId(@PathVariable("id") Long id) {
        if (id == null || id <= 0L) {
            throw new InvalidParameterException("Invalid record id");
        }
        Record recordByRecordId = recordManager.getRecordByRecordId(id);
        return recordConverter.convert(recordByRecordId);
    }

    /**
     * Create new Record.
     *
     * @param record record information.
     * @return record information created.
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public RecordInService createRecord(@RequestBody RecordInService record) {
        if (checkRecordIllegal(record)) {
            throw new InvalidParameterException("Invalid record to create");
        }
        Record recordToCreate = recordConverter.reverse().convert(record);
        Record resource = recordManager.createRecord(recordToCreate);
        return recordConverter.convert(resource);

    }

    private boolean checkRecordIllegal(RecordInService record) {
        return record.getUserId() == null
                || record.getAmount() == null
                || record.getCategory() == null
                || (!record.getCategory().equals("INCOME") && !record.getCategory().equals("OUTCOME"));
    }

    /**
     * Update record .
     *
     * @param recordId record id to update.
     * @param record   record information to update.
     * @return updated information.
     */
    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public RecordInService updateRecord(@PathVariable("id") Long recordId, @RequestBody RecordInService record) {
        if (record == null || recordId <= 0L) {
            throw new InvalidParameterException("Record id must be not empty and positive");
        }
        if (record.getUserId() == null || record.getUserId() <= 0L) {
            throw new InvalidParameterException("The user id is empty of invalid");
        }
        record.setId(recordId);
        Record resource = recordManager.updateRecord(recordConverter.reverse().convert(record));
        return recordConverter.convert(resource);
    }
}
