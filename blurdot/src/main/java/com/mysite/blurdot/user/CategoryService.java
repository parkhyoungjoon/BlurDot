package com.mysite.blurdot.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
    	return categoryRepository.findAll();
    }
    
    public Optional<Category> findByCategoryId(Integer id) {
    	return categoryRepository.findById(id);
    }
}