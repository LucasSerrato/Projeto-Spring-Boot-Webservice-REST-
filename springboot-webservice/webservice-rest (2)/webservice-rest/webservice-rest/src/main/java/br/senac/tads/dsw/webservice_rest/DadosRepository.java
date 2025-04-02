package br.senac.tads.dsw.webservice_rest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DadosRepository extends JpaRepository<DadosEntity, Integer> {
}
