package fashionblogrestapi.controller;

import fashionblogrestapi.pojo.PostDto;
import fashionblogrestapi.pojo.PostResponse;
import fashionblogrestapi.service.LikeService;
import fashionblogrestapi.service.PostService;
import fashionblogrestapi.util.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user/{userId}/post")
public class PostController {

    private final PostService postService;
    private final LikeService likeService;

    public PostController(PostService postService, LikeService likeService) {
        this.postService = postService;
        this.likeService = likeService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@PathVariable(name = "userId") Long userId,
                                              @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(userId, postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @PathVariable(name = "userId") Long userId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return new ResponseEntity<>(postService.getAllPosts(userId, pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "userId") Long userId,
                                               @PathVariable(name = "postId") Long postId){
        return new ResponseEntity<>(postService.getPost(userId, postId), HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,
                                              @PathVariable(name = "userId") Long userId,
                                              @PathVariable(name = "postId") Long postId){
        PostDto postResponse = postService.updatePost(postDto, userId, postId);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostDto>> findPostBySearch(@RequestParam("q") String q,
                                                          @PathVariable String userId){
        return new ResponseEntity<>(postService.findBySearch(q), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Object> deletePost(@PathVariable(name = "userId") Long userId,
                                             @PathVariable(name = "postId") Long postId){
        postService.deletePost(userId, postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{postId}/like")
    public ResponseEntity<String> likeAndUnlikePost(@PathVariable(name = "postId") Long postId,
                                                    @PathVariable(name = "userId") Long userId) {
        return likeService.likeAndUnlikePost(postId, userId);
    }
}

