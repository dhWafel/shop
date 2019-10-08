package pl.example.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class Basket {

    @Id
    @GeneratedValue
    private Long id;


    private Integer quantity;


    @ManyToOne(fetch = FetchType.EAGER)//EAGER to podczas pytania o basket zmienna user zawsze bedzie pobierana lub LAZY mowi podczas pobierania basketu nie pobieraj zmiennej user ale podczas funkcji getUser zostanie pobrany ten obiekt
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)//EAGER to podczas pytania o basket zmienna user zawsze bedzie pobierana lub LAZY mowi podczas pobierania basketu nie pobieraj zmiennej user ale podczas funkcji getUser zostanie pobrany ten obiekt
    @JoinColumn(name = "productId")
    private Product product;


}
