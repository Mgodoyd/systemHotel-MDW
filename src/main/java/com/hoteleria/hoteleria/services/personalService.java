package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.hotelDto;
import com.hoteleria.hoteleria.dtos.puestoDto;
import com.hoteleria.hoteleria.dtos.staffDto;
import com.hoteleria.hoteleria.interfaces.personalInterface;
import com.hoteleria.hoteleria.models.hotel;
import com.hoteleria.hoteleria.models.personal;
import com.hoteleria.hoteleria.models.puesto;

@Service
public class personalService {

    @Autowired
    private personalInterface personalRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<staffDto> getAllPersonal() {
        List<personal> personalList = personalRepository.findAll();
        return personalList.stream()
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
        personal.setPassword(passwordEncoder.encode(personal.getPassword()));
        return personalRepository.save(personal);
    }

    public staffDto updatePersonal(staffDto staff) {
        personal personal = convertToEntity(staff);
        return convertDTOStaff(personalRepository.save(personal));
    }

    private personal convertToEntity(staffDto staff) {
        personal personal = new personal();
        personal.setId(staff.getId());
        personal.setname(staff.getName());
        personal.setPhone(staff.getPhone());
        personal.setEmail(staff.getEmail());
        personal.setPassword(staff.getPassword());
        personal.setAddress(staff.getAddress());
        personal.setRole(staff.getRole());

        if (staff.getHotel() != null) {
            hotel hotel = new hotel();
            hotel.setId(staff.getHotel().getId());
            hotel.setName(staff.getHotel().getName());
            personal.setHotel(hotel);
        }

        if (staff.getRol() != null) {
            puesto puesto = new puesto();
            puesto.setId(staff.getRol().getId());
            puesto.setName(staff.getRol().getName());
            personal.setRol(puesto);
        }

        return personal;
    }

    public puesto convertToPuesto(puestoDto dto) {
        puesto entity = new puesto();
        entity.setId(dto.getId());
        entity.setName(dto.getName());

        return entity;
    }

    public hotel convertToHotel(hotelDto dto) {
        hotel entity = new hotel();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        return entity;
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
        personalExist.get().setPassword(passwordEncoder.encode(passwordNew));
        personalRepository.save(personalExist.get());
    }

    private staffDto convertDTOStaff(personal personal) {
        staffDto staff = new staffDto();
        staff.setId(personal.getId());
        staff.setName(personal.getName());
        staff.setPhone(personal.getPhone());
        staff.setEmail(personal.getEmail());
        staff.setPassword(personal.getPassword());
        staff.setAddress(personal.getAddress());

        // Convertir hotel
        staffDto.hotelDTO hotel = new staffDto.hotelDTO();
        hotel.setId(personal.getHotel().getId());
        hotel.setName(personal.getHotel().getName());
        staff.setHotel(hotel);

        // Convertir rol
        staffDto.rolDTO rol = new staffDto.rolDTO();
        rol.setId(personal.getRol().getId());
        rol.setName(personal.getRol().getName());
        staff.setRol(rol);

        return staff;
    }

}
