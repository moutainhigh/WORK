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
/**
 * 收款信息
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2017-12-21")
public class ProjectCreLoanDTO implements org.apache.thrift.TBase<ProjectCreLoanDTO, ProjectCreLoanDTO._Fields>, java.io.Serializable, Cloneable, Comparable<ProjectCreLoanDTO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProjectCreLoanDTO");

  private static final org.apache.thrift.protocol.TField LOAN_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("loanId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PROJECTS_FIELD_DESC = new org.apache.thrift.protocol.TField("projects", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProjectCreLoanDTOStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProjectCreLoanDTOTupleSchemeFactory());
  }

  public int loanId; // required
  public List<com.xlkfinance.bms.rpc.beforeloan.Project> projects; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    LOAN_ID((short)1, "loanId"),
    PROJECTS((short)2, "projects");

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
        case 1: // LOAN_ID
          return LOAN_ID;
        case 2: // PROJECTS
          return PROJECTS;
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
  private static final int __LOANID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.LOAN_ID, new org.apache.thrift.meta_data.FieldMetaData("loanId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PROJECTS, new org.apache.thrift.meta_data.FieldMetaData("projects", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.xlkfinance.bms.rpc.beforeloan.Project.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProjectCreLoanDTO.class, metaDataMap);
  }

  public ProjectCreLoanDTO() {
  }

  public ProjectCreLoanDTO(
    int loanId,
    List<com.xlkfinance.bms.rpc.beforeloan.Project> projects)
  {
    this();
    this.loanId = loanId;
    setLoanIdIsSet(true);
    this.projects = projects;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProjectCreLoanDTO(ProjectCreLoanDTO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.loanId = other.loanId;
    if (other.isSetProjects()) {
      List<com.xlkfinance.bms.rpc.beforeloan.Project> __this__projects = new ArrayList<com.xlkfinance.bms.rpc.beforeloan.Project>(other.projects.size());
      for (com.xlkfinance.bms.rpc.beforeloan.Project other_element : other.projects) {
        __this__projects.add(new com.xlkfinance.bms.rpc.beforeloan.Project(other_element));
      }
      this.projects = __this__projects;
    }
  }

  public ProjectCreLoanDTO deepCopy() {
    return new ProjectCreLoanDTO(this);
  }

  @Override
  public void clear() {
    setLoanIdIsSet(false);
    this.loanId = 0;
    this.projects = null;
  }

  public int getLoanId() {
    return this.loanId;
  }

  public ProjectCreLoanDTO setLoanId(int loanId) {
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

  public int getProjectsSize() {
    return (this.projects == null) ? 0 : this.projects.size();
  }

  public java.util.Iterator<com.xlkfinance.bms.rpc.beforeloan.Project> getProjectsIterator() {
    return (this.projects == null) ? null : this.projects.iterator();
  }

  public void addToProjects(com.xlkfinance.bms.rpc.beforeloan.Project elem) {
    if (this.projects == null) {
      this.projects = new ArrayList<com.xlkfinance.bms.rpc.beforeloan.Project>();
    }
    this.projects.add(elem);
  }

  public List<com.xlkfinance.bms.rpc.beforeloan.Project> getProjects() {
    return this.projects;
  }

  public ProjectCreLoanDTO setProjects(List<com.xlkfinance.bms.rpc.beforeloan.Project> projects) {
    this.projects = projects;
    return this;
  }

  public void unsetProjects() {
    this.projects = null;
  }

  /** Returns true if field projects is set (has been assigned a value) and false otherwise */
  public boolean isSetProjects() {
    return this.projects != null;
  }

  public void setProjectsIsSet(boolean value) {
    if (!value) {
      this.projects = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case LOAN_ID:
      if (value == null) {
        unsetLoanId();
      } else {
        setLoanId((Integer)value);
      }
      break;

    case PROJECTS:
      if (value == null) {
        unsetProjects();
      } else {
        setProjects((List<com.xlkfinance.bms.rpc.beforeloan.Project>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case LOAN_ID:
      return Integer.valueOf(getLoanId());

    case PROJECTS:
      return getProjects();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case LOAN_ID:
      return isSetLoanId();
    case PROJECTS:
      return isSetProjects();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProjectCreLoanDTO)
      return this.equals((ProjectCreLoanDTO)that);
    return false;
  }

  public boolean equals(ProjectCreLoanDTO that) {
    if (that == null)
      return false;

    boolean this_present_loanId = true;
    boolean that_present_loanId = true;
    if (this_present_loanId || that_present_loanId) {
      if (!(this_present_loanId && that_present_loanId))
        return false;
      if (this.loanId != that.loanId)
        return false;
    }

    boolean this_present_projects = true && this.isSetProjects();
    boolean that_present_projects = true && that.isSetProjects();
    if (this_present_projects || that_present_projects) {
      if (!(this_present_projects && that_present_projects))
        return false;
      if (!this.projects.equals(that.projects))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_loanId = true;
    list.add(present_loanId);
    if (present_loanId)
      list.add(loanId);

    boolean present_projects = true && (isSetProjects());
    list.add(present_projects);
    if (present_projects)
      list.add(projects);

    return list.hashCode();
  }

  @Override
  public int compareTo(ProjectCreLoanDTO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

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
    lastComparison = Boolean.valueOf(isSetProjects()).compareTo(other.isSetProjects());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProjects()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.projects, other.projects);
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
    StringBuilder sb = new StringBuilder("ProjectCreLoanDTO(");
    boolean first = true;

    sb.append("loanId:");
    sb.append(this.loanId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("projects:");
    if (this.projects == null) {
      sb.append("null");
    } else {
      sb.append(this.projects);
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

  private static class ProjectCreLoanDTOStandardSchemeFactory implements SchemeFactory {
    public ProjectCreLoanDTOStandardScheme getScheme() {
      return new ProjectCreLoanDTOStandardScheme();
    }
  }

  private static class ProjectCreLoanDTOStandardScheme extends StandardScheme<ProjectCreLoanDTO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProjectCreLoanDTO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // LOAN_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.loanId = iprot.readI32();
              struct.setLoanIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PROJECTS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.projects = new ArrayList<com.xlkfinance.bms.rpc.beforeloan.Project>(_list0.size);
                com.xlkfinance.bms.rpc.beforeloan.Project _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new com.xlkfinance.bms.rpc.beforeloan.Project();
                  _elem1.read(iprot);
                  struct.projects.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setProjectsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProjectCreLoanDTO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(LOAN_ID_FIELD_DESC);
      oprot.writeI32(struct.loanId);
      oprot.writeFieldEnd();
      if (struct.projects != null) {
        oprot.writeFieldBegin(PROJECTS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.projects.size()));
          for (com.xlkfinance.bms.rpc.beforeloan.Project _iter3 : struct.projects)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProjectCreLoanDTOTupleSchemeFactory implements SchemeFactory {
    public ProjectCreLoanDTOTupleScheme getScheme() {
      return new ProjectCreLoanDTOTupleScheme();
    }
  }

  private static class ProjectCreLoanDTOTupleScheme extends TupleScheme<ProjectCreLoanDTO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProjectCreLoanDTO struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetLoanId()) {
        optionals.set(0);
      }
      if (struct.isSetProjects()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetLoanId()) {
        oprot.writeI32(struct.loanId);
      }
      if (struct.isSetProjects()) {
        {
          oprot.writeI32(struct.projects.size());
          for (com.xlkfinance.bms.rpc.beforeloan.Project _iter4 : struct.projects)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProjectCreLoanDTO struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.loanId = iprot.readI32();
        struct.setLoanIdIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.projects = new ArrayList<com.xlkfinance.bms.rpc.beforeloan.Project>(_list5.size);
          com.xlkfinance.bms.rpc.beforeloan.Project _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = new com.xlkfinance.bms.rpc.beforeloan.Project();
            _elem6.read(iprot);
            struct.projects.add(_elem6);
          }
        }
        struct.setProjectsIsSet(true);
      }
    }
  }

}

