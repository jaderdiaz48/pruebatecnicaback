package co.nuvu.controllers;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import co.nuvu.models.CreditCards;
import co.nuvu.models.Users;
import co.nuvu.services.ICardTypesService;
import co.nuvu.services.ICreditCardsService;
import co.nuvu.services.ICustomersService;
import co.nuvu.services.IUsersService;
import co.nuvu.util.CreateCreditCard;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/testbackend/creditcards")
public class CreditCardsController {

	@Autowired
	private ICreditCardsService creditCardsService;
	
	@Autowired
	private ICardTypesService cardTypesService;

	@Autowired
	private ICustomersService customersService;

	@Autowired
	private IUsersService usersService;

	@PreAuthorize("hasRole('ROLE_CUSTOMER') OR hasRole('ROLE_ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody CreateCreditCard creditCard) {

		Map<String, Object> response = new HashMap<>();

		try {
			CreditCards aux = creditCardsService.validateExist(getCustomerId(), creditCard.getCardTypesId(),
					creditCard.getNumber());
			if (aux == null && creditCard.getId() == null) {				
				CreditCards newCreditCard = new CreditCards();
				newCreditCard.setCardTypesId(cardTypesService.findById(creditCard.getCardTypesId()));
				newCreditCard.setCustomerId(customersService.findById(getCustomerId()));
				newCreditCard.setCreatedAt(Calendar.getInstance().getTime());
				newCreditCard.setNumber(creditCard.getNumber());
				creditCardsService.save(newCreditCard);				
				response.put("mensaje", "Tarjeta de credito creada correctamente");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);				
			} else {				
				if (creditCard.getId() != null) {					
					CreditCards newCreditCard = creditCardsService.findById(creditCard.getId());
					newCreditCard.setCardTypesId(cardTypesService.findById(creditCard.getCardTypesId()));
					newCreditCard.setCustomerId(customersService.findById(getCustomerId()));
					newCreditCard.setCreatedAt(Calendar.getInstance().getTime());
					newCreditCard.setNumber(creditCard.getNumber());
					creditCardsService.save(newCreditCard);
					
					response.put("mensaje", "Tarjeta de credito editada correctamente");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				} else {
					response.put("mensaje", "El cliente ya ha registrado una tarjeta de credito con los mismo datos");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.FOUND);
				}
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
			creditCardsService.delete(id);
			response.put("mensaje", "Tarjeta de credito eliminada exitosamente");
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
			return new ResponseEntity<CreditCards>(creditCardsService.findById(id), HttpStatus.OK);

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

			return new ResponseEntity<List<CreditCards>>(creditCardsService.findByUser(userInSession().getId()),
					HttpStatus.OK);

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
			e.printStackTrace();
			return null;
		}
	}

	public Integer getCustomerId() {
		try {
			return customersService.findByUser(userInSession().getId()).getId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
