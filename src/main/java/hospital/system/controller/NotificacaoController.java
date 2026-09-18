package hospital.system.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hospital.system.model.Notificacao;
import hospital.system.service.NotificacaoService;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {

    private final NotificacaoService service;

    public NotificacaoController(NotificacaoService service) {
        this.service = service;
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Notificacao>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.listarPorPaciente(pacienteId));
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<Notificacao>> listarPorMedico(@PathVariable Long medicoId) {
        return ResponseEntity.ok(service.listarPorMedico(medicoId));
    }

    @PutMapping("/{id}/lida")
    public ResponseEntity<Notificacao> marcarComoLida(@PathVariable Long id) {
        return ResponseEntity.ok(service.marcarComoLida(id));
    }

    @GetMapping("/paciente/{pacienteId}/nao-lidas/count")
    public ResponseEntity<Map<String, Long>> contarNaoLidasPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(Map.of("naoLidas", service.contarNaoLidasPorPaciente(pacienteId)));
    }

    @GetMapping("/medico/{medicoId}/nao-lidas/count")
    public ResponseEntity<Map<String, Long>> contarNaoLidasMedico(@PathVariable Long medicoId) {
        return ResponseEntity.ok(Map.of("naoLidas", service.contarNaoLidasPorMedico(medicoId)));
    }
}