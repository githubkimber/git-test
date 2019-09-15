package org.apache.jsp.pages.common;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class top_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\r');
      out.write('\n');

    String userCnName = (String) request.getSession().getAttribute("_user_cnname");

      out.write("\r\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
      out.write("<html>\r\n");
      out.write("\r\n");
      out.write("<div class=\"top\">\r\n");
      out.write("\t<div class=\"top-logo\">传智播客反爬虫</div>\r\n");
      out.write("\t<div class=\"top-user\">\r\n");
      out.write("\t\t<div class=\"top-user1\">\r\n");
      out.write("\t\t\t<div class=\"user1\">\r\n");
      out.write("\t\t\t\t<img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/themes/default/images/user-portrait.png\" />\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"user1-accounts\" title=\"\">");
      out.print(userCnName);
      out.write("</div>\r\n");
      out.write("\t\t\t<div class=\"user1-Triangle\"></div>\r\n");
      out.write("\t\t\t<div style=\"\" class=\"none\">\r\n");
      out.write("\t\t\t\t<div class=\"user1-Triangle1\"></div>\r\n");
      out.write("\t\t\t\t<a id=\"mofityPassword\" href=\"#\">修改密码</a> <a\r\n");
      out.write("\t\t\t\t\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/auth/logout\">退出</a>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<ul class=\"dwei\">\r\n");
      out.write("\t\t<li class=\"dwei-li\">\r\n");
      out.write("\t\t\t<a class=\"active\" id=\"indexFlage\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/pages/index.jsp\">首页</a>\r\n");
      out.write("\t\t</li>\r\n");
      out.write("\t\t<li class=\"dwei-li\"><a id=\"dataManage\">数据管理</a>\r\n");
      out.write("\t\t\t<ul class=\"dwei-yinc\">\r\n");
      out.write("\t\t\t\t<li><a id=\"dataCollect\" href=\"#\">数据采集</a></li>\r\n");
      out.write("\t\t\t\t<li><a id=\"dataHandle\" href=\"#\">数据处理</a></li>\r\n");
      out.write("\t\t\t</ul></li>\r\n");
      out.write("\t\t<li class=\"dwei-li\"><a id=\"dataVisualize\" href=\"#\">数据可视化</a></li>\r\n");
      out.write("\t\t<li class=\"dwei-li\"><a id=\"processManage\" href=\"#\">流程管理</a></li>\r\n");
      out.write("\t\t<li class=\"dwei-li\"><a id=\"sysManage\">系统管理</a>\r\n");
      out.write("\t\t\t<ul class=\"dwei-yinc\">\r\n");
      out.write("\t\t\t\t<li><a id=\"userManage\">用户管理</a></li>\r\n");
      out.write("\t\t\t\t<li><a id=\"roleManage\">角色管理</a></li>\r\n");
      out.write("\t\t\t\t<li><a id=\"persManage\">权限管理</a></li>\r\n");
      out.write("\t\t\t</ul></li>\r\n");
      out.write("\t</ul>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "../model/model.jsp", out, false);
      out.write("\r\n");
      out.write("\r\n");
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
