package com.raimi_dikamona.visorpost.services;

import com.raimi_dikamona.visorpost.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;
}
