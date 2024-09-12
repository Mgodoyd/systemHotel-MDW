package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.hotelDto;
import com.hoteleria.hoteleria.dtos.staffDto;
import com.hoteleria.hoteleria.interfaces.personalInterface;
import com.hoteleria.hoteleria.models.hotel;
import com.hoteleria.hoteleria.models.personal;
import com.hoteleria.hoteleria.models.puesto;

@Service
public class personalService {

    @Autowired
    private personalInterface personalRepository;

    public List<staffDto> getAllPersonal() {
        List<personal> personal = personalRepository.findAll();
        return personal.stream()
                .map(this::convertDTOStaff)
                .collect(Collectors.toList());
    }

    public Optional<staffDto> findByEmail(String email) {
        return personalRepository.findByEmail(email)
                .map(this::convertDTOStaff);
    }

    public Optional<staffDto> findById(UUID id) {
        Optional<personal> personalExist = personalRepository.findById(id);
        if (personalExist == null) {
            return null;
        }
        return Optional.of(convertDTOStaff(personalExist.get()));
    }

    public staffDto findByTelefono(String phone) {
        personal personalExist = personalRepository.findByPhone(phone);
        if (personalExist == null) {
            return null;
        }
        return convertDTOStaff(personalExist);
    }

    public personal save(personal personal) {
        return personalRepository.save(personal);
    }

    public staffDto updatePersonal(staffDto staff) {
        personal personal = convertToEntity(staff);
        return convertDTOStaff(personalRepository.save(personal));
    }

    private personal convertToEntity(staffDto staff) {
        personal personal = new personal();
        personal.setId(staff.getId());

        // Asignar el rol (puesto) si el id está presente
        if (staff.getRolId() != null) {
            puesto rol = new puesto(); // Asumiendo que tienes un objeto 'puesto'
            rol.setId(staff.getRolId());
            rol.setName(staff.getRolNombre()); // Si quieres usar el nombre también
            personal.setRol(rol);
        }

        // Asignar el hotel si el id está presente
        if (staff.getHotelId() != null) {
            hotel hotel = new hotel(); // Asumiendo que tienes un objeto 'hotel'
            hotel.setId(staff.getHotelId());
            hotel.setName(staff.getHotelNombre()); // Si quieres usar el nombre también
            personal.setHotel(hotel);
        }

        personal.setname(staff.getName());
        personal.setPhone(staff.getPhone());
        personal.setEmail(staff.getEmail());
        personal.setPassword(staff.getPassword());
        personal.setAddress(staff.getAddress());

        return personal;
    }

    public Boolean deleteById(UUID id) {
        Optional<personal> personalExist = personalRepository.findById(id);
        if (personalExist == null) {
            return false;
        }
        personalRepository.deleteById(id);
        return true;
    }

    public void changePassword(String emailNew, String passwordNew) {
        Optional<personal> personalExist = personalRepository.findByEmail(emailNew);
        if (personalExist == null) {
            throw new RuntimeException("Personal does not exist!");
        }
        personalExist.get().setPassword(passwordNew);
        personalRepository.save(personalExist.get());
    }

    private staffDto convertDTOStaff(personal personal) {
        staffDto staff = new staffDto();
        staff.setId(personal.getId());

        // Solo asignar id y nombre del puesto
        if (personal.getRol() != null) {
            staff.setRolId(personal.getRol().getId());
            staff.setRolNombre(personal.getRol().getName());
        }

        // Solo asignar id y nombre del hotel
        if (personal.getHotel() != null) {
            staff.setHotelId(personal.getHotel().getId());
            staff.setHotelNombre(personal.getHotel().getName());
        }

        staff.setName(personal.getName());
        staff.setPhone(personal.getPhone());
        staff.setEmail(personal.getEmail());
        staff.setPassword(personal.getPassword());
        staff.setAddress(personal.getAddress());

        return staff;
    }

}
