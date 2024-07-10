package com.group3.artcollectorregistration.security;

import com.group3.artcollectorregistration.entity.ArtCollector;
import com.group3.artcollectorregistration.entity.Role;
import com.group3.artcollectorregistration.repository.ArtCollectorRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomArtCollectorDetailsService implements UserDetailsService {

    private ArtCollectorRepository artCollectorRepository;

    public CustomArtCollectorDetailsService(ArtCollectorRepository artCollectorRepository){
        this.artCollectorRepository = artCollectorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        ArtCollector artCollector = artCollectorRepository.findByEmail(email);

        if (artCollector != null) {
//            return new org.springframework.security.core.userdetails.User(artCollector.getEmail(),
//                    artCollector.getPassword(),
//                    mapRolesToAuthorities(artCollector.getRoles()));

            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) mapRolesToAuthorities(artCollector.getRoles());

            System.out.println("User: " + artCollector.getEmail() + ", Roles: " + authorities);

            return new org.springframework.security.core.userdetails.User(artCollector.getEmail(),
                    artCollector.getPassword(),
                    authorities);
        }else{
            throw new UsernameNotFoundException("Invalid email or password.");
        }

    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {
        Collection< ? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return mapRoles;
    }
}
