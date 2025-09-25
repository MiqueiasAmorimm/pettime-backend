package com.pettime.repository;

import com.pettime.model.Appointment;
import com.pettime.model.Pet;
import com.pettime.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


    List<Appointment> findByPet(Pet pet);


    List<Appointment> findByPetshop(User petshop);
}
