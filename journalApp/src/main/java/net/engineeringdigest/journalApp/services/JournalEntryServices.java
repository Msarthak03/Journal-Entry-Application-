package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryServices {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserServices userServices;
    private Object user;


    @Transactional
   public void saveEntry(JournalEntry journalEntry,String userName){
       try {
           User user = userServices.findByUserName(userName);
           journalEntry.setDate(LocalDateTime.now());// to create entry
           JournalEntry saved = journalEntryRepository.save(journalEntry);
           user.getJournalEntries().add(saved);
           userServices.saveNewUser(user);

       }catch (Exception e){
           System.out.println(e );
           throw new RuntimeException("An error Occured whike saving the entry",e);
       }
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() { // to cehck what all entries are prezent
       return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry>findById(ObjectId id){ // to find entry by number
       return journalEntryRepository.findById(id);
    }

     @Transactional
     public boolean deleteById(ObjectId id, String userName){
        boolean remove = false;
        try{
            User user = userServices.findByUserName(userName);
            remove = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
             if (remove) {
                 userServices.saveNewUser(user);
                 journalEntryRepository.deleteById(id);
             }

        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("an error while deleting the entry ");
        }
         return remove;
     }


}
