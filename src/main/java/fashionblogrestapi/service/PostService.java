package fashionblogrestapi.service;

import fashionblogrestapi.pojo.PostDto;
import fashionblogrestapi.pojo.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    PostDto createPost(Long userId, PostDto postDto);
    PostDto findById(Long id);
    PostResponse getAllPosts(Long userId, int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPost(Long userId, Long postId);
    List<PostDto> findBySearch(String q);
    PostDto updatePost(PostDto updatePost, Long userId, Long postId);
    void deletePost(Long userId, Long postId);
}
