package br.com.fiap.controller;

import br.com.fiap.controller.response.CategoryVO;
import br.com.fiap.controller.response.GifVO;
import br.com.fiap.entity.Category;
import br.com.fiap.entity.Gif;
import br.com.fiap.entity.User;
import br.com.fiap.service.CategoryService;
import br.com.fiap.service.GifService;
import br.com.fiap.service.StorageService;
import br.com.fiap.service.UserService;
import br.com.fiap.storage.StorageFileNotFoundException;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GifController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private GifService gifService;

    @Autowired
    private UserService userService;

    @RequestMapping(value={"/admin/gif"}, method = RequestMethod.GET)
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("gif", new Gif());
        modelAndView.addObject("categories", categoryService.findAll());
        modelAndView.setViewName("admin/gif");
        return modelAndView;
    }

    @RequestMapping(value={"/user/gif"}, method = RequestMethod.GET)
    public ModelAndView listUploadedFiles(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(session.getAttribute("user") == null) {
            User user = userService.findUserByEmail(authentication.getName());
            session.setAttribute("user", user);
            session.setAttribute("isAdmin", authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")));

            if(user.getFavorites() != null && !user.getFavorites().isEmpty()){
                List<GifVO> gifVOS = new ArrayList<>();
                user.getFavorites().forEach(favorite -> {
                    gifVOS.add(new GifVO(MvcUriComponentsBuilder.fromMethodName(GifController.class,
                            "serveFile", favorite.getPath()).build().toString(), favorite.getName(), favorite.getDescription()));
                });
                session.setAttribute("favorites", gifVOS);
            }

        }

        List<Category> categories = categoryService.findAllWithGifs();
        List<CategoryVO> listCategory = new ArrayList<>();

        categories.forEach(category -> {
            CategoryVO vo = new CategoryVO(category.getName());
            category.getGifs().forEach(gif ->
                vo.getGifs().add(new GifVO(MvcUriComponentsBuilder.fromMethodName(GifController.class,
                        "serveFile", gif.getPath()).build().toString(), gif.getName(), gif.getDescription()))
            );

            listCategory.add(vo);
        });

        modelAndView.addObject("categories", listCategory);
        modelAndView.setViewName("user/gif");
        return modelAndView;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @RequestMapping(value={"/admin/gif"}, method = RequestMethod.POST)
    public ModelAndView save(@RequestParam("file") MultipartFile file, @Valid Gif gif){

        gif.setPath(storageService.store(file, gif.getName()));
        gifService.save(gif);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @RequestMapping(value={"/user/favorite"}, method = RequestMethod.POST)
    public ModelAndView saveToFavorite(@RequestParam String url, @RequestParam String name, @RequestParam(required = false) String description, HttpSession session){

        GifVO gif = new GifVO(url, name, description);

        Object object = session.getAttribute("favorites");
        List<GifVO> gifs = new ArrayList<>();

        if(object != null){
            gifs = (List<GifVO>) object;
        }

        gifService.saveGifToFavorite(gif, ((User) session.getAttribute("user")).getEmail());

        gifs.add(gif);
        session.setAttribute("favorites", gifs);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/user/gif");
        return modelAndView;

    }

    @RequestMapping(value={"/user/delete/favorite"}, method = RequestMethod.POST)
    public ModelAndView removeToFavorite(@RequestParam String name, HttpSession session){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/user/gif");

        List<GifVO> gifs = (List<GifVO>) session.getAttribute("favorites");

        GifVO gif = new GifVO(name);
        gifs.remove(gif);
        session.setAttribute("favorites", gifs);

        gifService.removeGifToFavorite(gif, ((User) session.getAttribute("user")).getEmail());

        return modelAndView;

    }

    @RequestMapping(value={"/user/favorite"}, method = RequestMethod.GET)
    public ModelAndView listFavorites() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/favorites");
        return modelAndView;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
