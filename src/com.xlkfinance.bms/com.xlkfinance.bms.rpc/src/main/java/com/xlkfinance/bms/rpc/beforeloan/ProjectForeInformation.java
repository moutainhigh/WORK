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
public class ProjectForeInformation implements org.apache.thrift.TBase<ProjectForeInformation, ProjectForeInformation._Fields>, java.io.Serializable, Cloneable, Comparable<ProjectForeInformation> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProjectForeInformation");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PROJECT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("projectId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField FORE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("foreId", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField ORIGINAL_NUMBER_FIELD_DESC = new org.apache.thrift.protocol.TField("originalNumber", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField COPY_NUMBER_FIELD_DESC = new org.apache.thrift.protocol.TField("copyNumber", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField REMARK_FIELD_DESC = new org.apache.thrift.protocol.TField("remark", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField FORE_INFORMATION_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("foreInformationName", org.apache.thrift.protocol.TType.STRING, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProjectForeInformationStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProjectForeInformationTupleSchemeFactory());
  }

  public int pid; // required
  public int projectId; // required
  public int foreId; // required
  public int originalNumber; // required
  public int copyNumber; // required
  public String remark; // required
  public String foreInformationName; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    PROJECT_ID((short)2, "projectId"),
    FORE_ID((short)3, "foreId"),
    ORIGINAL_NUMBER((short)4, "originalNumber"),
    COPY_NUMBER((short)5, "copyNumber"),
    REMARK((short)6, "remark"),
    FORE_INFORMATION_NAME((short)7, "foreInformationName");

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
        case 3: // FORE_ID
          return FORE_ID;
        case 4: // ORIGINAL_NUMBER
          return ORIGINAL_NUMBER;
        case 5: // COPY_NUMBER
          return COPY_NUMBER;
        case 6: // REMARK
          return REMARK;
        case 7: // FORE_INFORMATION_NAME
          return FORE_INFORMATION_NAME;
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
  private static final int __FOREID_ISSET_ID = 2;
  private static final int __ORIGINALNUMBER_ISSET_ID = 3;
  private static final int __COPYNUMBER_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PROJECT_ID, new org.apache.thrift.meta_data.FieldMetaData("projectId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.FORE_ID, new org.apache.thrift.meta_data.FieldMetaData("foreId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ORIGINAL_NUMBER, new org.apache.thrift.meta_data.FieldMetaData("originalNumber", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.COPY_NUMBER, new org.apache.thrift.meta_data.FieldMetaData("copyNumber", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.REMARK, new org.apache.thrift.meta_data.FieldMetaData("remark", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FORE_INFORMATION_NAME, new org.apache.thrift.meta_data.FieldMetaData("foreInformationName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProjectForeInformation.class, metaDataMap);
  }

  public ProjectForeInformation() {
  }

  public ProjectForeInformation(
    int pid,
    int projectId,
    int foreId,
    int originalNumber,
    int copyNumber,
    String remark,
    String foreInformationName)
  {
    this();
    this.pid = pid;
    setPidIsSet(true);
    this.projectId = projectId;
    setProjectIdIsSet(true);
    this.foreId = foreId;
    setForeIdIsSet(true);
    this.originalNumber = originalNumber;
    setOriginalNumberIsSet(true);
    this.copyNumber = copyNumber;
    setCopyNumberIsSet(true);
    this.remark = remark;
    this.foreInformationName = foreInformationName;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProjectForeInformation(ProjectForeInformation other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pid = other.pid;
    this.projectId = other.projectId;
    this.foreId = other.foreId;
    this.originalNumber = other.originalNumber;
    this.copyNumber = other.copyNumber;
    if (other.isSetRemark()) {
      this.remark = other.remark;
    }
    if (other.isSetForeInformationName()) {
      this.foreInformationName = other.foreInformationName;
    }
  }

  public ProjectForeInformation deepCopy() {
    return new ProjectForeInformation(this);
  }

  @Override
  public void clear() {
    setPidIsSet(false);
    this.pid = 0;
    setProjectIdIsSet(false);
    this.projectId = 0;
    setForeIdIsSet(false);
    this.foreId = 0;
    setOriginalNumberIsSet(false);
    this.originalNumber = 0;
    setCopyNumberIsSet(false);
    this.copyNumber = 0;
    this.remark = null;
    this.foreInformationName = null;
  }

  public int getPid() {
    return this.pid;
  }

  public ProjectForeInformation setPid(int pid) {
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

  public ProjectForeInformation setProjectId(int projectId) {
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

  public int getForeId() {
    return this.foreId;
  }

  public ProjectForeInformation setForeId(int foreId) {
    this.foreId = foreId;
    setForeIdIsSet(true);
    return this;
  }

  public void unsetForeId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __FOREID_ISSET_ID);
  }

  /** Returns true if field foreId is set (has been assigned a value) and false otherwise */
  public boolean isSetForeId() {
    return EncodingUtils.testBit(__isset_bitfield, __FOREID_ISSET_ID);
  }

  public void setForeIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __FOREID_ISSET_ID, value);
  }

  public int getOriginalNumber() {
    return this.originalNumber;
  }

  public ProjectForeInformation setOriginalNumber(int originalNumber) {
    this.originalNumber = originalNumber;
    setOriginalNumberIsSet(true);
    return this;
  }

  public void unsetOriginalNumber() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ORIGINALNUMBER_ISSET_ID);
  }

  /** Returns true if field originalNumber is set (has been assigned a value) and false otherwise */
  public boolean isSetOriginalNumber() {
    return EncodingUtils.testBit(__isset_bitfield, __ORIGINALNUMBER_ISSET_ID);
  }

  public void setOriginalNumberIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ORIGINALNUMBER_ISSET_ID, value);
  }

  public int getCopyNumber() {
    return this.copyNumber;
  }

  public ProjectForeInformation setCopyNumber(int copyNumber) {
    this.copyNumber = copyNumber;
    setCopyNumberIsSet(true);
    return this;
  }

  public void unsetCopyNumber() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __COPYNUMBER_ISSET_ID);
  }

  /** Returns true if field copyNumber is set (has been assigned a value) and false otherwise */
  public boolean isSetCopyNumber() {
    return EncodingUtils.testBit(__isset_bitfield, __COPYNUMBER_ISSET_ID);
  }

  public void setCopyNumberIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __COPYNUMBER_ISSET_ID, value);
  }

  public String getRemark() {
    return this.remark;
  }

  public ProjectForeInformation setRemark(String remark) {
    this.remark = remark;
    return this;
  }

  public void unsetRemark() {
    this.remark = null;
  }

  /** Returns true if field remark is set (has been assigned a value) and false otherwise */
  public boolean isSetRemark() {
    return this.remark != null;
  }

  public void setRemarkIsSet(boolean value) {
    if (!value) {
      this.remark = null;
    }
  }

  public String getForeInformationName() {
    return this.foreInformationName;
  }

  public ProjectForeInformation setForeInformationName(String foreInformationName) {
    this.foreInformationName = foreInformationName;
    return this;
  }

  public void unsetForeInformationName() {
    this.foreInformationName = null;
  }

  /** Returns true if field foreInformationName is set (has been assigned a value) and false otherwise */
  public boolean isSetForeInformationName() {
    return this.foreInformationName != null;
  }

  public void setForeInformationNameIsSet(boolean value) {
    if (!value) {
      this.foreInformationName = null;
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

    case FORE_ID:
      if (value == null) {
        unsetForeId();
      } else {
        setForeId((Integer)value);
      }
      break;

    case ORIGINAL_NUMBER:
      if (value == null) {
        unsetOriginalNumber();
      } else {
        setOriginalNumber((Integer)value);
      }
      break;

    case COPY_NUMBER:
      if (value == null) {
        unsetCopyNumber();
      } else {
        setCopyNumber((Integer)value);
      }
      break;

    case REMARK:
      if (value == null) {
        unsetRemark();
      } else {
        setRemark((String)value);
      }
      break;

    case FORE_INFORMATION_NAME:
      if (value == null) {
        unsetForeInformationName();
      } else {
        setForeInformationName((String)value);
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

    case FORE_ID:
      return Integer.valueOf(getForeId());

    case ORIGINAL_NUMBER:
      return Integer.valueOf(getOriginalNumber());

    case COPY_NUMBER:
      return Integer.valueOf(getCopyNumber());

    case REMARK:
      return getRemark();

    case FORE_INFORMATION_NAME:
      return getForeInformationName();

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
    case FORE_ID:
      return isSetForeId();
    case ORIGINAL_NUMBER:
      return isSetOriginalNumber();
    case COPY_NUMBER:
      return isSetCopyNumber();
    case REMARK:
      return isSetRemark();
    case FORE_INFORMATION_NAME:
      return isSetForeInformationName();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProjectForeInformation)
      return this.equals((ProjectForeInformation)that);
    return false;
  }

  public boolean equals(ProjectForeInformation that) {
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

    boolean this_present_foreId = true;
    boolean that_present_foreId = true;
    if (this_present_foreId || that_present_foreId) {
      if (!(this_present_foreId && that_present_foreId))
        return false;
      if (this.foreId != that.foreId)
        return false;
    }

    boolean this_present_originalNumber = true;
    boolean that_present_originalNumber = true;
    if (this_present_originalNumber || that_present_originalNumber) {
      if (!(this_present_originalNumber && that_present_originalNumber))
        return false;
      if (this.originalNumber != that.originalNumber)
        return false;
    }

    boolean this_present_copyNumber = true;
    boolean that_present_copyNumber = true;
    if (this_present_copyNumber || that_present_copyNumber) {
      if (!(this_present_copyNumber && that_present_copyNumber))
        return false;
      if (this.copyNumber != that.copyNumber)
        return false;
    }

    boolean this_present_remark = true && this.isSetRemark();
    boolean that_present_remark = true && that.isSetRemark();
    if (this_present_remark || that_present_remark) {
      if (!(this_present_remark && that_present_remark))
        return false;
      if (!this.remark.equals(that.remark))
        return false;
    }

    boolean this_present_foreInformationName = true && this.isSetForeInformationName();
    boolean that_present_foreInformationName = true && that.isSetForeInformationName();
    if (this_present_foreInformationName || that_present_foreInformationName) {
      if (!(this_present_foreInformationName && that_present_foreInformationName))
        return false;
      if (!this.foreInformationName.equals(that.foreInformationName))
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

    boolean present_foreId = true;
    list.add(present_foreId);
    if (present_foreId)
      list.add(foreId);

    boolean present_originalNumber = true;
    list.add(present_originalNumber);
    if (present_originalNumber)
      list.add(originalNumber);

    boolean present_copyNumber = true;
    list.add(present_copyNumber);
    if (present_copyNumber)
      list.add(copyNumber);

    boolean present_remark = true && (isSetRemark());
    list.add(present_remark);
    if (present_remark)
      list.add(remark);

    boolean present_foreInformationName = true && (isSetForeInformationName());
    list.add(present_foreInformationName);
    if (present_foreInformationName)
      list.add(foreInformationName);

    return list.hashCode();
  }

  @Override
  public int compareTo(ProjectForeInformation other) {
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
    lastComparison = Boolean.valueOf(isSetForeId()).compareTo(other.isSetForeId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetForeId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.foreId, other.foreId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOriginalNumber()).compareTo(other.isSetOriginalNumber());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOriginalNumber()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.originalNumber, other.originalNumber);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCopyNumber()).compareTo(other.isSetCopyNumber());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCopyNumber()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.copyNumber, other.copyNumber);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRemark()).compareTo(other.isSetRemark());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRemark()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.remark, other.remark);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetForeInformationName()).compareTo(other.isSetForeInformationName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetForeInformationName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.foreInformationName, other.foreInformationName);
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
    StringBuilder sb = new StringBuilder("ProjectForeInformation(");
    boolean first = true;

    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("projectId:");
    sb.append(this.projectId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("foreId:");
    sb.append(this.foreId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("originalNumber:");
    sb.append(this.originalNumber);
    first = false;
    if (!first) sb.append(", ");
    sb.append("copyNumber:");
    sb.append(this.copyNumber);
    first = false;
    if (!first) sb.append(", ");
    sb.append("remark:");
    if (this.remark == null) {
      sb.append("null");
    } else {
      sb.append(this.remark);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("foreInformationName:");
    if (this.foreInformationName == null) {
      sb.append("null");
    } else {
      sb.append(this.foreInformationName);
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

  private static class ProjectForeInformationStandardSchemeFactory implements SchemeFactory {
    public ProjectForeInformationStandardScheme getScheme() {
      return new ProjectForeInformationStandardScheme();
    }
  }

  private static class ProjectForeInformationStandardScheme extends StandardScheme<ProjectForeInformation> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProjectForeInformation struct) throws org.apache.thrift.TException {
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
          case 3: // FORE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.foreId = iprot.readI32();
              struct.setForeIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ORIGINAL_NUMBER
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.originalNumber = iprot.readI32();
              struct.setOriginalNumberIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // COPY_NUMBER
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.copyNumber = iprot.readI32();
              struct.setCopyNumberIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // REMARK
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.remark = iprot.readString();
              struct.setRemarkIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // FORE_INFORMATION_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.foreInformationName = iprot.readString();
              struct.setForeInformationNameIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProjectForeInformation struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PID_FIELD_DESC);
      oprot.writeI32(struct.pid);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PROJECT_ID_FIELD_DESC);
      oprot.writeI32(struct.projectId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(FORE_ID_FIELD_DESC);
      oprot.writeI32(struct.foreId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ORIGINAL_NUMBER_FIELD_DESC);
      oprot.writeI32(struct.originalNumber);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(COPY_NUMBER_FIELD_DESC);
      oprot.writeI32(struct.copyNumber);
      oprot.writeFieldEnd();
      if (struct.remark != null) {
        oprot.writeFieldBegin(REMARK_FIELD_DESC);
        oprot.writeString(struct.remark);
        oprot.writeFieldEnd();
      }
      if (struct.foreInformationName != null) {
        oprot.writeFieldBegin(FORE_INFORMATION_NAME_FIELD_DESC);
        oprot.writeString(struct.foreInformationName);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProjectForeInformationTupleSchemeFactory implements SchemeFactory {
    public ProjectForeInformationTupleScheme getScheme() {
      return new ProjectForeInformationTupleScheme();
    }
  }

  private static class ProjectForeInformationTupleScheme extends TupleScheme<ProjectForeInformation> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProjectForeInformation struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPid()) {
        optionals.set(0);
      }
      if (struct.isSetProjectId()) {
        optionals.set(1);
      }
      if (struct.isSetForeId()) {
        optionals.set(2);
      }
      if (struct.isSetOriginalNumber()) {
        optionals.set(3);
      }
      if (struct.isSetCopyNumber()) {
        optionals.set(4);
      }
      if (struct.isSetRemark()) {
        optionals.set(5);
      }
      if (struct.isSetForeInformationName()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetPid()) {
        oprot.writeI32(struct.pid);
      }
      if (struct.isSetProjectId()) {
        oprot.writeI32(struct.projectId);
      }
      if (struct.isSetForeId()) {
        oprot.writeI32(struct.foreId);
      }
      if (struct.isSetOriginalNumber()) {
        oprot.writeI32(struct.originalNumber);
      }
      if (struct.isSetCopyNumber()) {
        oprot.writeI32(struct.copyNumber);
      }
      if (struct.isSetRemark()) {
        oprot.writeString(struct.remark);
      }
      if (struct.isSetForeInformationName()) {
        oprot.writeString(struct.foreInformationName);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProjectForeInformation struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.pid = iprot.readI32();
        struct.setPidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.projectId = iprot.readI32();
        struct.setProjectIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.foreId = iprot.readI32();
        struct.setForeIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.originalNumber = iprot.readI32();
        struct.setOriginalNumberIsSet(true);
      }
      if (incoming.get(4)) {
        struct.copyNumber = iprot.readI32();
        struct.setCopyNumberIsSet(true);
      }
      if (incoming.get(5)) {
        struct.remark = iprot.readString();
        struct.setRemarkIsSet(true);
      }
      if (incoming.get(6)) {
        struct.foreInformationName = iprot.readString();
        struct.setForeInformationNameIsSet(true);
      }
    }
  }

}
