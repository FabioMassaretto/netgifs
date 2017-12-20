package br.com.fiap.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "GIF")
public class Gif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GIF_ID")
    private Integer id;

    @Column(name = "PATH")
    private String path;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ETA_RATING")
    private Integer etaRating;

    @Column(name = "LANGUAGE")
    private String language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEtaRating() {
        return etaRating;
    }

    public void setEtaRating(Integer etaRating) {
        this.etaRating = etaRating;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gif gif = (Gif) o;
        return Objects.equals(id, gif.id) &&
                Objects.equals(name, gif.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
