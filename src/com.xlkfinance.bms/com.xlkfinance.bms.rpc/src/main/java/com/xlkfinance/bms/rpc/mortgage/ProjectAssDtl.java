/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xlkfinance.bms.rpc.mortgage;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2017-2-10")
public class ProjectAssDtl implements org.apache.thrift.TBase<ProjectAssDtl, ProjectAssDtl._Fields>, java.io.Serializable, Cloneable, Comparable<ProjectAssDtl> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProjectAssDtl");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField BASE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("baseId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField LOOKUP_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("lookupId", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField LOOKUP_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("lookupVal", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField INFO_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("infoVal", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField LOOKUP_DESC_FIELD_DESC = new org.apache.thrift.protocol.TField("lookupDesc", org.apache.thrift.protocol.TType.STRING, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProjectAssDtlStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProjectAssDtlTupleSchemeFactory());
  }

  public int pid; // required
  public int baseId; // required
  public int lookupId; // required
  public String lookupVal; // required
  public String infoVal; // required
  public int status; // required
  public String lookupDesc; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    BASE_ID((short)2, "baseId"),
    LOOKUP_ID((short)3, "lookupId"),
    LOOKUP_VAL((short)4, "lookupVal"),
    INFO_VAL((short)5, "infoVal"),
    STATUS((short)6, "status"),
    LOOKUP_DESC((short)7, "lookupDesc");

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
        case 2: // BASE_ID
          return BASE_ID;
        case 3: // LOOKUP_ID
          return LOOKUP_ID;
        case 4: // LOOKUP_VAL
          return LOOKUP_VAL;
        case 5: // INFO_VAL
          return INFO_VAL;
        case 6: // STATUS
          return STATUS;
        case 7: // LOOKUP_DESC
          return LOOKUP_DESC;
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
  private static final int __BASEID_ISSET_ID = 1;
  private static final int __LOOKUPID_ISSET_ID = 2;
  private static final int __STATUS_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.BASE_ID, new org.apache.thrift.meta_data.FieldMetaData("baseId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LOOKUP_ID, new org.apache.thrift.meta_data.FieldMetaData("lookupId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LOOKUP_VAL, new org.apache.thrift.meta_data.FieldMetaData("lookupVal", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.INFO_VAL, new org.apache.thrift.meta_data.FieldMetaData("infoVal", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LOOKUP_DESC, new org.apache.thrift.meta_data.FieldMetaData("lookupDesc", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProjectAssDtl.class, metaDataMap);
  }

  public ProjectAssDtl() {
  }

  public ProjectAssDtl(
    int pid,
    int baseId,
    int lookupId,
    String lookupVal,
    String infoVal,
    int status,
    String lookupDesc)
  {
    this();
    this.pid = pid;
    setPidIsSet(true);
    this.baseId = baseId;
    setBaseIdIsSet(true);
    this.lookupId = lookupId;
    setLookupIdIsSet(true);
    this.lookupVal = lookupVal;
    this.infoVal = infoVal;
    this.status = status;
    setStatusIsSet(true);
    this.lookupDesc = lookupDesc;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProjectAssDtl(ProjectAssDtl other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pid = other.pid;
    this.baseId = other.baseId;
    this.lookupId = other.lookupId;
    if (other.isSetLookupVal()) {
      this.lookupVal = other.lookupVal;
    }
    if (other.isSetInfoVal()) {
      this.infoVal = other.infoVal;
    }
    this.status = other.status;
    if (other.isSetLookupDesc()) {
      this.lookupDesc = other.lookupDesc;
    }
  }

  public ProjectAssDtl deepCopy() {
    return new ProjectAssDtl(this);
  }

  @Override
  public void clear() {
    setPidIsSet(false);
    this.pid = 0;
    setBaseIdIsSet(false);
    this.baseId = 0;
    setLookupIdIsSet(false);
    this.lookupId = 0;
    this.lookupVal = null;
    this.infoVal = null;
    setStatusIsSet(false);
    this.status = 0;
    this.lookupDesc = null;
  }

  public int getPid() {
    return this.pid;
  }

  public ProjectAssDtl setPid(int pid) {
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

  public int getBaseId() {
    return this.baseId;
  }

  public ProjectAssDtl setBaseId(int baseId) {
    this.baseId = baseId;
    setBaseIdIsSet(true);
    return this;
  }

  public void unsetBaseId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __BASEID_ISSET_ID);
  }

  /** Returns true if field baseId is set (has been assigned a value) and false otherwise */
  public boolean isSetBaseId() {
    return EncodingUtils.testBit(__isset_bitfield, __BASEID_ISSET_ID);
  }

  public void setBaseIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __BASEID_ISSET_ID, value);
  }

  public int getLookupId() {
    return this.lookupId;
  }

  public ProjectAssDtl setLookupId(int lookupId) {
    this.lookupId = lookupId;
    setLookupIdIsSet(true);
    return this;
  }

  public void unsetLookupId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LOOKUPID_ISSET_ID);
  }

  /** Returns true if field lookupId is set (has been assigned a value) and false otherwise */
  public boolean isSetLookupId() {
    return EncodingUtils.testBit(__isset_bitfield, __LOOKUPID_ISSET_ID);
  }

  public void setLookupIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LOOKUPID_ISSET_ID, value);
  }

  public String getLookupVal() {
    return this.lookupVal;
  }

  public ProjectAssDtl setLookupVal(String lookupVal) {
    this.lookupVal = lookupVal;
    return this;
  }

  public void unsetLookupVal() {
    this.lookupVal = null;
  }

  /** Returns true if field lookupVal is set (has been assigned a value) and false otherwise */
  public boolean isSetLookupVal() {
    return this.lookupVal != null;
  }

  public void setLookupValIsSet(boolean value) {
    if (!value) {
      this.lookupVal = null;
    }
  }

  public String getInfoVal() {
    return this.infoVal;
  }

  public ProjectAssDtl setInfoVal(String infoVal) {
    this.infoVal = infoVal;
    return this;
  }

  public void unsetInfoVal() {
    this.infoVal = null;
  }

  /** Returns true if field infoVal is set (has been assigned a value) and false otherwise */
  public boolean isSetInfoVal() {
    return this.infoVal != null;
  }

  public void setInfoValIsSet(boolean value) {
    if (!value) {
      this.infoVal = null;
    }
  }

  public int getStatus() {
    return this.status;
  }

  public ProjectAssDtl setStatus(int status) {
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

  public String getLookupDesc() {
    return this.lookupDesc;
  }

  public ProjectAssDtl setLookupDesc(String lookupDesc) {
    this.lookupDesc = lookupDesc;
    return this;
  }

  public void unsetLookupDesc() {
    this.lookupDesc = null;
  }

  /** Returns true if field lookupDesc is set (has been assigned a value) and false otherwise */
  public boolean isSetLookupDesc() {
    return this.lookupDesc != null;
  }

  public void setLookupDescIsSet(boolean value) {
    if (!value) {
      this.lookupDesc = null;
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

    case BASE_ID:
      if (value == null) {
        unsetBaseId();
      } else {
        setBaseId((Integer)value);
      }
      break;

    case LOOKUP_ID:
      if (value == null) {
        unsetLookupId();
      } else {
        setLookupId((Integer)value);
      }
      break;

    case LOOKUP_VAL:
      if (value == null) {
        unsetLookupVal();
      } else {
        setLookupVal((String)value);
      }
      break;

    case INFO_VAL:
      if (value == null) {
        unsetInfoVal();
      } else {
        setInfoVal((String)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((Integer)value);
      }
      break;

    case LOOKUP_DESC:
      if (value == null) {
        unsetLookupDesc();
      } else {
        setLookupDesc((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PID:
      return Integer.valueOf(getPid());

    case BASE_ID:
      return Integer.valueOf(getBaseId());

    case LOOKUP_ID:
      return Integer.valueOf(getLookupId());

    case LOOKUP_VAL:
      return getLookupVal();

    case INFO_VAL:
      return getInfoVal();

    case STATUS:
      return Integer.valueOf(getStatus());

    case LOOKUP_DESC:
      return getLookupDesc();

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
    case BASE_ID:
      return isSetBaseId();
    case LOOKUP_ID:
      return isSetLookupId();
    case LOOKUP_VAL:
      return isSetLookupVal();
    case INFO_VAL:
      return isSetInfoVal();
    case STATUS:
      return isSetStatus();
    case LOOKUP_DESC:
      return isSetLookupDesc();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProjectAssDtl)
      return this.equals((ProjectAssDtl)that);
    return false;
  }

  public boolean equals(ProjectAssDtl that) {
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

    boolean this_present_baseId = true;
    boolean that_present_baseId = true;
    if (this_present_baseId || that_present_baseId) {
      if (!(this_present_baseId && that_present_baseId))
        return false;
      if (this.baseId != that.baseId)
        return false;
    }

    boolean this_present_lookupId = true;
    boolean that_present_lookupId = true;
    if (this_present_lookupId || that_present_lookupId) {
      if (!(this_present_lookupId && that_present_lookupId))
        return false;
      if (this.lookupId != that.lookupId)
        return false;
    }

    boolean this_present_lookupVal = true && this.isSetLookupVal();
    boolean that_present_lookupVal = true && that.isSetLookupVal();
    if (this_present_lookupVal || that_present_lookupVal) {
      if (!(this_present_lookupVal && that_present_lookupVal))
        return false;
      if (!this.lookupVal.equals(that.lookupVal))
        return false;
    }

    boolean this_present_infoVal = true && this.isSetInfoVal();
    boolean that_present_infoVal = true && that.isSetInfoVal();
    if (this_present_infoVal || that_present_infoVal) {
      if (!(this_present_infoVal && that_present_infoVal))
        return false;
      if (!this.infoVal.equals(that.infoVal))
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

    boolean this_present_lookupDesc = true && this.isSetLookupDesc();
    boolean that_present_lookupDesc = true && that.isSetLookupDesc();
    if (this_present_lookupDesc || that_present_lookupDesc) {
      if (!(this_present_lookupDesc && that_present_lookupDesc))
        return false;
      if (!this.lookupDesc.equals(that.lookupDesc))
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

    boolean present_baseId = true;
    list.add(present_baseId);
    if (present_baseId)
      list.add(baseId);

    boolean present_lookupId = true;
    list.add(present_lookupId);
    if (present_lookupId)
      list.add(lookupId);

    boolean present_lookupVal = true && (isSetLookupVal());
    list.add(present_lookupVal);
    if (present_lookupVal)
      list.add(lookupVal);

    boolean present_infoVal = true && (isSetInfoVal());
    list.add(present_infoVal);
    if (present_infoVal)
      list.add(infoVal);

    boolean present_status = true;
    list.add(present_status);
    if (present_status)
      list.add(status);

    boolean present_lookupDesc = true && (isSetLookupDesc());
    list.add(present_lookupDesc);
    if (present_lookupDesc)
      list.add(lookupDesc);

    return list.hashCode();
  }

  @Override
  public int compareTo(ProjectAssDtl other) {
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
    lastComparison = Boolean.valueOf(isSetBaseId()).compareTo(other.isSetBaseId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBaseId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.baseId, other.baseId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLookupId()).compareTo(other.isSetLookupId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLookupId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lookupId, other.lookupId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLookupVal()).compareTo(other.isSetLookupVal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLookupVal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lookupVal, other.lookupVal);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetInfoVal()).compareTo(other.isSetInfoVal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInfoVal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.infoVal, other.infoVal);
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
    lastComparison = Boolean.valueOf(isSetLookupDesc()).compareTo(other.isSetLookupDesc());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLookupDesc()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lookupDesc, other.lookupDesc);
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
    StringBuilder sb = new StringBuilder("ProjectAssDtl(");
    boolean first = true;

    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("baseId:");
    sb.append(this.baseId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("lookupId:");
    sb.append(this.lookupId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("lookupVal:");
    if (this.lookupVal == null) {
      sb.append("null");
    } else {
      sb.append(this.lookupVal);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("infoVal:");
    if (this.infoVal == null) {
      sb.append("null");
    } else {
      sb.append(this.infoVal);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("status:");
    sb.append(this.status);
    first = false;
    if (!first) sb.append(", ");
    sb.append("lookupDesc:");
    if (this.lookupDesc == null) {
      sb.append("null");
    } else {
      sb.append(this.lookupDesc);
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

  private static class ProjectAssDtlStandardSchemeFactory implements SchemeFactory {
    public ProjectAssDtlStandardScheme getScheme() {
      return new ProjectAssDtlStandardScheme();
    }
  }

  private static class ProjectAssDtlStandardScheme extends StandardScheme<ProjectAssDtl> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProjectAssDtl struct) throws org.apache.thrift.TException {
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
          case 2: // BASE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.baseId = iprot.readI32();
              struct.setBaseIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // LOOKUP_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.lookupId = iprot.readI32();
              struct.setLookupIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // LOOKUP_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.lookupVal = iprot.readString();
              struct.setLookupValIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // INFO_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.infoVal = iprot.readString();
              struct.setInfoValIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.status = iprot.readI32();
              struct.setStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // LOOKUP_DESC
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.lookupDesc = iprot.readString();
              struct.setLookupDescIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProjectAssDtl struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PID_FIELD_DESC);
      oprot.writeI32(struct.pid);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(BASE_ID_FIELD_DESC);
      oprot.writeI32(struct.baseId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(LOOKUP_ID_FIELD_DESC);
      oprot.writeI32(struct.lookupId);
      oprot.writeFieldEnd();
      if (struct.lookupVal != null) {
        oprot.writeFieldBegin(LOOKUP_VAL_FIELD_DESC);
        oprot.writeString(struct.lookupVal);
        oprot.writeFieldEnd();
      }
      if (struct.infoVal != null) {
        oprot.writeFieldBegin(INFO_VAL_FIELD_DESC);
        oprot.writeString(struct.infoVal);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(STATUS_FIELD_DESC);
      oprot.writeI32(struct.status);
      oprot.writeFieldEnd();
      if (struct.lookupDesc != null) {
        oprot.writeFieldBegin(LOOKUP_DESC_FIELD_DESC);
        oprot.writeString(struct.lookupDesc);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProjectAssDtlTupleSchemeFactory implements SchemeFactory {
    public ProjectAssDtlTupleScheme getScheme() {
      return new ProjectAssDtlTupleScheme();
    }
  }

  private static class ProjectAssDtlTupleScheme extends TupleScheme<ProjectAssDtl> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProjectAssDtl struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPid()) {
        optionals.set(0);
      }
      if (struct.isSetBaseId()) {
        optionals.set(1);
      }
      if (struct.isSetLookupId()) {
        optionals.set(2);
      }
      if (struct.isSetLookupVal()) {
        optionals.set(3);
      }
      if (struct.isSetInfoVal()) {
        optionals.set(4);
      }
      if (struct.isSetStatus()) {
        optionals.set(5);
      }
      if (struct.isSetLookupDesc()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetPid()) {
        oprot.writeI32(struct.pid);
      }
      if (struct.isSetBaseId()) {
        oprot.writeI32(struct.baseId);
      }
      if (struct.isSetLookupId()) {
        oprot.writeI32(struct.lookupId);
      }
      if (struct.isSetLookupVal()) {
        oprot.writeString(struct.lookupVal);
      }
      if (struct.isSetInfoVal()) {
        oprot.writeString(struct.infoVal);
      }
      if (struct.isSetStatus()) {
        oprot.writeI32(struct.status);
      }
      if (struct.isSetLookupDesc()) {
        oprot.writeString(struct.lookupDesc);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProjectAssDtl struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.pid = iprot.readI32();
        struct.setPidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.baseId = iprot.readI32();
        struct.setBaseIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.lookupId = iprot.readI32();
        struct.setLookupIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.lookupVal = iprot.readString();
        struct.setLookupValIsSet(true);
      }
      if (incoming.get(4)) {
        struct.infoVal = iprot.readString();
        struct.setInfoValIsSet(true);
      }
      if (incoming.get(5)) {
        struct.status = iprot.readI32();
        struct.setStatusIsSet(true);
      }
      if (incoming.get(6)) {
        struct.lookupDesc = iprot.readString();
        struct.setLookupDescIsSet(true);
      }
    }
  }

}

