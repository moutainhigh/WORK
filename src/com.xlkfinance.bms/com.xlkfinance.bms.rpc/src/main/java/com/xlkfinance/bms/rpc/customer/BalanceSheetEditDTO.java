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
 * 编辑财务-负债报表时候使用
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2017-7-25")
public class BalanceSheetEditDTO implements org.apache.thrift.TBase<BalanceSheetEditDTO, BalanceSheetEditDTO._Fields>, java.io.Serializable, Cloneable, Comparable<BalanceSheetEditDTO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BalanceSheetEditDTO");

  private static final org.apache.thrift.protocol.TField BS_PID_FIELD_DESC = new org.apache.thrift.protocol.TField("bsPid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField ACCOUNTS_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("accountsName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField BEGIN_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("beginVal", org.apache.thrift.protocol.TType.DOUBLE, (short)3);
  private static final org.apache.thrift.protocol.TField END_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("endVal", org.apache.thrift.protocol.TType.DOUBLE, (short)4);
  private static final org.apache.thrift.protocol.TField BS_REPORT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("bsReportId", org.apache.thrift.protocol.TType.I32, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BalanceSheetEditDTOStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BalanceSheetEditDTOTupleSchemeFactory());
  }

  public int bsPid; // required
  public String accountsName; // required
  public double beginVal; // required
  public double endVal; // required
  public int bsReportId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    BS_PID((short)1, "bsPid"),
    ACCOUNTS_NAME((short)2, "accountsName"),
    BEGIN_VAL((short)3, "beginVal"),
    END_VAL((short)4, "endVal"),
    BS_REPORT_ID((short)5, "bsReportId");

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
        case 1: // BS_PID
          return BS_PID;
        case 2: // ACCOUNTS_NAME
          return ACCOUNTS_NAME;
        case 3: // BEGIN_VAL
          return BEGIN_VAL;
        case 4: // END_VAL
          return END_VAL;
        case 5: // BS_REPORT_ID
          return BS_REPORT_ID;
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
  private static final int __BSPID_ISSET_ID = 0;
  private static final int __BEGINVAL_ISSET_ID = 1;
  private static final int __ENDVAL_ISSET_ID = 2;
  private static final int __BSREPORTID_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.BS_PID, new org.apache.thrift.meta_data.FieldMetaData("bsPid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ACCOUNTS_NAME, new org.apache.thrift.meta_data.FieldMetaData("accountsName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BEGIN_VAL, new org.apache.thrift.meta_data.FieldMetaData("beginVal", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.END_VAL, new org.apache.thrift.meta_data.FieldMetaData("endVal", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.BS_REPORT_ID, new org.apache.thrift.meta_data.FieldMetaData("bsReportId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BalanceSheetEditDTO.class, metaDataMap);
  }

  public BalanceSheetEditDTO() {
  }

  public BalanceSheetEditDTO(
    int bsPid,
    String accountsName,
    double beginVal,
    double endVal,
    int bsReportId)
  {
    this();
    this.bsPid = bsPid;
    setBsPidIsSet(true);
    this.accountsName = accountsName;
    this.beginVal = beginVal;
    setBeginValIsSet(true);
    this.endVal = endVal;
    setEndValIsSet(true);
    this.bsReportId = bsReportId;
    setBsReportIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BalanceSheetEditDTO(BalanceSheetEditDTO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.bsPid = other.bsPid;
    if (other.isSetAccountsName()) {
      this.accountsName = other.accountsName;
    }
    this.beginVal = other.beginVal;
    this.endVal = other.endVal;
    this.bsReportId = other.bsReportId;
  }

  public BalanceSheetEditDTO deepCopy() {
    return new BalanceSheetEditDTO(this);
  }

  @Override
  public void clear() {
    setBsPidIsSet(false);
    this.bsPid = 0;
    this.accountsName = null;
    setBeginValIsSet(false);
    this.beginVal = 0.0;
    setEndValIsSet(false);
    this.endVal = 0.0;
    setBsReportIdIsSet(false);
    this.bsReportId = 0;
  }

  public int getBsPid() {
    return this.bsPid;
  }

  public BalanceSheetEditDTO setBsPid(int bsPid) {
    this.bsPid = bsPid;
    setBsPidIsSet(true);
    return this;
  }

  public void unsetBsPid() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __BSPID_ISSET_ID);
  }

  /** Returns true if field bsPid is set (has been assigned a value) and false otherwise */
  public boolean isSetBsPid() {
    return EncodingUtils.testBit(__isset_bitfield, __BSPID_ISSET_ID);
  }

  public void setBsPidIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __BSPID_ISSET_ID, value);
  }

  public String getAccountsName() {
    return this.accountsName;
  }

  public BalanceSheetEditDTO setAccountsName(String accountsName) {
    this.accountsName = accountsName;
    return this;
  }

  public void unsetAccountsName() {
    this.accountsName = null;
  }

  /** Returns true if field accountsName is set (has been assigned a value) and false otherwise */
  public boolean isSetAccountsName() {
    return this.accountsName != null;
  }

  public void setAccountsNameIsSet(boolean value) {
    if (!value) {
      this.accountsName = null;
    }
  }

  public double getBeginVal() {
    return this.beginVal;
  }

  public BalanceSheetEditDTO setBeginVal(double beginVal) {
    this.beginVal = beginVal;
    setBeginValIsSet(true);
    return this;
  }

  public void unsetBeginVal() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __BEGINVAL_ISSET_ID);
  }

  /** Returns true if field beginVal is set (has been assigned a value) and false otherwise */
  public boolean isSetBeginVal() {
    return EncodingUtils.testBit(__isset_bitfield, __BEGINVAL_ISSET_ID);
  }

  public void setBeginValIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __BEGINVAL_ISSET_ID, value);
  }

  public double getEndVal() {
    return this.endVal;
  }

  public BalanceSheetEditDTO setEndVal(double endVal) {
    this.endVal = endVal;
    setEndValIsSet(true);
    return this;
  }

  public void unsetEndVal() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ENDVAL_ISSET_ID);
  }

  /** Returns true if field endVal is set (has been assigned a value) and false otherwise */
  public boolean isSetEndVal() {
    return EncodingUtils.testBit(__isset_bitfield, __ENDVAL_ISSET_ID);
  }

  public void setEndValIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ENDVAL_ISSET_ID, value);
  }

  public int getBsReportId() {
    return this.bsReportId;
  }

  public BalanceSheetEditDTO setBsReportId(int bsReportId) {
    this.bsReportId = bsReportId;
    setBsReportIdIsSet(true);
    return this;
  }

  public void unsetBsReportId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __BSREPORTID_ISSET_ID);
  }

  /** Returns true if field bsReportId is set (has been assigned a value) and false otherwise */
  public boolean isSetBsReportId() {
    return EncodingUtils.testBit(__isset_bitfield, __BSREPORTID_ISSET_ID);
  }

  public void setBsReportIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __BSREPORTID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case BS_PID:
      if (value == null) {
        unsetBsPid();
      } else {
        setBsPid((Integer)value);
      }
      break;

    case ACCOUNTS_NAME:
      if (value == null) {
        unsetAccountsName();
      } else {
        setAccountsName((String)value);
      }
      break;

    case BEGIN_VAL:
      if (value == null) {
        unsetBeginVal();
      } else {
        setBeginVal((Double)value);
      }
      break;

    case END_VAL:
      if (value == null) {
        unsetEndVal();
      } else {
        setEndVal((Double)value);
      }
      break;

    case BS_REPORT_ID:
      if (value == null) {
        unsetBsReportId();
      } else {
        setBsReportId((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case BS_PID:
      return Integer.valueOf(getBsPid());

    case ACCOUNTS_NAME:
      return getAccountsName();

    case BEGIN_VAL:
      return Double.valueOf(getBeginVal());

    case END_VAL:
      return Double.valueOf(getEndVal());

    case BS_REPORT_ID:
      return Integer.valueOf(getBsReportId());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case BS_PID:
      return isSetBsPid();
    case ACCOUNTS_NAME:
      return isSetAccountsName();
    case BEGIN_VAL:
      return isSetBeginVal();
    case END_VAL:
      return isSetEndVal();
    case BS_REPORT_ID:
      return isSetBsReportId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BalanceSheetEditDTO)
      return this.equals((BalanceSheetEditDTO)that);
    return false;
  }

  public boolean equals(BalanceSheetEditDTO that) {
    if (that == null)
      return false;

    boolean this_present_bsPid = true;
    boolean that_present_bsPid = true;
    if (this_present_bsPid || that_present_bsPid) {
      if (!(this_present_bsPid && that_present_bsPid))
        return false;
      if (this.bsPid != that.bsPid)
        return false;
    }

    boolean this_present_accountsName = true && this.isSetAccountsName();
    boolean that_present_accountsName = true && that.isSetAccountsName();
    if (this_present_accountsName || that_present_accountsName) {
      if (!(this_present_accountsName && that_present_accountsName))
        return false;
      if (!this.accountsName.equals(that.accountsName))
        return false;
    }

    boolean this_present_beginVal = true;
    boolean that_present_beginVal = true;
    if (this_present_beginVal || that_present_beginVal) {
      if (!(this_present_beginVal && that_present_beginVal))
        return false;
      if (this.beginVal != that.beginVal)
        return false;
    }

    boolean this_present_endVal = true;
    boolean that_present_endVal = true;
    if (this_present_endVal || that_present_endVal) {
      if (!(this_present_endVal && that_present_endVal))
        return false;
      if (this.endVal != that.endVal)
        return false;
    }

    boolean this_present_bsReportId = true;
    boolean that_present_bsReportId = true;
    if (this_present_bsReportId || that_present_bsReportId) {
      if (!(this_present_bsReportId && that_present_bsReportId))
        return false;
      if (this.bsReportId != that.bsReportId)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_bsPid = true;
    list.add(present_bsPid);
    if (present_bsPid)
      list.add(bsPid);

    boolean present_accountsName = true && (isSetAccountsName());
    list.add(present_accountsName);
    if (present_accountsName)
      list.add(accountsName);

    boolean present_beginVal = true;
    list.add(present_beginVal);
    if (present_beginVal)
      list.add(beginVal);

    boolean present_endVal = true;
    list.add(present_endVal);
    if (present_endVal)
      list.add(endVal);

    boolean present_bsReportId = true;
    list.add(present_bsReportId);
    if (present_bsReportId)
      list.add(bsReportId);

    return list.hashCode();
  }

  @Override
  public int compareTo(BalanceSheetEditDTO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetBsPid()).compareTo(other.isSetBsPid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBsPid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bsPid, other.bsPid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAccountsName()).compareTo(other.isSetAccountsName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAccountsName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.accountsName, other.accountsName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBeginVal()).compareTo(other.isSetBeginVal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBeginVal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.beginVal, other.beginVal);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEndVal()).compareTo(other.isSetEndVal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEndVal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.endVal, other.endVal);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBsReportId()).compareTo(other.isSetBsReportId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBsReportId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bsReportId, other.bsReportId);
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
    StringBuilder sb = new StringBuilder("BalanceSheetEditDTO(");
    boolean first = true;

    sb.append("bsPid:");
    sb.append(this.bsPid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("accountsName:");
    if (this.accountsName == null) {
      sb.append("null");
    } else {
      sb.append(this.accountsName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("beginVal:");
    sb.append(this.beginVal);
    first = false;
    if (!first) sb.append(", ");
    sb.append("endVal:");
    sb.append(this.endVal);
    first = false;
    if (!first) sb.append(", ");
    sb.append("bsReportId:");
    sb.append(this.bsReportId);
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

  private static class BalanceSheetEditDTOStandardSchemeFactory implements SchemeFactory {
    public BalanceSheetEditDTOStandardScheme getScheme() {
      return new BalanceSheetEditDTOStandardScheme();
    }
  }

  private static class BalanceSheetEditDTOStandardScheme extends StandardScheme<BalanceSheetEditDTO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BalanceSheetEditDTO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // BS_PID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.bsPid = iprot.readI32();
              struct.setBsPidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ACCOUNTS_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.accountsName = iprot.readString();
              struct.setAccountsNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // BEGIN_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.beginVal = iprot.readDouble();
              struct.setBeginValIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // END_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.endVal = iprot.readDouble();
              struct.setEndValIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // BS_REPORT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.bsReportId = iprot.readI32();
              struct.setBsReportIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BalanceSheetEditDTO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(BS_PID_FIELD_DESC);
      oprot.writeI32(struct.bsPid);
      oprot.writeFieldEnd();
      if (struct.accountsName != null) {
        oprot.writeFieldBegin(ACCOUNTS_NAME_FIELD_DESC);
        oprot.writeString(struct.accountsName);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(BEGIN_VAL_FIELD_DESC);
      oprot.writeDouble(struct.beginVal);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(END_VAL_FIELD_DESC);
      oprot.writeDouble(struct.endVal);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(BS_REPORT_ID_FIELD_DESC);
      oprot.writeI32(struct.bsReportId);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BalanceSheetEditDTOTupleSchemeFactory implements SchemeFactory {
    public BalanceSheetEditDTOTupleScheme getScheme() {
      return new BalanceSheetEditDTOTupleScheme();
    }
  }

  private static class BalanceSheetEditDTOTupleScheme extends TupleScheme<BalanceSheetEditDTO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BalanceSheetEditDTO struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetBsPid()) {
        optionals.set(0);
      }
      if (struct.isSetAccountsName()) {
        optionals.set(1);
      }
      if (struct.isSetBeginVal()) {
        optionals.set(2);
      }
      if (struct.isSetEndVal()) {
        optionals.set(3);
      }
      if (struct.isSetBsReportId()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetBsPid()) {
        oprot.writeI32(struct.bsPid);
      }
      if (struct.isSetAccountsName()) {
        oprot.writeString(struct.accountsName);
      }
      if (struct.isSetBeginVal()) {
        oprot.writeDouble(struct.beginVal);
      }
      if (struct.isSetEndVal()) {
        oprot.writeDouble(struct.endVal);
      }
      if (struct.isSetBsReportId()) {
        oprot.writeI32(struct.bsReportId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BalanceSheetEditDTO struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.bsPid = iprot.readI32();
        struct.setBsPidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.accountsName = iprot.readString();
        struct.setAccountsNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.beginVal = iprot.readDouble();
        struct.setBeginValIsSet(true);
      }
      if (incoming.get(3)) {
        struct.endVal = iprot.readDouble();
        struct.setEndValIsSet(true);
      }
      if (incoming.get(4)) {
        struct.bsReportId = iprot.readI32();
        struct.setBsReportIdIsSet(true);
      }
    }
  }

}
