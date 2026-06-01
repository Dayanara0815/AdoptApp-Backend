package com.utp.adoptappbackend.adoption.service.impl;

import com.utp.adoptappbackend.adoption.mapper.AdoptionMapper;
import com.utp.adoptappbackend.adoption.model.Adoption;
import com.utp.adoptappbackend.adoption.model.dto.AdoptionRequest;
import com.utp.adoptappbackend.adoption.model.dto.AdoptionResponse;
import com.utp.adoptappbackend.adoption.repository.AdoptionRepository;
import com.utp.adoptappbackend.adoption.service.AdoptionService;
import com.utp.adoptappbackend.common.exception.ApiValidateException;
import com.utp.adoptappbackend.common.model.PageResponse;
import com.utp.adoptappbackend.common.model.enumeration.Status;
import com.utp.adoptappbackend.pet.model.Pet;
import com.utp.adoptappbackend.pet.repository.PetRepository;
import com.utp.adoptappbackend.user.model.User;
import com.utp.adoptappbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdoptionServiceImpl implements AdoptionService {

    private final AdoptionRepository adoptionRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final AdoptionMapper adoptionMapper;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<AdoptionResponse> findFiltered(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("adoptionDate").descending());
        Page<Adoption> pageAdoptions = adoptionRepository.findFiltered(search, pageable);

        return new PageResponse<>(
                pageAdoptions.getContent().stream()
                        .map(adoptionMapper::toResponse)
                        .collect(Collectors.toList()),
                page,
                size,
                pageAdoptions.getTotalElements(),
                pageAdoptions.getTotalPages()
        );
    }

    @Override
    @Transactional
    public AdoptionResponse create(AdoptionRequest request) {
        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new ApiValidateException("Mascota no encontrada."));

        if (pet.getStatus() == Status.DELETED) {
            throw new ApiValidateException("La mascota ha sido eliminada.");
        }

        User adopter = null;
        if (request.getAdopterId() != null) {
            adopter = userRepository.findById(request.getAdopterId())
                    .orElseThrow(() -> new ApiValidateException("Adoptante no encontrado."));
        }

        // Update pet status to ADOPTED
        pet.setStatus(Status.ADOPTED);
        petRepository.save(pet);

        // Check if adoption record already exists
        if (adoptionRepository.existsByPetId(pet.getId())) {
            throw new ApiValidateException("Esta mascota ya registra una adopción.");
        }

        Adoption adoption = Adoption.builder()
                .pet(pet)
                .adopter(adopter)
                .adoptionDate(LocalDateTime.now())
                .status("Finalizado")
                .build();

        return adoptionMapper.toResponse(adoptionRepository.save(adoption));
    }
}
