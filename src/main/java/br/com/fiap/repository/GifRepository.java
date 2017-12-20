package br.com.fiap.repository;

import br.com.fiap.entity.Category;
import br.com.fiap.entity.Gif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GifRepository extends JpaRepository<Gif, Integer> {

    List<Gif> findByCategory(Category category);

    List<Gif> findByNameContaining(String name);

}
