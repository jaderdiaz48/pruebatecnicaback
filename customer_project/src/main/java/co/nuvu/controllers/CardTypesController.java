package co.nuvu.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.nuvu.models.CardTypes;
import co.nuvu.services.ICardTypesService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/testbackend/cardtypes")
public class CardTypesController {

	@Autowired
	private ICardTypesService cardTypesService;


	@PreAuthorize("hasRole('ROLE_CUSTOMER') OR hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		try {
			return new ResponseEntity<CardTypes>(cardTypesService.findById(id), HttpStatus.OK);

		} catch (Exception e) {
			response.put("mensaje", "Error al realizar el query en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('ROLE_CUSTOMER') OR hasRole('ROLE_ADMIN')")
	@GetMapping("/findAll")
	public ResponseEntity<?> findByCompany() {
		Map<String, Object> response = new HashMap<>();
		try {

			return new ResponseEntity<List<CardTypes>>(cardTypesService.findAll(), HttpStatus.OK);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el query en la base de datos");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
