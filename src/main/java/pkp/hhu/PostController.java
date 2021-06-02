package pkp.hhu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pkp.hhu.place.Place;
import pkp.hhu.place.PlaceService;
import pkp.hhu.post.Post;
import pkp.hhu.post.PostService;

@Controller
@RequestMapping("/post")
public class PostController {
    private PostService postService;
    private PlaceService placeService;

    public PostController(PostService postService, PlaceService placeService) {
        this.postService = postService;
        this.placeService = placeService;
    }

    @GetMapping
    public String getPostForm(ModelMap modelMap){
        modelMap.addAttribute("place", new Place());
        modelMap.addAttribute("post", new Post());
        return "post";
    }

    @PostMapping
    public String addPost(ModelMap modelMap, Post post, Place place, BindingResult bindingResult){
        modelMap.addAttribute("post", post);
        postService.save(post);
        modelMap.addAttribute("place", place);
        placeService.save(place);
        return "post-added";
    }
}
