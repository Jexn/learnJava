package com.ydzx.Utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtils {
    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSession getSqlSessionFactory() {
        try {
            InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
            if (sqlSessionFactory == null){
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            }
            // openSession()方法默认为false，则需要自己主动使用sqlSession.commit()提交，openSession(true)则不需要sqlSession.commit()提交
            return sqlSessionFactory.openSession(true);
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
