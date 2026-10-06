package com.artisthub.service;

import com.artisthub.entity.ArtClass;
import com.artisthub.repository.ArtClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtClassService {

    private final ArtClassRepository artClassRepository;

    @Autowired
    public ArtClassService(ArtClassRepository artClassRepository) {
        this.artClassRepository = artClassRepository;
    }

    public List<ArtClass> getClasses(String category, String level, String mode, String search, Double maxFee) {
        // If all parameters are null or empty, return all classes
        boolean hasFilter = (category != null && !category.trim().isEmpty())
                || (level != null && !level.trim().isEmpty())
                || (mode != null && !mode.trim().isEmpty())
                || (search != null && !search.trim().isEmpty())
                || (maxFee != null);

        if (!hasFilter) {
            return artClassRepository.findAll();
        }

        String catParam = (category != null && !category.trim().isEmpty()) ? category.trim() : null;
        String levelParam = (level != null && !level.trim().isEmpty()) ? level.trim() : null;
        String modeParam = (mode != null && !mode.trim().isEmpty()) ? mode.trim() : null;
        String searchParam = (search != null && !search.trim().isEmpty()) ? search.trim() : null;

        return artClassRepository.findWithFilters(catParam, levelParam, modeParam, searchParam, maxFee);
    }

    public Optional<ArtClass> getClassById(Long id) {
        return artClassRepository.findById(id);
    }

    public ArtClass saveClass(ArtClass artClass) {
        return artClassRepository.save(artClass);
    }

    public long getClassCount() {
        return artClassRepository.count();
    }
}
