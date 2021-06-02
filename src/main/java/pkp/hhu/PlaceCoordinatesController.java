package pkp.hhu;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pkp.hhu.place.Place;
import pkp.hhu.place.PlaceService;
import pkp.hhu.post.PostService;

import java.util.List;

@RestController
public class PlaceCoordinatesController {
    private PostService postService;
    private PlaceService placeService;

    public PlaceCoordinatesController(PostService postService, PlaceService placeService) {
        this.postService = postService;
        this.placeService = placeService;
    }

    @GetMapping("/post/coordinates/all")
    public List<Place> getPlaces() {
        return placeService.findAll();
    }

//    @GetMapping("/post/coordinates")
//    public String getPostForm(ModelMap modelMap) {
//        modelMap.addAttribute("place", new Place());
//        modelMap.addAttribute("post", new Post());
//        return "post-coordinates";
//    }

//    @PostMapping("/post/coordinates")
//    public Coordinates addCoordinates(@RequestBody Coordinates coordinates) {
//        System.out.println(coordinates);
//        return coordinates;
//
//    }

}
