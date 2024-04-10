package ro.adapostcanin.adapostcanin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.adapostcanin.adapostcanin.entity.persoana.angajat.AngajatRepository;

import java.util.List;

import static java.util.Collections.singletonList;

@Service
@RequiredArgsConstructor
public class UserDetalisServiceImpl implements UserDetailsService {

    final AngajatRepository angajatRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var angajat = angajatRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        List<SimpleGrantedAuthority> authorities = singletonList(
                new SimpleGrantedAuthority("ROLE_" + angajat.getRole().name())
        );
        return new User(angajat.getUsername(), angajat.getPassword(), authorities);
    }
}