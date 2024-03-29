package org.apache.jsp.pages;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html style=\"height: 100%;\">\r\n");
      out.write("<head>\r\n");
      out.write("\t<meta charset=\"utf-8\" />\r\n");
      out.write("\t<title>登录</title>\r\n");
      out.write("\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "common/common.jsp", out, false);
      out.write("\r\n");
      out.write("\t<style type=\"text/css\">\t\r\n");
      out.write("\t</style>\r\n");
      out.write("\t<script>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif (location != top.location){\r\n");
      out.write("\t\t\ttop.location = location;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tfunction emptyUserName(){\r\n");
      out.write("\t\t\t$(\"#userNameInput\").val(\"\");\r\n");
      out.write("\t\t};\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tfunction showOrHiddenPassword(){\r\n");
      out.write("\t\t\tif(\"password\" == $(\"#passwordInput\").attr(\"type\")){\r\n");
      out.write("\t\t\t\t$(\"#passwordInput\").attr(\"type\",\"text\");\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\t$(\"#passwordInput\").attr(\"type\",\"password\");\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t};\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tfunction onfocusl(e){\r\n");
      out.write("\t\t\t$('div').removeClass('on');\r\n");
      out.write("\t\t\t$(e).parent('div').addClass('on');\r\n");
      out.write("\t\t};\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tfunction login(){\r\n");
      out.write("\t\t\tvar userName = $(\"#userNameInput\").val();\r\n");
      out.write("\t\t\tdebugger\r\n");
      out.write("\t\t\tif(userName == undefined || userName == null || userName == \"\") {\r\n");
      out.write("\t\t\t\t$(\"#errMsg\").html(\"用户名不能为空\");\r\n");
      out.write("\t\t\t\treturn false;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tcookieUtil.setCookie(\"userName\", userName);\r\n");
      out.write("\t\t\tvar pwd = $(\"#passwordInput\").val();\r\n");
      out.write("\t\t\tif(pwd == undefined || pwd == null || pwd == \"\") {\r\n");
      out.write("\t\t\t\t$(\"#errMsg\").html(\"密码不能为空\");\r\n");
      out.write("\t\t\t\treturn false;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\t\tvar key = RSAUtils.getKeyPair('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${_RSA_EXPONENT}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("', '', '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${_RSA_MODULES}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("');\r\n");
      out.write("\t\t\t//对用户名，密码进行加密\r\n");
      out.write("\t\t\tuserName = RSAUtils.encryptedString(key, userName);\r\n");
      out.write("\t\t\tpwd = RSAUtils.encryptedString(key, pwd);\r\n");
      out.write("\t\t\t$(\"#userNameInput\").val(\"\");\r\n");
      out.write("\t\t\t$(\"#userName\").val(userName);\r\n");
      out.write("\t\t\t$(\"#password\").val(pwd);\r\n");
      out.write("\t\t\treturn true;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body class=\"login\" style=\"height: 100%;\">\r\n");
      out.write("\t<div class=\"login-bg\"></div>\r\n");
      out.write("\t<div class=\"login-box\">\r\n");
      out.write("\t\t<div class=\"login-box-img\">\r\n");
      out.write("\t\t\t<img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/themes/default/images/login_01.jpg\">\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<p class=\"login-box-text\">传智播客反爬虫</p>\r\n");
      out.write("\t\t<form method=\"post\" action=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/auth/logon\" onsubmit=\"return login()\">\r\n");
      out.write("\t\t\t<div class=\"login-name\">\r\n");
      out.write("\t\t\t\t<img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/themes/default/images/login_02.png\" >\r\n");
      out.write("\t\t\t\t<input type=\"text\" id=\"userNameInput\" name=\"userNameInput\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${username}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" onfocus=\"onfocusl(this)\" placeholder=\"请输入您的用户名\">\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" name=\"userName\" id=\"userName\"/>\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" name=\"source\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${source }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"/>\r\n");
      out.write("          \t\t<input type=\"hidden\" name=\"pwdKey\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${_RSA_KEY }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"/>\r\n");
      out.write("          \t\t<input type=\"hidden\" name=\"rsaExponent\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${_RSA_EXPONENT }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"/>\r\n");
      out.write("          \t\t<input type=\"hidden\" name=\"rsaModules\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${_RSA_MODULES }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"/>\r\n");
      out.write("\t\t\t\t<span onclick=\"emptyUserName()\"></span>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"login-password\">\r\n");
      out.write("\t\t\t\t<img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/themes/default/images/login_03.png\" >\r\n");
      out.write("\t\t\t\t<input type=\"password\" id=\"passwordInput\" onfocus=\"onfocusl(this)\" placeholder=\"请输入您的密码\">\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" name=\"password\" id=\"password\"/>\r\n");
      out.write("\t\t\t\t<span onclick=\"showOrHiddenPassword()\"></span>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"login-title\">\r\n");
      out.write("\t\t\t\t<p id=\"errMsg\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${LOGIN_ERROR_MES }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("</p>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"login-btn\">\r\n");
      out.write("\t\t\t\t<button class=\"btn\" type=\"submit\">立即登录</button>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
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
