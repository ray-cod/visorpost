package com.raimi_dikamona.visorpost.repositories;

import com.raimi_dikamona.visorpost.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
    int countByPost(Post post);
}
