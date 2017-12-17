package br.com.fiap.controller.response;

public class GifVO {

    private String url;

    private String name;

    private String description;

    public GifVO(String url, String name, String description ) { this.url = url; this.name = name; this.description = description; }

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
}
