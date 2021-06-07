package pkp.hhu.post;

import org.springframework.data.jpa.repository.JpaRepository;
import pkp.hhu.post.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    public List<Post> findAllByPlaceId(Integer id);
}
