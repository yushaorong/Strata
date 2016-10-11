/**
 * Copyright (C) 2016 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.pricer.credit.cds;

import java.util.Locale;

import org.joda.convert.FromString;
import org.joda.convert.ToString;

import com.opengamma.strata.collect.ArgChecker;

/**
 * The formula for accrual on default.
 * <p>
 * This specifies which formula is used in {@code IsdaCdsProductPricer} for computing the accrued payment on default. 
 * The formula is 'original ISDA', 'Markit fix' or 'correct'.
 */
public enum AccrualOnDefaultFormula {

  /**
   * The formula in v1.8.1 and below.
   */
  ORIGINAL_ISDA("Original-ISDA"),

  /**
   * The correction proposed by Markit (v 1.8.2).
   */
  MARKIT_FIX("Markit-Fix"),

  /**
   * The mathematically correct formula .
   */
  CORRECT("Correct");

  // name
  private final String name;

  // create
  private AccrualOnDefaultFormula(String name) {
    this.name = name;
  }

  //-------------------------------------------------------------------------
  /**
   * Obtains an instance from the specified unique name.
   * 
   * @param uniqueName  the unique name
   * @return the type
   * @throws IllegalArgumentException if the name is not known
   */
  @FromString
  public static AccrualOnDefaultFormula of(String uniqueName) {
    ArgChecker.notNull(uniqueName, "uniqueName");
    return valueOf(uniqueName.replace('-', '_').toUpperCase(Locale.ENGLISH));
  }

  /**
   * Returns the formatted unique name of the type.
   * 
   * @return the formatted string representing the type
   */
  @ToString
  @Override
  public String toString() {
    return name;
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the omega value. 
   * <p>
   * The omega value is used in {@link IsdaCdsProductPricer}.
   * 
   * @return the omega value
   */
  public double getOmega() {
    if (this == ORIGINAL_ISDA) {
      return 1d / 730d;
    } else {
      return 0d;
    }
  }
}
