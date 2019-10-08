package pl.example.shop.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class ClientOrder {


    @Id
    @GeneratedValue
    private Long id;

    private String orderNumber;

    private Integer quantity;

    private Double orderValue;


    @ManyToOne(fetch = FetchType.EAGER)//EAGER to podczas pytania o basket zmienna user zawsze bedzie pobierana lub LAZY mowi podczas pobierania basketu nie pobieraj zmiennej user ale podczas funkcji getUser zostanie pobrany ten obiekt
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)//EAGER to podczas pytania o basket zmienna user zawsze bedzie pobierana lub LAZY mowi podczas pobierania basketu nie pobieraj zmiennej user ale podczas funkcji getUser zostanie pobrany ten obiekt
    @JoinColumn(name = "productId")
    private Product product;

}
