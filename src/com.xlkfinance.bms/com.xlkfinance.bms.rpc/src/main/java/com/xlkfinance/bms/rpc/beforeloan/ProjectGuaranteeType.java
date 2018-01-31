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
public class ProjectGuaranteeType implements org.apache.thrift.TBase<ProjectGuaranteeType, ProjectGuaranteeType._Fields>, java.io.Serializable, Cloneable, Comparable<ProjectGuaranteeType> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProjectGuaranteeType");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PROJECT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("projectId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField GUARANTEE_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("guaranteeType", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField GUARANTEE_TYPE_TEXT_FIELD_DESC = new org.apache.thrift.protocol.TField("guaranteeTypeText", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProjectGuaranteeTypeStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProjectGuaranteeTypeTupleSchemeFactory());
  }

  public int pid; // required
  public int projectId; // required
  public int guaranteeType; // required
  public int status; // required
  public String guaranteeTypeText; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    PROJECT_ID((short)2, "projectId"),
    GUARANTEE_TYPE((short)3, "guaranteeType"),
    STATUS((short)4, "status"),
    GUARANTEE_TYPE_TEXT((short)5, "guaranteeTypeText");

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
        case 3: // GUARANTEE_TYPE
          return GUARANTEE_TYPE;
        case 4: // STATUS
          return STATUS;
        case 5: // GUARANTEE_TYPE_TEXT
          return GUARANTEE_TYPE_TEXT;
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
  private static final int __GUARANTEETYPE_ISSET_ID = 2;
  private static final int __STATUS_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PROJECT_ID, new org.apache.thrift.meta_data.FieldMetaData("projectId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.GUARANTEE_TYPE, new org.apache.thrift.meta_data.FieldMetaData("guaranteeType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.GUARANTEE_TYPE_TEXT, new org.apache.thrift.meta_data.FieldMetaData("guaranteeTypeText", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProjectGuaranteeType.class, metaDataMap);
  }

  public ProjectGuaranteeType() {
  }

  public ProjectGuaranteeType(
    int pid,
    int projectId,
    int guaranteeType,
    int status,
    String guaranteeTypeText)
  {
    this();
    this.pid = pid;
    setPidIsSet(true);
    this.projectId = projectId;
    setProjectIdIsSet(true);
    this.guaranteeType = guaranteeType;
    setGuaranteeTypeIsSet(true);
    this.status = status;
    setStatusIsSet(true);
    this.guaranteeTypeText = guaranteeTypeText;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProjectGuaranteeType(ProjectGuaranteeType other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pid = other.pid;
    this.projectId = other.projectId;
    this.guaranteeType = other.guaranteeType;
    this.status = other.status;
    if (other.isSetGuaranteeTypeText()) {
      this.guaranteeTypeText = other.guaranteeTypeText;
    }
  }

  public ProjectGuaranteeType deepCopy() {
    return new ProjectGuaranteeType(this);
  }

  @Override
  public void clear() {
    setPidIsSet(false);
    this.pid = 0;
    setProjectIdIsSet(false);
    this.projectId = 0;
    setGuaranteeTypeIsSet(false);
    this.guaranteeType = 0;
    setStatusIsSet(false);
    this.status = 0;
    this.guaranteeTypeText = null;
  }

  public int getPid() {
    return this.pid;
  }

  public ProjectGuaranteeType setPid(int pid) {
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

  public ProjectGuaranteeType setProjectId(int projectId) {
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

  public int getGuaranteeType() {
    return this.guaranteeType;
  }

  public ProjectGuaranteeType setGuaranteeType(int guaranteeType) {
    this.guaranteeType = guaranteeType;
    setGuaranteeTypeIsSet(true);
    return this;
  }

  public void unsetGuaranteeType() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __GUARANTEETYPE_ISSET_ID);
  }

  /** Returns true if field guaranteeType is set (has been assigned a value) and false otherwise */
  public boolean isSetGuaranteeType() {
    return EncodingUtils.testBit(__isset_bitfield, __GUARANTEETYPE_ISSET_ID);
  }

  public void setGuaranteeTypeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __GUARANTEETYPE_ISSET_ID, value);
  }

  public int getStatus() {
    return this.status;
  }

  public ProjectGuaranteeType setStatus(int status) {
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

  public String getGuaranteeTypeText() {
    return this.guaranteeTypeText;
  }

  public ProjectGuaranteeType setGuaranteeTypeText(String guaranteeTypeText) {
    this.guaranteeTypeText = guaranteeTypeText;
    return this;
  }

  public void unsetGuaranteeTypeText() {
    this.guaranteeTypeText = null;
  }

  /** Returns true if field guaranteeTypeText is set (has been assigned a value) and false otherwise */
  public boolean isSetGuaranteeTypeText() {
    return this.guaranteeTypeText != null;
  }

  public void setGuaranteeTypeTextIsSet(boolean value) {
    if (!value) {
      this.guaranteeTypeText = null;
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

    case GUARANTEE_TYPE:
      if (value == null) {
        unsetGuaranteeType();
      } else {
        setGuaranteeType((Integer)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((Integer)value);
      }
      break;

    case GUARANTEE_TYPE_TEXT:
      if (value == null) {
        unsetGuaranteeTypeText();
      } else {
        setGuaranteeTypeText((String)value);
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

    case GUARANTEE_TYPE:
      return Integer.valueOf(getGuaranteeType());

    case STATUS:
      return Integer.valueOf(getStatus());

    case GUARANTEE_TYPE_TEXT:
      return getGuaranteeTypeText();

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
    case GUARANTEE_TYPE:
      return isSetGuaranteeType();
    case STATUS:
      return isSetStatus();
    case GUARANTEE_TYPE_TEXT:
      return isSetGuaranteeTypeText();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProjectGuaranteeType)
      return this.equals((ProjectGuaranteeType)that);
    return false;
  }

  public boolean equals(ProjectGuaranteeType that) {
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

    boolean this_present_guaranteeType = true;
    boolean that_present_guaranteeType = true;
    if (this_present_guaranteeType || that_present_guaranteeType) {
      if (!(this_present_guaranteeType && that_present_guaranteeType))
        return false;
      if (this.guaranteeType != that.guaranteeType)
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

    boolean this_present_guaranteeTypeText = true && this.isSetGuaranteeTypeText();
    boolean that_present_guaranteeTypeText = true && that.isSetGuaranteeTypeText();
    if (this_present_guaranteeTypeText || that_present_guaranteeTypeText) {
      if (!(this_present_guaranteeTypeText && that_present_guaranteeTypeText))
        return false;
      if (!this.guaranteeTypeText.equals(that.guaranteeTypeText))
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

    boolean present_guaranteeType = true;
    list.add(present_guaranteeType);
    if (present_guaranteeType)
      list.add(guaranteeType);

    boolean present_status = true;
    list.add(present_status);
    if (present_status)
      list.add(status);

    boolean present_guaranteeTypeText = true && (isSetGuaranteeTypeText());
    list.add(present_guaranteeTypeText);
    if (present_guaranteeTypeText)
      list.add(guaranteeTypeText);

    return list.hashCode();
  }

  @Override
  public int compareTo(ProjectGuaranteeType other) {
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
    lastComparison = Boolean.valueOf(isSetGuaranteeType()).compareTo(other.isSetGuaranteeType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGuaranteeType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.guaranteeType, other.guaranteeType);
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
    lastComparison = Boolean.valueOf(isSetGuaranteeTypeText()).compareTo(other.isSetGuaranteeTypeText());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGuaranteeTypeText()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.guaranteeTypeText, other.guaranteeTypeText);
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
    StringBuilder sb = new StringBuilder("ProjectGuaranteeType(");
    boolean first = true;

    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("projectId:");
    sb.append(this.projectId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("guaranteeType:");
    sb.append(this.guaranteeType);
    first = false;
    if (!first) sb.append(", ");
    sb.append("status:");
    sb.append(this.status);
    first = false;
    if (!first) sb.append(", ");
    sb.append("guaranteeTypeText:");
    if (this.guaranteeTypeText == null) {
      sb.append("null");
    } else {
      sb.append(this.guaranteeTypeText);
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

  private static class ProjectGuaranteeTypeStandardSchemeFactory implements SchemeFactory {
    public ProjectGuaranteeTypeStandardScheme getScheme() {
      return new ProjectGuaranteeTypeStandardScheme();
    }
  }

  private static class ProjectGuaranteeTypeStandardScheme extends StandardScheme<ProjectGuaranteeType> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProjectGuaranteeType struct) throws org.apache.thrift.TException {
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
          case 3: // GUARANTEE_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.guaranteeType = iprot.readI32();
              struct.setGuaranteeTypeIsSet(true);
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
          case 5: // GUARANTEE_TYPE_TEXT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.guaranteeTypeText = iprot.readString();
              struct.setGuaranteeTypeTextIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProjectGuaranteeType struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PID_FIELD_DESC);
      oprot.writeI32(struct.pid);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PROJECT_ID_FIELD_DESC);
      oprot.writeI32(struct.projectId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(GUARANTEE_TYPE_FIELD_DESC);
      oprot.writeI32(struct.guaranteeType);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(STATUS_FIELD_DESC);
      oprot.writeI32(struct.status);
      oprot.writeFieldEnd();
      if (struct.guaranteeTypeText != null) {
        oprot.writeFieldBegin(GUARANTEE_TYPE_TEXT_FIELD_DESC);
        oprot.writeString(struct.guaranteeTypeText);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProjectGuaranteeTypeTupleSchemeFactory implements SchemeFactory {
    public ProjectGuaranteeTypeTupleScheme getScheme() {
      return new ProjectGuaranteeTypeTupleScheme();
    }
  }

  private static class ProjectGuaranteeTypeTupleScheme extends TupleScheme<ProjectGuaranteeType> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProjectGuaranteeType struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPid()) {
        optionals.set(0);
      }
      if (struct.isSetProjectId()) {
        optionals.set(1);
      }
      if (struct.isSetGuaranteeType()) {
        optionals.set(2);
      }
      if (struct.isSetStatus()) {
        optionals.set(3);
      }
      if (struct.isSetGuaranteeTypeText()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetPid()) {
        oprot.writeI32(struct.pid);
      }
      if (struct.isSetProjectId()) {
        oprot.writeI32(struct.projectId);
      }
      if (struct.isSetGuaranteeType()) {
        oprot.writeI32(struct.guaranteeType);
      }
      if (struct.isSetStatus()) {
        oprot.writeI32(struct.status);
      }
      if (struct.isSetGuaranteeTypeText()) {
        oprot.writeString(struct.guaranteeTypeText);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProjectGuaranteeType struct) throws org.apache.thrift.TException {
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
        struct.guaranteeType = iprot.readI32();
        struct.setGuaranteeTypeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.status = iprot.readI32();
        struct.setStatusIsSet(true);
      }
      if (incoming.get(4)) {
        struct.guaranteeTypeText = iprot.readString();
        struct.setGuaranteeTypeTextIsSet(true);
      }
    }
  }

}

