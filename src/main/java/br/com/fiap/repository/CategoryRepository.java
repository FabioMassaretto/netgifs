package br.com.fiap.repository;

import br.com.fiap.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by logonrm on 12/12/2017.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
