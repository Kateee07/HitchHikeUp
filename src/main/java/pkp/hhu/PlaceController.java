package pkp.hhu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pkp.hhu.place.Place;
import pkp.hhu.place.PlaceService;
import pkp.hhu.post.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class PlaceController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private PlaceService placeService;
    private PostService postService;

    public PlaceController(PlaceService placeService, PostService postService) {
        this.placeService = placeService;
        this.postService = postService;
    }

    @GetMapping("/place/delete/{id}")
    public String deletePlace(@PathVariable Integer id) {
        placeService.deleteById(id);
        return "redirect:/";
    }


    @GetMapping("/show-photo-by-id/{id}")
    @ResponseBody
    void showImage(@PathVariable("id") Integer id, HttpServletResponse response, Optional<Place> place) {

        place = placeService.getPlaceById(id);
        try {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(place.get().getPhoto());
            response.getOutputStream().close();
            log.info("Place ID: " + id + ". Image found and loaded.");
        } catch (NullPointerException nullPointerException) {
            log.info("Place ID: " + id + ". No image found.");
        } catch (IOException ioException) {
            log.error(String.valueOf(ioException));
        }
    }

}
