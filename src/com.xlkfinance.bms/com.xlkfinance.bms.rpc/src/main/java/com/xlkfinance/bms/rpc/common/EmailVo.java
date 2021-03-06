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
 * 邮件通知属性Vo
 * @author Daijingyu
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2017-7-17")
public class EmailVo implements org.apache.thrift.TBase<EmailVo, EmailVo._Fields>, java.io.Serializable, Cloneable, Comparable<EmailVo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("EmailVo");

  private static final org.apache.thrift.protocol.TField SENDER_EMAIL_FIELD_DESC = new org.apache.thrift.protocol.TField("senderEmail", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField RECIPIENT_EMAILS_FIELD_DESC = new org.apache.thrift.protocol.TField("recipientEmails", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField SUBJECT_FIELD_DESC = new org.apache.thrift.protocol.TField("subject", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField HTML_TEXT_FIELD_DESC = new org.apache.thrift.protocol.TField("htmlText", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new EmailVoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new EmailVoTupleSchemeFactory());
  }

  public String senderEmail; // required
  public List<String> recipientEmails; // required
  public String subject; // required
  public String htmlText; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SENDER_EMAIL((short)1, "senderEmail"),
    RECIPIENT_EMAILS((short)2, "recipientEmails"),
    SUBJECT((short)3, "subject"),
    HTML_TEXT((short)4, "htmlText");

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
        case 1: // SENDER_EMAIL
          return SENDER_EMAIL;
        case 2: // RECIPIENT_EMAILS
          return RECIPIENT_EMAILS;
        case 3: // SUBJECT
          return SUBJECT;
        case 4: // HTML_TEXT
          return HTML_TEXT;
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
    tmpMap.put(_Fields.SENDER_EMAIL, new org.apache.thrift.meta_data.FieldMetaData("senderEmail", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.RECIPIENT_EMAILS, new org.apache.thrift.meta_data.FieldMetaData("recipientEmails", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.SUBJECT, new org.apache.thrift.meta_data.FieldMetaData("subject", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.HTML_TEXT, new org.apache.thrift.meta_data.FieldMetaData("htmlText", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(EmailVo.class, metaDataMap);
  }

  public EmailVo() {
  }

  public EmailVo(
    String senderEmail,
    List<String> recipientEmails,
    String subject,
    String htmlText)
  {
    this();
    this.senderEmail = senderEmail;
    this.recipientEmails = recipientEmails;
    this.subject = subject;
    this.htmlText = htmlText;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public EmailVo(EmailVo other) {
    if (other.isSetSenderEmail()) {
      this.senderEmail = other.senderEmail;
    }
    if (other.isSetRecipientEmails()) {
      List<String> __this__recipientEmails = new ArrayList<String>(other.recipientEmails);
      this.recipientEmails = __this__recipientEmails;
    }
    if (other.isSetSubject()) {
      this.subject = other.subject;
    }
    if (other.isSetHtmlText()) {
      this.htmlText = other.htmlText;
    }
  }

  public EmailVo deepCopy() {
    return new EmailVo(this);
  }

  @Override
  public void clear() {
    this.senderEmail = null;
    this.recipientEmails = null;
    this.subject = null;
    this.htmlText = null;
  }

  public String getSenderEmail() {
    return this.senderEmail;
  }

  public EmailVo setSenderEmail(String senderEmail) {
    this.senderEmail = senderEmail;
    return this;
  }

  public void unsetSenderEmail() {
    this.senderEmail = null;
  }

  /** Returns true if field senderEmail is set (has been assigned a value) and false otherwise */
  public boolean isSetSenderEmail() {
    return this.senderEmail != null;
  }

  public void setSenderEmailIsSet(boolean value) {
    if (!value) {
      this.senderEmail = null;
    }
  }

  public int getRecipientEmailsSize() {
    return (this.recipientEmails == null) ? 0 : this.recipientEmails.size();
  }

  public java.util.Iterator<String> getRecipientEmailsIterator() {
    return (this.recipientEmails == null) ? null : this.recipientEmails.iterator();
  }

  public void addToRecipientEmails(String elem) {
    if (this.recipientEmails == null) {
      this.recipientEmails = new ArrayList<String>();
    }
    this.recipientEmails.add(elem);
  }

  public List<String> getRecipientEmails() {
    return this.recipientEmails;
  }

  public EmailVo setRecipientEmails(List<String> recipientEmails) {
    this.recipientEmails = recipientEmails;
    return this;
  }

  public void unsetRecipientEmails() {
    this.recipientEmails = null;
  }

  /** Returns true if field recipientEmails is set (has been assigned a value) and false otherwise */
  public boolean isSetRecipientEmails() {
    return this.recipientEmails != null;
  }

  public void setRecipientEmailsIsSet(boolean value) {
    if (!value) {
      this.recipientEmails = null;
    }
  }

  public String getSubject() {
    return this.subject;
  }

  public EmailVo setSubject(String subject) {
    this.subject = subject;
    return this;
  }

  public void unsetSubject() {
    this.subject = null;
  }

  /** Returns true if field subject is set (has been assigned a value) and false otherwise */
  public boolean isSetSubject() {
    return this.subject != null;
  }

  public void setSubjectIsSet(boolean value) {
    if (!value) {
      this.subject = null;
    }
  }

  public String getHtmlText() {
    return this.htmlText;
  }

  public EmailVo setHtmlText(String htmlText) {
    this.htmlText = htmlText;
    return this;
  }

  public void unsetHtmlText() {
    this.htmlText = null;
  }

  /** Returns true if field htmlText is set (has been assigned a value) and false otherwise */
  public boolean isSetHtmlText() {
    return this.htmlText != null;
  }

  public void setHtmlTextIsSet(boolean value) {
    if (!value) {
      this.htmlText = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SENDER_EMAIL:
      if (value == null) {
        unsetSenderEmail();
      } else {
        setSenderEmail((String)value);
      }
      break;

    case RECIPIENT_EMAILS:
      if (value == null) {
        unsetRecipientEmails();
      } else {
        setRecipientEmails((List<String>)value);
      }
      break;

    case SUBJECT:
      if (value == null) {
        unsetSubject();
      } else {
        setSubject((String)value);
      }
      break;

    case HTML_TEXT:
      if (value == null) {
        unsetHtmlText();
      } else {
        setHtmlText((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SENDER_EMAIL:
      return getSenderEmail();

    case RECIPIENT_EMAILS:
      return getRecipientEmails();

    case SUBJECT:
      return getSubject();

    case HTML_TEXT:
      return getHtmlText();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SENDER_EMAIL:
      return isSetSenderEmail();
    case RECIPIENT_EMAILS:
      return isSetRecipientEmails();
    case SUBJECT:
      return isSetSubject();
    case HTML_TEXT:
      return isSetHtmlText();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof EmailVo)
      return this.equals((EmailVo)that);
    return false;
  }

  public boolean equals(EmailVo that) {
    if (that == null)
      return false;

    boolean this_present_senderEmail = true && this.isSetSenderEmail();
    boolean that_present_senderEmail = true && that.isSetSenderEmail();
    if (this_present_senderEmail || that_present_senderEmail) {
      if (!(this_present_senderEmail && that_present_senderEmail))
        return false;
      if (!this.senderEmail.equals(that.senderEmail))
        return false;
    }

    boolean this_present_recipientEmails = true && this.isSetRecipientEmails();
    boolean that_present_recipientEmails = true && that.isSetRecipientEmails();
    if (this_present_recipientEmails || that_present_recipientEmails) {
      if (!(this_present_recipientEmails && that_present_recipientEmails))
        return false;
      if (!this.recipientEmails.equals(that.recipientEmails))
        return false;
    }

    boolean this_present_subject = true && this.isSetSubject();
    boolean that_present_subject = true && that.isSetSubject();
    if (this_present_subject || that_present_subject) {
      if (!(this_present_subject && that_present_subject))
        return false;
      if (!this.subject.equals(that.subject))
        return false;
    }

    boolean this_present_htmlText = true && this.isSetHtmlText();
    boolean that_present_htmlText = true && that.isSetHtmlText();
    if (this_present_htmlText || that_present_htmlText) {
      if (!(this_present_htmlText && that_present_htmlText))
        return false;
      if (!this.htmlText.equals(that.htmlText))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_senderEmail = true && (isSetSenderEmail());
    list.add(present_senderEmail);
    if (present_senderEmail)
      list.add(senderEmail);

    boolean present_recipientEmails = true && (isSetRecipientEmails());
    list.add(present_recipientEmails);
    if (present_recipientEmails)
      list.add(recipientEmails);

    boolean present_subject = true && (isSetSubject());
    list.add(present_subject);
    if (present_subject)
      list.add(subject);

    boolean present_htmlText = true && (isSetHtmlText());
    list.add(present_htmlText);
    if (present_htmlText)
      list.add(htmlText);

    return list.hashCode();
  }

  @Override
  public int compareTo(EmailVo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetSenderEmail()).compareTo(other.isSetSenderEmail());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSenderEmail()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.senderEmail, other.senderEmail);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRecipientEmails()).compareTo(other.isSetRecipientEmails());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRecipientEmails()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.recipientEmails, other.recipientEmails);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSubject()).compareTo(other.isSetSubject());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubject()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.subject, other.subject);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetHtmlText()).compareTo(other.isSetHtmlText());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHtmlText()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.htmlText, other.htmlText);
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
    StringBuilder sb = new StringBuilder("EmailVo(");
    boolean first = true;

    sb.append("senderEmail:");
    if (this.senderEmail == null) {
      sb.append("null");
    } else {
      sb.append(this.senderEmail);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("recipientEmails:");
    if (this.recipientEmails == null) {
      sb.append("null");
    } else {
      sb.append(this.recipientEmails);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("subject:");
    if (this.subject == null) {
      sb.append("null");
    } else {
      sb.append(this.subject);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("htmlText:");
    if (this.htmlText == null) {
      sb.append("null");
    } else {
      sb.append(this.htmlText);
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

  private static class EmailVoStandardSchemeFactory implements SchemeFactory {
    public EmailVoStandardScheme getScheme() {
      return new EmailVoStandardScheme();
    }
  }

  private static class EmailVoStandardScheme extends StandardScheme<EmailVo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, EmailVo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SENDER_EMAIL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.senderEmail = iprot.readString();
              struct.setSenderEmailIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // RECIPIENT_EMAILS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.recipientEmails = new ArrayList<String>(_list0.size);
                String _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = iprot.readString();
                  struct.recipientEmails.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setRecipientEmailsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SUBJECT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.subject = iprot.readString();
              struct.setSubjectIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // HTML_TEXT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.htmlText = iprot.readString();
              struct.setHtmlTextIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, EmailVo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.senderEmail != null) {
        oprot.writeFieldBegin(SENDER_EMAIL_FIELD_DESC);
        oprot.writeString(struct.senderEmail);
        oprot.writeFieldEnd();
      }
      if (struct.recipientEmails != null) {
        oprot.writeFieldBegin(RECIPIENT_EMAILS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.recipientEmails.size()));
          for (String _iter3 : struct.recipientEmails)
          {
            oprot.writeString(_iter3);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.subject != null) {
        oprot.writeFieldBegin(SUBJECT_FIELD_DESC);
        oprot.writeString(struct.subject);
        oprot.writeFieldEnd();
      }
      if (struct.htmlText != null) {
        oprot.writeFieldBegin(HTML_TEXT_FIELD_DESC);
        oprot.writeString(struct.htmlText);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class EmailVoTupleSchemeFactory implements SchemeFactory {
    public EmailVoTupleScheme getScheme() {
      return new EmailVoTupleScheme();
    }
  }

  private static class EmailVoTupleScheme extends TupleScheme<EmailVo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, EmailVo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSenderEmail()) {
        optionals.set(0);
      }
      if (struct.isSetRecipientEmails()) {
        optionals.set(1);
      }
      if (struct.isSetSubject()) {
        optionals.set(2);
      }
      if (struct.isSetHtmlText()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetSenderEmail()) {
        oprot.writeString(struct.senderEmail);
      }
      if (struct.isSetRecipientEmails()) {
        {
          oprot.writeI32(struct.recipientEmails.size());
          for (String _iter4 : struct.recipientEmails)
          {
            oprot.writeString(_iter4);
          }
        }
      }
      if (struct.isSetSubject()) {
        oprot.writeString(struct.subject);
      }
      if (struct.isSetHtmlText()) {
        oprot.writeString(struct.htmlText);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, EmailVo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.senderEmail = iprot.readString();
        struct.setSenderEmailIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.recipientEmails = new ArrayList<String>(_list5.size);
          String _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = iprot.readString();
            struct.recipientEmails.add(_elem6);
          }
        }
        struct.setRecipientEmailsIsSet(true);
      }
      if (incoming.get(2)) {
        struct.subject = iprot.readString();
        struct.setSubjectIsSet(true);
      }
      if (incoming.get(3)) {
        struct.htmlText = iprot.readString();
        struct.setHtmlTextIsSet(true);
      }
    }
  }

}

