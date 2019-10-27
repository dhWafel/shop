package pl.example.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.example.shop.domain.Basket;
import pl.example.shop.service.BasketService;

import java.util.List;

@PreAuthorize("isAuthenticated()")              //funkcja z klasy SecurityExpressionRoot sprawdza czy uzytkownik jest zalogowany
@RestController
@RequestMapping("/api/basket")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @PreAuthorize("hasRole('ADMIN')")           //funkcja z klasy SecurityExpressionRoot sprawdza czy użytkownik jest role admine
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

    @GetMapping                         //zwraca liste przedmiotów dla użytkownika z basketu ale pogrupowana tzn stronami (page)
    public Page<Basket> basketPage(@RequestParam Integer page, @RequestParam Integer size){
        return basketService.basketPage(PageRequest.of(page, size));
    }

    @PutMapping
    public Basket update(@RequestBody Basket basket) throws Exception {
        return basketService.update(basket);
    }
}
