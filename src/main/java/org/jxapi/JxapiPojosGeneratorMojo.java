package org.jxapi;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.jxapi.generator.java.pojo.PojosGeneratorMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Goal that calls {@link PojosGeneratorMain} to generate POJOs in the current
 * project, looking for POJOs descriptor files in
 * 'src/main/resources/jxapi/pojos' folder.
 * 
 * @see PojosGeneratorMain#generatePojosInCurrentProject(String, String)
 */
@Mojo(name = "generate-pojos", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class JxapiPojosGeneratorMojo extends AbstractMojo {
  
  private static final Logger log = LoggerFactory.getLogger(JxapiPojosGeneratorMojo.class);

  @Parameter(property = "baseProjectDir", required = false)
  private String baseProjectDir;
  
  @Parameter(property = "mainSourcesDirectory", required = false, defaultValue = "target/generated-sources")
  private String mainSourcesDirectory;
  
  @Override
  public void execute() throws MojoExecutionException {
      try {
          log.info("Generating exchange POJOs in current project, baseProjectDir: {}", baseProjectDir);
          PojosGeneratorMain.generatePojosInCurrentProject(baseProjectDir, mainSourcesDirectory);
      } catch (Exception e) {
          log.error("Error processing JXAPI descriptor files.", e);
          throw new MojoExecutionException("Error processing JXAPI descriptor files.", e);
      }
  }

}
