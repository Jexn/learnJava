package com.ydzx.dao;

import com.ydzx.beans.Student;

import java.util.List;
import java.util.Map;

public interface IStudentDao {
    void insertStudent(Student student);
    void insertStudentCacheId(Student student);

    void deleteStudentById(int id);
    void updateStudent(Student student);

    List<Student> selectAllStudents();
    Map<Integer,Object> selectAllStudentsMap();

    Student selectStudentById(int id);
    List<Student> selectStudentsByName(String name);
    List<Student> selectStudentsByCondition(Map<String,Object> map);

    // 动态SQL
    List<Student> dynamicSelectStudentByCondition(Student student);
    List<Student> dynamicSelectStudentByChoose(Student student);
    List<Student> dynamicSelectStudentByForEach(int [] ints);
    List<Student> dynamicSelectStudentByForEachList(List<Integer> list);
    List<Student> dynamicSelectStudentByForEachGeneric(List<Student> list);
}
