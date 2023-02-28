package io.nology.fullstackexample.pokemon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
  // Sometimes we need a custom query - this is using JPQL syntax
  @Query("SELECT MAX(p.id) FROM Pokemon p")
  Long lastUsedId();
}
