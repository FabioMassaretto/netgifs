package br.com.fiap.controller;

import br.com.fiap.controller.request.SearchRequest;
import br.com.fiap.controller.response.CategoryVO;
import br.com.fiap.controller.response.GifVO;
import br.com.fiap.entity.Category;
import br.com.fiap.entity.Gif;
import br.com.fiap.entity.User;
import br.com.fiap.service.CategoryService;
import br.com.fiap.service.GifService;
import br.com.fiap.service.StorageService;
import br.com.fiap.storage.StorageFileNotFoundException;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GifController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private GifService gifService;

    @RequestMapping(value = {"/admin/gif"}, method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("gif", new Gif());
        modelAndView.addObject("categories", categoryService.findAll());
        modelAndView.setViewName("admin/gif");
        return modelAndView;
    }

    @RequestMapping(value = {"/user/gif"}, method = RequestMethod.GET)
    public ModelAndView listUploadedFiles() {
        ModelAndView modelAndView = new ModelAndView();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("isAdmin", authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")));

        List<Category> categories = categoryService.findAllWithGifs();
        List<CategoryVO> listCategory = new ArrayList<>();

        categories.forEach(category -> {
            CategoryVO vo = new CategoryVO(category.getName());
            category.getGifs().forEach(gif ->
                    vo.getGifs().add(new GifVO(MvcUriComponentsBuilder.fromMethodName(GifController.class,
                            "serveFile", gif.getPath()).build().toString(), gif.getName().toString(), gif.getDescription().toString()))
            );

            listCategory.add(vo);
        });

        modelAndView.addObject("categories", listCategory);
        modelAndView.setViewName("user/gif");
        return modelAndView;
    }

    @RequestMapping(value = {"/user/gif/search"}, method = RequestMethod.POST)
    public ModelAndView findGifsByName(@RequestBody final SearchRequest searchRequest) {
        ModelAndView modelAndView = new ModelAndView();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("isAdmin", authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")));

        final List<Gif> gifs = gifService.findByNameContaining(searchRequest.getGifName());


        List<GifVO> gifVOList = gifs.stream().map(gif -> new GifVO(MvcUriComponentsBuilder.fromMethodName(GifController.class,
                "serveFile", gif.getPath()).build().toString(), gif.getName().toString(), gif.getDescription().toString())).collect(Collectors.toList());

        modelAndView.addObject("gifs", gifVOList);
        modelAndView.setViewName("user/gif_search");
        return modelAndView;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @RequestMapping(value = {"/admin/gif"}, method = RequestMethod.POST)
    public ModelAndView save(@RequestParam("file") MultipartFile file, @Valid Gif gif) {

        gif.setPath(storageService.store(file, gif.getName()));
        gifService.save(gif);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
