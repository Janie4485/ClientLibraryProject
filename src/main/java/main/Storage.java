package main;

import main.model.ClientUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Storage {
    private static int currentID = 1;
    private static HashMap<Integer, ClientUser> clients = new HashMap<>();

    public static List<ClientUser> getAllClients() {
        return new ArrayList<>(clients.values());
    }

    public static int addClient(ClientUser client) {
        int id = currentID++;
        client.setId(id);
        clients.put(id, client);
        return id;
    }

    public static ClientUser getClient(int clientID) {
        return clients.get(clientID);
    }

    public static int remove(int clientID) {
        clients.remove(clientID);
        return clientID;
    }
}
