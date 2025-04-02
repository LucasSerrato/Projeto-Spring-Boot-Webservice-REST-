package br.senac.tads.dsw.webservice_rest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DadosService {

    private final DadosRepository dadosRepository;

    public DadosService(DadosRepository dadosRepository) {
        this.dadosRepository = dadosRepository;
    }

  
    public List<DadosDto> findAll() {
        return dadosRepository.findAll().stream()
                .map(entity -> new DadosDto(
                        entity.getId(),
                        entity.getNome(),
                        entity.getDataNascimento(),
                        entity.getEmail(),
                        entity.getTelefone()))
                .collect(Collectors.toList());
    }

 
    public DadosDto findById(Integer id) {
        return dadosRepository.findById(id)
                .map(entity -> new DadosDto(
                        entity.getId(),
                        entity.getNome(),
                        entity.getDataNascimento(),
                        entity.getEmail(),
                        entity.getTelefone()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado"));
    }

 
    @Transactional
    public DadosDto create(DadosDto dadosDto) {
        DadosEntity entity = new DadosEntity(
                dadosDto.getNome(),
                dadosDto.getDataNascimento(),
                dadosDto.getEmail(),
                dadosDto.getTelefone()
        );
        DadosEntity savedEntity = dadosRepository.save(entity);
        return new DadosDto(
                savedEntity.getId(),
                savedEntity.getNome(),
                savedEntity.getDataNascimento(),
                savedEntity.getEmail(),
                savedEntity.getTelefone()
        );
    }

  
    @Transactional
    public DadosDto update(Integer id, DadosDto dadosDto) {
        DadosEntity entity = dadosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado"));

        entity.setNome(dadosDto.getNome());
        entity.setDataNascimento(dadosDto.getDataNascimento());
        entity.setEmail(dadosDto.getEmail());
        entity.setTelefone(dadosDto.getTelefone());

        DadosEntity updatedEntity = dadosRepository.save(entity);
        return new DadosDto(
                updatedEntity.getId(),
                updatedEntity.getNome(),
                updatedEntity.getDataNascimento(),
                updatedEntity.getEmail(),
                updatedEntity.getTelefone()
        );
    }

    
    @Transactional
    public void delete(Integer id) {
        if (!dadosRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado");
        }
        dadosRepository.deleteById(id);
    }
}
