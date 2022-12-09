package fashionblogrestapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity(name = "Comment")
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_edited")
    private Boolean isEdited = false;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "comment_user_id_fk"
            )
    )
    private User users;

    @ManyToOne
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "comment_post_id_fk"
            )
    )
    private Post posts;

    @Column(name = "like_quantity", nullable = false)
    private int likeQuantity;

    @OneToMany
    @JsonIgnore
    @ToString.Exclude
    private Set<Like> commentLikes = new HashSet<>();

    public Comment(String body, User users, Post posts) {
        this.body = body;
        this.users = users;
        this.posts = posts;
    }

    public Comment(Long id, String body, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isEdited,
                   User users, Post posts, int likeQuantity) {
        this.id = id;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isEdited = isEdited;
        this.users = users;
        this.posts = posts;
        this.likeQuantity = likeQuantity;
    }
}

