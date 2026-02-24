package com.devsuperior.projeto1.dscommerce.services;

import com.devsuperior.projeto1.dscommerce.dto.OrderDTO;
import com.devsuperior.projeto1.dscommerce.entities.Order;
import com.devsuperior.projeto1.dscommerce.repositories.OrderRepository;
import com.devsuperior.projeto1.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true) // Sim, exatamente! A anotação @Transactional em Java (especialmente com Spring Framework) indica que o metodo ou classe participa de uma transação do banco de dados.
    public OrderDTO findByID(Long id){
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso Não Encontrado!"));
        return new OrderDTO(order);
    }
}
