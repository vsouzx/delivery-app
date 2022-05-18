package br.com.anhembi.iHealth.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.anhembi.iHealth.modelo.Comentario;
import br.com.anhembi.iHealth.modelo.User;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

	List<Comentario> findAllByUser(User user, Sort sort);

	List<Comentario>findAll(Sort sort);
}
