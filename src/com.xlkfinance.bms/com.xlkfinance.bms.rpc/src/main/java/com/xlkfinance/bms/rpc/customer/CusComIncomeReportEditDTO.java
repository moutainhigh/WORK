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
/**
 * 编辑财务-利润报表时候使用
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2017-7-25")
public class CusComIncomeReportEditDTO implements org.apache.thrift.TBase<CusComIncomeReportEditDTO, CusComIncomeReportEditDTO._Fields>, java.io.Serializable, Cloneable, Comparable<CusComIncomeReportEditDTO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CusComIncomeReportEditDTO");

  private static final org.apache.thrift.protocol.TField IR_PID_FIELD_DESC = new org.apache.thrift.protocol.TField("irPid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField ITEM_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("itemName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField ITEM_PREFIX_FIELD_DESC = new org.apache.thrift.protocol.TField("itemPrefix", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField THIS_MONTH_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("thisMonthVal", org.apache.thrift.protocol.TType.DOUBLE, (short)4);
  private static final org.apache.thrift.protocol.TField THIS_YEAR_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("thisYearVal", org.apache.thrift.protocol.TType.DOUBLE, (short)5);
  private static final org.apache.thrift.protocol.TField IR_REPORT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("irReportId", org.apache.thrift.protocol.TType.I32, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CusComIncomeReportEditDTOStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CusComIncomeReportEditDTOTupleSchemeFactory());
  }

  public int irPid; // required
  public String itemName; // required
  public String itemPrefix; // required
  public double thisMonthVal; // required
  public double thisYearVal; // required
  public int irReportId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    IR_PID((short)1, "irPid"),
    ITEM_NAME((short)2, "itemName"),
    ITEM_PREFIX((short)3, "itemPrefix"),
    THIS_MONTH_VAL((short)4, "thisMonthVal"),
    THIS_YEAR_VAL((short)5, "thisYearVal"),
    IR_REPORT_ID((short)6, "irReportId");

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
        case 1: // IR_PID
          return IR_PID;
        case 2: // ITEM_NAME
          return ITEM_NAME;
        case 3: // ITEM_PREFIX
          return ITEM_PREFIX;
        case 4: // THIS_MONTH_VAL
          return THIS_MONTH_VAL;
        case 5: // THIS_YEAR_VAL
          return THIS_YEAR_VAL;
        case 6: // IR_REPORT_ID
          return IR_REPORT_ID;
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
  private static final int __IRPID_ISSET_ID = 0;
  private static final int __THISMONTHVAL_ISSET_ID = 1;
  private static final int __THISYEARVAL_ISSET_ID = 2;
  private static final int __IRREPORTID_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.IR_PID, new org.apache.thrift.meta_data.FieldMetaData("irPid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ITEM_NAME, new org.apache.thrift.meta_data.FieldMetaData("itemName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ITEM_PREFIX, new org.apache.thrift.meta_data.FieldMetaData("itemPrefix", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.THIS_MONTH_VAL, new org.apache.thrift.meta_data.FieldMetaData("thisMonthVal", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.THIS_YEAR_VAL, new org.apache.thrift.meta_data.FieldMetaData("thisYearVal", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.IR_REPORT_ID, new org.apache.thrift.meta_data.FieldMetaData("irReportId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CusComIncomeReportEditDTO.class, metaDataMap);
  }

  public CusComIncomeReportEditDTO() {
  }

  public CusComIncomeReportEditDTO(
    int irPid,
    String itemName,
    String itemPrefix,
    double thisMonthVal,
    double thisYearVal,
    int irReportId)
  {
    this();
    this.irPid = irPid;
    setIrPidIsSet(true);
    this.itemName = itemName;
    this.itemPrefix = itemPrefix;
    this.thisMonthVal = thisMonthVal;
    setThisMonthValIsSet(true);
    this.thisYearVal = thisYearVal;
    setThisYearValIsSet(true);
    this.irReportId = irReportId;
    setIrReportIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CusComIncomeReportEditDTO(CusComIncomeReportEditDTO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.irPid = other.irPid;
    if (other.isSetItemName()) {
      this.itemName = other.itemName;
    }
    if (other.isSetItemPrefix()) {
      this.itemPrefix = other.itemPrefix;
    }
    this.thisMonthVal = other.thisMonthVal;
    this.thisYearVal = other.thisYearVal;
    this.irReportId = other.irReportId;
  }

  public CusComIncomeReportEditDTO deepCopy() {
    return new CusComIncomeReportEditDTO(this);
  }

  @Override
  public void clear() {
    setIrPidIsSet(false);
    this.irPid = 0;
    this.itemName = null;
    this.itemPrefix = null;
    setThisMonthValIsSet(false);
    this.thisMonthVal = 0.0;
    setThisYearValIsSet(false);
    this.thisYearVal = 0.0;
    setIrReportIdIsSet(false);
    this.irReportId = 0;
  }

  public int getIrPid() {
    return this.irPid;
  }

  public CusComIncomeReportEditDTO setIrPid(int irPid) {
    this.irPid = irPid;
    setIrPidIsSet(true);
    return this;
  }

  public void unsetIrPid() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __IRPID_ISSET_ID);
  }

  /** Returns true if field irPid is set (has been assigned a value) and false otherwise */
  public boolean isSetIrPid() {
    return EncodingUtils.testBit(__isset_bitfield, __IRPID_ISSET_ID);
  }

  public void setIrPidIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __IRPID_ISSET_ID, value);
  }

  public String getItemName() {
    return this.itemName;
  }

  public CusComIncomeReportEditDTO setItemName(String itemName) {
    this.itemName = itemName;
    return this;
  }

  public void unsetItemName() {
    this.itemName = null;
  }

  /** Returns true if field itemName is set (has been assigned a value) and false otherwise */
  public boolean isSetItemName() {
    return this.itemName != null;
  }

  public void setItemNameIsSet(boolean value) {
    if (!value) {
      this.itemName = null;
    }
  }

  public String getItemPrefix() {
    return this.itemPrefix;
  }

  public CusComIncomeReportEditDTO setItemPrefix(String itemPrefix) {
    this.itemPrefix = itemPrefix;
    return this;
  }

  public void unsetItemPrefix() {
    this.itemPrefix = null;
  }

  /** Returns true if field itemPrefix is set (has been assigned a value) and false otherwise */
  public boolean isSetItemPrefix() {
    return this.itemPrefix != null;
  }

  public void setItemPrefixIsSet(boolean value) {
    if (!value) {
      this.itemPrefix = null;
    }
  }

  public double getThisMonthVal() {
    return this.thisMonthVal;
  }

  public CusComIncomeReportEditDTO setThisMonthVal(double thisMonthVal) {
    this.thisMonthVal = thisMonthVal;
    setThisMonthValIsSet(true);
    return this;
  }

  public void unsetThisMonthVal() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __THISMONTHVAL_ISSET_ID);
  }

  /** Returns true if field thisMonthVal is set (has been assigned a value) and false otherwise */
  public boolean isSetThisMonthVal() {
    return EncodingUtils.testBit(__isset_bitfield, __THISMONTHVAL_ISSET_ID);
  }

  public void setThisMonthValIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __THISMONTHVAL_ISSET_ID, value);
  }

  public double getThisYearVal() {
    return this.thisYearVal;
  }

  public CusComIncomeReportEditDTO setThisYearVal(double thisYearVal) {
    this.thisYearVal = thisYearVal;
    setThisYearValIsSet(true);
    return this;
  }

  public void unsetThisYearVal() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __THISYEARVAL_ISSET_ID);
  }

  /** Returns true if field thisYearVal is set (has been assigned a value) and false otherwise */
  public boolean isSetThisYearVal() {
    return EncodingUtils.testBit(__isset_bitfield, __THISYEARVAL_ISSET_ID);
  }

  public void setThisYearValIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __THISYEARVAL_ISSET_ID, value);
  }

  public int getIrReportId() {
    return this.irReportId;
  }

  public CusComIncomeReportEditDTO setIrReportId(int irReportId) {
    this.irReportId = irReportId;
    setIrReportIdIsSet(true);
    return this;
  }

  public void unsetIrReportId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __IRREPORTID_ISSET_ID);
  }

  /** Returns true if field irReportId is set (has been assigned a value) and false otherwise */
  public boolean isSetIrReportId() {
    return EncodingUtils.testBit(__isset_bitfield, __IRREPORTID_ISSET_ID);
  }

  public void setIrReportIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __IRREPORTID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case IR_PID:
      if (value == null) {
        unsetIrPid();
      } else {
        setIrPid((Integer)value);
      }
      break;

    case ITEM_NAME:
      if (value == null) {
        unsetItemName();
      } else {
        setItemName((String)value);
      }
      break;

    case ITEM_PREFIX:
      if (value == null) {
        unsetItemPrefix();
      } else {
        setItemPrefix((String)value);
      }
      break;

    case THIS_MONTH_VAL:
      if (value == null) {
        unsetThisMonthVal();
      } else {
        setThisMonthVal((Double)value);
      }
      break;

    case THIS_YEAR_VAL:
      if (value == null) {
        unsetThisYearVal();
      } else {
        setThisYearVal((Double)value);
      }
      break;

    case IR_REPORT_ID:
      if (value == null) {
        unsetIrReportId();
      } else {
        setIrReportId((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case IR_PID:
      return Integer.valueOf(getIrPid());

    case ITEM_NAME:
      return getItemName();

    case ITEM_PREFIX:
      return getItemPrefix();

    case THIS_MONTH_VAL:
      return Double.valueOf(getThisMonthVal());

    case THIS_YEAR_VAL:
      return Double.valueOf(getThisYearVal());

    case IR_REPORT_ID:
      return Integer.valueOf(getIrReportId());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case IR_PID:
      return isSetIrPid();
    case ITEM_NAME:
      return isSetItemName();
    case ITEM_PREFIX:
      return isSetItemPrefix();
    case THIS_MONTH_VAL:
      return isSetThisMonthVal();
    case THIS_YEAR_VAL:
      return isSetThisYearVal();
    case IR_REPORT_ID:
      return isSetIrReportId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CusComIncomeReportEditDTO)
      return this.equals((CusComIncomeReportEditDTO)that);
    return false;
  }

  public boolean equals(CusComIncomeReportEditDTO that) {
    if (that == null)
      return false;

    boolean this_present_irPid = true;
    boolean that_present_irPid = true;
    if (this_present_irPid || that_present_irPid) {
      if (!(this_present_irPid && that_present_irPid))
        return false;
      if (this.irPid != that.irPid)
        return false;
    }

    boolean this_present_itemName = true && this.isSetItemName();
    boolean that_present_itemName = true && that.isSetItemName();
    if (this_present_itemName || that_present_itemName) {
      if (!(this_present_itemName && that_present_itemName))
        return false;
      if (!this.itemName.equals(that.itemName))
        return false;
    }

    boolean this_present_itemPrefix = true && this.isSetItemPrefix();
    boolean that_present_itemPrefix = true && that.isSetItemPrefix();
    if (this_present_itemPrefix || that_present_itemPrefix) {
      if (!(this_present_itemPrefix && that_present_itemPrefix))
        return false;
      if (!this.itemPrefix.equals(that.itemPrefix))
        return false;
    }

    boolean this_present_thisMonthVal = true;
    boolean that_present_thisMonthVal = true;
    if (this_present_thisMonthVal || that_present_thisMonthVal) {
      if (!(this_present_thisMonthVal && that_present_thisMonthVal))
        return false;
      if (this.thisMonthVal != that.thisMonthVal)
        return false;
    }

    boolean this_present_thisYearVal = true;
    boolean that_present_thisYearVal = true;
    if (this_present_thisYearVal || that_present_thisYearVal) {
      if (!(this_present_thisYearVal && that_present_thisYearVal))
        return false;
      if (this.thisYearVal != that.thisYearVal)
        return false;
    }

    boolean this_present_irReportId = true;
    boolean that_present_irReportId = true;
    if (this_present_irReportId || that_present_irReportId) {
      if (!(this_present_irReportId && that_present_irReportId))
        return false;
      if (this.irReportId != that.irReportId)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_irPid = true;
    list.add(present_irPid);
    if (present_irPid)
      list.add(irPid);

    boolean present_itemName = true && (isSetItemName());
    list.add(present_itemName);
    if (present_itemName)
      list.add(itemName);

    boolean present_itemPrefix = true && (isSetItemPrefix());
    list.add(present_itemPrefix);
    if (present_itemPrefix)
      list.add(itemPrefix);

    boolean present_thisMonthVal = true;
    list.add(present_thisMonthVal);
    if (present_thisMonthVal)
      list.add(thisMonthVal);

    boolean present_thisYearVal = true;
    list.add(present_thisYearVal);
    if (present_thisYearVal)
      list.add(thisYearVal);

    boolean present_irReportId = true;
    list.add(present_irReportId);
    if (present_irReportId)
      list.add(irReportId);

    return list.hashCode();
  }

  @Override
  public int compareTo(CusComIncomeReportEditDTO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetIrPid()).compareTo(other.isSetIrPid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIrPid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.irPid, other.irPid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetItemName()).compareTo(other.isSetItemName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetItemName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.itemName, other.itemName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetItemPrefix()).compareTo(other.isSetItemPrefix());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetItemPrefix()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.itemPrefix, other.itemPrefix);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetThisMonthVal()).compareTo(other.isSetThisMonthVal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetThisMonthVal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.thisMonthVal, other.thisMonthVal);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetThisYearVal()).compareTo(other.isSetThisYearVal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetThisYearVal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.thisYearVal, other.thisYearVal);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIrReportId()).compareTo(other.isSetIrReportId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIrReportId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.irReportId, other.irReportId);
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
    StringBuilder sb = new StringBuilder("CusComIncomeReportEditDTO(");
    boolean first = true;

    sb.append("irPid:");
    sb.append(this.irPid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("itemName:");
    if (this.itemName == null) {
      sb.append("null");
    } else {
      sb.append(this.itemName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("itemPrefix:");
    if (this.itemPrefix == null) {
      sb.append("null");
    } else {
      sb.append(this.itemPrefix);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("thisMonthVal:");
    sb.append(this.thisMonthVal);
    first = false;
    if (!first) sb.append(", ");
    sb.append("thisYearVal:");
    sb.append(this.thisYearVal);
    first = false;
    if (!first) sb.append(", ");
    sb.append("irReportId:");
    sb.append(this.irReportId);
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

  private static class CusComIncomeReportEditDTOStandardSchemeFactory implements SchemeFactory {
    public CusComIncomeReportEditDTOStandardScheme getScheme() {
      return new CusComIncomeReportEditDTOStandardScheme();
    }
  }

  private static class CusComIncomeReportEditDTOStandardScheme extends StandardScheme<CusComIncomeReportEditDTO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CusComIncomeReportEditDTO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // IR_PID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.irPid = iprot.readI32();
              struct.setIrPidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ITEM_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.itemName = iprot.readString();
              struct.setItemNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ITEM_PREFIX
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.itemPrefix = iprot.readString();
              struct.setItemPrefixIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // THIS_MONTH_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.thisMonthVal = iprot.readDouble();
              struct.setThisMonthValIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // THIS_YEAR_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.thisYearVal = iprot.readDouble();
              struct.setThisYearValIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // IR_REPORT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.irReportId = iprot.readI32();
              struct.setIrReportIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CusComIncomeReportEditDTO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(IR_PID_FIELD_DESC);
      oprot.writeI32(struct.irPid);
      oprot.writeFieldEnd();
      if (struct.itemName != null) {
        oprot.writeFieldBegin(ITEM_NAME_FIELD_DESC);
        oprot.writeString(struct.itemName);
        oprot.writeFieldEnd();
      }
      if (struct.itemPrefix != null) {
        oprot.writeFieldBegin(ITEM_PREFIX_FIELD_DESC);
        oprot.writeString(struct.itemPrefix);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(THIS_MONTH_VAL_FIELD_DESC);
      oprot.writeDouble(struct.thisMonthVal);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(THIS_YEAR_VAL_FIELD_DESC);
      oprot.writeDouble(struct.thisYearVal);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(IR_REPORT_ID_FIELD_DESC);
      oprot.writeI32(struct.irReportId);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CusComIncomeReportEditDTOTupleSchemeFactory implements SchemeFactory {
    public CusComIncomeReportEditDTOTupleScheme getScheme() {
      return new CusComIncomeReportEditDTOTupleScheme();
    }
  }

  private static class CusComIncomeReportEditDTOTupleScheme extends TupleScheme<CusComIncomeReportEditDTO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CusComIncomeReportEditDTO struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetIrPid()) {
        optionals.set(0);
      }
      if (struct.isSetItemName()) {
        optionals.set(1);
      }
      if (struct.isSetItemPrefix()) {
        optionals.set(2);
      }
      if (struct.isSetThisMonthVal()) {
        optionals.set(3);
      }
      if (struct.isSetThisYearVal()) {
        optionals.set(4);
      }
      if (struct.isSetIrReportId()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetIrPid()) {
        oprot.writeI32(struct.irPid);
      }
      if (struct.isSetItemName()) {
        oprot.writeString(struct.itemName);
      }
      if (struct.isSetItemPrefix()) {
        oprot.writeString(struct.itemPrefix);
      }
      if (struct.isSetThisMonthVal()) {
        oprot.writeDouble(struct.thisMonthVal);
      }
      if (struct.isSetThisYearVal()) {
        oprot.writeDouble(struct.thisYearVal);
      }
      if (struct.isSetIrReportId()) {
        oprot.writeI32(struct.irReportId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CusComIncomeReportEditDTO struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.irPid = iprot.readI32();
        struct.setIrPidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.itemName = iprot.readString();
        struct.setItemNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.itemPrefix = iprot.readString();
        struct.setItemPrefixIsSet(true);
      }
      if (incoming.get(3)) {
        struct.thisMonthVal = iprot.readDouble();
        struct.setThisMonthValIsSet(true);
      }
      if (incoming.get(4)) {
        struct.thisYearVal = iprot.readDouble();
        struct.setThisYearValIsSet(true);
      }
      if (incoming.get(5)) {
        struct.irReportId = iprot.readI32();
        struct.setIrReportIdIsSet(true);
      }
    }
  }

}

