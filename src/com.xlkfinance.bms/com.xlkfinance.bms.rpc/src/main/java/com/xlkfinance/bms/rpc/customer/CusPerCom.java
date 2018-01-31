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
public class CusPerCom implements org.apache.thrift.TBase<CusPerCom, CusPerCom._Fields>, java.io.Serializable, Cloneable, Comparable<CusPerCom> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CusPerCom");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField CUS_PER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("cusPerId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField COM_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("comId", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CusPerComStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CusPerComTupleSchemeFactory());
  }

  public int pid; // required
  public int cusPerId; // required
  public int comId; // required
  public int status; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    CUS_PER_ID((short)2, "cusPerId"),
    COM_ID((short)3, "comId"),
    STATUS((short)4, "status");

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
        case 2: // CUS_PER_ID
          return CUS_PER_ID;
        case 3: // COM_ID
          return COM_ID;
        case 4: // STATUS
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
  private static final int __CUSPERID_ISSET_ID = 1;
  private static final int __COMID_ISSET_ID = 2;
  private static final int __STATUS_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CUS_PER_ID, new org.apache.thrift.meta_data.FieldMetaData("cusPerId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.COM_ID, new org.apache.thrift.meta_data.FieldMetaData("comId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CusPerCom.class, metaDataMap);
  }

  public CusPerCom() {
  }

  public CusPerCom(
    int pid,
    int cusPerId,
    int comId,
    int status)
  {
    this();
    this.pid = pid;
    setPidIsSet(true);
    this.cusPerId = cusPerId;
    setCusPerIdIsSet(true);
    this.comId = comId;
    setComIdIsSet(true);
    this.status = status;
    setStatusIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CusPerCom(CusPerCom other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pid = other.pid;
    this.cusPerId = other.cusPerId;
    this.comId = other.comId;
    this.status = other.status;
  }

  public CusPerCom deepCopy() {
    return new CusPerCom(this);
  }

  @Override
  public void clear() {
    setPidIsSet(false);
    this.pid = 0;
    setCusPerIdIsSet(false);
    this.cusPerId = 0;
    setComIdIsSet(false);
    this.comId = 0;
    setStatusIsSet(false);
    this.status = 0;
  }

  public int getPid() {
    return this.pid;
  }

  public CusPerCom setPid(int pid) {
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

  public int getCusPerId() {
    return this.cusPerId;
  }

  public CusPerCom setCusPerId(int cusPerId) {
    this.cusPerId = cusPerId;
    setCusPerIdIsSet(true);
    return this;
  }

  public void unsetCusPerId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CUSPERID_ISSET_ID);
  }

  /** Returns true if field cusPerId is set (has been assigned a value) and false otherwise */
  public boolean isSetCusPerId() {
    return EncodingUtils.testBit(__isset_bitfield, __CUSPERID_ISSET_ID);
  }

  public void setCusPerIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CUSPERID_ISSET_ID, value);
  }

  public int getComId() {
    return this.comId;
  }

  public CusPerCom setComId(int comId) {
    this.comId = comId;
    setComIdIsSet(true);
    return this;
  }

  public void unsetComId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __COMID_ISSET_ID);
  }

  /** Returns true if field comId is set (has been assigned a value) and false otherwise */
  public boolean isSetComId() {
    return EncodingUtils.testBit(__isset_bitfield, __COMID_ISSET_ID);
  }

  public void setComIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __COMID_ISSET_ID, value);
  }

  public int getStatus() {
    return this.status;
  }

  public CusPerCom setStatus(int status) {
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

    case CUS_PER_ID:
      if (value == null) {
        unsetCusPerId();
      } else {
        setCusPerId((Integer)value);
      }
      break;

    case COM_ID:
      if (value == null) {
        unsetComId();
      } else {
        setComId((Integer)value);
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

    case CUS_PER_ID:
      return Integer.valueOf(getCusPerId());

    case COM_ID:
      return Integer.valueOf(getComId());

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
    case CUS_PER_ID:
      return isSetCusPerId();
    case COM_ID:
      return isSetComId();
    case STATUS:
      return isSetStatus();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CusPerCom)
      return this.equals((CusPerCom)that);
    return false;
  }

  public boolean equals(CusPerCom that) {
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

    boolean this_present_cusPerId = true;
    boolean that_present_cusPerId = true;
    if (this_present_cusPerId || that_present_cusPerId) {
      if (!(this_present_cusPerId && that_present_cusPerId))
        return false;
      if (this.cusPerId != that.cusPerId)
        return false;
    }

    boolean this_present_comId = true;
    boolean that_present_comId = true;
    if (this_present_comId || that_present_comId) {
      if (!(this_present_comId && that_present_comId))
        return false;
      if (this.comId != that.comId)
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

    boolean present_cusPerId = true;
    list.add(present_cusPerId);
    if (present_cusPerId)
      list.add(cusPerId);

    boolean present_comId = true;
    list.add(present_comId);
    if (present_comId)
      list.add(comId);

    boolean present_status = true;
    list.add(present_status);
    if (present_status)
      list.add(status);

    return list.hashCode();
  }

  @Override
  public int compareTo(CusPerCom other) {
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
    lastComparison = Boolean.valueOf(isSetCusPerId()).compareTo(other.isSetCusPerId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCusPerId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cusPerId, other.cusPerId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetComId()).compareTo(other.isSetComId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetComId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.comId, other.comId);
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
    StringBuilder sb = new StringBuilder("CusPerCom(");
    boolean first = true;

    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("cusPerId:");
    sb.append(this.cusPerId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("comId:");
    sb.append(this.comId);
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

  private static class CusPerComStandardSchemeFactory implements SchemeFactory {
    public CusPerComStandardScheme getScheme() {
      return new CusPerComStandardScheme();
    }
  }

  private static class CusPerComStandardScheme extends StandardScheme<CusPerCom> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CusPerCom struct) throws org.apache.thrift.TException {
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
          case 2: // CUS_PER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.cusPerId = iprot.readI32();
              struct.setCusPerIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // COM_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.comId = iprot.readI32();
              struct.setComIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // STATUS
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CusPerCom struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PID_FIELD_DESC);
      oprot.writeI32(struct.pid);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(CUS_PER_ID_FIELD_DESC);
      oprot.writeI32(struct.cusPerId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(COM_ID_FIELD_DESC);
      oprot.writeI32(struct.comId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(STATUS_FIELD_DESC);
      oprot.writeI32(struct.status);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CusPerComTupleSchemeFactory implements SchemeFactory {
    public CusPerComTupleScheme getScheme() {
      return new CusPerComTupleScheme();
    }
  }

  private static class CusPerComTupleScheme extends TupleScheme<CusPerCom> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CusPerCom struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPid()) {
        optionals.set(0);
      }
      if (struct.isSetCusPerId()) {
        optionals.set(1);
      }
      if (struct.isSetComId()) {
        optionals.set(2);
      }
      if (struct.isSetStatus()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetPid()) {
        oprot.writeI32(struct.pid);
      }
      if (struct.isSetCusPerId()) {
        oprot.writeI32(struct.cusPerId);
      }
      if (struct.isSetComId()) {
        oprot.writeI32(struct.comId);
      }
      if (struct.isSetStatus()) {
        oprot.writeI32(struct.status);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CusPerCom struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.pid = iprot.readI32();
        struct.setPidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.cusPerId = iprot.readI32();
        struct.setCusPerIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.comId = iprot.readI32();
        struct.setComIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.status = iprot.readI32();
        struct.setStatusIsSet(true);
      }
    }
  }

}

