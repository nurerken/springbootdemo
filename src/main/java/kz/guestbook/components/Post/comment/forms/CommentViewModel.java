package kz.guestbook.components.Post.comment.forms;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CommentViewModel {
    private String commentText;
    private String dateString;
    private String userFullName;
}