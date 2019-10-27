package pl.example.shop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.example.shop.domain.ClientOrder;
import pl.example.shop.service.ClientOrderService;

import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/order")
public class ClientOrderController {

    @Autowired
    private ClientOrderService clientOrderService;

    @PostMapping
    public List<ClientOrder> createOrder(){
        return clientOrderService.createOrder();
    }

    @GetMapping("/list")
    public Page<ClientOrder> getListClientOrder(@RequestParam Integer page, @RequestParam Integer size){
        return clientOrderService.getAllOrder(PageRequest.of(page, size));
    }

    @GetMapping
    public Page<ClientOrder> getListClientsOrderById(@RequestParam Integer page, @RequestParam Integer size) throws Exception {
        return clientOrderService.getOrderByIdUser(PageRequest.of(page, size));
    }
}