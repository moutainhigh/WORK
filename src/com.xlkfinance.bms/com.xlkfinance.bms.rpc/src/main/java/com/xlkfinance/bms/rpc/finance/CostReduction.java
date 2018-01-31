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
public class CostReduction implements org.apache.thrift.TBase<CostReduction, CostReduction._Fields>, java.io.Serializable, Cloneable, Comparable<CostReduction> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CostReduction");

  private static final org.apache.thrift.protocol.TField P_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("pId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField LOAN_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("loanId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField CYCLE_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("cycleNum", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField MANG_COST_FIELD_DESC = new org.apache.thrift.protocol.TField("mangCost", org.apache.thrift.protocol.TType.DOUBLE, (short)7);
  private static final org.apache.thrift.protocol.TField OTHER_COST_FIELD_DESC = new org.apache.thrift.protocol.TField("otherCost", org.apache.thrift.protocol.TType.DOUBLE, (short)8);
  private static final org.apache.thrift.protocol.TField INTEREST_FIELD_DESC = new org.apache.thrift.protocol.TField("interest", org.apache.thrift.protocol.TType.DOUBLE, (short)9);
  private static final org.apache.thrift.protocol.TField OVERDUE_INTEREST_FIELD_DESC = new org.apache.thrift.protocol.TField("overdueInterest", org.apache.thrift.protocol.TType.DOUBLE, (short)10);
  private static final org.apache.thrift.protocol.TField OVERDUE_PUNITIVE_FIELD_DESC = new org.apache.thrift.protocol.TField("overduePunitive", org.apache.thrift.protocol.TType.DOUBLE, (short)11);
  private static final org.apache.thrift.protocol.TField REPAY_DT_FIELD_DESC = new org.apache.thrift.protocol.TField("repayDt", org.apache.thrift.protocol.TType.STRING, (short)12);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CostReductionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CostReductionTupleSchemeFactory());
  }

  public int pId; // required
  public int loanId; // required
  public int cycleNum; // required
  public double mangCost; // required
  public double otherCost; // required
  public double interest; // required
  public double overdueInterest; // required
  public double overduePunitive; // required
  public String repayDt; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    P_ID((short)1, "pId"),
    LOAN_ID((short)2, "loanId"),
    CYCLE_NUM((short)3, "cycleNum"),
    MANG_COST((short)7, "mangCost"),
    OTHER_COST((short)8, "otherCost"),
    INTEREST((short)9, "interest"),
    OVERDUE_INTEREST((short)10, "overdueInterest"),
    OVERDUE_PUNITIVE((short)11, "overduePunitive"),
    REPAY_DT((short)12, "repayDt");

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
        case 2: // LOAN_ID
          return LOAN_ID;
        case 3: // CYCLE_NUM
          return CYCLE_NUM;
        case 7: // MANG_COST
          return MANG_COST;
        case 8: // OTHER_COST
          return OTHER_COST;
        case 9: // INTEREST
          return INTEREST;
        case 10: // OVERDUE_INTEREST
          return OVERDUE_INTEREST;
        case 11: // OVERDUE_PUNITIVE
          return OVERDUE_PUNITIVE;
        case 12: // REPAY_DT
          return REPAY_DT;
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
  private static final int __LOANID_ISSET_ID = 1;
  private static final int __CYCLENUM_ISSET_ID = 2;
  private static final int __MANGCOST_ISSET_ID = 3;
  private static final int __OTHERCOST_ISSET_ID = 4;
  private static final int __INTEREST_ISSET_ID = 5;
  private static final int __OVERDUEINTEREST_ISSET_ID = 6;
  private static final int __OVERDUEPUNITIVE_ISSET_ID = 7;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.P_ID, new org.apache.thrift.meta_data.FieldMetaData("pId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LOAN_ID, new org.apache.thrift.meta_data.FieldMetaData("loanId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CYCLE_NUM, new org.apache.thrift.meta_data.FieldMetaData("cycleNum", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MANG_COST, new org.apache.thrift.meta_data.FieldMetaData("mangCost", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.OTHER_COST, new org.apache.thrift.meta_data.FieldMetaData("otherCost", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.INTEREST, new org.apache.thrift.meta_data.FieldMetaData("interest", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.OVERDUE_INTEREST, new org.apache.thrift.meta_data.FieldMetaData("overdueInterest", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.OVERDUE_PUNITIVE, new org.apache.thrift.meta_data.FieldMetaData("overduePunitive", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.REPAY_DT, new org.apache.thrift.meta_data.FieldMetaData("repayDt", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CostReduction.class, metaDataMap);
  }

  public CostReduction() {
  }

  public CostReduction(
    int pId,
    int loanId,
    int cycleNum,
    double mangCost,
    double otherCost,
    double interest,
    double overdueInterest,
    double overduePunitive,
    String repayDt)
  {
    this();
    this.pId = pId;
    setPIdIsSet(true);
    this.loanId = loanId;
    setLoanIdIsSet(true);
    this.cycleNum = cycleNum;
    setCycleNumIsSet(true);
    this.mangCost = mangCost;
    setMangCostIsSet(true);
    this.otherCost = otherCost;
    setOtherCostIsSet(true);
    this.interest = interest;
    setInterestIsSet(true);
    this.overdueInterest = overdueInterest;
    setOverdueInterestIsSet(true);
    this.overduePunitive = overduePunitive;
    setOverduePunitiveIsSet(true);
    this.repayDt = repayDt;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CostReduction(CostReduction other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pId = other.pId;
    this.loanId = other.loanId;
    this.cycleNum = other.cycleNum;
    this.mangCost = other.mangCost;
    this.otherCost = other.otherCost;
    this.interest = other.interest;
    this.overdueInterest = other.overdueInterest;
    this.overduePunitive = other.overduePunitive;
    if (other.isSetRepayDt()) {
      this.repayDt = other.repayDt;
    }
  }

  public CostReduction deepCopy() {
    return new CostReduction(this);
  }

  @Override
  public void clear() {
    setPIdIsSet(false);
    this.pId = 0;
    setLoanIdIsSet(false);
    this.loanId = 0;
    setCycleNumIsSet(false);
    this.cycleNum = 0;
    setMangCostIsSet(false);
    this.mangCost = 0.0;
    setOtherCostIsSet(false);
    this.otherCost = 0.0;
    setInterestIsSet(false);
    this.interest = 0.0;
    setOverdueInterestIsSet(false);
    this.overdueInterest = 0.0;
    setOverduePunitiveIsSet(false);
    this.overduePunitive = 0.0;
    this.repayDt = null;
  }

  public int getPId() {
    return this.pId;
  }

  public CostReduction setPId(int pId) {
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

  public int getLoanId() {
    return this.loanId;
  }

  public CostReduction setLoanId(int loanId) {
    this.loanId = loanId;
    setLoanIdIsSet(true);
    return this;
  }

  public void unsetLoanId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LOANID_ISSET_ID);
  }

  /** Returns true if field loanId is set (has been assigned a value) and false otherwise */
  public boolean isSetLoanId() {
    return EncodingUtils.testBit(__isset_bitfield, __LOANID_ISSET_ID);
  }

  public void setLoanIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LOANID_ISSET_ID, value);
  }

  public int getCycleNum() {
    return this.cycleNum;
  }

  public CostReduction setCycleNum(int cycleNum) {
    this.cycleNum = cycleNum;
    setCycleNumIsSet(true);
    return this;
  }

  public void unsetCycleNum() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CYCLENUM_ISSET_ID);
  }

  /** Returns true if field cycleNum is set (has been assigned a value) and false otherwise */
  public boolean isSetCycleNum() {
    return EncodingUtils.testBit(__isset_bitfield, __CYCLENUM_ISSET_ID);
  }

  public void setCycleNumIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CYCLENUM_ISSET_ID, value);
  }

  public double getMangCost() {
    return this.mangCost;
  }

  public CostReduction setMangCost(double mangCost) {
    this.mangCost = mangCost;
    setMangCostIsSet(true);
    return this;
  }

  public void unsetMangCost() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MANGCOST_ISSET_ID);
  }

  /** Returns true if field mangCost is set (has been assigned a value) and false otherwise */
  public boolean isSetMangCost() {
    return EncodingUtils.testBit(__isset_bitfield, __MANGCOST_ISSET_ID);
  }

  public void setMangCostIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MANGCOST_ISSET_ID, value);
  }

  public double getOtherCost() {
    return this.otherCost;
  }

  public CostReduction setOtherCost(double otherCost) {
    this.otherCost = otherCost;
    setOtherCostIsSet(true);
    return this;
  }

  public void unsetOtherCost() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __OTHERCOST_ISSET_ID);
  }

  /** Returns true if field otherCost is set (has been assigned a value) and false otherwise */
  public boolean isSetOtherCost() {
    return EncodingUtils.testBit(__isset_bitfield, __OTHERCOST_ISSET_ID);
  }

  public void setOtherCostIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __OTHERCOST_ISSET_ID, value);
  }

  public double getInterest() {
    return this.interest;
  }

  public CostReduction setInterest(double interest) {
    this.interest = interest;
    setInterestIsSet(true);
    return this;
  }

  public void unsetInterest() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __INTEREST_ISSET_ID);
  }

  /** Returns true if field interest is set (has been assigned a value) and false otherwise */
  public boolean isSetInterest() {
    return EncodingUtils.testBit(__isset_bitfield, __INTEREST_ISSET_ID);
  }

  public void setInterestIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __INTEREST_ISSET_ID, value);
  }

  public double getOverdueInterest() {
    return this.overdueInterest;
  }

  public CostReduction setOverdueInterest(double overdueInterest) {
    this.overdueInterest = overdueInterest;
    setOverdueInterestIsSet(true);
    return this;
  }

  public void unsetOverdueInterest() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __OVERDUEINTEREST_ISSET_ID);
  }

  /** Returns true if field overdueInterest is set (has been assigned a value) and false otherwise */
  public boolean isSetOverdueInterest() {
    return EncodingUtils.testBit(__isset_bitfield, __OVERDUEINTEREST_ISSET_ID);
  }

  public void setOverdueInterestIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __OVERDUEINTEREST_ISSET_ID, value);
  }

  public double getOverduePunitive() {
    return this.overduePunitive;
  }

  public CostReduction setOverduePunitive(double overduePunitive) {
    this.overduePunitive = overduePunitive;
    setOverduePunitiveIsSet(true);
    return this;
  }

  public void unsetOverduePunitive() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __OVERDUEPUNITIVE_ISSET_ID);
  }

  /** Returns true if field overduePunitive is set (has been assigned a value) and false otherwise */
  public boolean isSetOverduePunitive() {
    return EncodingUtils.testBit(__isset_bitfield, __OVERDUEPUNITIVE_ISSET_ID);
  }

  public void setOverduePunitiveIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __OVERDUEPUNITIVE_ISSET_ID, value);
  }

  public String getRepayDt() {
    return this.repayDt;
  }

  public CostReduction setRepayDt(String repayDt) {
    this.repayDt = repayDt;
    return this;
  }

  public void unsetRepayDt() {
    this.repayDt = null;
  }

  /** Returns true if field repayDt is set (has been assigned a value) and false otherwise */
  public boolean isSetRepayDt() {
    return this.repayDt != null;
  }

  public void setRepayDtIsSet(boolean value) {
    if (!value) {
      this.repayDt = null;
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

    case LOAN_ID:
      if (value == null) {
        unsetLoanId();
      } else {
        setLoanId((Integer)value);
      }
      break;

    case CYCLE_NUM:
      if (value == null) {
        unsetCycleNum();
      } else {
        setCycleNum((Integer)value);
      }
      break;

    case MANG_COST:
      if (value == null) {
        unsetMangCost();
      } else {
        setMangCost((Double)value);
      }
      break;

    case OTHER_COST:
      if (value == null) {
        unsetOtherCost();
      } else {
        setOtherCost((Double)value);
      }
      break;

    case INTEREST:
      if (value == null) {
        unsetInterest();
      } else {
        setInterest((Double)value);
      }
      break;

    case OVERDUE_INTEREST:
      if (value == null) {
        unsetOverdueInterest();
      } else {
        setOverdueInterest((Double)value);
      }
      break;

    case OVERDUE_PUNITIVE:
      if (value == null) {
        unsetOverduePunitive();
      } else {
        setOverduePunitive((Double)value);
      }
      break;

    case REPAY_DT:
      if (value == null) {
        unsetRepayDt();
      } else {
        setRepayDt((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case P_ID:
      return Integer.valueOf(getPId());

    case LOAN_ID:
      return Integer.valueOf(getLoanId());

    case CYCLE_NUM:
      return Integer.valueOf(getCycleNum());

    case MANG_COST:
      return Double.valueOf(getMangCost());

    case OTHER_COST:
      return Double.valueOf(getOtherCost());

    case INTEREST:
      return Double.valueOf(getInterest());

    case OVERDUE_INTEREST:
      return Double.valueOf(getOverdueInterest());

    case OVERDUE_PUNITIVE:
      return Double.valueOf(getOverduePunitive());

    case REPAY_DT:
      return getRepayDt();

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
    case LOAN_ID:
      return isSetLoanId();
    case CYCLE_NUM:
      return isSetCycleNum();
    case MANG_COST:
      return isSetMangCost();
    case OTHER_COST:
      return isSetOtherCost();
    case INTEREST:
      return isSetInterest();
    case OVERDUE_INTEREST:
      return isSetOverdueInterest();
    case OVERDUE_PUNITIVE:
      return isSetOverduePunitive();
    case REPAY_DT:
      return isSetRepayDt();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CostReduction)
      return this.equals((CostReduction)that);
    return false;
  }

  public boolean equals(CostReduction that) {
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

    boolean this_present_loanId = true;
    boolean that_present_loanId = true;
    if (this_present_loanId || that_present_loanId) {
      if (!(this_present_loanId && that_present_loanId))
        return false;
      if (this.loanId != that.loanId)
        return false;
    }

    boolean this_present_cycleNum = true;
    boolean that_present_cycleNum = true;
    if (this_present_cycleNum || that_present_cycleNum) {
      if (!(this_present_cycleNum && that_present_cycleNum))
        return false;
      if (this.cycleNum != that.cycleNum)
        return false;
    }

    boolean this_present_mangCost = true;
    boolean that_present_mangCost = true;
    if (this_present_mangCost || that_present_mangCost) {
      if (!(this_present_mangCost && that_present_mangCost))
        return false;
      if (this.mangCost != that.mangCost)
        return false;
    }

    boolean this_present_otherCost = true;
    boolean that_present_otherCost = true;
    if (this_present_otherCost || that_present_otherCost) {
      if (!(this_present_otherCost && that_present_otherCost))
        return false;
      if (this.otherCost != that.otherCost)
        return false;
    }

    boolean this_present_interest = true;
    boolean that_present_interest = true;
    if (this_present_interest || that_present_interest) {
      if (!(this_present_interest && that_present_interest))
        return false;
      if (this.interest != that.interest)
        return false;
    }

    boolean this_present_overdueInterest = true;
    boolean that_present_overdueInterest = true;
    if (this_present_overdueInterest || that_present_overdueInterest) {
      if (!(this_present_overdueInterest && that_present_overdueInterest))
        return false;
      if (this.overdueInterest != that.overdueInterest)
        return false;
    }

    boolean this_present_overduePunitive = true;
    boolean that_present_overduePunitive = true;
    if (this_present_overduePunitive || that_present_overduePunitive) {
      if (!(this_present_overduePunitive && that_present_overduePunitive))
        return false;
      if (this.overduePunitive != that.overduePunitive)
        return false;
    }

    boolean this_present_repayDt = true && this.isSetRepayDt();
    boolean that_present_repayDt = true && that.isSetRepayDt();
    if (this_present_repayDt || that_present_repayDt) {
      if (!(this_present_repayDt && that_present_repayDt))
        return false;
      if (!this.repayDt.equals(that.repayDt))
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

    boolean present_loanId = true;
    list.add(present_loanId);
    if (present_loanId)
      list.add(loanId);

    boolean present_cycleNum = true;
    list.add(present_cycleNum);
    if (present_cycleNum)
      list.add(cycleNum);

    boolean present_mangCost = true;
    list.add(present_mangCost);
    if (present_mangCost)
      list.add(mangCost);

    boolean present_otherCost = true;
    list.add(present_otherCost);
    if (present_otherCost)
      list.add(otherCost);

    boolean present_interest = true;
    list.add(present_interest);
    if (present_interest)
      list.add(interest);

    boolean present_overdueInterest = true;
    list.add(present_overdueInterest);
    if (present_overdueInterest)
      list.add(overdueInterest);

    boolean present_overduePunitive = true;
    list.add(present_overduePunitive);
    if (present_overduePunitive)
      list.add(overduePunitive);

    boolean present_repayDt = true && (isSetRepayDt());
    list.add(present_repayDt);
    if (present_repayDt)
      list.add(repayDt);

    return list.hashCode();
  }

  @Override
  public int compareTo(CostReduction other) {
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
    lastComparison = Boolean.valueOf(isSetLoanId()).compareTo(other.isSetLoanId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLoanId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.loanId, other.loanId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCycleNum()).compareTo(other.isSetCycleNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCycleNum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cycleNum, other.cycleNum);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMangCost()).compareTo(other.isSetMangCost());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMangCost()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.mangCost, other.mangCost);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOtherCost()).compareTo(other.isSetOtherCost());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOtherCost()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.otherCost, other.otherCost);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetInterest()).compareTo(other.isSetInterest());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInterest()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.interest, other.interest);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOverdueInterest()).compareTo(other.isSetOverdueInterest());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOverdueInterest()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.overdueInterest, other.overdueInterest);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOverduePunitive()).compareTo(other.isSetOverduePunitive());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOverduePunitive()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.overduePunitive, other.overduePunitive);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRepayDt()).compareTo(other.isSetRepayDt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRepayDt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.repayDt, other.repayDt);
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
    StringBuilder sb = new StringBuilder("CostReduction(");
    boolean first = true;

    sb.append("pId:");
    sb.append(this.pId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("loanId:");
    sb.append(this.loanId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("cycleNum:");
    sb.append(this.cycleNum);
    first = false;
    if (!first) sb.append(", ");
    sb.append("mangCost:");
    sb.append(this.mangCost);
    first = false;
    if (!first) sb.append(", ");
    sb.append("otherCost:");
    sb.append(this.otherCost);
    first = false;
    if (!first) sb.append(", ");
    sb.append("interest:");
    sb.append(this.interest);
    first = false;
    if (!first) sb.append(", ");
    sb.append("overdueInterest:");
    sb.append(this.overdueInterest);
    first = false;
    if (!first) sb.append(", ");
    sb.append("overduePunitive:");
    sb.append(this.overduePunitive);
    first = false;
    if (!first) sb.append(", ");
    sb.append("repayDt:");
    if (this.repayDt == null) {
      sb.append("null");
    } else {
      sb.append(this.repayDt);
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

  private static class CostReductionStandardSchemeFactory implements SchemeFactory {
    public CostReductionStandardScheme getScheme() {
      return new CostReductionStandardScheme();
    }
  }

  private static class CostReductionStandardScheme extends StandardScheme<CostReduction> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CostReduction struct) throws org.apache.thrift.TException {
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
          case 2: // LOAN_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.loanId = iprot.readI32();
              struct.setLoanIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CYCLE_NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.cycleNum = iprot.readI32();
              struct.setCycleNumIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // MANG_COST
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.mangCost = iprot.readDouble();
              struct.setMangCostIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // OTHER_COST
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.otherCost = iprot.readDouble();
              struct.setOtherCostIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 9: // INTEREST
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.interest = iprot.readDouble();
              struct.setInterestIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 10: // OVERDUE_INTEREST
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.overdueInterest = iprot.readDouble();
              struct.setOverdueInterestIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 11: // OVERDUE_PUNITIVE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.overduePunitive = iprot.readDouble();
              struct.setOverduePunitiveIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 12: // REPAY_DT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.repayDt = iprot.readString();
              struct.setRepayDtIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CostReduction struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(P_ID_FIELD_DESC);
      oprot.writeI32(struct.pId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(LOAN_ID_FIELD_DESC);
      oprot.writeI32(struct.loanId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(CYCLE_NUM_FIELD_DESC);
      oprot.writeI32(struct.cycleNum);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(MANG_COST_FIELD_DESC);
      oprot.writeDouble(struct.mangCost);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(OTHER_COST_FIELD_DESC);
      oprot.writeDouble(struct.otherCost);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(INTEREST_FIELD_DESC);
      oprot.writeDouble(struct.interest);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(OVERDUE_INTEREST_FIELD_DESC);
      oprot.writeDouble(struct.overdueInterest);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(OVERDUE_PUNITIVE_FIELD_DESC);
      oprot.writeDouble(struct.overduePunitive);
      oprot.writeFieldEnd();
      if (struct.repayDt != null) {
        oprot.writeFieldBegin(REPAY_DT_FIELD_DESC);
        oprot.writeString(struct.repayDt);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CostReductionTupleSchemeFactory implements SchemeFactory {
    public CostReductionTupleScheme getScheme() {
      return new CostReductionTupleScheme();
    }
  }

  private static class CostReductionTupleScheme extends TupleScheme<CostReduction> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CostReduction struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPId()) {
        optionals.set(0);
      }
      if (struct.isSetLoanId()) {
        optionals.set(1);
      }
      if (struct.isSetCycleNum()) {
        optionals.set(2);
      }
      if (struct.isSetMangCost()) {
        optionals.set(3);
      }
      if (struct.isSetOtherCost()) {
        optionals.set(4);
      }
      if (struct.isSetInterest()) {
        optionals.set(5);
      }
      if (struct.isSetOverdueInterest()) {
        optionals.set(6);
      }
      if (struct.isSetOverduePunitive()) {
        optionals.set(7);
      }
      if (struct.isSetRepayDt()) {
        optionals.set(8);
      }
      oprot.writeBitSet(optionals, 9);
      if (struct.isSetPId()) {
        oprot.writeI32(struct.pId);
      }
      if (struct.isSetLoanId()) {
        oprot.writeI32(struct.loanId);
      }
      if (struct.isSetCycleNum()) {
        oprot.writeI32(struct.cycleNum);
      }
      if (struct.isSetMangCost()) {
        oprot.writeDouble(struct.mangCost);
      }
      if (struct.isSetOtherCost()) {
        oprot.writeDouble(struct.otherCost);
      }
      if (struct.isSetInterest()) {
        oprot.writeDouble(struct.interest);
      }
      if (struct.isSetOverdueInterest()) {
        oprot.writeDouble(struct.overdueInterest);
      }
      if (struct.isSetOverduePunitive()) {
        oprot.writeDouble(struct.overduePunitive);
      }
      if (struct.isSetRepayDt()) {
        oprot.writeString(struct.repayDt);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CostReduction struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(9);
      if (incoming.get(0)) {
        struct.pId = iprot.readI32();
        struct.setPIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.loanId = iprot.readI32();
        struct.setLoanIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.cycleNum = iprot.readI32();
        struct.setCycleNumIsSet(true);
      }
      if (incoming.get(3)) {
        struct.mangCost = iprot.readDouble();
        struct.setMangCostIsSet(true);
      }
      if (incoming.get(4)) {
        struct.otherCost = iprot.readDouble();
        struct.setOtherCostIsSet(true);
      }
      if (incoming.get(5)) {
        struct.interest = iprot.readDouble();
        struct.setInterestIsSet(true);
      }
      if (incoming.get(6)) {
        struct.overdueInterest = iprot.readDouble();
        struct.setOverdueInterestIsSet(true);
      }
      if (incoming.get(7)) {
        struct.overduePunitive = iprot.readDouble();
        struct.setOverduePunitiveIsSet(true);
      }
      if (incoming.get(8)) {
        struct.repayDt = iprot.readString();
        struct.setRepayDtIsSet(true);
      }
    }
  }

}

