package com.raimi_dikamona.visorpost.Controllers.api;

import com.raimi_dikamona.visorpost.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;
}
