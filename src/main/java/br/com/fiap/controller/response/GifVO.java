package br.com.fiap.controller.response;

import java.util.Objects;

public class GifVO {

    private String url;

    private String name;

    private String description;

    public GifVO(String url, String name, String description ) { this.url = url; this.name = name; this.description = description; }

    public GifVO(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GifVO gifVO = (GifVO) o;
        return Objects.equals(name, gifVO.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
