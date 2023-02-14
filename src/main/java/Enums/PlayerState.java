package Enums;

public enum PlayerState {
  FETCHING(1),
  CHASING(2),
  ESCAPING(3);

  public final Integer value;

  private PlayerState(Integer value) {
    this.value = value;
  }
}
