package com.example.simpleboard.post.service;

import com.example.simpleboard.board.db.BoardRepository;
import com.example.simpleboard.common.Api;
import com.example.simpleboard.common.Pagination;
import com.example.simpleboard.post.db.PostEntity;
import com.example.simpleboard.post.db.PostRepository;
import com.example.simpleboard.post.model.PostDto;
import com.example.simpleboard.post.model.PostRequest;
import com.example.simpleboard.post.model.PostUpdateRequest;
import com.example.simpleboard.post.model.PostViewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final PostConverter postConverter;

    public PostDto create(PostRequest postRequest) {

        var boardEntity = boardRepository.findById(postRequest.getBoardId());

        if (boardEntity.isEmpty()) {
            throw new RuntimeException("해당 게시판을 찾을 수 없습니다. " + postRequest.getBoardId());
        }

        var response = PostEntity.builder()
                .board(boardEntity.get())
                .userName(postRequest.getUserName())
                .password(postRequest.getPassword())
                .email(postRequest.getEmail())
                .status("REGISTERED")
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .postedAt(LocalDateTime.now())
                .build();

        postRepository.save(response);

        return postConverter.toDto(response);
    }

    public PostDto view(PostViewRequest postViewRequest) {

        var result = postRepository.findFirstByIdAndStatusOrderByIdDesc(
                        postViewRequest.getPostId(),
                        "REGISTERED"
                )
                .map(it -> {
                    if (!it.getPassword().equals(postViewRequest.getPassword())) {
                        var format = "패스워드가 일치하지 않습니다. %s vs %s";
                        throw new RuntimeException(String.format(format, it.getPassword(), postViewRequest.getPassword()));
                    }
                    return it;
                })
                .orElseThrow(() ->
                        new RuntimeException("해당 게시글이 존재하지 않습니다 : " + postViewRequest.getPostId())
                );

        return postConverter.toDto(result);
    }

    public Api<List<PostEntity>> all(Pageable pageable) {

        var list = postRepository.findAll(pageable);

        var pagination = Pagination.builder()
                .page(list.getNumber())
                .size(list.getSize())
                .currentElement(list.getNumberOfElements())
                .totalElement(list.getTotalElements())
                .totalPage(list.getTotalPages())
                .build();

        return Api.<List<PostEntity>>builder()
                .body(list.toList())
                .pagination(pagination)
                .build();
    }

    public PostDto update(PostUpdateRequest postUpdateRequest) {

        var boardEntity = boardRepository.findById(postUpdateRequest.getBoardId());

        if (boardEntity.isEmpty()) {
            throw new RuntimeException("해당 게시판을 찾을 수 없습니다. " + postUpdateRequest.getBoardId());
        }

        var result = postRepository.findById(postUpdateRequest.getPostId())
                .map(it -> {
                    if (!it.getPassword().equals(postUpdateRequest.getPassword())) {
                        var format = "패스워드가 일치하지 않습니다. %s vs %s";
                        throw new RuntimeException(String.format(format, it.getPassword(), postUpdateRequest.getPassword()));
                    }

                    it.setBoard(boardEntity.get());
                    it.setUserName(postUpdateRequest.getUserName());
                    it.setEmail(postUpdateRequest.getEmail());
                    it.setTitle(postUpdateRequest.getTitle());
                    it.setContent(postUpdateRequest.getContent());

                    return postRepository.save(it);
                })
                .orElseThrow(() ->
                        new RuntimeException("해당 게시글이 존재하지 않습니다 : " + postUpdateRequest.getPostId())
                );

        return postConverter.toDto(result);
    }

    public void delete(PostViewRequest postViewRequest) {

        postRepository.findById(postViewRequest.getPostId())
                .map(it -> {
                    if (!it.getPassword().equals(postViewRequest.getPassword())) {
                        var format = "패스워드가 일치하지 않습니다. %s vs %s";
                        throw new RuntimeException(String.format(format, it.getPassword(), postViewRequest.getPassword()));
                    }
                    it.setStatus("UNREGISTERED");
                    postRepository.save(it);
                    return it;
                })
                .orElseThrow(() ->
                        new RuntimeException("해당 게시글이 존재하지 않습니다 : " + postViewRequest.getPostId())
                );
    }
}