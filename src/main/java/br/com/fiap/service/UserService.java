package br.com.fiap.service;

import br.com.fiap.entity.User;

/**
 * Created by logonrm on 12/12/2017.
 */
public interface UserService {

    User findUserByEmail(String email);

    User saveUser(User user);

    User saveAdmin(User user);

    void startRoles();

}
