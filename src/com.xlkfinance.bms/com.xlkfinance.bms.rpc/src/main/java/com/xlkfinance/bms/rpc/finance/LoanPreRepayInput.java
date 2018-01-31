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
public class LoanPreRepayInput implements org.apache.thrift.TBase<LoanPreRepayInput, LoanPreRepayInput._Fields>, java.io.Serializable, Cloneable, Comparable<LoanPreRepayInput> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("LoanPreRepayInput");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PRE_REPAY_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("preRepayId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField INPUT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("inputId", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField RECEIVED_AMT_FIELD_DESC = new org.apache.thrift.protocol.TField("receivedAmt", org.apache.thrift.protocol.TType.DOUBLE, (short)4);
  private static final org.apache.thrift.protocol.TField RECEIVED_FINES_FIELD_DESC = new org.apache.thrift.protocol.TField("receivedFines", org.apache.thrift.protocol.TType.DOUBLE, (short)5);
  private static final org.apache.thrift.protocol.TField UNCOLLECTED_AMT_FIELD_DESC = new org.apache.thrift.protocol.TField("uncollectedAmt", org.apache.thrift.protocol.TType.DOUBLE, (short)6);
  private static final org.apache.thrift.protocol.TField UNCOLLECTED_FINES_FIELD_DESC = new org.apache.thrift.protocol.TField("uncollectedFines", org.apache.thrift.protocol.TType.DOUBLE, (short)7);
  private static final org.apache.thrift.protocol.TField REMARK_FIELD_DESC = new org.apache.thrift.protocol.TField("remark", org.apache.thrift.protocol.TType.STRING, (short)8);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short)9);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new LoanPreRepayInputStandardSchemeFactory());
    schemes.put(TupleScheme.class, new LoanPreRepayInputTupleSchemeFactory());
  }

  public int pid; // required
  public int preRepayId; // required
  public int inputId; // required
  public double receivedAmt; // required
  public double receivedFines; // required
  public double uncollectedAmt; // required
  public double uncollectedFines; // required
  public String remark; // required
  public int status; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    PRE_REPAY_ID((short)2, "preRepayId"),
    INPUT_ID((short)3, "inputId"),
    RECEIVED_AMT((short)4, "receivedAmt"),
    RECEIVED_FINES((short)5, "receivedFines"),
    UNCOLLECTED_AMT((short)6, "uncollectedAmt"),
    UNCOLLECTED_FINES((short)7, "uncollectedFines"),
    REMARK((short)8, "remark"),
    STATUS((short)9, "status");

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
        case 2: // PRE_REPAY_ID
          return PRE_REPAY_ID;
        case 3: // INPUT_ID
          return INPUT_ID;
        case 4: // RECEIVED_AMT
          return RECEIVED_AMT;
        case 5: // RECEIVED_FINES
          return RECEIVED_FINES;
        case 6: // UNCOLLECTED_AMT
          return UNCOLLECTED_AMT;
        case 7: // UNCOLLECTED_FINES
          return UNCOLLECTED_FINES;
        case 8: // REMARK
          return REMARK;
        case 9: // STATUS
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
  private static final int __PREREPAYID_ISSET_ID = 1;
  private static final int __INPUTID_ISSET_ID = 2;
  private static final int __RECEIVEDAMT_ISSET_ID = 3;
  private static final int __RECEIVEDFINES_ISSET_ID = 4;
  private static final int __UNCOLLECTEDAMT_ISSET_ID = 5;
  private static final int __UNCOLLECTEDFINES_ISSET_ID = 6;
  private static final int __STATUS_ISSET_ID = 7;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PRE_REPAY_ID, new org.apache.thrift.meta_data.FieldMetaData("preRepayId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.INPUT_ID, new org.apache.thrift.meta_data.FieldMetaData("inputId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.RECEIVED_AMT, new org.apache.thrift.meta_data.FieldMetaData("receivedAmt", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.RECEIVED_FINES, new org.apache.thrift.meta_data.FieldMetaData("receivedFines", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.UNCOLLECTED_AMT, new org.apache.thrift.meta_data.FieldMetaData("uncollectedAmt", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.UNCOLLECTED_FINES, new org.apache.thrift.meta_data.FieldMetaData("uncollectedFines", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.REMARK, new org.apache.thrift.meta_data.FieldMetaData("remark", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(LoanPreRepayInput.class, metaDataMap);
  }

  public LoanPreRepayInput() {
  }

  public LoanPreRepayInput(
    int pid,
    int preRepayId,
    int inputId,
    double receivedAmt,
    double receivedFines,
    double uncollectedAmt,
    double uncollectedFines,
    String remark,
    int status)
  {
    this();
    this.pid = pid;
    setPidIsSet(true);
    this.preRepayId = preRepayId;
    setPreRepayIdIsSet(true);
    this.inputId = inputId;
    setInputIdIsSet(true);
    this.receivedAmt = receivedAmt;
    setReceivedAmtIsSet(true);
    this.receivedFines = receivedFines;
    setReceivedFinesIsSet(true);
    this.uncollectedAmt = uncollectedAmt;
    setUncollectedAmtIsSet(true);
    this.uncollectedFines = uncollectedFines;
    setUncollectedFinesIsSet(true);
    this.remark = remark;
    this.status = status;
    setStatusIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public LoanPreRepayInput(LoanPreRepayInput other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pid = other.pid;
    this.preRepayId = other.preRepayId;
    this.inputId = other.inputId;
    this.receivedAmt = other.receivedAmt;
    this.receivedFines = other.receivedFines;
    this.uncollectedAmt = other.uncollectedAmt;
    this.uncollectedFines = other.uncollectedFines;
    if (other.isSetRemark()) {
      this.remark = other.remark;
    }
    this.status = other.status;
  }

  public LoanPreRepayInput deepCopy() {
    return new LoanPreRepayInput(this);
  }

  @Override
  public void clear() {
    setPidIsSet(false);
    this.pid = 0;
    setPreRepayIdIsSet(false);
    this.preRepayId = 0;
    setInputIdIsSet(false);
    this.inputId = 0;
    setReceivedAmtIsSet(false);
    this.receivedAmt = 0.0;
    setReceivedFinesIsSet(false);
    this.receivedFines = 0.0;
    setUncollectedAmtIsSet(false);
    this.uncollectedAmt = 0.0;
    setUncollectedFinesIsSet(false);
    this.uncollectedFines = 0.0;
    this.remark = null;
    setStatusIsSet(false);
    this.status = 0;
  }

  public int getPid() {
    return this.pid;
  }

  public LoanPreRepayInput setPid(int pid) {
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

  public int getPreRepayId() {
    return this.preRepayId;
  }

  public LoanPreRepayInput setPreRepayId(int preRepayId) {
    this.preRepayId = preRepayId;
    setPreRepayIdIsSet(true);
    return this;
  }

  public void unsetPreRepayId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PREREPAYID_ISSET_ID);
  }

  /** Returns true if field preRepayId is set (has been assigned a value) and false otherwise */
  public boolean isSetPreRepayId() {
    return EncodingUtils.testBit(__isset_bitfield, __PREREPAYID_ISSET_ID);
  }

  public void setPreRepayIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PREREPAYID_ISSET_ID, value);
  }

  public int getInputId() {
    return this.inputId;
  }

  public LoanPreRepayInput setInputId(int inputId) {
    this.inputId = inputId;
    setInputIdIsSet(true);
    return this;
  }

  public void unsetInputId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __INPUTID_ISSET_ID);
  }

  /** Returns true if field inputId is set (has been assigned a value) and false otherwise */
  public boolean isSetInputId() {
    return EncodingUtils.testBit(__isset_bitfield, __INPUTID_ISSET_ID);
  }

  public void setInputIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __INPUTID_ISSET_ID, value);
  }

  public double getReceivedAmt() {
    return this.receivedAmt;
  }

  public LoanPreRepayInput setReceivedAmt(double receivedAmt) {
    this.receivedAmt = receivedAmt;
    setReceivedAmtIsSet(true);
    return this;
  }

  public void unsetReceivedAmt() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __RECEIVEDAMT_ISSET_ID);
  }

  /** Returns true if field receivedAmt is set (has been assigned a value) and false otherwise */
  public boolean isSetReceivedAmt() {
    return EncodingUtils.testBit(__isset_bitfield, __RECEIVEDAMT_ISSET_ID);
  }

  public void setReceivedAmtIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __RECEIVEDAMT_ISSET_ID, value);
  }

  public double getReceivedFines() {
    return this.receivedFines;
  }

  public LoanPreRepayInput setReceivedFines(double receivedFines) {
    this.receivedFines = receivedFines;
    setReceivedFinesIsSet(true);
    return this;
  }

  public void unsetReceivedFines() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __RECEIVEDFINES_ISSET_ID);
  }

  /** Returns true if field receivedFines is set (has been assigned a value) and false otherwise */
  public boolean isSetReceivedFines() {
    return EncodingUtils.testBit(__isset_bitfield, __RECEIVEDFINES_ISSET_ID);
  }

  public void setReceivedFinesIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __RECEIVEDFINES_ISSET_ID, value);
  }

  public double getUncollectedAmt() {
    return this.uncollectedAmt;
  }

  public LoanPreRepayInput setUncollectedAmt(double uncollectedAmt) {
    this.uncollectedAmt = uncollectedAmt;
    setUncollectedAmtIsSet(true);
    return this;
  }

  public void unsetUncollectedAmt() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __UNCOLLECTEDAMT_ISSET_ID);
  }

  /** Returns true if field uncollectedAmt is set (has been assigned a value) and false otherwise */
  public boolean isSetUncollectedAmt() {
    return EncodingUtils.testBit(__isset_bitfield, __UNCOLLECTEDAMT_ISSET_ID);
  }

  public void setUncollectedAmtIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __UNCOLLECTEDAMT_ISSET_ID, value);
  }

  public double getUncollectedFines() {
    return this.uncollectedFines;
  }

  public LoanPreRepayInput setUncollectedFines(double uncollectedFines) {
    this.uncollectedFines = uncollectedFines;
    setUncollectedFinesIsSet(true);
    return this;
  }

  public void unsetUncollectedFines() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __UNCOLLECTEDFINES_ISSET_ID);
  }

  /** Returns true if field uncollectedFines is set (has been assigned a value) and false otherwise */
  public boolean isSetUncollectedFines() {
    return EncodingUtils.testBit(__isset_bitfield, __UNCOLLECTEDFINES_ISSET_ID);
  }

  public void setUncollectedFinesIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __UNCOLLECTEDFINES_ISSET_ID, value);
  }

  public String getRemark() {
    return this.remark;
  }

  public LoanPreRepayInput setRemark(String remark) {
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

  public int getStatus() {
    return this.status;
  }

  public LoanPreRepayInput setStatus(int status) {
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

    case PRE_REPAY_ID:
      if (value == null) {
        unsetPreRepayId();
      } else {
        setPreRepayId((Integer)value);
      }
      break;

    case INPUT_ID:
      if (value == null) {
        unsetInputId();
      } else {
        setInputId((Integer)value);
      }
      break;

    case RECEIVED_AMT:
      if (value == null) {
        unsetReceivedAmt();
      } else {
        setReceivedAmt((Double)value);
      }
      break;

    case RECEIVED_FINES:
      if (value == null) {
        unsetReceivedFines();
      } else {
        setReceivedFines((Double)value);
      }
      break;

    case UNCOLLECTED_AMT:
      if (value == null) {
        unsetUncollectedAmt();
      } else {
        setUncollectedAmt((Double)value);
      }
      break;

    case UNCOLLECTED_FINES:
      if (value == null) {
        unsetUncollectedFines();
      } else {
        setUncollectedFines((Double)value);
      }
      break;

    case REMARK:
      if (value == null) {
        unsetRemark();
      } else {
        setRemark((String)value);
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

    case PRE_REPAY_ID:
      return Integer.valueOf(getPreRepayId());

    case INPUT_ID:
      return Integer.valueOf(getInputId());

    case RECEIVED_AMT:
      return Double.valueOf(getReceivedAmt());

    case RECEIVED_FINES:
      return Double.valueOf(getReceivedFines());

    case UNCOLLECTED_AMT:
      return Double.valueOf(getUncollectedAmt());

    case UNCOLLECTED_FINES:
      return Double.valueOf(getUncollectedFines());

    case REMARK:
      return getRemark();

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
    case PRE_REPAY_ID:
      return isSetPreRepayId();
    case INPUT_ID:
      return isSetInputId();
    case RECEIVED_AMT:
      return isSetReceivedAmt();
    case RECEIVED_FINES:
      return isSetReceivedFines();
    case UNCOLLECTED_AMT:
      return isSetUncollectedAmt();
    case UNCOLLECTED_FINES:
      return isSetUncollectedFines();
    case REMARK:
      return isSetRemark();
    case STATUS:
      return isSetStatus();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof LoanPreRepayInput)
      return this.equals((LoanPreRepayInput)that);
    return false;
  }

  public boolean equals(LoanPreRepayInput that) {
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

    boolean this_present_preRepayId = true;
    boolean that_present_preRepayId = true;
    if (this_present_preRepayId || that_present_preRepayId) {
      if (!(this_present_preRepayId && that_present_preRepayId))
        return false;
      if (this.preRepayId != that.preRepayId)
        return false;
    }

    boolean this_present_inputId = true;
    boolean that_present_inputId = true;
    if (this_present_inputId || that_present_inputId) {
      if (!(this_present_inputId && that_present_inputId))
        return false;
      if (this.inputId != that.inputId)
        return false;
    }

    boolean this_present_receivedAmt = true;
    boolean that_present_receivedAmt = true;
    if (this_present_receivedAmt || that_present_receivedAmt) {
      if (!(this_present_receivedAmt && that_present_receivedAmt))
        return false;
      if (this.receivedAmt != that.receivedAmt)
        return false;
    }

    boolean this_present_receivedFines = true;
    boolean that_present_receivedFines = true;
    if (this_present_receivedFines || that_present_receivedFines) {
      if (!(this_present_receivedFines && that_present_receivedFines))
        return false;
      if (this.receivedFines != that.receivedFines)
        return false;
    }

    boolean this_present_uncollectedAmt = true;
    boolean that_present_uncollectedAmt = true;
    if (this_present_uncollectedAmt || that_present_uncollectedAmt) {
      if (!(this_present_uncollectedAmt && that_present_uncollectedAmt))
        return false;
      if (this.uncollectedAmt != that.uncollectedAmt)
        return false;
    }

    boolean this_present_uncollectedFines = true;
    boolean that_present_uncollectedFines = true;
    if (this_present_uncollectedFines || that_present_uncollectedFines) {
      if (!(this_present_uncollectedFines && that_present_uncollectedFines))
        return false;
      if (this.uncollectedFines != that.uncollectedFines)
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

    boolean present_preRepayId = true;
    list.add(present_preRepayId);
    if (present_preRepayId)
      list.add(preRepayId);

    boolean present_inputId = true;
    list.add(present_inputId);
    if (present_inputId)
      list.add(inputId);

    boolean present_receivedAmt = true;
    list.add(present_receivedAmt);
    if (present_receivedAmt)
      list.add(receivedAmt);

    boolean present_receivedFines = true;
    list.add(present_receivedFines);
    if (present_receivedFines)
      list.add(receivedFines);

    boolean present_uncollectedAmt = true;
    list.add(present_uncollectedAmt);
    if (present_uncollectedAmt)
      list.add(uncollectedAmt);

    boolean present_uncollectedFines = true;
    list.add(present_uncollectedFines);
    if (present_uncollectedFines)
      list.add(uncollectedFines);

    boolean present_remark = true && (isSetRemark());
    list.add(present_remark);
    if (present_remark)
      list.add(remark);

    boolean present_status = true;
    list.add(present_status);
    if (present_status)
      list.add(status);

    return list.hashCode();
  }

  @Override
  public int compareTo(LoanPreRepayInput other) {
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
    lastComparison = Boolean.valueOf(isSetPreRepayId()).compareTo(other.isSetPreRepayId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPreRepayId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.preRepayId, other.preRepayId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetInputId()).compareTo(other.isSetInputId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInputId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.inputId, other.inputId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReceivedAmt()).compareTo(other.isSetReceivedAmt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReceivedAmt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.receivedAmt, other.receivedAmt);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReceivedFines()).compareTo(other.isSetReceivedFines());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReceivedFines()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.receivedFines, other.receivedFines);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUncollectedAmt()).compareTo(other.isSetUncollectedAmt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUncollectedAmt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.uncollectedAmt, other.uncollectedAmt);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUncollectedFines()).compareTo(other.isSetUncollectedFines());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUncollectedFines()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.uncollectedFines, other.uncollectedFines);
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
    StringBuilder sb = new StringBuilder("LoanPreRepayInput(");
    boolean first = true;

    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("preRepayId:");
    sb.append(this.preRepayId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("inputId:");
    sb.append(this.inputId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("receivedAmt:");
    sb.append(this.receivedAmt);
    first = false;
    if (!first) sb.append(", ");
    sb.append("receivedFines:");
    sb.append(this.receivedFines);
    first = false;
    if (!first) sb.append(", ");
    sb.append("uncollectedAmt:");
    sb.append(this.uncollectedAmt);
    first = false;
    if (!first) sb.append(", ");
    sb.append("uncollectedFines:");
    sb.append(this.uncollectedFines);
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

  private static class LoanPreRepayInputStandardSchemeFactory implements SchemeFactory {
    public LoanPreRepayInputStandardScheme getScheme() {
      return new LoanPreRepayInputStandardScheme();
    }
  }

  private static class LoanPreRepayInputStandardScheme extends StandardScheme<LoanPreRepayInput> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, LoanPreRepayInput struct) throws org.apache.thrift.TException {
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
          case 2: // PRE_REPAY_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.preRepayId = iprot.readI32();
              struct.setPreRepayIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // INPUT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.inputId = iprot.readI32();
              struct.setInputIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // RECEIVED_AMT
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.receivedAmt = iprot.readDouble();
              struct.setReceivedAmtIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // RECEIVED_FINES
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.receivedFines = iprot.readDouble();
              struct.setReceivedFinesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // UNCOLLECTED_AMT
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.uncollectedAmt = iprot.readDouble();
              struct.setUncollectedAmtIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // UNCOLLECTED_FINES
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.uncollectedFines = iprot.readDouble();
              struct.setUncollectedFinesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // REMARK
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.remark = iprot.readString();
              struct.setRemarkIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 9: // STATUS
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, LoanPreRepayInput struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PID_FIELD_DESC);
      oprot.writeI32(struct.pid);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PRE_REPAY_ID_FIELD_DESC);
      oprot.writeI32(struct.preRepayId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(INPUT_ID_FIELD_DESC);
      oprot.writeI32(struct.inputId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(RECEIVED_AMT_FIELD_DESC);
      oprot.writeDouble(struct.receivedAmt);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(RECEIVED_FINES_FIELD_DESC);
      oprot.writeDouble(struct.receivedFines);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(UNCOLLECTED_AMT_FIELD_DESC);
      oprot.writeDouble(struct.uncollectedAmt);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(UNCOLLECTED_FINES_FIELD_DESC);
      oprot.writeDouble(struct.uncollectedFines);
      oprot.writeFieldEnd();
      if (struct.remark != null) {
        oprot.writeFieldBegin(REMARK_FIELD_DESC);
        oprot.writeString(struct.remark);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(STATUS_FIELD_DESC);
      oprot.writeI32(struct.status);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class LoanPreRepayInputTupleSchemeFactory implements SchemeFactory {
    public LoanPreRepayInputTupleScheme getScheme() {
      return new LoanPreRepayInputTupleScheme();
    }
  }

  private static class LoanPreRepayInputTupleScheme extends TupleScheme<LoanPreRepayInput> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, LoanPreRepayInput struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPid()) {
        optionals.set(0);
      }
      if (struct.isSetPreRepayId()) {
        optionals.set(1);
      }
      if (struct.isSetInputId()) {
        optionals.set(2);
      }
      if (struct.isSetReceivedAmt()) {
        optionals.set(3);
      }
      if (struct.isSetReceivedFines()) {
        optionals.set(4);
      }
      if (struct.isSetUncollectedAmt()) {
        optionals.set(5);
      }
      if (struct.isSetUncollectedFines()) {
        optionals.set(6);
      }
      if (struct.isSetRemark()) {
        optionals.set(7);
      }
      if (struct.isSetStatus()) {
        optionals.set(8);
      }
      oprot.writeBitSet(optionals, 9);
      if (struct.isSetPid()) {
        oprot.writeI32(struct.pid);
      }
      if (struct.isSetPreRepayId()) {
        oprot.writeI32(struct.preRepayId);
      }
      if (struct.isSetInputId()) {
        oprot.writeI32(struct.inputId);
      }
      if (struct.isSetReceivedAmt()) {
        oprot.writeDouble(struct.receivedAmt);
      }
      if (struct.isSetReceivedFines()) {
        oprot.writeDouble(struct.receivedFines);
      }
      if (struct.isSetUncollectedAmt()) {
        oprot.writeDouble(struct.uncollectedAmt);
      }
      if (struct.isSetUncollectedFines()) {
        oprot.writeDouble(struct.uncollectedFines);
      }
      if (struct.isSetRemark()) {
        oprot.writeString(struct.remark);
      }
      if (struct.isSetStatus()) {
        oprot.writeI32(struct.status);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, LoanPreRepayInput struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(9);
      if (incoming.get(0)) {
        struct.pid = iprot.readI32();
        struct.setPidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.preRepayId = iprot.readI32();
        struct.setPreRepayIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.inputId = iprot.readI32();
        struct.setInputIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.receivedAmt = iprot.readDouble();
        struct.setReceivedAmtIsSet(true);
      }
      if (incoming.get(4)) {
        struct.receivedFines = iprot.readDouble();
        struct.setReceivedFinesIsSet(true);
      }
      if (incoming.get(5)) {
        struct.uncollectedAmt = iprot.readDouble();
        struct.setUncollectedAmtIsSet(true);
      }
      if (incoming.get(6)) {
        struct.uncollectedFines = iprot.readDouble();
        struct.setUncollectedFinesIsSet(true);
      }
      if (incoming.get(7)) {
        struct.remark = iprot.readString();
        struct.setRemarkIsSet(true);
      }
      if (incoming.get(8)) {
        struct.status = iprot.readI32();
        struct.setStatusIsSet(true);
      }
    }
  }

}

