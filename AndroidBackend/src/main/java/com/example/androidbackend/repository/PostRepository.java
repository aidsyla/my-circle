package com.example.androidbackend.repository;

import com.example.androidbackend.models.Posts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
@Transactional
public interface PostRepository extends JpaRepository<Posts, Integer> {
    Optional<Posts> findById(int id);
    Optional<Posts> findByName(String name);
    Optional<Posts> getPostsByUsername(String username);
    List<Posts> getAllByUsername(String username);

    @Override
    List<Posts> findAll();
}
