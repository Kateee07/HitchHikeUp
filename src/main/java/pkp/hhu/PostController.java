package pkp.hhu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import pkp.hhu.place.Place;
import pkp.hhu.place.PlaceService;
import pkp.hhu.post.Post;
import pkp.hhu.post.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.file.Paths;

@Controller
public class PostController {
    @Value("${file.uploadDir}")
    private String uploadFolder;

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
        return "post";
    }

//    @PostMapping("/post")
//    public String addPlaceAndPost(ModelMap modelMap, Post post, Place place, BindingResult bindingResult) {
//
//        modelMap.addAttribute("post", post);
//        postService.save(post);
//        modelMap.addAttribute("place", place);
//        place.setTimeAvg(post.getTime());
//        place.setRateAvg(post.getRate());
//        placeService.save(place);
//        return "post-added";
//    }

    @PostMapping("/post")
    public String createProduct(@RequestParam("name") String placeName, @RequestParam("lat") BigDecimal placeLat, @RequestParam("lng") BigDecimal placeLng,
                                @RequestParam("direction") String placeDirection, @RequestParam("description") String placeDescription, Post post,
                                ModelMap modelMap, HttpServletRequest request, final @RequestParam("photo") MultipartFile photo) {

        modelMap.addAttribute("post", new Post());

        try {
            //ustalamy jaka jest ścieżka to folderu przechowywania plików lokalnie (przed wysłaniem do mysql)
            String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
            log.info("UploadDirectory: " + uploadDirectory);
            //ustalamy skąd od klienta pobrać plik
            String fileName = photo.getOriginalFilename();
            String filePath = Paths.get(uploadDirectory, fileName).toString();
            log.info("FileName: " + photo.getOriginalFilename());
            log.info("FilePath: " + filePath);
            if (fileName == null || fileName.contains("..")) {
                // modelMap.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
                log.info("Sorry! Filename contains invalid path sequence ");
                return "redirect:/";
            }
            String[] names = placeName.split(",");
            String[] descriptions = placeDescription.split(",");

            log.info("Name: " + names[0] + " " + filePath);
            log.info("Lat: " + placeLat);
            log.info("Lng: " + placeLng);
            log.info("Direction: " + placeDirection);
            log.info("Description: " + descriptions[0]);

            try {  //sprawdzamy czy folder do pobrania tymczasowego lokalnie istnieje
                File dir = new File(uploadDirectory);
                if (!dir.exists()) {
                    log.info("Folder Created");
                    dir.mkdirs();       // jeśli nie, to go stwórz
                }
                // Save the file locally
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                stream.write(photo.getBytes());
                stream.close();
            } catch (Exception e) {
                log.info("in catch");
                e.printStackTrace();
            }
            byte[] imageData = photo.getBytes();  //przypis odebrany strumień/plik do zmiennej image data (tablica bajtów)
            Place place = new Place();
            place.setName(names[0]);
            place.setPhoto(imageData);      // dopisz dane pliku do obiektu place
            place.setLat(placeLat);
            place.setLng(placeLng);
            place.setDescription(descriptions[0]);
            place.setDirection(placeDirection);

            place.setTimeAvg(post.getTime());
            place.setRateAvg(post.getRate());

            placeService.save(place);
            postService.save(post);

            log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
            log.info("Product Saved With File - " + fileName);
            return "redirect:/";

        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
            return "redirect:/";
        }
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

