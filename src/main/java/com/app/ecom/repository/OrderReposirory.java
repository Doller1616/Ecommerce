package com.app.ecom.repository;

import com.app.ecom.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderReposirory extends JpaRepository<Order, Long> {
}
