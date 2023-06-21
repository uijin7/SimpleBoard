package com.example.simpleboard.post.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Optional<PostEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, String status);

}
