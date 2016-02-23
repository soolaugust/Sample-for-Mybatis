package com.soolaugust.test;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.soolaugust.inter.UserOperation;
import com.soolaugust.model.Article;
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
   
   public void addUser(){
      User user = new User();
      user.setUserAddress("People Square");
      user.setUserName("autumn");
      user.setUserAge(20);
      SqlSession session = sqlSessionFactory.openSession();
      try{
         UserOperation userOperation = session.getMapper(UserOperation.class);
         userOperation.addUser(user);
         session.commit();
         System.out.println("The id of new user is " + user.getId());
      }finally{
         session.close();
      }
   }
   
   public void updateUser(){
      SqlSession session = sqlSessionFactory.openSession();
      try{
         UserOperation userOperation = session.getMapper(UserOperation.class);
         User user = userOperation.selectUserByID(4);
         user.setUserAddress("Beijing,sanlitun");
         userOperation.updateUser(user);
         session.commit();
      }finally{
         session.close();
      }
   }
   
   public void deleteUser(int id){
      SqlSession session = sqlSessionFactory.openSession();
      try{
         UserOperation userOperation = session.getMapper(UserOperation.class);
         userOperation.deleteUser(id);
         session.commit();
      }finally{
         session.close();
      }
   }
   
   public void getUserArticles(int userid){
      SqlSession session = sqlSessionFactory.openSession();
      try{
         UserOperation userOperation = session.getMapper(UserOperation.class);
         List<Article> articles = userOperation.getUserArticles(userid);
         for(Article article:articles){
            System.out.println(article.getTitle() + ":" + article.getContent() +
                  ":Author is " + article.getUser().getUserName() + ":Address is " +
                  article.getUser().getUserAddress());
         }
      }finally{
         session.close();
      }
   }
   
   public static void main(String[] args){
      Test testUser = new Test();
//      testUser.addUser();
//      testUser.updateUser();
//      testUser.deleteUser(3);
//      testUser.getUserList("%");
      testUser.getUserArticles(1);
   }
}
