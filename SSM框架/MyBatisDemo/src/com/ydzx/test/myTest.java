package com.ydzx.test;

import com.ydzx.Utils.MyBatisUtils;
import com.ydzx.beans.Student;
import com.ydzx.dao.IStudentDao;
import com.ydzx.dao.StudentDaoImpl;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class myTest {
    private IStudentDao dao;
    private SqlSession sqlSession;

    @Before
    public void before(){
        // 可以直接使用getMapper()获取
        //dao = new StudentDaoImpl();

        // Mapper动态代理模式
        sqlSession = MyBatisUtils.getSqlSessionFactory();
        dao = sqlSession.getMapper(IStudentDao.class);
    }

    @Test
    public void testInsert01(){
        Student student = new Student("张三",23,94.5);
        System.out.println("插入前：student = " + student);
        dao.insertStudentCacheId(student);
        System.out.println("插入后：student = " + student);
    }

    @Test
    public void testInsert02(){
        Student student = new Student("李四",25,95);
        System.out.println("插入前：student = " + student);
        dao.insertStudentCacheId(student);
        System.out.println("插入后：student = " + student);
    }

    @Test
    public void testDeleteTest(){
        dao.deleteStudentById(32);
    }

    @Test
    public void updateStudentTest(){
        Student student = new Student("王五",21,90);
        student.setId(31);
        dao.updateStudent(student);
    }

    @Test
    public void selectAllStudentsTest(){
        List<Student> students = dao.selectAllStudents();
        if (students.size() > 0){
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    // 因为使用的是selectAllStudents，所以无法找到响应的代理
    //@Test
    //public void selectAllStudentsMapTest(){
    //    Map<Integer,Object> map = dao.selectAllStudentsMap();
    //    System.out.println(map.get(30));
    //}

    @Test
    public void selectStudentByIdTest(){
        Student student = dao.selectStudentById(33);
        System.out.println(student);
    }

    @Test
    public void selectStudentsByName(){
        List<Student> students = dao.selectStudentsByName("李");
        if (students.size() > 0){
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    @Test
    public void selectStudentsByCondition(){
        Student stu = new Student("田七",24,95);
        Map<String,Object> map = new HashMap<>();
        map.put("nameCon","张");
        map.put("ageCon",22);
        map.put("stu",stu);

        List<Student> students = dao.selectStudentsByCondition(map);
        for (Student student : students) {
            System.out.println(student);
        }
    }

    @Test
    public void dynamicSelectStudentByCondition(){
        Student stu = new Student("",24,0);
        List<Student> students = dao.dynamicSelectStudentByCondition(stu);
        for (Student student : students) {
            System.out.println(student);
        }
    }

    @Test
    public void dynamicSelectStudentByChoose(){
        Student stu = new Student("",23,0);
        List<Student> students = dao.dynamicSelectStudentByChoose(stu);
        for (Student student : students) {
            System.out.println(student);
        }
    }

    @Test
    public void dynamicSelectStudentByForEach(){
        int[] ints = {1,3,5,7,9};
        List<Student> students = dao.dynamicSelectStudentByForEach(ints);
        for (Student student : students) {
            System.out.println(student);
        }
    }

    @Test
    public void dynamicSelectStudentByForEachList(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(5);
        list.add(12);
        List<Student> students = dao.dynamicSelectStudentByForEachList(list);
        for (Student student : students) {
            System.out.println(student);
        }
    }

    @Test
    public void dynamicSelectStudentByForEachGeneric(){
        List<Student> list = new ArrayList<>();
        Student stu1 = new Student();
        stu1.setId(1);
        Student stu2 = new Student();
        stu2.setId(4);
        Student stu3 = new Student();
        stu3.setId(19);

        list.add(stu1);
        list.add(stu2);
        list.add(stu3);

        List<Student> students = dao.dynamicSelectStudentByForEachGeneric(list);
        for (Student student : students) {
            System.out.println(student);

        }
    }

    @After
    public void auto_commit(){
        sqlSession.commit();
        if (sqlSession != null){
            sqlSession.close();
        }
    }
}
