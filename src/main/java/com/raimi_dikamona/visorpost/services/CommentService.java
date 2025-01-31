package com.raimi_dikamona.visorpost.services;

import com.raimi_dikamona.visorpost.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;
}
