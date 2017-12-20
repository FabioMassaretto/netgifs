package br.com.fiap.service;

import br.com.fiap.entity.Category;
import br.com.fiap.entity.Gif;

import java.util.List;

public interface GifService {

    List<Gif> findAll();

    Gif save(Gif gif);

    List<Gif> findGifByCategory(Category category);

    List<Gif> findByNameContaining(String gifName);
}
