package fashionblogrestapi.entity;

import fashionblogrestapi.enums.Role;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity(name = "User")
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint( name = "user_username_unique",
                        columnNames = "username")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles", columnDefinition = "VARCHAR(255) default 'ADMIN'")
    private Role roles;

    @ToString.Exclude
    @OneToMany(mappedBy = "users",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private final List<Post> posts = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "users",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private final List<Comment> comments = new ArrayList<>();

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }
}
