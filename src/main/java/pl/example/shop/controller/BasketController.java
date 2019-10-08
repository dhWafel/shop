package pl.example.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.example.shop.domain.Basket;
import pl.example.shop.service.BasketService;

import java.util.List;

@RestController
@RequestMapping("/api/basket")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @GetMapping("/list")                //zwraca cala liste basket
    public List<Basket> getListBasket(){
        return basketService.getAllBasket();
    }

    @PostMapping
    public Basket createProduct(@RequestBody Basket basket) throws Exception {
        return basketService.createBasket(basket);
    }

    @GetMapping("/{id}")
    public Basket findBasket(@PathVariable Long id) throws Exception {
        return basketService.findById(id);
    }

    @DeleteMapping
    public void deleteBasket(@PathVariable Long id){
        basketService.deleteById(id);
    }

    @GetMapping                         //zwraca liste basket ale pogrupowana tzn stronami (page)
    public Page<Basket> basketPage(Pageable pageable){
        return basketService.basketPage(pageable);
    }

    @PutMapping
    public Basket update(@RequestBody Basket basket) throws Exception {
        return basketService.update(basket);
    }
}
