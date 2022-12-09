package fashionblogrestapi.service.impl;

import fashionblogrestapi.entity.Comment;
import fashionblogrestapi.entity.Like;
import fashionblogrestapi.entity.Post;
import fashionblogrestapi.entity.User;
import fashionblogrestapi.exception.AppException;
import fashionblogrestapi.repository.CommentRepository;
import fashionblogrestapi.repository.LikeRepository;
import fashionblogrestapi.repository.PostRepository;
import fashionblogrestapi.repository.UserRepository;
import fashionblogrestapi.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class LikeServiceImpl implements LikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Autowired
    public LikeServiceImpl(PostRepository postRepository, UserRepository userRepository,
                           CommentRepository commentRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.likeRepository =likeRepository;
    }

    @Override
    public ResponseEntity<String> likeAndUnlikePost(Long postId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new AppException(HttpStatus.BAD_REQUEST, "User with ID:  " + userId  + " not found.");
        });

        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new AppException(HttpStatus.BAD_REQUEST, "Post with ID:  " + postId  + " not found.");
        });

        Optional<Like> likes = likeRepository.findLikeByUserAndPost(user, post);
        Set<Like> likeSet;
        if (likes.isEmpty()){
            Like like = new Like();
            like.setPost(post);
            like.setUser(user);
            likeSet = post.getPostLikes();
            likeSet.add(like);
            post.setLikeQuantity(likeSet.size());
            likeRepository.save(like);

            return new ResponseEntity<>(user.getUsername() + " has liked the post.", HttpStatus.CREATED);
        }  else {
            likeSet = post.getPostLikes();
            likeSet.remove(likes.get());
            post.setLikeQuantity(likeSet.size());
            likeRepository.delete(likes.get());

            return new ResponseEntity<>(user.getUsername() + " has unliked the post.", HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<String> likeAndUnlikeComment(Long userId, Long postId, Long commentId) {
        User user = userRepository.findById(userId).orElseThrow(()-> {
            throw new AppException(HttpStatus.BAD_REQUEST, "User with ID:  " + userId  + " not found.");
        });

        Post post = postRepository.findById(postId).orElseThrow(()-> {
            throw new AppException(HttpStatus.BAD_REQUEST, "Post with ID:  " + postId  + " not found.");
        });

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> {
            throw new AppException(HttpStatus.BAD_REQUEST, "Comment with ID:  " + commentId  + " not found.");
        });

        Set<Like> likeSet;
        Optional<Like> liked = likeRepository.findLikeByUserAndPostAndComment(user, post, comment);

        if (liked.isEmpty()){
            Like like  = new Like();
            like.setUser(user);
            like.setPost(post);
            like.setComment(comment);
            likeSet = comment.getCommentLikes();
            likeSet.add(like);
            comment.setLikeQuantity(likeSet.size());
            likeRepository.save(like);

            return new ResponseEntity<>(user.getUsername() + " has liked the comment.", HttpStatus.CREATED);

        } else {
            likeSet = comment.getCommentLikes();
            likeSet.remove(liked.get());
            comment.setLikeQuantity(likeSet.size());
            likeRepository.delete(liked.get());

            return new ResponseEntity<>(user.getUsername() + " has unliked the comment.", HttpStatus.NO_CONTENT);
        }
    }
}

