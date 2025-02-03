package com.raimi_dikamona.visorpost.services;

import com.raimi_dikamona.visorpost.models.Post;
import com.raimi_dikamona.visorpost.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;

    public List<Post> getAllPosts(){
        return repository.findAll();
    }
}
