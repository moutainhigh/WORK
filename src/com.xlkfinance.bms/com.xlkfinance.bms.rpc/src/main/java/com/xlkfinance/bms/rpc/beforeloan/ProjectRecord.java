/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xlkfinance.bms.rpc.beforeloan;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2017-12-21")
public class ProjectRecord implements org.apache.thrift.TBase<ProjectRecord, ProjectRecord._Fields>, java.io.Serializable, Cloneable, Comparable<ProjectRecord> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProjectRecord");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PROJECT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("projectId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField PROCESS_USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("processUserId", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField PROCESS_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("processType", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField COMPLETE_DTTM_FIELD_DESC = new org.apache.thrift.protocol.TField("completeDttm", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProjectRecordStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProjectRecordTupleSchemeFactory());
  }

  public int pid; // required
  public int projectId; // required
  public int processUserId; // required
  public int processType; // required
  public String completeDttm; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    PROJECT_ID((short)2, "projectId"),
    PROCESS_USER_ID((short)3, "processUserId"),
    PROCESS_TYPE((short)4, "processType"),
    COMPLETE_DTTM((short)5, "completeDttm");

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
        case 2: // PROJECT_ID
          return PROJECT_ID;
        case 3: // PROCESS_USER_ID
          return PROCESS_USER_ID;
        case 4: // PROCESS_TYPE
          return PROCESS_TYPE;
        case 5: // COMPLETE_DTTM
          return COMPLETE_DTTM;
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
  private static final int __PROJECTID_ISSET_ID = 1;
  private static final int __PROCESSUSERID_ISSET_ID = 2;
  private static final int __PROCESSTYPE_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PROJECT_ID, new org.apache.thrift.meta_data.FieldMetaData("projectId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PROCESS_USER_ID, new org.apache.thrift.meta_data.FieldMetaData("processUserId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PROCESS_TYPE, new org.apache.thrift.meta_data.FieldMetaData("processType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.COMPLETE_DTTM, new org.apache.thrift.meta_data.FieldMetaData("completeDttm", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProjectRecord.class, metaDataMap);
  }

  public ProjectRecord() {
  }

  public ProjectRecord(
    int pid,
    int projectId,
    int processUserId,
    int processType,
    String completeDttm)
  {
    this();
    this.pid = pid;
    setPidIsSet(true);
    this.projectId = projectId;
    setProjectIdIsSet(true);
    this.processUserId = processUserId;
    setProcessUserIdIsSet(true);
    this.processType = processType;
    setProcessTypeIsSet(true);
    this.completeDttm = completeDttm;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProjectRecord(ProjectRecord other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pid = other.pid;
    this.projectId = other.projectId;
    this.processUserId = other.processUserId;
    this.processType = other.processType;
    if (other.isSetCompleteDttm()) {
      this.completeDttm = other.completeDttm;
    }
  }

  public ProjectRecord deepCopy() {
    return new ProjectRecord(this);
  }

  @Override
  public void clear() {
    setPidIsSet(false);
    this.pid = 0;
    setProjectIdIsSet(false);
    this.projectId = 0;
    setProcessUserIdIsSet(false);
    this.processUserId = 0;
    setProcessTypeIsSet(false);
    this.processType = 0;
    this.completeDttm = null;
  }

  public int getPid() {
    return this.pid;
  }

  public ProjectRecord setPid(int pid) {
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

  public int getProjectId() {
    return this.projectId;
  }

  public ProjectRecord setProjectId(int projectId) {
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

  public int getProcessUserId() {
    return this.processUserId;
  }

  public ProjectRecord setProcessUserId(int processUserId) {
    this.processUserId = processUserId;
    setProcessUserIdIsSet(true);
    return this;
  }

  public void unsetProcessUserId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PROCESSUSERID_ISSET_ID);
  }

  /** Returns true if field processUserId is set (has been assigned a value) and false otherwise */
  public boolean isSetProcessUserId() {
    return EncodingUtils.testBit(__isset_bitfield, __PROCESSUSERID_ISSET_ID);
  }

  public void setProcessUserIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PROCESSUSERID_ISSET_ID, value);
  }

  public int getProcessType() {
    return this.processType;
  }

  public ProjectRecord setProcessType(int processType) {
    this.processType = processType;
    setProcessTypeIsSet(true);
    return this;
  }

  public void unsetProcessType() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PROCESSTYPE_ISSET_ID);
  }

  /** Returns true if field processType is set (has been assigned a value) and false otherwise */
  public boolean isSetProcessType() {
    return EncodingUtils.testBit(__isset_bitfield, __PROCESSTYPE_ISSET_ID);
  }

  public void setProcessTypeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PROCESSTYPE_ISSET_ID, value);
  }

  public String getCompleteDttm() {
    return this.completeDttm;
  }

  public ProjectRecord setCompleteDttm(String completeDttm) {
    this.completeDttm = completeDttm;
    return this;
  }

  public void unsetCompleteDttm() {
    this.completeDttm = null;
  }

  /** Returns true if field completeDttm is set (has been assigned a value) and false otherwise */
  public boolean isSetCompleteDttm() {
    return this.completeDttm != null;
  }

  public void setCompleteDttmIsSet(boolean value) {
    if (!value) {
      this.completeDttm = null;
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

    case PROJECT_ID:
      if (value == null) {
        unsetProjectId();
      } else {
        setProjectId((Integer)value);
      }
      break;

    case PROCESS_USER_ID:
      if (value == null) {
        unsetProcessUserId();
      } else {
        setProcessUserId((Integer)value);
      }
      break;

    case PROCESS_TYPE:
      if (value == null) {
        unsetProcessType();
      } else {
        setProcessType((Integer)value);
      }
      break;

    case COMPLETE_DTTM:
      if (value == null) {
        unsetCompleteDttm();
      } else {
        setCompleteDttm((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PID:
      return Integer.valueOf(getPid());

    case PROJECT_ID:
      return Integer.valueOf(getProjectId());

    case PROCESS_USER_ID:
      return Integer.valueOf(getProcessUserId());

    case PROCESS_TYPE:
      return Integer.valueOf(getProcessType());

    case COMPLETE_DTTM:
      return getCompleteDttm();

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
    case PROJECT_ID:
      return isSetProjectId();
    case PROCESS_USER_ID:
      return isSetProcessUserId();
    case PROCESS_TYPE:
      return isSetProcessType();
    case COMPLETE_DTTM:
      return isSetCompleteDttm();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProjectRecord)
      return this.equals((ProjectRecord)that);
    return false;
  }

  public boolean equals(ProjectRecord that) {
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

    boolean this_present_projectId = true;
    boolean that_present_projectId = true;
    if (this_present_projectId || that_present_projectId) {
      if (!(this_present_projectId && that_present_projectId))
        return false;
      if (this.projectId != that.projectId)
        return false;
    }

    boolean this_present_processUserId = true;
    boolean that_present_processUserId = true;
    if (this_present_processUserId || that_present_processUserId) {
      if (!(this_present_processUserId && that_present_processUserId))
        return false;
      if (this.processUserId != that.processUserId)
        return false;
    }

    boolean this_present_processType = true;
    boolean that_present_processType = true;
    if (this_present_processType || that_present_processType) {
      if (!(this_present_processType && that_present_processType))
        return false;
      if (this.processType != that.processType)
        return false;
    }

    boolean this_present_completeDttm = true && this.isSetCompleteDttm();
    boolean that_present_completeDttm = true && that.isSetCompleteDttm();
    if (this_present_completeDttm || that_present_completeDttm) {
      if (!(this_present_completeDttm && that_present_completeDttm))
        return false;
      if (!this.completeDttm.equals(that.completeDttm))
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

    boolean present_projectId = true;
    list.add(present_projectId);
    if (present_projectId)
      list.add(projectId);

    boolean present_processUserId = true;
    list.add(present_processUserId);
    if (present_processUserId)
      list.add(processUserId);

    boolean present_processType = true;
    list.add(present_processType);
    if (present_processType)
      list.add(processType);

    boolean present_completeDttm = true && (isSetCompleteDttm());
    list.add(present_completeDttm);
    if (present_completeDttm)
      list.add(completeDttm);

    return list.hashCode();
  }

  @Override
  public int compareTo(ProjectRecord other) {
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
    lastComparison = Boolean.valueOf(isSetProcessUserId()).compareTo(other.isSetProcessUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProcessUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.processUserId, other.processUserId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProcessType()).compareTo(other.isSetProcessType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProcessType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.processType, other.processType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCompleteDttm()).compareTo(other.isSetCompleteDttm());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCompleteDttm()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.completeDttm, other.completeDttm);
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
    StringBuilder sb = new StringBuilder("ProjectRecord(");
    boolean first = true;

    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("projectId:");
    sb.append(this.projectId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("processUserId:");
    sb.append(this.processUserId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("processType:");
    sb.append(this.processType);
    first = false;
    if (!first) sb.append(", ");
    sb.append("completeDttm:");
    if (this.completeDttm == null) {
      sb.append("null");
    } else {
      sb.append(this.completeDttm);
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

  private static class ProjectRecordStandardSchemeFactory implements SchemeFactory {
    public ProjectRecordStandardScheme getScheme() {
      return new ProjectRecordStandardScheme();
    }
  }

  private static class ProjectRecordStandardScheme extends StandardScheme<ProjectRecord> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProjectRecord struct) throws org.apache.thrift.TException {
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
          case 2: // PROJECT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.projectId = iprot.readI32();
              struct.setProjectIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PROCESS_USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.processUserId = iprot.readI32();
              struct.setProcessUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PROCESS_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.processType = iprot.readI32();
              struct.setProcessTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // COMPLETE_DTTM
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.completeDttm = iprot.readString();
              struct.setCompleteDttmIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProjectRecord struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PID_FIELD_DESC);
      oprot.writeI32(struct.pid);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PROJECT_ID_FIELD_DESC);
      oprot.writeI32(struct.projectId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PROCESS_USER_ID_FIELD_DESC);
      oprot.writeI32(struct.processUserId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PROCESS_TYPE_FIELD_DESC);
      oprot.writeI32(struct.processType);
      oprot.writeFieldEnd();
      if (struct.completeDttm != null) {
        oprot.writeFieldBegin(COMPLETE_DTTM_FIELD_DESC);
        oprot.writeString(struct.completeDttm);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProjectRecordTupleSchemeFactory implements SchemeFactory {
    public ProjectRecordTupleScheme getScheme() {
      return new ProjectRecordTupleScheme();
    }
  }

  private static class ProjectRecordTupleScheme extends TupleScheme<ProjectRecord> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProjectRecord struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPid()) {
        optionals.set(0);
      }
      if (struct.isSetProjectId()) {
        optionals.set(1);
      }
      if (struct.isSetProcessUserId()) {
        optionals.set(2);
      }
      if (struct.isSetProcessType()) {
        optionals.set(3);
      }
      if (struct.isSetCompleteDttm()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetPid()) {
        oprot.writeI32(struct.pid);
      }
      if (struct.isSetProjectId()) {
        oprot.writeI32(struct.projectId);
      }
      if (struct.isSetProcessUserId()) {
        oprot.writeI32(struct.processUserId);
      }
      if (struct.isSetProcessType()) {
        oprot.writeI32(struct.processType);
      }
      if (struct.isSetCompleteDttm()) {
        oprot.writeString(struct.completeDttm);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProjectRecord struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.pid = iprot.readI32();
        struct.setPidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.projectId = iprot.readI32();
        struct.setProjectIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.processUserId = iprot.readI32();
        struct.setProcessUserIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.processType = iprot.readI32();
        struct.setProcessTypeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.completeDttm = iprot.readString();
        struct.setCompleteDttmIsSet(true);
      }
    }
  }

}

