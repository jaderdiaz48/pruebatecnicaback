package co.nuvu.controllers;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.nuvu.models.Users;
import co.nuvu.services.IRolesService;
import co.nuvu.services.IUsersService;

@CrossOrigin
@RestController
@RequestMapping("/testbackend/users")
public class UsersController {

	@Autowired
	private IUsersService usersService;

	@Autowired
	private IRolesService rolesService;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@PreAuthorize("hasRole('ROLE_CUSTOMER') OR hasRole('ROLE_ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody Users user) {
		Map<String, Object> response = new HashMap<>();
		try {
			Users userAux = null;
			if (user.getId() == null) {
				userAux = usersService.findByUser(user.getUserName().toLowerCase());
			}

			if (userAux == null) {
				user.setPassword(bcryptEncoder.encode(user.getPassword()));
				user.setCreatedAt(Calendar.getInstance().getTime());
				user.setRol(rolesService.findById(2));
				userAux = usersService.save(user);
				if (userAux != null) {
					response.put("mensaje", "Usuario creado correctamente");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				} else {
					response.put("mensaje", "No se pudo crear el usuario");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
				}
			} else {
				response.put("mensaje", "Usuario ya existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.FOUND);
			}
		} catch (Exception e) {
			response.put("mensaje", "Error al realizar el query en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('ROLE_CUSTOMER') OR hasRole('ROLE_ADMIN')")
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody Users user) {
		Map<String, Object> response = new HashMap<>();
		try {

			Users userAux = null;

			if (user.getId() != null) {
				
				userAux = usersService.findById(user.getId());

				if (userAux != null) {
					userAux.setUserName(user.getUserName());
					userAux.setPassword(bcryptEncoder.encode(user.getPassword()));
					userAux.setRol(rolesService.findById(1));
					if (usersService.save(userAux) != null) {
						response.put("mensaje", "Usuario editado correctamente");
						return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
					} else {
						response.put("mensaje", "No se pudo editar el usuario");
						return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
					}
				} else {
					response.put("mensaje", "Usuario no existe en la base de datos");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.FOUND);
				}
			}else {
				response.put("mensaje", "El id del usuario no puede estar vacio");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			response.put("mensaje", "Error al realizar el query en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/createAdmin")
	public ResponseEntity<?> createAdmin(@RequestBody Users user) {
		Map<String, Object> response = new HashMap<>();
		try {
			Users userAux = null;
			if (user.getId() == null) {
				userAux = usersService.findByUser(user.getUserName().toLowerCase());
			}

			if (userAux == null) {
				user.setPassword(bcryptEncoder.encode(user.getPassword()));
				user.setCreatedAt(Calendar.getInstance().getTime());
				user.setRol(rolesService.findById(1));
				userAux = usersService.save(user);
				if (userAux != null) {
					return new ResponseEntity<Users>(usersService.findById(userAux.getId()), HttpStatus.OK);
				} else {
					response.put("mensaje", "No se pudo crear el usuario");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
				}
			} else {
				response.put("mensaje", "Usuario ya existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.FOUND);
			}
		} catch (Exception e) {
			response.put("mensaje", "Error al realizar el query en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		try {
			usersService.delete(id);
			response.put("mensaje", "Usuario eliminado exitosamente");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.put("mensaje", "Error al realizar el query en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('ROLE_CUSTOMER') OR hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		try {
			return new ResponseEntity<Users>(usersService.findById(id), HttpStatus.OK);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el query en la base de datos");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
