package com.wadpam.ricotta.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import net.sf.mardao.api.domain.AEDCreatedUpdatedEntity;

import com.google.appengine.api.datastore.Key;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"userId"})})
public class AppUser extends AEDCreatedUpdatedEntity<Key> {
    /**
     * 
     */
    private static final long serialVersionUID = 6008959981553934307L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Key                       key;

    String                    userId;

    String                    email;

    @Override
    public Key getSimpleKey() {
        return key;
    }

    @Override
    public Class<Key> getIdClass() {
        return Key.class;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '{' + email + ',' + userId + ',' + key + '}';
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
