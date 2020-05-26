package com.github.accounting.dao.impl;

import com.github.accounting.dao.RecordDao;
import com.github.accounting.dao.mapper.RecordMapper;
import com.github.accounting.dao.mapper.RecordTagMapper;
import com.github.accounting.model.persistence.RecordInPersistence;
import com.github.accounting.model.persistence.TagInPersistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
public class RecordDaoImpl implements RecordDao {
    private final RecordMapper recordMapper;
    private RecordTagMapper recordTagMapper;

    @Autowired
    public RecordDaoImpl(RecordMapper recordMapper, RecordTagMapper recordTagMapper) {
        this.recordMapper = recordMapper;
        this.recordTagMapper = recordTagMapper;
    }

    @Override
    public void insertRecord(RecordInPersistence record) {
        if (record.getCreateTime() == null) {
            record.setCreateTime(LocalDate.now());
        }
        record.setStatus(1);

        List<TagInPersistence> tagListsByRecordId = recordTagMapper.getTagListByRecordId(record.getId());
        log.debug("Record in RecordDaoImpl is {}", record);
        recordMapper.insertRecord(record);
        log.debug("Record after insert is {}", record);
    }

    @Override
    public RecordInPersistence getRecordById(Long id) {
        return recordMapper.getRecordById(id);
    }

    @Override
    public void updateRecord(RecordInPersistence record) {
        recordMapper.updateRecord(record);
    }
}
