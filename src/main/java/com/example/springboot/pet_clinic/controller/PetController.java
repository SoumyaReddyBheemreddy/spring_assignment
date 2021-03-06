package com.example.springboot.pet_clinic.controller;


import com.example.springboot.pet_clinic.converter.PetConverter;
import com.example.springboot.pet_clinic.dto.PetDTO;
import com.example.springboot.pet_clinic.entity.Pet;
import com.example.springboot.pet_clinic.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/pets")
public class PetController {
    @Autowired
    private PetService petService;

    private final PetConverter petConverter = new PetConverter();
    @InitBinder
    public void initBinder(WebDataBinder dataBinder)
    {
        StringTrimmerEditor stringTrimmerEditor=new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/list")
    public String listPets(Model model){
        List<PetDTO> pets= petConverter.entityToDto(petService.findAll());
        model.addAttribute("pets",pets);
        return "pets/listPets";
    }
    @GetMapping("/form")
    public String addPet(Model model){
        PetDTO pet = petConverter.entityToDto(new Pet());
        model.addAttribute("pet",pet);
        return "pets/petForm";
    }
    @PostMapping("/save")
    public String savePet(@Valid  @ModelAttribute("pet") PetDTO pet, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "pets/petForm";
        else {
            if (pet.getId() != 0) {
                PetDTO temPet = petConverter.entityToDto(petService.findById(pet.getId()));
                temPet.setName(pet.getName());
                temPet.setType(pet.getType());
                petService.save(petConverter.dtoToEntity(temPet));
            } else
                petService.save(petConverter.dtoToEntity(pet));
        }
        return "redirect:/pets/list";
    }

    @GetMapping("/update")
    public String updatePet(Model model, @RequestParam("petId") int petId){
        PetDTO pet = petConverter.entityToDto(petService.findById(petId));
        model.addAttribute("pet",pet);
        return "pets/petForm";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("petId") int petId){
        petService.deleteById(petId);
        return "redirect:/pets/list";
    }
}
