/**
 */
package com.jaspersoft.studio.data.sql.impl;

import com.jaspersoft.studio.data.sql.DbObjectName;
import com.jaspersoft.studio.data.sql.SelectQuery;
import com.jaspersoft.studio.data.sql.SqlPackage;
import com.jaspersoft.studio.data.sql.WithColumns;
import com.jaspersoft.studio.data.sql.WithQuery;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>With Query</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.jaspersoft.studio.data.sql.impl.WithQueryImpl#getW <em>W</em>}</li>
 *   <li>{@link com.jaspersoft.studio.data.sql.impl.WithQueryImpl#getWname <em>Wname</em>}</li>
 *   <li>{@link com.jaspersoft.studio.data.sql.impl.WithQueryImpl#getWithCols <em>With Cols</em>}</li>
 *   <li>{@link com.jaspersoft.studio.data.sql.impl.WithQueryImpl#getQuery <em>Query</em>}</li>
 *   <li>{@link com.jaspersoft.studio.data.sql.impl.WithQueryImpl#getAdditionalWname <em>Additional Wname</em>}</li>
 *   <li>{@link com.jaspersoft.studio.data.sql.impl.WithQueryImpl#getAdditionalWithCols <em>Additional With Cols</em>}</li>
 *   <li>{@link com.jaspersoft.studio.data.sql.impl.WithQueryImpl#getAdditionalQueries <em>Additional Queries</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WithQueryImpl extends MinimalEObjectImpl.Container implements WithQuery
{
  /**
   * The default value of the '{@link #getW() <em>W</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getW()
   * @generated
   * @ordered
   */
  protected static final String W_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getW() <em>W</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getW()
   * @generated
   * @ordered
   */
  protected String w = W_EDEFAULT;

  /**
   * The cached value of the '{@link #getWname() <em>Wname</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWname()
   * @generated
   * @ordered
   */
  protected DbObjectName wname;

  /**
   * The cached value of the '{@link #getWithCols() <em>With Cols</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWithCols()
   * @generated
   * @ordered
   */
  protected WithColumns withCols;

  /**
   * The cached value of the '{@link #getQuery() <em>Query</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getQuery()
   * @generated
   * @ordered
   */
  protected SelectQuery query;

  /**
   * The cached value of the '{@link #getAdditionalWname() <em>Additional Wname</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAdditionalWname()
   * @generated
   * @ordered
   */
  protected EList<DbObjectName> additionalWname;

  /**
   * The cached value of the '{@link #getAdditionalWithCols() <em>Additional With Cols</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAdditionalWithCols()
   * @generated
   * @ordered
   */
  protected EList<WithColumns> additionalWithCols;

  /**
   * The cached value of the '{@link #getAdditionalQueries() <em>Additional Queries</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAdditionalQueries()
   * @generated
   * @ordered
   */
  protected EList<SelectQuery> additionalQueries;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected WithQueryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return SqlPackage.Literals.WITH_QUERY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getW()
  {
    return w;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setW(String newW)
  {
    String oldW = w;
    w = newW;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SqlPackage.WITH_QUERY__W, oldW, w));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public DbObjectName getWname()
  {
    return wname;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetWname(DbObjectName newWname, NotificationChain msgs)
  {
    DbObjectName oldWname = wname;
    wname = newWname;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SqlPackage.WITH_QUERY__WNAME, oldWname, newWname);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setWname(DbObjectName newWname)
  {
    if (newWname != wname)
    {
      NotificationChain msgs = null;
      if (wname != null)
        msgs = ((InternalEObject)wname).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SqlPackage.WITH_QUERY__WNAME, null, msgs);
      if (newWname != null)
        msgs = ((InternalEObject)newWname).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SqlPackage.WITH_QUERY__WNAME, null, msgs);
      msgs = basicSetWname(newWname, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SqlPackage.WITH_QUERY__WNAME, newWname, newWname));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public WithColumns getWithCols()
  {
    return withCols;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetWithCols(WithColumns newWithCols, NotificationChain msgs)
  {
    WithColumns oldWithCols = withCols;
    withCols = newWithCols;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SqlPackage.WITH_QUERY__WITH_COLS, oldWithCols, newWithCols);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setWithCols(WithColumns newWithCols)
  {
    if (newWithCols != withCols)
    {
      NotificationChain msgs = null;
      if (withCols != null)
        msgs = ((InternalEObject)withCols).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SqlPackage.WITH_QUERY__WITH_COLS, null, msgs);
      if (newWithCols != null)
        msgs = ((InternalEObject)newWithCols).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SqlPackage.WITH_QUERY__WITH_COLS, null, msgs);
      msgs = basicSetWithCols(newWithCols, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SqlPackage.WITH_QUERY__WITH_COLS, newWithCols, newWithCols));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public SelectQuery getQuery()
  {
    return query;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetQuery(SelectQuery newQuery, NotificationChain msgs)
  {
    SelectQuery oldQuery = query;
    query = newQuery;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SqlPackage.WITH_QUERY__QUERY, oldQuery, newQuery);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setQuery(SelectQuery newQuery)
  {
    if (newQuery != query)
    {
      NotificationChain msgs = null;
      if (query != null)
        msgs = ((InternalEObject)query).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SqlPackage.WITH_QUERY__QUERY, null, msgs);
      if (newQuery != null)
        msgs = ((InternalEObject)newQuery).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SqlPackage.WITH_QUERY__QUERY, null, msgs);
      msgs = basicSetQuery(newQuery, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SqlPackage.WITH_QUERY__QUERY, newQuery, newQuery));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<DbObjectName> getAdditionalWname()
  {
    if (additionalWname == null)
    {
      additionalWname = new EObjectContainmentEList<DbObjectName>(DbObjectName.class, this, SqlPackage.WITH_QUERY__ADDITIONAL_WNAME);
    }
    return additionalWname;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<WithColumns> getAdditionalWithCols()
  {
    if (additionalWithCols == null)
    {
      additionalWithCols = new EObjectContainmentEList<WithColumns>(WithColumns.class, this, SqlPackage.WITH_QUERY__ADDITIONAL_WITH_COLS);
    }
    return additionalWithCols;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<SelectQuery> getAdditionalQueries()
  {
    if (additionalQueries == null)
    {
      additionalQueries = new EObjectContainmentEList<SelectQuery>(SelectQuery.class, this, SqlPackage.WITH_QUERY__ADDITIONAL_QUERIES);
    }
    return additionalQueries;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case SqlPackage.WITH_QUERY__WNAME:
        return basicSetWname(null, msgs);
      case SqlPackage.WITH_QUERY__WITH_COLS:
        return basicSetWithCols(null, msgs);
      case SqlPackage.WITH_QUERY__QUERY:
        return basicSetQuery(null, msgs);
      case SqlPackage.WITH_QUERY__ADDITIONAL_WNAME:
        return ((InternalEList<?>)getAdditionalWname()).basicRemove(otherEnd, msgs);
      case SqlPackage.WITH_QUERY__ADDITIONAL_WITH_COLS:
        return ((InternalEList<?>)getAdditionalWithCols()).basicRemove(otherEnd, msgs);
      case SqlPackage.WITH_QUERY__ADDITIONAL_QUERIES:
        return ((InternalEList<?>)getAdditionalQueries()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case SqlPackage.WITH_QUERY__W:
        return getW();
      case SqlPackage.WITH_QUERY__WNAME:
        return getWname();
      case SqlPackage.WITH_QUERY__WITH_COLS:
        return getWithCols();
      case SqlPackage.WITH_QUERY__QUERY:
        return getQuery();
      case SqlPackage.WITH_QUERY__ADDITIONAL_WNAME:
        return getAdditionalWname();
      case SqlPackage.WITH_QUERY__ADDITIONAL_WITH_COLS:
        return getAdditionalWithCols();
      case SqlPackage.WITH_QUERY__ADDITIONAL_QUERIES:
        return getAdditionalQueries();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case SqlPackage.WITH_QUERY__W:
        setW((String)newValue);
        return;
      case SqlPackage.WITH_QUERY__WNAME:
        setWname((DbObjectName)newValue);
        return;
      case SqlPackage.WITH_QUERY__WITH_COLS:
        setWithCols((WithColumns)newValue);
        return;
      case SqlPackage.WITH_QUERY__QUERY:
        setQuery((SelectQuery)newValue);
        return;
      case SqlPackage.WITH_QUERY__ADDITIONAL_WNAME:
        getAdditionalWname().clear();
        getAdditionalWname().addAll((Collection<? extends DbObjectName>)newValue);
        return;
      case SqlPackage.WITH_QUERY__ADDITIONAL_WITH_COLS:
        getAdditionalWithCols().clear();
        getAdditionalWithCols().addAll((Collection<? extends WithColumns>)newValue);
        return;
      case SqlPackage.WITH_QUERY__ADDITIONAL_QUERIES:
        getAdditionalQueries().clear();
        getAdditionalQueries().addAll((Collection<? extends SelectQuery>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case SqlPackage.WITH_QUERY__W:
        setW(W_EDEFAULT);
        return;
      case SqlPackage.WITH_QUERY__WNAME:
        setWname((DbObjectName)null);
        return;
      case SqlPackage.WITH_QUERY__WITH_COLS:
        setWithCols((WithColumns)null);
        return;
      case SqlPackage.WITH_QUERY__QUERY:
        setQuery((SelectQuery)null);
        return;
      case SqlPackage.WITH_QUERY__ADDITIONAL_WNAME:
        getAdditionalWname().clear();
        return;
      case SqlPackage.WITH_QUERY__ADDITIONAL_WITH_COLS:
        getAdditionalWithCols().clear();
        return;
      case SqlPackage.WITH_QUERY__ADDITIONAL_QUERIES:
        getAdditionalQueries().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case SqlPackage.WITH_QUERY__W:
        return W_EDEFAULT == null ? w != null : !W_EDEFAULT.equals(w);
      case SqlPackage.WITH_QUERY__WNAME:
        return wname != null;
      case SqlPackage.WITH_QUERY__WITH_COLS:
        return withCols != null;
      case SqlPackage.WITH_QUERY__QUERY:
        return query != null;
      case SqlPackage.WITH_QUERY__ADDITIONAL_WNAME:
        return additionalWname != null && !additionalWname.isEmpty();
      case SqlPackage.WITH_QUERY__ADDITIONAL_WITH_COLS:
        return additionalWithCols != null && !additionalWithCols.isEmpty();
      case SqlPackage.WITH_QUERY__ADDITIONAL_QUERIES:
        return additionalQueries != null && !additionalQueries.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (w: ");
    result.append(w);
    result.append(')');
    return result.toString();
  }

} //WithQueryImpl
