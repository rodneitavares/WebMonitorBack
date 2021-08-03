package com.webmonitor.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webmonitor.model.ApplicationUser;
import com.webmonitor.repository.ApplicationUserRepository;

@RestController
@RequestMapping("/appuser")
@CrossOrigin(origins = "*")
public class ApplicationUserController {

	@Autowired
	private ApplicationUserRepository applicationUserRepository;

	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<ApplicationUser>> listUsers() {

		List<ApplicationUser> list = applicationUserRepository.findAll();

		return new ResponseEntity<List<ApplicationUser>>(list, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Optional<ApplicationUser>> findUser(@PathVariable Long id) {

		Optional<ApplicationUser> applicationUser = applicationUserRepository.findById(id);

		return new ResponseEntity<Optional<ApplicationUser>>(applicationUser, HttpStatus.OK);
	}

	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<ApplicationUser> addUser(@RequestBody ApplicationUser applicationUser) {

		ApplicationUser savedUser = applicationUserRepository.save(applicationUser);

		return new ResponseEntity<ApplicationUser>(savedUser, HttpStatus.OK);
	}

	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<ApplicationUser> updateUser(@RequestBody ApplicationUser applicationUser) {

		ApplicationUser savedUser = applicationUserRepository.save(applicationUser);

		return new ResponseEntity<ApplicationUser>(savedUser, HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteUser/{id}", produces = "application/text")
	public String deleteUser(@PathVariable Long id) {

		Optional<ApplicationUser> userToDelete = applicationUserRepository.findById(id);

		if (userToDelete != null) {
			applicationUserRepository.deleteById(id);
		} else {
			return "User not found.";
		}
		return "User deleted.";
	}
}
