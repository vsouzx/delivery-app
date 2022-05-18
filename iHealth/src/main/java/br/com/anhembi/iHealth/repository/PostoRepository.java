package br.com.anhembi.iHealth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.anhembi.iHealth.modelo.Posto;
import br.com.anhembi.iHealth.modelo.Regiao;

@Repository
public interface PostoRepository extends JpaRepository<Posto, Long>{

	Posto findByNome(String regiao);

	Page<Posto> findAllByRegiao(Regiao regiao, Pageable pageable);
}
