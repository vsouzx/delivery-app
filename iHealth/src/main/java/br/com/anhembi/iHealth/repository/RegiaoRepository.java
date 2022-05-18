package br.com.anhembi.iHealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.anhembi.iHealth.modelo.Regiao;

@Repository
public interface RegiaoRepository extends JpaRepository<Regiao, Long>{

	Regiao findByNome(String nomeRegiao);

}
