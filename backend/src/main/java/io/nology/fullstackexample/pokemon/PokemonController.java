package io.nology.fullstackexample.pokemon;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

  @Autowired
  PokemonService pokemonService;

  @PostMapping
  public ResponseEntity<Pokemon> create(
    @Valid @RequestBody PokemonCreateDto data
  ) {
    System.out.println(data.toString());
    Pokemon createdPokemon = this.pokemonService.create(data);
    return new ResponseEntity<>(createdPokemon, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<Pokemon>> findAll() {
    List<Pokemon> allPokemon = this.pokemonService.findAll();
    return new ResponseEntity<>(allPokemon, HttpStatus.OK);
  }
}
