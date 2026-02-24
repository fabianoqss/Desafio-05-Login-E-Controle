package com.devsuperior.projeto1.dscommerce.services;

import com.devsuperior.projeto1.dscommerce.dto.CategoryDTO;
import com.devsuperior.projeto1.dscommerce.dto.ProductDTO;
import com.devsuperior.projeto1.dscommerce.entities.Category;
import com.devsuperior.projeto1.dscommerce.entities.Product;
import com.devsuperior.projeto1.dscommerce.repositories.ProductRepository;
import com.devsuperior.projeto1.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.projeto1.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService { // Essa vai ser minha classe de serviço , onde eu vou devolver os DTO aos Controllers, e o meu service irá buscar no banco de Dados

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true) // Sim, exatamente! A anotação @Transactional em Java (especialmente com Spring Framework) indica que o metodo ou classe participa de uma transação do banco de dados.
    public ProductDTO findByID(Long id){
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso Não Encontrado!"));
        return new ProductDTO(product);
    }


    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(String name, Pageable pageable){ // o Page seria minha coleção de dados do Java, e o Pageable seria a Interface.
        Page<Product> result = repository.searchByName(name, pageable); // Isso aqui vai no banco de dados
        return result.map(x -> new ProductDTO(x));
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto){
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto){
        try {
            Product entity = repository.getReferenceById(id); // Isso aqui não vai no banco de Dados
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
        return new ProductDTO(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)// So executa a transação se o metodo estiver no contexto de outra transação
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity){
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());

        entity.getCategories().clear();
        for(CategoryDTO catDto : dto.getCategories()){
            Category cat = new Category();
            cat.setId(catDto.getId());
            entity.getCategories().add(cat);
        }
    }
}
