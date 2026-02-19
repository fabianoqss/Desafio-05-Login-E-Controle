package com.devsuperior.projeto1.dscommerce.controllers;

import com.devsuperior.projeto1.dscommerce.dto.ProductDTO;
import com.devsuperior.projeto1.dscommerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

//Minha Classe de Controllers sempre irá devolver uma resposta ao front-end
@RestController // Essa anotation vai configurar , para que quando eu rodar a aplicação ele irá responder pela WEB
@RequestMapping(value = "/products") // Isso seria minha rota ou recurso -> Endpoints
// Meus Controllers que vão implementar esses recursos
public class ProductController {

    @Autowired // Injetando as dependências dos services na minha classe
    private ProductService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity <ProductDTO> findById(@PathVariable Long id){ // Parâmetro de rota.
        ProductDTO dto = service.findByID(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(@RequestParam(name = "", defaultValue = "") String name, Pageable pageable){
        Page<ProductDTO> dto = service.findAll(name, pageable);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto){ // Isso basicamente é para casar o Corpo da requisição com o meu DTO.
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto){
         dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
