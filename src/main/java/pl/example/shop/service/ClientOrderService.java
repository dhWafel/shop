package pl.example.shop.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.example.shop.domain.Basket;
import pl.example.shop.domain.ClientOrder;
import pl.example.shop.repository.BasketRepository;
import pl.example.shop.repository.ClientOrderRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientOrderService {

    @Autowired
    private ClientOrderRepository clientOrderRepository;
    @Autowired
    private BasketRepository basketRepository;


    public List<ClientOrder> createOrder(Long id) {
        List<Basket> baskets = basketRepository.findByUserId(id);

        String orderId = UUID.randomUUID().toString();

        List<ClientOrder> clientOrders = baskets
                .stream()
                .map(b -> {
                    b.getProduct().setQuantity(b.getProduct().getQuantity() - b.getQuantity());

                    return ClientOrder.builder()
                            .orderNumber(orderId)
                            .product(b.getProduct())
                            .user(b.getUser())
                            .quantity(b.getQuantity())
                            .build();
                })
                .collect(Collectors.toList());
        Double sumOrder = clientOrders
                .stream()
                .mapToDouble(o -> o.getQuantity() * o.getProduct().getPrice())
                .sum();
        clientOrders = clientOrders
                .parallelStream()
                .map(o -> {
                    o.setOrderValue(sumOrder);
                    return o;
                })
                .collect(Collectors.toList());


        basketRepository.deleteAll(baskets);

        return clientOrderRepository.saveAll(clientOrders);
    }

    public Page<ClientOrder> getAllOrder(Pageable pageable) {
        return clientOrderRepository.findAll(pageable);
    }

    public Page<ClientOrder> getOrderByIdUser(Long id, Pageable pageable) throws Exception {
        return clientOrderRepository.findByUserId(id, pageable);
    }


}
