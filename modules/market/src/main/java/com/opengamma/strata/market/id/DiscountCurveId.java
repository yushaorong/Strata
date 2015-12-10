/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.market.id;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.basics.market.MarketDataFeed;
import com.opengamma.strata.market.curve.Curve;
import com.opengamma.strata.market.curve.CurveGroupName;
import com.opengamma.strata.market.key.DiscountCurveKey;

/**
 * Market data ID identifying the discount curve for a currency.
 * <p>
 * This is used when there is a need to obtain an instance of {@link Curve} that
 * can be used to determine discount factors.
 */
@BeanDefinition(builderScope = "private")
public final class DiscountCurveId
    implements RateCurveId, ImmutableBean, Serializable {

  /**
   * The currency of the discount factor curve that is required.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final Currency currency;
  /**
   * The name of the curve group containing the curve.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final CurveGroupName curveGroupName;
  /**
   * The market data feed which provides quotes used to build the curve.
   */
  @PropertyDefinition(validate = "notNull")
  private final MarketDataFeed marketDataFeed;

  //-------------------------------------------------------------------------
  /**
   * Obtains an ID used to find the discount factor curve associated with a currency.
   *
   * @param currency  the currency of the discount curve
   * @param curveGroupName  the name of the curve group containing the curve
   * @param marketDataFeed  the market data feed which provides quotes used to build the curve
   * @return an ID that identifies the discount curve for the specified currency
   */
  public static DiscountCurveId of(Currency currency, CurveGroupName curveGroupName, MarketDataFeed marketDataFeed) {
    return new DiscountCurveId(currency, curveGroupName, marketDataFeed);
  }

  /**
   * Obtains an ID used to find the discount factor curve associated with a currency.
   * <p>
   * The result will use {@link MarketDataFeed#NONE}.
   *
   * @param currency  the currency of the discounting curve
   * @param curveGroupName  the name of the curve group containing the curve
   * @return an ID that identifies the discount curve for the specified currency
   */
  public static DiscountCurveId of(Currency currency, CurveGroupName curveGroupName) {
    return new DiscountCurveId(currency, curveGroupName, MarketDataFeed.NONE);
  }

  @Override
  public DiscountCurveKey toMarketDataKey() {
    return DiscountCurveKey.of(currency);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code DiscountCurveId}.
   * @return the meta-bean, not null
   */
  public static DiscountCurveId.Meta meta() {
    return DiscountCurveId.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(DiscountCurveId.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private DiscountCurveId(
      Currency currency,
      CurveGroupName curveGroupName,
      MarketDataFeed marketDataFeed) {
    JodaBeanUtils.notNull(currency, "currency");
    JodaBeanUtils.notNull(curveGroupName, "curveGroupName");
    JodaBeanUtils.notNull(marketDataFeed, "marketDataFeed");
    this.currency = currency;
    this.curveGroupName = curveGroupName;
    this.marketDataFeed = marketDataFeed;
  }

  @Override
  public DiscountCurveId.Meta metaBean() {
    return DiscountCurveId.Meta.INSTANCE;
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
   * Gets the currency of the discount factor curve that is required.
   * @return the value of the property, not null
   */
  @Override
  public Currency getCurrency() {
    return currency;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the name of the curve group containing the curve.
   * @return the value of the property, not null
   */
  @Override
  public CurveGroupName getCurveGroupName() {
    return curveGroupName;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the market data feed which provides quotes used to build the curve.
   * @return the value of the property, not null
   */
  public MarketDataFeed getMarketDataFeed() {
    return marketDataFeed;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      DiscountCurveId other = (DiscountCurveId) obj;
      return JodaBeanUtils.equal(currency, other.currency) &&
          JodaBeanUtils.equal(curveGroupName, other.curveGroupName) &&
          JodaBeanUtils.equal(marketDataFeed, other.marketDataFeed);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(currency);
    hash = hash * 31 + JodaBeanUtils.hashCode(curveGroupName);
    hash = hash * 31 + JodaBeanUtils.hashCode(marketDataFeed);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(128);
    buf.append("DiscountCurveId{");
    buf.append("currency").append('=').append(currency).append(',').append(' ');
    buf.append("curveGroupName").append('=').append(curveGroupName).append(',').append(' ');
    buf.append("marketDataFeed").append('=').append(JodaBeanUtils.toString(marketDataFeed));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code DiscountCurveId}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code currency} property.
     */
    private final MetaProperty<Currency> currency = DirectMetaProperty.ofImmutable(
        this, "currency", DiscountCurveId.class, Currency.class);
    /**
     * The meta-property for the {@code curveGroupName} property.
     */
    private final MetaProperty<CurveGroupName> curveGroupName = DirectMetaProperty.ofImmutable(
        this, "curveGroupName", DiscountCurveId.class, CurveGroupName.class);
    /**
     * The meta-property for the {@code marketDataFeed} property.
     */
    private final MetaProperty<MarketDataFeed> marketDataFeed = DirectMetaProperty.ofImmutable(
        this, "marketDataFeed", DiscountCurveId.class, MarketDataFeed.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "currency",
        "curveGroupName",
        "marketDataFeed");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 575402001:  // currency
          return currency;
        case -382645893:  // curveGroupName
          return curveGroupName;
        case 842621124:  // marketDataFeed
          return marketDataFeed;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends DiscountCurveId> builder() {
      return new DiscountCurveId.Builder();
    }

    @Override
    public Class<? extends DiscountCurveId> beanType() {
      return DiscountCurveId.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code currency} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Currency> currency() {
      return currency;
    }

    /**
     * The meta-property for the {@code curveGroupName} property.
     * @return the meta-property, not null
     */
    public MetaProperty<CurveGroupName> curveGroupName() {
      return curveGroupName;
    }

    /**
     * The meta-property for the {@code marketDataFeed} property.
     * @return the meta-property, not null
     */
    public MetaProperty<MarketDataFeed> marketDataFeed() {
      return marketDataFeed;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 575402001:  // currency
          return ((DiscountCurveId) bean).getCurrency();
        case -382645893:  // curveGroupName
          return ((DiscountCurveId) bean).getCurveGroupName();
        case 842621124:  // marketDataFeed
          return ((DiscountCurveId) bean).getMarketDataFeed();
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
   * The bean-builder for {@code DiscountCurveId}.
   */
  private static final class Builder extends DirectFieldsBeanBuilder<DiscountCurveId> {

    private Currency currency;
    private CurveGroupName curveGroupName;
    private MarketDataFeed marketDataFeed;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 575402001:  // currency
          return currency;
        case -382645893:  // curveGroupName
          return curveGroupName;
        case 842621124:  // marketDataFeed
          return marketDataFeed;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 575402001:  // currency
          this.currency = (Currency) newValue;
          break;
        case -382645893:  // curveGroupName
          this.curveGroupName = (CurveGroupName) newValue;
          break;
        case 842621124:  // marketDataFeed
          this.marketDataFeed = (MarketDataFeed) newValue;
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
    public DiscountCurveId build() {
      return new DiscountCurveId(
          currency,
          curveGroupName,
          marketDataFeed);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(128);
      buf.append("DiscountCurveId.Builder{");
      buf.append("currency").append('=').append(JodaBeanUtils.toString(currency)).append(',').append(' ');
      buf.append("curveGroupName").append('=').append(JodaBeanUtils.toString(curveGroupName)).append(',').append(' ');
      buf.append("marketDataFeed").append('=').append(JodaBeanUtils.toString(marketDataFeed));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
