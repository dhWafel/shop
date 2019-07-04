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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
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
    @NotNull(message = "Field email is required")
    @Email(message = "Email is invalid")
    @Column(unique = true)
    private String email;
    @Min(value = 12, message = "Your age is not be younger 12")
    @Max(value = 100, message = "Your age is not be older 100")
    private Integer age;

    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime lastModyfiDate;

}
