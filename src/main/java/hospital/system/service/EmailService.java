package hospital.system.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void enviarEmail(String destinatario, String assunto, String corpo) {
        System.out.println("=================================");
        System.out.println("📧 E-MAIL SIMULADO (não enviado de verdade)");
        System.out.println("Para: " + destinatario);
        System.out.println("Assunto: " + assunto);
        System.out.println("Corpo: " + corpo);
        System.out.println("=================================");
    }
}