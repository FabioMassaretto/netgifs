package br.com.fiap.service;

import br.com.fiap.entity.Role;
import br.com.fiap.entity.User;
import br.com.fiap.repository.RoleRepository;
import br.com.fiap.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Created by logonrm on 12/12/2017.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<User> listAll(){
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        Hibernate.initialize(user.getFavorites());
        return user;
    }

    @Override
    public User saveUser(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);

        Role userRole = roleRepository.findByRole("USER");
        if(user.getRoles() != null) {
            user.getRoles().add(userRole);
        } else {
            user.setRoles(Collections.singletonList(userRole));
        }

        return userRepository.save(user);
    }

    public void startRoles() {

        Role userRole = roleRepository.findByRole("USER");
        Role userRoleAdm = roleRepository.findByRole("ADMIN");

        if(userRoleAdm == null){
            userRoleAdm = roleRepository.save(new Role("ADMIN"));
        }

        if (userRole == null) {
           roleRepository.save(new Role("USER"));
        }

        List<User> users = userRepository.findByRoles(userRoleAdm);

        if(users == null || users.isEmpty()){
            saveAdmin(new User("admin@admin.com", "admin", "admin", true, null));
        }

    }

    @Override
    public User saveAdmin(User user) {
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(Collections.singletonList(userRole));
        return saveUser(user);
    }

    @Override
    @Transactional
    public void giveAdminPrevileges(User user) {
        User userBD = userRepository.findOne(user.getId());
        userBD.getRoles().add(roleRepository.findByRole("ADMIN"));
        userRepository.save(userBD);
    }

}
