package com.devsuperior.projeto1.dscommerce.controllers;

import com.devsuperior.projeto1.dscommerce.dto.OrderDTO;
import com.devsuperior.projeto1.dscommerce.dto.UserDTO;
import com.devsuperior.projeto1.dscommerce.services.OrderService;
import com.devsuperior.projeto1.dscommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {


    @Autowired
    private OrderService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> getMe(@PathVariable Long id){ //
        OrderDTO dto = service.findByID(id);
        return ResponseEntity.ok(dto);
    }
}
