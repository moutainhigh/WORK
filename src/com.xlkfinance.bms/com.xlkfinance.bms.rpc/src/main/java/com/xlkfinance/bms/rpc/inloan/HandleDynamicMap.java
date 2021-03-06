/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xlkfinance.bms.rpc.inloan;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2018-1-11")
public class HandleDynamicMap implements org.apache.thrift.TBase<HandleDynamicMap, HandleDynamicMap._Fields>, java.io.Serializable, Cloneable, Comparable<HandleDynamicMap> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HandleDynamicMap");

  private static final org.apache.thrift.protocol.TField COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("count", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField DYNAMIC_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("dynamicName", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new HandleDynamicMapStandardSchemeFactory());
    schemes.put(TupleScheme.class, new HandleDynamicMapTupleSchemeFactory());
  }

  public int count; // required
  public String dynamicName; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    COUNT((short)1, "count"),
    DYNAMIC_NAME((short)2, "dynamicName");

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
        case 1: // COUNT
          return COUNT;
        case 2: // DYNAMIC_NAME
          return DYNAMIC_NAME;
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
  private static final int __COUNT_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.COUNT, new org.apache.thrift.meta_data.FieldMetaData("count", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.DYNAMIC_NAME, new org.apache.thrift.meta_data.FieldMetaData("dynamicName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HandleDynamicMap.class, metaDataMap);
  }

  public HandleDynamicMap() {
  }

  public HandleDynamicMap(
    int count,
    String dynamicName)
  {
    this();
    this.count = count;
    setCountIsSet(true);
    this.dynamicName = dynamicName;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HandleDynamicMap(HandleDynamicMap other) {
    __isset_bitfield = other.__isset_bitfield;
    this.count = other.count;
    if (other.isSetDynamicName()) {
      this.dynamicName = other.dynamicName;
    }
  }

  public HandleDynamicMap deepCopy() {
    return new HandleDynamicMap(this);
  }

  @Override
  public void clear() {
    setCountIsSet(false);
    this.count = 0;
    this.dynamicName = null;
  }

  public int getCount() {
    return this.count;
  }

  public HandleDynamicMap setCount(int count) {
    this.count = count;
    setCountIsSet(true);
    return this;
  }

  public void unsetCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __COUNT_ISSET_ID);
  }

  /** Returns true if field count is set (has been assigned a value) and false otherwise */
  public boolean isSetCount() {
    return EncodingUtils.testBit(__isset_bitfield, __COUNT_ISSET_ID);
  }

  public void setCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __COUNT_ISSET_ID, value);
  }

  public String getDynamicName() {
    return this.dynamicName;
  }

  public HandleDynamicMap setDynamicName(String dynamicName) {
    this.dynamicName = dynamicName;
    return this;
  }

  public void unsetDynamicName() {
    this.dynamicName = null;
  }

  /** Returns true if field dynamicName is set (has been assigned a value) and false otherwise */
  public boolean isSetDynamicName() {
    return this.dynamicName != null;
  }

  public void setDynamicNameIsSet(boolean value) {
    if (!value) {
      this.dynamicName = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case COUNT:
      if (value == null) {
        unsetCount();
      } else {
        setCount((Integer)value);
      }
      break;

    case DYNAMIC_NAME:
      if (value == null) {
        unsetDynamicName();
      } else {
        setDynamicName((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case COUNT:
      return Integer.valueOf(getCount());

    case DYNAMIC_NAME:
      return getDynamicName();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case COUNT:
      return isSetCount();
    case DYNAMIC_NAME:
      return isSetDynamicName();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HandleDynamicMap)
      return this.equals((HandleDynamicMap)that);
    return false;
  }

  public boolean equals(HandleDynamicMap that) {
    if (that == null)
      return false;

    boolean this_present_count = true;
    boolean that_present_count = true;
    if (this_present_count || that_present_count) {
      if (!(this_present_count && that_present_count))
        return false;
      if (this.count != that.count)
        return false;
    }

    boolean this_present_dynamicName = true && this.isSetDynamicName();
    boolean that_present_dynamicName = true && that.isSetDynamicName();
    if (this_present_dynamicName || that_present_dynamicName) {
      if (!(this_present_dynamicName && that_present_dynamicName))
        return false;
      if (!this.dynamicName.equals(that.dynamicName))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_count = true;
    list.add(present_count);
    if (present_count)
      list.add(count);

    boolean present_dynamicName = true && (isSetDynamicName());
    list.add(present_dynamicName);
    if (present_dynamicName)
      list.add(dynamicName);

    return list.hashCode();
  }

  @Override
  public int compareTo(HandleDynamicMap other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCount()).compareTo(other.isSetCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.count, other.count);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDynamicName()).compareTo(other.isSetDynamicName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDynamicName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dynamicName, other.dynamicName);
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
    StringBuilder sb = new StringBuilder("HandleDynamicMap(");
    boolean first = true;

    sb.append("count:");
    sb.append(this.count);
    first = false;
    if (!first) sb.append(", ");
    sb.append("dynamicName:");
    if (this.dynamicName == null) {
      sb.append("null");
    } else {
      sb.append(this.dynamicName);
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

  private static class HandleDynamicMapStandardSchemeFactory implements SchemeFactory {
    public HandleDynamicMapStandardScheme getScheme() {
      return new HandleDynamicMapStandardScheme();
    }
  }

  private static class HandleDynamicMapStandardScheme extends StandardScheme<HandleDynamicMap> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HandleDynamicMap struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.count = iprot.readI32();
              struct.setCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DYNAMIC_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.dynamicName = iprot.readString();
              struct.setDynamicNameIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HandleDynamicMap struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(COUNT_FIELD_DESC);
      oprot.writeI32(struct.count);
      oprot.writeFieldEnd();
      if (struct.dynamicName != null) {
        oprot.writeFieldBegin(DYNAMIC_NAME_FIELD_DESC);
        oprot.writeString(struct.dynamicName);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HandleDynamicMapTupleSchemeFactory implements SchemeFactory {
    public HandleDynamicMapTupleScheme getScheme() {
      return new HandleDynamicMapTupleScheme();
    }
  }

  private static class HandleDynamicMapTupleScheme extends TupleScheme<HandleDynamicMap> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HandleDynamicMap struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetCount()) {
        optionals.set(0);
      }
      if (struct.isSetDynamicName()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetCount()) {
        oprot.writeI32(struct.count);
      }
      if (struct.isSetDynamicName()) {
        oprot.writeString(struct.dynamicName);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HandleDynamicMap struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.count = iprot.readI32();
        struct.setCountIsSet(true);
      }
      if (incoming.get(1)) {
        struct.dynamicName = iprot.readString();
        struct.setDynamicNameIsSet(true);
      }
    }
  }

}

