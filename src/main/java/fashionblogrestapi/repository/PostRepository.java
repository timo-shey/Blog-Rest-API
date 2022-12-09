package fashionblogrestapi.repository;

import fashionblogrestapi.entity.Post;
import fashionblogrestapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findPostByUsers(User user, Pageable pageable);
    Optional<Post> findPostByIdAndUsers(Long postId, User user);
    Optional<List<Post>> searchAllByTitleContainsIgnoreCase(String q);

}
