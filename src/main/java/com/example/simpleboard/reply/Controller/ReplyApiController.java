package com.example.simpleboard.reply.Controller;


import com.example.simpleboard.crud.CRUDAbstractApiController;
import com.example.simpleboard.reply.db.ReplyEntity;
import com.example.simpleboard.reply.model.ReplyDto;
import com.example.simpleboard.reply.model.ReplyRequest;
import com.example.simpleboard.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyApiController extends CRUDAbstractApiController<ReplyDto, ReplyEntity> {

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
