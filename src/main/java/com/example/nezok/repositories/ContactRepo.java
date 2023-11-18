package com.example.nezok.repositories;

import com.example.nezok.models.ContactModel;
import org.springframework.data.repository.CrudRepository;


public interface ContactRepo extends CrudRepository<ContactModel, Integer> {
}
