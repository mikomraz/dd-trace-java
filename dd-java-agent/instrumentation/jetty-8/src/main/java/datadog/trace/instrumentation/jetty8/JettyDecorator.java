package datadog.trace.instrumentation.jetty8;

import datadog.trace.agent.decorator.HttpServerDecorator;
import io.opentracing.Span;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JettyDecorator extends HttpServerDecorator<HttpServletRequest, HttpServletResponse> {
  public static final JettyDecorator DECORATE = new JettyDecorator();

  @Override
  protected String[] instrumentationNames() {
    return new String[] {"jetty", "jetty-8"};
  }

  @Override
  protected String component() {
    return "jetty-handler";
  }

  @Override
  protected String method(final HttpServletRequest httpServletRequest) {
    return httpServletRequest.getMethod();
  }

  @Override
  protected String url(final HttpServletRequest httpServletRequest) {
    return httpServletRequest.getRequestURL().toString();
  }

  @Override
  protected String hostname(final HttpServletRequest httpServletRequest) {
    return httpServletRequest.getServerName();
  }

  @Override
  protected Integer port(final HttpServletRequest httpServletRequest) {
    return httpServletRequest.getServerPort();
  }

  @Override
  protected Integer status(final HttpServletResponse httpServletResponse) {
    return httpServletResponse.getStatus();
  }

  @Override
  public Span onRequest(final Span span, final HttpServletRequest request) {
    assert span != null;
    if (request != null) {
      span.setTag("servlet.context", request.getContextPath());
    }
    return super.onRequest(span, request);
  }
}
