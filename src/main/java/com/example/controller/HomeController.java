package com.example.controller;

import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private StockService stockService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private PricingService pricingService;

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("appName", "Inventory Management System");
        model.addAttribute("version", "1.0.0");

        long categoryCount = 0;
        long productCount = 0;
        long stockCount = 0;
        long supplierCount = 0;
        long invoiceCount = 0;
        long pricingCount = 0;

        try {
            List<com.example.entity.Category> categoriesList = toList(categoryService.findAll());
            categoryCount = categoriesList.size();
            
            List<com.example.entity.Product> productsList = toList(productService.findAll());
            productCount = productsList.size();
            
            stockCount = toList(stockService.findAll()).size();
            supplierCount = toList(supplierService.findAll()).size();
            invoiceCount = toList(invoiceService.findAll()).size();
            pricingCount = toList(pricingService.findAll()).size();

            // Prepare chart data: Number of products per category
            java.util.Map<String, Long> productsByCategory = productsList.stream()
                .filter(p -> p.getCategory() != null)
                .collect(java.util.stream.Collectors.groupingBy(
                    p -> p.getCategory().getCategoryName(),
                    java.util.stream.Collectors.counting()
                ));
            
            model.addAttribute("chartLabels", productsByCategory.keySet());
            model.addAttribute("chartData", productsByCategory.values());

        } catch (Exception e) {
            // Tables might not have data yet
        }

        model.addAttribute("categoryCount", categoryCount);
        model.addAttribute("productCount", productCount);
        model.addAttribute("stockCount", stockCount);
        model.addAttribute("supplierCount", supplierCount);
        model.addAttribute("invoiceCount", invoiceCount);
        model.addAttribute("pricingCount", pricingCount);

        return "index";
    }

    @GetMapping("/web/categories")
    public String categories(Model model) {
        model.addAttribute("categories", toList(categoryService.findAll()));
        return "categories";
    }

    @GetMapping("/web/products")
    public String products(Model model) {
        model.addAttribute("products", toList(productService.findAll()));
        return "products";
    }

    @GetMapping("/web/stock")
    public String stock(Model model) {
        model.addAttribute("stocks", toList(stockService.findAll()));
        return "stock";
    }

    @GetMapping("/web/suppliers")
    public String suppliers(Model model) {
        model.addAttribute("suppliers", toList(supplierService.findAll()));
        return "suppliers";
    }

    @GetMapping("/web/invoices")
    public String invoices(Model model) {
        model.addAttribute("invoices", toList(invoiceService.findAll()));
        return "invoices";
    }

    @GetMapping("/web/pricing")
    public String pricing(Model model) {
        model.addAttribute("pricings", toList(pricingService.findAll()));
        return "pricing";
    }
}
