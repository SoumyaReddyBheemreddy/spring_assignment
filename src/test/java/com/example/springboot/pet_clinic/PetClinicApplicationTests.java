package com.example.springboot.pet_clinic;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PetClinicApplicationTests {

	/*@Test
	void deleteTest(){
		Pet pet = new Pet(1,"Tom","Cat");
		petService.save(pet);
		Pet pet1 = petService.findById(1);
		petRepository.deleteById(1);
		Optional<Pet> result = Optional.of(petService.findById(1));
		assertEquals(false,result.isPresent());
	}*/

}
