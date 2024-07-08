package com.group3.artcollectorregistration.service;

import com.group3.artcollectorregistration.dto.ArtCollectorDto;
import com.group3.artcollectorregistration.entity.ArtCollector;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ArtCollectorService {
    void saveArtCollector(ArtCollectorDto artCollectorDto);
    ArtCollector findArtCollectorByEmail(String email);
    List<ArtCollector> findAllArtCollector();
}
