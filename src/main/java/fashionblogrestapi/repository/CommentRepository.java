package fashionblogrestapi.repository;

import fashionblogrestapi.entity.Comment;
import fashionblogrestapi.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findCommentsByPosts(Post post, Pageable pageable);
    Optional<Comment> findCommentsByIdAndPosts(Long commentId, Post post);
    Optional<List<Comment>> searchAllByBodyContainsIgnoreCase(String q);
}
