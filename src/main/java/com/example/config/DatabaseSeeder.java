package com.example.config;

import com.example.entity.*;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Seed Admin User
            User admin = new User();
            admin.setUserName("admin");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setUserFname("System");
            admin.setUserLname("Administrator");
            userRepository.save(admin);

            // Seed Categories
            Category electronics = new Category();
            electronics.setCategoryName("Electronics");
            categoryRepository.save(electronics);

            Category apparel = new Category();
            apparel.setCategoryName("Apparel");
            categoryRepository.save(apparel);

            // Seed Products
            Product p1 = new Product();
            p1.setProductName("Laptop Pro");
            p1.setCategory(electronics);
            p1.setProductbuyingPrice(800.0);
            p1.setProductsellingPrice(1200.0);
            productRepository.save(p1);

            Product p2 = new Product();
            p2.setProductName("Wireless Mouse");
            p2.setCategory(electronics);
            p2.setProductbuyingPrice(15.0);
            p2.setProductsellingPrice(40.0);
            productRepository.save(p2);

            Product p3 = new Product();
            p3.setProductName("Cotton T-Shirt");
            p3.setCategory(apparel);
            p3.setProductbuyingPrice(5.0);
            p3.setProductsellingPrice(20.0);
            productRepository.save(p3);

            // Seed Suppliers
            Supplier s1 = new Supplier();
            s1.setSupplierName("Tech Distro");
            s1.setSupplierCompany("Tech Distro Inc");
            s1.setSupplierContact(new BigDecimal("18005550199"));
            supplierRepository.save(s1);

            // Seed Stock
            Stock stock1 = new Stock();
            stock1.setProduct(p1);
            stock1.setCategory(electronics);
            stock1.setSupplier(s1);
            stock1.setQuantity(50);
            stock1.setDateStock(new Date());
            stock1.setBranchId("BR-100");
            stockRepository.save(stock1);
            
            Stock stock2 = new Stock();
            stock2.setProduct(p2);
            stock2.setCategory(electronics);
            stock2.setSupplier(s1);
            stock2.setQuantity(200);
            stock2.setDateStock(new Date());
            stock2.setBranchId("BR-100");
            stockRepository.save(stock2);
        }
    }
}
