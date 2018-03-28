package kz.guestbook.components.Post.comment.service;

import kz.guestbook.components.Post.comment.repository.CommentRepository;
import kz.guestbook.components.Post.comment.forms.CreateCommentForm;
import kz.guestbook.components.Post.comment.model.Comment;
import kz.guestbook.components.Post.service.PostService;
import kz.guestbook.components.User.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentsRepository;

    @Autowired
    private PostService postService;

    public Comment create(CreateCommentForm createCommentForm, User user){
        Comment comment = new Comment();
        comment.setText(createCommentForm.getComment());
        comment.setDate(new Date());
        comment.setPost(postService.getByID(createCommentForm.getPostId()));
        comment.setUser(user);

        return commentsRepository.save(comment);
    }
}
