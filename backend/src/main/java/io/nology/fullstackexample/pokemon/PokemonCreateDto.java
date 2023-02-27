package io.nology.fullstackexample.pokemon;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PokemonCreateDto {

  @NotBlank
  private String name;

  @NotBlank
  private String element;

  @NotNull
  @Min(20)
  private Integer hp;

  @NotNull
  @Min(5)
  private Integer attackPower;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getElement() {
    return element;
  }

  public void setElement(String element) {
    this.element = element;
  }

  public Integer getHp() {
    return hp;
  }

  public void setHp(Integer hp) {
    this.hp = hp;
  }

  public Integer getAttackPower() {
    return attackPower;
  }

  public void setAttackPower(Integer attackPower) {
    this.attackPower = attackPower;
  }
}
