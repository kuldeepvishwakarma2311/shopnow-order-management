package com.shopnow.order.mapper;

import com.shopnow.order.dto.CustomerDTO;
import com.shopnow.order.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerDTO toDto(Customer entity) {
        if (entity == null) {
            return null;
        }
        CustomerDTO dto = new CustomerDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setAddress(entity.getAddress());
        return dto;
    }

    @Override
    public Customer toEntity(CustomerDTO dto) {
        if (dto == null) {
            return null;
        }
        return Customer.builder()
            .id(dto.getId())
            .name(dto.getName())
            .email(dto.getEmail())
            .phone(dto.getPhone())
            .address(dto.getAddress())
            .build();
    }
}
