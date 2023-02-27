package io.nology.fullstackexample.pokemon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pokemon {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @Column
  private String element;

  @Column(columnDefinition = "int default 1")
  private Integer level;

  @Column
  private Integer hp;

  @Column
  private Integer attackPower;

  @Column
  private Integer remainingHp;

  public Pokemon() {
    this.level = 1;
  }

  public Pokemon(String name, String element, Integer hp, Integer attackPower) {
    this.name = name;
    this.element = element;
    this.hp = hp;
    this.attackPower = attackPower;
    this.level = 1;
    this.remainingHp = hp;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
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

  public Integer getRemainingHp() {
    return remainingHp;
  }

  public void setRemainingHp(Integer remainingHp) {
    this.remainingHp = remainingHp;
  }
}
