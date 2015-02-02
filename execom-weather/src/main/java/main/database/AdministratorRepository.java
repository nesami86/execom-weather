package main.database;

import main.entities.Administrator;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface AdministratorRepository extends CrudRepository<Administrator, Long> {

    Administrator findByUsername(String username);
}