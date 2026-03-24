package com.example.simpleboard.board.service;

import com.example.simpleboard.board.entity.BoardEntity;
import com.example.simpleboard.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardInitializer implements ApplicationRunner {

    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (boardRepository.count() > 0) {
            return;
        }

        var defaultBoards = List.of(
                BoardEntity.builder()
                        .boardName("Free Board")
                        .status("REGISTERED")
                        .build(),
                BoardEntity.builder()
                        .boardName("Notice")
                        .status("REGISTERED")
                        .build()
        );

        boardRepository.saveAll(defaultBoards);
        log.info("Initialized default boards: {}", defaultBoards.size());
    }
}
