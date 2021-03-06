package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;

	
	@Transactional(readOnly = true)
	public Page<ProductDTO> find(PageRequest pageRequest) {		
		Page<Product> page = repository.findAll(pageRequest);
		// Guarda em memória o mapa de identidade.
		repository.findProductsCategories(page.stream().collect(Collectors.toList()));
		
		//O Jpa vai verificar que as categorias estão em memória e não vai buscar 
		//no Banco de Dados o mesmo objeto mais de uma vez no mesmo contexto.  
		return page.map(x -> new ProductDTO(x));		
	}
	
	/*
	@Transactional(readOnly = true)
	public List<ProductDTO> find(PageRequest pageRequest) {
		List<Product> list = repository.findProductsCategories();
		Page<Product> pages = repository.findAll(pageRequest);
		return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
	}
	*/
	
}
