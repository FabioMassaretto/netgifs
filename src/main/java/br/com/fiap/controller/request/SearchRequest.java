package br.com.fiap.controller.request;

import java.io.Serializable;

public class SearchRequest implements Serializable {

    private String gifName;

    public String getGifName() {
        return gifName;
    }

    public void setGifName(String gifName) {
        this.gifName = gifName;
    }
}
