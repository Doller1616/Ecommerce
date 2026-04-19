package com.app.ecom.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderReposirory extends JpaRepository<Order, Long> {
}
