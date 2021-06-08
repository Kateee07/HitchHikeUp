package pkp.hhu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pkp.hhu.place.Place;
import pkp.hhu.place.PlaceRepository;
import pkp.hhu.place.PlaceService;
import pkp.hhu.post.Post;
import pkp.hhu.post.PostService;

import java.util.List;


@Controller
public class IndexController {

    private PostService postService;
    private PlaceService placeService;

    public IndexController(PostService postService, PlaceService placeService) {
        this.postService = postService;
        this.placeService = placeService;
    }

    @GetMapping
    public String getPostForm(ModelMap modelMap) {
        modelMap.addAttribute("place", new Place());
        modelMap.addAttribute("post", new Post());
        modelMap.addAttribute("places", placeService.findAll());
        return "index";
    }


    @PostMapping
    public String addPost(ModelMap modelMap, Post post, Place place, BindingResult bindingResult) {
        modelMap.addAttribute("post", post);
        postService.save(post);
        modelMap.addAttribute("place", place);
        placeService.save(place);
        return "redirect:/";
    }

    @GetMapping("/after-login")
    public String afterLogin() {
        return "redirect:/";
    }
}
