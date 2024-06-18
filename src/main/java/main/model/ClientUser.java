package main.model;

import jakarta.persistence.*;

import javax.persistence.Entity;


@jakarta.persistence.Entity
@Table(name = "clients", uniqueConstraints = {@UniqueConstraint(columnNames = "hetonghao")})
public class ClientUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(name = "hetonghao", nullable = false)
    private String hetonghao;
    private String manager;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getHetonghao() { return hetonghao; }
    public void setHetonghao(String hetonghao) { this.hetonghao = hetonghao; }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
