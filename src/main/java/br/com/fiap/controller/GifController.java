package br.com.fiap.controller;

import br.com.fiap.entity.Gif;
import br.com.fiap.service.CategoryService;
import br.com.fiap.service.GifService;
import br.com.fiap.service.StorageService;
import br.com.fiap.storage.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class GifController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private GifService gifService;

    @RequestMapping(value={"/admin/gif"}, method = RequestMethod.GET)
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("gif", new Gif());
        modelAndView.addObject("categories", categoryService.findAll());
        modelAndView.setViewName("admin/gif");
        return modelAndView;
    }

    @RequestMapping(value={"/user/gif"}, method = RequestMethod.GET)
    public ModelAndView listUploadedFiles() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(GifController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
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
    public ModelAndView save(@RequestParam("file") MultipartFile file, Gif gif){

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
