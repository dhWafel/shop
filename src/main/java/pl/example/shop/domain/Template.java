package pl.example.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Template {

    @Id
    @GeneratedValue
    private Long id;

    private String type;

    @Lob                                                //duże dane w kolumnie (powyżej 250 znakow), nie wolno po nich szukać
    private String body;

}
