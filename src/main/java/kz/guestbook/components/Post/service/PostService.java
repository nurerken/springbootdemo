package kz.guestbook.components.Post.service;

import kz.guestbook.components.Post.forms.SearchPostForm;
import kz.guestbook.components.Post.model.Post;
import kz.guestbook.components.Post.repository.PostRepository;
import kz.guestbook.components.Post.repository.PostSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    final int maxSize = 50;
    final int defaultSize = 10;

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public List<Post> getAllPostsOnModeration(){
        return postRepository.findAllPosts(false);
    }

    public List<Post> getAllPostsPassedModeration(){
        return postRepository.findAllPosts(true);
    }

    public List<Post> getPostsPassedModerationBySearch(String searchString){
        if(searchString == null)
            searchString = "";
        return postRepository.findByTextOrAuthor(searchString);
    }

    public Page<Post> getPostsByForm(SearchPostForm searchPostForm){

        Specification<Post> specification = new PostSpecification(searchPostForm);

        Sort sort = null;
        if(searchPostForm.getSortColumn() != null && !searchPostForm.getSortColumn().isEmpty()){
            sort = new Sort(new Sort.Order(searchPostForm.getSortDirection() == 0 ? Sort.Direction.ASC : Sort.Direction.DESC, searchPostForm.getSortColumn()));
        }

        int startPage = Math.max(searchPostForm.getStartPage() , 0);

        if(searchPostForm.getSize() == 0){
            searchPostForm.setSize(defaultSize);
        }
        int size = Math.min(searchPostForm.getSize(), maxSize);
        Pageable pageable =  new PageRequest(startPage, size, sort);

        Page<Post> pages = postRepository.findAll(specification ,pageable);
        return pages;
    }

    public List<Post> getByText(String text){
        return postRepository.getByText(text);
    }

    public Post getByID(Long id){
        return postRepository.getById(id);
    }

    public List<Post> getByTextLike(String text){
        return postRepository.findPostByTextLike(text);
    }

    public void updateText(Long id, String text){
        postRepository.updateText(text, id);
    }

    public void save(Post post){
        postRepository.save(post);
    }

    public int activatePost(Long id){
        return postRepository.setPassedModeration(id, true);
    }

    public void delete(Post post){
        postRepository.delete(post);
    }

    public void updateText(String text, Long id){
        postRepository.updateText(text, id);
    }
}
