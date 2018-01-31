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
public class CusComBalanceSheetCalculateDTO implements org.apache.thrift.TBase<CusComBalanceSheetCalculateDTO, CusComBalanceSheetCalculateDTO._Fields>, java.io.Serializable, Cloneable, Comparable<CusComBalanceSheetCalculateDTO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CusComBalanceSheetCalculateDTO");

  private static final org.apache.thrift.protocol.TField LINE_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("lineNum", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField ACCOUNTS_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("accountsName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField BEGIN_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("beginVal", org.apache.thrift.protocol.TType.DOUBLE, (short)3);
  private static final org.apache.thrift.protocol.TField END_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("endVal", org.apache.thrift.protocol.TType.DOUBLE, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CusComBalanceSheetCalculateDTOStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CusComBalanceSheetCalculateDTOTupleSchemeFactory());
  }

  public int lineNum; // required
  public String accountsName; // required
  public double beginVal; // required
  public double endVal; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    LINE_NUM((short)1, "lineNum"),
    ACCOUNTS_NAME((short)2, "accountsName"),
    BEGIN_VAL((short)3, "beginVal"),
    END_VAL((short)4, "endVal");

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
        case 1: // LINE_NUM
          return LINE_NUM;
        case 2: // ACCOUNTS_NAME
          return ACCOUNTS_NAME;
        case 3: // BEGIN_VAL
          return BEGIN_VAL;
        case 4: // END_VAL
          return END_VAL;
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
  private static final int __LINENUM_ISSET_ID = 0;
  private static final int __BEGINVAL_ISSET_ID = 1;
  private static final int __ENDVAL_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.LINE_NUM, new org.apache.thrift.meta_data.FieldMetaData("lineNum", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ACCOUNTS_NAME, new org.apache.thrift.meta_data.FieldMetaData("accountsName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BEGIN_VAL, new org.apache.thrift.meta_data.FieldMetaData("beginVal", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.END_VAL, new org.apache.thrift.meta_data.FieldMetaData("endVal", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CusComBalanceSheetCalculateDTO.class, metaDataMap);
  }

  public CusComBalanceSheetCalculateDTO() {
  }

  public CusComBalanceSheetCalculateDTO(
    int lineNum,
    String accountsName,
    double beginVal,
    double endVal)
  {
    this();
    this.lineNum = lineNum;
    setLineNumIsSet(true);
    this.accountsName = accountsName;
    this.beginVal = beginVal;
    setBeginValIsSet(true);
    this.endVal = endVal;
    setEndValIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CusComBalanceSheetCalculateDTO(CusComBalanceSheetCalculateDTO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.lineNum = other.lineNum;
    if (other.isSetAccountsName()) {
      this.accountsName = other.accountsName;
    }
    this.beginVal = other.beginVal;
    this.endVal = other.endVal;
  }

  public CusComBalanceSheetCalculateDTO deepCopy() {
    return new CusComBalanceSheetCalculateDTO(this);
  }

  @Override
  public void clear() {
    setLineNumIsSet(false);
    this.lineNum = 0;
    this.accountsName = null;
    setBeginValIsSet(false);
    this.beginVal = 0.0;
    setEndValIsSet(false);
    this.endVal = 0.0;
  }

  public int getLineNum() {
    return this.lineNum;
  }

  public CusComBalanceSheetCalculateDTO setLineNum(int lineNum) {
    this.lineNum = lineNum;
    setLineNumIsSet(true);
    return this;
  }

  public void unsetLineNum() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LINENUM_ISSET_ID);
  }

  /** Returns true if field lineNum is set (has been assigned a value) and false otherwise */
  public boolean isSetLineNum() {
    return EncodingUtils.testBit(__isset_bitfield, __LINENUM_ISSET_ID);
  }

  public void setLineNumIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LINENUM_ISSET_ID, value);
  }

  public String getAccountsName() {
    return this.accountsName;
  }

  public CusComBalanceSheetCalculateDTO setAccountsName(String accountsName) {
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

  public CusComBalanceSheetCalculateDTO setBeginVal(double beginVal) {
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

  public CusComBalanceSheetCalculateDTO setEndVal(double endVal) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case LINE_NUM:
      if (value == null) {
        unsetLineNum();
      } else {
        setLineNum((Integer)value);
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

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case LINE_NUM:
      return Integer.valueOf(getLineNum());

    case ACCOUNTS_NAME:
      return getAccountsName();

    case BEGIN_VAL:
      return Double.valueOf(getBeginVal());

    case END_VAL:
      return Double.valueOf(getEndVal());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case LINE_NUM:
      return isSetLineNum();
    case ACCOUNTS_NAME:
      return isSetAccountsName();
    case BEGIN_VAL:
      return isSetBeginVal();
    case END_VAL:
      return isSetEndVal();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CusComBalanceSheetCalculateDTO)
      return this.equals((CusComBalanceSheetCalculateDTO)that);
    return false;
  }

  public boolean equals(CusComBalanceSheetCalculateDTO that) {
    if (that == null)
      return false;

    boolean this_present_lineNum = true;
    boolean that_present_lineNum = true;
    if (this_present_lineNum || that_present_lineNum) {
      if (!(this_present_lineNum && that_present_lineNum))
        return false;
      if (this.lineNum != that.lineNum)
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

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_lineNum = true;
    list.add(present_lineNum);
    if (present_lineNum)
      list.add(lineNum);

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

    return list.hashCode();
  }

  @Override
  public int compareTo(CusComBalanceSheetCalculateDTO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetLineNum()).compareTo(other.isSetLineNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLineNum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lineNum, other.lineNum);
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
    StringBuilder sb = new StringBuilder("CusComBalanceSheetCalculateDTO(");
    boolean first = true;

    sb.append("lineNum:");
    sb.append(this.lineNum);
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

  private static class CusComBalanceSheetCalculateDTOStandardSchemeFactory implements SchemeFactory {
    public CusComBalanceSheetCalculateDTOStandardScheme getScheme() {
      return new CusComBalanceSheetCalculateDTOStandardScheme();
    }
  }

  private static class CusComBalanceSheetCalculateDTOStandardScheme extends StandardScheme<CusComBalanceSheetCalculateDTO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CusComBalanceSheetCalculateDTO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // LINE_NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.lineNum = iprot.readI32();
              struct.setLineNumIsSet(true);
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
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, CusComBalanceSheetCalculateDTO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(LINE_NUM_FIELD_DESC);
      oprot.writeI32(struct.lineNum);
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
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CusComBalanceSheetCalculateDTOTupleSchemeFactory implements SchemeFactory {
    public CusComBalanceSheetCalculateDTOTupleScheme getScheme() {
      return new CusComBalanceSheetCalculateDTOTupleScheme();
    }
  }

  private static class CusComBalanceSheetCalculateDTOTupleScheme extends TupleScheme<CusComBalanceSheetCalculateDTO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CusComBalanceSheetCalculateDTO struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetLineNum()) {
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
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetLineNum()) {
        oprot.writeI32(struct.lineNum);
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
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CusComBalanceSheetCalculateDTO struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.lineNum = iprot.readI32();
        struct.setLineNumIsSet(true);
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
    }
  }

}

