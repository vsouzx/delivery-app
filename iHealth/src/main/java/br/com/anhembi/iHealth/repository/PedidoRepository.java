package br.com.anhembi.iHealth.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.anhembi.iHealth.modelo.Pedido;
import br.com.anhembi.iHealth.modelo.Status;
import br.com.anhembi.iHealth.modelo.User;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	List<Pedido> findAllByStatus(Status status, Sort sort);

	List<Pedido> findAllByUser(User user);

	List<Pedido> findAllByUserAndStatus(User user, Status recusado, Sort descending);

	List<Pedido> deleteAllByUser(User user);

	List<Pedido> findAllByUser(User user, Sort descending);

}
