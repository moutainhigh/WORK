/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xlkfinance.bms.rpc.product;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-8-11")
public class ActProduct implements org.apache.thrift.TBase<ActProduct, ActProduct._Fields>, java.io.Serializable, Cloneable, Comparable<ActProduct> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ActProduct");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField ACT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("actId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField PRODUCT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("productId", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField ACT_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("actType", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField ACT_TYPE_TEXT_FIELD_DESC = new org.apache.thrift.protocol.TField("actTypeText", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ActProductStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ActProductTupleSchemeFactory());
  }

  public int pid; // required
  public String actId; // required
  public int productId; // required
  public int actType; // required
  public String actTypeText; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    ACT_ID((short)2, "actId"),
    PRODUCT_ID((short)3, "productId"),
    ACT_TYPE((short)4, "actType"),
    ACT_TYPE_TEXT((short)5, "actTypeText");

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
        case 2: // ACT_ID
          return ACT_ID;
        case 3: // PRODUCT_ID
          return PRODUCT_ID;
        case 4: // ACT_TYPE
          return ACT_TYPE;
        case 5: // ACT_TYPE_TEXT
          return ACT_TYPE_TEXT;
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
  private static final int __PRODUCTID_ISSET_ID = 1;
  private static final int __ACTTYPE_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ACT_ID, new org.apache.thrift.meta_data.FieldMetaData("actId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PRODUCT_ID, new org.apache.thrift.meta_data.FieldMetaData("productId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ACT_TYPE, new org.apache.thrift.meta_data.FieldMetaData("actType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ACT_TYPE_TEXT, new org.apache.thrift.meta_data.FieldMetaData("actTypeText", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ActProduct.class, metaDataMap);
  }

  public ActProduct() {
  }

  public ActProduct(
    int pid,
    String actId,
    int productId,
    int actType,
    String actTypeText)
  {
    this();
    this.pid = pid;
    setPidIsSet(true);
    this.actId = actId;
    this.productId = productId;
    setProductIdIsSet(true);
    this.actType = actType;
    setActTypeIsSet(true);
    this.actTypeText = actTypeText;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ActProduct(ActProduct other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pid = other.pid;
    if (other.isSetActId()) {
      this.actId = other.actId;
    }
    this.productId = other.productId;
    this.actType = other.actType;
    if (other.isSetActTypeText()) {
      this.actTypeText = other.actTypeText;
    }
  }

  public ActProduct deepCopy() {
    return new ActProduct(this);
  }

  @Override
  public void clear() {
    setPidIsSet(false);
    this.pid = 0;
    this.actId = null;
    setProductIdIsSet(false);
    this.productId = 0;
    setActTypeIsSet(false);
    this.actType = 0;
    this.actTypeText = null;
  }

  public int getPid() {
    return this.pid;
  }

  public ActProduct setPid(int pid) {
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

  public String getActId() {
    return this.actId;
  }

  public ActProduct setActId(String actId) {
    this.actId = actId;
    return this;
  }

  public void unsetActId() {
    this.actId = null;
  }

  /** Returns true if field actId is set (has been assigned a value) and false otherwise */
  public boolean isSetActId() {
    return this.actId != null;
  }

  public void setActIdIsSet(boolean value) {
    if (!value) {
      this.actId = null;
    }
  }

  public int getProductId() {
    return this.productId;
  }

  public ActProduct setProductId(int productId) {
    this.productId = productId;
    setProductIdIsSet(true);
    return this;
  }

  public void unsetProductId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PRODUCTID_ISSET_ID);
  }

  /** Returns true if field productId is set (has been assigned a value) and false otherwise */
  public boolean isSetProductId() {
    return EncodingUtils.testBit(__isset_bitfield, __PRODUCTID_ISSET_ID);
  }

  public void setProductIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PRODUCTID_ISSET_ID, value);
  }

  public int getActType() {
    return this.actType;
  }

  public ActProduct setActType(int actType) {
    this.actType = actType;
    setActTypeIsSet(true);
    return this;
  }

  public void unsetActType() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ACTTYPE_ISSET_ID);
  }

  /** Returns true if field actType is set (has been assigned a value) and false otherwise */
  public boolean isSetActType() {
    return EncodingUtils.testBit(__isset_bitfield, __ACTTYPE_ISSET_ID);
  }

  public void setActTypeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ACTTYPE_ISSET_ID, value);
  }

  public String getActTypeText() {
    return this.actTypeText;
  }

  public ActProduct setActTypeText(String actTypeText) {
    this.actTypeText = actTypeText;
    return this;
  }

  public void unsetActTypeText() {
    this.actTypeText = null;
  }

  /** Returns true if field actTypeText is set (has been assigned a value) and false otherwise */
  public boolean isSetActTypeText() {
    return this.actTypeText != null;
  }

  public void setActTypeTextIsSet(boolean value) {
    if (!value) {
      this.actTypeText = null;
    }
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

    case ACT_ID:
      if (value == null) {
        unsetActId();
      } else {
        setActId((String)value);
      }
      break;

    case PRODUCT_ID:
      if (value == null) {
        unsetProductId();
      } else {
        setProductId((Integer)value);
      }
      break;

    case ACT_TYPE:
      if (value == null) {
        unsetActType();
      } else {
        setActType((Integer)value);
      }
      break;

    case ACT_TYPE_TEXT:
      if (value == null) {
        unsetActTypeText();
      } else {
        setActTypeText((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PID:
      return Integer.valueOf(getPid());

    case ACT_ID:
      return getActId();

    case PRODUCT_ID:
      return Integer.valueOf(getProductId());

    case ACT_TYPE:
      return Integer.valueOf(getActType());

    case ACT_TYPE_TEXT:
      return getActTypeText();

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
    case ACT_ID:
      return isSetActId();
    case PRODUCT_ID:
      return isSetProductId();
    case ACT_TYPE:
      return isSetActType();
    case ACT_TYPE_TEXT:
      return isSetActTypeText();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ActProduct)
      return this.equals((ActProduct)that);
    return false;
  }

  public boolean equals(ActProduct that) {
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

    boolean this_present_actId = true && this.isSetActId();
    boolean that_present_actId = true && that.isSetActId();
    if (this_present_actId || that_present_actId) {
      if (!(this_present_actId && that_present_actId))
        return false;
      if (!this.actId.equals(that.actId))
        return false;
    }

    boolean this_present_productId = true;
    boolean that_present_productId = true;
    if (this_present_productId || that_present_productId) {
      if (!(this_present_productId && that_present_productId))
        return false;
      if (this.productId != that.productId)
        return false;
    }

    boolean this_present_actType = true;
    boolean that_present_actType = true;
    if (this_present_actType || that_present_actType) {
      if (!(this_present_actType && that_present_actType))
        return false;
      if (this.actType != that.actType)
        return false;
    }

    boolean this_present_actTypeText = true && this.isSetActTypeText();
    boolean that_present_actTypeText = true && that.isSetActTypeText();
    if (this_present_actTypeText || that_present_actTypeText) {
      if (!(this_present_actTypeText && that_present_actTypeText))
        return false;
      if (!this.actTypeText.equals(that.actTypeText))
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

    boolean present_actId = true && (isSetActId());
    list.add(present_actId);
    if (present_actId)
      list.add(actId);

    boolean present_productId = true;
    list.add(present_productId);
    if (present_productId)
      list.add(productId);

    boolean present_actType = true;
    list.add(present_actType);
    if (present_actType)
      list.add(actType);

    boolean present_actTypeText = true && (isSetActTypeText());
    list.add(present_actTypeText);
    if (present_actTypeText)
      list.add(actTypeText);

    return list.hashCode();
  }

  @Override
  public int compareTo(ActProduct other) {
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
    lastComparison = Boolean.valueOf(isSetActId()).compareTo(other.isSetActId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetActId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.actId, other.actId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProductId()).compareTo(other.isSetProductId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.productId, other.productId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetActType()).compareTo(other.isSetActType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetActType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.actType, other.actType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetActTypeText()).compareTo(other.isSetActTypeText());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetActTypeText()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.actTypeText, other.actTypeText);
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
    StringBuilder sb = new StringBuilder("ActProduct(");
    boolean first = true;

    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("actId:");
    if (this.actId == null) {
      sb.append("null");
    } else {
      sb.append(this.actId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("productId:");
    sb.append(this.productId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("actType:");
    sb.append(this.actType);
    first = false;
    if (!first) sb.append(", ");
    sb.append("actTypeText:");
    if (this.actTypeText == null) {
      sb.append("null");
    } else {
      sb.append(this.actTypeText);
    }
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

  private static class ActProductStandardSchemeFactory implements SchemeFactory {
    public ActProductStandardScheme getScheme() {
      return new ActProductStandardScheme();
    }
  }

  private static class ActProductStandardScheme extends StandardScheme<ActProduct> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ActProduct struct) throws org.apache.thrift.TException {
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
          case 2: // ACT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.actId = iprot.readString();
              struct.setActIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PRODUCT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.productId = iprot.readI32();
              struct.setProductIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ACT_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.actType = iprot.readI32();
              struct.setActTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // ACT_TYPE_TEXT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.actTypeText = iprot.readString();
              struct.setActTypeTextIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ActProduct struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PID_FIELD_DESC);
      oprot.writeI32(struct.pid);
      oprot.writeFieldEnd();
      if (struct.actId != null) {
        oprot.writeFieldBegin(ACT_ID_FIELD_DESC);
        oprot.writeString(struct.actId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(PRODUCT_ID_FIELD_DESC);
      oprot.writeI32(struct.productId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ACT_TYPE_FIELD_DESC);
      oprot.writeI32(struct.actType);
      oprot.writeFieldEnd();
      if (struct.actTypeText != null) {
        oprot.writeFieldBegin(ACT_TYPE_TEXT_FIELD_DESC);
        oprot.writeString(struct.actTypeText);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ActProductTupleSchemeFactory implements SchemeFactory {
    public ActProductTupleScheme getScheme() {
      return new ActProductTupleScheme();
    }
  }

  private static class ActProductTupleScheme extends TupleScheme<ActProduct> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ActProduct struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPid()) {
        optionals.set(0);
      }
      if (struct.isSetActId()) {
        optionals.set(1);
      }
      if (struct.isSetProductId()) {
        optionals.set(2);
      }
      if (struct.isSetActType()) {
        optionals.set(3);
      }
      if (struct.isSetActTypeText()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetPid()) {
        oprot.writeI32(struct.pid);
      }
      if (struct.isSetActId()) {
        oprot.writeString(struct.actId);
      }
      if (struct.isSetProductId()) {
        oprot.writeI32(struct.productId);
      }
      if (struct.isSetActType()) {
        oprot.writeI32(struct.actType);
      }
      if (struct.isSetActTypeText()) {
        oprot.writeString(struct.actTypeText);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ActProduct struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.pid = iprot.readI32();
        struct.setPidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.actId = iprot.readString();
        struct.setActIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.productId = iprot.readI32();
        struct.setProductIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.actType = iprot.readI32();
        struct.setActTypeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.actTypeText = iprot.readString();
        struct.setActTypeTextIsSet(true);
      }
    }
  }

}
