package com.raimi_dikamona.visorpost.repositories;

import com.raimi_dikamona.visorpost.models.*;
import com.raimi_dikamona.visorpost.models.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Find a post by its title
    Optional<Post> findByTitle(String title);

    // Find all posts by a specific author
    List<Post> findByAuthor(User author);

    // Find all posts in a specific category
    List<Post> findByCategory(Category category);

    // Find all published posts
    List<Post> findByPostStatus(PostStatus status);

    // Search posts by title containing a keyword (case-insensitive)
    List<Post> findByTitleContainingIgnoreCase(String keyword);

    @Query( "SELECT p FROM Post p " +
            "WHERE p.createdAt >= CURRENT_DATE - 7 " +
            "ORDER BY p.createdAt DESC")
    List<Post> findRecentPosts();
}
