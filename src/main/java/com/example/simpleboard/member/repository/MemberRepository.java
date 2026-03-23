package com.example.simpleboard.member.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);

    Optional<MemberEntity> findFirstByLoginIdAndStatus(String loginId, String status);
}