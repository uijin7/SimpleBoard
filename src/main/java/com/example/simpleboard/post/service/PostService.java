package com.example.simpleboard.post.service;

import com.example.simpleboard.board.repository.BoardRepository;
import com.example.simpleboard.global.api.Api;
import com.example.simpleboard.global.pagination.Pagination;
import com.example.simpleboard.global.time.TimeProvider;
import com.example.simpleboard.post.entity.PostEntity;
import com.example.simpleboard.post.model.PostDto;
import com.example.simpleboard.post.model.PostRequest;
import com.example.simpleboard.post.model.PostUpdateRequest;
import com.example.simpleboard.post.model.PostViewRequest;
import com.example.simpleboard.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final PostConverter postConverter;

    public PostDto create(PostRequest postRequest) {

        var boardEntity = boardRepository.findById(postRequest.getBoardId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "선택한 게시판을 찾을 수 없습니다."));

        var response = PostEntity.builder()
                .board(boardEntity)
                .userName(postRequest.getUserName())
                .password(postRequest.getPassword())
                .email(postRequest.getEmail())
                .status("REGISTERED")
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .postedAt(TimeProvider.nowInKorea())
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
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "게시글 비밀번호가 일치하지 않습니다.");
                    }
                    return it;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));

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

        var boardEntity = boardRepository.findById(postUpdateRequest.getBoardId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "선택한 게시판을 찾을 수 없습니다."));

        var result = postRepository.findById(postUpdateRequest.getPostId())
                .map(it -> {
                    if (!it.getPassword().equals(postUpdateRequest.getPassword())) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "게시글 수정에 실패했습니다. 비밀번호를 확인해주세요.");
                    }

                    it.setBoard(boardEntity);
                    it.setUserName(postUpdateRequest.getUserName());
                    it.setEmail(postUpdateRequest.getEmail());
                    it.setTitle(postUpdateRequest.getTitle());
                    it.setContent(postUpdateRequest.getContent());

                    return postRepository.save(it);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "수정할 게시글을 찾을 수 없습니다."));

        return postConverter.toDto(result);
    }

    public void delete(PostViewRequest postViewRequest) {

        postRepository.findById(postViewRequest.getPostId())
                .map(it -> {
                    if (!it.getPassword().equals(postViewRequest.getPassword())) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "게시글 삭제에 실패했습니다. 비밀번호를 확인해주세요.");
                    }
                    it.setStatus("UNREGISTERED");
                    postRepository.save(it);
                    return it;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제할 게시글을 찾을 수 없습니다."));
    }
}
