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
            categoryCount = toList(categoryService.findAll()).size();
            productCount = toList(productService.findAll()).size();
            stockCount = toList(stockService.findAll()).size();
            supplierCount = toList(supplierService.findAll()).size();
            invoiceCount = toList(invoiceService.findAll()).size();
            pricingCount = toList(pricingService.findAll()).size();
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
