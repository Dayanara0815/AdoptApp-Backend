package com.utp.adoptappbackend.pet.service.impl;

import com.utp.adoptappbackend.common.exception.ApiValidateException;
import com.utp.adoptappbackend.common.model.PageResponse;
import com.utp.adoptappbackend.common.model.enumeration.Sex;
import com.utp.adoptappbackend.common.model.enumeration.Size;
import com.utp.adoptappbackend.common.model.enumeration.Species;
import com.utp.adoptappbackend.common.model.enumeration.Status;
import com.utp.adoptappbackend.common.util.ConstantUtil;
import com.utp.adoptappbackend.pet.mapper.PetMapper;
import com.utp.adoptappbackend.pet.model.Pet;
import com.utp.adoptappbackend.pet.model.dto.PetRequest;
import com.utp.adoptappbackend.pet.model.dto.PetResponse;
import com.utp.adoptappbackend.pet.repository.PetRepository;
import com.utp.adoptappbackend.pet.service.PetService;
import com.utp.adoptappbackend.user.model.User;
import com.utp.adoptappbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.utp.adoptappbackend.adoption.repository.AdoptionRepository;
import com.utp.adoptappbackend.adoption.model.Adoption;
import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final PetMapper petMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final AdoptionRepository adoptionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PetResponse> findAll() {
        return petRepository.findByStatusNot(Status.DELETED).stream()
                .map(petMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PetResponse findById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ApiValidateException(ConstantUtil.NOT_FOUND));
        if (pet.getStatus() == Status.DELETED) {
            throw new ApiValidateException(ConstantUtil.NOT_FOUND);
        }
        return petMapper.toResponse(pet);
    }

    @Override
    @Transactional
    public PetResponse create(PetRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ApiValidateException("Usuario no encontrado para crear la publicación."));

        Pet pet = petMapper.toEntity(request, user);
        PetResponse response = petMapper.toResponse(petRepository.save(pet));
        messagingTemplate.convertAndSend("/topic/pets/created", response);
        return response;
    }

    @Override
    @Transactional
    public PetResponse update(Long id, PetRequest request) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ApiValidateException(ConstantUtil.NOT_FOUND));

        Status oldStatus = pet.getStatus();

        pet.setName(request.getName());
        pet.setSpecies(request.getSpecies());
        pet.setAge(request.getAge());
        pet.setColor(request.getColor());
        pet.setSize(request.getSize());
        pet.setDescription(request.getDescription());
        pet.setImage(request.getImage());
        pet.setStatus(request.getStatus());

        Pet savedPet = petRepository.save(pet);

        if (request.getStatus() == Status.ADOPTED && oldStatus != Status.ADOPTED) {
            if (!adoptionRepository.existsByPetId(id)) {
                Adoption adoption = Adoption.builder()
                        .pet(savedPet)
                        .adopter(null)
                        .adoptionDate(LocalDateTime.now())
                        .status("Finalizado")
                        .build();
                adoptionRepository.save(adoption);
            }
        }

        PetResponse response = petMapper.toResponse(savedPet);
        messagingTemplate.convertAndSend("/topic/pets/updated", response);
        return response;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ApiValidateException(ConstantUtil.NOT_FOUND));
        pet.setStatus(Status.DELETED);
        petRepository.save(pet);
        messagingTemplate.convertAndSend("/topic/pets/deleted", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PetResponse> findByUserId(Long userId) {
        return petRepository.findByUserIdAndStatusNot(userId, com.utp.adoptappbackend.common.model.enumeration.Status.DELETED).stream()
                .map(petMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PetResponse> findFiltered(Status status, List<Species> species, Size size, Sex sex, String age, String search, int page, int sizeVal) {
        Pageable pageable = PageRequest.of(page, sizeVal, Sort.by("id").descending());
        Status petStatus = status;
        List<Species> speciesList = (species == null || species.isEmpty()) ? null : species;
        boolean isSpeciesEmpty = (speciesList == null);
        String searchQuery = (search == null || search.trim().isEmpty()) ? "" : search.trim();
        boolean isSearchEmpty = searchQuery.isEmpty();

        String petAge = (age == null || age.trim().isEmpty()) ? null : age.trim();

        Page<Pet> pagePets = petRepository.findFiltered(
                petStatus, 
                speciesList, 
                isSpeciesEmpty, 
                size, 
                sex,
                petAge,
                searchQuery, 
                isSearchEmpty, 
                pageable
        );

        return new PageResponse<>(
                pagePets.getContent().stream()
                        .map(petMapper::toResponse)
                        .collect(Collectors.toList()),
                page,
                sizeVal,
                pagePets.getTotalElements(),
                pagePets.getTotalPages()
        );
    }
}
