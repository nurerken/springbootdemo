package kz.guestbook.components.Post.repository;

import kz.guestbook.components.Post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by nurlan on 7/12/17.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    public Post getById(Long id);

    @Query("select p from Post p where p.passedModeration = :passedModeration order by p.id desc")
    public List<Post> findAllPosts(@Param("passedModeration") boolean passedModeration);

    public List<Post> getByText(String text);

    @Query("select post from Post post where post.text like %:text%")
    public List<Post> findPostByTextLike(@Param("text") String text);

    @Query("select p from Post p where p.passedModeration = true and (lower(p.text) like concat('%', lower(:searchString), '%') or lower(p.user.firstName) like concat('%', lower(:searchString), '%') or lower(p.user.lastName) like concat('%', lower(:searchString), '%'))")
    public List<Post> findByTextOrAuthor(@Param("searchString") String searchString);

    @Modifying
    @Transactional
    @Query("update Post p set p.passedModeration = :passedModeration where p.id = :id")
    public Integer setPassedModeration(@Param("id") Long id,@Param("passedModeration") boolean passedModeration);

    @Modifying
    @Transactional
    @Query("update Post set text = :text where id = :id")
    public void updateText(@Param("text") String text, @Param("id") Long id);
}
