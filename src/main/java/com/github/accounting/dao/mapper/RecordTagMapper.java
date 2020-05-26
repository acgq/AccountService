package com.github.accounting.dao.mapper;

import com.github.accounting.model.persistence.RecordTagMapping;
import com.github.accounting.model.persistence.TagInPersistence;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RecordTagMapper {

    @Insert("INSERT INTO as_record_tag_mapping (id, record_id, tag_id)"
            + "VALUE (#{id}, #{recordId}, #{tagId})")
    int insertMapping(RecordTagMapping mapping);

    @Insert({
            "<script>",
            "insert into as_record_tag_mapping (record_id, tag_id)",
            "values ",
            "<foreach  collection='list' item='map' separator=','>",
            "( #{map.recordId}, #{map.tagId})",
            "</foreach>",
            "</script>"
    })
    int batchInsertMapping(@Param("list") List<RecordTagMapping> mappingList);

    @Delete("DELETE FROM as_record_tag_mapping WHERE record_id = #{recordId}")
    int deleteMapping(@Param("recordId") Long recordId);

    //    @SelectProvider(value = RecordTagMappingProvider.class, method = "getMappingByRecordId")
    @Select("SELECT id, record_id, tag_id,create_time, update_time  FROM as_record_tag_mapping "
            + "WHERE record_id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(column = "record_id", property = "recordId"),
            @Result(column = "tag_id", property = "tagId"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<RecordTagMapping> getMappingByRecordId(@Param("id") Long id);


    @Select("SELECT id, user_id, description, status FROM as_tag "
            + "WHERE id IN (SELECT tag_id FROM as_record_tag_mapping WHERE record_id = #{id})")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "description", property = "description"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<TagInPersistence> getTagListByRecordId(@Param("id") Long id);
}
