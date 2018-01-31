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
public class MonthlyReportRecordCondition implements org.apache.thrift.TBase<MonthlyReportRecordCondition, MonthlyReportRecordCondition._Fields>, java.io.Serializable, Cloneable, Comparable<MonthlyReportRecordCondition> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MonthlyReportRecordCondition");

  private static final org.apache.thrift.protocol.TField MONTH_FIELD_DESC = new org.apache.thrift.protocol.TField("month", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField PROJECT_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("projectName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField PROJECT_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("projectNo", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField PIDS_FIELD_DESC = new org.apache.thrift.protocol.TField("pids", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField ASS_WAY_FIELD_DESC = new org.apache.thrift.protocol.TField("assWay", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField ROWS_FIELD_DESC = new org.apache.thrift.protocol.TField("rows", org.apache.thrift.protocol.TType.I32, (short)7);
  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.I32, (short)8);
  private static final org.apache.thrift.protocol.TField PROJECT_MANAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("projectManage", org.apache.thrift.protocol.TType.STRING, (short)9);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MonthlyReportRecordConditionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MonthlyReportRecordConditionTupleSchemeFactory());
  }

  public String month; // required
  public String projectName; // required
  public String projectNo; // required
  public int status; // required
  public String pids; // required
  public String assWay; // required
  public int rows; // required
  public int page; // required
  public String projectManage; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MONTH((short)1, "month"),
    PROJECT_NAME((short)2, "projectName"),
    PROJECT_NO((short)3, "projectNo"),
    STATUS((short)4, "status"),
    PIDS((short)5, "pids"),
    ASS_WAY((short)6, "assWay"),
    ROWS((short)7, "rows"),
    PAGE((short)8, "page"),
    PROJECT_MANAGE((short)9, "projectManage");

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
        case 1: // MONTH
          return MONTH;
        case 2: // PROJECT_NAME
          return PROJECT_NAME;
        case 3: // PROJECT_NO
          return PROJECT_NO;
        case 4: // STATUS
          return STATUS;
        case 5: // PIDS
          return PIDS;
        case 6: // ASS_WAY
          return ASS_WAY;
        case 7: // ROWS
          return ROWS;
        case 8: // PAGE
          return PAGE;
        case 9: // PROJECT_MANAGE
          return PROJECT_MANAGE;
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
  private static final int __STATUS_ISSET_ID = 0;
  private static final int __ROWS_ISSET_ID = 1;
  private static final int __PAGE_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.MONTH, new org.apache.thrift.meta_data.FieldMetaData("month", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PROJECT_NAME, new org.apache.thrift.meta_data.FieldMetaData("projectName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PROJECT_NO, new org.apache.thrift.meta_data.FieldMetaData("projectNo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PIDS, new org.apache.thrift.meta_data.FieldMetaData("pids", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ASS_WAY, new org.apache.thrift.meta_data.FieldMetaData("assWay", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ROWS, new org.apache.thrift.meta_data.FieldMetaData("rows", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PAGE, new org.apache.thrift.meta_data.FieldMetaData("page", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PROJECT_MANAGE, new org.apache.thrift.meta_data.FieldMetaData("projectManage", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MonthlyReportRecordCondition.class, metaDataMap);
  }

  public MonthlyReportRecordCondition() {
  }

  public MonthlyReportRecordCondition(
    String month,
    String projectName,
    String projectNo,
    int status,
    String pids,
    String assWay,
    int rows,
    int page,
    String projectManage)
  {
    this();
    this.month = month;
    this.projectName = projectName;
    this.projectNo = projectNo;
    this.status = status;
    setStatusIsSet(true);
    this.pids = pids;
    this.assWay = assWay;
    this.rows = rows;
    setRowsIsSet(true);
    this.page = page;
    setPageIsSet(true);
    this.projectManage = projectManage;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MonthlyReportRecordCondition(MonthlyReportRecordCondition other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetMonth()) {
      this.month = other.month;
    }
    if (other.isSetProjectName()) {
      this.projectName = other.projectName;
    }
    if (other.isSetProjectNo()) {
      this.projectNo = other.projectNo;
    }
    this.status = other.status;
    if (other.isSetPids()) {
      this.pids = other.pids;
    }
    if (other.isSetAssWay()) {
      this.assWay = other.assWay;
    }
    this.rows = other.rows;
    this.page = other.page;
    if (other.isSetProjectManage()) {
      this.projectManage = other.projectManage;
    }
  }

  public MonthlyReportRecordCondition deepCopy() {
    return new MonthlyReportRecordCondition(this);
  }

  @Override
  public void clear() {
    this.month = null;
    this.projectName = null;
    this.projectNo = null;
    setStatusIsSet(false);
    this.status = 0;
    this.pids = null;
    this.assWay = null;
    setRowsIsSet(false);
    this.rows = 0;
    setPageIsSet(false);
    this.page = 0;
    this.projectManage = null;
  }

  public String getMonth() {
    return this.month;
  }

  public MonthlyReportRecordCondition setMonth(String month) {
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

  public String getProjectName() {
    return this.projectName;
  }

  public MonthlyReportRecordCondition setProjectName(String projectName) {
    this.projectName = projectName;
    return this;
  }

  public void unsetProjectName() {
    this.projectName = null;
  }

  /** Returns true if field projectName is set (has been assigned a value) and false otherwise */
  public boolean isSetProjectName() {
    return this.projectName != null;
  }

  public void setProjectNameIsSet(boolean value) {
    if (!value) {
      this.projectName = null;
    }
  }

  public String getProjectNo() {
    return this.projectNo;
  }

  public MonthlyReportRecordCondition setProjectNo(String projectNo) {
    this.projectNo = projectNo;
    return this;
  }

  public void unsetProjectNo() {
    this.projectNo = null;
  }

  /** Returns true if field projectNo is set (has been assigned a value) and false otherwise */
  public boolean isSetProjectNo() {
    return this.projectNo != null;
  }

  public void setProjectNoIsSet(boolean value) {
    if (!value) {
      this.projectNo = null;
    }
  }

  public int getStatus() {
    return this.status;
  }

  public MonthlyReportRecordCondition setStatus(int status) {
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

  public String getPids() {
    return this.pids;
  }

  public MonthlyReportRecordCondition setPids(String pids) {
    this.pids = pids;
    return this;
  }

  public void unsetPids() {
    this.pids = null;
  }

  /** Returns true if field pids is set (has been assigned a value) and false otherwise */
  public boolean isSetPids() {
    return this.pids != null;
  }

  public void setPidsIsSet(boolean value) {
    if (!value) {
      this.pids = null;
    }
  }

  public String getAssWay() {
    return this.assWay;
  }

  public MonthlyReportRecordCondition setAssWay(String assWay) {
    this.assWay = assWay;
    return this;
  }

  public void unsetAssWay() {
    this.assWay = null;
  }

  /** Returns true if field assWay is set (has been assigned a value) and false otherwise */
  public boolean isSetAssWay() {
    return this.assWay != null;
  }

  public void setAssWayIsSet(boolean value) {
    if (!value) {
      this.assWay = null;
    }
  }

  public int getRows() {
    return this.rows;
  }

  public MonthlyReportRecordCondition setRows(int rows) {
    this.rows = rows;
    setRowsIsSet(true);
    return this;
  }

  public void unsetRows() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ROWS_ISSET_ID);
  }

  /** Returns true if field rows is set (has been assigned a value) and false otherwise */
  public boolean isSetRows() {
    return EncodingUtils.testBit(__isset_bitfield, __ROWS_ISSET_ID);
  }

  public void setRowsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ROWS_ISSET_ID, value);
  }

  public int getPage() {
    return this.page;
  }

  public MonthlyReportRecordCondition setPage(int page) {
    this.page = page;
    setPageIsSet(true);
    return this;
  }

  public void unsetPage() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PAGE_ISSET_ID);
  }

  /** Returns true if field page is set (has been assigned a value) and false otherwise */
  public boolean isSetPage() {
    return EncodingUtils.testBit(__isset_bitfield, __PAGE_ISSET_ID);
  }

  public void setPageIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PAGE_ISSET_ID, value);
  }

  public String getProjectManage() {
    return this.projectManage;
  }

  public MonthlyReportRecordCondition setProjectManage(String projectManage) {
    this.projectManage = projectManage;
    return this;
  }

  public void unsetProjectManage() {
    this.projectManage = null;
  }

  /** Returns true if field projectManage is set (has been assigned a value) and false otherwise */
  public boolean isSetProjectManage() {
    return this.projectManage != null;
  }

  public void setProjectManageIsSet(boolean value) {
    if (!value) {
      this.projectManage = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case MONTH:
      if (value == null) {
        unsetMonth();
      } else {
        setMonth((String)value);
      }
      break;

    case PROJECT_NAME:
      if (value == null) {
        unsetProjectName();
      } else {
        setProjectName((String)value);
      }
      break;

    case PROJECT_NO:
      if (value == null) {
        unsetProjectNo();
      } else {
        setProjectNo((String)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((Integer)value);
      }
      break;

    case PIDS:
      if (value == null) {
        unsetPids();
      } else {
        setPids((String)value);
      }
      break;

    case ASS_WAY:
      if (value == null) {
        unsetAssWay();
      } else {
        setAssWay((String)value);
      }
      break;

    case ROWS:
      if (value == null) {
        unsetRows();
      } else {
        setRows((Integer)value);
      }
      break;

    case PAGE:
      if (value == null) {
        unsetPage();
      } else {
        setPage((Integer)value);
      }
      break;

    case PROJECT_MANAGE:
      if (value == null) {
        unsetProjectManage();
      } else {
        setProjectManage((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case MONTH:
      return getMonth();

    case PROJECT_NAME:
      return getProjectName();

    case PROJECT_NO:
      return getProjectNo();

    case STATUS:
      return Integer.valueOf(getStatus());

    case PIDS:
      return getPids();

    case ASS_WAY:
      return getAssWay();

    case ROWS:
      return Integer.valueOf(getRows());

    case PAGE:
      return Integer.valueOf(getPage());

    case PROJECT_MANAGE:
      return getProjectManage();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case MONTH:
      return isSetMonth();
    case PROJECT_NAME:
      return isSetProjectName();
    case PROJECT_NO:
      return isSetProjectNo();
    case STATUS:
      return isSetStatus();
    case PIDS:
      return isSetPids();
    case ASS_WAY:
      return isSetAssWay();
    case ROWS:
      return isSetRows();
    case PAGE:
      return isSetPage();
    case PROJECT_MANAGE:
      return isSetProjectManage();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof MonthlyReportRecordCondition)
      return this.equals((MonthlyReportRecordCondition)that);
    return false;
  }

  public boolean equals(MonthlyReportRecordCondition that) {
    if (that == null)
      return false;

    boolean this_present_month = true && this.isSetMonth();
    boolean that_present_month = true && that.isSetMonth();
    if (this_present_month || that_present_month) {
      if (!(this_present_month && that_present_month))
        return false;
      if (!this.month.equals(that.month))
        return false;
    }

    boolean this_present_projectName = true && this.isSetProjectName();
    boolean that_present_projectName = true && that.isSetProjectName();
    if (this_present_projectName || that_present_projectName) {
      if (!(this_present_projectName && that_present_projectName))
        return false;
      if (!this.projectName.equals(that.projectName))
        return false;
    }

    boolean this_present_projectNo = true && this.isSetProjectNo();
    boolean that_present_projectNo = true && that.isSetProjectNo();
    if (this_present_projectNo || that_present_projectNo) {
      if (!(this_present_projectNo && that_present_projectNo))
        return false;
      if (!this.projectNo.equals(that.projectNo))
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

    boolean this_present_pids = true && this.isSetPids();
    boolean that_present_pids = true && that.isSetPids();
    if (this_present_pids || that_present_pids) {
      if (!(this_present_pids && that_present_pids))
        return false;
      if (!this.pids.equals(that.pids))
        return false;
    }

    boolean this_present_assWay = true && this.isSetAssWay();
    boolean that_present_assWay = true && that.isSetAssWay();
    if (this_present_assWay || that_present_assWay) {
      if (!(this_present_assWay && that_present_assWay))
        return false;
      if (!this.assWay.equals(that.assWay))
        return false;
    }

    boolean this_present_rows = true;
    boolean that_present_rows = true;
    if (this_present_rows || that_present_rows) {
      if (!(this_present_rows && that_present_rows))
        return false;
      if (this.rows != that.rows)
        return false;
    }

    boolean this_present_page = true;
    boolean that_present_page = true;
    if (this_present_page || that_present_page) {
      if (!(this_present_page && that_present_page))
        return false;
      if (this.page != that.page)
        return false;
    }

    boolean this_present_projectManage = true && this.isSetProjectManage();
    boolean that_present_projectManage = true && that.isSetProjectManage();
    if (this_present_projectManage || that_present_projectManage) {
      if (!(this_present_projectManage && that_present_projectManage))
        return false;
      if (!this.projectManage.equals(that.projectManage))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_month = true && (isSetMonth());
    list.add(present_month);
    if (present_month)
      list.add(month);

    boolean present_projectName = true && (isSetProjectName());
    list.add(present_projectName);
    if (present_projectName)
      list.add(projectName);

    boolean present_projectNo = true && (isSetProjectNo());
    list.add(present_projectNo);
    if (present_projectNo)
      list.add(projectNo);

    boolean present_status = true;
    list.add(present_status);
    if (present_status)
      list.add(status);

    boolean present_pids = true && (isSetPids());
    list.add(present_pids);
    if (present_pids)
      list.add(pids);

    boolean present_assWay = true && (isSetAssWay());
    list.add(present_assWay);
    if (present_assWay)
      list.add(assWay);

    boolean present_rows = true;
    list.add(present_rows);
    if (present_rows)
      list.add(rows);

    boolean present_page = true;
    list.add(present_page);
    if (present_page)
      list.add(page);

    boolean present_projectManage = true && (isSetProjectManage());
    list.add(present_projectManage);
    if (present_projectManage)
      list.add(projectManage);

    return list.hashCode();
  }

  @Override
  public int compareTo(MonthlyReportRecordCondition other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

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
    lastComparison = Boolean.valueOf(isSetProjectName()).compareTo(other.isSetProjectName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProjectName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.projectName, other.projectName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProjectNo()).compareTo(other.isSetProjectNo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProjectNo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.projectNo, other.projectNo);
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
    lastComparison = Boolean.valueOf(isSetPids()).compareTo(other.isSetPids());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPids()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pids, other.pids);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAssWay()).compareTo(other.isSetAssWay());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAssWay()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.assWay, other.assWay);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRows()).compareTo(other.isSetRows());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRows()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.rows, other.rows);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPage()).compareTo(other.isSetPage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.page, other.page);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProjectManage()).compareTo(other.isSetProjectManage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProjectManage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.projectManage, other.projectManage);
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
    StringBuilder sb = new StringBuilder("MonthlyReportRecordCondition(");
    boolean first = true;

    sb.append("month:");
    if (this.month == null) {
      sb.append("null");
    } else {
      sb.append(this.month);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("projectName:");
    if (this.projectName == null) {
      sb.append("null");
    } else {
      sb.append(this.projectName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("projectNo:");
    if (this.projectNo == null) {
      sb.append("null");
    } else {
      sb.append(this.projectNo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("status:");
    sb.append(this.status);
    first = false;
    if (!first) sb.append(", ");
    sb.append("pids:");
    if (this.pids == null) {
      sb.append("null");
    } else {
      sb.append(this.pids);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("assWay:");
    if (this.assWay == null) {
      sb.append("null");
    } else {
      sb.append(this.assWay);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("rows:");
    sb.append(this.rows);
    first = false;
    if (!first) sb.append(", ");
    sb.append("page:");
    sb.append(this.page);
    first = false;
    if (!first) sb.append(", ");
    sb.append("projectManage:");
    if (this.projectManage == null) {
      sb.append("null");
    } else {
      sb.append(this.projectManage);
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

  private static class MonthlyReportRecordConditionStandardSchemeFactory implements SchemeFactory {
    public MonthlyReportRecordConditionStandardScheme getScheme() {
      return new MonthlyReportRecordConditionStandardScheme();
    }
  }

  private static class MonthlyReportRecordConditionStandardScheme extends StandardScheme<MonthlyReportRecordCondition> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MonthlyReportRecordCondition struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MONTH
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.month = iprot.readString();
              struct.setMonthIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PROJECT_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.projectName = iprot.readString();
              struct.setProjectNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PROJECT_NO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.projectNo = iprot.readString();
              struct.setProjectNoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.status = iprot.readI32();
              struct.setStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PIDS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.pids = iprot.readString();
              struct.setPidsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // ASS_WAY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.assWay = iprot.readString();
              struct.setAssWayIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // ROWS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.rows = iprot.readI32();
              struct.setRowsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // PAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.page = iprot.readI32();
              struct.setPageIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 9: // PROJECT_MANAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.projectManage = iprot.readString();
              struct.setProjectManageIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MonthlyReportRecordCondition struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.month != null) {
        oprot.writeFieldBegin(MONTH_FIELD_DESC);
        oprot.writeString(struct.month);
        oprot.writeFieldEnd();
      }
      if (struct.projectName != null) {
        oprot.writeFieldBegin(PROJECT_NAME_FIELD_DESC);
        oprot.writeString(struct.projectName);
        oprot.writeFieldEnd();
      }
      if (struct.projectNo != null) {
        oprot.writeFieldBegin(PROJECT_NO_FIELD_DESC);
        oprot.writeString(struct.projectNo);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(STATUS_FIELD_DESC);
      oprot.writeI32(struct.status);
      oprot.writeFieldEnd();
      if (struct.pids != null) {
        oprot.writeFieldBegin(PIDS_FIELD_DESC);
        oprot.writeString(struct.pids);
        oprot.writeFieldEnd();
      }
      if (struct.assWay != null) {
        oprot.writeFieldBegin(ASS_WAY_FIELD_DESC);
        oprot.writeString(struct.assWay);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(ROWS_FIELD_DESC);
      oprot.writeI32(struct.rows);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PAGE_FIELD_DESC);
      oprot.writeI32(struct.page);
      oprot.writeFieldEnd();
      if (struct.projectManage != null) {
        oprot.writeFieldBegin(PROJECT_MANAGE_FIELD_DESC);
        oprot.writeString(struct.projectManage);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MonthlyReportRecordConditionTupleSchemeFactory implements SchemeFactory {
    public MonthlyReportRecordConditionTupleScheme getScheme() {
      return new MonthlyReportRecordConditionTupleScheme();
    }
  }

  private static class MonthlyReportRecordConditionTupleScheme extends TupleScheme<MonthlyReportRecordCondition> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MonthlyReportRecordCondition struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetMonth()) {
        optionals.set(0);
      }
      if (struct.isSetProjectName()) {
        optionals.set(1);
      }
      if (struct.isSetProjectNo()) {
        optionals.set(2);
      }
      if (struct.isSetStatus()) {
        optionals.set(3);
      }
      if (struct.isSetPids()) {
        optionals.set(4);
      }
      if (struct.isSetAssWay()) {
        optionals.set(5);
      }
      if (struct.isSetRows()) {
        optionals.set(6);
      }
      if (struct.isSetPage()) {
        optionals.set(7);
      }
      if (struct.isSetProjectManage()) {
        optionals.set(8);
      }
      oprot.writeBitSet(optionals, 9);
      if (struct.isSetMonth()) {
        oprot.writeString(struct.month);
      }
      if (struct.isSetProjectName()) {
        oprot.writeString(struct.projectName);
      }
      if (struct.isSetProjectNo()) {
        oprot.writeString(struct.projectNo);
      }
      if (struct.isSetStatus()) {
        oprot.writeI32(struct.status);
      }
      if (struct.isSetPids()) {
        oprot.writeString(struct.pids);
      }
      if (struct.isSetAssWay()) {
        oprot.writeString(struct.assWay);
      }
      if (struct.isSetRows()) {
        oprot.writeI32(struct.rows);
      }
      if (struct.isSetPage()) {
        oprot.writeI32(struct.page);
      }
      if (struct.isSetProjectManage()) {
        oprot.writeString(struct.projectManage);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MonthlyReportRecordCondition struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(9);
      if (incoming.get(0)) {
        struct.month = iprot.readString();
        struct.setMonthIsSet(true);
      }
      if (incoming.get(1)) {
        struct.projectName = iprot.readString();
        struct.setProjectNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.projectNo = iprot.readString();
        struct.setProjectNoIsSet(true);
      }
      if (incoming.get(3)) {
        struct.status = iprot.readI32();
        struct.setStatusIsSet(true);
      }
      if (incoming.get(4)) {
        struct.pids = iprot.readString();
        struct.setPidsIsSet(true);
      }
      if (incoming.get(5)) {
        struct.assWay = iprot.readString();
        struct.setAssWayIsSet(true);
      }
      if (incoming.get(6)) {
        struct.rows = iprot.readI32();
        struct.setRowsIsSet(true);
      }
      if (incoming.get(7)) {
        struct.page = iprot.readI32();
        struct.setPageIsSet(true);
      }
      if (incoming.get(8)) {
        struct.projectManage = iprot.readString();
        struct.setProjectManageIsSet(true);
      }
    }
  }

}
