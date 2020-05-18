package com.github.accounting.dao.mapper;

import com.github.accounting.model.persistence.UserInfoInPersistence;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserInfoMapper {

    @Select("select id, username,password,salt, create_time,update_time from as_userinfo where id =#{id}")
    UserInfoInPersistence getUserInfoByUserId(@Param("id") Long id);

    @Select("select id, username, password, salt, create_time, update_time "
            + "from as_userinfo where username =#{username}")
    UserInfoInPersistence getUserInfoByUsername(@Param("username") String username);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT into as_userinfo (username,password,salt,create_time)"
            + " VALUES (#{username},#{password},#{salt},#{createTime})")
    int createUserInfo(UserInfoInPersistence userInfo);
}
