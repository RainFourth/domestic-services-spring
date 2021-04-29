package com.rrain.springdomesticservicesapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
@Data @NoArgsConstructor
public class Role {
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    @Id private String id;
    @Indexed(unique = true, direction = IndexDirection.ASCENDING) // не влияет на работу монго, просто для читабельности кода походу
    private String role;

    public Role(String role) {
        this.role = role;
    }
}
