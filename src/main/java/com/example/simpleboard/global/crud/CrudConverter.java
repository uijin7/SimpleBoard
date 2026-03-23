package com.example.simpleboard.global.crud;


public interface CrudConverter<DTO, ENTITY> {

    DTO toDto(ENTITY entity);

    ENTITY toEntity(DTO dto);
}
