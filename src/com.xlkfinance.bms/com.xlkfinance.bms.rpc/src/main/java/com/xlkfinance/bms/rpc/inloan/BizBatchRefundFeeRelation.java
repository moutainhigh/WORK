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
public class BizBatchRefundFeeRelation implements org.apache.thrift.TBase<BizBatchRefundFeeRelation, BizBatchRefundFeeRelation._Fields>, java.io.Serializable, Cloneable, Comparable<BizBatchRefundFeeRelation> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BizBatchRefundFeeRelation");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField BATCH_REFUND_FEE_MAIN_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("batchRefundFeeMainId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField PROJECT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("projectId", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField REFUND_FEE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("refundFeeId", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BizBatchRefundFeeRelationStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BizBatchRefundFeeRelationTupleSchemeFactory());
  }

  public int pid; // required
  public int batchRefundFeeMainId; // required
  public int projectId; // required
  public int refundFeeId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    BATCH_REFUND_FEE_MAIN_ID((short)2, "batchRefundFeeMainId"),
    PROJECT_ID((short)3, "projectId"),
    REFUND_FEE_ID((short)4, "refundFeeId");

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
        case 2: // BATCH_REFUND_FEE_MAIN_ID
          return BATCH_REFUND_FEE_MAIN_ID;
        case 3: // PROJECT_ID
          return PROJECT_ID;
        case 4: // REFUND_FEE_ID
          return REFUND_FEE_ID;
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
  private static final int __BATCHREFUNDFEEMAINID_ISSET_ID = 1;
  private static final int __PROJECTID_ISSET_ID = 2;
  private static final int __REFUNDFEEID_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.BATCH_REFUND_FEE_MAIN_ID, new org.apache.thrift.meta_data.FieldMetaData("batchRefundFeeMainId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PROJECT_ID, new org.apache.thrift.meta_data.FieldMetaData("projectId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.REFUND_FEE_ID, new org.apache.thrift.meta_data.FieldMetaData("refundFeeId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BizBatchRefundFeeRelation.class, metaDataMap);
  }

  public BizBatchRefundFeeRelation() {
  }

  public BizBatchRefundFeeRelation(
    int pid,
    int batchRefundFeeMainId,
    int projectId,
    int refundFeeId)
  {
    this();
    this.pid = pid;
    setPidIsSet(true);
    this.batchRefundFeeMainId = batchRefundFeeMainId;
    setBatchRefundFeeMainIdIsSet(true);
    this.projectId = projectId;
    setProjectIdIsSet(true);
    this.refundFeeId = refundFeeId;
    setRefundFeeIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BizBatchRefundFeeRelation(BizBatchRefundFeeRelation other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pid = other.pid;
    this.batchRefundFeeMainId = other.batchRefundFeeMainId;
    this.projectId = other.projectId;
    this.refundFeeId = other.refundFeeId;
  }

  public BizBatchRefundFeeRelation deepCopy() {
    return new BizBatchRefundFeeRelation(this);
  }

  @Override
  public void clear() {
    setPidIsSet(false);
    this.pid = 0;
    setBatchRefundFeeMainIdIsSet(false);
    this.batchRefundFeeMainId = 0;
    setProjectIdIsSet(false);
    this.projectId = 0;
    setRefundFeeIdIsSet(false);
    this.refundFeeId = 0;
  }

  public int getPid() {
    return this.pid;
  }

  public BizBatchRefundFeeRelation setPid(int pid) {
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

  public int getBatchRefundFeeMainId() {
    return this.batchRefundFeeMainId;
  }

  public BizBatchRefundFeeRelation setBatchRefundFeeMainId(int batchRefundFeeMainId) {
    this.batchRefundFeeMainId = batchRefundFeeMainId;
    setBatchRefundFeeMainIdIsSet(true);
    return this;
  }

  public void unsetBatchRefundFeeMainId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __BATCHREFUNDFEEMAINID_ISSET_ID);
  }

  /** Returns true if field batchRefundFeeMainId is set (has been assigned a value) and false otherwise */
  public boolean isSetBatchRefundFeeMainId() {
    return EncodingUtils.testBit(__isset_bitfield, __BATCHREFUNDFEEMAINID_ISSET_ID);
  }

  public void setBatchRefundFeeMainIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __BATCHREFUNDFEEMAINID_ISSET_ID, value);
  }

  public int getProjectId() {
    return this.projectId;
  }

  public BizBatchRefundFeeRelation setProjectId(int projectId) {
    this.projectId = projectId;
    setProjectIdIsSet(true);
    return this;
  }

  public void unsetProjectId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PROJECTID_ISSET_ID);
  }

  /** Returns true if field projectId is set (has been assigned a value) and false otherwise */
  public boolean isSetProjectId() {
    return EncodingUtils.testBit(__isset_bitfield, __PROJECTID_ISSET_ID);
  }

  public void setProjectIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PROJECTID_ISSET_ID, value);
  }

  public int getRefundFeeId() {
    return this.refundFeeId;
  }

  public BizBatchRefundFeeRelation setRefundFeeId(int refundFeeId) {
    this.refundFeeId = refundFeeId;
    setRefundFeeIdIsSet(true);
    return this;
  }

  public void unsetRefundFeeId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __REFUNDFEEID_ISSET_ID);
  }

  /** Returns true if field refundFeeId is set (has been assigned a value) and false otherwise */
  public boolean isSetRefundFeeId() {
    return EncodingUtils.testBit(__isset_bitfield, __REFUNDFEEID_ISSET_ID);
  }

  public void setRefundFeeIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __REFUNDFEEID_ISSET_ID, value);
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

    case BATCH_REFUND_FEE_MAIN_ID:
      if (value == null) {
        unsetBatchRefundFeeMainId();
      } else {
        setBatchRefundFeeMainId((Integer)value);
      }
      break;

    case PROJECT_ID:
      if (value == null) {
        unsetProjectId();
      } else {
        setProjectId((Integer)value);
      }
      break;

    case REFUND_FEE_ID:
      if (value == null) {
        unsetRefundFeeId();
      } else {
        setRefundFeeId((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PID:
      return Integer.valueOf(getPid());

    case BATCH_REFUND_FEE_MAIN_ID:
      return Integer.valueOf(getBatchRefundFeeMainId());

    case PROJECT_ID:
      return Integer.valueOf(getProjectId());

    case REFUND_FEE_ID:
      return Integer.valueOf(getRefundFeeId());

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
    case BATCH_REFUND_FEE_MAIN_ID:
      return isSetBatchRefundFeeMainId();
    case PROJECT_ID:
      return isSetProjectId();
    case REFUND_FEE_ID:
      return isSetRefundFeeId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BizBatchRefundFeeRelation)
      return this.equals((BizBatchRefundFeeRelation)that);
    return false;
  }

  public boolean equals(BizBatchRefundFeeRelation that) {
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

    boolean this_present_batchRefundFeeMainId = true;
    boolean that_present_batchRefundFeeMainId = true;
    if (this_present_batchRefundFeeMainId || that_present_batchRefundFeeMainId) {
      if (!(this_present_batchRefundFeeMainId && that_present_batchRefundFeeMainId))
        return false;
      if (this.batchRefundFeeMainId != that.batchRefundFeeMainId)
        return false;
    }

    boolean this_present_projectId = true;
    boolean that_present_projectId = true;
    if (this_present_projectId || that_present_projectId) {
      if (!(this_present_projectId && that_present_projectId))
        return false;
      if (this.projectId != that.projectId)
        return false;
    }

    boolean this_present_refundFeeId = true;
    boolean that_present_refundFeeId = true;
    if (this_present_refundFeeId || that_present_refundFeeId) {
      if (!(this_present_refundFeeId && that_present_refundFeeId))
        return false;
      if (this.refundFeeId != that.refundFeeId)
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

    boolean present_batchRefundFeeMainId = true;
    list.add(present_batchRefundFeeMainId);
    if (present_batchRefundFeeMainId)
      list.add(batchRefundFeeMainId);

    boolean present_projectId = true;
    list.add(present_projectId);
    if (present_projectId)
      list.add(projectId);

    boolean present_refundFeeId = true;
    list.add(present_refundFeeId);
    if (present_refundFeeId)
      list.add(refundFeeId);

    return list.hashCode();
  }

  @Override
  public int compareTo(BizBatchRefundFeeRelation other) {
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
    lastComparison = Boolean.valueOf(isSetBatchRefundFeeMainId()).compareTo(other.isSetBatchRefundFeeMainId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBatchRefundFeeMainId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.batchRefundFeeMainId, other.batchRefundFeeMainId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProjectId()).compareTo(other.isSetProjectId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProjectId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.projectId, other.projectId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRefundFeeId()).compareTo(other.isSetRefundFeeId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRefundFeeId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.refundFeeId, other.refundFeeId);
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
    StringBuilder sb = new StringBuilder("BizBatchRefundFeeRelation(");
    boolean first = true;

    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("batchRefundFeeMainId:");
    sb.append(this.batchRefundFeeMainId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("projectId:");
    sb.append(this.projectId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("refundFeeId:");
    sb.append(this.refundFeeId);
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

  private static class BizBatchRefundFeeRelationStandardSchemeFactory implements SchemeFactory {
    public BizBatchRefundFeeRelationStandardScheme getScheme() {
      return new BizBatchRefundFeeRelationStandardScheme();
    }
  }

  private static class BizBatchRefundFeeRelationStandardScheme extends StandardScheme<BizBatchRefundFeeRelation> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BizBatchRefundFeeRelation struct) throws org.apache.thrift.TException {
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
          case 2: // BATCH_REFUND_FEE_MAIN_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.batchRefundFeeMainId = iprot.readI32();
              struct.setBatchRefundFeeMainIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PROJECT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.projectId = iprot.readI32();
              struct.setProjectIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // REFUND_FEE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.refundFeeId = iprot.readI32();
              struct.setRefundFeeIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BizBatchRefundFeeRelation struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PID_FIELD_DESC);
      oprot.writeI32(struct.pid);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(BATCH_REFUND_FEE_MAIN_ID_FIELD_DESC);
      oprot.writeI32(struct.batchRefundFeeMainId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PROJECT_ID_FIELD_DESC);
      oprot.writeI32(struct.projectId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(REFUND_FEE_ID_FIELD_DESC);
      oprot.writeI32(struct.refundFeeId);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BizBatchRefundFeeRelationTupleSchemeFactory implements SchemeFactory {
    public BizBatchRefundFeeRelationTupleScheme getScheme() {
      return new BizBatchRefundFeeRelationTupleScheme();
    }
  }

  private static class BizBatchRefundFeeRelationTupleScheme extends TupleScheme<BizBatchRefundFeeRelation> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BizBatchRefundFeeRelation struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPid()) {
        optionals.set(0);
      }
      if (struct.isSetBatchRefundFeeMainId()) {
        optionals.set(1);
      }
      if (struct.isSetProjectId()) {
        optionals.set(2);
      }
      if (struct.isSetRefundFeeId()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetPid()) {
        oprot.writeI32(struct.pid);
      }
      if (struct.isSetBatchRefundFeeMainId()) {
        oprot.writeI32(struct.batchRefundFeeMainId);
      }
      if (struct.isSetProjectId()) {
        oprot.writeI32(struct.projectId);
      }
      if (struct.isSetRefundFeeId()) {
        oprot.writeI32(struct.refundFeeId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BizBatchRefundFeeRelation struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.pid = iprot.readI32();
        struct.setPidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.batchRefundFeeMainId = iprot.readI32();
        struct.setBatchRefundFeeMainIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.projectId = iprot.readI32();
        struct.setProjectIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.refundFeeId = iprot.readI32();
        struct.setRefundFeeIdIsSet(true);
      }
    }
  }

}

