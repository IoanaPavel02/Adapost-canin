package ro.adapostcanin.adapostcanin.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AngajatUserDetails extends User {
    private final Long angajatId;

    public AngajatUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Long angajatId) {
        super(username, password, authorities);
        this.angajatId = angajatId;
    }

    public Long getAngajatId() {
        return angajatId;
    }
}