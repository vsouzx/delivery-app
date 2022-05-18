package br.com.anhembi.iHealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.anhembi.iHealth.modelo.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String string);

}
