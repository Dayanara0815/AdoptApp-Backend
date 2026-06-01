package com.utp.adoptappbackend.adoption.expose;

import com.utp.adoptappbackend.adoption.model.dto.AdoptionRequest;
import com.utp.adoptappbackend.adoption.model.dto.AdoptionResponse;
import com.utp.adoptappbackend.adoption.service.AdoptionService;
import com.utp.adoptappbackend.common.model.ApiResponse;
import com.utp.adoptappbackend.common.model.PageResponse;
import com.utp.adoptappbackend.common.util.ConstantUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/adoptions")
@RequiredArgsConstructor
public class AdoptionController {

    private final AdoptionService adoptionService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AdoptionResponse>>> findPage(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.<PageResponse<AdoptionResponse>>builder()
                .code(ConstantUtil.OK_CODE)
                .message(ConstantUtil.OK_MESSAGE)
                .data(adoptionService.findFiltered(search, page, size))
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AdoptionResponse>> create(@Valid @RequestBody AdoptionRequest request) {
        return ResponseEntity.ok(ApiResponse.<AdoptionResponse>builder()
                .code(ConstantUtil.OK_CODE)
                .message("Adopción registrada exitosamente.")
                .data(adoptionService.create(request))
                .build());
    }
}
