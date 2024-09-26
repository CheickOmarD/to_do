package com.technologia.to_do.controller;

import com.technologia.to_do.dto.TacheResponse;
import com.technologia.to_do.enums.Statut;
import com.technologia.to_do.enums.Type;
import com.technologia.to_do.models.Tache;
import com.technologia.to_do.services.tache.TacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/taches")
public class TacheController {

    private final TacheService tacheService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TacheResponse save(@RequestBody Tache tache) {
        return tacheService.save(tache);
    }

    @GetMapping("/statut/{statut}")
    @ResponseStatus(HttpStatus.OK)
    public List<TacheResponse> findByStatut(@PathVariable Statut statut) {
        return tacheService.findByStatut(statut);
    }

    @GetMapping("/auth/statut/{statut}")
    @ResponseStatus(HttpStatus.OK)
    public List<TacheResponse> findByAuthAndStatut(@PathVariable Statut statut) {
        return tacheService.findByAuthAndStatut(statut);
    }

    @GetMapping("/auth/type/{type}/statut/{statut}")
    @ResponseStatus(HttpStatus.OK)
    public List<TacheResponse> findByAuthAndTypeAndStatut(@PathVariable Type type, @PathVariable Statut statut) {
        return tacheService.findByAuthAndTypeAndStatut(type, statut);
    }

    @GetMapping("/{id}/statut/{statut}")
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
}
