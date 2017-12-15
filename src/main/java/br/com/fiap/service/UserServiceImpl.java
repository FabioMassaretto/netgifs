package br.com.fiap.service;

import br.com.fiap.entity.Role;
import br.com.fiap.entity.User;
import br.com.fiap.repository.RoleRepository;
import br.com.fiap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

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
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
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

        if(userRole != null && userRoleAdm != null){
            return;
        }

        if(userRole == null && userRoleAdm == null){
            roleRepository.save(Arrays.asList(new Role("ADMIN"), new Role("USER")));
        } else if (userRole == null) {
            roleRepository.save(new Role("USER"));
        } else {
            new Role("USER");
        }

    }

    @Override
    public User saveAdmin(User user) {
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(Collections.singletonList(userRole));
        return saveUser(user);
    }

}
