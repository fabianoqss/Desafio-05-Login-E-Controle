package com.devsuperior.projeto1.dscommerce.repositories;

import com.devsuperior.projeto1.dscommerce.entities.OrderItem;
import com.devsuperior.projeto1.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
