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
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Field name is required")
    @Length(min = 3, max = 12, message = "Name length is min 3 and max 12")
    @Pattern(regexp = "[a-zA-Z]*", message = "You can set only letters")
    private String name;

    @Length(min = 3, max = 100, message = "Dyscription length is min 3 and max 100")
    @Pattern(regexp = "[a-zA-Z0-9 ']+", message = "You can set letters and numbers")
    private String dyscription;

    @DecimalMin(value = "0.01", message = "Price is not be smaller than 0.01")
    private Double price;

    @Max(value = 99, message = "Quantity is not be bigger than 99")
    private Integer quantity;

    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime lastModyfiDate;
}
