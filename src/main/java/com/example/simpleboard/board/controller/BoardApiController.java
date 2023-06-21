package com.example.simpleboard.board.controller;

import com.example.simpleboard.board.db.BoardRepository;
import com.example.simpleboard.board.model.BoardDto;
import com.example.simpleboard.board.model.BoardRequest;
import com.example.simpleboard.board.service.BoardConverter;
import com.example.simpleboard.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardApiController {

    public final BoardService boardService;
    private final BoardRepository boardRepository;
    private final BoardConverter boardConverter;
    @PostMapping("")
    public BoardDto create(
            @Valid @RequestBody BoardRequest boardRequest) {

        return boardService.create(boardRequest);
    }

    @GetMapping("/id/{id}")
    public BoardDto view(
            @PathVariable Long id) {

        var dto = boardService.view(id);

        log.info("result : {}", dto);

        return dto;
    }

    @GetMapping("/ids")
    public List<BoardDto> all(
//            @PathVariable Long id
    ) {

        return boardService.all();
    }
}
