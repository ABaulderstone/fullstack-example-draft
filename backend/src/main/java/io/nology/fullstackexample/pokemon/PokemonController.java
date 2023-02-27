package io.nology.fullstackexample.pokemon;

import io.nology.fullstackexample.exception.NotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @GetMapping("/{id}")
  public ResponseEntity<Pokemon> findById(@PathVariable Long id) {
    Pokemon foundPokemon = this.findPokemonOrThrow(id);
    return new ResponseEntity<>(foundPokemon, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Pokemon> deleteById(@PathVariable Long id) {
    Pokemon foundPokemon = this.findPokemonOrThrow(id);
    this.pokemonService.delete(foundPokemon);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Pokemon> updateById(
    @PathVariable Long id,
    @Valid @RequestBody PokemonUpdateDto data
  ) {
    Pokemon foundPokemon = this.findPokemonOrThrow(id);

    Pokemon updatedPokemon = this.pokemonService.update(foundPokemon, data);
    return new ResponseEntity<>(updatedPokemon, HttpStatus.OK);
  }

  private Pokemon findPokemonOrThrow(Long id) {
    return this.pokemonService.findById(id)
      .orElseThrow(() ->
        new NotFoundException("Could not find Pokemon with id " + id)
      );
  }
}
