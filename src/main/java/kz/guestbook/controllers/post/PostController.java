package kz.guestbook.controllers.post;

import kz.guestbook.components.Post.comment.forms.CreateCommentForm;
import kz.guestbook.components.Post.comment.service.CommentService;
import kz.guestbook.components.Post.forms.SearchPostForm;
import kz.guestbook.components.Post.model.Post;
import kz.guestbook.components.Post.service.PostService;
import kz.guestbook.components.User.model.User;
import kz.guestbook.controllers.DefaultController;
import kz.guestbook.components.Post.comment.forms.CommentViewModel;
import kz.guestbook.components.Post.comment.model.Comment;
import kz.guestbook.components.Post.forms.CreatePostForm;
import kz.guestbook.controllers.ajax.AjaxResponseModel;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class PostController extends DefaultController {

    @Autowired
    private PostService postService;

    @Autowired
	CommentService commentService;

    ///////////////////////////////////////GET REQUESTS. RETURN VIEW//////////////////////////////////////////
    @RequestMapping("/")
    public String home(CreatePostForm createPostForm, Model model) {
        List posts = postService.getAllPostsPassedModeration();
        model.addAttribute("posts", posts);
        model.addAttribute("loggedInUser", getAuthenticatedUser());

        return "posts/posts";
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public String getPosts(CreatePostForm createPostForm, Model model){
        List posts = postService.getAllPostsPassedModeration();

        model.addAttribute("posts",posts);
        model.addAttribute("loggedInUser", getAuthenticatedUser());

        return "posts/posts";
    }

    @RequestMapping(value = "/post/newPost", method = RequestMethod.POST)
    public String addNewPost(@Valid CreatePostForm postForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List posts = postService.getAllPostsPassedModeration();
            model.addAttribute("posts", posts);
            model.addAttribute("message", "Validation error");
            model.addAttribute("loggedInUser", getAuthenticatedUser());
            return "posts/posts";
        }

        Post post = new Post();
        post.setText(postForm.getText());
        post.setDate(new Date());
        post.setPassedModeration(false);

        User author = getAuthenticatedUser();
        post.setUser(author);
        postService.save(post);

        model.addAttribute("message", "post will be shown after moderation");
        return getPosts(new CreatePostForm(), model);
    }

    @RequestMapping(value = "/post/newcomment", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseModel createCommentAjax(@RequestBody @Valid CreateCommentForm createCommentForm, BindingResult bindingResult) {

        AjaxResponseModel ajaxResponseModel = new AjaxResponseModel();

        if (bindingResult.hasErrors()) {
            if(StringUtils.isEmpty(createCommentForm.getComment())){
                ajaxResponseModel.setResultCodeError("Please, fill out comment.");
            }
            if(createCommentForm.getPostId() == null || createCommentForm.getPostId() <= 0){
                ajaxResponseModel.setResultCodeError("Unknown post");
            }
            return ajaxResponseModel;
        }

        Comment comment = commentService.create(createCommentForm, getAuthenticatedUser());

        if(comment != null){
            CommentViewModel commentViewModel = CommentViewModel
                                                .builder()
                                                .commentText(comment.getText())
                                                .dateString((new SimpleDateFormat("dd-MMM-YYYY HH:mm:ss")).format(comment.getDate()))
                                                .userFullName(comment.getUser().getFirstName() + " " + comment.getUser().getLastName())
                                                .build();

            ajaxResponseModel.setResultCodeOk(commentViewModel);
        }
        else{
            ajaxResponseModel.setResultCodeError("Error occured. Please, try later.");
        }

        return ajaxResponseModel;
    }

    /////////////////////////////////////// RETURN JSON/XML (for Angular or ReactJS)//////////////////////////////////////////
    @RequestMapping(value = "/postsJson", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity postsRespEntity() {
        List posts = postService.getAllPostsPassedModeration();
        if (posts.isEmpty()) {
            return new ResponseEntity<List<Post>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(posts, HttpStatus.OK);
    }


    @RequestMapping(value = "/postsByText", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity postsByTextParam(@RequestParam("text") String postText) {
        List<Post> posts = postService.getByTextLike(postText);
        if (posts.isEmpty()) {
            return new ResponseEntity<List<Post>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(posts, HttpStatus.OK);
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Post postsById(@PathVariable("id") long id) {
        return postService.getByID(id);
    }

    // POST,UPDATE,DELETE methods..

}
