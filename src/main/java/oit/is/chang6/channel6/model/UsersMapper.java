package oit.is.chang6.channel6.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;



@Mapper
public interface UsersMapper {
    @Select("SELECT id,user from users where id = #{id}")
    Users selectById(int id);

    @Select("SELECT id,user from users where user = #{user}")
    Users selectByUser(String user);


    @Select("SELECT * FROM USERS")
    ArrayList<Users> selectAll();

    @Update("UPDATE USERS SET ID=#{id}, USER=#{user}, ROOM=#{room} WHERE ID = #{id}")
    void updateById(Users users);
}