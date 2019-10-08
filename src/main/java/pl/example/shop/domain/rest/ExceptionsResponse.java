package pl.example.shop.domain.rest;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionsResponse {


    private Map<String, String> message;
    private  LocalDateTime dateOccurse;


}
