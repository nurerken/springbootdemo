package kz.guestbook.components.Post.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreatePostForm {
    @NotNull
    @Size(min=10, max=100, message = "Post length must be at least 10")
    private String text;
}
