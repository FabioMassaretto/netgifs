package br.com.fiap.controller;

import br.com.fiap.entity.Category;
import br.com.fiap.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value={"/admin/category"}, method = RequestMethod.GET)
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("category", new Category());
        modelAndView.setViewName("admin/category");
        return modelAndView;
    }

    @RequestMapping(value={"/admin/category"}, method = RequestMethod.POST)
    public ModelAndView save(Category category){
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

}
