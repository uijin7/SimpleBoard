package com.example.simpleboard.board.service;

import com.example.simpleboard.board.db.BoardEntity;
import com.example.simpleboard.board.db.BoardRepository;
import com.example.simpleboard.board.model.BoardDto;
import com.example.simpleboard.board.model.BoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardConverter boardConverter;

    public BoardDto create(BoardRequest boardRequest) {

        var response = BoardEntity.builder()
                .boardName(boardRequest.getBoardName())
                .status("REGISTERED")
                .build()
                ;

        var saveEntity = boardRepository.save(response);
        return boardConverter.toDto(saveEntity);
    }

    public BoardDto view(Long id) {

        var entity = boardRepository.findById(id).get();

        return boardConverter.toDto(entity);
    }

    public List<BoardDto> all() {
        return boardRepository.findAll()
                .stream()
                .map(boardConverter::toDto)
                .collect(Collectors.toList());
    }
}
