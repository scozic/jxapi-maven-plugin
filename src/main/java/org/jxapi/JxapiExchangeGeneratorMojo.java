package org.jxapi;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.jxapi.generator.java.exchange.ExchangeGeneratorMain;

/**
 * Goal that calls {@link ExchangeGeneratorMain} to generate exchange wrappers
 * in the current project, looking for descriptor files in 'src/main/resources/'
 * folder.
 * 
 * @see ExchangeGeneratorMain#generateExchangeWrappersInCurrentProject(String,
 *      String, String, String, String)
 */
@Mojo(name = "generate-exchanges", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class JxapiExchangeGeneratorMojo extends AbstractMojo {

    @Parameter(property = "baseProjectDir", required = false, defaultValue = "${project.basedir}")
    private String baseProjectDir;
	
    @Parameter(property = "baseJavaDocUrl", required = false)
    private String baseJavaDocUrl;
    
    @Parameter(property = "baseSrcUrl", required = false)
    private String baseSrcUrl;
    
    @Parameter(property = "mainSourcesDirectory", required = false, defaultValue = "target/generated-sources/jxapi")
    private String mainSourcesDirectory;
    
    @Parameter(property = "testSourcesDirectory", required = false, defaultValue = "target/generated-test-sources/jxapi")
    private String testSourcesDirectory;
    
    /**
     * @parameter default-value="${project}"
     * @readonly 
     * @required
     */
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;
    
    @Override
    public void execute() throws MojoExecutionException {
      try {
        File projectBaseDir = new File(baseProjectDir);
        File mainSources = new File(projectBaseDir, mainSourcesDirectory);
        if (!mainSources.exists()) {
          mainSources.mkdirs();
        }
        project.addCompileSourceRoot(mainSources.getAbsolutePath());
        
        File testSources = new File(projectBaseDir, this.testSourcesDirectory);
        if (!testSources.exists()) {
          testSources.mkdirs();
        }
        project.addTestCompileSourceRoot(testSources.getAbsolutePath());
        getLog().info(String.format("Added main sources directory: %s and test sources directory:%s to compile resources.", 
            mainSources.getAbsolutePath(), 
            testSources.getAbsolutePath()));
        
        getLog().info(String.format(
            "Generating exchange wrappers in current project, baseProjectDir: %s, baseJavaDocUrl:%s, baseSrcUrl:%s, mainSourcesDirectory: %s, testSourcesDirectory: %s",
            baseProjectDir, 
            baseJavaDocUrl, 
            baseSrcUrl,
            mainSources.getAbsolutePath(),
            testSources.getAbsolutePath()));
        ExchangeGeneratorMain.generateExchangeWrappersInCurrentProject(
            baseProjectDir, 
            mainSourcesDirectory,
            testSourcesDirectory,
            baseJavaDocUrl, 
            baseSrcUrl);
      } catch (Exception e) {
        getLog().error("Error processing JXAPI descriptor files.", e);
        throw new MojoExecutionException("Error processing JXAPI descriptor files.", e);
      }
    }
}
