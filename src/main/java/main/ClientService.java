package main;

import jakarta.transaction.Transactional;
import main.model.ClientUser;
import main.repository.AnotherClientRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private AnotherClientRepository anotherClientRepository;
    @Transactional
    public ClientUser addClient(ClientUser client) {
        if (anotherClientRepository.existsByHetonghao(client.getHetonghao())) {
            throw new DataIntegrityViolationException("Договор уже существует в системе");
        }
        return anotherClientRepository.save(client);
    }
}
