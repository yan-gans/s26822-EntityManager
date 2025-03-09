package pl.edu.pja.tpo03.s26822entitymanager;


import org.springframework.stereotype.Service;

@Service

public class TranslationCap implements TranslationHandler{
    @Override
    public void show(EntryRepository entryRepository) {
        System.out.printf("%-20s   %-20s   %-20s%n", "English:","German:","Polish:");
        for (Entry e:entryRepository.getDb())
        {
            System.out.println(e.toString().toUpperCase());
        }
    }
}
