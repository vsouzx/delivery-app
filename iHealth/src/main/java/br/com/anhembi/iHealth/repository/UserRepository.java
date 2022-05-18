package br.com.anhembi.iHealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.anhembi.iHealth.modelo.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByCpf(String cpf);

	User getUserByCpf(String username);

	
}
