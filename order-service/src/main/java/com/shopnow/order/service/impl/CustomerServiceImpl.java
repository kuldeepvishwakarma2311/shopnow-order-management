package com.shopnow.order.service.impl;

import com.shopnow.order.dto.CustomerDTO;
import com.shopnow.order.mapper.CustomerMapper;
import com.shopnow.order.model.Customer;
import com.shopnow.order.repository.CustomerRepository;
import com.shopnow.order.service.CustomerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerDTO createCustomer(CustomerDTO request) {
        Customer customer = customerMapper.toEntity(request);
        return customerMapper.toDto(customerRepository.save(customer));
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::toDto).toList();
    }
}
