package pl.example.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.example.shop.domain.Basket;
import pl.example.shop.domain.Product;
import pl.example.shop.domain.User;
import pl.example.shop.repository.BasketRepository;

import java.util.List;


@Service
public class BasketService {

    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    public Basket createBasket(Basket basket) throws Exception {
        User user = userService.findById(basket.getUser().getId());
        Product product = productService.findById(basket.getProduct().getId());

        if(product.getQuantity() < basket.getQuantity()){
            throw new Exception("There are not so many products");
        }
        basket.setProduct(product);
        basket.setUser(user);
        return basketRepository.save(basket);
    }

    public List<Basket> getAllBasket(){
        return basketRepository.findAll();
    }

    public void deleteById(Long id){
        basketRepository.deleteById(id);
    }

    public Basket findById(Long id) throws Exception {
        return basketRepository.findById(id).orElseThrow(() -> new Exception("Basket is not found!!!"));
    }

    public Page<Basket> basketPage(@PageableDefault Pageable pageable) {
        String getEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return basketRepository.findByUserEmail(getEmail, pageable);
    }

    public Basket update(Basket basket) throws Exception {
        return basketRepository.findById(basket.getId()).map(b -> {
            if(!b.getQuantity().equals(basket.getQuantity())){
                b.setQuantity(basket.getQuantity());
            }
            if(!b.getUser().equals(basket.getUser())){
                b.setUser(basket.getUser());
            }
            if(!b.getProduct().equals(basket.getProduct())){
                b.setProduct(basket.getProduct());
            }

            return basketRepository.save(b);

        }).orElseThrow(()->new Exception("No data"));
    }
}
