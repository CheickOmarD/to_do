package com.technologia.to_do.services.tache;

import com.technologia.to_do.dto.TacheResponse;
import com.technologia.to_do.dto.UsersResponse;
import com.technologia.to_do.enums.Statut;
import com.technologia.to_do.enums.Type;
import com.technologia.to_do.exceptions.NotFoundException;
import com.technologia.to_do.models.Tache;
import com.technologia.to_do.models.Users;
import com.technologia.to_do.repository.TacheRepository;
import com.technologia.to_do.services.authentificaion.AuthentificationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class TacheServiceImpl implements TacheService {

    private final TacheRepository tacheRepository;
    private final AuthentificationService authentificationService;

    @Override
    public TacheResponse save(Tache tache) {
        Users users = authentificationService.getAuthor();
        tache.setAssignTo(users);
        tache.setCreatedBy(users);
        return mapToResponse(tacheRepository.save(tache));
    }

    @Override
    public List<TacheResponse> findByStatut(Statut statut) {
        List<Tache> taches = tacheRepository.findByStatut(statut);
        taches.sort(Comparator.comparing(Tache::getCreatedAt));
        return mapToResponse(taches);
    }

    @Override
    public TacheResponse findByIdAndStatut(Long id, Statut statut) {
        Tache tache = tacheRepository.findByIdAndStatut(id, statut);
        if (tache == null) {
            throw new NotFoundException("Cette tâche n'existe pas");
        }
        return mapToResponse(tache);
    }

    @Override
    public TacheResponse update(Tache tache) {
        Tache tacheToUpdate = tacheRepository
                .findByIdAndStatut(tache.getId(), Statut.ACTIVATED);
        if (tacheToUpdate == null) {
            throw new NotFoundException("Cette tâche n'existe pas");
        }
        tacheToUpdate.setName(tache.getName());
        tacheToUpdate.setType(tache.getType());
        tacheToUpdate.setStatut(tache.getStatut());
        return mapToResponse(tacheToUpdate);
    }

    @Override
    public TacheResponse assign(Tache tache) {
        Tache tacheToAssign = tacheRepository
                .findByIdAndStatut(tache.getId(), Statut.ACTIVATED);
        if (tacheToAssign == null) {
            throw new NotFoundException("Cette tâche n'existe pas");
        }
        tacheToAssign.setAssignTo(tache.getAssignTo());
        return mapToResponse(tacheToAssign);
    }

    @Override
    public TacheResponse mapToResponse(Tache tache) {
        UsersResponse assignTo = null;
        if (tache.getAssignTo() != null) {
            assignTo = UsersResponse.builder()
                    .firstName(tache.getAssignTo().getFirstName())
                    .lastName(tache.getAssignTo().getLastName())
                    .email(tache.getAssignTo().getEmail())
                    .phoneNumber(tache.getAssignTo().getPhoneNumber())
                    .statut(tache.getAssignTo().getStatut())
                    .build();
        }

        UsersResponse createdBy = null;
        if (tache.getCreatedBy() != null) {
            createdBy = UsersResponse.builder()
                    .firstName(tache.getCreatedBy().getFirstName())
                    .lastName(tache.getCreatedBy().getLastName())
                    .email(tache.getCreatedBy().getEmail())
                    .phoneNumber(tache.getCreatedBy().getPhoneNumber())
                    .statut(tache.getCreatedBy().getStatut())
                    .build();
        }
        return TacheResponse.builder()
                .id(tache.getId())
                .name(tache.getName())
                .createdAt(tache.getCreatedAt())
                .assignTo(assignTo)
                .createdBy(createdBy)
                .type(tache.getType())
                .statut(tache.getStatut())
                .build();
    }

    @Override
    public List<TacheResponse> findByAuthAndTypeAndStatut(Type type, Statut statut) {

        // Récupère l'utilisateur authentifié
        Users auth = authentificationService.getAuthor();
        List<Tache> taches = tacheRepository
                .findByCreatedByIdOrAssignToIdAndTypeAndStatut
                        (auth.getId(), auth.getId(), type, statut);
        // Trier par date de création
        taches.sort(Comparator.comparing(Tache::getCreatedAt));
          // Retourner la liste mappée à une réponse
        return mapToResponse(taches);
    }

    @Override
    public List<TacheResponse> findByAuthAndStatut(Statut statut) {

        // Récupère l'utilisateur authentifié
        Users auth = authentificationService.getAuthor();
        List<Tache> taches = tacheRepository.findByCreatedByOrAssignToAndStatut(auth, auth, statut);

        if (taches.isEmpty()) {
            throw new NotFoundException
                    ("Aucune tâche trouvée pour cet utilisateur avec le statut donné.");
        }

        return mapToResponse(taches);
    }

    @Override
    public List<TacheResponse> mapToResponse(List<Tache> tachesList) {
        List<TacheResponse> tacheResponses = new ArrayList<>();
        for (Tache tache : tachesList) {
            tacheResponses.add(mapToResponse(tache));
        }
        return tacheResponses;
    }

    @Override
    public List<TacheResponse> findByDates(LocalDate startDate, LocalDate endDate) {
        List<Tache> taches = tacheRepository
                .findByCreatedAtBetween(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
        taches.sort(Comparator.comparing(Tache::getCreatedAt));
        return mapToResponse(taches);
    }

    @Override
    public List<TacheResponse> findByAuthAndDates(LocalDate startDate, LocalDate endDate) {
        Users auth = authentificationService.getAuthor();
        List<Tache> taches = tacheRepository
                .findByAssignToIdAndCreatedAtBetween(auth.getId(), startDate.atStartOfDay(),
                        endDate.atStartOfDay());
        taches.sort(Comparator.comparing(Tache::getCreatedAt));
        return mapToResponse(taches);
    }

    @Override
    public void exportToExcel(HttpServletResponse response, LocalDate startDate, LocalDate endDate) throws IOException {
        List<Tache> taches = tacheRepository.findByCreatedAtBetween(startDate.atStartOfDay(), endDate.atStartOfDay());

        if (!taches.isEmpty()) {
            // Créer le fichier Excel
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Tâches");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Nom");
            headerRow.createCell(2).setCellValue("Créé par");
            headerRow.createCell(3).setCellValue("Assigné à");
            headerRow.createCell(4).setCellValue("Date de création");
            headerRow.createCell(5).setCellValue("Statut");
            // Ajouter les données des tâches
            int rowCount = 1;
            for (Tache tache : taches) {
                Row row = sheet.createRow(rowCount++);
                if (tache.getId() != null) {
                    row.createCell(0).setCellValue(tache.getId());
                }
                if (tache.getName() != null) {
                    row.createCell(1).setCellValue(tache.getName());
                }
                if (tache.getAssignTo() != null) {
                    row.createCell(3).setCellValue(tache.getAssignTo().getFirstName() + " " + tache.getAssignTo().getLastName());
                }
                if (tache.getCreatedBy() != null) {
                    row.createCell(2).setCellValue(tache.getCreatedBy().getFirstName() + " " + tache.getCreatedBy().getLastName());
                }
                if (tache.getCreatedAt() != null) {
                    row.createCell(4).setCellValue(tache.getCreatedAt().toString());
                }
                if (tache.getStatut() != null) {
                    row.createCell(5).setCellValue(tache.getStatut().name());
                }
            }
            // Configurer la réponse HTTP pour téléchargement
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=taches.xlsx");
            // Écrire dans la réponse
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        }
        else {
            throw new NotFoundException("Cette liste n existe pas");
        }

    }
}
