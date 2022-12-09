package fashionblogrestapi.service;

import fashionblogrestapi.pojo.CommentDto;
import fashionblogrestapi.pojo.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    CommentDto createComment(Long userId, Long postId, CommentDto commentDto);
    CommentResponse getAllComments(Long userId, Long postId, int pageNo, int pageSize, String sortBy, String sortDir);
    CommentDto getComment(Long postId, Long commentId);
    List<CommentDto> findBySearch(String q);
    CommentDto updateComment(CommentDto updateComment, Long userId, Long postId, Long commentId);
    String deleteComment(Long userId, Long postId, Long commentId);
}
