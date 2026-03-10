package com.shopnow.orderapp.config;

import com.shopnow.orderapi.model.Customer;
import com.shopnow.orderapi.model.Inventory;
import com.shopnow.orderapi.model.Product;
import com.shopnow.orderapi.model.Warehouse;
import com.shopnow.orderservice.repository.CustomerRepository;
import com.shopnow.orderservice.repository.InventoryRepository;
import com.shopnow.orderservice.repository.ProductRepository;
import com.shopnow.orderservice.repository.WarehouseRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedOrderDomainData(
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            WarehouseRepository warehouseRepository,
            InventoryRepository inventoryRepository) {
        return args -> {
            if (customerRepository.count() > 0) {
                return;
            }

            Customer customer = new Customer();
            customer.setCustomerCode("CUST-1001");
            customer.setFullName("Aarav Mehta");
            customer.setEmail("aarav.mehta@shopnow.example");
            customer.setPhoneNumber("+91-9876543210");
            customer.setDefaultShippingAddress("12 Lake View Road, Bengaluru");
            customerRepository.save(customer);

            Product laptop = buildProduct("SKU-LAP-001", "Ultra Laptop 14", "Electronics", new BigDecimal("899.99"));
            Product earbuds = buildProduct("SKU-AUD-010", "Noise Canceling Earbuds", "Electronics", new BigDecimal("129.99"));
            productRepository.saveAll(List.of(laptop, earbuds));

            Warehouse bengaluru = buildWarehouse("WH-BLR", "Bengaluru Fulfillment Center", "Bengaluru", 1);
            Warehouse mumbai = buildWarehouse("WH-BOM", "Mumbai Fulfillment Center", "Mumbai", 2);
            warehouseRepository.saveAll(List.of(bengaluru, mumbai));

            inventoryRepository.saveAll(List.of(
                    buildInventory(laptop, bengaluru, 12, 0),
                    buildInventory(earbuds, bengaluru, 30, 0),
                    buildInventory(laptop, mumbai, 5, 0),
                    buildInventory(earbuds, mumbai, 20, 0)));
        };
    }

    private Product buildProduct(String sku, String name, String category, BigDecimal price) {
        Product product = new Product();
        product.setSku(sku);
        product.setProductName(name);
        product.setCategory(category);
        product.setUnitPrice(price);
        return product;
    }

    private Warehouse buildWarehouse(String code, String name, String city, int priority) {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseCode(code);
        warehouse.setWarehouseName(name);
        warehouse.setCity(city);
        warehouse.setRoutePriority(priority);
        warehouse.setActive(true);
        return warehouse;
    }

    private Inventory buildInventory(Product product, Warehouse warehouse, int available, int reserved) {
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setWarehouse(warehouse);
        inventory.setAvailableQuantity(available);
        inventory.setReservedQuantity(reserved);
        return inventory;
    }
}
