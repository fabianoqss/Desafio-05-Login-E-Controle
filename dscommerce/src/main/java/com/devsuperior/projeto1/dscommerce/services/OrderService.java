package com.devsuperior.projeto1.dscommerce.services;

import com.devsuperior.projeto1.dscommerce.dto.OrderDTO;
import com.devsuperior.projeto1.dscommerce.dto.OrderItemDTO;
import com.devsuperior.projeto1.dscommerce.entities.*;
import com.devsuperior.projeto1.dscommerce.repositories.OrderItemRepository;
import com.devsuperior.projeto1.dscommerce.repositories.OrderRepository;
import com.devsuperior.projeto1.dscommerce.repositories.ProductRepository;
import com.devsuperior.projeto1.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderItemRepository orderItemRepository;


    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true) // Sim, exatamente! A anotação @Transactional em Java (especialmente com Spring Framework) indica que o metodo ou classe participa de uma transação do banco de dados.
    public OrderDTO findByID(Long id){
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso Não Encontrado!"));

        authService.validateSelfOrAdmin(order.getClient().getId());
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        Order order = new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        User user = userService.authenticated();
        order.setClient(user);

        for(OrderItemDTO itemDto : dto.getItems()){
            Product product = productRepository.getReferenceById(itemDto.getProductId());
            OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), itemDto.getPrice());
            order.getItems().add(item);
        }
        repository.save(order);
        orderItemRepository.saveAll(order.getItems());
        return new OrderDTO(order);
    }


}
