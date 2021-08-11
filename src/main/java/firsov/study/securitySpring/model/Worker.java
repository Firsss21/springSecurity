package firsov.study.securitySpring.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Worker {
    private Long id;
    private String firstName;
    private String lastName;
}
