package com.raimi_dikamona.visorpost.repositories;

import com.raimi_dikamona.visorpost.models.*;
import com.raimi_dikamona.visorpost.models.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query("SELECT p FROM Post p JOIN p.categories c WHERE c = :category")
    List<Post> findByCategory(@Param("category") Category category);


    // Find all published posts
    List<Post> findByStatus(PostStatus status);

    // Search posts by title containing a keyword (case-insensitive)
    List<Post> findByTitleContainingIgnoreCase(String keyword);

    @Query(value = "SELECT p FROM Post p " +
            "WHERE p.createdAt >= DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) " +
            "ORDER BY p.createdAt DESC", nativeQuery = true)
    List<Post> findRecentPosts();

}
