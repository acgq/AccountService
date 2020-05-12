package com.github.accounting.dao.mapper;

import com.github.accounting.model.persistence.UserInfoInPersistence;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {

    @Select("select id, username,password,salt, create_time,update_time from as_userinfo where id =#{id}")
    UserInfoInPersistence getUserInfoByUserId(@Param("id") Long id);

    @Select("select id, username, password, salt, create_time, update_time " +
            "from as_userinfo where username =#{username}")
    UserInfoInPersistence getUserInfoByUsername(@Param("username") String username);

    @Insert("INSERT into as_userinfo (username,password,salt,create_time)"
            + " VALUES (#{username},#{password},#{salt},#{createTime})")
    void createUserInfo(UserInfoInPersistence userInfo);
}
