package com.lcwd.electronic.store;

import com.lcwd.electronic.store.entities.Role;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.repositories.RoleRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.UUID;

@SpringBootApplication
@EnableWebMvc
public class ElectronicStoreApplication implements CommandLineRunner {

	public static void main(String[] args)
	{
		ConfigurableApplicationContext context = SpringApplication.run(ElectronicStoreApplication.class, args);

	}


	@Autowired
	private RoleRepository roleRepository;

	@Value("${role_admin_id}")
	private  String role_admin_id;

	@Value("${role_normal_id}")
	private  String role_normal_id;

	@Override
	public void run(String... args) throws Exception {
//		System.out.println(passwordEncoder.encode("abcd"));
		try {

			Role roleAdmin = Role.builder().roleId(role_admin_id).roleName("ROLE_ADMIN").build();
			Role rolenormal = Role.builder().roleId(role_normal_id).roleName("ROLE_NORMAL").build();
			roleRepository.save(rolenormal);
		    roleRepository.save(roleAdmin);


		}catch (Exception e){
			e.printStackTrace();
		}

	}
}
