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
public class CusEstTemplate implements org.apache.thrift.TBase<CusEstTemplate, CusEstTemplate._Fields>, java.io.Serializable, Cloneable, Comparable<CusEstTemplate> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CusEstTemplate");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField MODEL_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("modelType", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField MODEL_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("modelName", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField REMARK_FIELD_DESC = new org.apache.thrift.protocol.TField("remark", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField CRE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("creTime", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField FACTORS_FIELD_DESC = new org.apache.thrift.protocol.TField("factors", org.apache.thrift.protocol.TType.LIST, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CusEstTemplateStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CusEstTemplateTupleSchemeFactory());
  }

  public int pid; // required
  public int modelType; // required
  public String modelName; // required
  public String remark; // required
  public String creTime; // required
  public int status; // required
  public List<CusEstFactorWeights> factors; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    MODEL_TYPE((short)2, "modelType"),
    MODEL_NAME((short)3, "modelName"),
    REMARK((short)4, "remark"),
    CRE_TIME((short)5, "creTime"),
    STATUS((short)6, "status"),
    FACTORS((short)7, "factors");

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
        case 2: // MODEL_TYPE
          return MODEL_TYPE;
        case 3: // MODEL_NAME
          return MODEL_NAME;
        case 4: // REMARK
          return REMARK;
        case 5: // CRE_TIME
          return CRE_TIME;
        case 6: // STATUS
          return STATUS;
        case 7: // FACTORS
          return FACTORS;
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
  private static final int __MODELTYPE_ISSET_ID = 1;
  private static final int __STATUS_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MODEL_TYPE, new org.apache.thrift.meta_data.FieldMetaData("modelType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MODEL_NAME, new org.apache.thrift.meta_data.FieldMetaData("modelName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.REMARK, new org.apache.thrift.meta_data.FieldMetaData("remark", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CRE_TIME, new org.apache.thrift.meta_data.FieldMetaData("creTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.FACTORS, new org.apache.thrift.meta_data.FieldMetaData("factors", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "CusEstFactorWeights"))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CusEstTemplate.class, metaDataMap);
  }

  public CusEstTemplate() {
  }

  public CusEstTemplate(
    int pid,
    int modelType,
    String modelName,
    String remark,
    String creTime,
    int status,
    List<CusEstFactorWeights> factors)
  {
    this();
    this.pid = pid;
    setPidIsSet(true);
    this.modelType = modelType;
    setModelTypeIsSet(true);
    this.modelName = modelName;
    this.remark = remark;
    this.creTime = creTime;
    this.status = status;
    setStatusIsSet(true);
    this.factors = factors;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CusEstTemplate(CusEstTemplate other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pid = other.pid;
    this.modelType = other.modelType;
    if (other.isSetModelName()) {
      this.modelName = other.modelName;
    }
    if (other.isSetRemark()) {
      this.remark = other.remark;
    }
    if (other.isSetCreTime()) {
      this.creTime = other.creTime;
    }
    this.status = other.status;
    if (other.isSetFactors()) {
      List<CusEstFactorWeights> __this__factors = new ArrayList<CusEstFactorWeights>(other.factors.size());
      for (CusEstFactorWeights other_element : other.factors) {
        __this__factors.add(other_element);
      }
      this.factors = __this__factors;
    }
  }

  public CusEstTemplate deepCopy() {
    return new CusEstTemplate(this);
  }

  @Override
  public void clear() {
    setPidIsSet(false);
    this.pid = 0;
    setModelTypeIsSet(false);
    this.modelType = 0;
    this.modelName = null;
    this.remark = null;
    this.creTime = null;
    setStatusIsSet(false);
    this.status = 0;
    this.factors = null;
  }

  public int getPid() {
    return this.pid;
  }

  public CusEstTemplate setPid(int pid) {
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

  public int getModelType() {
    return this.modelType;
  }

  public CusEstTemplate setModelType(int modelType) {
    this.modelType = modelType;
    setModelTypeIsSet(true);
    return this;
  }

  public void unsetModelType() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MODELTYPE_ISSET_ID);
  }

  /** Returns true if field modelType is set (has been assigned a value) and false otherwise */
  public boolean isSetModelType() {
    return EncodingUtils.testBit(__isset_bitfield, __MODELTYPE_ISSET_ID);
  }

  public void setModelTypeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MODELTYPE_ISSET_ID, value);
  }

  public String getModelName() {
    return this.modelName;
  }

  public CusEstTemplate setModelName(String modelName) {
    this.modelName = modelName;
    return this;
  }

  public void unsetModelName() {
    this.modelName = null;
  }

  /** Returns true if field modelName is set (has been assigned a value) and false otherwise */
  public boolean isSetModelName() {
    return this.modelName != null;
  }

  public void setModelNameIsSet(boolean value) {
    if (!value) {
      this.modelName = null;
    }
  }

  public String getRemark() {
    return this.remark;
  }

  public CusEstTemplate setRemark(String remark) {
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

  public String getCreTime() {
    return this.creTime;
  }

  public CusEstTemplate setCreTime(String creTime) {
    this.creTime = creTime;
    return this;
  }

  public void unsetCreTime() {
    this.creTime = null;
  }

  /** Returns true if field creTime is set (has been assigned a value) and false otherwise */
  public boolean isSetCreTime() {
    return this.creTime != null;
  }

  public void setCreTimeIsSet(boolean value) {
    if (!value) {
      this.creTime = null;
    }
  }

  public int getStatus() {
    return this.status;
  }

  public CusEstTemplate setStatus(int status) {
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

  public int getFactorsSize() {
    return (this.factors == null) ? 0 : this.factors.size();
  }

  public java.util.Iterator<CusEstFactorWeights> getFactorsIterator() {
    return (this.factors == null) ? null : this.factors.iterator();
  }

  public void addToFactors(CusEstFactorWeights elem) {
    if (this.factors == null) {
      this.factors = new ArrayList<CusEstFactorWeights>();
    }
    this.factors.add(elem);
  }

  public List<CusEstFactorWeights> getFactors() {
    return this.factors;
  }

  public CusEstTemplate setFactors(List<CusEstFactorWeights> factors) {
    this.factors = factors;
    return this;
  }

  public void unsetFactors() {
    this.factors = null;
  }

  /** Returns true if field factors is set (has been assigned a value) and false otherwise */
  public boolean isSetFactors() {
    return this.factors != null;
  }

  public void setFactorsIsSet(boolean value) {
    if (!value) {
      this.factors = null;
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

    case MODEL_TYPE:
      if (value == null) {
        unsetModelType();
      } else {
        setModelType((Integer)value);
      }
      break;

    case MODEL_NAME:
      if (value == null) {
        unsetModelName();
      } else {
        setModelName((String)value);
      }
      break;

    case REMARK:
      if (value == null) {
        unsetRemark();
      } else {
        setRemark((String)value);
      }
      break;

    case CRE_TIME:
      if (value == null) {
        unsetCreTime();
      } else {
        setCreTime((String)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((Integer)value);
      }
      break;

    case FACTORS:
      if (value == null) {
        unsetFactors();
      } else {
        setFactors((List<CusEstFactorWeights>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PID:
      return Integer.valueOf(getPid());

    case MODEL_TYPE:
      return Integer.valueOf(getModelType());

    case MODEL_NAME:
      return getModelName();

    case REMARK:
      return getRemark();

    case CRE_TIME:
      return getCreTime();

    case STATUS:
      return Integer.valueOf(getStatus());

    case FACTORS:
      return getFactors();

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
    case MODEL_TYPE:
      return isSetModelType();
    case MODEL_NAME:
      return isSetModelName();
    case REMARK:
      return isSetRemark();
    case CRE_TIME:
      return isSetCreTime();
    case STATUS:
      return isSetStatus();
    case FACTORS:
      return isSetFactors();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CusEstTemplate)
      return this.equals((CusEstTemplate)that);
    return false;
  }

  public boolean equals(CusEstTemplate that) {
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

    boolean this_present_modelType = true;
    boolean that_present_modelType = true;
    if (this_present_modelType || that_present_modelType) {
      if (!(this_present_modelType && that_present_modelType))
        return false;
      if (this.modelType != that.modelType)
        return false;
    }

    boolean this_present_modelName = true && this.isSetModelName();
    boolean that_present_modelName = true && that.isSetModelName();
    if (this_present_modelName || that_present_modelName) {
      if (!(this_present_modelName && that_present_modelName))
        return false;
      if (!this.modelName.equals(that.modelName))
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

    boolean this_present_creTime = true && this.isSetCreTime();
    boolean that_present_creTime = true && that.isSetCreTime();
    if (this_present_creTime || that_present_creTime) {
      if (!(this_present_creTime && that_present_creTime))
        return false;
      if (!this.creTime.equals(that.creTime))
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

    boolean this_present_factors = true && this.isSetFactors();
    boolean that_present_factors = true && that.isSetFactors();
    if (this_present_factors || that_present_factors) {
      if (!(this_present_factors && that_present_factors))
        return false;
      if (!this.factors.equals(that.factors))
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

    boolean present_modelType = true;
    list.add(present_modelType);
    if (present_modelType)
      list.add(modelType);

    boolean present_modelName = true && (isSetModelName());
    list.add(present_modelName);
    if (present_modelName)
      list.add(modelName);

    boolean present_remark = true && (isSetRemark());
    list.add(present_remark);
    if (present_remark)
      list.add(remark);

    boolean present_creTime = true && (isSetCreTime());
    list.add(present_creTime);
    if (present_creTime)
      list.add(creTime);

    boolean present_status = true;
    list.add(present_status);
    if (present_status)
      list.add(status);

    boolean present_factors = true && (isSetFactors());
    list.add(present_factors);
    if (present_factors)
      list.add(factors);

    return list.hashCode();
  }

  @Override
  public int compareTo(CusEstTemplate other) {
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
    lastComparison = Boolean.valueOf(isSetModelType()).compareTo(other.isSetModelType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetModelType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.modelType, other.modelType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetModelName()).compareTo(other.isSetModelName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetModelName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.modelName, other.modelName);
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
    lastComparison = Boolean.valueOf(isSetCreTime()).compareTo(other.isSetCreTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.creTime, other.creTime);
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
    lastComparison = Boolean.valueOf(isSetFactors()).compareTo(other.isSetFactors());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFactors()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.factors, other.factors);
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
    StringBuilder sb = new StringBuilder("CusEstTemplate(");
    boolean first = true;

    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("modelType:");
    sb.append(this.modelType);
    first = false;
    if (!first) sb.append(", ");
    sb.append("modelName:");
    if (this.modelName == null) {
      sb.append("null");
    } else {
      sb.append(this.modelName);
    }
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
    sb.append("creTime:");
    if (this.creTime == null) {
      sb.append("null");
    } else {
      sb.append(this.creTime);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("status:");
    sb.append(this.status);
    first = false;
    if (!first) sb.append(", ");
    sb.append("factors:");
    if (this.factors == null) {
      sb.append("null");
    } else {
      sb.append(this.factors);
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

  private static class CusEstTemplateStandardSchemeFactory implements SchemeFactory {
    public CusEstTemplateStandardScheme getScheme() {
      return new CusEstTemplateStandardScheme();
    }
  }

  private static class CusEstTemplateStandardScheme extends StandardScheme<CusEstTemplate> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CusEstTemplate struct) throws org.apache.thrift.TException {
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
          case 2: // MODEL_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.modelType = iprot.readI32();
              struct.setModelTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MODEL_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.modelName = iprot.readString();
              struct.setModelNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // REMARK
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.remark = iprot.readString();
              struct.setRemarkIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // CRE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.creTime = iprot.readString();
              struct.setCreTimeIsSet(true);
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
          case 7: // FACTORS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list32 = iprot.readListBegin();
                struct.factors = new ArrayList<CusEstFactorWeights>(_list32.size);
                CusEstFactorWeights _elem33;
                for (int _i34 = 0; _i34 < _list32.size; ++_i34)
                {
                  _elem33 = new CusEstFactorWeights();
                  _elem33.read(iprot);
                  struct.factors.add(_elem33);
                }
                iprot.readListEnd();
              }
              struct.setFactorsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CusEstTemplate struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PID_FIELD_DESC);
      oprot.writeI32(struct.pid);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(MODEL_TYPE_FIELD_DESC);
      oprot.writeI32(struct.modelType);
      oprot.writeFieldEnd();
      if (struct.modelName != null) {
        oprot.writeFieldBegin(MODEL_NAME_FIELD_DESC);
        oprot.writeString(struct.modelName);
        oprot.writeFieldEnd();
      }
      if (struct.remark != null) {
        oprot.writeFieldBegin(REMARK_FIELD_DESC);
        oprot.writeString(struct.remark);
        oprot.writeFieldEnd();
      }
      if (struct.creTime != null) {
        oprot.writeFieldBegin(CRE_TIME_FIELD_DESC);
        oprot.writeString(struct.creTime);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(STATUS_FIELD_DESC);
      oprot.writeI32(struct.status);
      oprot.writeFieldEnd();
      if (struct.factors != null) {
        oprot.writeFieldBegin(FACTORS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.factors.size()));
          for (CusEstFactorWeights _iter35 : struct.factors)
          {
            _iter35.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CusEstTemplateTupleSchemeFactory implements SchemeFactory {
    public CusEstTemplateTupleScheme getScheme() {
      return new CusEstTemplateTupleScheme();
    }
  }

  private static class CusEstTemplateTupleScheme extends TupleScheme<CusEstTemplate> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CusEstTemplate struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPid()) {
        optionals.set(0);
      }
      if (struct.isSetModelType()) {
        optionals.set(1);
      }
      if (struct.isSetModelName()) {
        optionals.set(2);
      }
      if (struct.isSetRemark()) {
        optionals.set(3);
      }
      if (struct.isSetCreTime()) {
        optionals.set(4);
      }
      if (struct.isSetStatus()) {
        optionals.set(5);
      }
      if (struct.isSetFactors()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetPid()) {
        oprot.writeI32(struct.pid);
      }
      if (struct.isSetModelType()) {
        oprot.writeI32(struct.modelType);
      }
      if (struct.isSetModelName()) {
        oprot.writeString(struct.modelName);
      }
      if (struct.isSetRemark()) {
        oprot.writeString(struct.remark);
      }
      if (struct.isSetCreTime()) {
        oprot.writeString(struct.creTime);
      }
      if (struct.isSetStatus()) {
        oprot.writeI32(struct.status);
      }
      if (struct.isSetFactors()) {
        {
          oprot.writeI32(struct.factors.size());
          for (CusEstFactorWeights _iter36 : struct.factors)
          {
            _iter36.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CusEstTemplate struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.pid = iprot.readI32();
        struct.setPidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.modelType = iprot.readI32();
        struct.setModelTypeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.modelName = iprot.readString();
        struct.setModelNameIsSet(true);
      }
      if (incoming.get(3)) {
        struct.remark = iprot.readString();
        struct.setRemarkIsSet(true);
      }
      if (incoming.get(4)) {
        struct.creTime = iprot.readString();
        struct.setCreTimeIsSet(true);
      }
      if (incoming.get(5)) {
        struct.status = iprot.readI32();
        struct.setStatusIsSet(true);
      }
      if (incoming.get(6)) {
        {
          org.apache.thrift.protocol.TList _list37 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.factors = new ArrayList<CusEstFactorWeights>(_list37.size);
          CusEstFactorWeights _elem38;
          for (int _i39 = 0; _i39 < _list37.size; ++_i39)
          {
            _elem38 = new CusEstFactorWeights();
            _elem38.read(iprot);
            struct.factors.add(_elem38);
          }
        }
        struct.setFactorsIsSet(true);
      }
    }
  }

}

