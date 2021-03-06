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
public class CusPerCreditDef implements org.apache.thrift.TBase<CusPerCreditDef, CusPerCreditDef._Fields>, java.io.Serializable, Cloneable, Comparable<CusPerCreditDef> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CusPerCreditDef");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField CUS_PER_CREDIT_FIELD_DESC = new org.apache.thrift.protocol.TField("cusPerCredit", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField DAY_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("dayType", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField DAY_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("dayVal", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField DAY_INDEX_FIELD_DESC = new org.apache.thrift.protocol.TField("dayIndex", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField DAY_NAME_DESC_FIELD_DESC = new org.apache.thrift.protocol.TField("dayNameDesc", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CusPerCreditDefStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CusPerCreditDefTupleSchemeFactory());
  }

  public int pid; // required
  public CusPerCredit cusPerCredit; // required
  public int dayType; // required
  public int dayVal; // required
  public int dayIndex; // required
  public String dayNameDesc; // required
  public int status; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    CUS_PER_CREDIT((short)2, "cusPerCredit"),
    DAY_TYPE((short)3, "dayType"),
    DAY_VAL((short)4, "dayVal"),
    DAY_INDEX((short)5, "dayIndex"),
    DAY_NAME_DESC((short)6, "dayNameDesc"),
    STATUS((short)7, "status");

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
        case 1: // PID
          return PID;
        case 2: // CUS_PER_CREDIT
          return CUS_PER_CREDIT;
        case 3: // DAY_TYPE
          return DAY_TYPE;
        case 4: // DAY_VAL
          return DAY_VAL;
        case 5: // DAY_INDEX
          return DAY_INDEX;
        case 6: // DAY_NAME_DESC
          return DAY_NAME_DESC;
        case 7: // STATUS
          return STATUS;
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
  private static final int __PID_ISSET_ID = 0;
  private static final int __DAYTYPE_ISSET_ID = 1;
  private static final int __DAYVAL_ISSET_ID = 2;
  private static final int __DAYINDEX_ISSET_ID = 3;
  private static final int __STATUS_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CUS_PER_CREDIT, new org.apache.thrift.meta_data.FieldMetaData("cusPerCredit", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, CusPerCredit.class)));
    tmpMap.put(_Fields.DAY_TYPE, new org.apache.thrift.meta_data.FieldMetaData("dayType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.DAY_VAL, new org.apache.thrift.meta_data.FieldMetaData("dayVal", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.DAY_INDEX, new org.apache.thrift.meta_data.FieldMetaData("dayIndex", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.DAY_NAME_DESC, new org.apache.thrift.meta_data.FieldMetaData("dayNameDesc", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CusPerCreditDef.class, metaDataMap);
  }

  public CusPerCreditDef() {
    this.status = 1;

  }

  public CusPerCreditDef(
    int pid,
    CusPerCredit cusPerCredit,
    int dayType,
    int dayVal,
    int dayIndex,
    String dayNameDesc,
    int status)
  {
    this();
    this.pid = pid;
    setPidIsSet(true);
    this.cusPerCredit = cusPerCredit;
    this.dayType = dayType;
    setDayTypeIsSet(true);
    this.dayVal = dayVal;
    setDayValIsSet(true);
    this.dayIndex = dayIndex;
    setDayIndexIsSet(true);
    this.dayNameDesc = dayNameDesc;
    this.status = status;
    setStatusIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CusPerCreditDef(CusPerCreditDef other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pid = other.pid;
    if (other.isSetCusPerCredit()) {
      this.cusPerCredit = new CusPerCredit(other.cusPerCredit);
    }
    this.dayType = other.dayType;
    this.dayVal = other.dayVal;
    this.dayIndex = other.dayIndex;
    if (other.isSetDayNameDesc()) {
      this.dayNameDesc = other.dayNameDesc;
    }
    this.status = other.status;
  }

  public CusPerCreditDef deepCopy() {
    return new CusPerCreditDef(this);
  }

  @Override
  public void clear() {
    setPidIsSet(false);
    this.pid = 0;
    this.cusPerCredit = null;
    setDayTypeIsSet(false);
    this.dayType = 0;
    setDayValIsSet(false);
    this.dayVal = 0;
    setDayIndexIsSet(false);
    this.dayIndex = 0;
    this.dayNameDesc = null;
    this.status = 1;

  }

  public int getPid() {
    return this.pid;
  }

  public CusPerCreditDef setPid(int pid) {
    this.pid = pid;
    setPidIsSet(true);
    return this;
  }

  public void unsetPid() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PID_ISSET_ID);
  }

  /** Returns true if field pid is set (has been assigned a value) and false otherwise */
  public boolean isSetPid() {
    return EncodingUtils.testBit(__isset_bitfield, __PID_ISSET_ID);
  }

  public void setPidIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PID_ISSET_ID, value);
  }

  public CusPerCredit getCusPerCredit() {
    return this.cusPerCredit;
  }

  public CusPerCreditDef setCusPerCredit(CusPerCredit cusPerCredit) {
    this.cusPerCredit = cusPerCredit;
    return this;
  }

  public void unsetCusPerCredit() {
    this.cusPerCredit = null;
  }

  /** Returns true if field cusPerCredit is set (has been assigned a value) and false otherwise */
  public boolean isSetCusPerCredit() {
    return this.cusPerCredit != null;
  }

  public void setCusPerCreditIsSet(boolean value) {
    if (!value) {
      this.cusPerCredit = null;
    }
  }

  public int getDayType() {
    return this.dayType;
  }

  public CusPerCreditDef setDayType(int dayType) {
    this.dayType = dayType;
    setDayTypeIsSet(true);
    return this;
  }

  public void unsetDayType() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DAYTYPE_ISSET_ID);
  }

  /** Returns true if field dayType is set (has been assigned a value) and false otherwise */
  public boolean isSetDayType() {
    return EncodingUtils.testBit(__isset_bitfield, __DAYTYPE_ISSET_ID);
  }

  public void setDayTypeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DAYTYPE_ISSET_ID, value);
  }

  public int getDayVal() {
    return this.dayVal;
  }

  public CusPerCreditDef setDayVal(int dayVal) {
    this.dayVal = dayVal;
    setDayValIsSet(true);
    return this;
  }

  public void unsetDayVal() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DAYVAL_ISSET_ID);
  }

  /** Returns true if field dayVal is set (has been assigned a value) and false otherwise */
  public boolean isSetDayVal() {
    return EncodingUtils.testBit(__isset_bitfield, __DAYVAL_ISSET_ID);
  }

  public void setDayValIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DAYVAL_ISSET_ID, value);
  }

  public int getDayIndex() {
    return this.dayIndex;
  }

  public CusPerCreditDef setDayIndex(int dayIndex) {
    this.dayIndex = dayIndex;
    setDayIndexIsSet(true);
    return this;
  }

  public void unsetDayIndex() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DAYINDEX_ISSET_ID);
  }

  /** Returns true if field dayIndex is set (has been assigned a value) and false otherwise */
  public boolean isSetDayIndex() {
    return EncodingUtils.testBit(__isset_bitfield, __DAYINDEX_ISSET_ID);
  }

  public void setDayIndexIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DAYINDEX_ISSET_ID, value);
  }

  public String getDayNameDesc() {
    return this.dayNameDesc;
  }

  public CusPerCreditDef setDayNameDesc(String dayNameDesc) {
    this.dayNameDesc = dayNameDesc;
    return this;
  }

  public void unsetDayNameDesc() {
    this.dayNameDesc = null;
  }

  /** Returns true if field dayNameDesc is set (has been assigned a value) and false otherwise */
  public boolean isSetDayNameDesc() {
    return this.dayNameDesc != null;
  }

  public void setDayNameDescIsSet(boolean value) {
    if (!value) {
      this.dayNameDesc = null;
    }
  }

  public int getStatus() {
    return this.status;
  }

  public CusPerCreditDef setStatus(int status) {
    this.status = status;
    setStatusIsSet(true);
    return this;
  }

  public void unsetStatus() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __STATUS_ISSET_ID);
  }

  /** Returns true if field status is set (has been assigned a value) and false otherwise */
  public boolean isSetStatus() {
    return EncodingUtils.testBit(__isset_bitfield, __STATUS_ISSET_ID);
  }

  public void setStatusIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __STATUS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PID:
      if (value == null) {
        unsetPid();
      } else {
        setPid((Integer)value);
      }
      break;

    case CUS_PER_CREDIT:
      if (value == null) {
        unsetCusPerCredit();
      } else {
        setCusPerCredit((CusPerCredit)value);
      }
      break;

    case DAY_TYPE:
      if (value == null) {
        unsetDayType();
      } else {
        setDayType((Integer)value);
      }
      break;

    case DAY_VAL:
      if (value == null) {
        unsetDayVal();
      } else {
        setDayVal((Integer)value);
      }
      break;

    case DAY_INDEX:
      if (value == null) {
        unsetDayIndex();
      } else {
        setDayIndex((Integer)value);
      }
      break;

    case DAY_NAME_DESC:
      if (value == null) {
        unsetDayNameDesc();
      } else {
        setDayNameDesc((String)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PID:
      return Integer.valueOf(getPid());

    case CUS_PER_CREDIT:
      return getCusPerCredit();

    case DAY_TYPE:
      return Integer.valueOf(getDayType());

    case DAY_VAL:
      return Integer.valueOf(getDayVal());

    case DAY_INDEX:
      return Integer.valueOf(getDayIndex());

    case DAY_NAME_DESC:
      return getDayNameDesc();

    case STATUS:
      return Integer.valueOf(getStatus());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PID:
      return isSetPid();
    case CUS_PER_CREDIT:
      return isSetCusPerCredit();
    case DAY_TYPE:
      return isSetDayType();
    case DAY_VAL:
      return isSetDayVal();
    case DAY_INDEX:
      return isSetDayIndex();
    case DAY_NAME_DESC:
      return isSetDayNameDesc();
    case STATUS:
      return isSetStatus();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CusPerCreditDef)
      return this.equals((CusPerCreditDef)that);
    return false;
  }

  public boolean equals(CusPerCreditDef that) {
    if (that == null)
      return false;

    boolean this_present_pid = true;
    boolean that_present_pid = true;
    if (this_present_pid || that_present_pid) {
      if (!(this_present_pid && that_present_pid))
        return false;
      if (this.pid != that.pid)
        return false;
    }

    boolean this_present_cusPerCredit = true && this.isSetCusPerCredit();
    boolean that_present_cusPerCredit = true && that.isSetCusPerCredit();
    if (this_present_cusPerCredit || that_present_cusPerCredit) {
      if (!(this_present_cusPerCredit && that_present_cusPerCredit))
        return false;
      if (!this.cusPerCredit.equals(that.cusPerCredit))
        return false;
    }

    boolean this_present_dayType = true;
    boolean that_present_dayType = true;
    if (this_present_dayType || that_present_dayType) {
      if (!(this_present_dayType && that_present_dayType))
        return false;
      if (this.dayType != that.dayType)
        return false;
    }

    boolean this_present_dayVal = true;
    boolean that_present_dayVal = true;
    if (this_present_dayVal || that_present_dayVal) {
      if (!(this_present_dayVal && that_present_dayVal))
        return false;
      if (this.dayVal != that.dayVal)
        return false;
    }

    boolean this_present_dayIndex = true;
    boolean that_present_dayIndex = true;
    if (this_present_dayIndex || that_present_dayIndex) {
      if (!(this_present_dayIndex && that_present_dayIndex))
        return false;
      if (this.dayIndex != that.dayIndex)
        return false;
    }

    boolean this_present_dayNameDesc = true && this.isSetDayNameDesc();
    boolean that_present_dayNameDesc = true && that.isSetDayNameDesc();
    if (this_present_dayNameDesc || that_present_dayNameDesc) {
      if (!(this_present_dayNameDesc && that_present_dayNameDesc))
        return false;
      if (!this.dayNameDesc.equals(that.dayNameDesc))
        return false;
    }

    boolean this_present_status = true;
    boolean that_present_status = true;
    if (this_present_status || that_present_status) {
      if (!(this_present_status && that_present_status))
        return false;
      if (this.status != that.status)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_pid = true;
    list.add(present_pid);
    if (present_pid)
      list.add(pid);

    boolean present_cusPerCredit = true && (isSetCusPerCredit());
    list.add(present_cusPerCredit);
    if (present_cusPerCredit)
      list.add(cusPerCredit);

    boolean present_dayType = true;
    list.add(present_dayType);
    if (present_dayType)
      list.add(dayType);

    boolean present_dayVal = true;
    list.add(present_dayVal);
    if (present_dayVal)
      list.add(dayVal);

    boolean present_dayIndex = true;
    list.add(present_dayIndex);
    if (present_dayIndex)
      list.add(dayIndex);

    boolean present_dayNameDesc = true && (isSetDayNameDesc());
    list.add(present_dayNameDesc);
    if (present_dayNameDesc)
      list.add(dayNameDesc);

    boolean present_status = true;
    list.add(present_status);
    if (present_status)
      list.add(status);

    return list.hashCode();
  }

  @Override
  public int compareTo(CusPerCreditDef other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPid()).compareTo(other.isSetPid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pid, other.pid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCusPerCredit()).compareTo(other.isSetCusPerCredit());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCusPerCredit()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cusPerCredit, other.cusPerCredit);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDayType()).compareTo(other.isSetDayType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDayType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dayType, other.dayType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDayVal()).compareTo(other.isSetDayVal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDayVal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dayVal, other.dayVal);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDayIndex()).compareTo(other.isSetDayIndex());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDayIndex()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dayIndex, other.dayIndex);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDayNameDesc()).compareTo(other.isSetDayNameDesc());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDayNameDesc()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dayNameDesc, other.dayNameDesc);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStatus()).compareTo(other.isSetStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.status, other.status);
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
    StringBuilder sb = new StringBuilder("CusPerCreditDef(");
    boolean first = true;

    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("cusPerCredit:");
    if (this.cusPerCredit == null) {
      sb.append("null");
    } else {
      sb.append(this.cusPerCredit);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("dayType:");
    sb.append(this.dayType);
    first = false;
    if (!first) sb.append(", ");
    sb.append("dayVal:");
    sb.append(this.dayVal);
    first = false;
    if (!first) sb.append(", ");
    sb.append("dayIndex:");
    sb.append(this.dayIndex);
    first = false;
    if (!first) sb.append(", ");
    sb.append("dayNameDesc:");
    if (this.dayNameDesc == null) {
      sb.append("null");
    } else {
      sb.append(this.dayNameDesc);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("status:");
    sb.append(this.status);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (cusPerCredit != null) {
      cusPerCredit.validate();
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class CusPerCreditDefStandardSchemeFactory implements SchemeFactory {
    public CusPerCreditDefStandardScheme getScheme() {
      return new CusPerCreditDefStandardScheme();
    }
  }

  private static class CusPerCreditDefStandardScheme extends StandardScheme<CusPerCreditDef> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CusPerCreditDef struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.pid = iprot.readI32();
              struct.setPidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CUS_PER_CREDIT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.cusPerCredit = new CusPerCredit();
              struct.cusPerCredit.read(iprot);
              struct.setCusPerCreditIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // DAY_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.dayType = iprot.readI32();
              struct.setDayTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // DAY_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.dayVal = iprot.readI32();
              struct.setDayValIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // DAY_INDEX
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.dayIndex = iprot.readI32();
              struct.setDayIndexIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // DAY_NAME_DESC
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.dayNameDesc = iprot.readString();
              struct.setDayNameDescIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.status = iprot.readI32();
              struct.setStatusIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CusPerCreditDef struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PID_FIELD_DESC);
      oprot.writeI32(struct.pid);
      oprot.writeFieldEnd();
      if (struct.cusPerCredit != null) {
        oprot.writeFieldBegin(CUS_PER_CREDIT_FIELD_DESC);
        struct.cusPerCredit.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(DAY_TYPE_FIELD_DESC);
      oprot.writeI32(struct.dayType);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(DAY_VAL_FIELD_DESC);
      oprot.writeI32(struct.dayVal);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(DAY_INDEX_FIELD_DESC);
      oprot.writeI32(struct.dayIndex);
      oprot.writeFieldEnd();
      if (struct.dayNameDesc != null) {
        oprot.writeFieldBegin(DAY_NAME_DESC_FIELD_DESC);
        oprot.writeString(struct.dayNameDesc);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(STATUS_FIELD_DESC);
      oprot.writeI32(struct.status);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CusPerCreditDefTupleSchemeFactory implements SchemeFactory {
    public CusPerCreditDefTupleScheme getScheme() {
      return new CusPerCreditDefTupleScheme();
    }
  }

  private static class CusPerCreditDefTupleScheme extends TupleScheme<CusPerCreditDef> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CusPerCreditDef struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPid()) {
        optionals.set(0);
      }
      if (struct.isSetCusPerCredit()) {
        optionals.set(1);
      }
      if (struct.isSetDayType()) {
        optionals.set(2);
      }
      if (struct.isSetDayVal()) {
        optionals.set(3);
      }
      if (struct.isSetDayIndex()) {
        optionals.set(4);
      }
      if (struct.isSetDayNameDesc()) {
        optionals.set(5);
      }
      if (struct.isSetStatus()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetPid()) {
        oprot.writeI32(struct.pid);
      }
      if (struct.isSetCusPerCredit()) {
        struct.cusPerCredit.write(oprot);
      }
      if (struct.isSetDayType()) {
        oprot.writeI32(struct.dayType);
      }
      if (struct.isSetDayVal()) {
        oprot.writeI32(struct.dayVal);
      }
      if (struct.isSetDayIndex()) {
        oprot.writeI32(struct.dayIndex);
      }
      if (struct.isSetDayNameDesc()) {
        oprot.writeString(struct.dayNameDesc);
      }
      if (struct.isSetStatus()) {
        oprot.writeI32(struct.status);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CusPerCreditDef struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.pid = iprot.readI32();
        struct.setPidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.cusPerCredit = new CusPerCredit();
        struct.cusPerCredit.read(iprot);
        struct.setCusPerCreditIsSet(true);
      }
      if (incoming.get(2)) {
        struct.dayType = iprot.readI32();
        struct.setDayTypeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.dayVal = iprot.readI32();
        struct.setDayValIsSet(true);
      }
      if (incoming.get(4)) {
        struct.dayIndex = iprot.readI32();
        struct.setDayIndexIsSet(true);
      }
      if (incoming.get(5)) {
        struct.dayNameDesc = iprot.readString();
        struct.setDayNameDescIsSet(true);
      }
      if (incoming.get(6)) {
        struct.status = iprot.readI32();
        struct.setStatusIsSet(true);
      }
    }
  }

}

