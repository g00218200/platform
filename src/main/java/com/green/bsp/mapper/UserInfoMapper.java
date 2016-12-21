package com.green.bsp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.green.bsp.pojo.UserInfo;

/**
 * @author guorui
 * @since 2016-01-22 22:17
 */
public interface UserInfoMapper{
	
//	@Select("select * from UserInfo where username = #{name}")
//    public List<UserInfo> likeName(String name);
//    
//    @Select("select * from UserInfo where id = #{id}")
//    public UserInfo getById(long id);
//    
//    @Select("select username from UserInfo where id = #{id}")
//    public String getNameById(long id);
    
    public UserInfo findUserInfo();
}
