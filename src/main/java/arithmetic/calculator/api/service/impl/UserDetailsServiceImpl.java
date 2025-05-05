package arithmetic.calculator.api.service.impl;

import arithmetic.calculator.api.persistence.model.UserModel;
import arithmetic.calculator.api.persistence.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = this.repository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Username or password incorrect"));
        return new User(userModel.getUsername(), userModel.getPassword(), true, true, true, true, List.of());
    }
}
