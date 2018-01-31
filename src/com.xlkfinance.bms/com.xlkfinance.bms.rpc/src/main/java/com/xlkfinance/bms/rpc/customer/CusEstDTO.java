/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xlkfinance.bms.rpc.customer;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2017-7-25")
public class CusEstDTO implements org.apache.thrift.TBase<CusEstDTO, CusEstDTO._Fields>, java.io.Serializable, Cloneable, Comparable<CusEstDTO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CusEstDTO");

  private static final org.apache.thrift.protocol.TField CUS_EST_FACTOR_WEIGHTS_FIELD_DESC = new org.apache.thrift.protocol.TField("cusEstFactorWeights", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField CUS_EST_QUOTA_FIELD_DESC = new org.apache.thrift.protocol.TField("cusEstQuota", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField QUOTAS_FIELD_DESC = new org.apache.thrift.protocol.TField("quotas", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField CUS_EST_OPTION_FIELD_DESC = new org.apache.thrift.protocol.TField("cusEstOption", org.apache.thrift.protocol.TType.STRUCT, (short)4);
  private static final org.apache.thrift.protocol.TField OPTIONS_FIELD_DESC = new org.apache.thrift.protocol.TField("options", org.apache.thrift.protocol.TType.LIST, (short)5);
  private static final org.apache.thrift.protocol.TField CUS_EST_TEMPLATE_FIELD_DESC = new org.apache.thrift.protocol.TField("cusEstTemplate", org.apache.thrift.protocol.TType.STRUCT, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CusEstDTOStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CusEstDTOTupleSchemeFactory());
  }

  public CusEstFactorWeights cusEstFactorWeights; // required
  public CusEstQuota cusEstQuota; // required
  public List<CusEstQuota> quotas; // required
  public CusEstOption cusEstOption; // required
  public List<CusEstOption> options; // required
  public CusEstTemplate cusEstTemplate; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CUS_EST_FACTOR_WEIGHTS((short)1, "cusEstFactorWeights"),
    CUS_EST_QUOTA((short)2, "cusEstQuota"),
    QUOTAS((short)3, "quotas"),
    CUS_EST_OPTION((short)4, "cusEstOption"),
    OPTIONS((short)5, "options"),
    CUS_EST_TEMPLATE((short)6, "cusEstTemplate");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // CUS_EST_FACTOR_WEIGHTS
          return CUS_EST_FACTOR_WEIGHTS;
        case 2: // CUS_EST_QUOTA
          return CUS_EST_QUOTA;
        case 3: // QUOTAS
          return QUOTAS;
        case 4: // CUS_EST_OPTION
          return CUS_EST_OPTION;
        case 5: // OPTIONS
          return OPTIONS;
        case 6: // CUS_EST_TEMPLATE
          return CUS_EST_TEMPLATE;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CUS_EST_FACTOR_WEIGHTS, new org.apache.thrift.meta_data.FieldMetaData("cusEstFactorWeights", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, CusEstFactorWeights.class)));
    tmpMap.put(_Fields.CUS_EST_QUOTA, new org.apache.thrift.meta_data.FieldMetaData("cusEstQuota", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, CusEstQuota.class)));
    tmpMap.put(_Fields.QUOTAS, new org.apache.thrift.meta_data.FieldMetaData("quotas", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, CusEstQuota.class))));
    tmpMap.put(_Fields.CUS_EST_OPTION, new org.apache.thrift.meta_data.FieldMetaData("cusEstOption", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT        , "CusEstOption")));
    tmpMap.put(_Fields.OPTIONS, new org.apache.thrift.meta_data.FieldMetaData("options", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "CusEstOption"))));
    tmpMap.put(_Fields.CUS_EST_TEMPLATE, new org.apache.thrift.meta_data.FieldMetaData("cusEstTemplate", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, CusEstTemplate.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CusEstDTO.class, metaDataMap);
  }

  public CusEstDTO() {
  }

  public CusEstDTO(
    CusEstFactorWeights cusEstFactorWeights,
    CusEstQuota cusEstQuota,
    List<CusEstQuota> quotas,
    CusEstOption cusEstOption,
    List<CusEstOption> options,
    CusEstTemplate cusEstTemplate)
  {
    this();
    this.cusEstFactorWeights = cusEstFactorWeights;
    this.cusEstQuota = cusEstQuota;
    this.quotas = quotas;
    this.cusEstOption = cusEstOption;
    this.options = options;
    this.cusEstTemplate = cusEstTemplate;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CusEstDTO(CusEstDTO other) {
    if (other.isSetCusEstFactorWeights()) {
      this.cusEstFactorWeights = new CusEstFactorWeights(other.cusEstFactorWeights);
    }
    if (other.isSetCusEstQuota()) {
      this.cusEstQuota = new CusEstQuota(other.cusEstQuota);
    }
    if (other.isSetQuotas()) {
      List<CusEstQuota> __this__quotas = new ArrayList<CusEstQuota>(other.quotas.size());
      for (CusEstQuota other_element : other.quotas) {
        __this__quotas.add(new CusEstQuota(other_element));
      }
      this.quotas = __this__quotas;
    }
    if (other.isSetCusEstOption()) {
      this.cusEstOption = other.cusEstOption;
    }
    if (other.isSetOptions()) {
      List<CusEstOption> __this__options = new ArrayList<CusEstOption>(other.options.size());
      for (CusEstOption other_element : other.options) {
        __this__options.add(other_element);
      }
      this.options = __this__options;
    }
    if (other.isSetCusEstTemplate()) {
      this.cusEstTemplate = new CusEstTemplate(other.cusEstTemplate);
    }
  }

  public CusEstDTO deepCopy() {
    return new CusEstDTO(this);
  }

  @Override
  public void clear() {
    this.cusEstFactorWeights = null;
    this.cusEstQuota = null;
    this.quotas = null;
    this.cusEstOption = null;
    this.options = null;
    this.cusEstTemplate = null;
  }

  public CusEstFactorWeights getCusEstFactorWeights() {
    return this.cusEstFactorWeights;
  }

  public CusEstDTO setCusEstFactorWeights(CusEstFactorWeights cusEstFactorWeights) {
    this.cusEstFactorWeights = cusEstFactorWeights;
    return this;
  }

  public void unsetCusEstFactorWeights() {
    this.cusEstFactorWeights = null;
  }

  /** Returns true if field cusEstFactorWeights is set (has been assigned a value) and false otherwise */
  public boolean isSetCusEstFactorWeights() {
    return this.cusEstFactorWeights != null;
  }

  public void setCusEstFactorWeightsIsSet(boolean value) {
    if (!value) {
      this.cusEstFactorWeights = null;
    }
  }

  public CusEstQuota getCusEstQuota() {
    return this.cusEstQuota;
  }

  public CusEstDTO setCusEstQuota(CusEstQuota cusEstQuota) {
    this.cusEstQuota = cusEstQuota;
    return this;
  }

  public void unsetCusEstQuota() {
    this.cusEstQuota = null;
  }

  /** Returns true if field cusEstQuota is set (has been assigned a value) and false otherwise */
  public boolean isSetCusEstQuota() {
    return this.cusEstQuota != null;
  }

  public void setCusEstQuotaIsSet(boolean value) {
    if (!value) {
      this.cusEstQuota = null;
    }
  }

  public int getQuotasSize() {
    return (this.quotas == null) ? 0 : this.quotas.size();
  }

  public java.util.Iterator<CusEstQuota> getQuotasIterator() {
    return (this.quotas == null) ? null : this.quotas.iterator();
  }

  public void addToQuotas(CusEstQuota elem) {
    if (this.quotas == null) {
      this.quotas = new ArrayList<CusEstQuota>();
    }
    this.quotas.add(elem);
  }

  public List<CusEstQuota> getQuotas() {
    return this.quotas;
  }

  public CusEstDTO setQuotas(List<CusEstQuota> quotas) {
    this.quotas = quotas;
    return this;
  }

  public void unsetQuotas() {
    this.quotas = null;
  }

  /** Returns true if field quotas is set (has been assigned a value) and false otherwise */
  public boolean isSetQuotas() {
    return this.quotas != null;
  }

  public void setQuotasIsSet(boolean value) {
    if (!value) {
      this.quotas = null;
    }
  }

  public CusEstOption getCusEstOption() {
    return this.cusEstOption;
  }

  public CusEstDTO setCusEstOption(CusEstOption cusEstOption) {
    this.cusEstOption = cusEstOption;
    return this;
  }

  public void unsetCusEstOption() {
    this.cusEstOption = null;
  }

  /** Returns true if field cusEstOption is set (has been assigned a value) and false otherwise */
  public boolean isSetCusEstOption() {
    return this.cusEstOption != null;
  }

  public void setCusEstOptionIsSet(boolean value) {
    if (!value) {
      this.cusEstOption = null;
    }
  }

  public int getOptionsSize() {
    return (this.options == null) ? 0 : this.options.size();
  }

  public java.util.Iterator<CusEstOption> getOptionsIterator() {
    return (this.options == null) ? null : this.options.iterator();
  }

  public void addToOptions(CusEstOption elem) {
    if (this.options == null) {
      this.options = new ArrayList<CusEstOption>();
    }
    this.options.add(elem);
  }

  public List<CusEstOption> getOptions() {
    return this.options;
  }

  public CusEstDTO setOptions(List<CusEstOption> options) {
    this.options = options;
    return this;
  }

  public void unsetOptions() {
    this.options = null;
  }

  /** Returns true if field options is set (has been assigned a value) and false otherwise */
  public boolean isSetOptions() {
    return this.options != null;
  }

  public void setOptionsIsSet(boolean value) {
    if (!value) {
      this.options = null;
    }
  }

  public CusEstTemplate getCusEstTemplate() {
    return this.cusEstTemplate;
  }

  public CusEstDTO setCusEstTemplate(CusEstTemplate cusEstTemplate) {
    this.cusEstTemplate = cusEstTemplate;
    return this;
  }

  public void unsetCusEstTemplate() {
    this.cusEstTemplate = null;
  }

  /** Returns true if field cusEstTemplate is set (has been assigned a value) and false otherwise */
  public boolean isSetCusEstTemplate() {
    return this.cusEstTemplate != null;
  }

  public void setCusEstTemplateIsSet(boolean value) {
    if (!value) {
      this.cusEstTemplate = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CUS_EST_FACTOR_WEIGHTS:
      if (value == null) {
        unsetCusEstFactorWeights();
      } else {
        setCusEstFactorWeights((CusEstFactorWeights)value);
      }
      break;

    case CUS_EST_QUOTA:
      if (value == null) {
        unsetCusEstQuota();
      } else {
        setCusEstQuota((CusEstQuota)value);
      }
      break;

    case QUOTAS:
      if (value == null) {
        unsetQuotas();
      } else {
        setQuotas((List<CusEstQuota>)value);
      }
      break;

    case CUS_EST_OPTION:
      if (value == null) {
        unsetCusEstOption();
      } else {
        setCusEstOption((CusEstOption)value);
      }
      break;

    case OPTIONS:
      if (value == null) {
        unsetOptions();
      } else {
        setOptions((List<CusEstOption>)value);
      }
      break;

    case CUS_EST_TEMPLATE:
      if (value == null) {
        unsetCusEstTemplate();
      } else {
        setCusEstTemplate((CusEstTemplate)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CUS_EST_FACTOR_WEIGHTS:
      return getCusEstFactorWeights();

    case CUS_EST_QUOTA:
      return getCusEstQuota();

    case QUOTAS:
      return getQuotas();

    case CUS_EST_OPTION:
      return getCusEstOption();

    case OPTIONS:
      return getOptions();

    case CUS_EST_TEMPLATE:
      return getCusEstTemplate();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CUS_EST_FACTOR_WEIGHTS:
      return isSetCusEstFactorWeights();
    case CUS_EST_QUOTA:
      return isSetCusEstQuota();
    case QUOTAS:
      return isSetQuotas();
    case CUS_EST_OPTION:
      return isSetCusEstOption();
    case OPTIONS:
      return isSetOptions();
    case CUS_EST_TEMPLATE:
      return isSetCusEstTemplate();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CusEstDTO)
      return this.equals((CusEstDTO)that);
    return false;
  }

  public boolean equals(CusEstDTO that) {
    if (that == null)
      return false;

    boolean this_present_cusEstFactorWeights = true && this.isSetCusEstFactorWeights();
    boolean that_present_cusEstFactorWeights = true && that.isSetCusEstFactorWeights();
    if (this_present_cusEstFactorWeights || that_present_cusEstFactorWeights) {
      if (!(this_present_cusEstFactorWeights && that_present_cusEstFactorWeights))
        return false;
      if (!this.cusEstFactorWeights.equals(that.cusEstFactorWeights))
        return false;
    }

    boolean this_present_cusEstQuota = true && this.isSetCusEstQuota();
    boolean that_present_cusEstQuota = true && that.isSetCusEstQuota();
    if (this_present_cusEstQuota || that_present_cusEstQuota) {
      if (!(this_present_cusEstQuota && that_present_cusEstQuota))
        return false;
      if (!this.cusEstQuota.equals(that.cusEstQuota))
        return false;
    }

    boolean this_present_quotas = true && this.isSetQuotas();
    boolean that_present_quotas = true && that.isSetQuotas();
    if (this_present_quotas || that_present_quotas) {
      if (!(this_present_quotas && that_present_quotas))
        return false;
      if (!this.quotas.equals(that.quotas))
        return false;
    }

    boolean this_present_cusEstOption = true && this.isSetCusEstOption();
    boolean that_present_cusEstOption = true && that.isSetCusEstOption();
    if (this_present_cusEstOption || that_present_cusEstOption) {
      if (!(this_present_cusEstOption && that_present_cusEstOption))
        return false;
      if (!this.cusEstOption.equals(that.cusEstOption))
        return false;
    }

    boolean this_present_options = true && this.isSetOptions();
    boolean that_present_options = true && that.isSetOptions();
    if (this_present_options || that_present_options) {
      if (!(this_present_options && that_present_options))
        return false;
      if (!this.options.equals(that.options))
        return false;
    }

    boolean this_present_cusEstTemplate = true && this.isSetCusEstTemplate();
    boolean that_present_cusEstTemplate = true && that.isSetCusEstTemplate();
    if (this_present_cusEstTemplate || that_present_cusEstTemplate) {
      if (!(this_present_cusEstTemplate && that_present_cusEstTemplate))
        return false;
      if (!this.cusEstTemplate.equals(that.cusEstTemplate))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_cusEstFactorWeights = true && (isSetCusEstFactorWeights());
    list.add(present_cusEstFactorWeights);
    if (present_cusEstFactorWeights)
      list.add(cusEstFactorWeights);

    boolean present_cusEstQuota = true && (isSetCusEstQuota());
    list.add(present_cusEstQuota);
    if (present_cusEstQuota)
      list.add(cusEstQuota);

    boolean present_quotas = true && (isSetQuotas());
    list.add(present_quotas);
    if (present_quotas)
      list.add(quotas);

    boolean present_cusEstOption = true && (isSetCusEstOption());
    list.add(present_cusEstOption);
    if (present_cusEstOption)
      list.add(cusEstOption);

    boolean present_options = true && (isSetOptions());
    list.add(present_options);
    if (present_options)
      list.add(options);

    boolean present_cusEstTemplate = true && (isSetCusEstTemplate());
    list.add(present_cusEstTemplate);
    if (present_cusEstTemplate)
      list.add(cusEstTemplate);

    return list.hashCode();
  }

  @Override
  public int compareTo(CusEstDTO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCusEstFactorWeights()).compareTo(other.isSetCusEstFactorWeights());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCusEstFactorWeights()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cusEstFactorWeights, other.cusEstFactorWeights);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCusEstQuota()).compareTo(other.isSetCusEstQuota());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCusEstQuota()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cusEstQuota, other.cusEstQuota);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetQuotas()).compareTo(other.isSetQuotas());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetQuotas()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.quotas, other.quotas);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCusEstOption()).compareTo(other.isSetCusEstOption());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCusEstOption()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cusEstOption, other.cusEstOption);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOptions()).compareTo(other.isSetOptions());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOptions()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.options, other.options);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCusEstTemplate()).compareTo(other.isSetCusEstTemplate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCusEstTemplate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cusEstTemplate, other.cusEstTemplate);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("CusEstDTO(");
    boolean first = true;

    sb.append("cusEstFactorWeights:");
    if (this.cusEstFactorWeights == null) {
      sb.append("null");
    } else {
      sb.append(this.cusEstFactorWeights);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("cusEstQuota:");
    if (this.cusEstQuota == null) {
      sb.append("null");
    } else {
      sb.append(this.cusEstQuota);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("quotas:");
    if (this.quotas == null) {
      sb.append("null");
    } else {
      sb.append(this.quotas);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("cusEstOption:");
    if (this.cusEstOption == null) {
      sb.append("null");
    } else {
      sb.append(this.cusEstOption);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("options:");
    if (this.options == null) {
      sb.append("null");
    } else {
      sb.append(this.options);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("cusEstTemplate:");
    if (this.cusEstTemplate == null) {
      sb.append("null");
    } else {
      sb.append(this.cusEstTemplate);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (cusEstFactorWeights != null) {
      cusEstFactorWeights.validate();
    }
    if (cusEstQuota != null) {
      cusEstQuota.validate();
    }
    if (cusEstTemplate != null) {
      cusEstTemplate.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class CusEstDTOStandardSchemeFactory implements SchemeFactory {
    public CusEstDTOStandardScheme getScheme() {
      return new CusEstDTOStandardScheme();
    }
  }

  private static class CusEstDTOStandardScheme extends StandardScheme<CusEstDTO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CusEstDTO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CUS_EST_FACTOR_WEIGHTS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.cusEstFactorWeights = new CusEstFactorWeights();
              struct.cusEstFactorWeights.read(iprot);
              struct.setCusEstFactorWeightsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CUS_EST_QUOTA
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.cusEstQuota = new CusEstQuota();
              struct.cusEstQuota.read(iprot);
              struct.setCusEstQuotaIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // QUOTAS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list56 = iprot.readListBegin();
                struct.quotas = new ArrayList<CusEstQuota>(_list56.size);
                CusEstQuota _elem57;
                for (int _i58 = 0; _i58 < _list56.size; ++_i58)
                {
                  _elem57 = new CusEstQuota();
                  _elem57.read(iprot);
                  struct.quotas.add(_elem57);
                }
                iprot.readListEnd();
              }
              struct.setQuotasIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CUS_EST_OPTION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.cusEstOption = new CusEstOption();
              struct.cusEstOption.read(iprot);
              struct.setCusEstOptionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // OPTIONS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list59 = iprot.readListBegin();
                struct.options = new ArrayList<CusEstOption>(_list59.size);
                CusEstOption _elem60;
                for (int _i61 = 0; _i61 < _list59.size; ++_i61)
                {
                  _elem60 = new CusEstOption();
                  _elem60.read(iprot);
                  struct.options.add(_elem60);
                }
                iprot.readListEnd();
              }
              struct.setOptionsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CUS_EST_TEMPLATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.cusEstTemplate = new CusEstTemplate();
              struct.cusEstTemplate.read(iprot);
              struct.setCusEstTemplateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, CusEstDTO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.cusEstFactorWeights != null) {
        oprot.writeFieldBegin(CUS_EST_FACTOR_WEIGHTS_FIELD_DESC);
        struct.cusEstFactorWeights.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.cusEstQuota != null) {
        oprot.writeFieldBegin(CUS_EST_QUOTA_FIELD_DESC);
        struct.cusEstQuota.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.quotas != null) {
        oprot.writeFieldBegin(QUOTAS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.quotas.size()));
          for (CusEstQuota _iter62 : struct.quotas)
          {
            _iter62.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.cusEstOption != null) {
        oprot.writeFieldBegin(CUS_EST_OPTION_FIELD_DESC);
        struct.cusEstOption.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.options != null) {
        oprot.writeFieldBegin(OPTIONS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.options.size()));
          for (CusEstOption _iter63 : struct.options)
          {
            _iter63.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.cusEstTemplate != null) {
        oprot.writeFieldBegin(CUS_EST_TEMPLATE_FIELD_DESC);
        struct.cusEstTemplate.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CusEstDTOTupleSchemeFactory implements SchemeFactory {
    public CusEstDTOTupleScheme getScheme() {
      return new CusEstDTOTupleScheme();
    }
  }

  private static class CusEstDTOTupleScheme extends TupleScheme<CusEstDTO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CusEstDTO struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetCusEstFactorWeights()) {
        optionals.set(0);
      }
      if (struct.isSetCusEstQuota()) {
        optionals.set(1);
      }
      if (struct.isSetQuotas()) {
        optionals.set(2);
      }
      if (struct.isSetCusEstOption()) {
        optionals.set(3);
      }
      if (struct.isSetOptions()) {
        optionals.set(4);
      }
      if (struct.isSetCusEstTemplate()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetCusEstFactorWeights()) {
        struct.cusEstFactorWeights.write(oprot);
      }
      if (struct.isSetCusEstQuota()) {
        struct.cusEstQuota.write(oprot);
      }
      if (struct.isSetQuotas()) {
        {
          oprot.writeI32(struct.quotas.size());
          for (CusEstQuota _iter64 : struct.quotas)
          {
            _iter64.write(oprot);
          }
        }
      }
      if (struct.isSetCusEstOption()) {
        struct.cusEstOption.write(oprot);
      }
      if (struct.isSetOptions()) {
        {
          oprot.writeI32(struct.options.size());
          for (CusEstOption _iter65 : struct.options)
          {
            _iter65.write(oprot);
          }
        }
      }
      if (struct.isSetCusEstTemplate()) {
        struct.cusEstTemplate.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CusEstDTO struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.cusEstFactorWeights = new CusEstFactorWeights();
        struct.cusEstFactorWeights.read(iprot);
        struct.setCusEstFactorWeightsIsSet(true);
      }
      if (incoming.get(1)) {
        struct.cusEstQuota = new CusEstQuota();
        struct.cusEstQuota.read(iprot);
        struct.setCusEstQuotaIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list66 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.quotas = new ArrayList<CusEstQuota>(_list66.size);
          CusEstQuota _elem67;
          for (int _i68 = 0; _i68 < _list66.size; ++_i68)
          {
            _elem67 = new CusEstQuota();
            _elem67.read(iprot);
            struct.quotas.add(_elem67);
          }
        }
        struct.setQuotasIsSet(true);
      }
      if (incoming.get(3)) {
        struct.cusEstOption = new CusEstOption();
        struct.cusEstOption.read(iprot);
        struct.setCusEstOptionIsSet(true);
      }
      if (incoming.get(4)) {
        {
          org.apache.thrift.protocol.TList _list69 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.options = new ArrayList<CusEstOption>(_list69.size);
          CusEstOption _elem70;
          for (int _i71 = 0; _i71 < _list69.size; ++_i71)
          {
            _elem70 = new CusEstOption();
            _elem70.read(iprot);
            struct.options.add(_elem70);
          }
        }
        struct.setOptionsIsSet(true);
      }
      if (incoming.get(5)) {
        struct.cusEstTemplate = new CusEstTemplate();
        struct.cusEstTemplate.read(iprot);
        struct.setCusEstTemplateIsSet(true);
      }
    }
  }

}
