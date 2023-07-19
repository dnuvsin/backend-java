package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Products;

@RestController
public class ProductsController {
	
	private List<Products> dataProducts = new ArrayList<Products>();
	
	@GetMapping("/products")
	public List<Products> getProducts(){
		return dataProducts;
	}
	
	@PostMapping("/products")
	public Products addProducts(@RequestBody Products body) {
		for (int i = 0; i < dataProducts.size(); i++) {
			if (dataProducts.get(i).getProductId() == body.getProductId()) {
				return null;
			}
		}
		dataProducts.add(body);
		return body;
	}
	
	@GetMapping("/products/{productId}")
	public Products getProductsDetail(@PathVariable Integer productId) {
		for (int i = 0; i < dataProducts.size(); i++) {
			if (productId == dataProducts.get(i).getProductId()) {
				return dataProducts.get(i);
			}

		}
		return null;
	}
	
	@PutMapping("/products/{productId}")
	public Products updateProduct(@PathVariable Integer productId, @RequestBody Products body) {

		for (int i = 0; i < dataProducts.size(); i++) {
			if (productId == dataProducts.get(i).getProductId()) {
				dataProducts.get(i).setProductName(body.getProductName());
				dataProducts.get(i).setProductDetail(body.getProductDetail());
				dataProducts.get(i).setProductPrice(body.getProductPrice());
				dataProducts.get(i).setProductAmount(body.getProductAmount());
				return dataProducts.get(i);
			}
		}

		return null;
	}

	@DeleteMapping("/products/{productId}")
	public String deleteProduct(@PathVariable Integer productId) {
		for (int i = 0; i < dataProducts.size(); i++) {
			if (productId == dataProducts.get(i).getProductId()) {
				dataProducts.remove(i);
				return "Delete Success";
			}

		}

		return "Product not found";
	}

}
