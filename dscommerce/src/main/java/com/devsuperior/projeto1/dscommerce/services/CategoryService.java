package com.devsuperior.projeto1.dscommerce.services;

import com.devsuperior.projeto1.dscommerce.dto.CategoryDTO;
import com.devsuperior.projeto1.dscommerce.entities.Category;
import com.devsuperior.projeto1.dscommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<Category> result = categoryRepository.findAll();
        return result.stream().map(x -> new CategoryDTO(x)).toList();
    }
}
