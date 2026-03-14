package com.shopnow.order.mapper;

import com.shopnow.order.dto.CustomerDTO;
import com.shopnow.order.model.Customer;
public interface CustomerMapper {
    CustomerDTO toDto(Customer entity);
    Customer toEntity(CustomerDTO dto);
}
