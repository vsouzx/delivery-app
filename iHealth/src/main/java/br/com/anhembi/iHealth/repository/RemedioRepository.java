package br.com.anhembi.iHealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.anhembi.iHealth.modelo.Remedio;

@Repository
public interface RemedioRepository extends JpaRepository<Remedio, Long>{

}
