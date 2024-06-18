package main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import main.model.ClientUser;

@Repository
public interface AnotherClientRepository extends CrudRepository<ClientUser, Integer> {
    boolean existsByHetonghao(String hetonghao);
}
