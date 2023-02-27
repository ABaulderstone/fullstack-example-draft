package io.nology.fullstackexample.pokemon;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class PokemonUpdateDto {

  @Size(min = 1)
  private String name;

  @Size(min = 1)
  private String element;

  @Min(20)
  private Integer hp;

  @Min(5)
  private Integer attackPower;
}
