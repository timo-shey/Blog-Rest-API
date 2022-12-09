package fashionblogrestapi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LikeService {
    ResponseEntity<String> likeAndUnlikePost(Long postId, Long userId);
    ResponseEntity<String> likeAndUnlikeComment(Long userId, Long postId, Long commentId);
}
