package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.ClientUser;
import main.repository.AnotherClientRepository;

import java.util.*;

@RestController
public class ClientController {

    @Autowired
    private AnotherClientRepository anotherClientRepository;

    /*@GetMapping("/clients/")
    public List<ClientUser> getAllClients() {
        return StreamSupport.stream(anotherClientRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }*/

    @GetMapping("/clients/")
    public List<ClientUser> getAllClients() {
        Iterable<ClientUser> clientUserIterable = anotherClientRepository.findAll();
        ArrayList<ClientUser> clientUsers = new ArrayList<>();
        for (ClientUser clientUser: clientUserIterable) {
            clientUsers.add(clientUser);
        }
        return clientUsers;
    }

    @PostMapping("/clients/")
    public ResponseEntity<Object> addClient(@RequestBody ClientUser client) {
        try {
            ClientUser savedClient = anotherClientRepository.save(client);
            return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Договор уже существует в системе", HttpStatus.BAD_REQUEST);
        }
    }
   /* @PutMapping
    public ResponseEntity updateClient(@RequestBody ClientUser client) {
        ClientUser updatedClient = anotherClientRepository.findById(client.getId()).orElse(null);
    }*/

    @PutMapping("/clients/{id}")
    public ResponseEntity<ClientUser> updateClient(@PathVariable int id, @RequestBody ClientUser updatedClient) {
        try {
            return anotherClientRepository.findById(id)
                    .map(client -> {
                        client.setHetonghao(updatedClient.getHetonghao());
                        client.setManager(updatedClient.getManager());
                        ClientUser savedClient = anotherClientRepository.save(client);
                        return new ResponseEntity<>(savedClient, HttpStatus.OK);
                    })
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity get(@PathVariable int id) {
        ClientUser client = anotherClientRepository.findById(id).orElse(null);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(client, HttpStatus.OK);
    }

    @DeleteMapping("/clients/{id}")
    public void removeClient(@PathVariable int id) {
        anotherClientRepository.deleteById(id);
    }
}

  /*@PostMapping("/clients/")
    public int add(ClientUser client) {
      try {
          ClientUser newClient = anotherClientRepository.save(client);
          return newClient.getId();
      } catch (DataIntegrityViolationException e) {
          System.out.println("Договор уже существует в системе!");
          System.out.println(HttpStatus.BAD_REQUEST);
          return 0;
      }

    }*/

  /*  @PostMapping("/clients")
    public ResponseEntity<ClientUser> add(@RequestBody ClientUser client) {
        try {
            ClientUser newClient = anotherClientRepository.save(client);
            return new ResponseEntity<>(newClient, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
