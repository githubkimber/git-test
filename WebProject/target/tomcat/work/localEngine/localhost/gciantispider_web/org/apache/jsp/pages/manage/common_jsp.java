package org.apache.jsp.pages.manage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class common_jsp extends org.apache.jasper.runtime.HttpJspBase
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

String path = request.getContextPath();
request.setAttribute("kpcas",path);

      out.write("\r\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
      out.write("<html>\r\n");
      out.write("  <head>\r\n");
      out.write("    <meta http-equiv=\"pragma\" content=\"no-cache\">\r\n");
      out.write("\t<meta HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\r\n");
      out.write("\t\r\n");
      out.write("\t<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${kpcas}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/themes/sso/css/bootstrap.min.css\" />\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\"  href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${kpcas}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/themes/sso/css/zTreeStyle.css\" />\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${kpcas}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/themes/sso/css/index.css\"/>\r\n");
      out.write("    \r\n");
      out.write("    <script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${kpcas}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/manage/jquery-1.7.2.min.js\" ></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${kpcas}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/manage/bootstrap.min.js\"></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${kpcas}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/manage/jquery.ztree.core-3.5.js\" ></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${kpcas}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/manage/jquery.ztree.excheck-3.5.js\"></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${kpcas}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/manage/jquery.ztree.exedit-3.5.js\"></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${kpcas}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/manage/jquery.ztree.exhide-3.5.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${kpcas}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/manage/jquery.page.js\"></script> \r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${kpcas}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/manage/fastjson.min.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${kpcas}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/manage/index.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t\tvar kpcas='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${kpcas}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("';\r\n");
      out.write("\t\t//var account = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sessionScope.permission.user.name}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("';\r\n");
      out.write("\t\t$.ajaxSetup ({ \r\n");
      out.write("    \t\tcache: false //关闭AJAX相应的缓存 \r\n");
      out.write("\t\t});\r\n");
      out.write("\t</script>\r\n");
      out.write("\t\r\n");
      out.write("   </head> \r\n");
      out.write("   \r\n");
      out.write("   <div class=\"modal fade\" id=\"modal\" tabindex=\"-1\" role=\"dialog\"\r\n");
      out.write("\t\taria-labelledby=\"myModalLabel\" aria-hidden=\"true\"\r\n");
      out.write("\t\tdata-keyboard=\"false\" data-backdrop=\"static\">\r\n");
      out.write("\t\t<div class=\"modal-dialog\">\r\n");
      out.write("\t\t\t<div class=\"modal-content\">\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"modal fade\" id=\"modal-confirm\" tabindex=\"-1\" role=\"dialog\"\r\n");
      out.write("\t\taria-labelledby=\"myModalLabel\" aria-hidden=\"true\"\r\n");
      out.write("\t\tdata-keyboard=\"false\" data-backdrop=\"static\" style=\"top:200px;\">\r\n");
      out.write("\t\t<div class=\"modal-dialog\" style=\"width:351px;\">\r\n");
      out.write("\t\t\t<div class=\"modal-content\" style=\"height:203px;\">\r\n");
      out.write("\t\t\t\t<div class=\"modal-header\"\r\n");
      out.write("\t\t\t\t\tstyle=\"background:#0093d0; border-top-left-radius: 6px;border-top-right-radius: 6px;\">\r\n");
      out.write("\t\t\t\t\t<h4 class=\"modal-title\" style=\"font-size:12px;color:#ffffff;\">提示</h4>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"modal-body\" style=\"padding:10px 15px;\">\r\n");
      out.write("\t\t\t\t\t<div class=\"content-body\"></div>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"content-botton-list content-list\">\r\n");
      out.write("\t\t\t\t\t<button type=\"button\" class=\"btn\" data-dismiss=\"modal\">取消</button>\r\n");
      out.write("\t\t\t\t\t<button type=\"button\" id=\"confirm\" class=\"btn botton-active\">确定</button>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"modal fade\" id=\"modal-alert\" tabindex=\"-1\" role=\"dialog\"\r\n");
      out.write("\t\taria-labelledby=\"myModalLabel\" aria-hidden=\"true\"\r\n");
      out.write("\t\tdata-keyboard=\"false\" data-backdrop=\"false\" style=\"top:200px;\">\r\n");
      out.write("\t\t<div class=\"modal-dialog\" style=\"width:351px;\">\r\n");
      out.write("\t\t\t<div class=\"modal-content\" style=\"height:203px;\">\r\n");
      out.write("\t\t\t\t<div class=\"modal-header\"\r\n");
      out.write("\t\t\t\t\tstyle=\"background:#0093d0; border-top-left-radius: 6px;border-top-right-radius: 6px;\">\r\n");
      out.write("\t\t\t\t\t<h4 class=\"modal-title\" style=\"font-size:12px;color:#ffffff;\">提示</h4>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"modal-body\" style=\"padding:10px 15px;\">\r\n");
      out.write("\t\t\t\t\t<div class=\"content-body\"></div>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"content-botton-list content-list\">\r\n");
      out.write("\t\t\t\t\t<button type=\"button\" class=\"btn botton-active\" data-dismiss=\"modal\">确定</button>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div> \r\n");
      out.write("    ");
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
