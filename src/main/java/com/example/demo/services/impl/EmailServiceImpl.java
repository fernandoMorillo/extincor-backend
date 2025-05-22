package com.example.demo.services.impl;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoHtml(String destinatario, String asunto, String htmlCuerpo) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(htmlCuerpo, true);

            helper.setFrom("morillonoriegaf@gmail.com");
            mailSender.send(mensaje);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage(), e);
        }
    }
}
