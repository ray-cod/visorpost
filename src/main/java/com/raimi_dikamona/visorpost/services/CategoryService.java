package com.raimi_dikamona.visorpost.services;

import com.raimi_dikamona.visorpost.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
}
