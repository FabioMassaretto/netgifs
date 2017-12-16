package br.com.fiap.repository;

import br.com.fiap.entity.Role;
import br.com.fiap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by logonrm on 12/12/2017.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    List<User> findByRole(Role role);

}
