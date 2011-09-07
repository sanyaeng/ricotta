package com.wadpam.ricotta.command;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.maven.plugin.logging.Log;

public class Downloader {

    private static Log LOG = null;

    protected static void warn(String s) {
        if (null != LOG) {
            LOG.warn(s);
        }
    }

    protected static void info(String s) {
        if (null != LOG) {
            LOG.info(s);
        }
    }

    public static String buildUrl(String baseUrl, String projectName, String version, String languageCode, String templateName,
            String artifactName) {
        StringBuffer url = new StringBuffer(baseUrl);
        url.append("proj/");
        url.append(projectName);

        url.append("/branch/");
        if (null != version && !"HEAD".equals(version)) {
            url.append(version);
        }
        else {
            url.append("trunk");
        }

        url.append("/lang/");
        url.append(languageCode);
        url.append("/templ/");
        url.append(templateName);
        if (null != artifactName) {
            url.append("/subset/");
            url.append(artifactName);
        }
        url.append('/');
        final String returnValue = url.toString();
        info("Download URL " + returnValue);
        return returnValue;
    }

    public static void download(String baseUrl, String projectName, String version, String languageCode, String templateName,
            String artifactName, PrintStream fos) throws ClientProtocolException, IOException {
        final String url = buildUrl(baseUrl, projectName, version, languageCode, templateName, artifactName);
        HttpGet method = new HttpGet(url);

        try {
            // TODO: add authentication

            HttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(method);

            StatusLine status = response.getStatusLine();

            // warming request?
            if (status.getStatusCode() == 500) {
                warn("Retrying as HTTP " + status.getStatusCode() + " " + status.getReasonPhrase());
                method.abort();
                // retry once!
                info("Download URL " + url);
                method = new HttpGet(url);
                // TODO: add authentication
                response = client.execute(method);
                status = response.getStatusLine();
            }

            if (status.getStatusCode() == 200) {
                InputStream is = response.getEntity().getContent();

                byte buf[] = new byte[1024];
                int count;
                while (0 < (count = is.read(buf))) {
                    fos.write(buf, 0, count);
                }

                is.close();
                info("Downloaded " + projectName + '/' + languageCode + '/' + templateName);
            }
            else {
                throw new IOException("HTTP " + status.getStatusCode() + " " + status.getReasonPhrase());
            }
        }
        finally {
            method.abort();
        }
    }

    public static void download(String baseUrl, String projectName, String version, String languageCode, String templateName,
            String artifactName, File destination, String encode) throws ClientProtocolException, IOException {
        File folder = destination.getParentFile();
        if (false == folder.exists()) {
            folder.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(destination);
        PrintStream ps = new PrintStream(fos, false, encode);
        download(baseUrl, projectName, version, languageCode, templateName, artifactName, ps);
        ps.close();
    }

    /**
     * @param args
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static void main(String[] args) throws ClientProtocolException, IOException {
        String baseUrl = "http://ricotta-ost.appspot.com/";
        switch (args.length) {
            case 3:
                download(baseUrl, args[0], null, args[1], args[2], null, System.out);
                break;
            case 5:
            case 7:
                String version = null;
                String artifact = null;
                for(int i = 3; i < args.length; i += 2) {
                    if ("-subset".equals(args[i])) {
                        artifact = args[i + 1];
                    }
                    else if ("-branch".equals(args[i])) {
                        version = args[i + 1];
                    }
                    else if ("-baseUrl".equals(args[i])) {
                        baseUrl = args[i + 1];
                    }
                }
                download(baseUrl, args[0], version, args[1], args[2], artifact, System.out);
                break;
            default:
                throw new IOException(
                        "Usage: <projectName> <languageCode> <templateName> [-branch <branchName>] [-subset <subsetName>] [-baseUrl <baseUrl>]");
        }
    }

    public static void setLog(Log log) {
        Downloader.LOG = log;
    }

}
