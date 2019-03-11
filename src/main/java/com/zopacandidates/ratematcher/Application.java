package com.zopacandidates.ratematcher;

import java.io.IOException;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

import static java.util.Locale.UK;

public class Application {
  int length;
  private Locale locale;
  private RateCalculator calculator;

  public static void main(String[] args) {
    Validator validator = new Validator();
    Either<String, Request> request = validator.validate(args);
    if (request.isLeft()) {
      System.out.println(request.getLeft());
      System.exit(1);
    }

    try {
      RateRepository repository = new RateRepository(request.getRight().getFileName());
      Application application = new Application(new RateCalculator(repository), UK);
      application.printQuote(request.getRight(), System.out);
    } catch (IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
  }

  public Application(RateCalculator calculator, Locale locale) {
    this(calculator, 36, locale);
  }

  public Application(RateCalculator calculator, int length, Locale locale) {
    this.calculator = calculator;
    this.locale = locale;
    this.length = length;
  }

  public void printQuote(Request request, PrintStream printStream) {
    Optional<Quote> quote = getQuote(request);
    String output = quote.map(this::formatQuote)
      .orElse("It is not possible to provide a quote at this time.");

    printStream.println(output);
  }

  public Optional<Quote> getQuote(Request request) {
    return calculator.quote(request.getAmount(), 36);
  }

  public String formatQuote(Quote quote) {
    NumberFormat fc = NumberFormat.getCurrencyInstance(locale);
    NumberFormat f = NumberFormat.getInstance(locale);
    StringBuilder builder = new StringBuilder();
    builder.append("Requested amount: ")
      .append(fc.format(quote.getAmount()))
      .append("\n");
    builder.append("Rate: ")
      .append(f.format(Math.ceil(quote.getRate() * 1000) / 10.0)).append("%")
      .append("\n");
    builder.append("Monthly repayment: ")
      .append(fc.format(quote.getMonthlyRepayment()))
      .append("\n");
    builder.append("Total repayment: ")
      .append(fc.format(quote.getTotalRepayment()));
    return builder.toString();
  }
}
