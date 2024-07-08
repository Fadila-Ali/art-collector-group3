package com.group3.artcollectorregistration.service.impl;

import com.group3.artcollectorregistration.dto.ArtCollectorDto;
import com.group3.artcollectorregistration.entity.ArtCollector;
import com.group3.artcollectorregistration.entity.Role;
import com.group3.artcollectorregistration.repository.ArtCollectorRepository;
import com.group3.artcollectorregistration.repository.RoleRepository;
import com.group3.artcollectorregistration.service.ArtCollectorService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtCollectorServiceImpl implements ArtCollectorService {

    private ArtCollectorRepository artCollectorRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public ArtCollectorServiceImpl(ArtCollectorRepository artCollectorRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.artCollectorRepository = artCollectorRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveArtCollector(ArtCollectorDto artCollectorDto) {
        ArtCollector artCollector = new ArtCollector();

        artCollector.setUsername(artCollectorDto.getUsername());
        artCollector.setEmail(artCollectorDto.getEmail());
        artCollector.setPassword(passwordEncoder.encode(artCollectorDto.getPassword()));

        String roleName;
        if (artCollectorDto.isAdminRegistration()) {
            roleName = "admin";
        } else {
            roleName = "user";
        }

        //Check if role already exists in database, otherwise create it
        Role role = roleRepository.findByName(roleName);
        if(role == null){
            role = new Role();
            role.setName((roleName));
            roleRepository.save(role);
        }

        //Assign the role to the user
        artCollector.setRoles((Collections.singletonList(role)));
        artCollectorRepository.save(artCollector);
    }


    @Override
    public ArtCollector findArtCollectorByEmail(String email) {
        return artCollectorRepository.findByEmail(email);
    }

    @Override
    public List<ArtCollector> findAllArtCollector() {
        List<ArtCollector> artCollectors = artCollectorRepository.findAll();
        return artCollectors;
    }

    private ArtCollectorDto convertEntityToDto(List<ArtCollector> artCollectors) {
        ArtCollectorDto artCollectorDto = new ArtCollectorDto();
        artCollectorDto.setUsername(artCollectors.get(0).getUsername());
        artCollectorDto.setEmail(artCollectors.get(0).getEmail());
        artCollectorDto.setPassword(passwordEncoder.encode(artCollectors.get(0).getPassword()));
        return artCollectorDto;
    }
}
