package br.com.fiap.service;

import br.com.fiap.entity.Category;
import sun.util.resources.cldr.to.CalendarData_to_TO;

import java.util.List;

/**
 * Created by logonrm on 12/12/2017.
 */
public interface CategoryService {

    List<Category> findAll();

    Category save(Category category);

}
