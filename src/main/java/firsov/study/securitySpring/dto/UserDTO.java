package firsov.study.securitySpring.dto;

import com.sun.istack.NotNull;
import firsov.study.securitySpring.validation.PasswordMatches;
import firsov.study.securitySpring.validation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Data
@PasswordMatches
public class UserDTO {
    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    public UserDTO() {

    }
}
