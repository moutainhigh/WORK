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
public class MonthlyReportRecordCalculateDetail implements org.apache.thrift.TBase<MonthlyReportRecordCalculateDetail, MonthlyReportRecordCalculateDetail._Fields>, java.io.Serializable, Cloneable, Comparable<MonthlyReportRecordCalculateDetail> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MonthlyReportRecordCalculateDetail");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField LOAN_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("loanId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField START_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("startDate", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField END_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("endDate", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField MONTH_FIELD_DESC = new org.apache.thrift.protocol.TField("month", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField CONTENT_FIELD_DESC = new org.apache.thrift.protocol.TField("content", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField MONTHLY_REPORT_RECORD_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("monthlyReportRecordId", org.apache.thrift.protocol.TType.I32, (short)7);
  private static final org.apache.thrift.protocol.TField CONTENT1_FIELD_DESC = new org.apache.thrift.protocol.TField("content1", org.apache.thrift.protocol.TType.STRING, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MonthlyReportRecordCalculateDetailStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MonthlyReportRecordCalculateDetailTupleSchemeFactory());
  }

  public int pid; // required
  public int loanId; // required
  public String startDate; // required
  public String endDate; // required
  public String month; // required
  public String content; // required
  public int monthlyReportRecordId; // required
  public String content1; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    LOAN_ID((short)2, "loanId"),
    START_DATE((short)3, "startDate"),
    END_DATE((short)4, "endDate"),
    MONTH((short)5, "month"),
    CONTENT((short)6, "content"),
    MONTHLY_REPORT_RECORD_ID((short)7, "monthlyReportRecordId"),
    CONTENT1((short)8, "content1");

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
        case 2: // LOAN_ID
          return LOAN_ID;
        case 3: // START_DATE
          return START_DATE;
        case 4: // END_DATE
          return END_DATE;
        case 5: // MONTH
          return MONTH;
        case 6: // CONTENT
          return CONTENT;
        case 7: // MONTHLY_REPORT_RECORD_ID
          return MONTHLY_REPORT_RECORD_ID;
        case 8: // CONTENT1
          return CONTENT1;
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
  private static final int __MONTHLYREPORTRECORDID_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LOAN_ID, new org.apache.thrift.meta_data.FieldMetaData("loanId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.START_DATE, new org.apache.thrift.meta_data.FieldMetaData("startDate", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.END_DATE, new org.apache.thrift.meta_data.FieldMetaData("endDate", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MONTH, new org.apache.thrift.meta_data.FieldMetaData("month", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CONTENT, new org.apache.thrift.meta_data.FieldMetaData("content", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MONTHLY_REPORT_RECORD_ID, new org.apache.thrift.meta_data.FieldMetaData("monthlyReportRecordId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CONTENT1, new org.apache.thrift.meta_data.FieldMetaData("content1", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MonthlyReportRecordCalculateDetail.class, metaDataMap);
  }

  public MonthlyReportRecordCalculateDetail() {
  }

  public MonthlyReportRecordCalculateDetail(
    int pid,
    int loanId,
    String startDate,
    String endDate,
    String month,
    String content,
    int monthlyReportRecordId,
    String content1)
  {
    this();
    this.pid = pid;
    setPidIsSet(true);
    this.loanId = loanId;
    setLoanIdIsSet(true);
    this.startDate = startDate;
    this.endDate = endDate;
    this.month = month;
    this.content = content;
    this.monthlyReportRecordId = monthlyReportRecordId;
    setMonthlyReportRecordIdIsSet(true);
    this.content1 = content1;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MonthlyReportRecordCalculateDetail(MonthlyReportRecordCalculateDetail other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pid = other.pid;
    this.loanId = other.loanId;
    if (other.isSetStartDate()) {
      this.startDate = other.startDate;
    }
    if (other.isSetEndDate()) {
      this.endDate = other.endDate;
    }
    if (other.isSetMonth()) {
      this.month = other.month;
    }
    if (other.isSetContent()) {
      this.content = other.content;
    }
    this.monthlyReportRecordId = other.monthlyReportRecordId;
    if (other.isSetContent1()) {
      this.content1 = other.content1;
    }
  }

  public MonthlyReportRecordCalculateDetail deepCopy() {
    return new MonthlyReportRecordCalculateDetail(this);
  }

  @Override
  public void clear() {
    setPidIsSet(false);
    this.pid = 0;
    setLoanIdIsSet(false);
    this.loanId = 0;
    this.startDate = null;
    this.endDate = null;
    this.month = null;
    this.content = null;
    setMonthlyReportRecordIdIsSet(false);
    this.monthlyReportRecordId = 0;
    this.content1 = null;
  }

  public int getPid() {
    return this.pid;
  }

  public MonthlyReportRecordCalculateDetail setPid(int pid) {
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

  public int getLoanId() {
    return this.loanId;
  }

  public MonthlyReportRecordCalculateDetail setLoanId(int loanId) {
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

  public String getStartDate() {
    return this.startDate;
  }

  public MonthlyReportRecordCalculateDetail setStartDate(String startDate) {
    this.startDate = startDate;
    return this;
  }

  public void unsetStartDate() {
    this.startDate = null;
  }

  /** Returns true if field startDate is set (has been assigned a value) and false otherwise */
  public boolean isSetStartDate() {
    return this.startDate != null;
  }

  public void setStartDateIsSet(boolean value) {
    if (!value) {
      this.startDate = null;
    }
  }

  public String getEndDate() {
    return this.endDate;
  }

  public MonthlyReportRecordCalculateDetail setEndDate(String endDate) {
    this.endDate = endDate;
    return this;
  }

  public void unsetEndDate() {
    this.endDate = null;
  }

  /** Returns true if field endDate is set (has been assigned a value) and false otherwise */
  public boolean isSetEndDate() {
    return this.endDate != null;
  }

  public void setEndDateIsSet(boolean value) {
    if (!value) {
      this.endDate = null;
    }
  }

  public String getMonth() {
    return this.month;
  }

  public MonthlyReportRecordCalculateDetail setMonth(String month) {
    this.month = month;
    return this;
  }

  public void unsetMonth() {
    this.month = null;
  }

  /** Returns true if field month is set (has been assigned a value) and false otherwise */
  public boolean isSetMonth() {
    return this.month != null;
  }

  public void setMonthIsSet(boolean value) {
    if (!value) {
      this.month = null;
    }
  }

  public String getContent() {
    return this.content;
  }

  public MonthlyReportRecordCalculateDetail setContent(String content) {
    this.content = content;
    return this;
  }

  public void unsetContent() {
    this.content = null;
  }

  /** Returns true if field content is set (has been assigned a value) and false otherwise */
  public boolean isSetContent() {
    return this.content != null;
  }

  public void setContentIsSet(boolean value) {
    if (!value) {
      this.content = null;
    }
  }

  public int getMonthlyReportRecordId() {
    return this.monthlyReportRecordId;
  }

  public MonthlyReportRecordCalculateDetail setMonthlyReportRecordId(int monthlyReportRecordId) {
    this.monthlyReportRecordId = monthlyReportRecordId;
    setMonthlyReportRecordIdIsSet(true);
    return this;
  }

  public void unsetMonthlyReportRecordId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MONTHLYREPORTRECORDID_ISSET_ID);
  }

  /** Returns true if field monthlyReportRecordId is set (has been assigned a value) and false otherwise */
  public boolean isSetMonthlyReportRecordId() {
    return EncodingUtils.testBit(__isset_bitfield, __MONTHLYREPORTRECORDID_ISSET_ID);
  }

  public void setMonthlyReportRecordIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MONTHLYREPORTRECORDID_ISSET_ID, value);
  }

  public String getContent1() {
    return this.content1;
  }

  public MonthlyReportRecordCalculateDetail setContent1(String content1) {
    this.content1 = content1;
    return this;
  }

  public void unsetContent1() {
    this.content1 = null;
  }

  /** Returns true if field content1 is set (has been assigned a value) and false otherwise */
  public boolean isSetContent1() {
    return this.content1 != null;
  }

  public void setContent1IsSet(boolean value) {
    if (!value) {
      this.content1 = null;
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

    case LOAN_ID:
      if (value == null) {
        unsetLoanId();
      } else {
        setLoanId((Integer)value);
      }
      break;

    case START_DATE:
      if (value == null) {
        unsetStartDate();
      } else {
        setStartDate((String)value);
      }
      break;

    case END_DATE:
      if (value == null) {
        unsetEndDate();
      } else {
        setEndDate((String)value);
      }
      break;

    case MONTH:
      if (value == null) {
        unsetMonth();
      } else {
        setMonth((String)value);
      }
      break;

    case CONTENT:
      if (value == null) {
        unsetContent();
      } else {
        setContent((String)value);
      }
      break;

    case MONTHLY_REPORT_RECORD_ID:
      if (value == null) {
        unsetMonthlyReportRecordId();
      } else {
        setMonthlyReportRecordId((Integer)value);
      }
      break;

    case CONTENT1:
      if (value == null) {
        unsetContent1();
      } else {
        setContent1((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PID:
      return Integer.valueOf(getPid());

    case LOAN_ID:
      return Integer.valueOf(getLoanId());

    case START_DATE:
      return getStartDate();

    case END_DATE:
      return getEndDate();

    case MONTH:
      return getMonth();

    case CONTENT:
      return getContent();

    case MONTHLY_REPORT_RECORD_ID:
      return Integer.valueOf(getMonthlyReportRecordId());

    case CONTENT1:
      return getContent1();

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
    case LOAN_ID:
      return isSetLoanId();
    case START_DATE:
      return isSetStartDate();
    case END_DATE:
      return isSetEndDate();
    case MONTH:
      return isSetMonth();
    case CONTENT:
      return isSetContent();
    case MONTHLY_REPORT_RECORD_ID:
      return isSetMonthlyReportRecordId();
    case CONTENT1:
      return isSetContent1();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof MonthlyReportRecordCalculateDetail)
      return this.equals((MonthlyReportRecordCalculateDetail)that);
    return false;
  }

  public boolean equals(MonthlyReportRecordCalculateDetail that) {
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

    boolean this_present_loanId = true;
    boolean that_present_loanId = true;
    if (this_present_loanId || that_present_loanId) {
      if (!(this_present_loanId && that_present_loanId))
        return false;
      if (this.loanId != that.loanId)
        return false;
    }

    boolean this_present_startDate = true && this.isSetStartDate();
    boolean that_present_startDate = true && that.isSetStartDate();
    if (this_present_startDate || that_present_startDate) {
      if (!(this_present_startDate && that_present_startDate))
        return false;
      if (!this.startDate.equals(that.startDate))
        return false;
    }

    boolean this_present_endDate = true && this.isSetEndDate();
    boolean that_present_endDate = true && that.isSetEndDate();
    if (this_present_endDate || that_present_endDate) {
      if (!(this_present_endDate && that_present_endDate))
        return false;
      if (!this.endDate.equals(that.endDate))
        return false;
    }

    boolean this_present_month = true && this.isSetMonth();
    boolean that_present_month = true && that.isSetMonth();
    if (this_present_month || that_present_month) {
      if (!(this_present_month && that_present_month))
        return false;
      if (!this.month.equals(that.month))
        return false;
    }

    boolean this_present_content = true && this.isSetContent();
    boolean that_present_content = true && that.isSetContent();
    if (this_present_content || that_present_content) {
      if (!(this_present_content && that_present_content))
        return false;
      if (!this.content.equals(that.content))
        return false;
    }

    boolean this_present_monthlyReportRecordId = true;
    boolean that_present_monthlyReportRecordId = true;
    if (this_present_monthlyReportRecordId || that_present_monthlyReportRecordId) {
      if (!(this_present_monthlyReportRecordId && that_present_monthlyReportRecordId))
        return false;
      if (this.monthlyReportRecordId != that.monthlyReportRecordId)
        return false;
    }

    boolean this_present_content1 = true && this.isSetContent1();
    boolean that_present_content1 = true && that.isSetContent1();
    if (this_present_content1 || that_present_content1) {
      if (!(this_present_content1 && that_present_content1))
        return false;
      if (!this.content1.equals(that.content1))
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

    boolean present_loanId = true;
    list.add(present_loanId);
    if (present_loanId)
      list.add(loanId);

    boolean present_startDate = true && (isSetStartDate());
    list.add(present_startDate);
    if (present_startDate)
      list.add(startDate);

    boolean present_endDate = true && (isSetEndDate());
    list.add(present_endDate);
    if (present_endDate)
      list.add(endDate);

    boolean present_month = true && (isSetMonth());
    list.add(present_month);
    if (present_month)
      list.add(month);

    boolean present_content = true && (isSetContent());
    list.add(present_content);
    if (present_content)
      list.add(content);

    boolean present_monthlyReportRecordId = true;
    list.add(present_monthlyReportRecordId);
    if (present_monthlyReportRecordId)
      list.add(monthlyReportRecordId);

    boolean present_content1 = true && (isSetContent1());
    list.add(present_content1);
    if (present_content1)
      list.add(content1);

    return list.hashCode();
  }

  @Override
  public int compareTo(MonthlyReportRecordCalculateDetail other) {
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
    lastComparison = Boolean.valueOf(isSetStartDate()).compareTo(other.isSetStartDate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStartDate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.startDate, other.startDate);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEndDate()).compareTo(other.isSetEndDate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEndDate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.endDate, other.endDate);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMonth()).compareTo(other.isSetMonth());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMonth()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.month, other.month);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetContent()).compareTo(other.isSetContent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.content, other.content);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMonthlyReportRecordId()).compareTo(other.isSetMonthlyReportRecordId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMonthlyReportRecordId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.monthlyReportRecordId, other.monthlyReportRecordId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetContent1()).compareTo(other.isSetContent1());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContent1()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.content1, other.content1);
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
    StringBuilder sb = new StringBuilder("MonthlyReportRecordCalculateDetail(");
    boolean first = true;

    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("loanId:");
    sb.append(this.loanId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("startDate:");
    if (this.startDate == null) {
      sb.append("null");
    } else {
      sb.append(this.startDate);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("endDate:");
    if (this.endDate == null) {
      sb.append("null");
    } else {
      sb.append(this.endDate);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("month:");
    if (this.month == null) {
      sb.append("null");
    } else {
      sb.append(this.month);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("content:");
    if (this.content == null) {
      sb.append("null");
    } else {
      sb.append(this.content);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("monthlyReportRecordId:");
    sb.append(this.monthlyReportRecordId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("content1:");
    if (this.content1 == null) {
      sb.append("null");
    } else {
      sb.append(this.content1);
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

  private static class MonthlyReportRecordCalculateDetailStandardSchemeFactory implements SchemeFactory {
    public MonthlyReportRecordCalculateDetailStandardScheme getScheme() {
      return new MonthlyReportRecordCalculateDetailStandardScheme();
    }
  }

  private static class MonthlyReportRecordCalculateDetailStandardScheme extends StandardScheme<MonthlyReportRecordCalculateDetail> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MonthlyReportRecordCalculateDetail struct) throws org.apache.thrift.TException {
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
          case 2: // LOAN_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.loanId = iprot.readI32();
              struct.setLoanIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // START_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.startDate = iprot.readString();
              struct.setStartDateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // END_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.endDate = iprot.readString();
              struct.setEndDateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // MONTH
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.month = iprot.readString();
              struct.setMonthIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CONTENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.content = iprot.readString();
              struct.setContentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // MONTHLY_REPORT_RECORD_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.monthlyReportRecordId = iprot.readI32();
              struct.setMonthlyReportRecordIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // CONTENT1
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.content1 = iprot.readString();
              struct.setContent1IsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MonthlyReportRecordCalculateDetail struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PID_FIELD_DESC);
      oprot.writeI32(struct.pid);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(LOAN_ID_FIELD_DESC);
      oprot.writeI32(struct.loanId);
      oprot.writeFieldEnd();
      if (struct.startDate != null) {
        oprot.writeFieldBegin(START_DATE_FIELD_DESC);
        oprot.writeString(struct.startDate);
        oprot.writeFieldEnd();
      }
      if (struct.endDate != null) {
        oprot.writeFieldBegin(END_DATE_FIELD_DESC);
        oprot.writeString(struct.endDate);
        oprot.writeFieldEnd();
      }
      if (struct.month != null) {
        oprot.writeFieldBegin(MONTH_FIELD_DESC);
        oprot.writeString(struct.month);
        oprot.writeFieldEnd();
      }
      if (struct.content != null) {
        oprot.writeFieldBegin(CONTENT_FIELD_DESC);
        oprot.writeString(struct.content);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(MONTHLY_REPORT_RECORD_ID_FIELD_DESC);
      oprot.writeI32(struct.monthlyReportRecordId);
      oprot.writeFieldEnd();
      if (struct.content1 != null) {
        oprot.writeFieldBegin(CONTENT1_FIELD_DESC);
        oprot.writeString(struct.content1);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MonthlyReportRecordCalculateDetailTupleSchemeFactory implements SchemeFactory {
    public MonthlyReportRecordCalculateDetailTupleScheme getScheme() {
      return new MonthlyReportRecordCalculateDetailTupleScheme();
    }
  }

  private static class MonthlyReportRecordCalculateDetailTupleScheme extends TupleScheme<MonthlyReportRecordCalculateDetail> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MonthlyReportRecordCalculateDetail struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPid()) {
        optionals.set(0);
      }
      if (struct.isSetLoanId()) {
        optionals.set(1);
      }
      if (struct.isSetStartDate()) {
        optionals.set(2);
      }
      if (struct.isSetEndDate()) {
        optionals.set(3);
      }
      if (struct.isSetMonth()) {
        optionals.set(4);
      }
      if (struct.isSetContent()) {
        optionals.set(5);
      }
      if (struct.isSetMonthlyReportRecordId()) {
        optionals.set(6);
      }
      if (struct.isSetContent1()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetPid()) {
        oprot.writeI32(struct.pid);
      }
      if (struct.isSetLoanId()) {
        oprot.writeI32(struct.loanId);
      }
      if (struct.isSetStartDate()) {
        oprot.writeString(struct.startDate);
      }
      if (struct.isSetEndDate()) {
        oprot.writeString(struct.endDate);
      }
      if (struct.isSetMonth()) {
        oprot.writeString(struct.month);
      }
      if (struct.isSetContent()) {
        oprot.writeString(struct.content);
      }
      if (struct.isSetMonthlyReportRecordId()) {
        oprot.writeI32(struct.monthlyReportRecordId);
      }
      if (struct.isSetContent1()) {
        oprot.writeString(struct.content1);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MonthlyReportRecordCalculateDetail struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.pid = iprot.readI32();
        struct.setPidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.loanId = iprot.readI32();
        struct.setLoanIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.startDate = iprot.readString();
        struct.setStartDateIsSet(true);
      }
      if (incoming.get(3)) {
        struct.endDate = iprot.readString();
        struct.setEndDateIsSet(true);
      }
      if (incoming.get(4)) {
        struct.month = iprot.readString();
        struct.setMonthIsSet(true);
      }
      if (incoming.get(5)) {
        struct.content = iprot.readString();
        struct.setContentIsSet(true);
      }
      if (incoming.get(6)) {
        struct.monthlyReportRecordId = iprot.readI32();
        struct.setMonthlyReportRecordIdIsSet(true);
      }
      if (incoming.get(7)) {
        struct.content1 = iprot.readString();
        struct.setContent1IsSet(true);
      }
    }
  }

}

