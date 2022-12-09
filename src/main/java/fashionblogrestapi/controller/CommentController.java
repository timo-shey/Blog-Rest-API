package fashionblogrestapi.controller;

import fashionblogrestapi.pojo.CommentDto;
import fashionblogrestapi.pojo.CommentResponse;
import fashionblogrestapi.service.CommentService;
import fashionblogrestapi.service.LikeService;
import fashionblogrestapi.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/{userId}/post/{postId}/comment")
public class CommentController {

    private final CommentService commentService;
    private final LikeService likeService;

    @Autowired
    public CommentController(CommentService commentService, LikeService likeService) {
        this.commentService = commentService;
        this.likeService = likeService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(
            @PathVariable(name = "postId") Long postId,
            @RequestBody CommentDto commentDto,
            @PathVariable(name = "userId") Long userId){
        return new ResponseEntity<>(commentService.createComment(userId, postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CommentResponse> getAllComments(
            @PathVariable(name = "postId") Long postId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @PathVariable Long userId){
        return new ResponseEntity<>(commentService.getAllComments(userId, postId, pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CommentDto>> findCommentBySearch(@RequestParam("q") String q,
                                                                @PathVariable String userId,
                                                                @PathVariable String postId){
        return new ResponseEntity<>(commentService.findBySearch(q), HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@Valid @RequestBody CommentDto commentDto,
                                                    @PathVariable(name = "postId") Long postId,
                                                    @PathVariable(name = "commentId") Long commentId,
                                                    @PathVariable Long userId){
        CommentDto commentResponse = commentService.updateComment(commentDto, userId, postId, commentId);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "postId") Long postId,
                                                @PathVariable(name = "commentId") Long commentId,
                                                @PathVariable Long userId){
        String deletedComment = commentService.deleteComment(userId, postId, commentId);
        return new ResponseEntity<>(deletedComment, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{commentId}/like")
    public ResponseEntity<String> likeAndUnlikeComment(@PathVariable(name = "postId") Long postId,
                                                       @PathVariable(name = "userId") Long userId,
                                                       @PathVariable(name = "commentId") Long commentId) {
        return likeService.likeAndUnlikeComment(userId, postId, commentId);
    }

}

