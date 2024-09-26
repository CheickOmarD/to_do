package com.technologia.to_do.controller;

import com.technologia.to_do.dto.UsersResponse;
import com.technologia.to_do.enums.Statut;
import com.technologia.to_do.models.Users;
import com.technologia.to_do.services.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsersResponse save(@RequestBody Users users) {
        return usersService.save(users);
    }

    @GetMapping("/statut/{statut}")
    @ResponseStatus(HttpStatus.OK)
    public List<UsersResponse> findByStatut(@PathVariable Statut statut) {
        return usersService.findByStatut(statut);
    }

    @GetMapping("/statut-not/{statut}")
    @ResponseStatus(HttpStatus.OK)
    public List<UsersResponse> findByStatutNot(@PathVariable Statut statut) {
        return usersService.findByStatutNot(statut);
    }

    @GetMapping("/{id}/statut/{statut}")
    @ResponseStatus(HttpStatus.OK)
    public UsersResponse findByIdAndStatut(@PathVariable Long id, @PathVariable Statut statut) {
        return usersService.findByIdAndStatut(id, statut);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UsersResponse update(@RequestBody Users users) {
        return usersService.update(users);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        usersService.delete(id);
    }

    @PutMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    public UsersResponse activate(@RequestBody Users users) {
        return usersService.activate(users);
    }
}
