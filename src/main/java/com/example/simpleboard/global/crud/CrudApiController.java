package com.example.simpleboard.global.crud;

import com.example.simpleboard.global.api.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public abstract class CrudApiController<DTO, ENTITY> implements CrudInterface<DTO> {

    @Autowired(required = false)
    private CrudService<DTO, ENTITY> crudService;

    @Override
    @PostMapping("")
    public DTO create(@Valid @RequestBody DTO dto) {
        return crudService.create(dto);
    }

    @Override
    @GetMapping("/id/{id}")
    public Optional<DTO> read(@PathVariable Long id) {
        return crudService.read(id);
    }

    @Override
    @PutMapping("")
    public DTO update(@Valid @RequestBody DTO dto) {
        return crudService.update(dto);
    }

    @Override
    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable Long id) {
        crudService.delete(id);
    }

    @Override
    @GetMapping("/all")
    public Api<List<DTO>> list(@PageableDefault Pageable pageable) {
        return crudService.list(pageable);
    }
}
