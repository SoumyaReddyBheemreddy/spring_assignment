package com.example.springboot.pet_clinic.controller;

import com.example.springboot.pet_clinic.entity.Authorities;
import com.example.springboot.pet_clinic.entity.Owner;
import com.example.springboot.pet_clinic.entity.Pet;
import com.example.springboot.pet_clinic.entity.Users;
import com.example.springboot.pet_clinic.service.OwnerService;
import com.example.springboot.pet_clinic.service.PetService;
import com.example.springboot.pet_clinic.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/owners")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private PetService petService;
    @Autowired
    private UsersService usersService;
    @InitBinder
    public void initBinder(WebDataBinder dataBinder)
    {
        StringTrimmerEditor stringTrimmerEditor=new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
    @GetMapping("/pets")
    public String getOwnerPets(Model model, Authentication authentication){
        Users users = usersService.findById(authentication.getName());
        Owner owner = users.getOwner();
        Set<Pet> pets = owner.getPets();
        model.addAttribute("pets",pets);
        return "owners/petsList";
    }
    @GetMapping("/list")
    public String getPetOwners(Model model,
                               @RequestParam("petId") String petId){
        int id = Integer.parseInt(petId);
        Pet pet  = petService.findById(id);
        model.addAttribute("pet",pet);
        model.addAttribute("owners",pet.getOwners());
        return "owners/getPetOwners";
    }
    @GetMapping("/form")
    public String addOwners(Model model, @RequestParam("petId") int petId){
        Pet pet = petService.findById(petId);
        model.addAttribute("pet",pet);
        Owner owner = new Owner();
        model.addAttribute("owner",owner);
        return "owners/ownerForm";
    }
    @PostMapping("/save")
    public String savePetOwner(@Valid  @ModelAttribute("owner") Owner owner, BindingResult bindingResult,
                                @ModelAttribute("pet") Pet petModel,
                               @RequestParam("petId") int petId){
        petModel.setId(petId);
        if(bindingResult.hasErrors())
            return "owners/ownerForm";
        Pet pet = petService.findById(petId);
        ownerService.savePetOwner(owner, pet);

        return "redirect:/owners/list?petId="+petId;
    }

    @GetMapping("/update")
    public String updateOwner(Model model, @RequestParam("ownerId") int ownerId,
                            @RequestParam("petId") int petId){
        Owner owner = ownerService.findById(ownerId);
        Pet pet  = petService.findById(petId);
        model.addAttribute("owner",owner);
        model.addAttribute("pet",pet);
        return "owners/updateOwnerForm";
    }
    @PostMapping("/save-update")
    public String updateOwner(@Valid @ModelAttribute("owner") Owner owner, BindingResult bindingResult,
                              @ModelAttribute("pet") Pet pet,
                              @RequestParam("petId") int petId){
        pet.setId(petId);
        if(bindingResult.hasErrors())
            return "owners/ownerForm";
        Owner tempOwner = ownerService.findById(owner.getId());
        tempOwner.setName(owner.getName());
        tempOwner.setPhoneNumber(owner.getPhoneNumber());
        ownerService.save(tempOwner);
        return "redirect:/owners/list?petId="+petId;
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("ownerId") int ownerId,@RequestParam("petId") int petId){
        ownerService.deleteById(ownerId);
        return "redirect:/owners/list?petId="+petId;
    }
}
