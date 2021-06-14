package pkp.hhu.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pkp.hhu.place.Place;
import pkp.hhu.place.PlaceService;
import pkp.hhu.post.Post;
import pkp.hhu.post.PostService;
import pkp.hhu.user.User;

import java.time.LocalDate;
import java.util.List;

@Controller
public class PostController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

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
        return "redirect:/";
    }

    @PostMapping("/post/add")
    public String addPost(@RequestParam("placeId") int placeId, @RequestParam("commentPost") String comment,
                          @RequestParam("timePost") int time, @RequestParam("ratePost") int rate) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = new Post();
        post.setComment(comment);
        post.setTime(time);
        post.setRate(rate);
        post.setUser(user);
        post.setDate(LocalDate.now());
        Place place = placeService.findById(placeId);
        post.setPlace(place);
        postService.save(post);
        log.info("Post saved");
        int timeAvg = postService.avgPlaceTime(placeId);
        float rateAvg = postService.avgPlaceRate(placeId);
        place.setTimeAvg(timeAvg);
        place.setRateAvg(rateAvg);
        placeService.save(place);
        log.info("Place avg time & avg rate updated");
        return "redirect:/";
    }

    @GetMapping("/posts")
    public String showPostbyId(@RequestParam(required = false) Integer id, ModelMap modelMap) {
        List<Post> posts = postService.findByPlaceId(id);
        modelMap.addAttribute("posts", posts);
        return "place";
    }

}
