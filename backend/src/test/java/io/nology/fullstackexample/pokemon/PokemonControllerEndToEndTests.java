package io.nology.fullstackexample.pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nology.fullstackexample.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(
  properties = "test.base-url=http://localhost:8080",
  webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
  private static ObjectMapper objectMapper;

  @BeforeAll
  public static void config() {
    objectMapper = new ObjectMapper();
  }

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
  class CreateTests {

    private HttpHeaders headers;

    @BeforeEach
    public void createHeaders() {
      headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void createPokemon_persistsPokemonInDbWhenPassedCorrectBody() {
      String requestBody =
        "{\"name\": \"Ditto\", \"element\": \"Normal\", \"hp\": 50, \"attackPower\": 10}";
      long pokemonCount = pokemonRepository.count();
      HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
      restTemplate.postForEntity("/pokemon", entity, String.class);
      assertEquals(pokemonCount + 1, pokemonRepository.count());
    }

    @Test
    public void createPokemon_respondsWithCreatedPokemonWhenPassedCorrectBody()
      throws JsonProcessingException {
      String requestBody =
        "{\"name\": \"Ditto\", \"element\": \"Normal\", \"hp\": 50, \"attackPower\": 10}";
      // this is a custom query - check the repository code
      Long lastId = pokemonRepository.lastUsedId();
      HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
      ResponseEntity<String> response = restTemplate.postForEntity(
        "/pokemon",
        entity,
        String.class
      );
      assertEquals(HttpStatus.CREATED, response.getStatusCode());
      String responseBody = response.getBody();
      Pokemon pokemon = objectMapper.readValue(responseBody, Pokemon.class);
      System.out.println(responseBody);
      assertEquals(lastId + 1, pokemon.getId());
      assertEquals("Ditto", pokemon.getName());
      assertEquals(10, pokemon.getAttackPower());
      assertEquals("Normal", pokemon.getElement());
      assertEquals(50, pokemon.getHp());
      assertEquals(50, pokemon.getRemainingHp());
    }

    @Test
    public void createPokemon_respondsWithBadRequestWhenPassedInvalidBody()
      throws JsonProcessingException {
      String requestBody = "{\"name\": \"\", \"hp\": 5, \"attackPower\": 1}";

      HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
      ResponseEntity<String> response = restTemplate.postForEntity(
        "/pokemon",
        entity,
        String.class
      );
      assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

      JsonNode responseBody = objectMapper.readTree(response.getBody());
      assertTrue(responseBody.has("errors"));

      JsonNode errorsNode = responseBody.get("errors");
      assertTrue(errorsNode.isObject());

      JsonNode nameError = errorsNode.get("name");
      assertNotNull(nameError);
      assertTrue(nameError.isArray());
      assertEquals(1, nameError.size());
      assertEquals("must not be blank", nameError.get(0).asText());

      JsonNode hpError = errorsNode.get("hp");
      assertNotNull(hpError);
      assertTrue(hpError.isArray());
      assertEquals(1, hpError.size());
      assertEquals(
        "must be greater than or equal to 20",
        hpError.get(0).asText()
      );
    }
  }

  @Nested
}
