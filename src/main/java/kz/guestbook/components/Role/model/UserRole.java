package kz.guestbook.components.Role.model;

import javax.persistence.*;

@Entity
@Table(name="user_roles")
public class UserRole {
    private Long id;
    private String name;
    private String description;

    public static final String roleAdmin = "admin";
    public static final String roleModerator = "moderator";

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
