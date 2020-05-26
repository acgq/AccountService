package com.github.accounting.dao.mapper;

import com.github.accounting.dao.provider.RecordSqlProvider;
import com.github.accounting.model.persistence.RecordInPersistence;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RecordMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO as_record (id, user_id, amount, note, category, status, create_time)"
            + "VALUES (#{id}, #{userId}, #{amount}, #{note}, #{category}, #{status}, #{createTime})")
    int insertRecord(RecordInPersistence record);

    @UpdateProvider(value = RecordSqlProvider.class, method = "updateRecord")
    int updateRecord(RecordInPersistence record);

    @Select("SELECT id, user_id, amount, note, category, status FROM as_record WHERE id = #{id}")
    @Results({@Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "category", column = "category"),
            @Result(property = "status", column = "status"),
            @Result(property = "tagList", javaType = List.class, column = "id",
                    many = @Many(select = "com.github.accounting.dao.mapper."
                            + "RecordTagMapper.getTagListByRecordId"))})
    RecordInPersistence getRecordById(@Param("id") Long id);

}
