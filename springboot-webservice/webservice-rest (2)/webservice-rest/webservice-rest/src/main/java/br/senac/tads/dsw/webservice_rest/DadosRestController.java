package br.senac.tads.dsw.webservice_rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dados") // URL BASE: http://localhost:8080/api/dados
public class DadosRestController {

    private final DadosService service;

   
    public DadosRestController(DadosService service) {
        this.service = service;
    }

   
    @GetMapping
    public List<DadosDto> findAll() {
        return service.findAll();
    }

  
    @GetMapping("/{id}")
    public DadosDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

   
    @PostMapping
    public ResponseEntity<DadosDto> create(@Valid @RequestBody DadosDto dto) {
        DadosDto novoDado = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoDado);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<DadosDto> update(@PathVariable Integer id, @Valid @RequestBody DadosDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

  
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

   
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
