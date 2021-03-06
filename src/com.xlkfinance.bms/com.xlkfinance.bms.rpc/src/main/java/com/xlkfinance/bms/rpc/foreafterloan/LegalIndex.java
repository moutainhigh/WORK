/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xlkfinance.bms.rpc.foreafterloan;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2017-3-1")
public class LegalIndex implements org.apache.thrift.TBase<LegalIndex, LegalIndex._Fields>, java.io.Serializable, Cloneable, Comparable<LegalIndex> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("LegalIndex");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField REPORT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("reportId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField LEGAL_CONTENT_FIELD_DESC = new org.apache.thrift.protocol.TField("legalContent", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField CREATE_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("createDate", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField CREATE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("createId", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField UPDATE_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("updateDate", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField UPDATE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("updateId", org.apache.thrift.protocol.TType.STRING, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new LegalIndexStandardSchemeFactory());
    schemes.put(TupleScheme.class, new LegalIndexTupleSchemeFactory());
  }

  public int pid; // required
  public int reportId; // required
  public int status; // required
  public String legalContent; // required
  public String createDate; // required
  public String createId; // required
  public String updateDate; // required
  public String updateId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    REPORT_ID((short)2, "reportId"),
    STATUS((short)3, "status"),
    LEGAL_CONTENT((short)4, "legalContent"),
    CREATE_DATE((short)5, "createDate"),
    CREATE_ID((short)6, "createId"),
    UPDATE_DATE((short)7, "updateDate"),
    UPDATE_ID((short)8, "updateId");

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
        case 3: // STATUS
          return STATUS;
        case 4: // LEGAL_CONTENT
          return LEGAL_CONTENT;
        case 5: // CREATE_DATE
          return CREATE_DATE;
        case 6: // CREATE_ID
          return CREATE_ID;
        case 7: // UPDATE_DATE
          return UPDATE_DATE;
        case 8: // UPDATE_ID
          return UPDATE_ID;
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
  private static final int __REPORTID_ISSET_ID = 1;
  private static final int __STATUS_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.REPORT_ID, new org.apache.thrift.meta_data.FieldMetaData("reportId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LEGAL_CONTENT, new org.apache.thrift.meta_data.FieldMetaData("legalContent", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_DATE, new org.apache.thrift.meta_data.FieldMetaData("createDate", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_ID, new org.apache.thrift.meta_data.FieldMetaData("createId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.UPDATE_DATE, new org.apache.thrift.meta_data.FieldMetaData("updateDate", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.UPDATE_ID, new org.apache.thrift.meta_data.FieldMetaData("updateId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(LegalIndex.class, metaDataMap);
  }

  public LegalIndex() {
  }

  public LegalIndex(
    int pid,
    int reportId,
    int status,
    String legalContent,
    String createDate,
    String createId,
    String updateDate,
    String updateId)
  {
    this();
    this.pid = pid;
    setPidIsSet(true);
    this.reportId = reportId;
    setReportIdIsSet(true);
    this.status = status;
    setStatusIsSet(true);
    this.legalContent = legalContent;
    this.createDate = createDate;
    this.createId = createId;
    this.updateDate = updateDate;
    this.updateId = updateId;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public LegalIndex(LegalIndex other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pid = other.pid;
    this.reportId = other.reportId;
    this.status = other.status;
    if (other.isSetLegalContent()) {
      this.legalContent = other.legalContent;
    }
    if (other.isSetCreateDate()) {
      this.createDate = other.createDate;
    }
    if (other.isSetCreateId()) {
      this.createId = other.createId;
    }
    if (other.isSetUpdateDate()) {
      this.updateDate = other.updateDate;
    }
    if (other.isSetUpdateId()) {
      this.updateId = other.updateId;
    }
  }

  public LegalIndex deepCopy() {
    return new LegalIndex(this);
  }

  @Override
  public void clear() {
    setPidIsSet(false);
    this.pid = 0;
    setReportIdIsSet(false);
    this.reportId = 0;
    setStatusIsSet(false);
    this.status = 0;
    this.legalContent = null;
    this.createDate = null;
    this.createId = null;
    this.updateDate = null;
    this.updateId = null;
  }

  public int getPid() {
    return this.pid;
  }

  public LegalIndex setPid(int pid) {
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

  public int getReportId() {
    return this.reportId;
  }

  public LegalIndex setReportId(int reportId) {
    this.reportId = reportId;
    setReportIdIsSet(true);
    return this;
  }

  public void unsetReportId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __REPORTID_ISSET_ID);
  }

  /** Returns true if field reportId is set (has been assigned a value) and false otherwise */
  public boolean isSetReportId() {
    return EncodingUtils.testBit(__isset_bitfield, __REPORTID_ISSET_ID);
  }

  public void setReportIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __REPORTID_ISSET_ID, value);
  }

  public int getStatus() {
    return this.status;
  }

  public LegalIndex setStatus(int status) {
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

  public String getLegalContent() {
    return this.legalContent;
  }

  public LegalIndex setLegalContent(String legalContent) {
    this.legalContent = legalContent;
    return this;
  }

  public void unsetLegalContent() {
    this.legalContent = null;
  }

  /** Returns true if field legalContent is set (has been assigned a value) and false otherwise */
  public boolean isSetLegalContent() {
    return this.legalContent != null;
  }

  public void setLegalContentIsSet(boolean value) {
    if (!value) {
      this.legalContent = null;
    }
  }

  public String getCreateDate() {
    return this.createDate;
  }

  public LegalIndex setCreateDate(String createDate) {
    this.createDate = createDate;
    return this;
  }

  public void unsetCreateDate() {
    this.createDate = null;
  }

  /** Returns true if field createDate is set (has been assigned a value) and false otherwise */
  public boolean isSetCreateDate() {
    return this.createDate != null;
  }

  public void setCreateDateIsSet(boolean value) {
    if (!value) {
      this.createDate = null;
    }
  }

  public String getCreateId() {
    return this.createId;
  }

  public LegalIndex setCreateId(String createId) {
    this.createId = createId;
    return this;
  }

  public void unsetCreateId() {
    this.createId = null;
  }

  /** Returns true if field createId is set (has been assigned a value) and false otherwise */
  public boolean isSetCreateId() {
    return this.createId != null;
  }

  public void setCreateIdIsSet(boolean value) {
    if (!value) {
      this.createId = null;
    }
  }

  public String getUpdateDate() {
    return this.updateDate;
  }

  public LegalIndex setUpdateDate(String updateDate) {
    this.updateDate = updateDate;
    return this;
  }

  public void unsetUpdateDate() {
    this.updateDate = null;
  }

  /** Returns true if field updateDate is set (has been assigned a value) and false otherwise */
  public boolean isSetUpdateDate() {
    return this.updateDate != null;
  }

  public void setUpdateDateIsSet(boolean value) {
    if (!value) {
      this.updateDate = null;
    }
  }

  public String getUpdateId() {
    return this.updateId;
  }

  public LegalIndex setUpdateId(String updateId) {
    this.updateId = updateId;
    return this;
  }

  public void unsetUpdateId() {
    this.updateId = null;
  }

  /** Returns true if field updateId is set (has been assigned a value) and false otherwise */
  public boolean isSetUpdateId() {
    return this.updateId != null;
  }

  public void setUpdateIdIsSet(boolean value) {
    if (!value) {
      this.updateId = null;
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

    case REPORT_ID:
      if (value == null) {
        unsetReportId();
      } else {
        setReportId((Integer)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((Integer)value);
      }
      break;

    case LEGAL_CONTENT:
      if (value == null) {
        unsetLegalContent();
      } else {
        setLegalContent((String)value);
      }
      break;

    case CREATE_DATE:
      if (value == null) {
        unsetCreateDate();
      } else {
        setCreateDate((String)value);
      }
      break;

    case CREATE_ID:
      if (value == null) {
        unsetCreateId();
      } else {
        setCreateId((String)value);
      }
      break;

    case UPDATE_DATE:
      if (value == null) {
        unsetUpdateDate();
      } else {
        setUpdateDate((String)value);
      }
      break;

    case UPDATE_ID:
      if (value == null) {
        unsetUpdateId();
      } else {
        setUpdateId((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PID:
      return Integer.valueOf(getPid());

    case REPORT_ID:
      return Integer.valueOf(getReportId());

    case STATUS:
      return Integer.valueOf(getStatus());

    case LEGAL_CONTENT:
      return getLegalContent();

    case CREATE_DATE:
      return getCreateDate();

    case CREATE_ID:
      return getCreateId();

    case UPDATE_DATE:
      return getUpdateDate();

    case UPDATE_ID:
      return getUpdateId();

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
    case STATUS:
      return isSetStatus();
    case LEGAL_CONTENT:
      return isSetLegalContent();
    case CREATE_DATE:
      return isSetCreateDate();
    case CREATE_ID:
      return isSetCreateId();
    case UPDATE_DATE:
      return isSetUpdateDate();
    case UPDATE_ID:
      return isSetUpdateId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof LegalIndex)
      return this.equals((LegalIndex)that);
    return false;
  }

  public boolean equals(LegalIndex that) {
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

    boolean this_present_reportId = true;
    boolean that_present_reportId = true;
    if (this_present_reportId || that_present_reportId) {
      if (!(this_present_reportId && that_present_reportId))
        return false;
      if (this.reportId != that.reportId)
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

    boolean this_present_legalContent = true && this.isSetLegalContent();
    boolean that_present_legalContent = true && that.isSetLegalContent();
    if (this_present_legalContent || that_present_legalContent) {
      if (!(this_present_legalContent && that_present_legalContent))
        return false;
      if (!this.legalContent.equals(that.legalContent))
        return false;
    }

    boolean this_present_createDate = true && this.isSetCreateDate();
    boolean that_present_createDate = true && that.isSetCreateDate();
    if (this_present_createDate || that_present_createDate) {
      if (!(this_present_createDate && that_present_createDate))
        return false;
      if (!this.createDate.equals(that.createDate))
        return false;
    }

    boolean this_present_createId = true && this.isSetCreateId();
    boolean that_present_createId = true && that.isSetCreateId();
    if (this_present_createId || that_present_createId) {
      if (!(this_present_createId && that_present_createId))
        return false;
      if (!this.createId.equals(that.createId))
        return false;
    }

    boolean this_present_updateDate = true && this.isSetUpdateDate();
    boolean that_present_updateDate = true && that.isSetUpdateDate();
    if (this_present_updateDate || that_present_updateDate) {
      if (!(this_present_updateDate && that_present_updateDate))
        return false;
      if (!this.updateDate.equals(that.updateDate))
        return false;
    }

    boolean this_present_updateId = true && this.isSetUpdateId();
    boolean that_present_updateId = true && that.isSetUpdateId();
    if (this_present_updateId || that_present_updateId) {
      if (!(this_present_updateId && that_present_updateId))
        return false;
      if (!this.updateId.equals(that.updateId))
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

    boolean present_reportId = true;
    list.add(present_reportId);
    if (present_reportId)
      list.add(reportId);

    boolean present_status = true;
    list.add(present_status);
    if (present_status)
      list.add(status);

    boolean present_legalContent = true && (isSetLegalContent());
    list.add(present_legalContent);
    if (present_legalContent)
      list.add(legalContent);

    boolean present_createDate = true && (isSetCreateDate());
    list.add(present_createDate);
    if (present_createDate)
      list.add(createDate);

    boolean present_createId = true && (isSetCreateId());
    list.add(present_createId);
    if (present_createId)
      list.add(createId);

    boolean present_updateDate = true && (isSetUpdateDate());
    list.add(present_updateDate);
    if (present_updateDate)
      list.add(updateDate);

    boolean present_updateId = true && (isSetUpdateId());
    list.add(present_updateId);
    if (present_updateId)
      list.add(updateId);

    return list.hashCode();
  }

  @Override
  public int compareTo(LegalIndex other) {
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
    lastComparison = Boolean.valueOf(isSetLegalContent()).compareTo(other.isSetLegalContent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLegalContent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.legalContent, other.legalContent);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCreateDate()).compareTo(other.isSetCreateDate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateDate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createDate, other.createDate);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCreateId()).compareTo(other.isSetCreateId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createId, other.createId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUpdateDate()).compareTo(other.isSetUpdateDate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpdateDate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.updateDate, other.updateDate);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUpdateId()).compareTo(other.isSetUpdateId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpdateId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.updateId, other.updateId);
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
    StringBuilder sb = new StringBuilder("LegalIndex(");
    boolean first = true;

    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("reportId:");
    sb.append(this.reportId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("status:");
    sb.append(this.status);
    first = false;
    if (!first) sb.append(", ");
    sb.append("legalContent:");
    if (this.legalContent == null) {
      sb.append("null");
    } else {
      sb.append(this.legalContent);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("createDate:");
    if (this.createDate == null) {
      sb.append("null");
    } else {
      sb.append(this.createDate);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("createId:");
    if (this.createId == null) {
      sb.append("null");
    } else {
      sb.append(this.createId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("updateDate:");
    if (this.updateDate == null) {
      sb.append("null");
    } else {
      sb.append(this.updateDate);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("updateId:");
    if (this.updateId == null) {
      sb.append("null");
    } else {
      sb.append(this.updateId);
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

  private static class LegalIndexStandardSchemeFactory implements SchemeFactory {
    public LegalIndexStandardScheme getScheme() {
      return new LegalIndexStandardScheme();
    }
  }

  private static class LegalIndexStandardScheme extends StandardScheme<LegalIndex> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, LegalIndex struct) throws org.apache.thrift.TException {
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
          case 2: // REPORT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.reportId = iprot.readI32();
              struct.setReportIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.status = iprot.readI32();
              struct.setStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // LEGAL_CONTENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.legalContent = iprot.readString();
              struct.setLegalContentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // CREATE_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.createDate = iprot.readString();
              struct.setCreateDateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CREATE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.createId = iprot.readString();
              struct.setCreateIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // UPDATE_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.updateDate = iprot.readString();
              struct.setUpdateDateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // UPDATE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.updateId = iprot.readString();
              struct.setUpdateIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, LegalIndex struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PID_FIELD_DESC);
      oprot.writeI32(struct.pid);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(REPORT_ID_FIELD_DESC);
      oprot.writeI32(struct.reportId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(STATUS_FIELD_DESC);
      oprot.writeI32(struct.status);
      oprot.writeFieldEnd();
      if (struct.legalContent != null) {
        oprot.writeFieldBegin(LEGAL_CONTENT_FIELD_DESC);
        oprot.writeString(struct.legalContent);
        oprot.writeFieldEnd();
      }
      if (struct.createDate != null) {
        oprot.writeFieldBegin(CREATE_DATE_FIELD_DESC);
        oprot.writeString(struct.createDate);
        oprot.writeFieldEnd();
      }
      if (struct.createId != null) {
        oprot.writeFieldBegin(CREATE_ID_FIELD_DESC);
        oprot.writeString(struct.createId);
        oprot.writeFieldEnd();
      }
      if (struct.updateDate != null) {
        oprot.writeFieldBegin(UPDATE_DATE_FIELD_DESC);
        oprot.writeString(struct.updateDate);
        oprot.writeFieldEnd();
      }
      if (struct.updateId != null) {
        oprot.writeFieldBegin(UPDATE_ID_FIELD_DESC);
        oprot.writeString(struct.updateId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class LegalIndexTupleSchemeFactory implements SchemeFactory {
    public LegalIndexTupleScheme getScheme() {
      return new LegalIndexTupleScheme();
    }
  }

  private static class LegalIndexTupleScheme extends TupleScheme<LegalIndex> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, LegalIndex struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPid()) {
        optionals.set(0);
      }
      if (struct.isSetReportId()) {
        optionals.set(1);
      }
      if (struct.isSetStatus()) {
        optionals.set(2);
      }
      if (struct.isSetLegalContent()) {
        optionals.set(3);
      }
      if (struct.isSetCreateDate()) {
        optionals.set(4);
      }
      if (struct.isSetCreateId()) {
        optionals.set(5);
      }
      if (struct.isSetUpdateDate()) {
        optionals.set(6);
      }
      if (struct.isSetUpdateId()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetPid()) {
        oprot.writeI32(struct.pid);
      }
      if (struct.isSetReportId()) {
        oprot.writeI32(struct.reportId);
      }
      if (struct.isSetStatus()) {
        oprot.writeI32(struct.status);
      }
      if (struct.isSetLegalContent()) {
        oprot.writeString(struct.legalContent);
      }
      if (struct.isSetCreateDate()) {
        oprot.writeString(struct.createDate);
      }
      if (struct.isSetCreateId()) {
        oprot.writeString(struct.createId);
      }
      if (struct.isSetUpdateDate()) {
        oprot.writeString(struct.updateDate);
      }
      if (struct.isSetUpdateId()) {
        oprot.writeString(struct.updateId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, LegalIndex struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.pid = iprot.readI32();
        struct.setPidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.reportId = iprot.readI32();
        struct.setReportIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.status = iprot.readI32();
        struct.setStatusIsSet(true);
      }
      if (incoming.get(3)) {
        struct.legalContent = iprot.readString();
        struct.setLegalContentIsSet(true);
      }
      if (incoming.get(4)) {
        struct.createDate = iprot.readString();
        struct.setCreateDateIsSet(true);
      }
      if (incoming.get(5)) {
        struct.createId = iprot.readString();
        struct.setCreateIdIsSet(true);
      }
      if (incoming.get(6)) {
        struct.updateDate = iprot.readString();
        struct.setUpdateDateIsSet(true);
      }
      if (incoming.get(7)) {
        struct.updateId = iprot.readString();
        struct.setUpdateIdIsSet(true);
      }
    }
  }

}

