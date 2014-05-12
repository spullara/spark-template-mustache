package com.mustachejava.spark;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.eclipse.jetty.io.RuntimeIOException;
import spark.ModelAndView;
import spark.TemplateEngine;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Defaults to the 'templates' directory under the resource path.
 */
public class MustacheTemplateEngine extends TemplateEngine {

  private MustacheFactory mf;

  public MustacheTemplateEngine() {
    mf = new DefaultMustacheFactory("templates");
  }

  public MustacheTemplateEngine(String root) {
    mf = new DefaultMustacheFactory(root);
  }

  public MustacheTemplateEngine(MustacheFactory mf) {
    this.mf = mf;
  }

  @Override
  public String render(ModelAndView modelAndView) {
    String viewName = modelAndView.getViewName();
    Mustache mustache = mf.compile(viewName);
    StringWriter sw = new StringWriter();
    try {
      mustache.execute(sw, modelAndView.getModel()).close();
    } catch (IOException e) {
      throw new RuntimeIOException(e);
    }
    return sw.toString();
  }
}
