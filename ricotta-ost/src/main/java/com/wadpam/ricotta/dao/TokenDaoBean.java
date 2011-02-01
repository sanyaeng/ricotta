package com.wadpam.ricotta.dao;

import java.util.HashMap;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.wadpam.ricotta.domain.Token;

/**
 * Implementation of Business Methods related to entity Token.
 */
public class TokenDaoBean extends GeneratedTokenDaoImpl implements TokenDao {

    @Override
    public List<Token> findByNameProject(String tokenName, Key projectKey) {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put(COLUMN_NAME_NAME, tokenName);
        args.put(COLUMN_NAME_PROJECT, projectKey);
        return findBy(args, null, false);
    }

    @Override
    public final List<Token> findByProject(Key projectKey, boolean ascending) {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put(COLUMN_NAME_PROJECT, projectKey);
        return findBy(args, COLUMN_NAME_NAME, ascending);
    }

    @Override
    public Token persist(Key projectKey, String name, String description) {
        List<Token> ts = findByNameProject(name, projectKey);
        if (ts.isEmpty()) {
            Token t = new Token();
            t.setProject(projectKey);
            t.setName(name);
            t.setDescription(description);
            persist(t);
            return t;
        }
        return ts.get(0);
    }

}