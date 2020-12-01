package oit.is.chang6.channel6.model;

//import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WordMapper {
  @Select("SELECT * from word where id = #{id}")
  Word selectById(int id);

  @Insert("INSERT INTO word (id, room, user,word) VALUES (#{id}, #{room},#{user},#{word});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertWord(Word word);

}
