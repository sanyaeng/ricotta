package com.wadpam.ricotta.dao;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.Key;
import com.wadpam.ricotta.domain.Proj;
import com.wadpam.ricotta.domain.ProjLang;
import com.wadpam.ricotta.domain.Tokn;
import com.wadpam.ricotta.domain.Trans;
import com.wadpam.ricotta.model.TransModel;

public interface UberDao {

    final static String VALUE_HEAD = "HEAD";

    Collection<TransModel> loadTrans(Key branchKey, Key subsetKey, ProjLang projLang, Key ctxtKey);

    List<String> updateTrans(Key projLangKey, Tokn token, Trans t, String name, String value, boolean delete);

    void notifyOwner(Proj proj, String branchName, String langCode, List<String> changes, String from);

    void importBody(HttpServletRequest request, Key branchKey, String langCode, String regexp, String body);

    // ---------------- import methods -----------------------

    Object createLang(String code, String name);

    Object createProj(String name, String owner);

    Object createBranch(Object proj, String name, String description);

    Object createProjLang(Object branchKey, String langCode, Object defaultLangKey, Object langKey);

    Object createCtxt(Object branch, String name, String description, String blobKeyString);

    Object createTokn(Object branch, Long id, String name, String description, Object ctxtKey);

    Object createSubset(Object branch, String name, String description);

    Object createTrans(Object projLangKey, Long toknId, String value);

    Object createSubsetTokn(Object subsetKey, Long toknId);

    Object createTempl(String name, String description, String body);

    Object createUser(Object proj, String email);

    // --------------------- delete methods ----------------------

    void deleteTokns(List<Key> keys);

    void deleteProj(Key projKey);

}
