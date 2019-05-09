package com.ydzx.dao;

import com.ydzx.Utils.MyBatisUtils;
import com.ydzx.beans.Student;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// dao类实现可以省略，直接使用mapper解决
public class StudentDaoImpl implements IStudentDao {
    private SqlSession sqlSession;

    // 直接插入元素
    @Override
    public void insertStudent(Student student) {
        try {
            //// 加载主配置文件
            //InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
            //// 创建SqlSessionFactory对象
            //SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            // 创建SqlSession对象

            // 使用工具类创建
            sqlSession = MyBatisUtils.getSqlSessionFactory();
            // 相关操作,映射文件mapper的ID
            sqlSession.insert("insertStudent", student);
            // 最后必须提交才会发送到数据库
            //sqlSession.commit();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }

    }

    // 获取插入元素的id
    @Override
    public void insertStudentCacheId(Student student) {
        try {
            sqlSession = MyBatisUtils.getSqlSessionFactory();
            sqlSession.insert("insertStudentCacheId", student);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void deleteStudentById(int id) {
        try {
            sqlSession = MyBatisUtils.getSqlSessionFactory();
            sqlSession.delete("deleteStudentById", id);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Override
    public List<Student> dynamicSelectStudentByCondition(Student student) {
        return null;
    }

    @Override
    public List<Student> dynamicSelectStudentByForEach(int[] ints) {
        return null;
    }

    @Override
    public List<Student> dynamicSelectStudentByChoose(Student student) {
        return null;
    }

    @Override
    public List<Student> dynamicSelectStudentByForEachList(List<Integer> list) {
        return null;
    }

    @Override
    public List<Student> dynamicSelectStudentByForEachGeneric(List<Student> list) {
        return null;
    }

    @Override
    public void updateStudent(Student student) {
        try {
            sqlSession = MyBatisUtils.getSqlSessionFactory();
            sqlSession.update("updateStudent", student);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Override
    public List<Student> selectAllStudents() {
        List<Student> students = null;
        try {
            sqlSession = MyBatisUtils.getSqlSessionFactory();
            students = sqlSession.selectList("selectAllStudents");
            // 查询不做修改，可以不用commit提交事务
            //sqlSession.commit();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return students;
    }

    @Override
    public List<Student> selectStudentsByCondition(Map<String, Object> map) {
        return null;
    }

    // map查询比较特殊，相当于先查一遍allList，然后将每个对应的值赋值一个key
    // 所以sqlSession.selectMap中的mapKey最好为数据的primary key，否则在输出时会覆盖值
    @Override
    public Map<Integer, Object> selectAllStudentsMap() {
        Map<Integer,Object> map = new HashMap<>();
        try {
            sqlSession = MyBatisUtils.getSqlSessionFactory();
            map = sqlSession.selectMap("selectAllStudents","t_id");
        }finally {
            if (sqlSession != null){
                sqlSession.close();
            }
        }
        return map;
    }

    @Override
    public Student selectStudentById(int id) {
        Student student = null;
        try {
            sqlSession = MyBatisUtils.getSqlSessionFactory();
            student = sqlSession.selectOne("selectStudentById",id);
        }finally {
            if (sqlSession != null){
                sqlSession.close();
            }
        }
        return student;
    }

    @Override
    public List<Student> selectStudentsByName(String name) {
        List<Student> list = null;
        try {
            sqlSession = MyBatisUtils.getSqlSessionFactory();
            list = sqlSession.selectList("selectStudentsByName",name);
        }finally {
            if (sqlSession != null){
                sqlSession.close();
            }
        }
        return list;
    }
}
