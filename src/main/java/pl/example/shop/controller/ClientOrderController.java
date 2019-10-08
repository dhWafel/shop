package pl.example.shop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.example.shop.domain.ClientOrder;
import pl.example.shop.service.ClientOrderService;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class ClientOrderController {

    @Autowired
    private ClientOrderService clientOrderService;

    @PostMapping("/{id}")
    public List<ClientOrder> createOrder(@PathVariable Long id){
        return clientOrderService.createOrder(id);
    }

    @GetMapping("/list")
    public Page<ClientOrder> getListClientOrder(Pageable pageable){
        return clientOrderService.getAllOrder(pageable);
    }

    @GetMapping("/{id}")
    public Page<ClientOrder> getListClientsOrderById(@PathVariable Long id, Pageable pageable) throws Exception {
        return clientOrderService.getOrderByIdUser(id, pageable);
    }
}