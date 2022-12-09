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
public class CommentDto {
    private Long id;
    private String username;
    @NotNull(message = "Comment body cannot be missing or empty.")
    @Size(min = 2, message = "Comment body must not be less than 2 characters.")
    private String body;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private Boolean isEdited = false;
    private int likeQuantity;

}

