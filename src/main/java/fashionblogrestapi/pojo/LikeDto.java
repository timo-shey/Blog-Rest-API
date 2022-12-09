package fashionblogrestapi.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {
    private UserDto users;
    private PostDto posts;
    private CommentDto comments;
}
