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
public class CusComStaff implements org.apache.thrift.TBase<CusComStaff, CusComStaff._Fields>, java.io.Serializable, Cloneable, Comparable<CusComStaff> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CusComStaff");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField CUS_COM_BASE_FIELD_DESC = new org.apache.thrift.protocol.TField("cusComBase", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField CATELOG_FIELD_DESC = new org.apache.thrift.protocol.TField("catelog", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField TEACH_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("teachType", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField PERSON_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("personNum", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField RATIO_FIELD_DESC = new org.apache.thrift.protocol.TField("ratio", org.apache.thrift.protocol.TType.DOUBLE, (short)6);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short)7);
  private static final org.apache.thrift.protocol.TField SORT_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("sortNum", org.apache.thrift.protocol.TType.I32, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CusComStaffStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CusComStaffTupleSchemeFactory());
  }

  public int pid; // required
  public CusComBase cusComBase; // required
  public String catelog; // required
  public String teachType; // required
  public int personNum; // required
  public double ratio; // required
  public int status; // required
  public int sortNum; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    CUS_COM_BASE((short)2, "cusComBase"),
    CATELOG((short)3, "catelog"),
    TEACH_TYPE((short)4, "teachType"),
    PERSON_NUM((short)5, "personNum"),
    RATIO((short)6, "ratio"),
    STATUS((short)7, "status"),
    SORT_NUM((short)8, "sortNum");

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
        case 2: // CUS_COM_BASE
          return CUS_COM_BASE;
        case 3: // CATELOG
          return CATELOG;
        case 4: // TEACH_TYPE
          return TEACH_TYPE;
        case 5: // PERSON_NUM
          return PERSON_NUM;
        case 6: // RATIO
          return RATIO;
        case 7: // STATUS
          return STATUS;
        case 8: // SORT_NUM
          return SORT_NUM;
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
  private static final int __PERSONNUM_ISSET_ID = 1;
  private static final int __RATIO_ISSET_ID = 2;
  private static final int __STATUS_ISSET_ID = 3;
  private static final int __SORTNUM_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CUS_COM_BASE, new org.apache.thrift.meta_data.FieldMetaData("cusComBase", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, CusComBase.class)));
    tmpMap.put(_Fields.CATELOG, new org.apache.thrift.meta_data.FieldMetaData("catelog", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TEACH_TYPE, new org.apache.thrift.meta_data.FieldMetaData("teachType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PERSON_NUM, new org.apache.thrift.meta_data.FieldMetaData("personNum", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.RATIO, new org.apache.thrift.meta_data.FieldMetaData("ratio", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.SORT_NUM, new org.apache.thrift.meta_data.FieldMetaData("sortNum", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CusComStaff.class, metaDataMap);
  }

  public CusComStaff() {
  }

  public CusComStaff(
    int pid,
    CusComBase cusComBase,
    String catelog,
    String teachType,
    int personNum,
    double ratio,
    int status,
    int sortNum)
  {
    this();
    this.pid = pid;
    setPidIsSet(true);
    this.cusComBase = cusComBase;
    this.catelog = catelog;
    this.teachType = teachType;
    this.personNum = personNum;
    setPersonNumIsSet(true);
    this.ratio = ratio;
    setRatioIsSet(true);
    this.status = status;
    setStatusIsSet(true);
    this.sortNum = sortNum;
    setSortNumIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CusComStaff(CusComStaff other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pid = other.pid;
    if (other.isSetCusComBase()) {
      this.cusComBase = new CusComBase(other.cusComBase);
    }
    if (other.isSetCatelog()) {
      this.catelog = other.catelog;
    }
    if (other.isSetTeachType()) {
      this.teachType = other.teachType;
    }
    this.personNum = other.personNum;
    this.ratio = other.ratio;
    this.status = other.status;
    this.sortNum = other.sortNum;
  }

  public CusComStaff deepCopy() {
    return new CusComStaff(this);
  }

  @Override
  public void clear() {
    setPidIsSet(false);
    this.pid = 0;
    this.cusComBase = null;
    this.catelog = null;
    this.teachType = null;
    setPersonNumIsSet(false);
    this.personNum = 0;
    setRatioIsSet(false);
    this.ratio = 0.0;
    setStatusIsSet(false);
    this.status = 0;
    setSortNumIsSet(false);
    this.sortNum = 0;
  }

  public int getPid() {
    return this.pid;
  }

  public CusComStaff setPid(int pid) {
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

  public CusComBase getCusComBase() {
    return this.cusComBase;
  }

  public CusComStaff setCusComBase(CusComBase cusComBase) {
    this.cusComBase = cusComBase;
    return this;
  }

  public void unsetCusComBase() {
    this.cusComBase = null;
  }

  /** Returns true if field cusComBase is set (has been assigned a value) and false otherwise */
  public boolean isSetCusComBase() {
    return this.cusComBase != null;
  }

  public void setCusComBaseIsSet(boolean value) {
    if (!value) {
      this.cusComBase = null;
    }
  }

  public String getCatelog() {
    return this.catelog;
  }

  public CusComStaff setCatelog(String catelog) {
    this.catelog = catelog;
    return this;
  }

  public void unsetCatelog() {
    this.catelog = null;
  }

  /** Returns true if field catelog is set (has been assigned a value) and false otherwise */
  public boolean isSetCatelog() {
    return this.catelog != null;
  }

  public void setCatelogIsSet(boolean value) {
    if (!value) {
      this.catelog = null;
    }
  }

  public String getTeachType() {
    return this.teachType;
  }

  public CusComStaff setTeachType(String teachType) {
    this.teachType = teachType;
    return this;
  }

  public void unsetTeachType() {
    this.teachType = null;
  }

  /** Returns true if field teachType is set (has been assigned a value) and false otherwise */
  public boolean isSetTeachType() {
    return this.teachType != null;
  }

  public void setTeachTypeIsSet(boolean value) {
    if (!value) {
      this.teachType = null;
    }
  }

  public int getPersonNum() {
    return this.personNum;
  }

  public CusComStaff setPersonNum(int personNum) {
    this.personNum = personNum;
    setPersonNumIsSet(true);
    return this;
  }

  public void unsetPersonNum() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PERSONNUM_ISSET_ID);
  }

  /** Returns true if field personNum is set (has been assigned a value) and false otherwise */
  public boolean isSetPersonNum() {
    return EncodingUtils.testBit(__isset_bitfield, __PERSONNUM_ISSET_ID);
  }

  public void setPersonNumIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PERSONNUM_ISSET_ID, value);
  }

  public double getRatio() {
    return this.ratio;
  }

  public CusComStaff setRatio(double ratio) {
    this.ratio = ratio;
    setRatioIsSet(true);
    return this;
  }

  public void unsetRatio() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __RATIO_ISSET_ID);
  }

  /** Returns true if field ratio is set (has been assigned a value) and false otherwise */
  public boolean isSetRatio() {
    return EncodingUtils.testBit(__isset_bitfield, __RATIO_ISSET_ID);
  }

  public void setRatioIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __RATIO_ISSET_ID, value);
  }

  public int getStatus() {
    return this.status;
  }

  public CusComStaff setStatus(int status) {
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

  public int getSortNum() {
    return this.sortNum;
  }

  public CusComStaff setSortNum(int sortNum) {
    this.sortNum = sortNum;
    setSortNumIsSet(true);
    return this;
  }

  public void unsetSortNum() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SORTNUM_ISSET_ID);
  }

  /** Returns true if field sortNum is set (has been assigned a value) and false otherwise */
  public boolean isSetSortNum() {
    return EncodingUtils.testBit(__isset_bitfield, __SORTNUM_ISSET_ID);
  }

  public void setSortNumIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SORTNUM_ISSET_ID, value);
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

    case CUS_COM_BASE:
      if (value == null) {
        unsetCusComBase();
      } else {
        setCusComBase((CusComBase)value);
      }
      break;

    case CATELOG:
      if (value == null) {
        unsetCatelog();
      } else {
        setCatelog((String)value);
      }
      break;

    case TEACH_TYPE:
      if (value == null) {
        unsetTeachType();
      } else {
        setTeachType((String)value);
      }
      break;

    case PERSON_NUM:
      if (value == null) {
        unsetPersonNum();
      } else {
        setPersonNum((Integer)value);
      }
      break;

    case RATIO:
      if (value == null) {
        unsetRatio();
      } else {
        setRatio((Double)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((Integer)value);
      }
      break;

    case SORT_NUM:
      if (value == null) {
        unsetSortNum();
      } else {
        setSortNum((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PID:
      return Integer.valueOf(getPid());

    case CUS_COM_BASE:
      return getCusComBase();

    case CATELOG:
      return getCatelog();

    case TEACH_TYPE:
      return getTeachType();

    case PERSON_NUM:
      return Integer.valueOf(getPersonNum());

    case RATIO:
      return Double.valueOf(getRatio());

    case STATUS:
      return Integer.valueOf(getStatus());

    case SORT_NUM:
      return Integer.valueOf(getSortNum());

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
    case CUS_COM_BASE:
      return isSetCusComBase();
    case CATELOG:
      return isSetCatelog();
    case TEACH_TYPE:
      return isSetTeachType();
    case PERSON_NUM:
      return isSetPersonNum();
    case RATIO:
      return isSetRatio();
    case STATUS:
      return isSetStatus();
    case SORT_NUM:
      return isSetSortNum();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CusComStaff)
      return this.equals((CusComStaff)that);
    return false;
  }

  public boolean equals(CusComStaff that) {
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

    boolean this_present_cusComBase = true && this.isSetCusComBase();
    boolean that_present_cusComBase = true && that.isSetCusComBase();
    if (this_present_cusComBase || that_present_cusComBase) {
      if (!(this_present_cusComBase && that_present_cusComBase))
        return false;
      if (!this.cusComBase.equals(that.cusComBase))
        return false;
    }

    boolean this_present_catelog = true && this.isSetCatelog();
    boolean that_present_catelog = true && that.isSetCatelog();
    if (this_present_catelog || that_present_catelog) {
      if (!(this_present_catelog && that_present_catelog))
        return false;
      if (!this.catelog.equals(that.catelog))
        return false;
    }

    boolean this_present_teachType = true && this.isSetTeachType();
    boolean that_present_teachType = true && that.isSetTeachType();
    if (this_present_teachType || that_present_teachType) {
      if (!(this_present_teachType && that_present_teachType))
        return false;
      if (!this.teachType.equals(that.teachType))
        return false;
    }

    boolean this_present_personNum = true;
    boolean that_present_personNum = true;
    if (this_present_personNum || that_present_personNum) {
      if (!(this_present_personNum && that_present_personNum))
        return false;
      if (this.personNum != that.personNum)
        return false;
    }

    boolean this_present_ratio = true;
    boolean that_present_ratio = true;
    if (this_present_ratio || that_present_ratio) {
      if (!(this_present_ratio && that_present_ratio))
        return false;
      if (this.ratio != that.ratio)
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

    boolean this_present_sortNum = true;
    boolean that_present_sortNum = true;
    if (this_present_sortNum || that_present_sortNum) {
      if (!(this_present_sortNum && that_present_sortNum))
        return false;
      if (this.sortNum != that.sortNum)
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

    boolean present_cusComBase = true && (isSetCusComBase());
    list.add(present_cusComBase);
    if (present_cusComBase)
      list.add(cusComBase);

    boolean present_catelog = true && (isSetCatelog());
    list.add(present_catelog);
    if (present_catelog)
      list.add(catelog);

    boolean present_teachType = true && (isSetTeachType());
    list.add(present_teachType);
    if (present_teachType)
      list.add(teachType);

    boolean present_personNum = true;
    list.add(present_personNum);
    if (present_personNum)
      list.add(personNum);

    boolean present_ratio = true;
    list.add(present_ratio);
    if (present_ratio)
      list.add(ratio);

    boolean present_status = true;
    list.add(present_status);
    if (present_status)
      list.add(status);

    boolean present_sortNum = true;
    list.add(present_sortNum);
    if (present_sortNum)
      list.add(sortNum);

    return list.hashCode();
  }

  @Override
  public int compareTo(CusComStaff other) {
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
    lastComparison = Boolean.valueOf(isSetCusComBase()).compareTo(other.isSetCusComBase());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCusComBase()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cusComBase, other.cusComBase);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCatelog()).compareTo(other.isSetCatelog());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCatelog()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.catelog, other.catelog);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTeachType()).compareTo(other.isSetTeachType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTeachType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.teachType, other.teachType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPersonNum()).compareTo(other.isSetPersonNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPersonNum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.personNum, other.personNum);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRatio()).compareTo(other.isSetRatio());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRatio()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ratio, other.ratio);
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
    lastComparison = Boolean.valueOf(isSetSortNum()).compareTo(other.isSetSortNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSortNum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sortNum, other.sortNum);
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
    StringBuilder sb = new StringBuilder("CusComStaff(");
    boolean first = true;

    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("cusComBase:");
    if (this.cusComBase == null) {
      sb.append("null");
    } else {
      sb.append(this.cusComBase);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("catelog:");
    if (this.catelog == null) {
      sb.append("null");
    } else {
      sb.append(this.catelog);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("teachType:");
    if (this.teachType == null) {
      sb.append("null");
    } else {
      sb.append(this.teachType);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("personNum:");
    sb.append(this.personNum);
    first = false;
    if (!first) sb.append(", ");
    sb.append("ratio:");
    sb.append(this.ratio);
    first = false;
    if (!first) sb.append(", ");
    sb.append("status:");
    sb.append(this.status);
    first = false;
    if (!first) sb.append(", ");
    sb.append("sortNum:");
    sb.append(this.sortNum);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (cusComBase != null) {
      cusComBase.validate();
    }
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

  private static class CusComStaffStandardSchemeFactory implements SchemeFactory {
    public CusComStaffStandardScheme getScheme() {
      return new CusComStaffStandardScheme();
    }
  }

  private static class CusComStaffStandardScheme extends StandardScheme<CusComStaff> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CusComStaff struct) throws org.apache.thrift.TException {
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
          case 2: // CUS_COM_BASE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.cusComBase = new CusComBase();
              struct.cusComBase.read(iprot);
              struct.setCusComBaseIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CATELOG
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.catelog = iprot.readString();
              struct.setCatelogIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TEACH_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.teachType = iprot.readString();
              struct.setTeachTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PERSON_NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.personNum = iprot.readI32();
              struct.setPersonNumIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // RATIO
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.ratio = iprot.readDouble();
              struct.setRatioIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.status = iprot.readI32();
              struct.setStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // SORT_NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.sortNum = iprot.readI32();
              struct.setSortNumIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CusComStaff struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PID_FIELD_DESC);
      oprot.writeI32(struct.pid);
      oprot.writeFieldEnd();
      if (struct.cusComBase != null) {
        oprot.writeFieldBegin(CUS_COM_BASE_FIELD_DESC);
        struct.cusComBase.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.catelog != null) {
        oprot.writeFieldBegin(CATELOG_FIELD_DESC);
        oprot.writeString(struct.catelog);
        oprot.writeFieldEnd();
      }
      if (struct.teachType != null) {
        oprot.writeFieldBegin(TEACH_TYPE_FIELD_DESC);
        oprot.writeString(struct.teachType);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(PERSON_NUM_FIELD_DESC);
      oprot.writeI32(struct.personNum);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(RATIO_FIELD_DESC);
      oprot.writeDouble(struct.ratio);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(STATUS_FIELD_DESC);
      oprot.writeI32(struct.status);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(SORT_NUM_FIELD_DESC);
      oprot.writeI32(struct.sortNum);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CusComStaffTupleSchemeFactory implements SchemeFactory {
    public CusComStaffTupleScheme getScheme() {
      return new CusComStaffTupleScheme();
    }
  }

  private static class CusComStaffTupleScheme extends TupleScheme<CusComStaff> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CusComStaff struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPid()) {
        optionals.set(0);
      }
      if (struct.isSetCusComBase()) {
        optionals.set(1);
      }
      if (struct.isSetCatelog()) {
        optionals.set(2);
      }
      if (struct.isSetTeachType()) {
        optionals.set(3);
      }
      if (struct.isSetPersonNum()) {
        optionals.set(4);
      }
      if (struct.isSetRatio()) {
        optionals.set(5);
      }
      if (struct.isSetStatus()) {
        optionals.set(6);
      }
      if (struct.isSetSortNum()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetPid()) {
        oprot.writeI32(struct.pid);
      }
      if (struct.isSetCusComBase()) {
        struct.cusComBase.write(oprot);
      }
      if (struct.isSetCatelog()) {
        oprot.writeString(struct.catelog);
      }
      if (struct.isSetTeachType()) {
        oprot.writeString(struct.teachType);
      }
      if (struct.isSetPersonNum()) {
        oprot.writeI32(struct.personNum);
      }
      if (struct.isSetRatio()) {
        oprot.writeDouble(struct.ratio);
      }
      if (struct.isSetStatus()) {
        oprot.writeI32(struct.status);
      }
      if (struct.isSetSortNum()) {
        oprot.writeI32(struct.sortNum);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CusComStaff struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.pid = iprot.readI32();
        struct.setPidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.cusComBase = new CusComBase();
        struct.cusComBase.read(iprot);
        struct.setCusComBaseIsSet(true);
      }
      if (incoming.get(2)) {
        struct.catelog = iprot.readString();
        struct.setCatelogIsSet(true);
      }
      if (incoming.get(3)) {
        struct.teachType = iprot.readString();
        struct.setTeachTypeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.personNum = iprot.readI32();
        struct.setPersonNumIsSet(true);
      }
      if (incoming.get(5)) {
        struct.ratio = iprot.readDouble();
        struct.setRatioIsSet(true);
      }
      if (incoming.get(6)) {
        struct.status = iprot.readI32();
        struct.setStatusIsSet(true);
      }
      if (incoming.get(7)) {
        struct.sortNum = iprot.readI32();
        struct.setSortNumIsSet(true);
      }
    }
  }

}

