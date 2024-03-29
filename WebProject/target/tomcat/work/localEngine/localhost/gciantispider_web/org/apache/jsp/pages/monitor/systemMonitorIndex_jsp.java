package org.apache.jsp.pages.monitor;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class systemMonitorIndex_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("  \t\t<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/monitor/systemMonitorIndex.js\"></script>\r\n");
      out.write("  </head>\r\n");
      out.write("  \r\n");
      out.write("  <body>\r\n");
      out.write("\t\t\t<div class=\"content-top\">\r\n");
      out.write("\t\t\t\t<div id=\"contentLeft1\" class=\"content-top-left\">\r\n");
      out.write("\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"content-top-right\">\r\n");
      out.write("\t\t\t\t\t<ul id=\"crawler-curday-info\"  class=\"content-top-right-top\">\r\n");
      out.write("\t\t\t\t\t\t<!-- <li style=\"background: #d0e4f4;text-align: center;line-height: 40px;margin-bottom: 7px;\">2017年9月28日</li>\r\n");
      out.write("\t\t\t\t\t\t<li>当日流量：<span class=\"lanse\">2033万</span></li>\r\n");
      out.write("\t\t\t\t\t\t<li style=\"margin-bottom: 7px;\">当日识别爬虫：<span class=\"lanse\">3452</span></li>\r\n");
      out.write("\t\t\t\t\t\t<li style=\"margin-bottom: 8px;\">积累识别爬虫：<span class=\"lanse\">222123345</span></li> -->\r\n");
      out.write("\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t\t<div class=\"content-Title\" style=\"text-align: center;padding-right: 15px;margin-top: 20px; background: #fff;margin-left: 20px;padding-bottom: 5px;border: 1px solid #ddd;\">\r\n");
      out.write("\t\t\t\t\t    <span class=\"content-Title-span\">系统功能运行情况</span>\r\n");
      out.write("\t\t\t\t\t    <div style=\"line-height: 40px;\">\r\n");
      out.write("\t\t\t\t\t    \t<span class=\"lyanse\">运行正常</span><span class=\"hyanse\">运行异常</span>\r\n");
      out.write("\t\t\t\t\t    </div>\r\n");
      out.write("\t\t\t\t\t    <table id=\"content-monitor\">\r\n");
      out.write("\t\t\t\t\t    \t <tr id=\"firstModel\">\r\n");
      out.write("\t\t\t\t\t    \t\t<td style=\"background:#58aea5;\" id=\"dataHandl\">数据处理模块</td>\r\n");
      out.write("\t\t\t\t\t    \t\t<td style=\"background:gray;\" id=\"process\">流程管理模块</td>\r\n");
      out.write("\t\t\t\t\t    \t</tr>\r\n");
      out.write("\t\t\t\t\t    \t<tr id=\"secondModel\">\r\n");
      out.write("\t\t\t\t\t    \t\t<td style=\"background:gray;\" id=\"visualization\">数据可视化模块</td>\r\n");
      out.write("\t\t\t\t\t    \t\t<td style=\"background:gray;\" id=\"strategy\">策略管理模块</td>\r\n");
      out.write("\t\t\t\t\t    \t</tr>\r\n");
      out.write("\t\t\t\t\t    \t<tr>\r\n");
      out.write("\t\t\t\t\t    \t\t<td style=\"background:gray;\" id=\"machineStudy\">机器学习模块</td>\r\n");
      out.write("\t\t\t\t\t    \t\t<td style=\"background:gray;\" id=\"rule\">规则管理模块</td>\r\n");
      out.write("\t\t\t\t\t    \t</tr>\r\n");
      out.write("\t\t\t\t\t    </table>\r\n");
      out.write("\t\t\t\t    </div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div id = \"flow-transmit\" class=\"content-bottom\">\r\n");
      out.write("\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("  </body>\r\n");
      out.write("</html>");
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
