package com.ibm.backend.job;

import com.ibm.backend.entity.Rents;
import com.ibm.backend.enums.RentState;
import com.ibm.backend.repository.RentsRepo;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class CheckRentState implements Job {

    @Autowired
    private RentsRepo rentsRepo;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LocalDate today = LocalDate.now();
        List<Rents> rents = rentsRepo.findAll();

        for (Rents rent : rents) {

            switch (rent.getRentState()){

                case PENDIENTE:
                    if (rent.getRentDate().isEqual(today) || (rent.getRentDate().isBefore(today) && rent.getExpDate().isAfter(today))){
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
                    // Send email
                    break;
            }

        }
    }
}
