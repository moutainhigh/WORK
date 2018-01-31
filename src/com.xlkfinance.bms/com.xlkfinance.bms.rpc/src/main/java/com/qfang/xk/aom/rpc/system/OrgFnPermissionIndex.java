/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.qfang.xk.aom.rpc.system;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2017-3-8")
public class OrgFnPermissionIndex implements org.apache.thrift.TBase<OrgFnPermissionIndex, OrgFnPermissionIndex._Fields>, java.io.Serializable, Cloneable, Comparable<OrgFnPermissionIndex> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("OrgFnPermissionIndex");

  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField ROWS_FIELD_DESC = new org.apache.thrift.protocol.TField("rows", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField MENU_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("menuId", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField MENU_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("menuName", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField ORG_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("orgId", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("userId", org.apache.thrift.protocol.TType.I32, (short)7);
  private static final org.apache.thrift.protocol.TField USER_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("userType", org.apache.thrift.protocol.TType.I32, (short)8);
  private static final org.apache.thrift.protocol.TField PARENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("parentId", org.apache.thrift.protocol.TType.I32, (short)9);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new OrgFnPermissionIndexStandardSchemeFactory());
    schemes.put(TupleScheme.class, new OrgFnPermissionIndexTupleSchemeFactory());
  }

  public int page; // required
  public int rows; // required
  public int menuId; // required
  public String menuName; // required
  public int status; // required
  public int orgId; // required
  public int userId; // required
  public int userType; // required
  public int parentId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAGE((short)1, "page"),
    ROWS((short)2, "rows"),
    MENU_ID((short)3, "menuId"),
    MENU_NAME((short)4, "menuName"),
    STATUS((short)5, "status"),
    ORG_ID((short)6, "orgId"),
    USER_ID((short)7, "userId"),
    USER_TYPE((short)8, "userType"),
    PARENT_ID((short)9, "parentId");

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
        case 1: // PAGE
          return PAGE;
        case 2: // ROWS
          return ROWS;
        case 3: // MENU_ID
          return MENU_ID;
        case 4: // MENU_NAME
          return MENU_NAME;
        case 5: // STATUS
          return STATUS;
        case 6: // ORG_ID
          return ORG_ID;
        case 7: // USER_ID
          return USER_ID;
        case 8: // USER_TYPE
          return USER_TYPE;
        case 9: // PARENT_ID
          return PARENT_ID;
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
  private static final int __PAGE_ISSET_ID = 0;
  private static final int __ROWS_ISSET_ID = 1;
  private static final int __MENUID_ISSET_ID = 2;
  private static final int __STATUS_ISSET_ID = 3;
  private static final int __ORGID_ISSET_ID = 4;
  private static final int __USERID_ISSET_ID = 5;
  private static final int __USERTYPE_ISSET_ID = 6;
  private static final int __PARENTID_ISSET_ID = 7;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PAGE, new org.apache.thrift.meta_data.FieldMetaData("page", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ROWS, new org.apache.thrift.meta_data.FieldMetaData("rows", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MENU_ID, new org.apache.thrift.meta_data.FieldMetaData("menuId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MENU_NAME, new org.apache.thrift.meta_data.FieldMetaData("menuName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ORG_ID, new org.apache.thrift.meta_data.FieldMetaData("orgId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("userId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.USER_TYPE, new org.apache.thrift.meta_data.FieldMetaData("userType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PARENT_ID, new org.apache.thrift.meta_data.FieldMetaData("parentId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(OrgFnPermissionIndex.class, metaDataMap);
  }

  public OrgFnPermissionIndex() {
  }

  public OrgFnPermissionIndex(
    int page,
    int rows,
    int menuId,
    String menuName,
    int status,
    int orgId,
    int userId,
    int userType,
    int parentId)
  {
    this();
    this.page = page;
    setPageIsSet(true);
    this.rows = rows;
    setRowsIsSet(true);
    this.menuId = menuId;
    setMenuIdIsSet(true);
    this.menuName = menuName;
    this.status = status;
    setStatusIsSet(true);
    this.orgId = orgId;
    setOrgIdIsSet(true);
    this.userId = userId;
    setUserIdIsSet(true);
    this.userType = userType;
    setUserTypeIsSet(true);
    this.parentId = parentId;
    setParentIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public OrgFnPermissionIndex(OrgFnPermissionIndex other) {
    __isset_bitfield = other.__isset_bitfield;
    this.page = other.page;
    this.rows = other.rows;
    this.menuId = other.menuId;
    if (other.isSetMenuName()) {
      this.menuName = other.menuName;
    }
    this.status = other.status;
    this.orgId = other.orgId;
    this.userId = other.userId;
    this.userType = other.userType;
    this.parentId = other.parentId;
  }

  public OrgFnPermissionIndex deepCopy() {
    return new OrgFnPermissionIndex(this);
  }

  @Override
  public void clear() {
    setPageIsSet(false);
    this.page = 0;
    setRowsIsSet(false);
    this.rows = 0;
    setMenuIdIsSet(false);
    this.menuId = 0;
    this.menuName = null;
    setStatusIsSet(false);
    this.status = 0;
    setOrgIdIsSet(false);
    this.orgId = 0;
    setUserIdIsSet(false);
    this.userId = 0;
    setUserTypeIsSet(false);
    this.userType = 0;
    setParentIdIsSet(false);
    this.parentId = 0;
  }

  public int getPage() {
    return this.page;
  }

  public OrgFnPermissionIndex setPage(int page) {
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

  public OrgFnPermissionIndex setRows(int rows) {
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

  public int getMenuId() {
    return this.menuId;
  }

  public OrgFnPermissionIndex setMenuId(int menuId) {
    this.menuId = menuId;
    setMenuIdIsSet(true);
    return this;
  }

  public void unsetMenuId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MENUID_ISSET_ID);
  }

  /** Returns true if field menuId is set (has been assigned a value) and false otherwise */
  public boolean isSetMenuId() {
    return EncodingUtils.testBit(__isset_bitfield, __MENUID_ISSET_ID);
  }

  public void setMenuIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MENUID_ISSET_ID, value);
  }

  public String getMenuName() {
    return this.menuName;
  }

  public OrgFnPermissionIndex setMenuName(String menuName) {
    this.menuName = menuName;
    return this;
  }

  public void unsetMenuName() {
    this.menuName = null;
  }

  /** Returns true if field menuName is set (has been assigned a value) and false otherwise */
  public boolean isSetMenuName() {
    return this.menuName != null;
  }

  public void setMenuNameIsSet(boolean value) {
    if (!value) {
      this.menuName = null;
    }
  }

  public int getStatus() {
    return this.status;
  }

  public OrgFnPermissionIndex setStatus(int status) {
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

  public int getOrgId() {
    return this.orgId;
  }

  public OrgFnPermissionIndex setOrgId(int orgId) {
    this.orgId = orgId;
    setOrgIdIsSet(true);
    return this;
  }

  public void unsetOrgId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ORGID_ISSET_ID);
  }

  /** Returns true if field orgId is set (has been assigned a value) and false otherwise */
  public boolean isSetOrgId() {
    return EncodingUtils.testBit(__isset_bitfield, __ORGID_ISSET_ID);
  }

  public void setOrgIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ORGID_ISSET_ID, value);
  }

  public int getUserId() {
    return this.userId;
  }

  public OrgFnPermissionIndex setUserId(int userId) {
    this.userId = userId;
    setUserIdIsSet(true);
    return this;
  }

  public void unsetUserId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __USERID_ISSET_ID);
  }

  /** Returns true if field userId is set (has been assigned a value) and false otherwise */
  public boolean isSetUserId() {
    return EncodingUtils.testBit(__isset_bitfield, __USERID_ISSET_ID);
  }

  public void setUserIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __USERID_ISSET_ID, value);
  }

  public int getUserType() {
    return this.userType;
  }

  public OrgFnPermissionIndex setUserType(int userType) {
    this.userType = userType;
    setUserTypeIsSet(true);
    return this;
  }

  public void unsetUserType() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __USERTYPE_ISSET_ID);
  }

  /** Returns true if field userType is set (has been assigned a value) and false otherwise */
  public boolean isSetUserType() {
    return EncodingUtils.testBit(__isset_bitfield, __USERTYPE_ISSET_ID);
  }

  public void setUserTypeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __USERTYPE_ISSET_ID, value);
  }

  public int getParentId() {
    return this.parentId;
  }

  public OrgFnPermissionIndex setParentId(int parentId) {
    this.parentId = parentId;
    setParentIdIsSet(true);
    return this;
  }

  public void unsetParentId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PARENTID_ISSET_ID);
  }

  /** Returns true if field parentId is set (has been assigned a value) and false otherwise */
  public boolean isSetParentId() {
    return EncodingUtils.testBit(__isset_bitfield, __PARENTID_ISSET_ID);
  }

  public void setParentIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PARENTID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
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

    case MENU_ID:
      if (value == null) {
        unsetMenuId();
      } else {
        setMenuId((Integer)value);
      }
      break;

    case MENU_NAME:
      if (value == null) {
        unsetMenuName();
      } else {
        setMenuName((String)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((Integer)value);
      }
      break;

    case ORG_ID:
      if (value == null) {
        unsetOrgId();
      } else {
        setOrgId((Integer)value);
      }
      break;

    case USER_ID:
      if (value == null) {
        unsetUserId();
      } else {
        setUserId((Integer)value);
      }
      break;

    case USER_TYPE:
      if (value == null) {
        unsetUserType();
      } else {
        setUserType((Integer)value);
      }
      break;

    case PARENT_ID:
      if (value == null) {
        unsetParentId();
      } else {
        setParentId((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE:
      return Integer.valueOf(getPage());

    case ROWS:
      return Integer.valueOf(getRows());

    case MENU_ID:
      return Integer.valueOf(getMenuId());

    case MENU_NAME:
      return getMenuName();

    case STATUS:
      return Integer.valueOf(getStatus());

    case ORG_ID:
      return Integer.valueOf(getOrgId());

    case USER_ID:
      return Integer.valueOf(getUserId());

    case USER_TYPE:
      return Integer.valueOf(getUserType());

    case PARENT_ID:
      return Integer.valueOf(getParentId());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PAGE:
      return isSetPage();
    case ROWS:
      return isSetRows();
    case MENU_ID:
      return isSetMenuId();
    case MENU_NAME:
      return isSetMenuName();
    case STATUS:
      return isSetStatus();
    case ORG_ID:
      return isSetOrgId();
    case USER_ID:
      return isSetUserId();
    case USER_TYPE:
      return isSetUserType();
    case PARENT_ID:
      return isSetParentId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof OrgFnPermissionIndex)
      return this.equals((OrgFnPermissionIndex)that);
    return false;
  }

  public boolean equals(OrgFnPermissionIndex that) {
    if (that == null)
      return false;

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

    boolean this_present_menuId = true;
    boolean that_present_menuId = true;
    if (this_present_menuId || that_present_menuId) {
      if (!(this_present_menuId && that_present_menuId))
        return false;
      if (this.menuId != that.menuId)
        return false;
    }

    boolean this_present_menuName = true && this.isSetMenuName();
    boolean that_present_menuName = true && that.isSetMenuName();
    if (this_present_menuName || that_present_menuName) {
      if (!(this_present_menuName && that_present_menuName))
        return false;
      if (!this.menuName.equals(that.menuName))
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

    boolean this_present_orgId = true;
    boolean that_present_orgId = true;
    if (this_present_orgId || that_present_orgId) {
      if (!(this_present_orgId && that_present_orgId))
        return false;
      if (this.orgId != that.orgId)
        return false;
    }

    boolean this_present_userId = true;
    boolean that_present_userId = true;
    if (this_present_userId || that_present_userId) {
      if (!(this_present_userId && that_present_userId))
        return false;
      if (this.userId != that.userId)
        return false;
    }

    boolean this_present_userType = true;
    boolean that_present_userType = true;
    if (this_present_userType || that_present_userType) {
      if (!(this_present_userType && that_present_userType))
        return false;
      if (this.userType != that.userType)
        return false;
    }

    boolean this_present_parentId = true;
    boolean that_present_parentId = true;
    if (this_present_parentId || that_present_parentId) {
      if (!(this_present_parentId && that_present_parentId))
        return false;
      if (this.parentId != that.parentId)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_page = true;
    list.add(present_page);
    if (present_page)
      list.add(page);

    boolean present_rows = true;
    list.add(present_rows);
    if (present_rows)
      list.add(rows);

    boolean present_menuId = true;
    list.add(present_menuId);
    if (present_menuId)
      list.add(menuId);

    boolean present_menuName = true && (isSetMenuName());
    list.add(present_menuName);
    if (present_menuName)
      list.add(menuName);

    boolean present_status = true;
    list.add(present_status);
    if (present_status)
      list.add(status);

    boolean present_orgId = true;
    list.add(present_orgId);
    if (present_orgId)
      list.add(orgId);

    boolean present_userId = true;
    list.add(present_userId);
    if (present_userId)
      list.add(userId);

    boolean present_userType = true;
    list.add(present_userType);
    if (present_userType)
      list.add(userType);

    boolean present_parentId = true;
    list.add(present_parentId);
    if (present_parentId)
      list.add(parentId);

    return list.hashCode();
  }

  @Override
  public int compareTo(OrgFnPermissionIndex other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

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
    lastComparison = Boolean.valueOf(isSetMenuId()).compareTo(other.isSetMenuId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMenuId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.menuId, other.menuId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMenuName()).compareTo(other.isSetMenuName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMenuName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.menuName, other.menuName);
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
    lastComparison = Boolean.valueOf(isSetOrgId()).compareTo(other.isSetOrgId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrgId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orgId, other.orgId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUserId()).compareTo(other.isSetUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userId, other.userId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUserType()).compareTo(other.isSetUserType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userType, other.userType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetParentId()).compareTo(other.isSetParentId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetParentId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.parentId, other.parentId);
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
    StringBuilder sb = new StringBuilder("OrgFnPermissionIndex(");
    boolean first = true;

    sb.append("page:");
    sb.append(this.page);
    first = false;
    if (!first) sb.append(", ");
    sb.append("rows:");
    sb.append(this.rows);
    first = false;
    if (!first) sb.append(", ");
    sb.append("menuId:");
    sb.append(this.menuId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("menuName:");
    if (this.menuName == null) {
      sb.append("null");
    } else {
      sb.append(this.menuName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("status:");
    sb.append(this.status);
    first = false;
    if (!first) sb.append(", ");
    sb.append("orgId:");
    sb.append(this.orgId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("userId:");
    sb.append(this.userId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("userType:");
    sb.append(this.userType);
    first = false;
    if (!first) sb.append(", ");
    sb.append("parentId:");
    sb.append(this.parentId);
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

  private static class OrgFnPermissionIndexStandardSchemeFactory implements SchemeFactory {
    public OrgFnPermissionIndexStandardScheme getScheme() {
      return new OrgFnPermissionIndexStandardScheme();
    }
  }

  private static class OrgFnPermissionIndexStandardScheme extends StandardScheme<OrgFnPermissionIndex> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, OrgFnPermissionIndex struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.page = iprot.readI32();
              struct.setPageIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ROWS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.rows = iprot.readI32();
              struct.setRowsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MENU_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.menuId = iprot.readI32();
              struct.setMenuIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // MENU_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.menuName = iprot.readString();
              struct.setMenuNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.status = iprot.readI32();
              struct.setStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // ORG_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.orgId = iprot.readI32();
              struct.setOrgIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.userId = iprot.readI32();
              struct.setUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // USER_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.userType = iprot.readI32();
              struct.setUserTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 9: // PARENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.parentId = iprot.readI32();
              struct.setParentIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, OrgFnPermissionIndex struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PAGE_FIELD_DESC);
      oprot.writeI32(struct.page);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ROWS_FIELD_DESC);
      oprot.writeI32(struct.rows);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(MENU_ID_FIELD_DESC);
      oprot.writeI32(struct.menuId);
      oprot.writeFieldEnd();
      if (struct.menuName != null) {
        oprot.writeFieldBegin(MENU_NAME_FIELD_DESC);
        oprot.writeString(struct.menuName);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(STATUS_FIELD_DESC);
      oprot.writeI32(struct.status);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ORG_ID_FIELD_DESC);
      oprot.writeI32(struct.orgId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(USER_ID_FIELD_DESC);
      oprot.writeI32(struct.userId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(USER_TYPE_FIELD_DESC);
      oprot.writeI32(struct.userType);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PARENT_ID_FIELD_DESC);
      oprot.writeI32(struct.parentId);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class OrgFnPermissionIndexTupleSchemeFactory implements SchemeFactory {
    public OrgFnPermissionIndexTupleScheme getScheme() {
      return new OrgFnPermissionIndexTupleScheme();
    }
  }

  private static class OrgFnPermissionIndexTupleScheme extends TupleScheme<OrgFnPermissionIndex> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, OrgFnPermissionIndex struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPage()) {
        optionals.set(0);
      }
      if (struct.isSetRows()) {
        optionals.set(1);
      }
      if (struct.isSetMenuId()) {
        optionals.set(2);
      }
      if (struct.isSetMenuName()) {
        optionals.set(3);
      }
      if (struct.isSetStatus()) {
        optionals.set(4);
      }
      if (struct.isSetOrgId()) {
        optionals.set(5);
      }
      if (struct.isSetUserId()) {
        optionals.set(6);
      }
      if (struct.isSetUserType()) {
        optionals.set(7);
      }
      if (struct.isSetParentId()) {
        optionals.set(8);
      }
      oprot.writeBitSet(optionals, 9);
      if (struct.isSetPage()) {
        oprot.writeI32(struct.page);
      }
      if (struct.isSetRows()) {
        oprot.writeI32(struct.rows);
      }
      if (struct.isSetMenuId()) {
        oprot.writeI32(struct.menuId);
      }
      if (struct.isSetMenuName()) {
        oprot.writeString(struct.menuName);
      }
      if (struct.isSetStatus()) {
        oprot.writeI32(struct.status);
      }
      if (struct.isSetOrgId()) {
        oprot.writeI32(struct.orgId);
      }
      if (struct.isSetUserId()) {
        oprot.writeI32(struct.userId);
      }
      if (struct.isSetUserType()) {
        oprot.writeI32(struct.userType);
      }
      if (struct.isSetParentId()) {
        oprot.writeI32(struct.parentId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, OrgFnPermissionIndex struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(9);
      if (incoming.get(0)) {
        struct.page = iprot.readI32();
        struct.setPageIsSet(true);
      }
      if (incoming.get(1)) {
        struct.rows = iprot.readI32();
        struct.setRowsIsSet(true);
      }
      if (incoming.get(2)) {
        struct.menuId = iprot.readI32();
        struct.setMenuIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.menuName = iprot.readString();
        struct.setMenuNameIsSet(true);
      }
      if (incoming.get(4)) {
        struct.status = iprot.readI32();
        struct.setStatusIsSet(true);
      }
      if (incoming.get(5)) {
        struct.orgId = iprot.readI32();
        struct.setOrgIdIsSet(true);
      }
      if (incoming.get(6)) {
        struct.userId = iprot.readI32();
        struct.setUserIdIsSet(true);
      }
      if (incoming.get(7)) {
        struct.userType = iprot.readI32();
        struct.setUserTypeIsSet(true);
      }
      if (incoming.get(8)) {
        struct.parentId = iprot.readI32();
        struct.setParentIdIsSet(true);
      }
    }
  }

}
