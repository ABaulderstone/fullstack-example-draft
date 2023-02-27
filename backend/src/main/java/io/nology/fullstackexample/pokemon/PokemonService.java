package io.nology.fullstackexample.pokemon;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PokemonService {

  @Autowired
  private PokemonRepository pokemonRepository;

  @Autowired
  private ModelMapper mapper;

  @PostConstruct
  public void init() {
    // This adds specific mappings after an instance of the Pokemon Service has been created
    // In this case remaining hp should be set to the same value as maximum hp
    mapper
      .typeMap(PokemonCreateDto.class, Pokemon.class)
      .addMappings(m -> m.map(PokemonCreateDto::getHp, Pokemon::setRemainingHp)
      );
  }

  public Pokemon create(PokemonCreateDto data) {
    Pokemon newPokemon = mapper.map(data, Pokemon.class);
    return this.pokemonRepository.save(newPokemon);
  }

  public List<Pokemon> findAll() {
    return this.pokemonRepository.findAll();
  }

  public Optional<Pokemon> findById(Long id) {
    return this.pokemonRepository.findById(id);
  }

  public void delete(Pokemon pokemon) {
    this.pokemonRepository.delete(pokemon);
  }

  public Pokemon update(Pokemon existingPokemon, PokemonUpdateDto data) {
    mapper.map(data, existingPokemon);
    return this.pokemonRepository.save(existingPokemon);
  }
}
