package kz.guestbook;

import kz.guestbook.components.Post.model.Post;
import kz.guestbook.components.Post.repository.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class PostRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void whenFindByName_thenReturnPosts(){
        // given
        Post post = new Post();
        post.setText("hello world!");
        testEntityManager.persist(post);
        testEntityManager.flush();

        // when
        List<Post> postsFound = postRepository.findPostByTextLike("hello world!");

        // then
        assertNotNull(postsFound);
        assertEquals(1, postsFound.size());
        assertTrue(postsFound.get(0).getText().equals("hello world!"));
    }
}
