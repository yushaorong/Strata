/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.platform.pricer.sensitivity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableValidator;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ComparisonChain;
import com.opengamma.basics.currency.Currency;
import com.opengamma.basics.index.OvernightIndex;
import com.opengamma.collect.ArgChecker;

/**
 * Point sensitivity to a rate from an Overnight index curve.
 * <p>
 * Holds the sensitivity to the {@link OvernightIndex} curve for a fixing period.
 * <p>
 * This class handles the common case where the rate for a period is approximated
 * instead of computing the individual rate for each date in the period by storing
 * the end date of the fixing period.
 */
@BeanDefinition
public final class OvernightRateSensitivity
    implements CurveSensitivity, ImmutableBean, Serializable {

  /**
   * The index of the curve for which the sensitivity is computed.
   */
  @PropertyDefinition(validate = "notNull")
  private final OvernightIndex index;
  /**
   * The currency of the sensitivity.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final Currency currency;
  /**
   * The fixing date that was looked up on the curve.
   */
  @PropertyDefinition(validate = "notNull")
  private final LocalDate fixingDate;
  /**
   * The end date of the period.
   * This must be after the fixing date.
   * It may be the maturity date implied by the fixing date, but it may also be later.
   */
  @PropertyDefinition(validate = "notNull")
  private final LocalDate endDate;
  /**
   * The value of the sensitivity.
   */
  @PropertyDefinition(overrideGet = true)
  private final double sensitivity;

  //-------------------------------------------------------------------------
  /**
   * Obtains an {@code OvernightRateSensitivity} from the index, currency, fixing date and value.
   * <p>
   * The end date is calculated using the index to be the maturity date of a single day deposit
   * based on the fixing date.
   * 
   * @param index  the index of the curve
   * @param currency  the currency of the curve
   * @param fixingDate  the fixing date
   * @param endDate  the end date of the period, if period curve lookup
   * @param sensitivity  the value of the sensitivity
   * @return the point sensitivity object
   */
  public static OvernightRateSensitivity of(
      OvernightIndex index, Currency currency, LocalDate fixingDate, double sensitivity) {
    LocalDate endDate = index.calculateMaturityFromEffective(index.calculateEffectiveFromFixing(fixingDate));
    return OvernightRateSensitivity.of(index, currency, fixingDate, endDate, sensitivity);
  }

  /**
   * Obtains an {@code OvernightRateSensitivity} from the index, currency, fixing date, end date and value.
   * 
   * @param index  the index of the curve
   * @param currency  the currency of the curve
   * @param fixingDate  the fixing date
   * @param endDate  the end date of the period, if period curve lookup
   * @param sensitivity  the value of the sensitivity
   * @return the point sensitivity object
   */
  public static OvernightRateSensitivity of(
      OvernightIndex index, Currency currency, LocalDate fixingDate, LocalDate endDate, double sensitivity) {
    return new OvernightRateSensitivity(index, currency, fixingDate, endDate, sensitivity);
  }

  //-------------------------------------------------------------------------
  @ImmutableValidator
  private void validate() {
    ArgChecker.inOrderNotEqual(fixingDate, endDate, "fixingDate", "endDate");
  }

  //-------------------------------------------------------------------------
  @Override
  public Object getCurveKey() {
    return index;
  }

  @Override
  public LocalDate getDate() {
    return fixingDate;
  }

  @Override
  public OvernightRateSensitivity withSensitivity(double sensitivity) {
    return new OvernightRateSensitivity(index, currency, fixingDate, endDate, sensitivity);
  }

  @Override
  public int compareExcludingSensitivity(CurveSensitivity other) {
    if (other instanceof OvernightRateSensitivity) {
      OvernightRateSensitivity otherOn = (OvernightRateSensitivity) other;
      return ComparisonChain.start()
          .compare(index.toString(), otherOn.index.toString())
          .compare(currency, otherOn.currency)
          .compare(fixingDate, otherOn.fixingDate)
          .compare(endDate, otherOn.endDate)
          .result();
    }
    return ComparisonChain.start()
        .compare(index.toString(), other.getCurveKey().toString())
        .result();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code OvernightRateSensitivity}.
   * @return the meta-bean, not null
   */
  public static OvernightRateSensitivity.Meta meta() {
    return OvernightRateSensitivity.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(OvernightRateSensitivity.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static OvernightRateSensitivity.Builder builder() {
    return new OvernightRateSensitivity.Builder();
  }

  private OvernightRateSensitivity(
      OvernightIndex index,
      Currency currency,
      LocalDate fixingDate,
      LocalDate endDate,
      double sensitivity) {
    JodaBeanUtils.notNull(index, "index");
    JodaBeanUtils.notNull(currency, "currency");
    JodaBeanUtils.notNull(fixingDate, "fixingDate");
    JodaBeanUtils.notNull(endDate, "endDate");
    this.index = index;
    this.currency = currency;
    this.fixingDate = fixingDate;
    this.endDate = endDate;
    this.sensitivity = sensitivity;
    validate();
  }

  @Override
  public OvernightRateSensitivity.Meta metaBean() {
    return OvernightRateSensitivity.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the index of the curve for which the sensitivity is computed.
   * @return the value of the property, not null
   */
  public OvernightIndex getIndex() {
    return index;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the currency of the sensitivity.
   * @return the value of the property, not null
   */
  @Override
  public Currency getCurrency() {
    return currency;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the fixing date that was looked up on the curve.
   * @return the value of the property, not null
   */
  public LocalDate getFixingDate() {
    return fixingDate;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the end date of the period.
   * This must be after the fixing date.
   * It may be the maturity date implied by the fixing date, but it may also be later.
   * @return the value of the property, not null
   */
  public LocalDate getEndDate() {
    return endDate;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the value of the sensitivity.
   * @return the value of the property
   */
  @Override
  public double getSensitivity() {
    return sensitivity;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      OvernightRateSensitivity other = (OvernightRateSensitivity) obj;
      return JodaBeanUtils.equal(getIndex(), other.getIndex()) &&
          JodaBeanUtils.equal(getCurrency(), other.getCurrency()) &&
          JodaBeanUtils.equal(getFixingDate(), other.getFixingDate()) &&
          JodaBeanUtils.equal(getEndDate(), other.getEndDate()) &&
          JodaBeanUtils.equal(getSensitivity(), other.getSensitivity());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getIndex());
    hash = hash * 31 + JodaBeanUtils.hashCode(getCurrency());
    hash = hash * 31 + JodaBeanUtils.hashCode(getFixingDate());
    hash = hash * 31 + JodaBeanUtils.hashCode(getEndDate());
    hash = hash * 31 + JodaBeanUtils.hashCode(getSensitivity());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(192);
    buf.append("OvernightRateSensitivity{");
    buf.append("index").append('=').append(getIndex()).append(',').append(' ');
    buf.append("currency").append('=').append(getCurrency()).append(',').append(' ');
    buf.append("fixingDate").append('=').append(getFixingDate()).append(',').append(' ');
    buf.append("endDate").append('=').append(getEndDate()).append(',').append(' ');
    buf.append("sensitivity").append('=').append(JodaBeanUtils.toString(getSensitivity()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code OvernightRateSensitivity}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code index} property.
     */
    private final MetaProperty<OvernightIndex> index = DirectMetaProperty.ofImmutable(
        this, "index", OvernightRateSensitivity.class, OvernightIndex.class);
    /**
     * The meta-property for the {@code currency} property.
     */
    private final MetaProperty<Currency> currency = DirectMetaProperty.ofImmutable(
        this, "currency", OvernightRateSensitivity.class, Currency.class);
    /**
     * The meta-property for the {@code fixingDate} property.
     */
    private final MetaProperty<LocalDate> fixingDate = DirectMetaProperty.ofImmutable(
        this, "fixingDate", OvernightRateSensitivity.class, LocalDate.class);
    /**
     * The meta-property for the {@code endDate} property.
     */
    private final MetaProperty<LocalDate> endDate = DirectMetaProperty.ofImmutable(
        this, "endDate", OvernightRateSensitivity.class, LocalDate.class);
    /**
     * The meta-property for the {@code sensitivity} property.
     */
    private final MetaProperty<Double> sensitivity = DirectMetaProperty.ofImmutable(
        this, "sensitivity", OvernightRateSensitivity.class, Double.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "index",
        "currency",
        "fixingDate",
        "endDate",
        "sensitivity");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 100346066:  // index
          return index;
        case 575402001:  // currency
          return currency;
        case 1255202043:  // fixingDate
          return fixingDate;
        case -1607727319:  // endDate
          return endDate;
        case 564403871:  // sensitivity
          return sensitivity;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public OvernightRateSensitivity.Builder builder() {
      return new OvernightRateSensitivity.Builder();
    }

    @Override
    public Class<? extends OvernightRateSensitivity> beanType() {
      return OvernightRateSensitivity.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code index} property.
     * @return the meta-property, not null
     */
    public MetaProperty<OvernightIndex> index() {
      return index;
    }

    /**
     * The meta-property for the {@code currency} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Currency> currency() {
      return currency;
    }

    /**
     * The meta-property for the {@code fixingDate} property.
     * @return the meta-property, not null
     */
    public MetaProperty<LocalDate> fixingDate() {
      return fixingDate;
    }

    /**
     * The meta-property for the {@code endDate} property.
     * @return the meta-property, not null
     */
    public MetaProperty<LocalDate> endDate() {
      return endDate;
    }

    /**
     * The meta-property for the {@code sensitivity} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Double> sensitivity() {
      return sensitivity;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 100346066:  // index
          return ((OvernightRateSensitivity) bean).getIndex();
        case 575402001:  // currency
          return ((OvernightRateSensitivity) bean).getCurrency();
        case 1255202043:  // fixingDate
          return ((OvernightRateSensitivity) bean).getFixingDate();
        case -1607727319:  // endDate
          return ((OvernightRateSensitivity) bean).getEndDate();
        case 564403871:  // sensitivity
          return ((OvernightRateSensitivity) bean).getSensitivity();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code OvernightRateSensitivity}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<OvernightRateSensitivity> {

    private OvernightIndex index;
    private Currency currency;
    private LocalDate fixingDate;
    private LocalDate endDate;
    private double sensitivity;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(OvernightRateSensitivity beanToCopy) {
      this.index = beanToCopy.getIndex();
      this.currency = beanToCopy.getCurrency();
      this.fixingDate = beanToCopy.getFixingDate();
      this.endDate = beanToCopy.getEndDate();
      this.sensitivity = beanToCopy.getSensitivity();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 100346066:  // index
          return index;
        case 575402001:  // currency
          return currency;
        case 1255202043:  // fixingDate
          return fixingDate;
        case -1607727319:  // endDate
          return endDate;
        case 564403871:  // sensitivity
          return sensitivity;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 100346066:  // index
          this.index = (OvernightIndex) newValue;
          break;
        case 575402001:  // currency
          this.currency = (Currency) newValue;
          break;
        case 1255202043:  // fixingDate
          this.fixingDate = (LocalDate) newValue;
          break;
        case -1607727319:  // endDate
          this.endDate = (LocalDate) newValue;
          break;
        case 564403871:  // sensitivity
          this.sensitivity = (Double) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public OvernightRateSensitivity build() {
      return new OvernightRateSensitivity(
          index,
          currency,
          fixingDate,
          endDate,
          sensitivity);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code index} property in the builder.
     * @param index  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder index(OvernightIndex index) {
      JodaBeanUtils.notNull(index, "index");
      this.index = index;
      return this;
    }

    /**
     * Sets the {@code currency} property in the builder.
     * @param currency  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder currency(Currency currency) {
      JodaBeanUtils.notNull(currency, "currency");
      this.currency = currency;
      return this;
    }

    /**
     * Sets the {@code fixingDate} property in the builder.
     * @param fixingDate  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder fixingDate(LocalDate fixingDate) {
      JodaBeanUtils.notNull(fixingDate, "fixingDate");
      this.fixingDate = fixingDate;
      return this;
    }

    /**
     * Sets the {@code endDate} property in the builder.
     * @param endDate  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder endDate(LocalDate endDate) {
      JodaBeanUtils.notNull(endDate, "endDate");
      this.endDate = endDate;
      return this;
    }

    /**
     * Sets the {@code sensitivity} property in the builder.
     * @param sensitivity  the new value
     * @return this, for chaining, not null
     */
    public Builder sensitivity(double sensitivity) {
      this.sensitivity = sensitivity;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(192);
      buf.append("OvernightRateSensitivity.Builder{");
      buf.append("index").append('=').append(JodaBeanUtils.toString(index)).append(',').append(' ');
      buf.append("currency").append('=').append(JodaBeanUtils.toString(currency)).append(',').append(' ');
      buf.append("fixingDate").append('=').append(JodaBeanUtils.toString(fixingDate)).append(',').append(' ');
      buf.append("endDate").append('=').append(JodaBeanUtils.toString(endDate)).append(',').append(' ');
      buf.append("sensitivity").append('=').append(JodaBeanUtils.toString(sensitivity));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}