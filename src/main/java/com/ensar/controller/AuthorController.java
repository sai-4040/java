package com.ensar.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ensar.entity.Epic;
import com.ensar.entity.elastic.Article;
import com.ensar.repository.elastic.ArticleRepository;
import com.ensar.request.dto.CreateUpdateEpicDto;
import com.ensar.service.EpicService;


import javax.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Api(tags = "Author Elastic Mgmt")
@RestController
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/author")
public class AuthorController {

	@Autowired
    private ArticleRepository articleRepository;

    
   

        
    @GetMapping
    
    public ResponseEntity<Iterable<Article>> getArticles() {
    	
        return ResponseEntity.ok(articleRepository.findAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Article> createArticle(@Valid @RequestBody Article article) {
        return ResponseEntity.ok(articleRepository.save(article));
    }

    
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<String> deleteEpic(@PathVariable String id) {
    	articleRepository.deleteById(id);
    	
        return ResponseEntity.ok(id);
    }

    
}
