package kz.guestbook.components.Post.repository;

import kz.guestbook.components.Post.forms.SearchPostForm;
import kz.guestbook.components.Post.model.Post;
import kz.guestbook.components.User.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class PostSpecification implements Specification<Post> {

    private SearchPostForm searchPostForm;

    public PostSpecification(SearchPostForm searchPostForm) {
        this.searchPostForm = searchPostForm;
    }

    @Override
    public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Predicate p = cb.conjunction();

        if (searchPostForm.getId() != null) {
            p.getExpressions().add(cb.equal(root.get("id"), searchPostForm.getId()));
        }

        if (searchPostForm.getText() != null && !searchPostForm.getText().isEmpty()) {
            p.getExpressions().add(cb.or(cb.like(root.get("text"), "%" + searchPostForm.getText() + "%"), cb.like(root.get("text"), "%" + searchPostForm.getText() + "%")));
        }

        if (searchPostForm.getUserId() != null && searchPostForm.getUserId() > 0) {
            Join<Post, User> userJoin = root.join("user");
            p.getExpressions().add(cb.equal(userJoin.get("id"), searchPostForm.getUserId()));
        }

        if (searchPostForm.getUserName() != null && !searchPostForm.getUserName().isEmpty()) {
            Join<Post, User> userJoin = root.join("user");
            p.getExpressions().add(cb.equal(cb.lower(userJoin.<String>get("name")), searchPostForm.getUserName()));
        }

        return p;
    }

}
