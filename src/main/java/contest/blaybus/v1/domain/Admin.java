package contest.blaybus.v1.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Admin {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identificationNumber;
    private String password;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

}
