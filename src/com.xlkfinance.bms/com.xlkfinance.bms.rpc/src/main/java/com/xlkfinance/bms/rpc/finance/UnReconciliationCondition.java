/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xlkfinance.bms.rpc.finance;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2017-3-7")
public class UnReconciliationCondition implements org.apache.thrift.TBase<UnReconciliationCondition, UnReconciliationCondition._Fields>, java.io.Serializable, Cloneable, Comparable<UnReconciliationCondition> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UnReconciliationCondition");

  private static final org.apache.thrift.protocol.TField PROJECT_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("projectName", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField PROJECT_NUMBER_FIELD_DESC = new org.apache.thrift.protocol.TField("projectNumber", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CUS_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("cusName", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField CUS_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("cusType", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField ECO_TRADE_FIELD_DESC = new org.apache.thrift.protocol.TField("ecoTrade", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField RECEIVE_START_DT_FIELD_DESC = new org.apache.thrift.protocol.TField("receiveStartDt", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField RECEIVE_END_DT_FIELD_DESC = new org.apache.thrift.protocol.TField("receiveEndDt", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField ROWS_FIELD_DESC = new org.apache.thrift.protocol.TField("rows", org.apache.thrift.protocol.TType.I32, (short)8);
  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.I32, (short)9);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new UnReconciliationConditionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new UnReconciliationConditionTupleSchemeFactory());
  }

  public String projectName; // required
  public String projectNumber; // required
  public String cusName; // required
  public int cusType; // required
  public int ecoTrade; // required
  public String receiveStartDt; // required
  public String receiveEndDt; // required
  public int rows; // required
  public int page; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PROJECT_NAME((short)1, "projectName"),
    PROJECT_NUMBER((short)2, "projectNumber"),
    CUS_NAME((short)3, "cusName"),
    CUS_TYPE((short)4, "cusType"),
    ECO_TRADE((short)5, "ecoTrade"),
    RECEIVE_START_DT((short)6, "receiveStartDt"),
    RECEIVE_END_DT((short)7, "receiveEndDt"),
    ROWS((short)8, "rows"),
    PAGE((short)9, "page");

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
        case 1: // PROJECT_NAME
          return PROJECT_NAME;
        case 2: // PROJECT_NUMBER
          return PROJECT_NUMBER;
        case 3: // CUS_NAME
          return CUS_NAME;
        case 4: // CUS_TYPE
          return CUS_TYPE;
        case 5: // ECO_TRADE
          return ECO_TRADE;
        case 6: // RECEIVE_START_DT
          return RECEIVE_START_DT;
        case 7: // RECEIVE_END_DT
          return RECEIVE_END_DT;
        case 8: // ROWS
          return ROWS;
        case 9: // PAGE
          return PAGE;
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
  private static final int __CUSTYPE_ISSET_ID = 0;
  private static final int __ECOTRADE_ISSET_ID = 1;
  private static final int __ROWS_ISSET_ID = 2;
  private static final int __PAGE_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PROJECT_NAME, new org.apache.thrift.meta_data.FieldMetaData("projectName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PROJECT_NUMBER, new org.apache.thrift.meta_data.FieldMetaData("projectNumber", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CUS_NAME, new org.apache.thrift.meta_data.FieldMetaData("cusName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CUS_TYPE, new org.apache.thrift.meta_data.FieldMetaData("cusType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ECO_TRADE, new org.apache.thrift.meta_data.FieldMetaData("ecoTrade", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.RECEIVE_START_DT, new org.apache.thrift.meta_data.FieldMetaData("receiveStartDt", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.RECEIVE_END_DT, new org.apache.thrift.meta_data.FieldMetaData("receiveEndDt", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ROWS, new org.apache.thrift.meta_data.FieldMetaData("rows", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PAGE, new org.apache.thrift.meta_data.FieldMetaData("page", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UnReconciliationCondition.class, metaDataMap);
  }

  public UnReconciliationCondition() {
  }

  public UnReconciliationCondition(
    String projectName,
    String projectNumber,
    String cusName,
    int cusType,
    int ecoTrade,
    String receiveStartDt,
    String receiveEndDt,
    int rows,
    int page)
  {
    this();
    this.projectName = projectName;
    this.projectNumber = projectNumber;
    this.cusName = cusName;
    this.cusType = cusType;
    setCusTypeIsSet(true);
    this.ecoTrade = ecoTrade;
    setEcoTradeIsSet(true);
    this.receiveStartDt = receiveStartDt;
    this.receiveEndDt = receiveEndDt;
    this.rows = rows;
    setRowsIsSet(true);
    this.page = page;
    setPageIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public UnReconciliationCondition(UnReconciliationCondition other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetProjectName()) {
      this.projectName = other.projectName;
    }
    if (other.isSetProjectNumber()) {
      this.projectNumber = other.projectNumber;
    }
    if (other.isSetCusName()) {
      this.cusName = other.cusName;
    }
    this.cusType = other.cusType;
    this.ecoTrade = other.ecoTrade;
    if (other.isSetReceiveStartDt()) {
      this.receiveStartDt = other.receiveStartDt;
    }
    if (other.isSetReceiveEndDt()) {
      this.receiveEndDt = other.receiveEndDt;
    }
    this.rows = other.rows;
    this.page = other.page;
  }

  public UnReconciliationCondition deepCopy() {
    return new UnReconciliationCondition(this);
  }

  @Override
  public void clear() {
    this.projectName = null;
    this.projectNumber = null;
    this.cusName = null;
    setCusTypeIsSet(false);
    this.cusType = 0;
    setEcoTradeIsSet(false);
    this.ecoTrade = 0;
    this.receiveStartDt = null;
    this.receiveEndDt = null;
    setRowsIsSet(false);
    this.rows = 0;
    setPageIsSet(false);
    this.page = 0;
  }

  public String getProjectName() {
    return this.projectName;
  }

  public UnReconciliationCondition setProjectName(String projectName) {
    this.projectName = projectName;
    return this;
  }

  public void unsetProjectName() {
    this.projectName = null;
  }

  /** Returns true if field projectName is set (has been assigned a value) and false otherwise */
  public boolean isSetProjectName() {
    return this.projectName != null;
  }

  public void setProjectNameIsSet(boolean value) {
    if (!value) {
      this.projectName = null;
    }
  }

  public String getProjectNumber() {
    return this.projectNumber;
  }

  public UnReconciliationCondition setProjectNumber(String projectNumber) {
    this.projectNumber = projectNumber;
    return this;
  }

  public void unsetProjectNumber() {
    this.projectNumber = null;
  }

  /** Returns true if field projectNumber is set (has been assigned a value) and false otherwise */
  public boolean isSetProjectNumber() {
    return this.projectNumber != null;
  }

  public void setProjectNumberIsSet(boolean value) {
    if (!value) {
      this.projectNumber = null;
    }
  }

  public String getCusName() {
    return this.cusName;
  }

  public UnReconciliationCondition setCusName(String cusName) {
    this.cusName = cusName;
    return this;
  }

  public void unsetCusName() {
    this.cusName = null;
  }

  /** Returns true if field cusName is set (has been assigned a value) and false otherwise */
  public boolean isSetCusName() {
    return this.cusName != null;
  }

  public void setCusNameIsSet(boolean value) {
    if (!value) {
      this.cusName = null;
    }
  }

  public int getCusType() {
    return this.cusType;
  }

  public UnReconciliationCondition setCusType(int cusType) {
    this.cusType = cusType;
    setCusTypeIsSet(true);
    return this;
  }

  public void unsetCusType() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CUSTYPE_ISSET_ID);
  }

  /** Returns true if field cusType is set (has been assigned a value) and false otherwise */
  public boolean isSetCusType() {
    return EncodingUtils.testBit(__isset_bitfield, __CUSTYPE_ISSET_ID);
  }

  public void setCusTypeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CUSTYPE_ISSET_ID, value);
  }

  public int getEcoTrade() {
    return this.ecoTrade;
  }

  public UnReconciliationCondition setEcoTrade(int ecoTrade) {
    this.ecoTrade = ecoTrade;
    setEcoTradeIsSet(true);
    return this;
  }

  public void unsetEcoTrade() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ECOTRADE_ISSET_ID);
  }

  /** Returns true if field ecoTrade is set (has been assigned a value) and false otherwise */
  public boolean isSetEcoTrade() {
    return EncodingUtils.testBit(__isset_bitfield, __ECOTRADE_ISSET_ID);
  }

  public void setEcoTradeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ECOTRADE_ISSET_ID, value);
  }

  public String getReceiveStartDt() {
    return this.receiveStartDt;
  }

  public UnReconciliationCondition setReceiveStartDt(String receiveStartDt) {
    this.receiveStartDt = receiveStartDt;
    return this;
  }

  public void unsetReceiveStartDt() {
    this.receiveStartDt = null;
  }

  /** Returns true if field receiveStartDt is set (has been assigned a value) and false otherwise */
  public boolean isSetReceiveStartDt() {
    return this.receiveStartDt != null;
  }

  public void setReceiveStartDtIsSet(boolean value) {
    if (!value) {
      this.receiveStartDt = null;
    }
  }

  public String getReceiveEndDt() {
    return this.receiveEndDt;
  }

  public UnReconciliationCondition setReceiveEndDt(String receiveEndDt) {
    this.receiveEndDt = receiveEndDt;
    return this;
  }

  public void unsetReceiveEndDt() {
    this.receiveEndDt = null;
  }

  /** Returns true if field receiveEndDt is set (has been assigned a value) and false otherwise */
  public boolean isSetReceiveEndDt() {
    return this.receiveEndDt != null;
  }

  public void setReceiveEndDtIsSet(boolean value) {
    if (!value) {
      this.receiveEndDt = null;
    }
  }

  public int getRows() {
    return this.rows;
  }

  public UnReconciliationCondition setRows(int rows) {
    this.rows = rows;
    setRowsIsSet(true);
    return this;
  }

  public void unsetRows() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ROWS_ISSET_ID);
  }

  /** Returns true if field rows is set (has been assigned a value) and false otherwise */
  public boolean isSetRows() {
    return EncodingUtils.testBit(__isset_bitfield, __ROWS_ISSET_ID);
  }

  public void setRowsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ROWS_ISSET_ID, value);
  }

  public int getPage() {
    return this.page;
  }

  public UnReconciliationCondition setPage(int page) {
    this.page = page;
    setPageIsSet(true);
    return this;
  }

  public void unsetPage() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PAGE_ISSET_ID);
  }

  /** Returns true if field page is set (has been assigned a value) and false otherwise */
  public boolean isSetPage() {
    return EncodingUtils.testBit(__isset_bitfield, __PAGE_ISSET_ID);
  }

  public void setPageIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PAGE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PROJECT_NAME:
      if (value == null) {
        unsetProjectName();
      } else {
        setProjectName((String)value);
      }
      break;

    case PROJECT_NUMBER:
      if (value == null) {
        unsetProjectNumber();
      } else {
        setProjectNumber((String)value);
      }
      break;

    case CUS_NAME:
      if (value == null) {
        unsetCusName();
      } else {
        setCusName((String)value);
      }
      break;

    case CUS_TYPE:
      if (value == null) {
        unsetCusType();
      } else {
        setCusType((Integer)value);
      }
      break;

    case ECO_TRADE:
      if (value == null) {
        unsetEcoTrade();
      } else {
        setEcoTrade((Integer)value);
      }
      break;

    case RECEIVE_START_DT:
      if (value == null) {
        unsetReceiveStartDt();
      } else {
        setReceiveStartDt((String)value);
      }
      break;

    case RECEIVE_END_DT:
      if (value == null) {
        unsetReceiveEndDt();
      } else {
        setReceiveEndDt((String)value);
      }
      break;

    case ROWS:
      if (value == null) {
        unsetRows();
      } else {
        setRows((Integer)value);
      }
      break;

    case PAGE:
      if (value == null) {
        unsetPage();
      } else {
        setPage((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PROJECT_NAME:
      return getProjectName();

    case PROJECT_NUMBER:
      return getProjectNumber();

    case CUS_NAME:
      return getCusName();

    case CUS_TYPE:
      return Integer.valueOf(getCusType());

    case ECO_TRADE:
      return Integer.valueOf(getEcoTrade());

    case RECEIVE_START_DT:
      return getReceiveStartDt();

    case RECEIVE_END_DT:
      return getReceiveEndDt();

    case ROWS:
      return Integer.valueOf(getRows());

    case PAGE:
      return Integer.valueOf(getPage());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PROJECT_NAME:
      return isSetProjectName();
    case PROJECT_NUMBER:
      return isSetProjectNumber();
    case CUS_NAME:
      return isSetCusName();
    case CUS_TYPE:
      return isSetCusType();
    case ECO_TRADE:
      return isSetEcoTrade();
    case RECEIVE_START_DT:
      return isSetReceiveStartDt();
    case RECEIVE_END_DT:
      return isSetReceiveEndDt();
    case ROWS:
      return isSetRows();
    case PAGE:
      return isSetPage();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof UnReconciliationCondition)
      return this.equals((UnReconciliationCondition)that);
    return false;
  }

  public boolean equals(UnReconciliationCondition that) {
    if (that == null)
      return false;

    boolean this_present_projectName = true && this.isSetProjectName();
    boolean that_present_projectName = true && that.isSetProjectName();
    if (this_present_projectName || that_present_projectName) {
      if (!(this_present_projectName && that_present_projectName))
        return false;
      if (!this.projectName.equals(that.projectName))
        return false;
    }

    boolean this_present_projectNumber = true && this.isSetProjectNumber();
    boolean that_present_projectNumber = true && that.isSetProjectNumber();
    if (this_present_projectNumber || that_present_projectNumber) {
      if (!(this_present_projectNumber && that_present_projectNumber))
        return false;
      if (!this.projectNumber.equals(that.projectNumber))
        return false;
    }

    boolean this_present_cusName = true && this.isSetCusName();
    boolean that_present_cusName = true && that.isSetCusName();
    if (this_present_cusName || that_present_cusName) {
      if (!(this_present_cusName && that_present_cusName))
        return false;
      if (!this.cusName.equals(that.cusName))
        return false;
    }

    boolean this_present_cusType = true;
    boolean that_present_cusType = true;
    if (this_present_cusType || that_present_cusType) {
      if (!(this_present_cusType && that_present_cusType))
        return false;
      if (this.cusType != that.cusType)
        return false;
    }

    boolean this_present_ecoTrade = true;
    boolean that_present_ecoTrade = true;
    if (this_present_ecoTrade || that_present_ecoTrade) {
      if (!(this_present_ecoTrade && that_present_ecoTrade))
        return false;
      if (this.ecoTrade != that.ecoTrade)
        return false;
    }

    boolean this_present_receiveStartDt = true && this.isSetReceiveStartDt();
    boolean that_present_receiveStartDt = true && that.isSetReceiveStartDt();
    if (this_present_receiveStartDt || that_present_receiveStartDt) {
      if (!(this_present_receiveStartDt && that_present_receiveStartDt))
        return false;
      if (!this.receiveStartDt.equals(that.receiveStartDt))
        return false;
    }

    boolean this_present_receiveEndDt = true && this.isSetReceiveEndDt();
    boolean that_present_receiveEndDt = true && that.isSetReceiveEndDt();
    if (this_present_receiveEndDt || that_present_receiveEndDt) {
      if (!(this_present_receiveEndDt && that_present_receiveEndDt))
        return false;
      if (!this.receiveEndDt.equals(that.receiveEndDt))
        return false;
    }

    boolean this_present_rows = true;
    boolean that_present_rows = true;
    if (this_present_rows || that_present_rows) {
      if (!(this_present_rows && that_present_rows))
        return false;
      if (this.rows != that.rows)
        return false;
    }

    boolean this_present_page = true;
    boolean that_present_page = true;
    if (this_present_page || that_present_page) {
      if (!(this_present_page && that_present_page))
        return false;
      if (this.page != that.page)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_projectName = true && (isSetProjectName());
    list.add(present_projectName);
    if (present_projectName)
      list.add(projectName);

    boolean present_projectNumber = true && (isSetProjectNumber());
    list.add(present_projectNumber);
    if (present_projectNumber)
      list.add(projectNumber);

    boolean present_cusName = true && (isSetCusName());
    list.add(present_cusName);
    if (present_cusName)
      list.add(cusName);

    boolean present_cusType = true;
    list.add(present_cusType);
    if (present_cusType)
      list.add(cusType);

    boolean present_ecoTrade = true;
    list.add(present_ecoTrade);
    if (present_ecoTrade)
      list.add(ecoTrade);

    boolean present_receiveStartDt = true && (isSetReceiveStartDt());
    list.add(present_receiveStartDt);
    if (present_receiveStartDt)
      list.add(receiveStartDt);

    boolean present_receiveEndDt = true && (isSetReceiveEndDt());
    list.add(present_receiveEndDt);
    if (present_receiveEndDt)
      list.add(receiveEndDt);

    boolean present_rows = true;
    list.add(present_rows);
    if (present_rows)
      list.add(rows);

    boolean present_page = true;
    list.add(present_page);
    if (present_page)
      list.add(page);

    return list.hashCode();
  }

  @Override
  public int compareTo(UnReconciliationCondition other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetProjectName()).compareTo(other.isSetProjectName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProjectName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.projectName, other.projectName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProjectNumber()).compareTo(other.isSetProjectNumber());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProjectNumber()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.projectNumber, other.projectNumber);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCusName()).compareTo(other.isSetCusName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCusName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cusName, other.cusName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCusType()).compareTo(other.isSetCusType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCusType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cusType, other.cusType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEcoTrade()).compareTo(other.isSetEcoTrade());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEcoTrade()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ecoTrade, other.ecoTrade);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReceiveStartDt()).compareTo(other.isSetReceiveStartDt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReceiveStartDt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.receiveStartDt, other.receiveStartDt);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReceiveEndDt()).compareTo(other.isSetReceiveEndDt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReceiveEndDt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.receiveEndDt, other.receiveEndDt);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRows()).compareTo(other.isSetRows());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRows()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.rows, other.rows);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPage()).compareTo(other.isSetPage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.page, other.page);
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
    StringBuilder sb = new StringBuilder("UnReconciliationCondition(");
    boolean first = true;

    sb.append("projectName:");
    if (this.projectName == null) {
      sb.append("null");
    } else {
      sb.append(this.projectName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("projectNumber:");
    if (this.projectNumber == null) {
      sb.append("null");
    } else {
      sb.append(this.projectNumber);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("cusName:");
    if (this.cusName == null) {
      sb.append("null");
    } else {
      sb.append(this.cusName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("cusType:");
    sb.append(this.cusType);
    first = false;
    if (!first) sb.append(", ");
    sb.append("ecoTrade:");
    sb.append(this.ecoTrade);
    first = false;
    if (!first) sb.append(", ");
    sb.append("receiveStartDt:");
    if (this.receiveStartDt == null) {
      sb.append("null");
    } else {
      sb.append(this.receiveStartDt);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("receiveEndDt:");
    if (this.receiveEndDt == null) {
      sb.append("null");
    } else {
      sb.append(this.receiveEndDt);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("rows:");
    sb.append(this.rows);
    first = false;
    if (!first) sb.append(", ");
    sb.append("page:");
    sb.append(this.page);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class UnReconciliationConditionStandardSchemeFactory implements SchemeFactory {
    public UnReconciliationConditionStandardScheme getScheme() {
      return new UnReconciliationConditionStandardScheme();
    }
  }

  private static class UnReconciliationConditionStandardScheme extends StandardScheme<UnReconciliationCondition> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, UnReconciliationCondition struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PROJECT_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.projectName = iprot.readString();
              struct.setProjectNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PROJECT_NUMBER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.projectNumber = iprot.readString();
              struct.setProjectNumberIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CUS_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.cusName = iprot.readString();
              struct.setCusNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CUS_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.cusType = iprot.readI32();
              struct.setCusTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // ECO_TRADE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.ecoTrade = iprot.readI32();
              struct.setEcoTradeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // RECEIVE_START_DT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.receiveStartDt = iprot.readString();
              struct.setReceiveStartDtIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // RECEIVE_END_DT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.receiveEndDt = iprot.readString();
              struct.setReceiveEndDtIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // ROWS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.rows = iprot.readI32();
              struct.setRowsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 9: // PAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.page = iprot.readI32();
              struct.setPageIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, UnReconciliationCondition struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.projectName != null) {
        oprot.writeFieldBegin(PROJECT_NAME_FIELD_DESC);
        oprot.writeString(struct.projectName);
        oprot.writeFieldEnd();
      }
      if (struct.projectNumber != null) {
        oprot.writeFieldBegin(PROJECT_NUMBER_FIELD_DESC);
        oprot.writeString(struct.projectNumber);
        oprot.writeFieldEnd();
      }
      if (struct.cusName != null) {
        oprot.writeFieldBegin(CUS_NAME_FIELD_DESC);
        oprot.writeString(struct.cusName);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(CUS_TYPE_FIELD_DESC);
      oprot.writeI32(struct.cusType);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ECO_TRADE_FIELD_DESC);
      oprot.writeI32(struct.ecoTrade);
      oprot.writeFieldEnd();
      if (struct.receiveStartDt != null) {
        oprot.writeFieldBegin(RECEIVE_START_DT_FIELD_DESC);
        oprot.writeString(struct.receiveStartDt);
        oprot.writeFieldEnd();
      }
      if (struct.receiveEndDt != null) {
        oprot.writeFieldBegin(RECEIVE_END_DT_FIELD_DESC);
        oprot.writeString(struct.receiveEndDt);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(ROWS_FIELD_DESC);
      oprot.writeI32(struct.rows);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PAGE_FIELD_DESC);
      oprot.writeI32(struct.page);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class UnReconciliationConditionTupleSchemeFactory implements SchemeFactory {
    public UnReconciliationConditionTupleScheme getScheme() {
      return new UnReconciliationConditionTupleScheme();
    }
  }

  private static class UnReconciliationConditionTupleScheme extends TupleScheme<UnReconciliationCondition> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, UnReconciliationCondition struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetProjectName()) {
        optionals.set(0);
      }
      if (struct.isSetProjectNumber()) {
        optionals.set(1);
      }
      if (struct.isSetCusName()) {
        optionals.set(2);
      }
      if (struct.isSetCusType()) {
        optionals.set(3);
      }
      if (struct.isSetEcoTrade()) {
        optionals.set(4);
      }
      if (struct.isSetReceiveStartDt()) {
        optionals.set(5);
      }
      if (struct.isSetReceiveEndDt()) {
        optionals.set(6);
      }
      if (struct.isSetRows()) {
        optionals.set(7);
      }
      if (struct.isSetPage()) {
        optionals.set(8);
      }
      oprot.writeBitSet(optionals, 9);
      if (struct.isSetProjectName()) {
        oprot.writeString(struct.projectName);
      }
      if (struct.isSetProjectNumber()) {
        oprot.writeString(struct.projectNumber);
      }
      if (struct.isSetCusName()) {
        oprot.writeString(struct.cusName);
      }
      if (struct.isSetCusType()) {
        oprot.writeI32(struct.cusType);
      }
      if (struct.isSetEcoTrade()) {
        oprot.writeI32(struct.ecoTrade);
      }
      if (struct.isSetReceiveStartDt()) {
        oprot.writeString(struct.receiveStartDt);
      }
      if (struct.isSetReceiveEndDt()) {
        oprot.writeString(struct.receiveEndDt);
      }
      if (struct.isSetRows()) {
        oprot.writeI32(struct.rows);
      }
      if (struct.isSetPage()) {
        oprot.writeI32(struct.page);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, UnReconciliationCondition struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(9);
      if (incoming.get(0)) {
        struct.projectName = iprot.readString();
        struct.setProjectNameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.projectNumber = iprot.readString();
        struct.setProjectNumberIsSet(true);
      }
      if (incoming.get(2)) {
        struct.cusName = iprot.readString();
        struct.setCusNameIsSet(true);
      }
      if (incoming.get(3)) {
        struct.cusType = iprot.readI32();
        struct.setCusTypeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.ecoTrade = iprot.readI32();
        struct.setEcoTradeIsSet(true);
      }
      if (incoming.get(5)) {
        struct.receiveStartDt = iprot.readString();
        struct.setReceiveStartDtIsSet(true);
      }
      if (incoming.get(6)) {
        struct.receiveEndDt = iprot.readString();
        struct.setReceiveEndDtIsSet(true);
      }
      if (incoming.get(7)) {
        struct.rows = iprot.readI32();
        struct.setRowsIsSet(true);
      }
      if (incoming.get(8)) {
        struct.page = iprot.readI32();
        struct.setPageIsSet(true);
      }
    }
  }

}

