package com.example.nezok.api;

public class ContactNotFoundException extends RuntimeException{
    ContactNotFoundException(Integer id) {
        super("The match could not be found " + id);
    }
}
