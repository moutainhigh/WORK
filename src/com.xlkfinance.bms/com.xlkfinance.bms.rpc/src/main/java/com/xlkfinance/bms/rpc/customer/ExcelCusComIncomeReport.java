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
public class ExcelCusComIncomeReport implements org.apache.thrift.TBase<ExcelCusComIncomeReport, ExcelCusComIncomeReport._Fields>, java.io.Serializable, Cloneable, Comparable<ExcelCusComIncomeReport> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ExcelCusComIncomeReport");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField REPORT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("reportId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField INCOME_ITEM_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("incomeItemId", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField THIS_MONTH_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("thisMonthVal", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField THIS_YEAR_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("thisYearVal", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.STRING, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ExcelCusComIncomeReportStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ExcelCusComIncomeReportTupleSchemeFactory());
  }

  public String pid; // required
  public String reportId; // required
  public String incomeItemId; // required
  public String thisMonthVal; // required
  public String thisYearVal; // required
  public String status; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    REPORT_ID((short)2, "reportId"),
    INCOME_ITEM_ID((short)3, "incomeItemId"),
    THIS_MONTH_VAL((short)4, "thisMonthVal"),
    THIS_YEAR_VAL((short)5, "thisYearVal"),
    STATUS((short)6, "status");

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
        case 2: // REPORT_ID
          return REPORT_ID;
        case 3: // INCOME_ITEM_ID
          return INCOME_ITEM_ID;
        case 4: // THIS_MONTH_VAL
          return THIS_MONTH_VAL;
        case 5: // THIS_YEAR_VAL
          return THIS_YEAR_VAL;
        case 6: // STATUS
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
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.REPORT_ID, new org.apache.thrift.meta_data.FieldMetaData("reportId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.INCOME_ITEM_ID, new org.apache.thrift.meta_data.FieldMetaData("incomeItemId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.THIS_MONTH_VAL, new org.apache.thrift.meta_data.FieldMetaData("thisMonthVal", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.THIS_YEAR_VAL, new org.apache.thrift.meta_data.FieldMetaData("thisYearVal", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ExcelCusComIncomeReport.class, metaDataMap);
  }

  public ExcelCusComIncomeReport() {
  }

  public ExcelCusComIncomeReport(
    String pid,
    String reportId,
    String incomeItemId,
    String thisMonthVal,
    String thisYearVal,
    String status)
  {
    this();
    this.pid = pid;
    this.reportId = reportId;
    this.incomeItemId = incomeItemId;
    this.thisMonthVal = thisMonthVal;
    this.thisYearVal = thisYearVal;
    this.status = status;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ExcelCusComIncomeReport(ExcelCusComIncomeReport other) {
    if (other.isSetPid()) {
      this.pid = other.pid;
    }
    if (other.isSetReportId()) {
      this.reportId = other.reportId;
    }
    if (other.isSetIncomeItemId()) {
      this.incomeItemId = other.incomeItemId;
    }
    if (other.isSetThisMonthVal()) {
      this.thisMonthVal = other.thisMonthVal;
    }
    if (other.isSetThisYearVal()) {
      this.thisYearVal = other.thisYearVal;
    }
    if (other.isSetStatus()) {
      this.status = other.status;
    }
  }

  public ExcelCusComIncomeReport deepCopy() {
    return new ExcelCusComIncomeReport(this);
  }

  @Override
  public void clear() {
    this.pid = null;
    this.reportId = null;
    this.incomeItemId = null;
    this.thisMonthVal = null;
    this.thisYearVal = null;
    this.status = null;
  }

  public String getPid() {
    return this.pid;
  }

  public ExcelCusComIncomeReport setPid(String pid) {
    this.pid = pid;
    return this;
  }

  public void unsetPid() {
    this.pid = null;
  }

  /** Returns true if field pid is set (has been assigned a value) and false otherwise */
  public boolean isSetPid() {
    return this.pid != null;
  }

  public void setPidIsSet(boolean value) {
    if (!value) {
      this.pid = null;
    }
  }

  public String getReportId() {
    return this.reportId;
  }

  public ExcelCusComIncomeReport setReportId(String reportId) {
    this.reportId = reportId;
    return this;
  }

  public void unsetReportId() {
    this.reportId = null;
  }

  /** Returns true if field reportId is set (has been assigned a value) and false otherwise */
  public boolean isSetReportId() {
    return this.reportId != null;
  }

  public void setReportIdIsSet(boolean value) {
    if (!value) {
      this.reportId = null;
    }
  }

  public String getIncomeItemId() {
    return this.incomeItemId;
  }

  public ExcelCusComIncomeReport setIncomeItemId(String incomeItemId) {
    this.incomeItemId = incomeItemId;
    return this;
  }

  public void unsetIncomeItemId() {
    this.incomeItemId = null;
  }

  /** Returns true if field incomeItemId is set (has been assigned a value) and false otherwise */
  public boolean isSetIncomeItemId() {
    return this.incomeItemId != null;
  }

  public void setIncomeItemIdIsSet(boolean value) {
    if (!value) {
      this.incomeItemId = null;
    }
  }

  public String getThisMonthVal() {
    return this.thisMonthVal;
  }

  public ExcelCusComIncomeReport setThisMonthVal(String thisMonthVal) {
    this.thisMonthVal = thisMonthVal;
    return this;
  }

  public void unsetThisMonthVal() {
    this.thisMonthVal = null;
  }

  /** Returns true if field thisMonthVal is set (has been assigned a value) and false otherwise */
  public boolean isSetThisMonthVal() {
    return this.thisMonthVal != null;
  }

  public void setThisMonthValIsSet(boolean value) {
    if (!value) {
      this.thisMonthVal = null;
    }
  }

  public String getThisYearVal() {
    return this.thisYearVal;
  }

  public ExcelCusComIncomeReport setThisYearVal(String thisYearVal) {
    this.thisYearVal = thisYearVal;
    return this;
  }

  public void unsetThisYearVal() {
    this.thisYearVal = null;
  }

  /** Returns true if field thisYearVal is set (has been assigned a value) and false otherwise */
  public boolean isSetThisYearVal() {
    return this.thisYearVal != null;
  }

  public void setThisYearValIsSet(boolean value) {
    if (!value) {
      this.thisYearVal = null;
    }
  }

  public String getStatus() {
    return this.status;
  }

  public ExcelCusComIncomeReport setStatus(String status) {
    this.status = status;
    return this;
  }

  public void unsetStatus() {
    this.status = null;
  }

  /** Returns true if field status is set (has been assigned a value) and false otherwise */
  public boolean isSetStatus() {
    return this.status != null;
  }

  public void setStatusIsSet(boolean value) {
    if (!value) {
      this.status = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PID:
      if (value == null) {
        unsetPid();
      } else {
        setPid((String)value);
      }
      break;

    case REPORT_ID:
      if (value == null) {
        unsetReportId();
      } else {
        setReportId((String)value);
      }
      break;

    case INCOME_ITEM_ID:
      if (value == null) {
        unsetIncomeItemId();
      } else {
        setIncomeItemId((String)value);
      }
      break;

    case THIS_MONTH_VAL:
      if (value == null) {
        unsetThisMonthVal();
      } else {
        setThisMonthVal((String)value);
      }
      break;

    case THIS_YEAR_VAL:
      if (value == null) {
        unsetThisYearVal();
      } else {
        setThisYearVal((String)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PID:
      return getPid();

    case REPORT_ID:
      return getReportId();

    case INCOME_ITEM_ID:
      return getIncomeItemId();

    case THIS_MONTH_VAL:
      return getThisMonthVal();

    case THIS_YEAR_VAL:
      return getThisYearVal();

    case STATUS:
      return getStatus();

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
    case REPORT_ID:
      return isSetReportId();
    case INCOME_ITEM_ID:
      return isSetIncomeItemId();
    case THIS_MONTH_VAL:
      return isSetThisMonthVal();
    case THIS_YEAR_VAL:
      return isSetThisYearVal();
    case STATUS:
      return isSetStatus();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ExcelCusComIncomeReport)
      return this.equals((ExcelCusComIncomeReport)that);
    return false;
  }

  public boolean equals(ExcelCusComIncomeReport that) {
    if (that == null)
      return false;

    boolean this_present_pid = true && this.isSetPid();
    boolean that_present_pid = true && that.isSetPid();
    if (this_present_pid || that_present_pid) {
      if (!(this_present_pid && that_present_pid))
        return false;
      if (!this.pid.equals(that.pid))
        return false;
    }

    boolean this_present_reportId = true && this.isSetReportId();
    boolean that_present_reportId = true && that.isSetReportId();
    if (this_present_reportId || that_present_reportId) {
      if (!(this_present_reportId && that_present_reportId))
        return false;
      if (!this.reportId.equals(that.reportId))
        return false;
    }

    boolean this_present_incomeItemId = true && this.isSetIncomeItemId();
    boolean that_present_incomeItemId = true && that.isSetIncomeItemId();
    if (this_present_incomeItemId || that_present_incomeItemId) {
      if (!(this_present_incomeItemId && that_present_incomeItemId))
        return false;
      if (!this.incomeItemId.equals(that.incomeItemId))
        return false;
    }

    boolean this_present_thisMonthVal = true && this.isSetThisMonthVal();
    boolean that_present_thisMonthVal = true && that.isSetThisMonthVal();
    if (this_present_thisMonthVal || that_present_thisMonthVal) {
      if (!(this_present_thisMonthVal && that_present_thisMonthVal))
        return false;
      if (!this.thisMonthVal.equals(that.thisMonthVal))
        return false;
    }

    boolean this_present_thisYearVal = true && this.isSetThisYearVal();
    boolean that_present_thisYearVal = true && that.isSetThisYearVal();
    if (this_present_thisYearVal || that_present_thisYearVal) {
      if (!(this_present_thisYearVal && that_present_thisYearVal))
        return false;
      if (!this.thisYearVal.equals(that.thisYearVal))
        return false;
    }

    boolean this_present_status = true && this.isSetStatus();
    boolean that_present_status = true && that.isSetStatus();
    if (this_present_status || that_present_status) {
      if (!(this_present_status && that_present_status))
        return false;
      if (!this.status.equals(that.status))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_pid = true && (isSetPid());
    list.add(present_pid);
    if (present_pid)
      list.add(pid);

    boolean present_reportId = true && (isSetReportId());
    list.add(present_reportId);
    if (present_reportId)
      list.add(reportId);

    boolean present_incomeItemId = true && (isSetIncomeItemId());
    list.add(present_incomeItemId);
    if (present_incomeItemId)
      list.add(incomeItemId);

    boolean present_thisMonthVal = true && (isSetThisMonthVal());
    list.add(present_thisMonthVal);
    if (present_thisMonthVal)
      list.add(thisMonthVal);

    boolean present_thisYearVal = true && (isSetThisYearVal());
    list.add(present_thisYearVal);
    if (present_thisYearVal)
      list.add(thisYearVal);

    boolean present_status = true && (isSetStatus());
    list.add(present_status);
    if (present_status)
      list.add(status);

    return list.hashCode();
  }

  @Override
  public int compareTo(ExcelCusComIncomeReport other) {
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
    lastComparison = Boolean.valueOf(isSetReportId()).compareTo(other.isSetReportId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReportId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.reportId, other.reportId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIncomeItemId()).compareTo(other.isSetIncomeItemId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIncomeItemId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.incomeItemId, other.incomeItemId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetThisMonthVal()).compareTo(other.isSetThisMonthVal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetThisMonthVal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.thisMonthVal, other.thisMonthVal);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetThisYearVal()).compareTo(other.isSetThisYearVal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetThisYearVal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.thisYearVal, other.thisYearVal);
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
    StringBuilder sb = new StringBuilder("ExcelCusComIncomeReport(");
    boolean first = true;

    sb.append("pid:");
    if (this.pid == null) {
      sb.append("null");
    } else {
      sb.append(this.pid);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("reportId:");
    if (this.reportId == null) {
      sb.append("null");
    } else {
      sb.append(this.reportId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("incomeItemId:");
    if (this.incomeItemId == null) {
      sb.append("null");
    } else {
      sb.append(this.incomeItemId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("thisMonthVal:");
    if (this.thisMonthVal == null) {
      sb.append("null");
    } else {
      sb.append(this.thisMonthVal);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("thisYearVal:");
    if (this.thisYearVal == null) {
      sb.append("null");
    } else {
      sb.append(this.thisYearVal);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("status:");
    if (this.status == null) {
      sb.append("null");
    } else {
      sb.append(this.status);
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ExcelCusComIncomeReportStandardSchemeFactory implements SchemeFactory {
    public ExcelCusComIncomeReportStandardScheme getScheme() {
      return new ExcelCusComIncomeReportStandardScheme();
    }
  }

  private static class ExcelCusComIncomeReportStandardScheme extends StandardScheme<ExcelCusComIncomeReport> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ExcelCusComIncomeReport struct) throws org.apache.thrift.TException {
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
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.pid = iprot.readString();
              struct.setPidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // REPORT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.reportId = iprot.readString();
              struct.setReportIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // INCOME_ITEM_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.incomeItemId = iprot.readString();
              struct.setIncomeItemIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // THIS_MONTH_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.thisMonthVal = iprot.readString();
              struct.setThisMonthValIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // THIS_YEAR_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.thisYearVal = iprot.readString();
              struct.setThisYearValIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.status = iprot.readString();
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ExcelCusComIncomeReport struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.pid != null) {
        oprot.writeFieldBegin(PID_FIELD_DESC);
        oprot.writeString(struct.pid);
        oprot.writeFieldEnd();
      }
      if (struct.reportId != null) {
        oprot.writeFieldBegin(REPORT_ID_FIELD_DESC);
        oprot.writeString(struct.reportId);
        oprot.writeFieldEnd();
      }
      if (struct.incomeItemId != null) {
        oprot.writeFieldBegin(INCOME_ITEM_ID_FIELD_DESC);
        oprot.writeString(struct.incomeItemId);
        oprot.writeFieldEnd();
      }
      if (struct.thisMonthVal != null) {
        oprot.writeFieldBegin(THIS_MONTH_VAL_FIELD_DESC);
        oprot.writeString(struct.thisMonthVal);
        oprot.writeFieldEnd();
      }
      if (struct.thisYearVal != null) {
        oprot.writeFieldBegin(THIS_YEAR_VAL_FIELD_DESC);
        oprot.writeString(struct.thisYearVal);
        oprot.writeFieldEnd();
      }
      if (struct.status != null) {
        oprot.writeFieldBegin(STATUS_FIELD_DESC);
        oprot.writeString(struct.status);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ExcelCusComIncomeReportTupleSchemeFactory implements SchemeFactory {
    public ExcelCusComIncomeReportTupleScheme getScheme() {
      return new ExcelCusComIncomeReportTupleScheme();
    }
  }

  private static class ExcelCusComIncomeReportTupleScheme extends TupleScheme<ExcelCusComIncomeReport> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ExcelCusComIncomeReport struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPid()) {
        optionals.set(0);
      }
      if (struct.isSetReportId()) {
        optionals.set(1);
      }
      if (struct.isSetIncomeItemId()) {
        optionals.set(2);
      }
      if (struct.isSetThisMonthVal()) {
        optionals.set(3);
      }
      if (struct.isSetThisYearVal()) {
        optionals.set(4);
      }
      if (struct.isSetStatus()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetPid()) {
        oprot.writeString(struct.pid);
      }
      if (struct.isSetReportId()) {
        oprot.writeString(struct.reportId);
      }
      if (struct.isSetIncomeItemId()) {
        oprot.writeString(struct.incomeItemId);
      }
      if (struct.isSetThisMonthVal()) {
        oprot.writeString(struct.thisMonthVal);
      }
      if (struct.isSetThisYearVal()) {
        oprot.writeString(struct.thisYearVal);
      }
      if (struct.isSetStatus()) {
        oprot.writeString(struct.status);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ExcelCusComIncomeReport struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.pid = iprot.readString();
        struct.setPidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.reportId = iprot.readString();
        struct.setReportIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.incomeItemId = iprot.readString();
        struct.setIncomeItemIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.thisMonthVal = iprot.readString();
        struct.setThisMonthValIsSet(true);
      }
      if (incoming.get(4)) {
        struct.thisYearVal = iprot.readString();
        struct.setThisYearValIsSet(true);
      }
      if (incoming.get(5)) {
        struct.status = iprot.readString();
        struct.setStatusIsSet(true);
      }
    }
  }

}
