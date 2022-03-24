package com.yuyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuyun.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<Users> {

    List<Users> getUsersByUsrNameAndPwd(@Param("usrName")String usrName, @Param("usrPwd") String usrPwd);
}
