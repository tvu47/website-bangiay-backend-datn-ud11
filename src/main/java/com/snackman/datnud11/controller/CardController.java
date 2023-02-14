package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.Card;
import com.snackman.datnud11.repo.CardRepository;
import com.snackman.datnud11.services.CardService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.generic.GenericObjFindById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
public class CardController {
	@Autowired
	CardRepository cardRepository;
	@Autowired
	CardService cardService;

	@GetMapping
	public ResponseEntity<List<Card>> getCards() {
		return new ResponseEntity<>(cardRepository.findAll(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Card> createCard(@RequestBody Card card) {
		return new ResponseEntity<>(cardRepository.save(card), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Card> updateCardById(@RequestBody Card card) throws CustomNotFoundException {
		cardService.checkCardExist(card.getId());
		return new ResponseEntity<>(cardRepository.save(card), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> DeleteCardById(@PathVariable(name = "id") Long id) throws CustomNotFoundException {
		Card bill = cardService.checkCardExist(id);
		cardRepository.delete(bill);
		return new ResponseEntity<>("Delete Successfully!", HttpStatus.NO_CONTENT);
	}
}
