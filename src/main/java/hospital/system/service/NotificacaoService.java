package hospital.system.service;

import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {
    public void notificarPaciente(String nomePaciente, String mensagem) {
        System.out.println("Notificação para " + nomePaciente + ": " + mensagem);
    }
}