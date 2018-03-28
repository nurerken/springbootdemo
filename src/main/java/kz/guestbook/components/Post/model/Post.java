package kz.guestbook.components.Post.model;

import kz.guestbook.components.Post.comment.model.Comment;
import kz.guestbook.components.User.model.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="posts")
public class Post {

    private Long id;
    private String text;
    private Date date;
    private User user;
    private Boolean passedModeration;
    private List<Comment> comments;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userID")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Column(name="passed_moderation")
    public Boolean getPassedModeration() {
        return passedModeration;
    }
    public void setPassedModeration(Boolean passedModeration) {
        this.passedModeration = passedModeration;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
