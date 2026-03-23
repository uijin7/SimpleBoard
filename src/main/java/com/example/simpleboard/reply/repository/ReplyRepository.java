package com.example.simpleboard.reply.repository;

import com.example.simpleboard.post.entity.PostEntity;
import com.example.simpleboard.reply.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

    List<ReplyEntity> findAllByPostIdAndStatusOrderByIdDesc(Long postId, String status);
}
