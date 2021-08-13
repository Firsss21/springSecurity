package firsov.study.securitySpring.service;

import firsov.study.securitySpring.exception.UserNotFoundException;
import firsov.study.securitySpring.model.PasswordResetToken;
import firsov.study.securitySpring.model.User;
import firsov.study.securitySpring.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class TokenService {
    @Autowired
    PasswordResetTokenRepository tokenRepo;

    public String validatePasswordResetToken(String token) {
        final Optional<PasswordResetToken> passToken = tokenRepo.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(Optional<PasswordResetToken> passToken) {
        return passToken.isPresent();
    }

    private boolean isTokenExpired(Optional<PasswordResetToken> passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.get().getExpiryDate().before(cal.getTime());
    }

    public User getUserByToken(String token) throws UserNotFoundException {
        final PasswordResetToken passToken = tokenRepo.findByToken(token).get();

        if (passToken == null) {
            throw new UserNotFoundException("User with this token not found");
        }

        return passToken.getUser();
    }
}
