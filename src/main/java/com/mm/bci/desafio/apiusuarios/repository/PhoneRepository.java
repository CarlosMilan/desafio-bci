package com.mm.bci.desafio.apiusuarios.repository;

import com.mm.bci.desafio.apiusuarios.domain.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, UUID> {
}
