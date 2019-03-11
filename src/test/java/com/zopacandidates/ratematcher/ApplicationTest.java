package com.zopacandidates.ratematcher;

import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

class ApplicationTest {

  @Test
  public void formatQuote() {
    String out = "Requested amount: £1,000.98\n" +
      "Rate: 7.1%\n" +
      "Monthly repayment: £30.82\n" +
      "Total repayment: £1,109.52";
    RateCalculator calculator = mock(RateCalculator.class);
    Application application = new Application(calculator, Locale.UK);
    Quote quote = new Quote(1000.981, 0.0701, 36);
    String result = application.formatQuote(quote);
    assertEquals(out, result);
  }

  @Test
  public void printQuote() {
    RateCalculator calculator = mock(RateCalculator.class);
    when(calculator.quote(10, 36)).thenReturn(Optional.empty());
    PrintStream printStream = mock(PrintStream.class);
    Application application = new Application(calculator, Locale.UK);
    Request request = new Request(null, 10);

    application.printQuote(request, printStream);
    verify(printStream).println(eq("It is not possible to provide a quote at this time."));
  }

}