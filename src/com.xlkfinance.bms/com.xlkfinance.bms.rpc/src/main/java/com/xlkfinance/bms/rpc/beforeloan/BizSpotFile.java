/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xlkfinance.bms.rpc.beforeloan;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2017-12-26")
public class BizSpotFile implements org.apache.thrift.TBase<BizSpotFile, BizSpotFile._Fields>, java.io.Serializable, Cloneable, Comparable<BizSpotFile> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BizSpotFile");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField SPOT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("spotId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField FILE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("fileId", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField FILE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("fileName", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField FILE_URL_FIELD_DESC = new org.apache.thrift.protocol.TField("fileUrl", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField FILE_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("fileType", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.I32, (short)8);
  private static final org.apache.thrift.protocol.TField ROWS_FIELD_DESC = new org.apache.thrift.protocol.TField("rows", org.apache.thrift.protocol.TType.I32, (short)9);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BizSpotFileStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BizSpotFileTupleSchemeFactory());
  }

  public int pid; // required
  public int spotId; // required
  public int fileId; // required
  public int status; // required
  public String fileName; // required
  public String fileUrl; // required
  public String fileType; // required
  public int page; // required
  public int rows; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    SPOT_ID((short)2, "spotId"),
    FILE_ID((short)3, "fileId"),
    STATUS((short)4, "status"),
    FILE_NAME((short)5, "fileName"),
    FILE_URL((short)6, "fileUrl"),
    FILE_TYPE((short)7, "fileType"),
    PAGE((short)8, "page"),
    ROWS((short)9, "rows");

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
        case 2: // SPOT_ID
          return SPOT_ID;
        case 3: // FILE_ID
          return FILE_ID;
        case 4: // STATUS
          return STATUS;
        case 5: // FILE_NAME
          return FILE_NAME;
        case 6: // FILE_URL
          return FILE_URL;
        case 7: // FILE_TYPE
          return FILE_TYPE;
        case 8: // PAGE
          return PAGE;
        case 9: // ROWS
          return ROWS;
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
  private static final int __SPOTID_ISSET_ID = 1;
  private static final int __FILEID_ISSET_ID = 2;
  private static final int __STATUS_ISSET_ID = 3;
  private static final int __PAGE_ISSET_ID = 4;
  private static final int __ROWS_ISSET_ID = 5;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.SPOT_ID, new org.apache.thrift.meta_data.FieldMetaData("spotId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.FILE_ID, new org.apache.thrift.meta_data.FieldMetaData("fileId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.FILE_NAME, new org.apache.thrift.meta_data.FieldMetaData("fileName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FILE_URL, new org.apache.thrift.meta_data.FieldMetaData("fileUrl", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FILE_TYPE, new org.apache.thrift.meta_data.FieldMetaData("fileType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PAGE, new org.apache.thrift.meta_data.FieldMetaData("page", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ROWS, new org.apache.thrift.meta_data.FieldMetaData("rows", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BizSpotFile.class, metaDataMap);
  }

  public BizSpotFile() {
  }

  public BizSpotFile(
    int pid,
    int spotId,
    int fileId,
    int status,
    String fileName,
    String fileUrl,
    String fileType,
    int page,
    int rows)
  {
    this();
    this.pid = pid;
    setPidIsSet(true);
    this.spotId = spotId;
    setSpotIdIsSet(true);
    this.fileId = fileId;
    setFileIdIsSet(true);
    this.status = status;
    setStatusIsSet(true);
    this.fileName = fileName;
    this.fileUrl = fileUrl;
    this.fileType = fileType;
    this.page = page;
    setPageIsSet(true);
    this.rows = rows;
    setRowsIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BizSpotFile(BizSpotFile other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pid = other.pid;
    this.spotId = other.spotId;
    this.fileId = other.fileId;
    this.status = other.status;
    if (other.isSetFileName()) {
      this.fileName = other.fileName;
    }
    if (other.isSetFileUrl()) {
      this.fileUrl = other.fileUrl;
    }
    if (other.isSetFileType()) {
      this.fileType = other.fileType;
    }
    this.page = other.page;
    this.rows = other.rows;
  }

  public BizSpotFile deepCopy() {
    return new BizSpotFile(this);
  }

  @Override
  public void clear() {
    setPidIsSet(false);
    this.pid = 0;
    setSpotIdIsSet(false);
    this.spotId = 0;
    setFileIdIsSet(false);
    this.fileId = 0;
    setStatusIsSet(false);
    this.status = 0;
    this.fileName = null;
    this.fileUrl = null;
    this.fileType = null;
    setPageIsSet(false);
    this.page = 0;
    setRowsIsSet(false);
    this.rows = 0;
  }

  public int getPid() {
    return this.pid;
  }

  public BizSpotFile setPid(int pid) {
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

  public int getSpotId() {
    return this.spotId;
  }

  public BizSpotFile setSpotId(int spotId) {
    this.spotId = spotId;
    setSpotIdIsSet(true);
    return this;
  }

  public void unsetSpotId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SPOTID_ISSET_ID);
  }

  /** Returns true if field spotId is set (has been assigned a value) and false otherwise */
  public boolean isSetSpotId() {
    return EncodingUtils.testBit(__isset_bitfield, __SPOTID_ISSET_ID);
  }

  public void setSpotIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SPOTID_ISSET_ID, value);
  }

  public int getFileId() {
    return this.fileId;
  }

  public BizSpotFile setFileId(int fileId) {
    this.fileId = fileId;
    setFileIdIsSet(true);
    return this;
  }

  public void unsetFileId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __FILEID_ISSET_ID);
  }

  /** Returns true if field fileId is set (has been assigned a value) and false otherwise */
  public boolean isSetFileId() {
    return EncodingUtils.testBit(__isset_bitfield, __FILEID_ISSET_ID);
  }

  public void setFileIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __FILEID_ISSET_ID, value);
  }

  public int getStatus() {
    return this.status;
  }

  public BizSpotFile setStatus(int status) {
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

  public String getFileName() {
    return this.fileName;
  }

  public BizSpotFile setFileName(String fileName) {
    this.fileName = fileName;
    return this;
  }

  public void unsetFileName() {
    this.fileName = null;
  }

  /** Returns true if field fileName is set (has been assigned a value) and false otherwise */
  public boolean isSetFileName() {
    return this.fileName != null;
  }

  public void setFileNameIsSet(boolean value) {
    if (!value) {
      this.fileName = null;
    }
  }

  public String getFileUrl() {
    return this.fileUrl;
  }

  public BizSpotFile setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
    return this;
  }

  public void unsetFileUrl() {
    this.fileUrl = null;
  }

  /** Returns true if field fileUrl is set (has been assigned a value) and false otherwise */
  public boolean isSetFileUrl() {
    return this.fileUrl != null;
  }

  public void setFileUrlIsSet(boolean value) {
    if (!value) {
      this.fileUrl = null;
    }
  }

  public String getFileType() {
    return this.fileType;
  }

  public BizSpotFile setFileType(String fileType) {
    this.fileType = fileType;
    return this;
  }

  public void unsetFileType() {
    this.fileType = null;
  }

  /** Returns true if field fileType is set (has been assigned a value) and false otherwise */
  public boolean isSetFileType() {
    return this.fileType != null;
  }

  public void setFileTypeIsSet(boolean value) {
    if (!value) {
      this.fileType = null;
    }
  }

  public int getPage() {
    return this.page;
  }

  public BizSpotFile setPage(int page) {
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

  public int getRows() {
    return this.rows;
  }

  public BizSpotFile setRows(int rows) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PID:
      if (value == null) {
        unsetPid();
      } else {
        setPid((Integer)value);
      }
      break;

    case SPOT_ID:
      if (value == null) {
        unsetSpotId();
      } else {
        setSpotId((Integer)value);
      }
      break;

    case FILE_ID:
      if (value == null) {
        unsetFileId();
      } else {
        setFileId((Integer)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((Integer)value);
      }
      break;

    case FILE_NAME:
      if (value == null) {
        unsetFileName();
      } else {
        setFileName((String)value);
      }
      break;

    case FILE_URL:
      if (value == null) {
        unsetFileUrl();
      } else {
        setFileUrl((String)value);
      }
      break;

    case FILE_TYPE:
      if (value == null) {
        unsetFileType();
      } else {
        setFileType((String)value);
      }
      break;

    case PAGE:
      if (value == null) {
        unsetPage();
      } else {
        setPage((Integer)value);
      }
      break;

    case ROWS:
      if (value == null) {
        unsetRows();
      } else {
        setRows((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PID:
      return Integer.valueOf(getPid());

    case SPOT_ID:
      return Integer.valueOf(getSpotId());

    case FILE_ID:
      return Integer.valueOf(getFileId());

    case STATUS:
      return Integer.valueOf(getStatus());

    case FILE_NAME:
      return getFileName();

    case FILE_URL:
      return getFileUrl();

    case FILE_TYPE:
      return getFileType();

    case PAGE:
      return Integer.valueOf(getPage());

    case ROWS:
      return Integer.valueOf(getRows());

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
    case SPOT_ID:
      return isSetSpotId();
    case FILE_ID:
      return isSetFileId();
    case STATUS:
      return isSetStatus();
    case FILE_NAME:
      return isSetFileName();
    case FILE_URL:
      return isSetFileUrl();
    case FILE_TYPE:
      return isSetFileType();
    case PAGE:
      return isSetPage();
    case ROWS:
      return isSetRows();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BizSpotFile)
      return this.equals((BizSpotFile)that);
    return false;
  }

  public boolean equals(BizSpotFile that) {
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

    boolean this_present_spotId = true;
    boolean that_present_spotId = true;
    if (this_present_spotId || that_present_spotId) {
      if (!(this_present_spotId && that_present_spotId))
        return false;
      if (this.spotId != that.spotId)
        return false;
    }

    boolean this_present_fileId = true;
    boolean that_present_fileId = true;
    if (this_present_fileId || that_present_fileId) {
      if (!(this_present_fileId && that_present_fileId))
        return false;
      if (this.fileId != that.fileId)
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

    boolean this_present_fileName = true && this.isSetFileName();
    boolean that_present_fileName = true && that.isSetFileName();
    if (this_present_fileName || that_present_fileName) {
      if (!(this_present_fileName && that_present_fileName))
        return false;
      if (!this.fileName.equals(that.fileName))
        return false;
    }

    boolean this_present_fileUrl = true && this.isSetFileUrl();
    boolean that_present_fileUrl = true && that.isSetFileUrl();
    if (this_present_fileUrl || that_present_fileUrl) {
      if (!(this_present_fileUrl && that_present_fileUrl))
        return false;
      if (!this.fileUrl.equals(that.fileUrl))
        return false;
    }

    boolean this_present_fileType = true && this.isSetFileType();
    boolean that_present_fileType = true && that.isSetFileType();
    if (this_present_fileType || that_present_fileType) {
      if (!(this_present_fileType && that_present_fileType))
        return false;
      if (!this.fileType.equals(that.fileType))
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

    boolean this_present_rows = true;
    boolean that_present_rows = true;
    if (this_present_rows || that_present_rows) {
      if (!(this_present_rows && that_present_rows))
        return false;
      if (this.rows != that.rows)
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

    boolean present_spotId = true;
    list.add(present_spotId);
    if (present_spotId)
      list.add(spotId);

    boolean present_fileId = true;
    list.add(present_fileId);
    if (present_fileId)
      list.add(fileId);

    boolean present_status = true;
    list.add(present_status);
    if (present_status)
      list.add(status);

    boolean present_fileName = true && (isSetFileName());
    list.add(present_fileName);
    if (present_fileName)
      list.add(fileName);

    boolean present_fileUrl = true && (isSetFileUrl());
    list.add(present_fileUrl);
    if (present_fileUrl)
      list.add(fileUrl);

    boolean present_fileType = true && (isSetFileType());
    list.add(present_fileType);
    if (present_fileType)
      list.add(fileType);

    boolean present_page = true;
    list.add(present_page);
    if (present_page)
      list.add(page);

    boolean present_rows = true;
    list.add(present_rows);
    if (present_rows)
      list.add(rows);

    return list.hashCode();
  }

  @Override
  public int compareTo(BizSpotFile other) {
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
    lastComparison = Boolean.valueOf(isSetSpotId()).compareTo(other.isSetSpotId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSpotId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.spotId, other.spotId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFileId()).compareTo(other.isSetFileId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileId, other.fileId);
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
    lastComparison = Boolean.valueOf(isSetFileName()).compareTo(other.isSetFileName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileName, other.fileName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFileUrl()).compareTo(other.isSetFileUrl());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileUrl()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileUrl, other.fileUrl);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFileType()).compareTo(other.isSetFileType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileType, other.fileType);
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
    StringBuilder sb = new StringBuilder("BizSpotFile(");
    boolean first = true;

    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("spotId:");
    sb.append(this.spotId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("fileId:");
    sb.append(this.fileId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("status:");
    sb.append(this.status);
    first = false;
    if (!first) sb.append(", ");
    sb.append("fileName:");
    if (this.fileName == null) {
      sb.append("null");
    } else {
      sb.append(this.fileName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("fileUrl:");
    if (this.fileUrl == null) {
      sb.append("null");
    } else {
      sb.append(this.fileUrl);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("fileType:");
    if (this.fileType == null) {
      sb.append("null");
    } else {
      sb.append(this.fileType);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("page:");
    sb.append(this.page);
    first = false;
    if (!first) sb.append(", ");
    sb.append("rows:");
    sb.append(this.rows);
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

  private static class BizSpotFileStandardSchemeFactory implements SchemeFactory {
    public BizSpotFileStandardScheme getScheme() {
      return new BizSpotFileStandardScheme();
    }
  }

  private static class BizSpotFileStandardScheme extends StandardScheme<BizSpotFile> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BizSpotFile struct) throws org.apache.thrift.TException {
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
          case 2: // SPOT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.spotId = iprot.readI32();
              struct.setSpotIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // FILE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.fileId = iprot.readI32();
              struct.setFileIdIsSet(true);
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
          case 5: // FILE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.fileName = iprot.readString();
              struct.setFileNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // FILE_URL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.fileUrl = iprot.readString();
              struct.setFileUrlIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // FILE_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.fileType = iprot.readString();
              struct.setFileTypeIsSet(true);
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
          case 9: // ROWS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.rows = iprot.readI32();
              struct.setRowsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BizSpotFile struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PID_FIELD_DESC);
      oprot.writeI32(struct.pid);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(SPOT_ID_FIELD_DESC);
      oprot.writeI32(struct.spotId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(FILE_ID_FIELD_DESC);
      oprot.writeI32(struct.fileId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(STATUS_FIELD_DESC);
      oprot.writeI32(struct.status);
      oprot.writeFieldEnd();
      if (struct.fileName != null) {
        oprot.writeFieldBegin(FILE_NAME_FIELD_DESC);
        oprot.writeString(struct.fileName);
        oprot.writeFieldEnd();
      }
      if (struct.fileUrl != null) {
        oprot.writeFieldBegin(FILE_URL_FIELD_DESC);
        oprot.writeString(struct.fileUrl);
        oprot.writeFieldEnd();
      }
      if (struct.fileType != null) {
        oprot.writeFieldBegin(FILE_TYPE_FIELD_DESC);
        oprot.writeString(struct.fileType);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(PAGE_FIELD_DESC);
      oprot.writeI32(struct.page);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ROWS_FIELD_DESC);
      oprot.writeI32(struct.rows);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BizSpotFileTupleSchemeFactory implements SchemeFactory {
    public BizSpotFileTupleScheme getScheme() {
      return new BizSpotFileTupleScheme();
    }
  }

  private static class BizSpotFileTupleScheme extends TupleScheme<BizSpotFile> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BizSpotFile struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPid()) {
        optionals.set(0);
      }
      if (struct.isSetSpotId()) {
        optionals.set(1);
      }
      if (struct.isSetFileId()) {
        optionals.set(2);
      }
      if (struct.isSetStatus()) {
        optionals.set(3);
      }
      if (struct.isSetFileName()) {
        optionals.set(4);
      }
      if (struct.isSetFileUrl()) {
        optionals.set(5);
      }
      if (struct.isSetFileType()) {
        optionals.set(6);
      }
      if (struct.isSetPage()) {
        optionals.set(7);
      }
      if (struct.isSetRows()) {
        optionals.set(8);
      }
      oprot.writeBitSet(optionals, 9);
      if (struct.isSetPid()) {
        oprot.writeI32(struct.pid);
      }
      if (struct.isSetSpotId()) {
        oprot.writeI32(struct.spotId);
      }
      if (struct.isSetFileId()) {
        oprot.writeI32(struct.fileId);
      }
      if (struct.isSetStatus()) {
        oprot.writeI32(struct.status);
      }
      if (struct.isSetFileName()) {
        oprot.writeString(struct.fileName);
      }
      if (struct.isSetFileUrl()) {
        oprot.writeString(struct.fileUrl);
      }
      if (struct.isSetFileType()) {
        oprot.writeString(struct.fileType);
      }
      if (struct.isSetPage()) {
        oprot.writeI32(struct.page);
      }
      if (struct.isSetRows()) {
        oprot.writeI32(struct.rows);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BizSpotFile struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(9);
      if (incoming.get(0)) {
        struct.pid = iprot.readI32();
        struct.setPidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.spotId = iprot.readI32();
        struct.setSpotIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.fileId = iprot.readI32();
        struct.setFileIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.status = iprot.readI32();
        struct.setStatusIsSet(true);
      }
      if (incoming.get(4)) {
        struct.fileName = iprot.readString();
        struct.setFileNameIsSet(true);
      }
      if (incoming.get(5)) {
        struct.fileUrl = iprot.readString();
        struct.setFileUrlIsSet(true);
      }
      if (incoming.get(6)) {
        struct.fileType = iprot.readString();
        struct.setFileTypeIsSet(true);
      }
      if (incoming.get(7)) {
        struct.page = iprot.readI32();
        struct.setPageIsSet(true);
      }
      if (incoming.get(8)) {
        struct.rows = iprot.readI32();
        struct.setRowsIsSet(true);
      }
    }
  }

}
