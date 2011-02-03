package com.wadpam.ricotta.web;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.appengine.api.datastore.Key;
import com.wadpam.ricotta.dao.LanguageDao;
import com.wadpam.ricotta.dao.TokenDao;
import com.wadpam.ricotta.dao.TranslationDao;
import com.wadpam.ricotta.dao.UberDao;
import com.wadpam.ricotta.domain.Language;
import com.wadpam.ricotta.domain.Project;
import com.wadpam.ricotta.domain.Token;
import com.wadpam.ricotta.domain.Translation;

/**
 * Created by Ola on Nov 12, 2010
 */
@Controller
@RequestMapping("/projects/{projectName}/languages/{languageCode}/import")
public class ImportController {
    static final Logger           LOGGER = LoggerFactory.getLogger(ImportController.class);

    private LanguageDao           languageDao;

    private TokenDao              tokenDao;

    private TranslationDao        translationDao;

    private UberDao               uberDao;

    private TranslationController translationController;

    @RequestMapping(value = "index.html", method = RequestMethod.GET)
    public String getAddUserForm(HttpServletRequest request, Model model, @PathVariable String projectName,
            @PathVariable String languageCode) {
        final Project project = (Project) request.getAttribute("project");
        model.addAttribute("project", project);

        final Language language = languageDao.findByCode(languageCode);
        model.addAttribute("language", language);

        return "import";
    }

    @RequestMapping(value = "index.html", method = RequestMethod.POST)
    public String postProjectLanguage(HttpServletRequest request, @PathVariable String projectName,
            @PathVariable String languageCode, @RequestParam String regexp, @RequestParam String custom, @RequestParam String body)
            throws IOException {
        final Project project = (Project) request.getAttribute("project");

        final Language language = languageDao.findByCode(languageCode);

        if ("custom".equals(regexp)) {
            regexp = custom;
        }

        importBody(project, language, regexp, body);

        return "redirect:/projects/" + projectName + "/languages/" + languageCode + "/translations/";
    }

    protected void importBody(Project project, Language language, String regexp, String body) {
        boolean invalidateCache = false;
        Key invalidateLanguageKey = language.getKey();
        LOGGER.info("matching {} on {}", body, regexp);
        final Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(body);
        while (matcher.find()) {
            final String tokenName = matcher.group(1);
            final String value = matcher.group(2);
            LOGGER.info("found {}={}", tokenName, value);

            try {

                // create new token?
                Token token = null;
                List<Token> tokens = tokenDao.findByNameProject(tokenName, project.getKey());
                if (tokens.isEmpty()) {
                    LOGGER.info("Creating token {}", tokenName);
                    token = new Token();
                    token.setName(tokenName);
                    token.setProject(project.getKey());
                    tokenDao.persist(token);
                    invalidateCache = true;

                    // if new token created, invalidate cache for all languages
                    invalidateLanguageKey = null;
                }
                else {
                    token = tokens.get(0);
                }

                Translation translation = null;
                for(Translation t : translationDao.findByToken(token.getKey())) {
                    if (t.getLanguage().equals(language.getKey())) {
                        translation = t;
                        break;
                    }
                }
                invalidateCache |= translationController.updateTranslation(project.getKey(), token.getKey(), language.getKey(),
                        translation, value, true);
                // LOGGER.debug("    for {} found {}", language.getCode(), translation);
                // if (null == translation) {
                // LOGGER.info("Creating translation {}={}", tokenName, value);
                // Translation t = new Translation();
                // t.setProject(project.getKey());
                // t.setLanguage(language.getKey());
                // t.setLocal(value);
                // t.setToken(token.getKey());
                // translationDao.persist(t);
                // invalidateCache = true;
                // }
                // else if (null == translation.getLocal() || false == value.equals(translation.getLocal())) {
                // LOGGER.info("Updating translation {}={}", tokenName, value);
                // translation.setLocal(value);
                // translationDao.update(translation);
                // invalidateCache = true;
                // }
            }
            catch (RuntimeException e) {
                LOGGER.error("Problems importing translation " + value + " for token " + tokenName, e);
            }
        }

        // invalidate cache for all artifacts (and for all languages)
        if (invalidateCache) {
            uberDao.invalidateCache(project.getKey(), invalidateLanguageKey, null);
        }
    }

    public void setLanguageDao(LanguageDao languageDao) {
        this.languageDao = languageDao;
    }

    public void setTokenDao(TokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    public void setTranslationDao(TranslationDao translationDao) {
        this.translationDao = translationDao;
    }

    public void setUberDao(UberDao uberDao) {
        this.uberDao = uberDao;
    }

    public void setTranslationController(TranslationController translationController) {
        this.translationController = translationController;
    }

}
