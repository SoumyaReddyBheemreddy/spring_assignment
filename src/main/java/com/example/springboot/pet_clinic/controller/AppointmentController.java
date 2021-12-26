package com.example.springboot.pet_clinic.controller;

import com.example.springboot.pet_clinic.entity.Appointment;
import com.example.springboot.pet_clinic.entity.Pet;
import com.example.springboot.pet_clinic.service.AppointmentService;
import com.example.springboot.pet_clinic.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PetService petService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder)
    {
        StringTrimmerEditor stringTrimmerEditor=new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/list")
    public String getPetAppointment(Model model,
                               @RequestParam("petId") String petId){
        int id = Integer.parseInt(petId);
        Pet pet  = petService.findById(id);
        model.addAttribute("pet",pet);
        model.addAttribute("appointments",pet.getAppointments());
        return "appointments/getPetAppointments";
    }
    @GetMapping("/show")
    public String addOwners(Model model, @RequestParam("petId") int petId){
        Pet pet = petService.findById(petId);
        model.addAttribute("pet",pet);
        Appointment appointment = new Appointment();
        model.addAttribute("appointment",appointment);
        return "appointments/appointmentForm";
    }
    //save-pet-appointment
    @PostMapping("/save")
    public String saveAppointment(@Valid  @ModelAttribute("appointment") Appointment appointment, BindingResult bindingResult,
                                  @ModelAttribute("pet") Pet petModel,
                              @RequestParam("petId") int petId){
        petModel.setId(petId);
        if(bindingResult.hasErrors())
            return "appointments/appointmentForm";
        Pet pet = petService.findById(petId);
        appointment.setPet(pet);

       appointmentService.save(appointment);
        return "redirect:/appointments/list?petId="+petId;
    }
    @GetMapping("/update")
    public String updatePet(Model model, @RequestParam("appointmentId") int appointmentId,
                            @RequestParam("petId") int petId){
        Appointment appointment = appointmentService.findById(appointmentId);
        Pet pet  = petService.findById(petId);
        model.addAttribute("appointment",appointment);
        model.addAttribute("pet",pet);
        return "appointments/appointmentForm";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("appointmentId") int appointmentId,@RequestParam("petId") int petId){
        appointmentService.deleteById(appointmentId);
        return "redirect:/appointments/list?petId="+petId;
    }
}
