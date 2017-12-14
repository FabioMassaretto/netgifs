package br.com.fiap.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by logonrm on 12/12/2017.
 */
@Entity
@Table(name = "CATEGORY")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Integer id;

    @Column(name = "NAME")
    @NotEmpty(message = "*Please provide a name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "category")
    private List<Gif> gifs;

    public Category(){}

    public Category(Integer id, String name) {
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Gif> getGifs() {
        return gifs;
    }

    public void setGifs(List<Gif> gifs) {
        this.gifs = gifs;
    }
}
