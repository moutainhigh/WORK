/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xlkfinance.bms.rpc.afterloan;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-7-18")
public class ProjectReminderPlanDto implements org.apache.thrift.TBase<ProjectReminderPlanDto, ProjectReminderPlanDto._Fields>, java.io.Serializable, Cloneable, Comparable<ProjectReminderPlanDto> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProjectReminderPlanDto");

  private static final org.apache.thrift.protocol.TField P_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("pId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PROJECT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("projectId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField REMINDER_USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("reminderUserId", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField PLAN_BEGIN_DT_FIELD_DESC = new org.apache.thrift.protocol.TField("planBeginDt", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField IS_MAIL_FIELD_DESC = new org.apache.thrift.protocol.TField("isMail", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField IS_SMS_FIELD_DESC = new org.apache.thrift.protocol.TField("isSms", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField PLAN_DT_FIELD_DESC = new org.apache.thrift.protocol.TField("planDt", org.apache.thrift.protocol.TType.STRING, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProjectReminderPlanDtoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProjectReminderPlanDtoTupleSchemeFactory());
  }

  public int pId; // required
  public int projectId; // required
  public int reminderUserId; // required
  public String planBeginDt; // required
  public int isMail; // required
  public int isSms; // required
  public String planDt; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    P_ID((short)1, "pId"),
    PROJECT_ID((short)2, "projectId"),
    REMINDER_USER_ID((short)3, "reminderUserId"),
    PLAN_BEGIN_DT((short)4, "planBeginDt"),
    IS_MAIL((short)5, "isMail"),
    IS_SMS((short)6, "isSms"),
    PLAN_DT((short)7, "planDt");

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
        case 3: // REMINDER_USER_ID
          return REMINDER_USER_ID;
        case 4: // PLAN_BEGIN_DT
          return PLAN_BEGIN_DT;
        case 5: // IS_MAIL
          return IS_MAIL;
        case 6: // IS_SMS
          return IS_SMS;
        case 7: // PLAN_DT
          return PLAN_DT;
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
  private static final int __REMINDERUSERID_ISSET_ID = 2;
  private static final int __ISMAIL_ISSET_ID = 3;
  private static final int __ISSMS_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.P_ID, new org.apache.thrift.meta_data.FieldMetaData("pId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PROJECT_ID, new org.apache.thrift.meta_data.FieldMetaData("projectId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.REMINDER_USER_ID, new org.apache.thrift.meta_data.FieldMetaData("reminderUserId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PLAN_BEGIN_DT, new org.apache.thrift.meta_data.FieldMetaData("planBeginDt", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.IS_MAIL, new org.apache.thrift.meta_data.FieldMetaData("isMail", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.IS_SMS, new org.apache.thrift.meta_data.FieldMetaData("isSms", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PLAN_DT, new org.apache.thrift.meta_data.FieldMetaData("planDt", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProjectReminderPlanDto.class, metaDataMap);
  }

  public ProjectReminderPlanDto() {
  }

  public ProjectReminderPlanDto(
    int pId,
    int projectId,
    int reminderUserId,
    String planBeginDt,
    int isMail,
    int isSms,
    String planDt)
  {
    this();
    this.pId = pId;
    setPIdIsSet(true);
    this.projectId = projectId;
    setProjectIdIsSet(true);
    this.reminderUserId = reminderUserId;
    setReminderUserIdIsSet(true);
    this.planBeginDt = planBeginDt;
    this.isMail = isMail;
    setIsMailIsSet(true);
    this.isSms = isSms;
    setIsSmsIsSet(true);
    this.planDt = planDt;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProjectReminderPlanDto(ProjectReminderPlanDto other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pId = other.pId;
    this.projectId = other.projectId;
    this.reminderUserId = other.reminderUserId;
    if (other.isSetPlanBeginDt()) {
      this.planBeginDt = other.planBeginDt;
    }
    this.isMail = other.isMail;
    this.isSms = other.isSms;
    if (other.isSetPlanDt()) {
      this.planDt = other.planDt;
    }
  }

  public ProjectReminderPlanDto deepCopy() {
    return new ProjectReminderPlanDto(this);
  }

  @Override
  public void clear() {
    setPIdIsSet(false);
    this.pId = 0;
    setProjectIdIsSet(false);
    this.projectId = 0;
    setReminderUserIdIsSet(false);
    this.reminderUserId = 0;
    this.planBeginDt = null;
    setIsMailIsSet(false);
    this.isMail = 0;
    setIsSmsIsSet(false);
    this.isSms = 0;
    this.planDt = null;
  }

  public int getPId() {
    return this.pId;
  }

  public ProjectReminderPlanDto setPId(int pId) {
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

  public ProjectReminderPlanDto setProjectId(int projectId) {
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

  public int getReminderUserId() {
    return this.reminderUserId;
  }

  public ProjectReminderPlanDto setReminderUserId(int reminderUserId) {
    this.reminderUserId = reminderUserId;
    setReminderUserIdIsSet(true);
    return this;
  }

  public void unsetReminderUserId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __REMINDERUSERID_ISSET_ID);
  }

  /** Returns true if field reminderUserId is set (has been assigned a value) and false otherwise */
  public boolean isSetReminderUserId() {
    return EncodingUtils.testBit(__isset_bitfield, __REMINDERUSERID_ISSET_ID);
  }

  public void setReminderUserIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __REMINDERUSERID_ISSET_ID, value);
  }

  public String getPlanBeginDt() {
    return this.planBeginDt;
  }

  public ProjectReminderPlanDto setPlanBeginDt(String planBeginDt) {
    this.planBeginDt = planBeginDt;
    return this;
  }

  public void unsetPlanBeginDt() {
    this.planBeginDt = null;
  }

  /** Returns true if field planBeginDt is set (has been assigned a value) and false otherwise */
  public boolean isSetPlanBeginDt() {
    return this.planBeginDt != null;
  }

  public void setPlanBeginDtIsSet(boolean value) {
    if (!value) {
      this.planBeginDt = null;
    }
  }

  public int getIsMail() {
    return this.isMail;
  }

  public ProjectReminderPlanDto setIsMail(int isMail) {
    this.isMail = isMail;
    setIsMailIsSet(true);
    return this;
  }

  public void unsetIsMail() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ISMAIL_ISSET_ID);
  }

  /** Returns true if field isMail is set (has been assigned a value) and false otherwise */
  public boolean isSetIsMail() {
    return EncodingUtils.testBit(__isset_bitfield, __ISMAIL_ISSET_ID);
  }

  public void setIsMailIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ISMAIL_ISSET_ID, value);
  }

  public int getIsSms() {
    return this.isSms;
  }

  public ProjectReminderPlanDto setIsSms(int isSms) {
    this.isSms = isSms;
    setIsSmsIsSet(true);
    return this;
  }

  public void unsetIsSms() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ISSMS_ISSET_ID);
  }

  /** Returns true if field isSms is set (has been assigned a value) and false otherwise */
  public boolean isSetIsSms() {
    return EncodingUtils.testBit(__isset_bitfield, __ISSMS_ISSET_ID);
  }

  public void setIsSmsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ISSMS_ISSET_ID, value);
  }

  public String getPlanDt() {
    return this.planDt;
  }

  public ProjectReminderPlanDto setPlanDt(String planDt) {
    this.planDt = planDt;
    return this;
  }

  public void unsetPlanDt() {
    this.planDt = null;
  }

  /** Returns true if field planDt is set (has been assigned a value) and false otherwise */
  public boolean isSetPlanDt() {
    return this.planDt != null;
  }

  public void setPlanDtIsSet(boolean value) {
    if (!value) {
      this.planDt = null;
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

    case REMINDER_USER_ID:
      if (value == null) {
        unsetReminderUserId();
      } else {
        setReminderUserId((Integer)value);
      }
      break;

    case PLAN_BEGIN_DT:
      if (value == null) {
        unsetPlanBeginDt();
      } else {
        setPlanBeginDt((String)value);
      }
      break;

    case IS_MAIL:
      if (value == null) {
        unsetIsMail();
      } else {
        setIsMail((Integer)value);
      }
      break;

    case IS_SMS:
      if (value == null) {
        unsetIsSms();
      } else {
        setIsSms((Integer)value);
      }
      break;

    case PLAN_DT:
      if (value == null) {
        unsetPlanDt();
      } else {
        setPlanDt((String)value);
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

    case REMINDER_USER_ID:
      return Integer.valueOf(getReminderUserId());

    case PLAN_BEGIN_DT:
      return getPlanBeginDt();

    case IS_MAIL:
      return Integer.valueOf(getIsMail());

    case IS_SMS:
      return Integer.valueOf(getIsSms());

    case PLAN_DT:
      return getPlanDt();

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
    case REMINDER_USER_ID:
      return isSetReminderUserId();
    case PLAN_BEGIN_DT:
      return isSetPlanBeginDt();
    case IS_MAIL:
      return isSetIsMail();
    case IS_SMS:
      return isSetIsSms();
    case PLAN_DT:
      return isSetPlanDt();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProjectReminderPlanDto)
      return this.equals((ProjectReminderPlanDto)that);
    return false;
  }

  public boolean equals(ProjectReminderPlanDto that) {
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

    boolean this_present_reminderUserId = true;
    boolean that_present_reminderUserId = true;
    if (this_present_reminderUserId || that_present_reminderUserId) {
      if (!(this_present_reminderUserId && that_present_reminderUserId))
        return false;
      if (this.reminderUserId != that.reminderUserId)
        return false;
    }

    boolean this_present_planBeginDt = true && this.isSetPlanBeginDt();
    boolean that_present_planBeginDt = true && that.isSetPlanBeginDt();
    if (this_present_planBeginDt || that_present_planBeginDt) {
      if (!(this_present_planBeginDt && that_present_planBeginDt))
        return false;
      if (!this.planBeginDt.equals(that.planBeginDt))
        return false;
    }

    boolean this_present_isMail = true;
    boolean that_present_isMail = true;
    if (this_present_isMail || that_present_isMail) {
      if (!(this_present_isMail && that_present_isMail))
        return false;
      if (this.isMail != that.isMail)
        return false;
    }

    boolean this_present_isSms = true;
    boolean that_present_isSms = true;
    if (this_present_isSms || that_present_isSms) {
      if (!(this_present_isSms && that_present_isSms))
        return false;
      if (this.isSms != that.isSms)
        return false;
    }

    boolean this_present_planDt = true && this.isSetPlanDt();
    boolean that_present_planDt = true && that.isSetPlanDt();
    if (this_present_planDt || that_present_planDt) {
      if (!(this_present_planDt && that_present_planDt))
        return false;
      if (!this.planDt.equals(that.planDt))
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

    boolean present_reminderUserId = true;
    list.add(present_reminderUserId);
    if (present_reminderUserId)
      list.add(reminderUserId);

    boolean present_planBeginDt = true && (isSetPlanBeginDt());
    list.add(present_planBeginDt);
    if (present_planBeginDt)
      list.add(planBeginDt);

    boolean present_isMail = true;
    list.add(present_isMail);
    if (present_isMail)
      list.add(isMail);

    boolean present_isSms = true;
    list.add(present_isSms);
    if (present_isSms)
      list.add(isSms);

    boolean present_planDt = true && (isSetPlanDt());
    list.add(present_planDt);
    if (present_planDt)
      list.add(planDt);

    return list.hashCode();
  }

  @Override
  public int compareTo(ProjectReminderPlanDto other) {
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
    lastComparison = Boolean.valueOf(isSetReminderUserId()).compareTo(other.isSetReminderUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReminderUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.reminderUserId, other.reminderUserId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPlanBeginDt()).compareTo(other.isSetPlanBeginDt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPlanBeginDt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.planBeginDt, other.planBeginDt);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIsMail()).compareTo(other.isSetIsMail());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsMail()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isMail, other.isMail);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIsSms()).compareTo(other.isSetIsSms());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsSms()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isSms, other.isSms);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPlanDt()).compareTo(other.isSetPlanDt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPlanDt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.planDt, other.planDt);
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
    StringBuilder sb = new StringBuilder("ProjectReminderPlanDto(");
    boolean first = true;

    sb.append("pId:");
    sb.append(this.pId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("projectId:");
    sb.append(this.projectId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("reminderUserId:");
    sb.append(this.reminderUserId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("planBeginDt:");
    if (this.planBeginDt == null) {
      sb.append("null");
    } else {
      sb.append(this.planBeginDt);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("isMail:");
    sb.append(this.isMail);
    first = false;
    if (!first) sb.append(", ");
    sb.append("isSms:");
    sb.append(this.isSms);
    first = false;
    if (!first) sb.append(", ");
    sb.append("planDt:");
    if (this.planDt == null) {
      sb.append("null");
    } else {
      sb.append(this.planDt);
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

  private static class ProjectReminderPlanDtoStandardSchemeFactory implements SchemeFactory {
    public ProjectReminderPlanDtoStandardScheme getScheme() {
      return new ProjectReminderPlanDtoStandardScheme();
    }
  }

  private static class ProjectReminderPlanDtoStandardScheme extends StandardScheme<ProjectReminderPlanDto> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProjectReminderPlanDto struct) throws org.apache.thrift.TException {
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
          case 3: // REMINDER_USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.reminderUserId = iprot.readI32();
              struct.setReminderUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PLAN_BEGIN_DT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.planBeginDt = iprot.readString();
              struct.setPlanBeginDtIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // IS_MAIL
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.isMail = iprot.readI32();
              struct.setIsMailIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // IS_SMS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.isSms = iprot.readI32();
              struct.setIsSmsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // PLAN_DT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.planDt = iprot.readString();
              struct.setPlanDtIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProjectReminderPlanDto struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(P_ID_FIELD_DESC);
      oprot.writeI32(struct.pId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PROJECT_ID_FIELD_DESC);
      oprot.writeI32(struct.projectId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(REMINDER_USER_ID_FIELD_DESC);
      oprot.writeI32(struct.reminderUserId);
      oprot.writeFieldEnd();
      if (struct.planBeginDt != null) {
        oprot.writeFieldBegin(PLAN_BEGIN_DT_FIELD_DESC);
        oprot.writeString(struct.planBeginDt);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(IS_MAIL_FIELD_DESC);
      oprot.writeI32(struct.isMail);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(IS_SMS_FIELD_DESC);
      oprot.writeI32(struct.isSms);
      oprot.writeFieldEnd();
      if (struct.planDt != null) {
        oprot.writeFieldBegin(PLAN_DT_FIELD_DESC);
        oprot.writeString(struct.planDt);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProjectReminderPlanDtoTupleSchemeFactory implements SchemeFactory {
    public ProjectReminderPlanDtoTupleScheme getScheme() {
      return new ProjectReminderPlanDtoTupleScheme();
    }
  }

  private static class ProjectReminderPlanDtoTupleScheme extends TupleScheme<ProjectReminderPlanDto> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProjectReminderPlanDto struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPId()) {
        optionals.set(0);
      }
      if (struct.isSetProjectId()) {
        optionals.set(1);
      }
      if (struct.isSetReminderUserId()) {
        optionals.set(2);
      }
      if (struct.isSetPlanBeginDt()) {
        optionals.set(3);
      }
      if (struct.isSetIsMail()) {
        optionals.set(4);
      }
      if (struct.isSetIsSms()) {
        optionals.set(5);
      }
      if (struct.isSetPlanDt()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetPId()) {
        oprot.writeI32(struct.pId);
      }
      if (struct.isSetProjectId()) {
        oprot.writeI32(struct.projectId);
      }
      if (struct.isSetReminderUserId()) {
        oprot.writeI32(struct.reminderUserId);
      }
      if (struct.isSetPlanBeginDt()) {
        oprot.writeString(struct.planBeginDt);
      }
      if (struct.isSetIsMail()) {
        oprot.writeI32(struct.isMail);
      }
      if (struct.isSetIsSms()) {
        oprot.writeI32(struct.isSms);
      }
      if (struct.isSetPlanDt()) {
        oprot.writeString(struct.planDt);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProjectReminderPlanDto struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.pId = iprot.readI32();
        struct.setPIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.projectId = iprot.readI32();
        struct.setProjectIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.reminderUserId = iprot.readI32();
        struct.setReminderUserIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.planBeginDt = iprot.readString();
        struct.setPlanBeginDtIsSet(true);
      }
      if (incoming.get(4)) {
        struct.isMail = iprot.readI32();
        struct.setIsMailIsSet(true);
      }
      if (incoming.get(5)) {
        struct.isSms = iprot.readI32();
        struct.setIsSmsIsSet(true);
      }
      if (incoming.get(6)) {
        struct.planDt = iprot.readString();
        struct.setPlanDtIsSet(true);
      }
    }
  }

}
