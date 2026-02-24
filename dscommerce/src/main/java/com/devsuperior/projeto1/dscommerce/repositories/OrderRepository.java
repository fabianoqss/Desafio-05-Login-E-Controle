package com.devsuperior.projeto1.dscommerce.repositories;

import com.devsuperior.projeto1.dscommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {


}
