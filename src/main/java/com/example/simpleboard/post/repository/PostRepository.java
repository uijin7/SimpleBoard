package com.example.simpleboard.post.repository;

import com.example.simpleboard.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Optional<PostEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, String status);

}
