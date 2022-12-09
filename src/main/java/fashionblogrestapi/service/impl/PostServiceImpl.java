package fashionblogrestapi.service.impl;

import fashionblogrestapi.entity.Post;
import fashionblogrestapi.entity.User;
import fashionblogrestapi.enums.Role;
import fashionblogrestapi.exception.AppException;
import fashionblogrestapi.exception.NotFoundException;
import fashionblogrestapi.pojo.PostDto;
import fashionblogrestapi.pojo.PostResponse;
import fashionblogrestapi.repository.PostRepository;
import fashionblogrestapi.repository.UserRepository;
import fashionblogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(Long userId, PostDto postDto) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> {throw new NotFoundException("User not found.");}
        );

        Post post = new Post();
        post.setAuthor(user.getUsername());
        post.setTitle(postDto.getTitle());
        post.setBody(postDto.getBody());
        post.setCreatedAt(LocalDateTime.now());
        post.setUsers(user);

        if (user.getRoles().equals(Role.VISITOR)) throw new AppException(HttpStatus.BAD_REQUEST,
                user.getUsername() + " is not authorized to create a post.");

        postRepository.save(post);
        return convertEntityToDto(post);
    }

    @Override
    public PostDto findById(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                ()-> {throw new NotFoundException("Post not found.");}
        );

        return convertEntityToDto(post);
    }

    @Override
    public PostResponse getAllPosts(Long userId, int pageNo, int pageSize, String sortBy, String sortDir) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> {throw new NotFoundException("User not found.");}
        );

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Post> posts = postRepository.findPostByUsers(user, pageable);

        List<Post> postList = posts.getContent();
        List<PostDto> body = convertListEntityToDto(postList);

        PostResponse postResponse = new PostResponse();
        postResponse.setBody(body);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPost(Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> {throw new NotFoundException("User not found.");}
        );
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> {throw new NotFoundException("Post not found.");}
        );

        return convertEntityToDto(post);
    }

    @Override
    public List<PostDto> findBySearch(String q) {
        List<Post> postList = postRepository.searchAllByTitleContainsIgnoreCase(q).orElseThrow(()-> {
            throw new NotFoundException("Post(s) not found.");});

        return convertListEntityToDto(postList);
    }

    @Override
    @Transactional
    public PostDto updatePost(PostDto updatePost, Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> {throw new NotFoundException("User not found.");}
        );
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> {throw new NotFoundException("Post not found.");}
        );

        post.setTitle(updatePost.getTitle());
        post.setBody(updatePost.getBody());
        post.setUpdatedAt(LocalDateTime.now());
        post.setIsEdited(true);

        if (user.getRoles().equals(Role.VISITOR)) throw new AppException(HttpStatus.BAD_REQUEST,
                user.getUsername() + " is not authorized to update a post.");

        postRepository.save(post);
        return convertEntityToDto(post);
    }

    @Override
    public void deletePost(Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> {throw new NotFoundException("User not found.");}
        );
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> {throw new NotFoundException("Post not found.");}
        );

        if (user.getRoles().equals(Role.VISITOR)) throw new AppException(HttpStatus.BAD_REQUEST,
                user.getUsername() + " is not authorized to delete a post.");
        else postRepository.delete(post);
    }

    private PostDto convertEntityToDto(Post post) {
        return new PostDto(
                post.getId(),
                post.getUsers().getUsername(),
                post.getTitle(),
                post.getBody(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getIsEdited(),
                post.getLikeQuantity()
        );
    }

    private static List<PostDto> convertListEntityToDto(List<Post> postList) {
        return postList.stream()
                .map(post -> new PostDto(
                        post.getId(),
                        post.getUsers().getUsername(),
                        post.getTitle(),
                        post.getBody(),
                        post.getCreatedAt(),
                        post.getUpdatedAt(),
                        post.getIsEdited(),
                        post.getLikeQuantity()))
                .collect(Collectors.toList());
    }
}
