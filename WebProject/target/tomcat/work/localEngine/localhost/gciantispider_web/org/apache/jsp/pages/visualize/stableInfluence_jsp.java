package org.apache.jsp.pages.visualize;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class stableInfluence_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
      out.write("<html>\r\n");
      out.write("  <head>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/visualize/stableInfluence.js\"></script>\r\n");
      out.write("  </head>\r\n");
      out.write("  \r\n");
      out.write("  <body>\r\n");
      out.write("\t\t<!-- <div  class=\"SubjectBox dataVisualization-one\">\r\n");
      out.write("\t\t\t<div class=\"date\">\r\n");
      out.write("\t\t\t\t<p>\r\n");
      out.write("\t\t\t\t\t<input id=\"chooseTimeFrom\" value=\"\" type=\"text\" placeholder=\"选择时间\" class=\"chooseTime\" onFocus=\"WdatePicker({onpicked:function(){stableInfluence.selectTimeFrom();}, readOnly:true,maxDate:'#F{$dp.$D(\\'chooseTimeTo\\') ||\\'%y-%M-%d\\'}' })\">\r\n");
      out.write("\t\t\t\t\t<span></span>\r\n");
      out.write("\t\t\t\t\t<samp>至</samp>\r\n");
      out.write("\t\t\t\t\t<input id=\"chooseTimeTo\" value=\"\" type=\"text\" placeholder=\"选择时间\" class=\"chooseTime\" onFocus=\"WdatePicker({onpicked:function(){stableInfluence.selectTimeTo();}, readOnly:true,minDate:'#F{$dp.$D(\\'chooseTimeFrom\\')}',maxDate:'%y-%M-%d'})\">\r\n");
      out.write("\t\t\t\t\t<span></span>\r\n");
      out.write("\t\t\t\t</p>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"box-centent\">\r\n");
      out.write("\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t\t<div id = \"nh-flow-info\"></div>\r\n");
      out.write("\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t\t<div id = \"crawler-flow-rate\"></div>\r\n");
      out.write("\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t</ul>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div> -->\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<div class=\"SubjectBox dataVisualization-one\">\r\n");
      out.write("\t\t\t<div class=\"date\">\r\n");
      out.write("\t\t\t\t<p>\r\n");
      out.write("\t\t\t\t\t<samp style=\"width: 70px;text-align: left;\">选择时间</samp>\r\n");
      out.write("\t\t\t\t\t<input id=\"chooseTimeFrom\" value=\"\" type=\"text\" placeholder=\"选择时间\" class=\"chooseTime\" onFocus=\"WdatePicker({onpicked:function(){stableInfluence.selectTimeFrom();}, readOnly:true,maxDate:'#F{$dp.$D(\\'chooseTimeTo\\') ||\\'%y-%M-%d\\'}' })\">\r\n");
      out.write("\t\t\t\t\t<span></span>\r\n");
      out.write("\t\t\t\t\t<samp>至</samp>\r\n");
      out.write("\t\t\t\t\t<input id=\"chooseTimeTo\" value=\"\" type=\"text\" placeholder=\"选择时间\" class=\"chooseTime\" onFocus=\"WdatePicker({onpicked:function(){stableInfluence.selectTimeTo();}, readOnly:true,minDate:'#F{$dp.$D(\\'chooseTimeFrom\\')}',maxDate:'%y-%M-%d'})\">\r\n");
      out.write("\t\t\t\t\t<span></span>\r\n");
      out.write("\t\t\t\t</p>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"box-centent\">\r\n");
      out.write("\t\t\t\t<div id = \"nh-flow-info\" class=\"oneBox-one\"></div>\r\n");
      out.write("\t\t\t\t<div id = \"crawler-flow-rate\" class=\"oneBox-two\"></div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("  </body>\r\n");
      out.write("</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
