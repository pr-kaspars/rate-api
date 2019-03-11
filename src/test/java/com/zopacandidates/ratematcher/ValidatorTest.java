package com.zopacandidates.ratematcher;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

  @Test
  public void validateArgLength() {
    Validator validator = new Validator();
    Either<String, Request> result = validator.validate(new String[]{"foo"});
    assertTrue(result.isLeft());
    assertEquals("Application accepts 2 arguments: [market_file] [loan_amount]", result.getLeft());
  }

  @Test
  public void validatePath() {
    Validator validator = new Validator();
    Either<String, Request> result = validator.validate(new String[]{"/foo", "1000"});
    assertTrue(result.isLeft());
    assertEquals("invalids market file", result.getLeft());
  }

  @Test
  public void validateInvalidNumber() {
    String fileName = getClass().getResource("/market.csv").getFile();
    Validator validator = new Validator();
    Either<String, Request> result = validator.validate(new String[]{fileName, "10s00"});
    assertTrue(result.isLeft());
    assertEquals("amount is not a number", result.getLeft());
  }

  @Test
  public void validateSmall() {
    String fileName = getClass().getResource("/market.csv").getFile();
    Validator validator = new Validator();
    Either<String, Request> result = validator.validate(new String[]{fileName, "900"});
    assertTrue(result.isLeft());
    assertEquals("amount must be increment of 100 between 1000 and 15000", result.getLeft());
  }

  @Test
  public void validateLarge() {
    String fileName = getClass().getResource("/market.csv").getFile();
    Validator validator = new Validator();
    Either<String, Request> result = validator.validate(new String[]{fileName, "15100"});
    assertTrue(result.isLeft());
    assertEquals("amount must be increment of 100 between 1000 and 15000", result.getLeft());
  }

  @Test
  public void validateMultiple() {
    String fileName = getClass().getResource("/market.csv").getFile();
    Validator validator = new Validator();
    Either<String, Request> result = validator.validate(new String[]{fileName, "2019"});
    assertTrue(result.isLeft());
    assertEquals("amount must be increment of 100 between 1000 and 15000", result.getLeft());
  }
}