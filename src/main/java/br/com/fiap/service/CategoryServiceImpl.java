package br.com.fiap.service;

import br.com.fiap.entity.Category;
import br.com.fiap.repository.CategoryRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by logonrm on 12/12/2017.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findAllWithGifs() {
        List<Category> categories = categoryRepository.findAll();
        for(Category category: categories) {
            Hibernate.initialize(category.getGifs());
        }
        return categories;
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}
