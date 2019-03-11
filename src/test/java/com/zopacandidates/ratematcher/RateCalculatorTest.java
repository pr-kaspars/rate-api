package com.zopacandidates.ratematcher;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RateCalculatorTest {

  @Test
  public void quoteEmpty() {
    RateRepository repository = mock(RateRepository.class);
    when(repository.getRates()).thenReturn(Collections.emptyList());
    RateCalculator calculator = new RateCalculator(repository);
    assertEquals(Optional.empty(), calculator.quote(100, 36));
  }

  @Test
  public void quoteInsufficient() {
    RateRepository repository = mock(RateRepository.class);
    when(repository.getRates()).thenReturn(List.of(new Rate("Foo", 0.05, 100)));
    RateCalculator calculator = new RateCalculator(repository);
    assertEquals(Optional.empty(), calculator.quote(200, 36));
  }

  @Test
  public void quoteOne() {
    RateRepository repository = mock(RateRepository.class);
    when(repository.getRates()).thenReturn(List.of(new Rate("Foo", 0.07, 1000)));
    RateCalculator calculator = new RateCalculator(repository);
    Quote quote = calculator.quote(1000, 36).get();
    assertEquals(0.07, quote.getRate());
    assertEquals(30.78, quote.getMonthlyRepayment());
    assertEquals(1108.08, quote.getTotalRepayment());
  }

  @Test
  public void quoteTwoSame() {
    RateRepository repository = mock(RateRepository.class);
    when(repository.getRates())
      .thenReturn(List.of(
        new Rate("Foo", 0.07, 500),
        new Rate("Bar", 0.07, 500)
      ));
    RateCalculator calculator = new RateCalculator(repository);
    Quote quote = calculator.quote(1000, 36).get();
    assertEquals(0.07, quote.getRate());
    assertEquals(30.78, quote.getMonthlyRepayment());
    assertEquals(1108.08, quote.getTotalRepayment());
  }

  @Test
  public void quoteThreeDifferent() {
    RateRepository repository = mock(RateRepository.class);
    when(repository.getRates())
      .thenReturn(List.of(
        new Rate("Foo", 0.05, 500),
        new Rate("Bar", 0.1, 500),
        new Rate("Baz", 0.15, 500)
      ));
    RateCalculator calculator = new RateCalculator(repository);
    Quote quote = calculator.quote(1500, 36).get();
    assertEquals(0.1, quote.getRate());
    assertEquals(48.1, quote.getMonthlyRepayment());
    assertEquals(1731.6, quote.getTotalRepayment());
  }
}
