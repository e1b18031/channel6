package oit.is.chang6.channel6.model;

//import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoomsMapper {
  @Select("SELECT id from rooms where id = #{id}")
  Users selectById(int id);
}
