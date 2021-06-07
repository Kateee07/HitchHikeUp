package pkp.hhu.place;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {
    private PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<Place> findAll() {return placeRepository.findAll();}

    public void save(Place place) {
        placeRepository.save(place);
    }

    public void deleteById(Integer id) { placeRepository.deleteById(id); }
}
