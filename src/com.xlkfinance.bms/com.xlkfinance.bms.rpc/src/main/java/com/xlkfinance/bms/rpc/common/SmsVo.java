/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xlkfinance.bms.rpc.common;

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
 * 短信通知传参Vo
 * @author Daijingyu
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2017-7-17")
public class SmsVo implements org.apache.thrift.TBase<SmsVo, SmsVo._Fields>, java.io.Serializable, Cloneable, Comparable<SmsVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SmsVo");

  private static final org.apache.thrift.protocol.TField MOBILE_STR_FIELD_DESC = new org.apache.thrift.protocol.TField("mobileStr", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField CONTENT_FIELD_DESC = new org.apache.thrift.protocol.TField("content", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField SIGN_FIELD_DESC = new org.apache.thrift.protocol.TField("sign", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SmsVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SmsVoTupleSchemeFactory());
  }

  public String mobileStr; // required
  public String content; // required
  public String sign; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MOBILE_STR((short)1, "mobileStr"),
    CONTENT((short)2, "content"),
    SIGN((short)3, "sign");

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
        case 1: // MOBILE_STR
          return MOBILE_STR;
        case 2: // CONTENT
          return CONTENT;
        case 3: // SIGN
          return SIGN;
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
    tmpMap.put(_Fields.MOBILE_STR, new org.apache.thrift.meta_data.FieldMetaData("mobileStr", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CONTENT, new org.apache.thrift.meta_data.FieldMetaData("content", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SIGN, new org.apache.thrift.meta_data.FieldMetaData("sign", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SmsVo.class, metaDataMap);
  }

  public SmsVo() {
  }

  public SmsVo(
    String mobileStr,
    String content,
    String sign)
  {
    this();
    this.mobileStr = mobileStr;
    this.content = content;
    this.sign = sign;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SmsVo(SmsVo other) {
    if (other.isSetMobileStr()) {
      this.mobileStr = other.mobileStr;
    }
    if (other.isSetContent()) {
      this.content = other.content;
    }
    if (other.isSetSign()) {
      this.sign = other.sign;
    }
  }

  public SmsVo deepCopy() {
    return new SmsVo(this);
  }

  @Override
  public void clear() {
    this.mobileStr = null;
    this.content = null;
    this.sign = null;
  }

  public String getMobileStr() {
    return this.mobileStr;
  }

  public SmsVo setMobileStr(String mobileStr) {
    this.mobileStr = mobileStr;
    return this;
  }

  public void unsetMobileStr() {
    this.mobileStr = null;
  }

  /** Returns true if field mobileStr is set (has been assigned a value) and false otherwise */
  public boolean isSetMobileStr() {
    return this.mobileStr != null;
  }

  public void setMobileStrIsSet(boolean value) {
    if (!value) {
      this.mobileStr = null;
    }
  }

  public String getContent() {
    return this.content;
  }

  public SmsVo setContent(String content) {
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

  public String getSign() {
    return this.sign;
  }

  public SmsVo setSign(String sign) {
    this.sign = sign;
    return this;
  }

  public void unsetSign() {
    this.sign = null;
  }

  /** Returns true if field sign is set (has been assigned a value) and false otherwise */
  public boolean isSetSign() {
    return this.sign != null;
  }

  public void setSignIsSet(boolean value) {
    if (!value) {
      this.sign = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case MOBILE_STR:
      if (value == null) {
        unsetMobileStr();
      } else {
        setMobileStr((String)value);
      }
      break;

    case CONTENT:
      if (value == null) {
        unsetContent();
      } else {
        setContent((String)value);
      }
      break;

    case SIGN:
      if (value == null) {
        unsetSign();
      } else {
        setSign((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case MOBILE_STR:
      return getMobileStr();

    case CONTENT:
      return getContent();

    case SIGN:
      return getSign();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case MOBILE_STR:
      return isSetMobileStr();
    case CONTENT:
      return isSetContent();
    case SIGN:
      return isSetSign();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SmsVo)
      return this.equals((SmsVo)that);
    return false;
  }

  public boolean equals(SmsVo that) {
    if (that == null)
      return false;

    boolean this_present_mobileStr = true && this.isSetMobileStr();
    boolean that_present_mobileStr = true && that.isSetMobileStr();
    if (this_present_mobileStr || that_present_mobileStr) {
      if (!(this_present_mobileStr && that_present_mobileStr))
        return false;
      if (!this.mobileStr.equals(that.mobileStr))
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

    boolean this_present_sign = true && this.isSetSign();
    boolean that_present_sign = true && that.isSetSign();
    if (this_present_sign || that_present_sign) {
      if (!(this_present_sign && that_present_sign))
        return false;
      if (!this.sign.equals(that.sign))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_mobileStr = true && (isSetMobileStr());
    list.add(present_mobileStr);
    if (present_mobileStr)
      list.add(mobileStr);

    boolean present_content = true && (isSetContent());
    list.add(present_content);
    if (present_content)
      list.add(content);

    boolean present_sign = true && (isSetSign());
    list.add(present_sign);
    if (present_sign)
      list.add(sign);

    return list.hashCode();
  }

  @Override
  public int compareTo(SmsVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetMobileStr()).compareTo(other.isSetMobileStr());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMobileStr()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.mobileStr, other.mobileStr);
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
    lastComparison = Boolean.valueOf(isSetSign()).compareTo(other.isSetSign());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSign()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sign, other.sign);
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
    StringBuilder sb = new StringBuilder("SmsVo(");
    boolean first = true;

    sb.append("mobileStr:");
    if (this.mobileStr == null) {
      sb.append("null");
    } else {
      sb.append(this.mobileStr);
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
    sb.append("sign:");
    if (this.sign == null) {
      sb.append("null");
    } else {
      sb.append(this.sign);
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

  private static class SmsVoStandardSchemeFactory implements SchemeFactory {
    public SmsVoStandardScheme getScheme() {
      return new SmsVoStandardScheme();
    }
  }

  private static class SmsVoStandardScheme extends StandardScheme<SmsVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SmsVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MOBILE_STR
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.mobileStr = iprot.readString();
              struct.setMobileStrIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CONTENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.content = iprot.readString();
              struct.setContentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SIGN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.sign = iprot.readString();
              struct.setSignIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, SmsVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.mobileStr != null) {
        oprot.writeFieldBegin(MOBILE_STR_FIELD_DESC);
        oprot.writeString(struct.mobileStr);
        oprot.writeFieldEnd();
      }
      if (struct.content != null) {
        oprot.writeFieldBegin(CONTENT_FIELD_DESC);
        oprot.writeString(struct.content);
        oprot.writeFieldEnd();
      }
      if (struct.sign != null) {
        oprot.writeFieldBegin(SIGN_FIELD_DESC);
        oprot.writeString(struct.sign);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SmsVoTupleSchemeFactory implements SchemeFactory {
    public SmsVoTupleScheme getScheme() {
      return new SmsVoTupleScheme();
    }
  }

  private static class SmsVoTupleScheme extends TupleScheme<SmsVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SmsVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetMobileStr()) {
        optionals.set(0);
      }
      if (struct.isSetContent()) {
        optionals.set(1);
      }
      if (struct.isSetSign()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetMobileStr()) {
        oprot.writeString(struct.mobileStr);
      }
      if (struct.isSetContent()) {
        oprot.writeString(struct.content);
      }
      if (struct.isSetSign()) {
        oprot.writeString(struct.sign);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SmsVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.mobileStr = iprot.readString();
        struct.setMobileStrIsSet(true);
      }
      if (incoming.get(1)) {
        struct.content = iprot.readString();
        struct.setContentIsSet(true);
      }
      if (incoming.get(2)) {
        struct.sign = iprot.readString();
        struct.setSignIsSet(true);
      }
    }
  }

}

