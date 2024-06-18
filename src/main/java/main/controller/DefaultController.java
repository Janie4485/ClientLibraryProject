package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import main.repository.AnotherClientRepository;
import main.model.ClientUser;

import java.util.ArrayList;

@Controller
public class DefaultController {
    @Autowired
    AnotherClientRepository anotherClientRepository;

    @RequestMapping("/")
    public String index(Model model) {
        Iterable<ClientUser> clientUserIterable = anotherClientRepository.findAll();
        ArrayList<ClientUser> clientUsersList = new ArrayList<>();
        for(ClientUser clientUser : clientUserIterable) {
            clientUsersList.add(clientUser);
            model.addAttribute("clients",clientUsersList);
            model.addAttribute("ClientsCount",clientUsersList.size());
        }
        return "index";
    }
}
