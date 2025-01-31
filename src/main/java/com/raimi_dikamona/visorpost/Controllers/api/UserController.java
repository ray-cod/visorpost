package com.raimi_dikamona.visorpost.Controllers.api;

import com.raimi_dikamona.visorpost.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
}
