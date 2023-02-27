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
