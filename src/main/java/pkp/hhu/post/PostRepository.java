package pkp.hhu.post;

import org.springframework.data.jpa.repository.JpaRepository;
import pkp.hhu.post.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
