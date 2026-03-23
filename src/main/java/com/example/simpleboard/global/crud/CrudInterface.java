package com.example.simpleboard.global.crud;

import com.example.simpleboard.global.api.Api;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CrudInterface<DTO> {

    DTO create(DTO dto);

    Optional<DTO> read(Long id);

    DTO update(DTO dto);

    void delete(Long id);

    Api<List<DTO>> list(Pageable pageable);
}
