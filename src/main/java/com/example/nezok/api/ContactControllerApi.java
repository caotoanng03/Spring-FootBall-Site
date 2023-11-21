package com.example.nezok.api;

import com.example.nezok.models.ContactModel;
import com.example.nezok.repositories.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ContactControllerApi {
    @Autowired
    private final ContactRepo contactRepo;
    ContactControllerApi(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }
    @GetMapping("/api/messages")
    Iterable<ContactModel> getAll() {
        return contactRepo.findAll();
    }

    @GetMapping("/api/messages/{id}")
    ContactModel getOne(@PathVariable Integer id) {
        return contactRepo.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(id));
    }
    @PostMapping("/api/messages")
    ContactModel create(@RequestBody ContactModel contactModel) {
        return contactRepo.save(contactModel);
    }
    @PutMapping("/api/messages/{id}")
    ContactModel edit(@RequestBody ContactModel message, @PathVariable Integer id) {
        return contactRepo.findById(id) .map(e -> {
                    e.setEmail(message.getEmail());
                    e.setContent(message.getContent());
                    e.setDate(message.getDate());
                    return contactRepo.save(e);
                })
                .orElseGet(() -> {
                    message.setId(id);
                    return contactRepo.save(message);
                });
    }
    @DeleteMapping ("/api/messages/{id}")
    void delete(@PathVariable Integer id) {
        contactRepo.deleteById(id);
    }
}
