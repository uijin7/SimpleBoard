package com.example.simpleboard.post.Controller;

import com.example.simpleboard.common.Api;
import com.example.simpleboard.post.db.PostEntity;
import com.example.simpleboard.post.model.PostDto;
import com.example.simpleboard.post.model.PostRequest;
import com.example.simpleboard.post.model.PostViewRequest;
import com.example.simpleboard.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @PostMapping("")
    public PostDto create(@Valid @RequestBody PostRequest postRequest) {

        return postService.create(postRequest);
    }

    @PostMapping("/view")
    public PostDto view(@RequestBody PostViewRequest postViewRequest) {

        var dto = postService.view(postViewRequest);

        log.info("result : {}", dto);

        return dto;
    }

    @GetMapping("/all")
    public Api<List<PostEntity>> list(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ){

        return postService.all(pageable);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody PostViewRequest postViewRequest) {

        postService.delete(postViewRequest);

    }
}
