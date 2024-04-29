package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.News;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Select;

public interface NewsMapper extends BaseMapper<News> {
    User selectByNameUser(String name);
//    @Insert("insert into `user` (name, password, phone, title)" +
//            "values (#{name}, #{password}, #{phone}, #{title})")
//    void insertUser(User user);
//
//    @Update("update `user` set name = #{name}, password = #{password}, phone = #{phone}, email = #{email}, title = #{title} where id = #{id}")
//    void updateUser(User user);
//
//    @Delete("delete from `user` where id = #{id}")
//    void deleteUser(Integer id);
//
//    //无用
//    @Delete(("delete from `user` where id in #{ids}"))
//    void batchDeleteUser(List<Integer> ids);
//
//    @Select("select * from `user` order by id desc")
//    List<User> selectUser();
//
    @Select("select * from `user` where id = #{id}")
    User selectByIdUser(Integer id);
//
//    @Select("select * from `user` where id = #{id} and name = #{name}")
//    User selectMoreUser(@Param("id") Integer id, @Param("name") String name);
//
//    @Select("select * from `user` where name like concat('%',#{name},'%')")
//    List<User> selectMo(String name);
//
//    @Select("select * from `user` where name like concat('%',#{name},'%') order by id desc limit #{skipNum}, #{pageSize}")
//    List<User> selectByPage(@Param("skipNum") Integer skipNum, @Param("pageSize") Integer pageSize, @Param("name") String name);
//
//    @Select("select * from `user` where name = #{name}")
//    User selectByNameUser(String name);

}
