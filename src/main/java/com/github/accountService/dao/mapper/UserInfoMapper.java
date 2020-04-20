package com.github.accountService.dao.mapper;

import com.github.accountService.model.persistence.UserInfoInPersistence;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {

    @Select("select id, username,password,create_time,update_time from as_userinfo where id =#{id}")
    UserInfoInPersistence getUserInfoByUserId(@Param("id") Long id);
}
