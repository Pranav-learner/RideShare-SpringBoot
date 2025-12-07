package com.pranav.rideshare.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data                         // getters + setters + toString
@NoArgsConstructor            // empty constructor
@AllArgsConstructor           // full constructor
public class User {
    @Id
    private String id;

    private String username;
    private String password;
    private String role;
}
