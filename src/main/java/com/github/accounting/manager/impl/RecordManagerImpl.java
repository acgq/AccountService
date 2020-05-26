package com.github.accounting.manager.impl;

import com.github.accounting.converter.p2c.RecordConverterP2C;
import com.github.accounting.dao.RecordDao;
import com.github.accounting.dao.RecordTagMappingDao;
import com.github.accounting.dao.TagDao;
import com.github.accounting.exception.InvalidParameterException;
import com.github.accounting.exception.ResourceNotFoundException;
import com.github.accounting.manager.RecordManager;
import com.github.accounting.model.commom.Record;
import com.github.accounting.model.persistence.RecordInPersistence;
import com.github.accounting.model.persistence.TagInPersistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class RecordManagerImpl implements RecordManager {
    private final RecordDao recordDao;
    private final RecordTagMappingDao mappingDao;
    private final TagDao tagDao;
    private final RecordConverterP2C recordConverter;

    /**
     * Constructor of RecordManger.
     * @param recordDao record dao.
     * @param mappingDao record tag mapping dao.
     * @param tagDao tag dao.
     * @param recordConverter record converter.
     */
    @Autowired
    public RecordManagerImpl(RecordDao recordDao, RecordTagMappingDao mappingDao, TagDao tagDao, RecordConverterP2C recordConverter) {
        this.recordDao = recordDao;
        this.mappingDao = mappingDao;
        this.tagDao = tagDao;
        this.recordConverter = recordConverter;
    }

    @Override
    public Record createRecord(Record record) {
        RecordInPersistence recordToCreate = recordConverter.reverse().convert(record);
        // create record and the record and tag mapping
        List<Long> tagIds = recordToCreate.getTagList().stream()
                .map(tag -> tag.getId())
                .collect(Collectors.toList());
        log.debug("tag id in record  {}", tagIds);

        List<TagInPersistence> tagList = tagDao.getTagList(tagIds);

        log.debug("tag list get from store {}", tagList);
        //validate tag is belong to user who create this record
        tagList.forEach(tag -> {
            if (!tag.getUserId().equals(record.getUserId())) {
                throw new InvalidParameterException(String.format("The tag [%s] is not matched for user", tag.getId()));
            }
        });
        //insert record and record tag mapping
        recordDao.insertRecord(recordToCreate);
        mappingDao.batchInsertRecordTagMapping(tagList, record.getId());

        return getRecordByRecordId(recordToCreate.getId());
    }

    @Override
    public void deleteRecord(Long recordId) {
        RecordInPersistence recordInStore = recordDao.getRecordById(recordId);
        recordInStore.setStatus(0);
        recordDao.updateRecord(recordInStore);
    }

    @Override
    public Record getRecordByRecordId(Long recordId) {
        return Optional.ofNullable(recordDao.getRecordById(recordId))
                .map(recordConverter::convert)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Can not find record with id [%s]", recordId)));
    }

    @Override
    public Record updateRecord(Record record) {
        RecordInPersistence recordInStore = Optional.ofNullable(recordDao.getRecordById(record.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("The related id [%s] not found", record.getId())));
        if (!record.getUserId().equals(recordInStore.getUserId())) {
            throw new InvalidParameterException(
                    String.format("The record id [%s] doesn't belong to user id: [%s]",
                            record.getId(),
                            record.getUserId()));
        }
        //check tag is valid
        List<Long> tagIds = record.getTagList().stream()
                .map(tag -> tag.getId())
                .collect(Collectors.toList());
        List<TagInPersistence> tagList = tagDao.getTagList(tagIds);
        for (TagInPersistence tagInPersistence : tagList) {
            if (!tagInPersistence.getUserId().equals(record.getUserId())) {
                throw new InvalidParameterException(
                        String.format("The tag [%s] doesn't belong to user [%s]",
                                tagInPersistence.getId(),
                                record.getUserId()));
            }
        }

        RecordInPersistence recordOriginal = recordConverter.reverse().convert(record);
        //update
        mappingDao.deleteRecordTagMappingByRecordId(record.getId());
        mappingDao.batchInsertRecordTagMapping(tagList, record.getId());
        recordDao.updateRecord(recordOriginal);

        return recordConverter.convert(recordDao.getRecordById(record.getId()));
    }

    private void replaceBeanPropertyIfNotEqual(Object recordOriginal, Object recordInStore, Object recordToInsert) {
        Method[] methods = recordOriginal.getClass().getDeclaredMethods();
        Stream.of(methods).filter(method -> method.getName().startsWith("get"))
                .forEach(method -> {
                    try {
                        if (!method.invoke(recordOriginal).equals(method.invoke(recordInStore))) {
                            String setMethodName = method.getName().replace("^get", "set");
                            Method setMethod = recordOriginal.getClass().getMethod(setMethodName);
                            setMethod.invoke(recordToInsert, method.invoke(recordInStore));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
