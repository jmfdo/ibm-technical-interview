package com.ibm.backend.job;

import com.ibm.backend.dto.CustomClientDTO;
import com.ibm.backend.entity.Clients;
import com.ibm.backend.entity.Rents;
import com.ibm.backend.enums.RentState;
import com.ibm.backend.repository.ClientsRepo;
import com.ibm.backend.repository.RentsRepo;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class CheckRentState implements Job {

    @Autowired
    private RentsRepo rentsRepo;

    @Autowired
    private ClientsRepo clientsRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void execute(JobExecutionContext context) {
        try {
            LocalDate today = LocalDate.now();
            List<Rents> rents = rentsRepo.findAll();

            for (Rents rent : rents) {

                switch (rent.getRentState()){

                    case PENDIENTE:
                        if (rent.getRentDate().isEqual(today)){
                            rent.setRentState(RentState.ACTIVO);
                        }
                        break;
                    case ACTIVO:
                        if (rent.getExpDate().isEqual(today)){
                            rent.setRentState(RentState.VENCIDO);
                        }
                        break;
                    case VENCIDO:
                        if (rent.getExpDate().isBefore(today)){
                            rent.setRentState(RentState.RETRASADO);
                        }
                    case RETRASADO:
                        Clients client = clientsRepo.findById(rent.getClients().getId()).orElseThrow();
                        long delayedDays = ChronoUnit.DAYS.between(rent.getExpDate(), today);
                        sendEmail(client.getEmail(), delayedDays);
                        break;
                }

            }
            rentsRepo.saveAll(rents);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEmail(String email, long delayedDays) {
        String to = email;
        String subject = "Aviso de alquiler retrasado";
        String text = String.format("Su dispositivo alquilado tiene %d días de retraso", delayedDays);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }
}
