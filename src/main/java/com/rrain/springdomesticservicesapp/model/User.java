package com.rrain.springdomesticservicesapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "users")
@Data @NoArgsConstructor
public class User {

    @Id private String id;
    @Indexed(unique = true, direction = IndexDirection.ASCENDING) // не влияет на работу монго, просто для читабельности кода походу
    private String login;
    private String password;
    @DBRef private Set<Role> roles;
    private String name;
    private boolean enabled;

    public User(String login, String password, Set<Role> roles, String name, boolean enabled) {
        this.login = login;
        this.password = password;
        this.roles = roles;
        this.name = name;
        this.enabled = enabled;
    }

    public User(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }
}
