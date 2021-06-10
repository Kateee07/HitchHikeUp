package pkp.hhu.post;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Integer id) {
        return postRepository.findById(id).orElse(null);
    }

    public List<Post> findByPlaceId(Integer id) {
        return postRepository.findAllByPlaceId(id);
    }

    public float avgPlaceRate(Integer id) {
        List<Post> allPostsByPlace = findByPlaceId(id);
        float rateAvg = 0;
        int sum = 0;
        for (Post post : allPostsByPlace) {
            sum += post.getRate();
        }
        rateAvg = (float) sum / allPostsByPlace.size();
        return rateAvg;
    }

    public int avgPlaceTime(Integer id) {
        List<Post> allPostsByPlace = findByPlaceId(id);
        int timeAvg = 0;
        int sum = 0;
        for (Post post : allPostsByPlace) {
            sum += post.getTime();
        }
        timeAvg = sum / allPostsByPlace.size();
        return timeAvg;
    }
}
