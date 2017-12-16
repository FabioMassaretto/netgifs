package br.com.fiap.controller.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryVO implements Serializable {

    private String name;

    private List<GifVO> gifs;

    public CategoryVO(String name) {
        this.name = name;
        gifs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GifVO> getGifs() {
        return gifs;
    }

    public void setGifs(List<GifVO> gifs) {
        this.gifs = gifs;
    }
}
