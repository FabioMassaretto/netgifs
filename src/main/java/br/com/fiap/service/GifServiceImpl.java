package br.com.fiap.service;

import br.com.fiap.entity.Category;
import br.com.fiap.entity.Gif;
import br.com.fiap.repository.GifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GifServiceImpl implements GifService {

    @Autowired
    private GifRepository gifRepository;

    private final StorageService storageService;

    @Autowired
    public GifServiceImpl(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public List<Gif> findAll() {
        return gifRepository.findAll();
    }

    @Override
    public Gif save(Gif gif) {
        return gifRepository.save(gif);
    }

    @Override
    public List<Gif> findGifByCategory(Category category) {
        return gifRepository.findByCategory(category);
    }

}
