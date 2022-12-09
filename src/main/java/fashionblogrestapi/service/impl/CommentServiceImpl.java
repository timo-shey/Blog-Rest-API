package fashionblogrestapi.service.impl;

import fashionblogrestapi.entity.Comment;
import fashionblogrestapi.entity.Post;
import fashionblogrestapi.entity.User;
import fashionblogrestapi.exception.NotFoundException;
import fashionblogrestapi.pojo.CommentDto;
import fashionblogrestapi.pojo.CommentResponse;
import fashionblogrestapi.pojo.PostDto;
import fashionblogrestapi.pojo.UserComment;
import fashionblogrestapi.repository.CommentRepository;
import fashionblogrestapi.repository.PostRepository;
import fashionblogrestapi.repository.UserRepository;
import fashionblogrestapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto createComment(Long userId, Long postId, CommentDto commentDto) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> {throw new NotFoundException("Users not found.");}
        );
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> {throw new NotFoundException("Posts not found.");}
        );

        Comment comment = new Comment();
        comment.setBody(commentDto.getBody());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUsers(user);
        comment.setPosts(post);

        commentRepository.save(comment);
        return convertEntityToDto(comment);
    }


    @Override
    public CommentResponse getAllComments(Long userId, Long postId, int pageNo, int pageSize, String sortBy, String sortDir) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> {throw new NotFoundException("Users not found.");}
        );
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> {throw new NotFoundException("Posts not found.");}
        );

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Comment> comments = commentRepository.findCommentsByPosts(post, pageable);

        List<Comment> commentList = comments.getContent();

        List<CommentDto> body = convertCommentListEntityToDto(commentList);

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setUser(convertEntityToDto(user));
        commentResponse.setPost(convertEntityToDto(post));
        commentResponse.setContent(body);
        commentResponse.setPageNo(comments.getNumber());
        commentResponse.setPageSize(comments.getSize());
        commentResponse.setTotalElements(comments.getTotalElements());
        commentResponse.setTotalPages(comments.getTotalPages());
        commentResponse.setLast(comments.isLast());

        return commentResponse;
    }

    @Override
    public CommentDto getComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> {throw new NotFoundException("Posts not found.");}
        );
        Comment comment = commentRepository.findCommentsByIdAndPosts(commentId, post).orElseThrow(
                ()-> {throw new NotFoundException("Comments not found.");}
        );
        return convertEntityToDto(comment);
    }

    @Override
    public List<CommentDto> findBySearch(String q) {
        List<Comment> commentList = commentRepository.searchAllByBodyContainsIgnoreCase(q).orElseThrow(()-> {
            throw new NotFoundException("Comment(s) not found.");});

        return convertCommentListEntityToDto(commentList);
    }

    @Override
    @Transactional
    public CommentDto updateComment(CommentDto updateComment, Long userId, Long postId, Long commentId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> {throw new NotFoundException("Users not found.");}
        );
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> {throw new NotFoundException("Posts not found.");}
        );
        Comment comment = commentRepository.findCommentsByIdAndPosts(commentId, post).orElseThrow(
                ()-> {throw new NotFoundException("Comments not found.");}
        );
        comment.setUsers(user);
        comment.setPosts(post);
        comment.setBody(updateComment.getBody());
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setIsEdited(true);
        Comment newComment = commentRepository.save(comment);

        return convertEntityToDto(newComment);
    }

    @Override
    public String deleteComment(Long userId, Long postId, Long commentId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> {throw new NotFoundException("Users not found.");}
        );
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> {throw new NotFoundException("Posts not found.");}
        );
        Comment comment = commentRepository.findCommentsByIdAndPosts(commentId, post).orElseThrow(
                ()-> {throw new NotFoundException("Comments not found.");}
        );
        commentRepository.delete(comment);
        return "Comment has been deleted successfully.";
    }

    private CommentDto convertEntityToDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getUsers().getUsername(),
                comment.getBody(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getIsEdited(),
                comment.getLikeQuantity()
        );
    }

    private static List<CommentDto> convertCommentListEntityToDto(List<Comment> commentList) {
        return commentList.stream()
                .map(comment -> new CommentDto(
                        comment.getId(),
                        comment.getUsers().getUsername(),
                        comment.getBody(),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt(),
                        comment.getIsEdited(),
                        comment.getLikeQuantity()))
                .collect(Collectors.toList());
    }

    private UserComment convertEntityToDto(User user) {
        return new UserComment(
                user.getId(),
                user.getUsername(),
                user.getRoles()
        );
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

}
