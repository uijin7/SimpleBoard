package com.example.simpleboard.reply.controller;

import com.example.simpleboard.global.crud.CrudApiController;
import com.example.simpleboard.reply.entity.ReplyEntity;
import com.example.simpleboard.reply.model.ReplyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyApiController extends CrudApiController<ReplyDto, ReplyEntity> {

//    private final ReplyService replyService;
//
//    @PostMapping("")
//    public ReplyDto create(
//            @Valid @RequestBody ReplyRequest replyRequest) {
//
//        return replyService.create(replyRequest);
//    }
//
//    @GetMapping("/all/{postId}")
//    public List<ReplyEntity> findAllByPostId(@PathVariable Long postId) {
//
//        return replyService.findAllByPostId(postId);
//    }
}
