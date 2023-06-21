package com.example.simpleboard.post.service;

import com.example.simpleboard.post.db.PostEntity;
import com.example.simpleboard.post.model.PostDto;
import com.example.simpleboard.reply.service.ReplyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostConverter {

//    private final PostEntity postEntity;

    private final ReplyConverter replyConverter;

    public PostDto toDto(PostEntity postEntity) {

        var replyList = postEntity.getReplyList()
                .stream()
                .map(replyConverter::toDto)
                .collect(Collectors.toList());

        return PostDto.builder()
                .id(postEntity.getId())
                .boardId(postEntity.getBoard().getId())
                .userName(postEntity.getUserName())
                .password(postEntity.getPassword())
                .email(postEntity.getEmail())
                .status(postEntity.getStatus())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .postedAt(postEntity.getPostedAt())
                .replyList(replyList)
                .build()
                ;
    }
}
