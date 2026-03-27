package com.example.simpleboard.member.repository;

import com.example.simpleboard.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);

    Optional<MemberEntity> findFirstByLoginIdAndStatus(String loginId, String status);

    List<MemberEntity> findAllByStatusOrderByIdDesc(String status);
}
