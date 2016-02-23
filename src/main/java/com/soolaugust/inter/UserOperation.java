package com.soolaugust.inter;

import java.util.List;

import com.soolaugust.model.User;

public interface UserOperation {
   public User selectUserByID(int id);
   public List<User> selectUsers(String userName);
}
