package com.soolaugust.test;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.soolaugust.inter.UserOperation;
import com.soolaugust.model.User;

public class Test {
   private static SqlSessionFactory sqlSessionFactory;
   private static Reader reader;
   
   static{
      try{
         reader = Resources.getResourceAsReader("Configuration.xml");
         sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
      }catch(Exception e){
         e.printStackTrace();
      }
   }
   
   public static SqlSessionFactory getSession(){
      return sqlSessionFactory;
   }
   
   public void getUserList(String userName){
      SqlSession session = sqlSessionFactory.openSession();
      try{
         UserOperation userOperation = session.getMapper(UserOperation.class);
         List<User> users = userOperation.selectUsers(userName);
         for(User user:users){
            System.out.println(user.getId()+":"+user.getUserName()+":"+user.getUserAddress());
         }
      }finally{
         session.close();
      }
   }
   
   public static void main(String[] args){
      Test testUser = new Test();
      testUser.getUserList("%");
   }
}
