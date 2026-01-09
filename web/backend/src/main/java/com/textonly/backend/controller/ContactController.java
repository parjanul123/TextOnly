package com.textonly.backend.controller;

import com.textonly.backend.model.Contact;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*")
public class ContactController {
    // Într-o aplicație reală, aceste date ar veni din DB sau din agenda utilizatorului
    private static final List<Contact> contacts = new ArrayList<>(List.of(
        new Contact("Andrei Popescu", "0741111111"),
        new Contact("Maria Ionescu", "0722333444"),
        new Contact("Alex Dinu", "0733555666")
    ));

    @GetMapping
    public List<Contact> getAllContacts() {
        return contacts;
    }

    @GetMapping("/search")
    public List<Contact> searchContacts(@RequestParam String query) {
        String q = query.toLowerCase();
        List<Contact> filtered = new ArrayList<>();
        for (Contact c : contacts) {
            if (c.getName().toLowerCase().startsWith(q) || c.getPhone().replace(" ", "").contains(q)) {
                filtered.add(c);
            }
        }
        return filtered;
    }
}
