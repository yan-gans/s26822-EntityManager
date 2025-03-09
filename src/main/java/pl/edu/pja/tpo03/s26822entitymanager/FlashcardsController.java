package pl.edu.pja.tpo03.s26822entitymanager;

import org.springframework.stereotype.Controller;

import java.util.Comparator;
import java.util.Scanner;


@Controller
public class FlashcardsController
{
    private  EntryRepository entryRepository;
    private final TranslationHandler translationHandler;
    private Scanner scanner;

    public FlashcardsController(EntryRepository entryRepository, TranslationHandler translationHandler, Scanner scanner)
    {
        this.scanner=scanner;
        this.translationHandler=translationHandler;
        this.entryRepository = entryRepository;
    }
    public void fire()
    {
        this.entryRepository.getAllEntries();
        if(this.entryRepository.getDb().isEmpty())
            this.entryRepository.upload();

        System.out.println("""
                To perform an operation please enter the corresponding letter:
                a->add a new translation
                d->delete a translation
                m->modify a translation
                j->sort
                h->search
                s->show all translations
                t->take a test
                q->quit""");
        String string=this.scanner.next();
        char c=string.charAt(0);
        while (string.length()!=1||(c!='a'&&c!='s'&&c!='t'&&c!='d'&&c!='j'&&c!='h'&&c!='q'&&c!='m'))
        {
            System.out.println("Please make sure that the input is in the correct format.");
            string=this.scanner.next();c=string.charAt(0);
        }
        while (c!='q')
        {
            switch (c)
            {
                case 'm':
                {
                    System.out.println("Enter any word from a translation to search for:");
                    try {
                        Entry  entry = this.entryRepository.search(this.scanner.next());
                        System.out.println("Enter the language of the word you want to translate and after submitting the change:");
                        switch (this.scanner.next().charAt(0))
                        {
                            case 'e':
                            {
                                entry.setEnglish(this.scanner.next());
                                break;
                            }
                            case 'g':
                            {
                                entry.setGerman(this.scanner.next());
                                break;
                            }
                            case 'p':
                            {
                                entry.setPolish(this.scanner.next());
                                break;
                            }
                        }
                        this.entryRepository.update(entry);
                    } catch (EntryNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case 'j':
                {
                    System.out.println("Enter the language:");
                    String string1=this.scanner.next();
                    while (!string1.equals("ea") && !string1.equals("ed") && !string1.equals("pa") && !string1.equals("pd") && !string1.equals("gd") && !string1.equals("ga"))
                    {
                        System.out.println("Please make sure that the input is in the correct format.");
                        string1=this.scanner.next();
                    }
                    this.entryRepository.sort(string1);
                    break;
                }
                case 'h':
                {
                    System.out.println("Enter any word from a translation to search for:");
                    Entry entry= null;
                    try {
                        entry = this.entryRepository.search(this.scanner.next());
                    } catch (EntryNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                        System.out.println(entry);

                    break;
                }
                case 'd':
                {
                    System.out.println("Enter any word from a translation to delete:");
                    Entry entry= null;
                    try {
                        entry = this.entryRepository.search(this.scanner.next());
                    } catch (EntryNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                        this.entryRepository.deleteById(entry.getId());
                        System.out.println("Successfully deleted.");
                    break;
                }
                case 'a':
                {
                    this.entryRepository.prepnsaveEntry();
                    System.out.println("Received the translation");
                    break;
                }
                case 's':
                {
                    this.translationHandler.show(this.entryRepository);
                    break;
                }
                case 't':
                {
                    this.test();
                    break;
                }
            }
            c=this.scanner.next().charAt(0);
            while ((c!='a'&&c!='s'&&c!='t'&&c!='d'&&c!='j'&&c!='h'&&c!='q'&&c!='m'))
            {
                System.out.println("There's no such an option. Please enter the appropriate input.");
                c=this.scanner.next().charAt(0);
            }
        }
    }
    public void test()
    {
        int a=(int)(Math.random()*3);
        int b=(int)(Math.random()*(this.entryRepository.getDb().size()));
        String lang,word, f,sec;
        Entry e=this.entryRepository.getDb().get(b);
        switch (a)
        {
            case 1:
            {
                f=e.getEnglish();sec=e.getPolish();
                lang="German";
                word=e.getGerman();
                break;
            }
            case 2:
            {
                f=e.getEnglish();sec=e.getGerman();
                lang="Polish";
                word=e.getPolish();
                break;
            }
            default:
            {
                f=e.getGerman();sec=e.getPolish();
                lang="English";
                word=e.getEnglish();
            }
        }
        System.out.println("The test has started." +
                           "\nThe word you receive is in "+lang+" and is " + word+"." +
                           " \nPlease provide the translations(order doesnt matter, ex.: eng_fr)");
        String s=this.scanner.next();
        while ( s.chars().filter(ch -> ch == '_').count()!=1||s.length()<3)
        {
            System.out.println("Please make sure that the input is in the correct format.");
            s=this.scanner.next();
        }
        String[] strings=s.split("_");
        boolean pass=true;
        for(int i=0;i<strings.length;i++)
        {
            if(!strings[i].equalsIgnoreCase(f))
            {
                if(!strings[i].equalsIgnoreCase(sec))
                {
                    pass=false;
                    if(i==0)
                        System.out.println("The first word is incorrect.");
                    else
                        System.out.println("The second word is incorrect.");
                }
            }
        }
        if(pass) System.out.println("Congrets!");
    }
}
