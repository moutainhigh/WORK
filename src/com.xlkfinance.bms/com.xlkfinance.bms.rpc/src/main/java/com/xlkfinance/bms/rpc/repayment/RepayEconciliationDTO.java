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
public class RepayEconciliationDTO implements org.apache.thrift.TBase<RepayEconciliationDTO, RepayEconciliationDTO._Fields>, java.io.Serializable, Cloneable, Comparable<RepayEconciliationDTO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("RepayEconciliationDTO");

  private static final org.apache.thrift.protocol.TField P_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("pId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField LOAN_INFO_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("loanInfoId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField RECONCILIATION_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("reconciliationType", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField RECONCILIATION_CYCLE_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("reconciliationCycleNum", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField REALTIME_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("realtimeId", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField RECONCILIATION_AMT_FIELD_DESC = new org.apache.thrift.protocol.TField("reconciliationAmt", org.apache.thrift.protocol.TType.DOUBLE, (short)6);
  private static final org.apache.thrift.protocol.TField DTL_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("dtlType", org.apache.thrift.protocol.TType.I32, (short)7);
  private static final org.apache.thrift.protocol.TField DTL_AMT_FIELD_DESC = new org.apache.thrift.protocol.TField("dtlAmt", org.apache.thrift.protocol.TType.DOUBLE, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new RepayEconciliationDTOStandardSchemeFactory());
    schemes.put(TupleScheme.class, new RepayEconciliationDTOTupleSchemeFactory());
  }

  public String pId; // required
  public String loanInfoId; // required
  public int reconciliationType; // required
  public int reconciliationCycleNum; // required
  public int realtimeId; // required
  public double reconciliationAmt; // required
  public int dtlType; // required
  public double dtlAmt; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    P_ID((short)1, "pId"),
    LOAN_INFO_ID((short)2, "loanInfoId"),
    RECONCILIATION_TYPE((short)3, "reconciliationType"),
    RECONCILIATION_CYCLE_NUM((short)4, "reconciliationCycleNum"),
    REALTIME_ID((short)5, "realtimeId"),
    RECONCILIATION_AMT((short)6, "reconciliationAmt"),
    DTL_TYPE((short)7, "dtlType"),
    DTL_AMT((short)8, "dtlAmt");

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
        case 2: // LOAN_INFO_ID
          return LOAN_INFO_ID;
        case 3: // RECONCILIATION_TYPE
          return RECONCILIATION_TYPE;
        case 4: // RECONCILIATION_CYCLE_NUM
          return RECONCILIATION_CYCLE_NUM;
        case 5: // REALTIME_ID
          return REALTIME_ID;
        case 6: // RECONCILIATION_AMT
          return RECONCILIATION_AMT;
        case 7: // DTL_TYPE
          return DTL_TYPE;
        case 8: // DTL_AMT
          return DTL_AMT;
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
  private static final int __RECONCILIATIONTYPE_ISSET_ID = 0;
  private static final int __RECONCILIATIONCYCLENUM_ISSET_ID = 1;
  private static final int __REALTIMEID_ISSET_ID = 2;
  private static final int __RECONCILIATIONAMT_ISSET_ID = 3;
  private static final int __DTLTYPE_ISSET_ID = 4;
  private static final int __DTLAMT_ISSET_ID = 5;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.P_ID, new org.apache.thrift.meta_data.FieldMetaData("pId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.LOAN_INFO_ID, new org.apache.thrift.meta_data.FieldMetaData("loanInfoId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.RECONCILIATION_TYPE, new org.apache.thrift.meta_data.FieldMetaData("reconciliationType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.RECONCILIATION_CYCLE_NUM, new org.apache.thrift.meta_data.FieldMetaData("reconciliationCycleNum", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.REALTIME_ID, new org.apache.thrift.meta_data.FieldMetaData("realtimeId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.RECONCILIATION_AMT, new org.apache.thrift.meta_data.FieldMetaData("reconciliationAmt", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.DTL_TYPE, new org.apache.thrift.meta_data.FieldMetaData("dtlType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.DTL_AMT, new org.apache.thrift.meta_data.FieldMetaData("dtlAmt", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(RepayEconciliationDTO.class, metaDataMap);
  }

  public RepayEconciliationDTO() {
  }

  public RepayEconciliationDTO(
    String pId,
    String loanInfoId,
    int reconciliationType,
    int reconciliationCycleNum,
    int realtimeId,
    double reconciliationAmt,
    int dtlType,
    double dtlAmt)
  {
    this();
    this.pId = pId;
    this.loanInfoId = loanInfoId;
    this.reconciliationType = reconciliationType;
    setReconciliationTypeIsSet(true);
    this.reconciliationCycleNum = reconciliationCycleNum;
    setReconciliationCycleNumIsSet(true);
    this.realtimeId = realtimeId;
    setRealtimeIdIsSet(true);
    this.reconciliationAmt = reconciliationAmt;
    setReconciliationAmtIsSet(true);
    this.dtlType = dtlType;
    setDtlTypeIsSet(true);
    this.dtlAmt = dtlAmt;
    setDtlAmtIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RepayEconciliationDTO(RepayEconciliationDTO other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetPId()) {
      this.pId = other.pId;
    }
    if (other.isSetLoanInfoId()) {
      this.loanInfoId = other.loanInfoId;
    }
    this.reconciliationType = other.reconciliationType;
    this.reconciliationCycleNum = other.reconciliationCycleNum;
    this.realtimeId = other.realtimeId;
    this.reconciliationAmt = other.reconciliationAmt;
    this.dtlType = other.dtlType;
    this.dtlAmt = other.dtlAmt;
  }

  public RepayEconciliationDTO deepCopy() {
    return new RepayEconciliationDTO(this);
  }

  @Override
  public void clear() {
    this.pId = null;
    this.loanInfoId = null;
    setReconciliationTypeIsSet(false);
    this.reconciliationType = 0;
    setReconciliationCycleNumIsSet(false);
    this.reconciliationCycleNum = 0;
    setRealtimeIdIsSet(false);
    this.realtimeId = 0;
    setReconciliationAmtIsSet(false);
    this.reconciliationAmt = 0.0;
    setDtlTypeIsSet(false);
    this.dtlType = 0;
    setDtlAmtIsSet(false);
    this.dtlAmt = 0.0;
  }

  public String getPId() {
    return this.pId;
  }

  public RepayEconciliationDTO setPId(String pId) {
    this.pId = pId;
    return this;
  }

  public void unsetPId() {
    this.pId = null;
  }

  /** Returns true if field pId is set (has been assigned a value) and false otherwise */
  public boolean isSetPId() {
    return this.pId != null;
  }

  public void setPIdIsSet(boolean value) {
    if (!value) {
      this.pId = null;
    }
  }

  public String getLoanInfoId() {
    return this.loanInfoId;
  }

  public RepayEconciliationDTO setLoanInfoId(String loanInfoId) {
    this.loanInfoId = loanInfoId;
    return this;
  }

  public void unsetLoanInfoId() {
    this.loanInfoId = null;
  }

  /** Returns true if field loanInfoId is set (has been assigned a value) and false otherwise */
  public boolean isSetLoanInfoId() {
    return this.loanInfoId != null;
  }

  public void setLoanInfoIdIsSet(boolean value) {
    if (!value) {
      this.loanInfoId = null;
    }
  }

  public int getReconciliationType() {
    return this.reconciliationType;
  }

  public RepayEconciliationDTO setReconciliationType(int reconciliationType) {
    this.reconciliationType = reconciliationType;
    setReconciliationTypeIsSet(true);
    return this;
  }

  public void unsetReconciliationType() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __RECONCILIATIONTYPE_ISSET_ID);
  }

  /** Returns true if field reconciliationType is set (has been assigned a value) and false otherwise */
  public boolean isSetReconciliationType() {
    return EncodingUtils.testBit(__isset_bitfield, __RECONCILIATIONTYPE_ISSET_ID);
  }

  public void setReconciliationTypeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __RECONCILIATIONTYPE_ISSET_ID, value);
  }

  public int getReconciliationCycleNum() {
    return this.reconciliationCycleNum;
  }

  public RepayEconciliationDTO setReconciliationCycleNum(int reconciliationCycleNum) {
    this.reconciliationCycleNum = reconciliationCycleNum;
    setReconciliationCycleNumIsSet(true);
    return this;
  }

  public void unsetReconciliationCycleNum() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __RECONCILIATIONCYCLENUM_ISSET_ID);
  }

  /** Returns true if field reconciliationCycleNum is set (has been assigned a value) and false otherwise */
  public boolean isSetReconciliationCycleNum() {
    return EncodingUtils.testBit(__isset_bitfield, __RECONCILIATIONCYCLENUM_ISSET_ID);
  }

  public void setReconciliationCycleNumIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __RECONCILIATIONCYCLENUM_ISSET_ID, value);
  }

  public int getRealtimeId() {
    return this.realtimeId;
  }

  public RepayEconciliationDTO setRealtimeId(int realtimeId) {
    this.realtimeId = realtimeId;
    setRealtimeIdIsSet(true);
    return this;
  }

  public void unsetRealtimeId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __REALTIMEID_ISSET_ID);
  }

  /** Returns true if field realtimeId is set (has been assigned a value) and false otherwise */
  public boolean isSetRealtimeId() {
    return EncodingUtils.testBit(__isset_bitfield, __REALTIMEID_ISSET_ID);
  }

  public void setRealtimeIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __REALTIMEID_ISSET_ID, value);
  }

  public double getReconciliationAmt() {
    return this.reconciliationAmt;
  }

  public RepayEconciliationDTO setReconciliationAmt(double reconciliationAmt) {
    this.reconciliationAmt = reconciliationAmt;
    setReconciliationAmtIsSet(true);
    return this;
  }

  public void unsetReconciliationAmt() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __RECONCILIATIONAMT_ISSET_ID);
  }

  /** Returns true if field reconciliationAmt is set (has been assigned a value) and false otherwise */
  public boolean isSetReconciliationAmt() {
    return EncodingUtils.testBit(__isset_bitfield, __RECONCILIATIONAMT_ISSET_ID);
  }

  public void setReconciliationAmtIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __RECONCILIATIONAMT_ISSET_ID, value);
  }

  public int getDtlType() {
    return this.dtlType;
  }

  public RepayEconciliationDTO setDtlType(int dtlType) {
    this.dtlType = dtlType;
    setDtlTypeIsSet(true);
    return this;
  }

  public void unsetDtlType() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DTLTYPE_ISSET_ID);
  }

  /** Returns true if field dtlType is set (has been assigned a value) and false otherwise */
  public boolean isSetDtlType() {
    return EncodingUtils.testBit(__isset_bitfield, __DTLTYPE_ISSET_ID);
  }

  public void setDtlTypeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DTLTYPE_ISSET_ID, value);
  }

  public double getDtlAmt() {
    return this.dtlAmt;
  }

  public RepayEconciliationDTO setDtlAmt(double dtlAmt) {
    this.dtlAmt = dtlAmt;
    setDtlAmtIsSet(true);
    return this;
  }

  public void unsetDtlAmt() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DTLAMT_ISSET_ID);
  }

  /** Returns true if field dtlAmt is set (has been assigned a value) and false otherwise */
  public boolean isSetDtlAmt() {
    return EncodingUtils.testBit(__isset_bitfield, __DTLAMT_ISSET_ID);
  }

  public void setDtlAmtIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DTLAMT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case P_ID:
      if (value == null) {
        unsetPId();
      } else {
        setPId((String)value);
      }
      break;

    case LOAN_INFO_ID:
      if (value == null) {
        unsetLoanInfoId();
      } else {
        setLoanInfoId((String)value);
      }
      break;

    case RECONCILIATION_TYPE:
      if (value == null) {
        unsetReconciliationType();
      } else {
        setReconciliationType((Integer)value);
      }
      break;

    case RECONCILIATION_CYCLE_NUM:
      if (value == null) {
        unsetReconciliationCycleNum();
      } else {
        setReconciliationCycleNum((Integer)value);
      }
      break;

    case REALTIME_ID:
      if (value == null) {
        unsetRealtimeId();
      } else {
        setRealtimeId((Integer)value);
      }
      break;

    case RECONCILIATION_AMT:
      if (value == null) {
        unsetReconciliationAmt();
      } else {
        setReconciliationAmt((Double)value);
      }
      break;

    case DTL_TYPE:
      if (value == null) {
        unsetDtlType();
      } else {
        setDtlType((Integer)value);
      }
      break;

    case DTL_AMT:
      if (value == null) {
        unsetDtlAmt();
      } else {
        setDtlAmt((Double)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case P_ID:
      return getPId();

    case LOAN_INFO_ID:
      return getLoanInfoId();

    case RECONCILIATION_TYPE:
      return Integer.valueOf(getReconciliationType());

    case RECONCILIATION_CYCLE_NUM:
      return Integer.valueOf(getReconciliationCycleNum());

    case REALTIME_ID:
      return Integer.valueOf(getRealtimeId());

    case RECONCILIATION_AMT:
      return Double.valueOf(getReconciliationAmt());

    case DTL_TYPE:
      return Integer.valueOf(getDtlType());

    case DTL_AMT:
      return Double.valueOf(getDtlAmt());

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
    case LOAN_INFO_ID:
      return isSetLoanInfoId();
    case RECONCILIATION_TYPE:
      return isSetReconciliationType();
    case RECONCILIATION_CYCLE_NUM:
      return isSetReconciliationCycleNum();
    case REALTIME_ID:
      return isSetRealtimeId();
    case RECONCILIATION_AMT:
      return isSetReconciliationAmt();
    case DTL_TYPE:
      return isSetDtlType();
    case DTL_AMT:
      return isSetDtlAmt();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof RepayEconciliationDTO)
      return this.equals((RepayEconciliationDTO)that);
    return false;
  }

  public boolean equals(RepayEconciliationDTO that) {
    if (that == null)
      return false;

    boolean this_present_pId = true && this.isSetPId();
    boolean that_present_pId = true && that.isSetPId();
    if (this_present_pId || that_present_pId) {
      if (!(this_present_pId && that_present_pId))
        return false;
      if (!this.pId.equals(that.pId))
        return false;
    }

    boolean this_present_loanInfoId = true && this.isSetLoanInfoId();
    boolean that_present_loanInfoId = true && that.isSetLoanInfoId();
    if (this_present_loanInfoId || that_present_loanInfoId) {
      if (!(this_present_loanInfoId && that_present_loanInfoId))
        return false;
      if (!this.loanInfoId.equals(that.loanInfoId))
        return false;
    }

    boolean this_present_reconciliationType = true;
    boolean that_present_reconciliationType = true;
    if (this_present_reconciliationType || that_present_reconciliationType) {
      if (!(this_present_reconciliationType && that_present_reconciliationType))
        return false;
      if (this.reconciliationType != that.reconciliationType)
        return false;
    }

    boolean this_present_reconciliationCycleNum = true;
    boolean that_present_reconciliationCycleNum = true;
    if (this_present_reconciliationCycleNum || that_present_reconciliationCycleNum) {
      if (!(this_present_reconciliationCycleNum && that_present_reconciliationCycleNum))
        return false;
      if (this.reconciliationCycleNum != that.reconciliationCycleNum)
        return false;
    }

    boolean this_present_realtimeId = true;
    boolean that_present_realtimeId = true;
    if (this_present_realtimeId || that_present_realtimeId) {
      if (!(this_present_realtimeId && that_present_realtimeId))
        return false;
      if (this.realtimeId != that.realtimeId)
        return false;
    }

    boolean this_present_reconciliationAmt = true;
    boolean that_present_reconciliationAmt = true;
    if (this_present_reconciliationAmt || that_present_reconciliationAmt) {
      if (!(this_present_reconciliationAmt && that_present_reconciliationAmt))
        return false;
      if (this.reconciliationAmt != that.reconciliationAmt)
        return false;
    }

    boolean this_present_dtlType = true;
    boolean that_present_dtlType = true;
    if (this_present_dtlType || that_present_dtlType) {
      if (!(this_present_dtlType && that_present_dtlType))
        return false;
      if (this.dtlType != that.dtlType)
        return false;
    }

    boolean this_present_dtlAmt = true;
    boolean that_present_dtlAmt = true;
    if (this_present_dtlAmt || that_present_dtlAmt) {
      if (!(this_present_dtlAmt && that_present_dtlAmt))
        return false;
      if (this.dtlAmt != that.dtlAmt)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_pId = true && (isSetPId());
    list.add(present_pId);
    if (present_pId)
      list.add(pId);

    boolean present_loanInfoId = true && (isSetLoanInfoId());
    list.add(present_loanInfoId);
    if (present_loanInfoId)
      list.add(loanInfoId);

    boolean present_reconciliationType = true;
    list.add(present_reconciliationType);
    if (present_reconciliationType)
      list.add(reconciliationType);

    boolean present_reconciliationCycleNum = true;
    list.add(present_reconciliationCycleNum);
    if (present_reconciliationCycleNum)
      list.add(reconciliationCycleNum);

    boolean present_realtimeId = true;
    list.add(present_realtimeId);
    if (present_realtimeId)
      list.add(realtimeId);

    boolean present_reconciliationAmt = true;
    list.add(present_reconciliationAmt);
    if (present_reconciliationAmt)
      list.add(reconciliationAmt);

    boolean present_dtlType = true;
    list.add(present_dtlType);
    if (present_dtlType)
      list.add(dtlType);

    boolean present_dtlAmt = true;
    list.add(present_dtlAmt);
    if (present_dtlAmt)
      list.add(dtlAmt);

    return list.hashCode();
  }

  @Override
  public int compareTo(RepayEconciliationDTO other) {
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
    lastComparison = Boolean.valueOf(isSetLoanInfoId()).compareTo(other.isSetLoanInfoId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLoanInfoId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.loanInfoId, other.loanInfoId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReconciliationType()).compareTo(other.isSetReconciliationType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReconciliationType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.reconciliationType, other.reconciliationType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReconciliationCycleNum()).compareTo(other.isSetReconciliationCycleNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReconciliationCycleNum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.reconciliationCycleNum, other.reconciliationCycleNum);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRealtimeId()).compareTo(other.isSetRealtimeId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRealtimeId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.realtimeId, other.realtimeId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReconciliationAmt()).compareTo(other.isSetReconciliationAmt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReconciliationAmt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.reconciliationAmt, other.reconciliationAmt);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDtlType()).compareTo(other.isSetDtlType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDtlType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dtlType, other.dtlType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDtlAmt()).compareTo(other.isSetDtlAmt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDtlAmt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dtlAmt, other.dtlAmt);
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
    StringBuilder sb = new StringBuilder("RepayEconciliationDTO(");
    boolean first = true;

    sb.append("pId:");
    if (this.pId == null) {
      sb.append("null");
    } else {
      sb.append(this.pId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("loanInfoId:");
    if (this.loanInfoId == null) {
      sb.append("null");
    } else {
      sb.append(this.loanInfoId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("reconciliationType:");
    sb.append(this.reconciliationType);
    first = false;
    if (!first) sb.append(", ");
    sb.append("reconciliationCycleNum:");
    sb.append(this.reconciliationCycleNum);
    first = false;
    if (!first) sb.append(", ");
    sb.append("realtimeId:");
    sb.append(this.realtimeId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("reconciliationAmt:");
    sb.append(this.reconciliationAmt);
    first = false;
    if (!first) sb.append(", ");
    sb.append("dtlType:");
    sb.append(this.dtlType);
    first = false;
    if (!first) sb.append(", ");
    sb.append("dtlAmt:");
    sb.append(this.dtlAmt);
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

  private static class RepayEconciliationDTOStandardSchemeFactory implements SchemeFactory {
    public RepayEconciliationDTOStandardScheme getScheme() {
      return new RepayEconciliationDTOStandardScheme();
    }
  }

  private static class RepayEconciliationDTOStandardScheme extends StandardScheme<RepayEconciliationDTO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, RepayEconciliationDTO struct) throws org.apache.thrift.TException {
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
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.pId = iprot.readString();
              struct.setPIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // LOAN_INFO_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.loanInfoId = iprot.readString();
              struct.setLoanInfoIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // RECONCILIATION_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.reconciliationType = iprot.readI32();
              struct.setReconciliationTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // RECONCILIATION_CYCLE_NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.reconciliationCycleNum = iprot.readI32();
              struct.setReconciliationCycleNumIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // REALTIME_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.realtimeId = iprot.readI32();
              struct.setRealtimeIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // RECONCILIATION_AMT
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.reconciliationAmt = iprot.readDouble();
              struct.setReconciliationAmtIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // DTL_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.dtlType = iprot.readI32();
              struct.setDtlTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // DTL_AMT
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.dtlAmt = iprot.readDouble();
              struct.setDtlAmtIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, RepayEconciliationDTO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.pId != null) {
        oprot.writeFieldBegin(P_ID_FIELD_DESC);
        oprot.writeString(struct.pId);
        oprot.writeFieldEnd();
      }
      if (struct.loanInfoId != null) {
        oprot.writeFieldBegin(LOAN_INFO_ID_FIELD_DESC);
        oprot.writeString(struct.loanInfoId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(RECONCILIATION_TYPE_FIELD_DESC);
      oprot.writeI32(struct.reconciliationType);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(RECONCILIATION_CYCLE_NUM_FIELD_DESC);
      oprot.writeI32(struct.reconciliationCycleNum);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(REALTIME_ID_FIELD_DESC);
      oprot.writeI32(struct.realtimeId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(RECONCILIATION_AMT_FIELD_DESC);
      oprot.writeDouble(struct.reconciliationAmt);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(DTL_TYPE_FIELD_DESC);
      oprot.writeI32(struct.dtlType);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(DTL_AMT_FIELD_DESC);
      oprot.writeDouble(struct.dtlAmt);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RepayEconciliationDTOTupleSchemeFactory implements SchemeFactory {
    public RepayEconciliationDTOTupleScheme getScheme() {
      return new RepayEconciliationDTOTupleScheme();
    }
  }

  private static class RepayEconciliationDTOTupleScheme extends TupleScheme<RepayEconciliationDTO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, RepayEconciliationDTO struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPId()) {
        optionals.set(0);
      }
      if (struct.isSetLoanInfoId()) {
        optionals.set(1);
      }
      if (struct.isSetReconciliationType()) {
        optionals.set(2);
      }
      if (struct.isSetReconciliationCycleNum()) {
        optionals.set(3);
      }
      if (struct.isSetRealtimeId()) {
        optionals.set(4);
      }
      if (struct.isSetReconciliationAmt()) {
        optionals.set(5);
      }
      if (struct.isSetDtlType()) {
        optionals.set(6);
      }
      if (struct.isSetDtlAmt()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetPId()) {
        oprot.writeString(struct.pId);
      }
      if (struct.isSetLoanInfoId()) {
        oprot.writeString(struct.loanInfoId);
      }
      if (struct.isSetReconciliationType()) {
        oprot.writeI32(struct.reconciliationType);
      }
      if (struct.isSetReconciliationCycleNum()) {
        oprot.writeI32(struct.reconciliationCycleNum);
      }
      if (struct.isSetRealtimeId()) {
        oprot.writeI32(struct.realtimeId);
      }
      if (struct.isSetReconciliationAmt()) {
        oprot.writeDouble(struct.reconciliationAmt);
      }
      if (struct.isSetDtlType()) {
        oprot.writeI32(struct.dtlType);
      }
      if (struct.isSetDtlAmt()) {
        oprot.writeDouble(struct.dtlAmt);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, RepayEconciliationDTO struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.pId = iprot.readString();
        struct.setPIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.loanInfoId = iprot.readString();
        struct.setLoanInfoIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.reconciliationType = iprot.readI32();
        struct.setReconciliationTypeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.reconciliationCycleNum = iprot.readI32();
        struct.setReconciliationCycleNumIsSet(true);
      }
      if (incoming.get(4)) {
        struct.realtimeId = iprot.readI32();
        struct.setRealtimeIdIsSet(true);
      }
      if (incoming.get(5)) {
        struct.reconciliationAmt = iprot.readDouble();
        struct.setReconciliationAmtIsSet(true);
      }
      if (incoming.get(6)) {
        struct.dtlType = iprot.readI32();
        struct.setDtlTypeIsSet(true);
      }
      if (incoming.get(7)) {
        struct.dtlAmt = iprot.readDouble();
        struct.setDtlAmtIsSet(true);
      }
    }
  }

}

