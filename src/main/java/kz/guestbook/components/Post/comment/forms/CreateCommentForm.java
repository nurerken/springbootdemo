package kz.guestbook.components.Post.comment.forms;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateCommentForm {
    @NotNull(message = "Comment cannot be empty")
    @NotEmpty(message = "Comment cannot be empty")
    @Length(min=1, message = "Comment cannot be empty")
    private String comment;

    @NotNull(message = "Comment should belong to post")
    private Long postId;
}
