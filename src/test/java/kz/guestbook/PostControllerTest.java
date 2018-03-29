package kz.guestbook;

import kz.guestbook.components.Post.comment.service.CommentService;
import kz.guestbook.components.Post.model.Post;
import kz.guestbook.components.Post.service.PostService;
import kz.guestbook.components.User.service.UserService;
import kz.guestbook.controllers.post.PostController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;

    @MockBean
    private UserService userService;

    @MockBean
    private CommentService commentService;

    @Before
    public void setUp() {
        Post post = new Post();
        post.setText("hello");
        List<Post> allPosts = Arrays.asList(post);
        Mockito.when(postService.getAllPosts()).thenReturn(allPosts);
    }

    @Test
    public void givenPosts_whenGetPosts_thenReturnJsonArray() throws Exception {

        Post post = new Post();
        post.setText("hello");
        List<Post> allPosts = Arrays.asList(post);
        given(postService.getAllPosts()).willReturn(allPosts);

        /*mvc.perform(get("/postsJson")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].text", is("hello")));*/
    }
}