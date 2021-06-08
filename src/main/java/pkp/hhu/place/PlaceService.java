package pkp.hhu.place;

import org.springframework.stereotype.Service;
import pkp.hhu.post.PostRepository;

import java.util.List;


@Service
public class PlaceService {
    private PlaceRepository placeRepository;
    private PostRepository postRepository;

    public PlaceService(PlaceRepository placeRepository, PostRepository postRepository) {
        this.placeRepository = placeRepository;
        this.postRepository = postRepository;
    }

    public List<Place> findAll() {return placeRepository.findAll();}

    public void save(Place place) {
        placeRepository.save(place);
    }

    public Place findById(Integer id){
        return placeRepository.findById(id).orElse(null);
    }


    public void deleteById(Integer id) { placeRepository.deleteById(id); }
}
