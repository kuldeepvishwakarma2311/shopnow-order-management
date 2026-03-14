package com.shopnow.order.service;

import com.shopnow.order.dto.CustomerDTO;
import java.util.List;

public interface CustomerService {
    CustomerDTO createCustomer(CustomerDTO request);
    List<CustomerDTO> getCustomers();
}
