package firsov.study.securitySpring.service;

import firsov.study.securitySpring.dto.UserDTO;
import firsov.study.securitySpring.exception.UserAlreadyExistException;
import firsov.study.securitySpring.model.Role;
import firsov.study.securitySpring.model.Status;
import firsov.study.securitySpring.model.User;
import firsov.study.securitySpring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    public User registerNewUserAccount(UserDTO userDto) throws UserAlreadyExistException {
        if (emailExist(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + userDto.getEmail());
        }

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);

        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }
}
