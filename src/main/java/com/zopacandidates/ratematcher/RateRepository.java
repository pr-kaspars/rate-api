package com.zopacandidates.ratematcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RateRepository {
  private List<Rate> rates;

  public RateRepository(String fileName) throws IOException {
    List<Rate> rates = Files.lines(Paths.get(fileName))
      .filter(l -> !l.equals("Lender,Rate,Available"))
      .map(l -> l.split(","))
      .map(r -> new Rate(r[0], Double.parseDouble(r[1]), Integer.parseInt(r[2])))
      .sorted(Comparator.comparingDouble(Rate::getRate))
      .collect(Collectors.toList());
    this.rates = Collections.unmodifiableList(rates);
  }

  public List<Rate> getRates() {
    return rates;
  }
}
