package com.technologia.to_do.services.tache;

import com.technologia.to_do.dto.TacheResponse;
import com.technologia.to_do.enums.Statut;
import com.technologia.to_do.enums.Type;
import com.technologia.to_do.models.Tache;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface TacheService {
    TacheResponse save(Tache tache);
    List<TacheResponse> findByStatut(Statut statut);
    TacheResponse findByIdAndStatut(Long id , Statut statut);
    TacheResponse update(Tache tache);
    TacheResponse assign(Tache tache);
    List<TacheResponse> findByAuthAndStatut(Statut statut);

    List<TacheResponse> findByAuthAndTypeAndStatut(Type type, Statut statut);

    List<TacheResponse> findByDates(LocalDate startDate, LocalDate endDate);

    List<TacheResponse> findByAuthAndDates(LocalDate startDate, LocalDate endDate);

    void exportToExcel(HttpServletResponse response, LocalDate startDate, LocalDate endDate) throws IOException, IOException;


    TacheResponse mapToResponse(Tache tache);
    List<TacheResponse> mapToResponse(List<Tache> tache);

    }
