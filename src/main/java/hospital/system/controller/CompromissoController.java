package hospital.system.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hospital.system.model.Compromisso;
import hospital.system.service.CompromissoService;

@RestController
@RequestMapping("/api/compromissos")
public class CompromissoController {

    private final CompromissoService service;
    public CompromissoController(CompromissoService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<Compromisso> criar(@Valid @RequestBody Compromisso compromisso) {
        Compromisso salvo = service.salvarCompromisso(compromisso);
        return ResponseEntity.ok(salvo);
    }
    @GetMapping
    public ResponseEntity<List<Compromisso>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<Compromisso>> porMedicoEData(
            @PathVariable Long medicoId,
            @RequestParam String data) {
        LocalDate localDate = LocalDate.parse(data);
        return ResponseEntity.ok(service.buscarPorMedicoEData(medicoId, localDate));
    }
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Compromisso>> porPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.buscarPorPaciente(pacienteId));
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<Compromisso> atualizarStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Compromisso.Status novoStatus = Compromisso.Status.valueOf(status.toUpperCase());
        Compromisso atualizado = service.atualizarStatus(id, novoStatus);
        return ResponseEntity.ok(atualizado);
    }
    @PutMapping("/{id}/remarcar")
    public ResponseEntity<Compromisso> remarcarCompromisso(
            @PathVariable Long id,
            @RequestParam String data,
            @RequestParam String hora) {
        LocalDate novaData = LocalDate.parse(data);
        LocalTime novaHora = LocalTime.parse(hora);
        Compromisso atualizado = service.remarcarCompromisso(id, novaData, novaHora);
        return ResponseEntity.ok(atualizado);
    }
}