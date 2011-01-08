package com.wadpam.ricotta.plugin;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.wadpam.ricotta.command.Downloader;

/**
 * @goal download
 * @author os
 * 
 */
public class DownloadMojo extends AbstractMojo {

    /**
     * @parameter expression="${download.projectName}" default-value="${project.artifactId}"
     */
    private String       projectName;

    // /**
    // * @parameter expression="${download.languageCode}" default-value="en"
    // */
    // private String languageCode;
    //
    // /**
    // * @parameter expression="${download.templateName}" default-value="properties_java"
    // */
    // private String templateName;

    /**
     * @parameter expression="${download.destination}" default-value="${project.build.directory}/generated-resources"
     */
    private File         destination;

    /**
     * @parameter expression="${download.resourceItems}" required"
     */
    private ResourceItem resourceItems[];

    // /**
    // * @parameter expression="${download.filePath}" required
    // */
    // private String filePath;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Starting download of ricotta language files...");
        Downloader.setLog(getLog());

        for(ResourceItem item : resourceItems) {
            File output = new File(destination, item.getFilePath());
            try {
                Downloader.download(projectName, item.getLanguageCode(), item.getTemplateName(), output);
            }
            catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        getLog().info("Done downloading ricotta language files.");
    }

}
