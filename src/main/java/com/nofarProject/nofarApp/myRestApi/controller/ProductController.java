package com.nofarProject.nofarApp.myRestApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nofarProject.nofarApp.myRestApi.entities.Product;
import com.nofarProject.nofarApp.myRestApi.services.ProductJPARepository;

@RestController
public class ProductController {

	@Autowired
	private ProductJPARepository product_service;

	@GetMapping("products")
	public List<Product> getProducts() {
		return product_service.findAll();
	}

	@GetMapping("products/{id}")
	public Product getProductById(@PathVariable int id) {
		return product_service.findById(id).get();
	}
	@GetMapping("products/Withdrawal/{id}")
	public int getProductWithdrawal(@PathVariable int id) {
		return product_service.findById(id).get().getWithdrawal();
	}
	@GetMapping("products/Deposit/{id}")
	public int getProductDeposit(@PathVariable int id) {
		return product_service.findById(id).get().getDeposit();
	}

	@DeleteMapping("products/{id}") 
	public String deleteProductById(@PathVariable int id) {
		Product product = product_service.findById(id).get();
		if (product == null)
			return "this product is not exist";
		else 
		{
			product_service.deleteById(id);
			return "OK";
		}
	}

	@DeleteMapping("products/{id}/{amount}") 
	public String deleteAmountProduct(@PathVariable int id, int amount) {
		if(product_service.getOne(id) != null && amount > 0) {
			int oldAmount = product_service.getOne(id).getAmount();
			if (oldAmount - amount >= 0) {
				product_service.getOne(id).setAmount(oldAmount-amount);
				product_service.getOne(id).setDeposit(product_service.getOne(id).getDeposit()+amount);
				product_service.save(product_service.getOne(id));
				return "OK";
			}
			else // oldAmount < amount
				return "ERROR - oldAmount less from amount";
		}
		else 
			return "ERROR - product not exist or amount < 0";
	}
		@PostMapping("products") 
		public String addNewProduct(@RequestBody Product newProduct) {
			if(newProduct != null) {
				product_service.save(newProduct);
				return "OK";
			}
			else 
				return "ERROR-product is null";	
		}

		@PostMapping("products/{id}/{amount}") 
		public String addAmountProduct(@PathVariable int id, int amount) {
			if(product_service.getOne(id) != null && amount > 0) {
				int oldAmount = product_service.getOne(id).getAmount();
				product_service.getOne(id).setAmount(oldAmount+amount);
				
				product_service.getOne(id).setWithdrawal(product_service.getOne(id).getWithdrawal()+amount);
				product_service.save(product_service.getOne(id));
				return "OK";
			}
			else 
				return "ERROR - product is null or amount < 0";


		}
	}
