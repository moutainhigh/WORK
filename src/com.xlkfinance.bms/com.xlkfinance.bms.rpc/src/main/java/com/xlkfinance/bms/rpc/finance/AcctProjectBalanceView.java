/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xlkfinance.bms.rpc.finance;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2017-3-7")
public class AcctProjectBalanceView implements org.apache.thrift.TBase<AcctProjectBalanceView, AcctProjectBalanceView._Fields>, java.io.Serializable, Cloneable, Comparable<AcctProjectBalanceView> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("AcctProjectBalanceView");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField ACCT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("acctId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField ACCT_AMT_FIELD_DESC = new org.apache.thrift.protocol.TField("acctAmt", org.apache.thrift.protocol.TType.DOUBLE, (short)3);
  private static final org.apache.thrift.protocol.TField BALANCE_AMT_FIELD_DESC = new org.apache.thrift.protocol.TField("balanceAmt", org.apache.thrift.protocol.TType.DOUBLE, (short)4);
  private static final org.apache.thrift.protocol.TField PROJECT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("projectId", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField ACCT_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("acctName", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField DATE_VERSION_FIELD_DESC = new org.apache.thrift.protocol.TField("dateVersion", org.apache.thrift.protocol.TType.I32, (short)7);
  private static final org.apache.thrift.protocol.TField RECEIVABLES_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("receivablesId", org.apache.thrift.protocol.TType.I32, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new AcctProjectBalanceViewStandardSchemeFactory());
    schemes.put(TupleScheme.class, new AcctProjectBalanceViewTupleSchemeFactory());
  }

  public int pid; // required
  public int acctId; // required
  public double acctAmt; // required
  public double balanceAmt; // required
  public int projectId; // required
  public String acctName; // required
  public int dateVersion; // required
  public int receivablesId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    ACCT_ID((short)2, "acctId"),
    ACCT_AMT((short)3, "acctAmt"),
    BALANCE_AMT((short)4, "balanceAmt"),
    PROJECT_ID((short)5, "projectId"),
    ACCT_NAME((short)6, "acctName"),
    DATE_VERSION((short)7, "dateVersion"),
    RECEIVABLES_ID((short)8, "receivablesId");

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
        case 3: // ACCT_AMT
          return ACCT_AMT;
        case 4: // BALANCE_AMT
          return BALANCE_AMT;
        case 5: // PROJECT_ID
          return PROJECT_ID;
        case 6: // ACCT_NAME
          return ACCT_NAME;
        case 7: // DATE_VERSION
          return DATE_VERSION;
        case 8: // RECEIVABLES_ID
          return RECEIVABLES_ID;
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
  private static final int __ACCTAMT_ISSET_ID = 2;
  private static final int __BALANCEAMT_ISSET_ID = 3;
  private static final int __PROJECTID_ISSET_ID = 4;
  private static final int __DATEVERSION_ISSET_ID = 5;
  private static final int __RECEIVABLESID_ISSET_ID = 6;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ACCT_ID, new org.apache.thrift.meta_data.FieldMetaData("acctId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ACCT_AMT, new org.apache.thrift.meta_data.FieldMetaData("acctAmt", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.BALANCE_AMT, new org.apache.thrift.meta_data.FieldMetaData("balanceAmt", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.PROJECT_ID, new org.apache.thrift.meta_data.FieldMetaData("projectId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ACCT_NAME, new org.apache.thrift.meta_data.FieldMetaData("acctName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DATE_VERSION, new org.apache.thrift.meta_data.FieldMetaData("dateVersion", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.RECEIVABLES_ID, new org.apache.thrift.meta_data.FieldMetaData("receivablesId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(AcctProjectBalanceView.class, metaDataMap);
  }

  public AcctProjectBalanceView() {
  }

  public AcctProjectBalanceView(
    int pid,
    int acctId,
    double acctAmt,
    double balanceAmt,
    int projectId,
    String acctName,
    int dateVersion,
    int receivablesId)
  {
    this();
    this.pid = pid;
    setPidIsSet(true);
    this.acctId = acctId;
    setAcctIdIsSet(true);
    this.acctAmt = acctAmt;
    setAcctAmtIsSet(true);
    this.balanceAmt = balanceAmt;
    setBalanceAmtIsSet(true);
    this.projectId = projectId;
    setProjectIdIsSet(true);
    this.acctName = acctName;
    this.dateVersion = dateVersion;
    setDateVersionIsSet(true);
    this.receivablesId = receivablesId;
    setReceivablesIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public AcctProjectBalanceView(AcctProjectBalanceView other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pid = other.pid;
    this.acctId = other.acctId;
    this.acctAmt = other.acctAmt;
    this.balanceAmt = other.balanceAmt;
    this.projectId = other.projectId;
    if (other.isSetAcctName()) {
      this.acctName = other.acctName;
    }
    this.dateVersion = other.dateVersion;
    this.receivablesId = other.receivablesId;
  }

  public AcctProjectBalanceView deepCopy() {
    return new AcctProjectBalanceView(this);
  }

  @Override
  public void clear() {
    setPidIsSet(false);
    this.pid = 0;
    setAcctIdIsSet(false);
    this.acctId = 0;
    setAcctAmtIsSet(false);
    this.acctAmt = 0.0;
    setBalanceAmtIsSet(false);
    this.balanceAmt = 0.0;
    setProjectIdIsSet(false);
    this.projectId = 0;
    this.acctName = null;
    setDateVersionIsSet(false);
    this.dateVersion = 0;
    setReceivablesIdIsSet(false);
    this.receivablesId = 0;
  }

  public int getPid() {
    return this.pid;
  }

  public AcctProjectBalanceView setPid(int pid) {
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

  public AcctProjectBalanceView setAcctId(int acctId) {
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

  public double getAcctAmt() {
    return this.acctAmt;
  }

  public AcctProjectBalanceView setAcctAmt(double acctAmt) {
    this.acctAmt = acctAmt;
    setAcctAmtIsSet(true);
    return this;
  }

  public void unsetAcctAmt() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ACCTAMT_ISSET_ID);
  }

  /** Returns true if field acctAmt is set (has been assigned a value) and false otherwise */
  public boolean isSetAcctAmt() {
    return EncodingUtils.testBit(__isset_bitfield, __ACCTAMT_ISSET_ID);
  }

  public void setAcctAmtIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ACCTAMT_ISSET_ID, value);
  }

  public double getBalanceAmt() {
    return this.balanceAmt;
  }

  public AcctProjectBalanceView setBalanceAmt(double balanceAmt) {
    this.balanceAmt = balanceAmt;
    setBalanceAmtIsSet(true);
    return this;
  }

  public void unsetBalanceAmt() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __BALANCEAMT_ISSET_ID);
  }

  /** Returns true if field balanceAmt is set (has been assigned a value) and false otherwise */
  public boolean isSetBalanceAmt() {
    return EncodingUtils.testBit(__isset_bitfield, __BALANCEAMT_ISSET_ID);
  }

  public void setBalanceAmtIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __BALANCEAMT_ISSET_ID, value);
  }

  public int getProjectId() {
    return this.projectId;
  }

  public AcctProjectBalanceView setProjectId(int projectId) {
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

  public String getAcctName() {
    return this.acctName;
  }

  public AcctProjectBalanceView setAcctName(String acctName) {
    this.acctName = acctName;
    return this;
  }

  public void unsetAcctName() {
    this.acctName = null;
  }

  /** Returns true if field acctName is set (has been assigned a value) and false otherwise */
  public boolean isSetAcctName() {
    return this.acctName != null;
  }

  public void setAcctNameIsSet(boolean value) {
    if (!value) {
      this.acctName = null;
    }
  }

  public int getDateVersion() {
    return this.dateVersion;
  }

  public AcctProjectBalanceView setDateVersion(int dateVersion) {
    this.dateVersion = dateVersion;
    setDateVersionIsSet(true);
    return this;
  }

  public void unsetDateVersion() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DATEVERSION_ISSET_ID);
  }

  /** Returns true if field dateVersion is set (has been assigned a value) and false otherwise */
  public boolean isSetDateVersion() {
    return EncodingUtils.testBit(__isset_bitfield, __DATEVERSION_ISSET_ID);
  }

  public void setDateVersionIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DATEVERSION_ISSET_ID, value);
  }

  public int getReceivablesId() {
    return this.receivablesId;
  }

  public AcctProjectBalanceView setReceivablesId(int receivablesId) {
    this.receivablesId = receivablesId;
    setReceivablesIdIsSet(true);
    return this;
  }

  public void unsetReceivablesId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __RECEIVABLESID_ISSET_ID);
  }

  /** Returns true if field receivablesId is set (has been assigned a value) and false otherwise */
  public boolean isSetReceivablesId() {
    return EncodingUtils.testBit(__isset_bitfield, __RECEIVABLESID_ISSET_ID);
  }

  public void setReceivablesIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __RECEIVABLESID_ISSET_ID, value);
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

    case ACCT_AMT:
      if (value == null) {
        unsetAcctAmt();
      } else {
        setAcctAmt((Double)value);
      }
      break;

    case BALANCE_AMT:
      if (value == null) {
        unsetBalanceAmt();
      } else {
        setBalanceAmt((Double)value);
      }
      break;

    case PROJECT_ID:
      if (value == null) {
        unsetProjectId();
      } else {
        setProjectId((Integer)value);
      }
      break;

    case ACCT_NAME:
      if (value == null) {
        unsetAcctName();
      } else {
        setAcctName((String)value);
      }
      break;

    case DATE_VERSION:
      if (value == null) {
        unsetDateVersion();
      } else {
        setDateVersion((Integer)value);
      }
      break;

    case RECEIVABLES_ID:
      if (value == null) {
        unsetReceivablesId();
      } else {
        setReceivablesId((Integer)value);
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

    case ACCT_AMT:
      return Double.valueOf(getAcctAmt());

    case BALANCE_AMT:
      return Double.valueOf(getBalanceAmt());

    case PROJECT_ID:
      return Integer.valueOf(getProjectId());

    case ACCT_NAME:
      return getAcctName();

    case DATE_VERSION:
      return Integer.valueOf(getDateVersion());

    case RECEIVABLES_ID:
      return Integer.valueOf(getReceivablesId());

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
    case ACCT_AMT:
      return isSetAcctAmt();
    case BALANCE_AMT:
      return isSetBalanceAmt();
    case PROJECT_ID:
      return isSetProjectId();
    case ACCT_NAME:
      return isSetAcctName();
    case DATE_VERSION:
      return isSetDateVersion();
    case RECEIVABLES_ID:
      return isSetReceivablesId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof AcctProjectBalanceView)
      return this.equals((AcctProjectBalanceView)that);
    return false;
  }

  public boolean equals(AcctProjectBalanceView that) {
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

    boolean this_present_acctAmt = true;
    boolean that_present_acctAmt = true;
    if (this_present_acctAmt || that_present_acctAmt) {
      if (!(this_present_acctAmt && that_present_acctAmt))
        return false;
      if (this.acctAmt != that.acctAmt)
        return false;
    }

    boolean this_present_balanceAmt = true;
    boolean that_present_balanceAmt = true;
    if (this_present_balanceAmt || that_present_balanceAmt) {
      if (!(this_present_balanceAmt && that_present_balanceAmt))
        return false;
      if (this.balanceAmt != that.balanceAmt)
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

    boolean this_present_acctName = true && this.isSetAcctName();
    boolean that_present_acctName = true && that.isSetAcctName();
    if (this_present_acctName || that_present_acctName) {
      if (!(this_present_acctName && that_present_acctName))
        return false;
      if (!this.acctName.equals(that.acctName))
        return false;
    }

    boolean this_present_dateVersion = true;
    boolean that_present_dateVersion = true;
    if (this_present_dateVersion || that_present_dateVersion) {
      if (!(this_present_dateVersion && that_present_dateVersion))
        return false;
      if (this.dateVersion != that.dateVersion)
        return false;
    }

    boolean this_present_receivablesId = true;
    boolean that_present_receivablesId = true;
    if (this_present_receivablesId || that_present_receivablesId) {
      if (!(this_present_receivablesId && that_present_receivablesId))
        return false;
      if (this.receivablesId != that.receivablesId)
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

    boolean present_acctAmt = true;
    list.add(present_acctAmt);
    if (present_acctAmt)
      list.add(acctAmt);

    boolean present_balanceAmt = true;
    list.add(present_balanceAmt);
    if (present_balanceAmt)
      list.add(balanceAmt);

    boolean present_projectId = true;
    list.add(present_projectId);
    if (present_projectId)
      list.add(projectId);

    boolean present_acctName = true && (isSetAcctName());
    list.add(present_acctName);
    if (present_acctName)
      list.add(acctName);

    boolean present_dateVersion = true;
    list.add(present_dateVersion);
    if (present_dateVersion)
      list.add(dateVersion);

    boolean present_receivablesId = true;
    list.add(present_receivablesId);
    if (present_receivablesId)
      list.add(receivablesId);

    return list.hashCode();
  }

  @Override
  public int compareTo(AcctProjectBalanceView other) {
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
    lastComparison = Boolean.valueOf(isSetAcctAmt()).compareTo(other.isSetAcctAmt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAcctAmt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.acctAmt, other.acctAmt);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBalanceAmt()).compareTo(other.isSetBalanceAmt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBalanceAmt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.balanceAmt, other.balanceAmt);
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
    lastComparison = Boolean.valueOf(isSetAcctName()).compareTo(other.isSetAcctName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAcctName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.acctName, other.acctName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDateVersion()).compareTo(other.isSetDateVersion());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDateVersion()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dateVersion, other.dateVersion);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReceivablesId()).compareTo(other.isSetReceivablesId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReceivablesId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.receivablesId, other.receivablesId);
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
    StringBuilder sb = new StringBuilder("AcctProjectBalanceView(");
    boolean first = true;

    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("acctId:");
    sb.append(this.acctId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("acctAmt:");
    sb.append(this.acctAmt);
    first = false;
    if (!first) sb.append(", ");
    sb.append("balanceAmt:");
    sb.append(this.balanceAmt);
    first = false;
    if (!first) sb.append(", ");
    sb.append("projectId:");
    sb.append(this.projectId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("acctName:");
    if (this.acctName == null) {
      sb.append("null");
    } else {
      sb.append(this.acctName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("dateVersion:");
    sb.append(this.dateVersion);
    first = false;
    if (!first) sb.append(", ");
    sb.append("receivablesId:");
    sb.append(this.receivablesId);
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

  private static class AcctProjectBalanceViewStandardSchemeFactory implements SchemeFactory {
    public AcctProjectBalanceViewStandardScheme getScheme() {
      return new AcctProjectBalanceViewStandardScheme();
    }
  }

  private static class AcctProjectBalanceViewStandardScheme extends StandardScheme<AcctProjectBalanceView> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, AcctProjectBalanceView struct) throws org.apache.thrift.TException {
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
          case 3: // ACCT_AMT
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.acctAmt = iprot.readDouble();
              struct.setAcctAmtIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // BALANCE_AMT
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.balanceAmt = iprot.readDouble();
              struct.setBalanceAmtIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PROJECT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.projectId = iprot.readI32();
              struct.setProjectIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // ACCT_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.acctName = iprot.readString();
              struct.setAcctNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // DATE_VERSION
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.dateVersion = iprot.readI32();
              struct.setDateVersionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // RECEIVABLES_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.receivablesId = iprot.readI32();
              struct.setReceivablesIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, AcctProjectBalanceView struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PID_FIELD_DESC);
      oprot.writeI32(struct.pid);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ACCT_ID_FIELD_DESC);
      oprot.writeI32(struct.acctId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ACCT_AMT_FIELD_DESC);
      oprot.writeDouble(struct.acctAmt);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(BALANCE_AMT_FIELD_DESC);
      oprot.writeDouble(struct.balanceAmt);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PROJECT_ID_FIELD_DESC);
      oprot.writeI32(struct.projectId);
      oprot.writeFieldEnd();
      if (struct.acctName != null) {
        oprot.writeFieldBegin(ACCT_NAME_FIELD_DESC);
        oprot.writeString(struct.acctName);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(DATE_VERSION_FIELD_DESC);
      oprot.writeI32(struct.dateVersion);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(RECEIVABLES_ID_FIELD_DESC);
      oprot.writeI32(struct.receivablesId);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class AcctProjectBalanceViewTupleSchemeFactory implements SchemeFactory {
    public AcctProjectBalanceViewTupleScheme getScheme() {
      return new AcctProjectBalanceViewTupleScheme();
    }
  }

  private static class AcctProjectBalanceViewTupleScheme extends TupleScheme<AcctProjectBalanceView> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, AcctProjectBalanceView struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPid()) {
        optionals.set(0);
      }
      if (struct.isSetAcctId()) {
        optionals.set(1);
      }
      if (struct.isSetAcctAmt()) {
        optionals.set(2);
      }
      if (struct.isSetBalanceAmt()) {
        optionals.set(3);
      }
      if (struct.isSetProjectId()) {
        optionals.set(4);
      }
      if (struct.isSetAcctName()) {
        optionals.set(5);
      }
      if (struct.isSetDateVersion()) {
        optionals.set(6);
      }
      if (struct.isSetReceivablesId()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetPid()) {
        oprot.writeI32(struct.pid);
      }
      if (struct.isSetAcctId()) {
        oprot.writeI32(struct.acctId);
      }
      if (struct.isSetAcctAmt()) {
        oprot.writeDouble(struct.acctAmt);
      }
      if (struct.isSetBalanceAmt()) {
        oprot.writeDouble(struct.balanceAmt);
      }
      if (struct.isSetProjectId()) {
        oprot.writeI32(struct.projectId);
      }
      if (struct.isSetAcctName()) {
        oprot.writeString(struct.acctName);
      }
      if (struct.isSetDateVersion()) {
        oprot.writeI32(struct.dateVersion);
      }
      if (struct.isSetReceivablesId()) {
        oprot.writeI32(struct.receivablesId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, AcctProjectBalanceView struct) throws org.apache.thrift.TException {
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
        struct.acctAmt = iprot.readDouble();
        struct.setAcctAmtIsSet(true);
      }
      if (incoming.get(3)) {
        struct.balanceAmt = iprot.readDouble();
        struct.setBalanceAmtIsSet(true);
      }
      if (incoming.get(4)) {
        struct.projectId = iprot.readI32();
        struct.setProjectIdIsSet(true);
      }
      if (incoming.get(5)) {
        struct.acctName = iprot.readString();
        struct.setAcctNameIsSet(true);
      }
      if (incoming.get(6)) {
        struct.dateVersion = iprot.readI32();
        struct.setDateVersionIsSet(true);
      }
      if (incoming.get(7)) {
        struct.receivablesId = iprot.readI32();
        struct.setReceivablesIdIsSet(true);
      }
    }
  }

}
