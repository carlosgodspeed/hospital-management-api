package hospital.system.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hospital.system.model.Medico;
import hospital.system.service.MedicoService;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    private final MedicoService service;
    public MedicoController(MedicoService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<Medico> criar(@Valid @RequestBody Medico medico) {
        return ResponseEntity.ok(service.salvar(medico));
    }
    @GetMapping
    public ResponseEntity<List<Medico>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Medico> buscarPorId(@PathVariable Long id) {
        Medico medico = service.buscarPorId(id);
        if (medico == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(medico);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}