package co.nuvu.controllers;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.nuvu.models.Users;

import co.nuvu.models.Customers;
import co.nuvu.services.ICustomersService;
import co.nuvu.services.IUsersService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/testbackend/customers")
public class CustomersController {

	@Autowired
	private ICustomersService customersService;

	@Autowired
	private IUsersService usersService;

	@PreAuthorize("hasRole('ROLE_CUSTOMER') OR hasRole('ROLE_ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody Customers customer) {
		Map<String, Object> response = new HashMap<>();
		try {
			Customers customerAux = null;
			if (customer.getId() == null) {
				customerAux = customersService.findByDni(customer.getDni());
			}
			if (customerAux == null) {				
				customer.setUser(userInSession());
				customer.setCreatedAt(Calendar.getInstance().getTime());
				customerAux = customersService.save(customer);
				if (customerAux != null) {
					return new ResponseEntity<Customers>(customersService.findById(customerAux.getId()), HttpStatus.OK);
				} else {
					response.put("mensaje", "No se pudo crear el cliente");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
				}
			} else {
				response.put("mensaje", "El cliente ya existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			customersService.delete(id);
			response.put("mensaje", "Cliente eliminado exitosamente");
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
			return new ResponseEntity<Customers>(customersService.findById(id), HttpStatus.OK);

		} catch (Exception e) {
			response.put("mensaje", "Error al realizar el query en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/findAll")
	public ResponseEntity<?> findByCompany() {
		Map<String, Object> response = new HashMap<>();
		try {

			return new ResponseEntity<List<Customers>>(customersService.findAll(), HttpStatus.OK);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el query en la base de datos");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public Users userInSession() {
		try {
			String username;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				username = ((UserDetails) principal).getUsername();
			} else {
				username = principal.toString();
			}

			return usersService.findByUser(username);
		} catch (Exception e) {
			return null;
		}
	}

}
