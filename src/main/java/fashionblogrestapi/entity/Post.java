package fashionblogrestapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity(name = "Post")
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_edited")
    private Boolean isEdited = false;

    @Column(name = "like_quantity", nullable = false)
    private int likeQuantity;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "post_user_id_fk"
            )
    )
    private User users;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "posts",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    @ToString.Exclude
    private Set<Like> postLikes = new HashSet<>();

    public Post(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Post(Long id, String author, String title, String body, LocalDateTime createdAt, LocalDateTime updatedAt,
                Boolean isEdited, int likeQuantity, User users) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isEdited = isEdited;
        this.likeQuantity = likeQuantity;
        this.users = users;
    }
}

