package fashionblogrestapi.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String author;
    @NotNull(message = "Post title cannot be missing or empty.")
    @Size(min = 2, message = "Post title must not be less than 2 characters.")
    private String title;
    @NotNull(message = "Post body cannot be missing or empty.")
    @Size(min = 2, message = "Post body must not be less than 2 characters.")
    private String body;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private Boolean isEdited = false;
    private int likeQuantity;

    public PostDto(String author,
                   String title,
                   String body
    ) {
        this.author = author;
        this.title = title;
        this.body = body;
    }
}

