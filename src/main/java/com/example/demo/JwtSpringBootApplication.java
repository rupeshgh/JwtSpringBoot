package com.example.demo;

import com.example.demo.Model.Role;
import com.example.demo.Model.User;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtSpringBootApplication implements CommandLineRunner {


	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;


	public static void main(String[] args) {
		SpringApplication.run(JwtSpringBootApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		Role r1= new Role();
		r1.setName("ROLE_USER");


		Role r2= new Role();
		r2.setName("ROLE_ADMIN");

		roleRepository.save(r1);
		roleRepository.save(r2);


//		roleService.saveRoles(r1);
//		roleService.saveRoles(r2);


		User u1= new User();
		u1.setUsername("xyz");
		u1.setPassword("123456");
		u1.getRole().add(r1);

		userRepository.save(u1);
//		userService.addUser(u1);

		User u2= new User();
		u2.setUsername("xyz1");
		u2.setPassword("123456");
		u2.getRole().add(r2);

		userRepository.save(u2);
//	userService.addUser(u2);


	}

}
