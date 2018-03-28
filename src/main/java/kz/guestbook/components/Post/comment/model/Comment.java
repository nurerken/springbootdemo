package kz.guestbook.components.Post.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.guestbook.components.Post.model.Post;
import kz.guestbook.components.User.model.User;

import javax.persistence.*;
import java.util.Date;

/**
 * CREATE TABLE public.comments  (
     id               	serial NOT NULL,
     text             	varchar(2000) NOT NULL,
     date             	date NOT NULL DEFAULT now(),
     user_id           	int4 NOT NULL DEFAULT 0,
     post_id           	int4 NOT NULL DEFAULT 0,
     PRIMARY KEY(id)
     )
 */
@Entity
@Table(name="comments")
public class Comment {
    private Long   id;
    private String text;
    private Date   date;
    private User   user;
    private Post   post;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }
}
