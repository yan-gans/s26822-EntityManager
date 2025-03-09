package pl.edu.pja.tpo03.s26822entitymanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;


@SpringBootApplication
public class FlashcardsApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FlashcardsApp.class, args);
        FlashcardsController controller = context.getBean(FlashcardsController.class);
        controller.fire();
//        entryRepository.addEntry(entry);
//        System.out.println(entryRepository.findById(1l));
//        entry.setPolish("gjhdgh");
//        try {
//        System.out.println(entryRepository.update(entry));
//     }catch (Exception ex){
//        ex.printStackTrace();
//     }

}

}
