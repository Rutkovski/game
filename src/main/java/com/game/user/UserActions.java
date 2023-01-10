package com.game.user;

import java.util.List;

public interface UserActions {
    void addUser(User user);
    void deleteUser(Long id);
    List<User> getUserList();
    void updateUser(User user);


}
