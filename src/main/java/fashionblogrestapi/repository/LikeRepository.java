package fashionblogrestapi.repository;

import fashionblogrestapi.entity.Comment;
import fashionblogrestapi.entity.Like;
import fashionblogrestapi.entity.Post;
import fashionblogrestapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findLikeByUserAndPost (User user, Post post);
    Optional<Like> findLikeByUserAndPostAndComment(User user, Post post, Comment comment);
}
