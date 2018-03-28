package kz.guestbook;

import kz.guestbook.components.Post.model.Post;
import kz.guestbook.components.Post.repository.PostRepository;
import kz.guestbook.components.Post.service.PostService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class PostServiceTest {
    @TestConfiguration
    static class PostServiceTestContextConfiguration {

        @Bean
        public PostService postService() {
            return new PostService();
        }
    }

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @Before
    public void setUp() {
        List<Post> posts = new ArrayList<>();
        Post post = new Post();
        post.setText("hello");
        posts.add(post);
        Post post2 = new Post();
        post2.setText("hello world");
        posts.add(post2);

        Mockito.when(postRepository.findPostByTextLike("hello")).thenReturn(posts);
    }

    @Test
    public void whenValidText_thenPostsShouldBeFound() {
        String text = "hello";
        List<Post> posts= postService.getByTextLike(text);

        assertNotNull(posts);
        assertEquals(2, posts.size());
        assertTrue(posts.get(0).getText().equals(text));
    }
}