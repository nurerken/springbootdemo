package kz.guestbook.components.Post.forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchPostForm {
    private Integer id;

    private String text;

    private Integer userId;
    private String userName;

    private int startPage;
    private int size;

    private String sortColumn;
    private int sortDirection;

}
