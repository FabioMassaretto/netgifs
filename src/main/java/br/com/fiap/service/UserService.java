package br.com.fiap.service;

import br.com.fiap.entity.User;

import java.util.List;

/**
 * Created by logonrm on 12/12/2017.
 */
public interface UserService {

    List<User> listAll();

    User findUserByEmail(String email);

    User saveUser(User user);

    User saveAdmin(User user);

    void startRoles();

    void giveAdminPrevileges(User user);

}
