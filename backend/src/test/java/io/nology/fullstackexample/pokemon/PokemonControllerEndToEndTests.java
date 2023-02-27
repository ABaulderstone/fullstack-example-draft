package io.nology.fullstackexample.pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.nology.fullstackexample.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(
  properties = "test.base-url=http://localhost:8080",
  webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@Import(TestConfig.class)
public class PokemonControllerEndToEndTests {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private PokemonService pokemonService;

  @Autowired
  private PokemonRepository pokemonRepository;

  @Autowired
  private ModelMapper mapper;

  private Pokemon bulbasaur;
  private Pokemon squirtle;
  private Pokemon charmander;

  @BeforeEach
  public void setup() {
    bulbasaur = new Pokemon("BulbaSaur", "Grass", 50, 5);
    squirtle = new Pokemon("Squirtle", "Water", 30, 10);
    charmander = new Pokemon("Charmander", "Fire", 40, 8);
    pokemonRepository.save(bulbasaur);
    pokemonRepository.save(squirtle);
    pokemonRepository.save(charmander);
  }

  @AfterEach
  public void teardown() {
    pokemonRepository.deleteAll();
  }

  @Test
  public void findAllPokemon_returnsAllPokemonInDb() {
    ResponseEntity<String> response = restTemplate.getForEntity(
      "/pokemon",
      String.class
    );
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
