package pkp.hhu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import pkp.hhu.place.Place;
import pkp.hhu.place.PlaceService;
import pkp.hhu.post.Post;
import pkp.hhu.post.PostService;

import java.util.List;

@Controller
public class PostController {
    private PostService postService;
    private PlaceService placeService;
    public PostController(PostService postService, PlaceService placeService) {
        this.postService = postService;
        this.placeService = placeService;
    }
    @GetMapping("/post")
    public String getPostForm(ModelMap modelMap) {
        modelMap.addAttribute("place", new Place());
        modelMap.addAttribute("post", new Post());
        return "post";
    }
    @PostMapping("/post")
    public String addPost(ModelMap modelMap, Post post, Place place, BindingResult bindingResult) {
        modelMap.addAttribute("post", post);
        postService.save(post);
        modelMap.addAttribute("place", place);
        place.setTimeAvg(post.getTime());
        place.setRateAvg(post.getRate());
        placeService.save(place);
        return "post-added";
    }

    @GetMapping("/place")
    public String showPostbyId(@RequestParam(required = false) Integer id, ModelMap modelMap) {

        List<Post> posts = postService.findByPlaceId(id);
        modelMap.addAttribute("posts", posts);
        return "place";
    }

    @GetMapping("/post/coordinates")
    public String getCoordinatesPostForm(ModelMap modelMap) {
        modelMap.addAttribute("place", new Place());
        modelMap.addAttribute("post", new Post());
        return "post";
    }
    @PostMapping("/post/coordinates")
    public String postCoordinatesPostForm(ModelMap modelMap, @RequestBody @Validated Place place, BindingResult bindingResult) {
        modelMap.addAttribute("place", place);
        modelMap.addAttribute("post", new Post());
        return "post";
    }
}
