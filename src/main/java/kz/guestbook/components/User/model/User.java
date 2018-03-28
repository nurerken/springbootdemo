package kz.guestbook.components.User.model;

import kz.guestbook.components.Role.model.UserRole;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Boolean active;

    private List<UserRole> roles;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name="firstname")
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name="lastname")
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name="active")
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name = "user_role_links",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Cascade({
            org.hibernate.annotations.CascadeType.SAVE_UPDATE
    })
    //@SQLInsert(callable = true, check = ResultCheckStyle.NONE, sql = "{call comp_empl_roles_add_join_sm(?, ?)}")
    //@SQLDeleteAll(callable = true, check = ResultCheckStyle.NONE, sql = "{call comp_empl_roles_join_delete(?)}")
    public List<UserRole> getRoles() {
        return roles;
    }
    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}
