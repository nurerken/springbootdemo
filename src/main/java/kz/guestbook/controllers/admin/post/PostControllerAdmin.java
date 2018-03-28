package kz.guestbook.controllers.admin.post;

import kz.guestbook.components.Post.service.PostService;
import kz.guestbook.components.Post.model.Post;
import kz.guestbook.controllers.admin.DefaultAdminController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostControllerAdmin extends DefaultAdminController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public String getPosts(Model model, PostSearchForm postSearchForm) {

        List newPosts = postService.getAllPostsOnModeration();
        model.addAttribute("newPosts", newPosts);

        List postsPassedModeration = postService.getPostsPassedModerationBySearch(postSearchForm.getSearchString());
        model.addAttribute("postsPassedModeration", postsPassedModeration);

        model.addAttribute("postSearchForm", postSearchForm);

        return "admin/post/posts";
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    public String getPost(@PathVariable("id") long id, Model model) {
        Post post = postService.getByID(id);
        if(post == null){
            // todo:handle error
        }

        PostEditForm postEditForm = new PostEditForm();
        postEditForm.setId(post.getId());
        postEditForm.setText(post.getText());

        model.addAttribute("postEditForm", postEditForm);
        model.addAttribute("post", post);
        return "admin/post/post";
    }

    @RequestMapping(value = "/post/savepost", method = RequestMethod.POST)
    public String savePost(PostEditForm postEditForm, Model model) {
        Post post = postService.getByID(postEditForm.getId());
        if(post == null){
            //todo handle
        }
        //todo:validate
        post.setText(postEditForm.getText());
        postService.save(post);

        model.addAttribute("message", "changes were saved");
        return getPost(post.getId(), model);
    }

    @RequestMapping(value = "/post/activate", method = RequestMethod.POST)
    public String activatePost(PostEditForm postEditForm, Model model) {
        Post post = postService.getByID(postEditForm.getId());
        if(post == null){
            //todo handle
        }

        if(post.getPassedModeration()){
            //todo: handle
        }

        //todo:validate
        int affectedRows = postService.activatePost(post.getId());

        model.addAttribute("message", "post was activated");
        return getPosts(model, new PostSearchForm());
    }

    @RequestMapping(value = "/post/delete", method = RequestMethod.POST)
    public String deletePost(@RequestParam Long id, Model model){
        Post post = postService.getByID(id);
        if(post == null){

        }
        postService.delete(post);

        model.addAttribute("message", "post deleted successfully");
        return getPosts(model, new PostSearchForm());
    }
}
