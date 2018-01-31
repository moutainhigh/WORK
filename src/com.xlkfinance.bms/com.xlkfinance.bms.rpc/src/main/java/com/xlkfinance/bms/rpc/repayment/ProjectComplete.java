/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xlkfinance.bms.rpc.repayment;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2017-12-19")
public class ProjectComplete implements org.apache.thrift.TBase<ProjectComplete, ProjectComplete._Fields>, java.io.Serializable, Cloneable, Comparable<ProjectComplete> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProjectComplete");

  private static final org.apache.thrift.protocol.TField P_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("pId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PROJECT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("projectId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField COMPLETE_DESC_FIELD_DESC = new org.apache.thrift.protocol.TField("completeDesc", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField COMPLETE_DTTM_FIELD_DESC = new org.apache.thrift.protocol.TField("completeDttm", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField IS_COMPLETE_FIELD_DESC = new org.apache.thrift.protocol.TField("is_complete", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProjectCompleteStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProjectCompleteTupleSchemeFactory());
  }

  public int pId; // required
  public int projectId; // required
  public String completeDesc; // required
  public String completeDttm; // required
  public String is_complete; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    P_ID((short)1, "pId"),
    PROJECT_ID((short)2, "projectId"),
    COMPLETE_DESC((short)3, "completeDesc"),
    COMPLETE_DTTM((short)4, "completeDttm"),
    IS_COMPLETE((short)5, "is_complete");

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
        case 1: // P_ID
          return P_ID;
        case 2: // PROJECT_ID
          return PROJECT_ID;
        case 3: // COMPLETE_DESC
          return COMPLETE_DESC;
        case 4: // COMPLETE_DTTM
          return COMPLETE_DTTM;
        case 5: // IS_COMPLETE
          return IS_COMPLETE;
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
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.P_ID, new org.apache.thrift.meta_data.FieldMetaData("pId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PROJECT_ID, new org.apache.thrift.meta_data.FieldMetaData("projectId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.COMPLETE_DESC, new org.apache.thrift.meta_data.FieldMetaData("completeDesc", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.COMPLETE_DTTM, new org.apache.thrift.meta_data.FieldMetaData("completeDttm", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.IS_COMPLETE, new org.apache.thrift.meta_data.FieldMetaData("is_complete", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProjectComplete.class, metaDataMap);
  }

  public ProjectComplete() {
  }

  public ProjectComplete(
    int pId,
    int projectId,
    String completeDesc,
    String completeDttm,
    String is_complete)
  {
    this();
    this.pId = pId;
    setPIdIsSet(true);
    this.projectId = projectId;
    setProjectIdIsSet(true);
    this.completeDesc = completeDesc;
    this.completeDttm = completeDttm;
    this.is_complete = is_complete;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProjectComplete(ProjectComplete other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pId = other.pId;
    this.projectId = other.projectId;
    if (other.isSetCompleteDesc()) {
      this.completeDesc = other.completeDesc;
    }
    if (other.isSetCompleteDttm()) {
      this.completeDttm = other.completeDttm;
    }
    if (other.isSetIs_complete()) {
      this.is_complete = other.is_complete;
    }
  }

  public ProjectComplete deepCopy() {
    return new ProjectComplete(this);
  }

  @Override
  public void clear() {
    setPIdIsSet(false);
    this.pId = 0;
    setProjectIdIsSet(false);
    this.projectId = 0;
    this.completeDesc = null;
    this.completeDttm = null;
    this.is_complete = null;
  }

  public int getPId() {
    return this.pId;
  }

  public ProjectComplete setPId(int pId) {
    this.pId = pId;
    setPIdIsSet(true);
    return this;
  }

  public void unsetPId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PID_ISSET_ID);
  }

  /** Returns true if field pId is set (has been assigned a value) and false otherwise */
  public boolean isSetPId() {
    return EncodingUtils.testBit(__isset_bitfield, __PID_ISSET_ID);
  }

  public void setPIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PID_ISSET_ID, value);
  }

  public int getProjectId() {
    return this.projectId;
  }

  public ProjectComplete setProjectId(int projectId) {
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

  public String getCompleteDesc() {
    return this.completeDesc;
  }

  public ProjectComplete setCompleteDesc(String completeDesc) {
    this.completeDesc = completeDesc;
    return this;
  }

  public void unsetCompleteDesc() {
    this.completeDesc = null;
  }

  /** Returns true if field completeDesc is set (has been assigned a value) and false otherwise */
  public boolean isSetCompleteDesc() {
    return this.completeDesc != null;
  }

  public void setCompleteDescIsSet(boolean value) {
    if (!value) {
      this.completeDesc = null;
    }
  }

  public String getCompleteDttm() {
    return this.completeDttm;
  }

  public ProjectComplete setCompleteDttm(String completeDttm) {
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

  public String getIs_complete() {
    return this.is_complete;
  }

  public ProjectComplete setIs_complete(String is_complete) {
    this.is_complete = is_complete;
    return this;
  }

  public void unsetIs_complete() {
    this.is_complete = null;
  }

  /** Returns true if field is_complete is set (has been assigned a value) and false otherwise */
  public boolean isSetIs_complete() {
    return this.is_complete != null;
  }

  public void setIs_completeIsSet(boolean value) {
    if (!value) {
      this.is_complete = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case P_ID:
      if (value == null) {
        unsetPId();
      } else {
        setPId((Integer)value);
      }
      break;

    case PROJECT_ID:
      if (value == null) {
        unsetProjectId();
      } else {
        setProjectId((Integer)value);
      }
      break;

    case COMPLETE_DESC:
      if (value == null) {
        unsetCompleteDesc();
      } else {
        setCompleteDesc((String)value);
      }
      break;

    case COMPLETE_DTTM:
      if (value == null) {
        unsetCompleteDttm();
      } else {
        setCompleteDttm((String)value);
      }
      break;

    case IS_COMPLETE:
      if (value == null) {
        unsetIs_complete();
      } else {
        setIs_complete((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case P_ID:
      return Integer.valueOf(getPId());

    case PROJECT_ID:
      return Integer.valueOf(getProjectId());

    case COMPLETE_DESC:
      return getCompleteDesc();

    case COMPLETE_DTTM:
      return getCompleteDttm();

    case IS_COMPLETE:
      return getIs_complete();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case P_ID:
      return isSetPId();
    case PROJECT_ID:
      return isSetProjectId();
    case COMPLETE_DESC:
      return isSetCompleteDesc();
    case COMPLETE_DTTM:
      return isSetCompleteDttm();
    case IS_COMPLETE:
      return isSetIs_complete();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProjectComplete)
      return this.equals((ProjectComplete)that);
    return false;
  }

  public boolean equals(ProjectComplete that) {
    if (that == null)
      return false;

    boolean this_present_pId = true;
    boolean that_present_pId = true;
    if (this_present_pId || that_present_pId) {
      if (!(this_present_pId && that_present_pId))
        return false;
      if (this.pId != that.pId)
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

    boolean this_present_completeDesc = true && this.isSetCompleteDesc();
    boolean that_present_completeDesc = true && that.isSetCompleteDesc();
    if (this_present_completeDesc || that_present_completeDesc) {
      if (!(this_present_completeDesc && that_present_completeDesc))
        return false;
      if (!this.completeDesc.equals(that.completeDesc))
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

    boolean this_present_is_complete = true && this.isSetIs_complete();
    boolean that_present_is_complete = true && that.isSetIs_complete();
    if (this_present_is_complete || that_present_is_complete) {
      if (!(this_present_is_complete && that_present_is_complete))
        return false;
      if (!this.is_complete.equals(that.is_complete))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_pId = true;
    list.add(present_pId);
    if (present_pId)
      list.add(pId);

    boolean present_projectId = true;
    list.add(present_projectId);
    if (present_projectId)
      list.add(projectId);

    boolean present_completeDesc = true && (isSetCompleteDesc());
    list.add(present_completeDesc);
    if (present_completeDesc)
      list.add(completeDesc);

    boolean present_completeDttm = true && (isSetCompleteDttm());
    list.add(present_completeDttm);
    if (present_completeDttm)
      list.add(completeDttm);

    boolean present_is_complete = true && (isSetIs_complete());
    list.add(present_is_complete);
    if (present_is_complete)
      list.add(is_complete);

    return list.hashCode();
  }

  @Override
  public int compareTo(ProjectComplete other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPId()).compareTo(other.isSetPId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pId, other.pId);
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
    lastComparison = Boolean.valueOf(isSetCompleteDesc()).compareTo(other.isSetCompleteDesc());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCompleteDesc()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.completeDesc, other.completeDesc);
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
    lastComparison = Boolean.valueOf(isSetIs_complete()).compareTo(other.isSetIs_complete());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIs_complete()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.is_complete, other.is_complete);
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
    StringBuilder sb = new StringBuilder("ProjectComplete(");
    boolean first = true;

    sb.append("pId:");
    sb.append(this.pId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("projectId:");
    sb.append(this.projectId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("completeDesc:");
    if (this.completeDesc == null) {
      sb.append("null");
    } else {
      sb.append(this.completeDesc);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("completeDttm:");
    if (this.completeDttm == null) {
      sb.append("null");
    } else {
      sb.append(this.completeDttm);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("is_complete:");
    if (this.is_complete == null) {
      sb.append("null");
    } else {
      sb.append(this.is_complete);
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

  private static class ProjectCompleteStandardSchemeFactory implements SchemeFactory {
    public ProjectCompleteStandardScheme getScheme() {
      return new ProjectCompleteStandardScheme();
    }
  }

  private static class ProjectCompleteStandardScheme extends StandardScheme<ProjectComplete> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProjectComplete struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // P_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.pId = iprot.readI32();
              struct.setPIdIsSet(true);
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
          case 3: // COMPLETE_DESC
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.completeDesc = iprot.readString();
              struct.setCompleteDescIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // COMPLETE_DTTM
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.completeDttm = iprot.readString();
              struct.setCompleteDttmIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // IS_COMPLETE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.is_complete = iprot.readString();
              struct.setIs_completeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProjectComplete struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(P_ID_FIELD_DESC);
      oprot.writeI32(struct.pId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PROJECT_ID_FIELD_DESC);
      oprot.writeI32(struct.projectId);
      oprot.writeFieldEnd();
      if (struct.completeDesc != null) {
        oprot.writeFieldBegin(COMPLETE_DESC_FIELD_DESC);
        oprot.writeString(struct.completeDesc);
        oprot.writeFieldEnd();
      }
      if (struct.completeDttm != null) {
        oprot.writeFieldBegin(COMPLETE_DTTM_FIELD_DESC);
        oprot.writeString(struct.completeDttm);
        oprot.writeFieldEnd();
      }
      if (struct.is_complete != null) {
        oprot.writeFieldBegin(IS_COMPLETE_FIELD_DESC);
        oprot.writeString(struct.is_complete);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProjectCompleteTupleSchemeFactory implements SchemeFactory {
    public ProjectCompleteTupleScheme getScheme() {
      return new ProjectCompleteTupleScheme();
    }
  }

  private static class ProjectCompleteTupleScheme extends TupleScheme<ProjectComplete> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProjectComplete struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPId()) {
        optionals.set(0);
      }
      if (struct.isSetProjectId()) {
        optionals.set(1);
      }
      if (struct.isSetCompleteDesc()) {
        optionals.set(2);
      }
      if (struct.isSetCompleteDttm()) {
        optionals.set(3);
      }
      if (struct.isSetIs_complete()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetPId()) {
        oprot.writeI32(struct.pId);
      }
      if (struct.isSetProjectId()) {
        oprot.writeI32(struct.projectId);
      }
      if (struct.isSetCompleteDesc()) {
        oprot.writeString(struct.completeDesc);
      }
      if (struct.isSetCompleteDttm()) {
        oprot.writeString(struct.completeDttm);
      }
      if (struct.isSetIs_complete()) {
        oprot.writeString(struct.is_complete);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProjectComplete struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.pId = iprot.readI32();
        struct.setPIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.projectId = iprot.readI32();
        struct.setProjectIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.completeDesc = iprot.readString();
        struct.setCompleteDescIsSet(true);
      }
      if (incoming.get(3)) {
        struct.completeDttm = iprot.readString();
        struct.setCompleteDttmIsSet(true);
      }
      if (incoming.get(4)) {
        struct.is_complete = iprot.readString();
        struct.setIs_completeIsSet(true);
      }
    }
  }

}
