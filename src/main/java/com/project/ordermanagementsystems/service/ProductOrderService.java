package com.project.ordermanagementsystems.service;

import com.project.ordermanagementsystems.exception.ResourceNotFoundException;
import com.project.ordermanagementsystems.model.Order;
import com.project.ordermanagementsystems.model.Product;
import com.project.ordermanagementsystems.model.ProductOrder;
import com.project.ordermanagementsystems.model.Stock;
import com.project.ordermanagementsystems.repository.ProductOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductOrderService {


    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private StockService stocksService;


    public List<ProductOrder> createProductOrders(Order order, List<Long> productsIds, List<Integer> quantities) {
        List<Product> products = productService.getProductsByIds(productsIds);

        if (products == null || products.size() != productsIds.size()) {
            throw new ResourceNotFoundException("No Products found for given ids");
        }


        List<ProductOrder> productOrders = new ArrayList<>();

        if(quantities == null){
            quantities = new ArrayList<>();
        }

        int quantitiesSize = quantities.size();
        if(productsIds.size() > quantitiesSize){
            for (int i = 0; i < (productsIds.size() - quantitiesSize); i++) {
                quantities.add(1);
            }
        }

        for (int i = 0; i < productsIds.size(); i++) {
            ProductOrder productOrder = new ProductOrder();
            productOrder.setProduct(products.get(i));
            productOrder.setOrder(order);
            productOrder.setCreatedAt(new Date());
            productOrder.setPrice(quantities.get(i) * products.get(i).getPrice());
            productOrder.setVat(products.get(i).getVat());
            productOrder.setQuantity(quantities.get(i));
            productOrders.add(productOrder);
            stocksService.updateStockByProductId(products.get(i).getId(), quantities.get(i));
            productOrderRepository.save(productOrder);
        }
        return productOrders;
    }

    public List<ProductOrder> updateProductOrders(Order order, List<Long> productsIds, List<Integer> quantities) {

        //  Re Adding quantities and remove product order
        for (ProductOrder productOrder: order.getProductOrders()){
            Stock stock = productOrder.getProduct().getStocks().get(0);
            stock.setQuantity(stock.getQuantity() + productOrder.getQuantity());
            productOrderRepository.deleteById(productOrder.getId());
        }

        List<Product> products = productService.getProductsByIds(productsIds);

        if (products == null || products.size() != productsIds.size()) {
            throw new ResourceNotFoundException("No Products found for given ids");
        }

        //  Define quantities to 1 if not found
        List<ProductOrder> productOrders = new ArrayList<>();
        if(quantities == null){
            quantities = new ArrayList<>();
        }

        int quantitiesSize = quantities.size();
        if(productsIds.size() > quantitiesSize){
            for (int i = 0; i < (productsIds.size() - quantitiesSize); i++) {
                quantities.add(1);
            }
        }

        //  Save Product Orders
        for (int i = 0; i < productsIds.size(); i++) {
            ProductOrder productOrder = new ProductOrder();
            productOrder.setProduct(products.get(i));
            productOrder.setOrder(order);
            productOrder.setCreatedAt(new Date());
            productOrder.setPrice(quantities.get(i) * products.get(i).getPrice());
            productOrder.setVat(products.get(i).getVat());
            productOrder.setQuantity(quantities.get(i));
            productOrders.add(productOrder);
            stocksService.updateStockByProductId(products.get(i).getId(), quantities.get(i));
            productOrderRepository.save(productOrder);
        }


        return productOrders;
    }

    public List<ProductOrder> getAllProductOrders() {
        return productOrderRepository.findAll();
    }

    public ProductOrder getProductOrderById(Long id) {
        Optional<ProductOrder> productOrderOptional = productOrderRepository.findById(id);
        if(!productOrderOptional.isPresent()){
            throw new ResourceNotFoundException("No ProductOrder with id: " + id);
        }
        return productOrderOptional.get();
    }

    public void deleteProductOrder(Long id) {
        productOrderRepository.deleteById(id);
    }

    public List<ProductOrder> getProductOrderByProductId(Long productId) {
        return productOrderRepository.findProductOrderByProductId(productId);
    }

    public List<ProductOrder> getProductOrderByOrderId(Long orderId) {
        return productOrderRepository.findProductOrderByOrderId(orderId);
    }

}
