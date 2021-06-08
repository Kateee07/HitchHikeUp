package pkp.hhu;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import pkp.hhu.place.Place;
import pkp.hhu.place.PlaceService;
import pkp.hhu.post.Post;
import pkp.hhu.post.PostService;
import pkp.hhu.user.User;
import pkp.hhu.user.UserService;

import java.time.LocalDate;
import java.util.Optional;

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
    public String addPostWithPlace(ModelMap modelMap, Post post, Place place, BindingResult bindingResult) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        modelMap.addAttribute("post", post);
        post.setUser(user);
        post.setDate(LocalDate.now());
        post.setPlace(place);
        modelMap.addAttribute("place", place);
        place.setTimeAvg(post.getTime());
        place.setRateAvg(post.getRate());
        placeService.save(place);
        postService.save(post);
        return "redirect:/";
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
