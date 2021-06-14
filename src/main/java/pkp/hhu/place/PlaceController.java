package pkp.hhu.place;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
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
import pkp.hhu.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Controller
public class PlaceController {
    @Value("${file.uploadDir}")
    private String uploadFolder;

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


    @GetMapping("/place/show-photo-by-id/{id}")
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

    @PostMapping("/place/add")
    public String createPlaceAnPost(@RequestParam("name") String placeName, @RequestParam("lat") BigDecimal placeLat, @RequestParam("lng") BigDecimal placeLng,
                                    @RequestParam("direction") String placeDirection, @RequestParam("description") String placeDescription, Post post,
                                    ModelMap modelMap, HttpServletRequest request, final @RequestParam("photo") MultipartFile photo) {

        try {
            //ustalamy jaka jest ścieżka to folderu przechowywania plików lokalnie (przed wysłaniem do mysql)
            String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
            log.info("UploadDirectory: " + uploadDirectory);
            //ustalamy skąd od klienta pobrać plik
            String fileName = photo.getOriginalFilename();
            String filePath = Paths.get(uploadDirectory, fileName).toString();
            log.info("FileName: " + photo.getOriginalFilename());
            if (fileName == null || fileName.contains("..")) {
                // modelMap.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
                log.warn("Sorry! Filename contains invalid path sequence!");
                return "redirect:/";
            }
            String[] names = placeName.split(",");
            String[] descriptions = placeDescription.split(",");

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
            } catch (FileNotFoundException fileNotFoundException) {
                log.info("Nie załączono pliku! " + fileNotFoundException);
            } catch (Exception exception) {
                log.error("Exception: " + exception);
                exception.printStackTrace();
            }
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            modelMap.addAttribute("post", new Post());

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

            post.setUser(user);
            post.setDate(LocalDate.now());
            post.setPlace(place);

            postService.save(post);

            log.info("Place Saved. Attached photo: >>>" + fileName + "<<<. No photo if field empty.");

            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
            return "redirect:/";
        }
    }

    @GetMapping("/place/edit/{id}")
    public String editPlace(@PathVariable Integer id, ModelMap modelMap) {
        log.info("Edit Place ID: " + id);
        Place place = placeService.findById(id);
        modelMap.addAttribute("place", place);
        return "editplace";
    }

    @PostMapping("/place/edited")
    public String updatePlace(@RequestParam("id") Integer placeId, @RequestParam("name") String placeName, @RequestParam("lat") BigDecimal placeLat,
                              @RequestParam("lng") BigDecimal placeLng, @RequestParam("direction") String placeDirection, @RequestParam("description") String placeDescription,
                              ModelMap modelMap, HttpServletRequest request, final @RequestParam("photo") MultipartFile photo) {
        try {
            //ustalamy jaka jest ścieżka to folderu przechowywania plików lokalnie (przed wysłaniem do mysql)
            String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
            log.info("UploadDirectory: " + uploadDirectory);
            //ustalamy skąd od klienta pobrać plik
            String fileName = photo.getOriginalFilename();
            String filePath = Paths.get(uploadDirectory, fileName).toString();
            log.info("FileName: " + photo.getOriginalFilename());
            if (fileName == null || fileName.contains("..")) {
                // modelMap.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
                log.warn("Sorry! Filename contains invalid path sequence!");
                return "redirect:/";
            }
            String[] names = placeName.split(",");
            String[] descriptions = placeDescription.split(",");

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
            } catch (FileNotFoundException fileNotFoundException) {
                log.info("Nie załączono pliku! " + fileNotFoundException);
            } catch (Exception exception) {
                log.error("Exception: " + exception);
                exception.printStackTrace();
            }
//            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            byte[] imageData = photo.getBytes();  //przypis odebrany strumień/plik do zmiennej image data (tablica bajtów)
            Place place = new Place();

            place.setId(placeId);
            place.setName(names[0]);
            place.setPhoto(imageData);      // dopisz dane pliku do obiektu place
            place.setLat(placeLat);
            place.setLng(placeLng);
            place.setDescription(descriptions[0]);
            place.setDirection(placeDirection);

            placeService.save(place);

            log.info("Place Updated. Attached photo: >>>" + fileName + "<<<. No photo if field empty.");

            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
            return "redirect:/";
        }
    }
}
