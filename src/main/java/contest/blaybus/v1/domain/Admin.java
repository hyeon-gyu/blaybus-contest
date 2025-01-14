package contest.blaybus.v1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Admin {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identificationNumber;
    private String password;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role = Role.TYPE_ADMIN;
}
