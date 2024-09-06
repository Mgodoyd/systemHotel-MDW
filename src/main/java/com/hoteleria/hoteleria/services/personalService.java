package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.interfaces.personalInterface;
import com.hoteleria.hoteleria.models.personal;

@Service
public class personalService {

    @Autowired
    private personalInterface personalRepository;

    public List<personal> getPersonals() {
        return personalRepository.findAll();
    }

    public Optional<personal> findByEmail(String email) {
        return personalRepository.findByEmail(email);
    }

    public personal findByTelefono(String telefono) {
        return personalRepository.findByTelefono(telefono);
    }

    public personal createPersonal(personal personal) {
        Optional<personal> personalExist = personalRepository.findByEmail(personal.getEmail());
        if (personalExist != null) {
            throw new RuntimeException("Personal already exists!");
        }
        return personalRepository.save(personal);
    }

    public personal getPersonal(String email) {
        Optional<personal> personalExist = personalRepository.findByEmail(email);
        if (personalExist == null) {
            return null;
        }
        return personalExist.get();
    }

    public personal getPersonalByTelefono(String telefono) {
        personal personalExist = personalRepository.findByTelefono(telefono);
        if (personalExist == null) {
            return null;
        }
        return personalExist;
    }

    public personal updatePersonal(personal personal) {
        Optional<personal> personalExist = personalRepository.findByEmail(personal.getEmail());
        if (personalExist == null) {
            throw new RuntimeException("Personal does not exist!");
        }
        return personalRepository.save(personal);
    }

    public Boolean deletePersonal(String email) {
        try {
            Optional<personal> personalExist = personalRepository.findByEmail(email);
            personalRepository.delete(personalExist.get());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void changePassword(String emailNew, String passwordNew) {
        Optional<personal> personalExist = personalRepository.findByEmail(emailNew);
        if (personalExist == null) {
            throw new RuntimeException("Personal does not exist!");
        }
        personalExist.get().setPassword(passwordNew);
        personalRepository.save(personalExist.get());
    }

}
