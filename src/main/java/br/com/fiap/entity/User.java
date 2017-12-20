package br.com.fiap.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

/**
 * Created by logonrm on 12/12/2017.
 */
@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Integer id;

    @Column(name = "EMAIL")
    @Email(message = "*Informe um e-mail válido")
    private String email;

    @Column(name = "PASSWORD")
    @NotEmpty(message = "*Informe uma senha")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "active")
    private boolean active;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<Role> roles;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "USER_FAVORITE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "GIF_ID"))
    private List<Gif> favorites;

    public User(){}

    public User(String email, String password, String name, boolean active, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.active = active;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
