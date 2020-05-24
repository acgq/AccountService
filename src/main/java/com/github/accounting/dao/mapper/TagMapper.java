package com.github.accounting.dao.mapper;

import com.github.accounting.dao.provider.TagSqlProvider;
import com.github.accounting.model.persistence.TagInPersistence;

import org.apache.ibatis.annotations.*;

@Mapper
public interface TagMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO as_tag(description, user_id, status)"
            + "VALUES (#{description}, #{userId}, #{status})")
    int insertTag(TagInPersistence tag);

    @UpdateProvider(value = TagSqlProvider.class, method = "updateTag")
    int updateTag(TagInPersistence tag);

    @Select("SELECT id, description, user_id, status, create_time, update_time FROM as_tag "
            + "WHERE id = #{id}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "description", property = "description"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
    })
    TagInPersistence getTagById(@Param("id") Long id);

    @Select("SELECT id, description, user_id, status, create_time, update_time FROM as_tag "
            + "WHERE  user_id = #{userId} and description = #{description}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "description", property = "description"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
    })
    TagInPersistence getTagByDescription(@Param("description") String description, @Param("userId") Long userId);


}
