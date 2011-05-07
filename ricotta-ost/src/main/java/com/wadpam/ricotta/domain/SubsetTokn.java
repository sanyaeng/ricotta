package com.wadpam.ricotta.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import net.sf.mardao.api.Parent;
import net.sf.mardao.api.domain.AEDPrimaryKeyEntity;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
// @Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"token", "artifact"})})
public class SubsetTokn extends AEDPrimaryKeyEntity {
    private static final long serialVersionUID = -2998638040524758351L;
    @Parent(kind = "Subset")
    Key                       subset;

    @Id
    Long                      tokn;

    @Override
    public String toString() {
        return getClass().getSimpleName() + '{' + subset + ',' + tokn + '}';
    }

    @Override
    public Object getSimpleKey() {
        return tokn;
    }

    @Override
    public Object getParentKey() {
        return subset;
    }

    @Override
    public String getKeyString() {
        return (null != tokn && null != subset) ? tokn.toString() + '.' + KeyFactory.keyToString(subset) : null;
    }

    public Key getSubset() {
        return subset;
    }

    public void setSubset(Key subset) {
        this.subset = subset;
    }

    public Long getTokn() {
        return tokn;
    }

    public void setTokn(Long tokn) {
        this.tokn = tokn;
    }

}
