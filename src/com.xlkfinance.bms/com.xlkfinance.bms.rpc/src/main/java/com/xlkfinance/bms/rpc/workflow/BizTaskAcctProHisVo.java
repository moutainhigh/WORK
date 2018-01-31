/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xlkfinance.bms.rpc.workflow;

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
/**
 * 流程项目客户中间表*
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2017-12-18")
public class BizTaskAcctProHisVo implements org.apache.thrift.TBase<BizTaskAcctProHisVo, BizTaskAcctProHisVo._Fields>, java.io.Serializable, Cloneable, Comparable<BizTaskAcctProHisVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BizTaskAcctProHisVo");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField ACCT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("acctId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField PROJECT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("projectId", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField WORKFLOW_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("workflowId", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField PROJECT_WORKFLOW_STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("projectWorkflowStatus", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField BEGIN_DT_FIELD_DESC = new org.apache.thrift.protocol.TField("beginDt", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField END_DT_FIELD_DESC = new org.apache.thrift.protocol.TField("endDt", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BizTaskAcctProHisVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BizTaskAcctProHisVoTupleSchemeFactory());
  }

  public int pid; // required
  public int acctId; // required
  public int projectId; // required
  public String workflowId; // required
  public int projectWorkflowStatus; // required
  public String beginDt; // required
  public String endDt; // required
  public int status; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    ACCT_ID((short)2, "acctId"),
    PROJECT_ID((short)3, "projectId"),
    WORKFLOW_ID((short)4, "workflowId"),
    PROJECT_WORKFLOW_STATUS((short)5, "projectWorkflowStatus"),
    BEGIN_DT((short)6, "beginDt"),
    END_DT((short)7, "endDt"),
    STATUS((short)8, "status");

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
        case 2: // ACCT_ID
          return ACCT_ID;
        case 3: // PROJECT_ID
          return PROJECT_ID;
        case 4: // WORKFLOW_ID
          return WORKFLOW_ID;
        case 5: // PROJECT_WORKFLOW_STATUS
          return PROJECT_WORKFLOW_STATUS;
        case 6: // BEGIN_DT
          return BEGIN_DT;
        case 7: // END_DT
          return END_DT;
        case 8: // STATUS
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
  private static final int __ACCTID_ISSET_ID = 1;
  private static final int __PROJECTID_ISSET_ID = 2;
  private static final int __PROJECTWORKFLOWSTATUS_ISSET_ID = 3;
  private static final int __STATUS_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ACCT_ID, new org.apache.thrift.meta_data.FieldMetaData("acctId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PROJECT_ID, new org.apache.thrift.meta_data.FieldMetaData("projectId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.WORKFLOW_ID, new org.apache.thrift.meta_data.FieldMetaData("workflowId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PROJECT_WORKFLOW_STATUS, new org.apache.thrift.meta_data.FieldMetaData("projectWorkflowStatus", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.BEGIN_DT, new org.apache.thrift.meta_data.FieldMetaData("beginDt", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.END_DT, new org.apache.thrift.meta_data.FieldMetaData("endDt", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BizTaskAcctProHisVo.class, metaDataMap);
  }

  public BizTaskAcctProHisVo() {
  }

  public BizTaskAcctProHisVo(
    int pid,
    int acctId,
    int projectId,
    String workflowId,
    int projectWorkflowStatus,
    String beginDt,
    String endDt,
    int status)
  {
    this();
    this.pid = pid;
    setPidIsSet(true);
    this.acctId = acctId;
    setAcctIdIsSet(true);
    this.projectId = projectId;
    setProjectIdIsSet(true);
    this.workflowId = workflowId;
    this.projectWorkflowStatus = projectWorkflowStatus;
    setProjectWorkflowStatusIsSet(true);
    this.beginDt = beginDt;
    this.endDt = endDt;
    this.status = status;
    setStatusIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BizTaskAcctProHisVo(BizTaskAcctProHisVo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pid = other.pid;
    this.acctId = other.acctId;
    this.projectId = other.projectId;
    if (other.isSetWorkflowId()) {
      this.workflowId = other.workflowId;
    }
    this.projectWorkflowStatus = other.projectWorkflowStatus;
    if (other.isSetBeginDt()) {
      this.beginDt = other.beginDt;
    }
    if (other.isSetEndDt()) {
      this.endDt = other.endDt;
    }
    this.status = other.status;
  }

  public BizTaskAcctProHisVo deepCopy() {
    return new BizTaskAcctProHisVo(this);
  }

  @Override
  public void clear() {
    setPidIsSet(false);
    this.pid = 0;
    setAcctIdIsSet(false);
    this.acctId = 0;
    setProjectIdIsSet(false);
    this.projectId = 0;
    this.workflowId = null;
    setProjectWorkflowStatusIsSet(false);
    this.projectWorkflowStatus = 0;
    this.beginDt = null;
    this.endDt = null;
    setStatusIsSet(false);
    this.status = 0;
  }

  public int getPid() {
    return this.pid;
  }

  public BizTaskAcctProHisVo setPid(int pid) {
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

  public int getAcctId() {
    return this.acctId;
  }

  public BizTaskAcctProHisVo setAcctId(int acctId) {
    this.acctId = acctId;
    setAcctIdIsSet(true);
    return this;
  }

  public void unsetAcctId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ACCTID_ISSET_ID);
  }

  /** Returns true if field acctId is set (has been assigned a value) and false otherwise */
  public boolean isSetAcctId() {
    return EncodingUtils.testBit(__isset_bitfield, __ACCTID_ISSET_ID);
  }

  public void setAcctIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ACCTID_ISSET_ID, value);
  }

  public int getProjectId() {
    return this.projectId;
  }

  public BizTaskAcctProHisVo setProjectId(int projectId) {
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

  public String getWorkflowId() {
    return this.workflowId;
  }

  public BizTaskAcctProHisVo setWorkflowId(String workflowId) {
    this.workflowId = workflowId;
    return this;
  }

  public void unsetWorkflowId() {
    this.workflowId = null;
  }

  /** Returns true if field workflowId is set (has been assigned a value) and false otherwise */
  public boolean isSetWorkflowId() {
    return this.workflowId != null;
  }

  public void setWorkflowIdIsSet(boolean value) {
    if (!value) {
      this.workflowId = null;
    }
  }

  public int getProjectWorkflowStatus() {
    return this.projectWorkflowStatus;
  }

  public BizTaskAcctProHisVo setProjectWorkflowStatus(int projectWorkflowStatus) {
    this.projectWorkflowStatus = projectWorkflowStatus;
    setProjectWorkflowStatusIsSet(true);
    return this;
  }

  public void unsetProjectWorkflowStatus() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PROJECTWORKFLOWSTATUS_ISSET_ID);
  }

  /** Returns true if field projectWorkflowStatus is set (has been assigned a value) and false otherwise */
  public boolean isSetProjectWorkflowStatus() {
    return EncodingUtils.testBit(__isset_bitfield, __PROJECTWORKFLOWSTATUS_ISSET_ID);
  }

  public void setProjectWorkflowStatusIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PROJECTWORKFLOWSTATUS_ISSET_ID, value);
  }

  public String getBeginDt() {
    return this.beginDt;
  }

  public BizTaskAcctProHisVo setBeginDt(String beginDt) {
    this.beginDt = beginDt;
    return this;
  }

  public void unsetBeginDt() {
    this.beginDt = null;
  }

  /** Returns true if field beginDt is set (has been assigned a value) and false otherwise */
  public boolean isSetBeginDt() {
    return this.beginDt != null;
  }

  public void setBeginDtIsSet(boolean value) {
    if (!value) {
      this.beginDt = null;
    }
  }

  public String getEndDt() {
    return this.endDt;
  }

  public BizTaskAcctProHisVo setEndDt(String endDt) {
    this.endDt = endDt;
    return this;
  }

  public void unsetEndDt() {
    this.endDt = null;
  }

  /** Returns true if field endDt is set (has been assigned a value) and false otherwise */
  public boolean isSetEndDt() {
    return this.endDt != null;
  }

  public void setEndDtIsSet(boolean value) {
    if (!value) {
      this.endDt = null;
    }
  }

  public int getStatus() {
    return this.status;
  }

  public BizTaskAcctProHisVo setStatus(int status) {
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

    case ACCT_ID:
      if (value == null) {
        unsetAcctId();
      } else {
        setAcctId((Integer)value);
      }
      break;

    case PROJECT_ID:
      if (value == null) {
        unsetProjectId();
      } else {
        setProjectId((Integer)value);
      }
      break;

    case WORKFLOW_ID:
      if (value == null) {
        unsetWorkflowId();
      } else {
        setWorkflowId((String)value);
      }
      break;

    case PROJECT_WORKFLOW_STATUS:
      if (value == null) {
        unsetProjectWorkflowStatus();
      } else {
        setProjectWorkflowStatus((Integer)value);
      }
      break;

    case BEGIN_DT:
      if (value == null) {
        unsetBeginDt();
      } else {
        setBeginDt((String)value);
      }
      break;

    case END_DT:
      if (value == null) {
        unsetEndDt();
      } else {
        setEndDt((String)value);
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

    case ACCT_ID:
      return Integer.valueOf(getAcctId());

    case PROJECT_ID:
      return Integer.valueOf(getProjectId());

    case WORKFLOW_ID:
      return getWorkflowId();

    case PROJECT_WORKFLOW_STATUS:
      return Integer.valueOf(getProjectWorkflowStatus());

    case BEGIN_DT:
      return getBeginDt();

    case END_DT:
      return getEndDt();

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
    case ACCT_ID:
      return isSetAcctId();
    case PROJECT_ID:
      return isSetProjectId();
    case WORKFLOW_ID:
      return isSetWorkflowId();
    case PROJECT_WORKFLOW_STATUS:
      return isSetProjectWorkflowStatus();
    case BEGIN_DT:
      return isSetBeginDt();
    case END_DT:
      return isSetEndDt();
    case STATUS:
      return isSetStatus();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BizTaskAcctProHisVo)
      return this.equals((BizTaskAcctProHisVo)that);
    return false;
  }

  public boolean equals(BizTaskAcctProHisVo that) {
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

    boolean this_present_acctId = true;
    boolean that_present_acctId = true;
    if (this_present_acctId || that_present_acctId) {
      if (!(this_present_acctId && that_present_acctId))
        return false;
      if (this.acctId != that.acctId)
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

    boolean this_present_workflowId = true && this.isSetWorkflowId();
    boolean that_present_workflowId = true && that.isSetWorkflowId();
    if (this_present_workflowId || that_present_workflowId) {
      if (!(this_present_workflowId && that_present_workflowId))
        return false;
      if (!this.workflowId.equals(that.workflowId))
        return false;
    }

    boolean this_present_projectWorkflowStatus = true;
    boolean that_present_projectWorkflowStatus = true;
    if (this_present_projectWorkflowStatus || that_present_projectWorkflowStatus) {
      if (!(this_present_projectWorkflowStatus && that_present_projectWorkflowStatus))
        return false;
      if (this.projectWorkflowStatus != that.projectWorkflowStatus)
        return false;
    }

    boolean this_present_beginDt = true && this.isSetBeginDt();
    boolean that_present_beginDt = true && that.isSetBeginDt();
    if (this_present_beginDt || that_present_beginDt) {
      if (!(this_present_beginDt && that_present_beginDt))
        return false;
      if (!this.beginDt.equals(that.beginDt))
        return false;
    }

    boolean this_present_endDt = true && this.isSetEndDt();
    boolean that_present_endDt = true && that.isSetEndDt();
    if (this_present_endDt || that_present_endDt) {
      if (!(this_present_endDt && that_present_endDt))
        return false;
      if (!this.endDt.equals(that.endDt))
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

    boolean present_acctId = true;
    list.add(present_acctId);
    if (present_acctId)
      list.add(acctId);

    boolean present_projectId = true;
    list.add(present_projectId);
    if (present_projectId)
      list.add(projectId);

    boolean present_workflowId = true && (isSetWorkflowId());
    list.add(present_workflowId);
    if (present_workflowId)
      list.add(workflowId);

    boolean present_projectWorkflowStatus = true;
    list.add(present_projectWorkflowStatus);
    if (present_projectWorkflowStatus)
      list.add(projectWorkflowStatus);

    boolean present_beginDt = true && (isSetBeginDt());
    list.add(present_beginDt);
    if (present_beginDt)
      list.add(beginDt);

    boolean present_endDt = true && (isSetEndDt());
    list.add(present_endDt);
    if (present_endDt)
      list.add(endDt);

    boolean present_status = true;
    list.add(present_status);
    if (present_status)
      list.add(status);

    return list.hashCode();
  }

  @Override
  public int compareTo(BizTaskAcctProHisVo other) {
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
    lastComparison = Boolean.valueOf(isSetAcctId()).compareTo(other.isSetAcctId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAcctId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.acctId, other.acctId);
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
    lastComparison = Boolean.valueOf(isSetWorkflowId()).compareTo(other.isSetWorkflowId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWorkflowId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.workflowId, other.workflowId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProjectWorkflowStatus()).compareTo(other.isSetProjectWorkflowStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProjectWorkflowStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.projectWorkflowStatus, other.projectWorkflowStatus);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBeginDt()).compareTo(other.isSetBeginDt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBeginDt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.beginDt, other.beginDt);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEndDt()).compareTo(other.isSetEndDt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEndDt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.endDt, other.endDt);
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
    StringBuilder sb = new StringBuilder("BizTaskAcctProHisVo(");
    boolean first = true;

    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("acctId:");
    sb.append(this.acctId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("projectId:");
    sb.append(this.projectId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("workflowId:");
    if (this.workflowId == null) {
      sb.append("null");
    } else {
      sb.append(this.workflowId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("projectWorkflowStatus:");
    sb.append(this.projectWorkflowStatus);
    first = false;
    if (!first) sb.append(", ");
    sb.append("beginDt:");
    if (this.beginDt == null) {
      sb.append("null");
    } else {
      sb.append(this.beginDt);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("endDt:");
    if (this.endDt == null) {
      sb.append("null");
    } else {
      sb.append(this.endDt);
    }
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

  private static class BizTaskAcctProHisVoStandardSchemeFactory implements SchemeFactory {
    public BizTaskAcctProHisVoStandardScheme getScheme() {
      return new BizTaskAcctProHisVoStandardScheme();
    }
  }

  private static class BizTaskAcctProHisVoStandardScheme extends StandardScheme<BizTaskAcctProHisVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BizTaskAcctProHisVo struct) throws org.apache.thrift.TException {
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
          case 2: // ACCT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.acctId = iprot.readI32();
              struct.setAcctIdIsSet(true);
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
          case 4: // WORKFLOW_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.workflowId = iprot.readString();
              struct.setWorkflowIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PROJECT_WORKFLOW_STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.projectWorkflowStatus = iprot.readI32();
              struct.setProjectWorkflowStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // BEGIN_DT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.beginDt = iprot.readString();
              struct.setBeginDtIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // END_DT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.endDt = iprot.readString();
              struct.setEndDtIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // STATUS
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BizTaskAcctProHisVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PID_FIELD_DESC);
      oprot.writeI32(struct.pid);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ACCT_ID_FIELD_DESC);
      oprot.writeI32(struct.acctId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PROJECT_ID_FIELD_DESC);
      oprot.writeI32(struct.projectId);
      oprot.writeFieldEnd();
      if (struct.workflowId != null) {
        oprot.writeFieldBegin(WORKFLOW_ID_FIELD_DESC);
        oprot.writeString(struct.workflowId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(PROJECT_WORKFLOW_STATUS_FIELD_DESC);
      oprot.writeI32(struct.projectWorkflowStatus);
      oprot.writeFieldEnd();
      if (struct.beginDt != null) {
        oprot.writeFieldBegin(BEGIN_DT_FIELD_DESC);
        oprot.writeString(struct.beginDt);
        oprot.writeFieldEnd();
      }
      if (struct.endDt != null) {
        oprot.writeFieldBegin(END_DT_FIELD_DESC);
        oprot.writeString(struct.endDt);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(STATUS_FIELD_DESC);
      oprot.writeI32(struct.status);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BizTaskAcctProHisVoTupleSchemeFactory implements SchemeFactory {
    public BizTaskAcctProHisVoTupleScheme getScheme() {
      return new BizTaskAcctProHisVoTupleScheme();
    }
  }

  private static class BizTaskAcctProHisVoTupleScheme extends TupleScheme<BizTaskAcctProHisVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BizTaskAcctProHisVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPid()) {
        optionals.set(0);
      }
      if (struct.isSetAcctId()) {
        optionals.set(1);
      }
      if (struct.isSetProjectId()) {
        optionals.set(2);
      }
      if (struct.isSetWorkflowId()) {
        optionals.set(3);
      }
      if (struct.isSetProjectWorkflowStatus()) {
        optionals.set(4);
      }
      if (struct.isSetBeginDt()) {
        optionals.set(5);
      }
      if (struct.isSetEndDt()) {
        optionals.set(6);
      }
      if (struct.isSetStatus()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetPid()) {
        oprot.writeI32(struct.pid);
      }
      if (struct.isSetAcctId()) {
        oprot.writeI32(struct.acctId);
      }
      if (struct.isSetProjectId()) {
        oprot.writeI32(struct.projectId);
      }
      if (struct.isSetWorkflowId()) {
        oprot.writeString(struct.workflowId);
      }
      if (struct.isSetProjectWorkflowStatus()) {
        oprot.writeI32(struct.projectWorkflowStatus);
      }
      if (struct.isSetBeginDt()) {
        oprot.writeString(struct.beginDt);
      }
      if (struct.isSetEndDt()) {
        oprot.writeString(struct.endDt);
      }
      if (struct.isSetStatus()) {
        oprot.writeI32(struct.status);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BizTaskAcctProHisVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.pid = iprot.readI32();
        struct.setPidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.acctId = iprot.readI32();
        struct.setAcctIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.projectId = iprot.readI32();
        struct.setProjectIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.workflowId = iprot.readString();
        struct.setWorkflowIdIsSet(true);
      }
      if (incoming.get(4)) {
        struct.projectWorkflowStatus = iprot.readI32();
        struct.setProjectWorkflowStatusIsSet(true);
      }
      if (incoming.get(5)) {
        struct.beginDt = iprot.readString();
        struct.setBeginDtIsSet(true);
      }
      if (incoming.get(6)) {
        struct.endDt = iprot.readString();
        struct.setEndDtIsSet(true);
      }
      if (incoming.get(7)) {
        struct.status = iprot.readI32();
        struct.setStatusIsSet(true);
      }
    }
  }

}

