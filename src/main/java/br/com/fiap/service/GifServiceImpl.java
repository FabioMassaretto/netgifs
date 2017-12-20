package br.com.fiap.service;

import br.com.fiap.controller.response.GifVO;
import br.com.fiap.entity.Category;
import br.com.fiap.entity.Gif;
import br.com.fiap.entity.User;
import br.com.fiap.repository.GifRepository;
import br.com.fiap.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GifServiceImpl implements GifService {

    @Autowired
    private GifRepository gifRepository;

    @Autowired
    private UserRepository userRepository;

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

    @Override
    @Transactional
    public void saveGifToFavorite(GifVO gifVO, String emailLogged) {
        Gif gif = gifRepository.findByName(gifVO.getName());

        User user = userRepository.findByEmail(emailLogged);
        Hibernate.initialize(user.getFavorites());

        if(user.getFavorites() == null){
            user.setFavorites(new ArrayList<>());
        }

        user.getFavorites().add(gif);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeGifToFavorite(GifVO gifVO, String emailLogged) {
        Gif gif = gifRepository.findByName(gifVO.getName());

        User user = userRepository.findByEmail(emailLogged);
        Hibernate.initialize(user.getFavorites());

        user.getFavorites().remove(gif);

        userRepository.save(user);

    }

    @Override
    public List<Gif> findByNameContaining(String gifName) {
        return gifRepository.findByNameContaining(gifName);
    }
}
