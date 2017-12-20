package br.com.fiap.service;

import br.com.fiap.controller.response.GifVO;
import br.com.fiap.entity.Category;
import br.com.fiap.entity.Gif;

import java.util.List;

public interface GifService {

    List<Gif> findAll();

    Gif save(Gif gif);

    List<Gif> findGifByCategory(Category category);

    void saveGifToFavorite(GifVO gifVO, String emailLogged);

    void removeGifToFavorite(GifVO gifVO, String emailLogged);

    List<Gif> findByNameContaining(String gifName);
}
