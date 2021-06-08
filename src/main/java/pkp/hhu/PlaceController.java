package pkp.hhu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pkp.hhu.place.Place;
import pkp.hhu.place.PlaceService;
import pkp.hhu.post.Post;
import pkp.hhu.post.PostService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/place")
public class PlaceController {

    private PlaceService placeService;
    private PostService postService;

    public PlaceController(PlaceService placeService, PostService postService) {
        this.placeService = placeService;
        this.postService = postService;
    }

    @GetMapping("/delete/{id}")
    public String deletePlace(@RequestParam Integer placeId, @PathVariable String id) {
        placeService.deleteById(placeId);
        return "redirect:/";
    }


}
