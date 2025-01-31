package com.raimi_dikamona.visorpost.Controllers.api;

import com.raimi_dikamona.visorpost.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;
}
