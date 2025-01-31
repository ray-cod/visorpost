package com.raimi_dikamona.visorpost.services;

import com.raimi_dikamona.visorpost.repositories.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository repository;
}
