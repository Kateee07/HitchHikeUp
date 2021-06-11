package pkp.hhu;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pkp.hhu.place.Place;
import pkp.hhu.place.PlaceService;
import pkp.hhu.post.Post;
import pkp.hhu.post.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

}
