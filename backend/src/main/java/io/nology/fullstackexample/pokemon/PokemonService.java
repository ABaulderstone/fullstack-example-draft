package io.nology.fullstackexample.pokemon;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.util.List;
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
}
