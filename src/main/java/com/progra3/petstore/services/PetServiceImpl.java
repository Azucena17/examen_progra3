package com.progra3.petstore.services;

import com.progra3.petstore.entities.Pet;
import com.progra3.petstore.exceptions.NotFoundException;
import com.progra3.petstore.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetServiceImpl implements PetService{

    @Autowired
    private PetRepository petRepository;
    @Override
    public List<Pet> listAll() {
        return (List<Pet>) petRepository.findAll();
    }

    @Override
    public Pet findById(Long id) {
                if (!petRepository.existsById(id)){
                    throw new NotFoundException ("La mascota solicitada no fue encontrada");
                }
        return petRepository.findById(id).orElse(null);
    }

    @Override
    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public Pet updatePet(Long id, Pet pet) {
        if (!petRepository.existsById(id)){
            throw new NotFoundException ("");
        }
        Optional<Pet> newPet = petRepository.findById(id);
        newPet.get().setId(id);
        newPet.get().setName(pet.getName());
        newPet.get().setPrice(pet.getPrice());
        newPet.get().setBirthDay(pet.getBirthDay());

        return petRepository.save(newPet.get());
    }

    @Override
    public void deletePet(Long id) {
        if (!petRepository.existsById(id)){
            throw new NotFoundException ("");
        }
        petRepository.deleteById(id);
    }
}
