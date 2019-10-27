package pl.example.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull(message = "Field name is required")
    @Length(min = 3, max = 12, message = "Name length is min 3 and max 12")
    @Pattern(regexp = "[a-zA-Z]*", message = "You can set only letters")
    private String name;
    @NotNull(message = "Field surname is required")
    private String surname;
    @NotNull(message = "Password is empty")
    @Length(min=5, max=255, message = "Password is min 5 and max 30")
    private String password;
    @NotNull(message = "Field email is required")
    @Email(message = "Email is invalid")
    @Column(unique = true)
    private String email;
    @Min(value = 12, message = "Your age is not be younger 12")
    @Max(value = 100, message = "Your age is not be older 100")
    private Integer age;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime lastModyfiDate;

}
