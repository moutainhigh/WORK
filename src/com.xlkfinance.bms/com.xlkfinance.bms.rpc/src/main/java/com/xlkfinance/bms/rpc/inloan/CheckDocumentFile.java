/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xlkfinance.bms.rpc.inloan;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2018-1-11")
public class CheckDocumentFile implements org.apache.thrift.TBase<CheckDocumentFile, CheckDocumentFile._Fields>, java.io.Serializable, Cloneable, Comparable<CheckDocumentFile> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CheckDocumentFile");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField FILE_CATEGORY_FIELD_DESC = new org.apache.thrift.protocol.TField("fileCategory", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField PROJECT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("projectId", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField FILE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("fileId", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField FILE_FIELD_DESC = new org.apache.thrift.protocol.TField("file", org.apache.thrift.protocol.TType.STRUCT, (short)5);
  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField ROWS_FIELD_DESC = new org.apache.thrift.protocol.TField("rows", org.apache.thrift.protocol.TType.I32, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CheckDocumentFileStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CheckDocumentFileTupleSchemeFactory());
  }

  public int pid; // required
  public int fileCategory; // required
  public int projectId; // required
  public int fileId; // required
  public com.xlkfinance.bms.rpc.system.BizFile file; // required
  public int page; // required
  public int rows; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    FILE_CATEGORY((short)2, "fileCategory"),
    PROJECT_ID((short)3, "projectId"),
    FILE_ID((short)4, "fileId"),
    FILE((short)5, "file"),
    PAGE((short)6, "page"),
    ROWS((short)7, "rows");

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
        case 2: // FILE_CATEGORY
          return FILE_CATEGORY;
        case 3: // PROJECT_ID
          return PROJECT_ID;
        case 4: // FILE_ID
          return FILE_ID;
        case 5: // FILE
          return FILE;
        case 6: // PAGE
          return PAGE;
        case 7: // ROWS
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
  private static final int __FILECATEGORY_ISSET_ID = 1;
  private static final int __PROJECTID_ISSET_ID = 2;
  private static final int __FILEID_ISSET_ID = 3;
  private static final int __PAGE_ISSET_ID = 4;
  private static final int __ROWS_ISSET_ID = 5;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.FILE_CATEGORY, new org.apache.thrift.meta_data.FieldMetaData("fileCategory", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PROJECT_ID, new org.apache.thrift.meta_data.FieldMetaData("projectId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.FILE_ID, new org.apache.thrift.meta_data.FieldMetaData("fileId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.FILE, new org.apache.thrift.meta_data.FieldMetaData("file", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.xlkfinance.bms.rpc.system.BizFile.class)));
    tmpMap.put(_Fields.PAGE, new org.apache.thrift.meta_data.FieldMetaData("page", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ROWS, new org.apache.thrift.meta_data.FieldMetaData("rows", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CheckDocumentFile.class, metaDataMap);
  }

  public CheckDocumentFile() {
    this.page = 1;

    this.rows = 10;

  }

  public CheckDocumentFile(
    int pid,
    int fileCategory,
    int projectId,
    int fileId,
    com.xlkfinance.bms.rpc.system.BizFile file,
    int page,
    int rows)
  {
    this();
    this.pid = pid;
    setPidIsSet(true);
    this.fileCategory = fileCategory;
    setFileCategoryIsSet(true);
    this.projectId = projectId;
    setProjectIdIsSet(true);
    this.fileId = fileId;
    setFileIdIsSet(true);
    this.file = file;
    this.page = page;
    setPageIsSet(true);
    this.rows = rows;
    setRowsIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CheckDocumentFile(CheckDocumentFile other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pid = other.pid;
    this.fileCategory = other.fileCategory;
    this.projectId = other.projectId;
    this.fileId = other.fileId;
    if (other.isSetFile()) {
      this.file = new com.xlkfinance.bms.rpc.system.BizFile(other.file);
    }
    this.page = other.page;
    this.rows = other.rows;
  }

  public CheckDocumentFile deepCopy() {
    return new CheckDocumentFile(this);
  }

  @Override
  public void clear() {
    setPidIsSet(false);
    this.pid = 0;
    setFileCategoryIsSet(false);
    this.fileCategory = 0;
    setProjectIdIsSet(false);
    this.projectId = 0;
    setFileIdIsSet(false);
    this.fileId = 0;
    this.file = null;
    this.page = 1;

    this.rows = 10;

  }

  public int getPid() {
    return this.pid;
  }

  public CheckDocumentFile setPid(int pid) {
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

  public int getFileCategory() {
    return this.fileCategory;
  }

  public CheckDocumentFile setFileCategory(int fileCategory) {
    this.fileCategory = fileCategory;
    setFileCategoryIsSet(true);
    return this;
  }

  public void unsetFileCategory() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __FILECATEGORY_ISSET_ID);
  }

  /** Returns true if field fileCategory is set (has been assigned a value) and false otherwise */
  public boolean isSetFileCategory() {
    return EncodingUtils.testBit(__isset_bitfield, __FILECATEGORY_ISSET_ID);
  }

  public void setFileCategoryIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __FILECATEGORY_ISSET_ID, value);
  }

  public int getProjectId() {
    return this.projectId;
  }

  public CheckDocumentFile setProjectId(int projectId) {
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

  public int getFileId() {
    return this.fileId;
  }

  public CheckDocumentFile setFileId(int fileId) {
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

  public com.xlkfinance.bms.rpc.system.BizFile getFile() {
    return this.file;
  }

  public CheckDocumentFile setFile(com.xlkfinance.bms.rpc.system.BizFile file) {
    this.file = file;
    return this;
  }

  public void unsetFile() {
    this.file = null;
  }

  /** Returns true if field file is set (has been assigned a value) and false otherwise */
  public boolean isSetFile() {
    return this.file != null;
  }

  public void setFileIsSet(boolean value) {
    if (!value) {
      this.file = null;
    }
  }

  public int getPage() {
    return this.page;
  }

  public CheckDocumentFile setPage(int page) {
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

  public CheckDocumentFile setRows(int rows) {
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

    case FILE_CATEGORY:
      if (value == null) {
        unsetFileCategory();
      } else {
        setFileCategory((Integer)value);
      }
      break;

    case PROJECT_ID:
      if (value == null) {
        unsetProjectId();
      } else {
        setProjectId((Integer)value);
      }
      break;

    case FILE_ID:
      if (value == null) {
        unsetFileId();
      } else {
        setFileId((Integer)value);
      }
      break;

    case FILE:
      if (value == null) {
        unsetFile();
      } else {
        setFile((com.xlkfinance.bms.rpc.system.BizFile)value);
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

    case FILE_CATEGORY:
      return Integer.valueOf(getFileCategory());

    case PROJECT_ID:
      return Integer.valueOf(getProjectId());

    case FILE_ID:
      return Integer.valueOf(getFileId());

    case FILE:
      return getFile();

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
    case FILE_CATEGORY:
      return isSetFileCategory();
    case PROJECT_ID:
      return isSetProjectId();
    case FILE_ID:
      return isSetFileId();
    case FILE:
      return isSetFile();
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
    if (that instanceof CheckDocumentFile)
      return this.equals((CheckDocumentFile)that);
    return false;
  }

  public boolean equals(CheckDocumentFile that) {
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

    boolean this_present_fileCategory = true;
    boolean that_present_fileCategory = true;
    if (this_present_fileCategory || that_present_fileCategory) {
      if (!(this_present_fileCategory && that_present_fileCategory))
        return false;
      if (this.fileCategory != that.fileCategory)
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

    boolean this_present_fileId = true;
    boolean that_present_fileId = true;
    if (this_present_fileId || that_present_fileId) {
      if (!(this_present_fileId && that_present_fileId))
        return false;
      if (this.fileId != that.fileId)
        return false;
    }

    boolean this_present_file = true && this.isSetFile();
    boolean that_present_file = true && that.isSetFile();
    if (this_present_file || that_present_file) {
      if (!(this_present_file && that_present_file))
        return false;
      if (!this.file.equals(that.file))
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

    boolean present_fileCategory = true;
    list.add(present_fileCategory);
    if (present_fileCategory)
      list.add(fileCategory);

    boolean present_projectId = true;
    list.add(present_projectId);
    if (present_projectId)
      list.add(projectId);

    boolean present_fileId = true;
    list.add(present_fileId);
    if (present_fileId)
      list.add(fileId);

    boolean present_file = true && (isSetFile());
    list.add(present_file);
    if (present_file)
      list.add(file);

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
  public int compareTo(CheckDocumentFile other) {
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
    lastComparison = Boolean.valueOf(isSetFileCategory()).compareTo(other.isSetFileCategory());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileCategory()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileCategory, other.fileCategory);
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
    lastComparison = Boolean.valueOf(isSetFile()).compareTo(other.isSetFile());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFile()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.file, other.file);
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
    StringBuilder sb = new StringBuilder("CheckDocumentFile(");
    boolean first = true;

    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("fileCategory:");
    sb.append(this.fileCategory);
    first = false;
    if (!first) sb.append(", ");
    sb.append("projectId:");
    sb.append(this.projectId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("fileId:");
    sb.append(this.fileId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("file:");
    if (this.file == null) {
      sb.append("null");
    } else {
      sb.append(this.file);
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
    if (file != null) {
      file.validate();
    }
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

  private static class CheckDocumentFileStandardSchemeFactory implements SchemeFactory {
    public CheckDocumentFileStandardScheme getScheme() {
      return new CheckDocumentFileStandardScheme();
    }
  }

  private static class CheckDocumentFileStandardScheme extends StandardScheme<CheckDocumentFile> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CheckDocumentFile struct) throws org.apache.thrift.TException {
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
          case 2: // FILE_CATEGORY
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.fileCategory = iprot.readI32();
              struct.setFileCategoryIsSet(true);
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
          case 4: // FILE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.fileId = iprot.readI32();
              struct.setFileIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // FILE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.file = new com.xlkfinance.bms.rpc.system.BizFile();
              struct.file.read(iprot);
              struct.setFileIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // PAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.page = iprot.readI32();
              struct.setPageIsSet(true);
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
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, CheckDocumentFile struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PID_FIELD_DESC);
      oprot.writeI32(struct.pid);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(FILE_CATEGORY_FIELD_DESC);
      oprot.writeI32(struct.fileCategory);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PROJECT_ID_FIELD_DESC);
      oprot.writeI32(struct.projectId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(FILE_ID_FIELD_DESC);
      oprot.writeI32(struct.fileId);
      oprot.writeFieldEnd();
      if (struct.file != null) {
        oprot.writeFieldBegin(FILE_FIELD_DESC);
        struct.file.write(oprot);
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

  private static class CheckDocumentFileTupleSchemeFactory implements SchemeFactory {
    public CheckDocumentFileTupleScheme getScheme() {
      return new CheckDocumentFileTupleScheme();
    }
  }

  private static class CheckDocumentFileTupleScheme extends TupleScheme<CheckDocumentFile> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CheckDocumentFile struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPid()) {
        optionals.set(0);
      }
      if (struct.isSetFileCategory()) {
        optionals.set(1);
      }
      if (struct.isSetProjectId()) {
        optionals.set(2);
      }
      if (struct.isSetFileId()) {
        optionals.set(3);
      }
      if (struct.isSetFile()) {
        optionals.set(4);
      }
      if (struct.isSetPage()) {
        optionals.set(5);
      }
      if (struct.isSetRows()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetPid()) {
        oprot.writeI32(struct.pid);
      }
      if (struct.isSetFileCategory()) {
        oprot.writeI32(struct.fileCategory);
      }
      if (struct.isSetProjectId()) {
        oprot.writeI32(struct.projectId);
      }
      if (struct.isSetFileId()) {
        oprot.writeI32(struct.fileId);
      }
      if (struct.isSetFile()) {
        struct.file.write(oprot);
      }
      if (struct.isSetPage()) {
        oprot.writeI32(struct.page);
      }
      if (struct.isSetRows()) {
        oprot.writeI32(struct.rows);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CheckDocumentFile struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.pid = iprot.readI32();
        struct.setPidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.fileCategory = iprot.readI32();
        struct.setFileCategoryIsSet(true);
      }
      if (incoming.get(2)) {
        struct.projectId = iprot.readI32();
        struct.setProjectIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.fileId = iprot.readI32();
        struct.setFileIdIsSet(true);
      }
      if (incoming.get(4)) {
        struct.file = new com.xlkfinance.bms.rpc.system.BizFile();
        struct.file.read(iprot);
        struct.setFileIsSet(true);
      }
      if (incoming.get(5)) {
        struct.page = iprot.readI32();
        struct.setPageIsSet(true);
      }
      if (incoming.get(6)) {
        struct.rows = iprot.readI32();
        struct.setRowsIsSet(true);
      }
    }
  }

}

