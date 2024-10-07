package com.technologia.to_do.controller;

import com.technologia.to_do.dto.TacheResponse;
import com.technologia.to_do.enums.Statut;
import com.technologia.to_do.enums.Type;
import com.technologia.to_do.models.Tache;
import com.technologia.to_do.services.tache.TacheService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*") // Remplacez "*" par des URLs spécifiques si nécessaire
@RequiredArgsConstructor
@RequestMapping("/taches")
public class TacheController {

    private final TacheService tacheService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TacheResponse save(@RequestBody Tache tache) {
        return tacheService.save(tache);
    }

    @GetMapping("/statut")
    @ResponseStatus(HttpStatus.OK)
    public List<TacheResponse> findByStatut(@PathVariable Statut statut) {
        return tacheService.findByStatut(statut);
    }

    @GetMapping("/auth/statut")
    @ResponseStatus(HttpStatus.OK)
    public List<TacheResponse> findByAuthAndStatut(@PathVariable Statut statut) {
        return tacheService.findByAuthAndStatut(statut);
    }

    @GetMapping("/auth/type/statut")
    @ResponseStatus(HttpStatus.OK)
    public List<TacheResponse> findByAuthAndTypeAndStatut(@PathVariable Type type, @PathVariable Statut statut) {
        return tacheService.findByAuthAndTypeAndStatut(type, statut);
    }

    @GetMapping("/{id}/statut")
    @ResponseStatus(HttpStatus.OK)
    public TacheResponse findByIdAndStatut(@PathVariable Long id, @PathVariable Statut statut) {
        return tacheService.findByIdAndStatut(id, statut);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public TacheResponse update(@RequestBody Tache tache) {
        return tacheService.update(tache);
    }

    @PutMapping("/assign")
    @ResponseStatus(HttpStatus.OK)
    public TacheResponse assign(@RequestBody Tache tache) {
        return tacheService.assign(tache);
    }

    @GetMapping("/export/{startDate}/{endDate}")
    public void exportTacheToExcel(HttpServletResponse response,
                                   @PathVariable(required = false) @DateTimeFormat(pattern= "yyyy-MM-dd") LocalDate startDate,
                                   @PathVariable(required = false) @DateTimeFormat(pattern= "yyyy-MM-dd") LocalDate endDate) throws IOException {
        tacheService.exportToExcel(response, startDate, endDate);
    }
}
