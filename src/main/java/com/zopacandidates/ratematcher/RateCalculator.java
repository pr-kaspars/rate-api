package com.zopacandidates.ratematcher;

import java.util.List;
import java.util.Optional;

public class RateCalculator {
  private RateRepository rateRepository;

  public RateCalculator(RateRepository rateRepository) {
    this.rateRepository = rateRepository;
  }

  public Optional<Quote> quote(double amount, int length) {
    List<Rate> rates = rateRepository.getRates();
    double a = amount;
    double r = 0;
    for (int i = 0; i < rates.size() && a > 0; i++) {
      double b = Math.min(a, rates.get(i).getAvailable());
      r += rates.get(i).getRate() * (b / amount);
      a -= b;
    }
    return a > 0 ? Optional.empty() : Optional.of(new Quote(amount, r, length));
  }
}
