package io.nology.fullstackexample.pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.nology.fullstackexample.TestConfig;
import java.util.List;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

  @Nested
  class FindAllTests {

    @Test
    public void findAllPokemon_returnsAllPokemonInDb() {
      ResponseEntity<String> response = restTemplate.getForEntity(
        "/pokemon",
        String.class
      );
      assertEquals(HttpStatus.OK, response.getStatusCode());
      Pokemon[] pokemon = restTemplate.getForObject(
        "/pokemon",
        Pokemon[].class
      );
      assertEquals(3, pokemon.length);
    }

    @Test
    public void findAllPokemon_returnsEmptyArrayWhenDbEmpty() {
      pokemonRepository.deleteAll();
      ResponseEntity<String> response = restTemplate.getForEntity(
        "/pokemon",
        String.class
      );
      assertEquals(HttpStatus.OK, response.getStatusCode());
      Pokemon[] pokemon = restTemplate.getForObject(
        "/pokemon",
        Pokemon[].class
      );
      assertEquals(0, pokemon.length);
    }
  }

  @Nested
  class createTests {

    private HttpHeaders headers;

    @BeforeEach
    public void createHeaders() {
      headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void createPokemon_persistsPokemonInDbWhenPassedCorrectBody() {}
  }
}
