package org.apache.jsp.pages.manage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class userManage_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=utf-8");
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

String path = request.getContextPath();
request.setAttribute("kpcas",path);

      out.write('\r');
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "common.jsp", out, false);
      out.write("\r\n");
      out.write("\r\n");
      out.write("    <script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${kpcas}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/manage/userList.js\"></script>\r\n");
      out.write("\r\n");
      out.write("  <body id=\"userManageBody\">\r\n");
      out.write("\t<div class=\"content-head\">\r\n");
      out.write("        <span>用户管理</span>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div class=\"content-body\">\r\n");
      out.write("        <div class=\"content-botton-list\">\r\n");
      out.write("            <button id=\"addUser\"  class=\"btn btn-sm botton-active\">新增用户</button>\r\n");
      out.write("            <button id=\"delUser\"  class=\"btn btn-sm\">删除用户</button>\r\n");
      out.write("            <button id=\"addRole\"  class=\"btn btn-sm\">角色分配</button>\r\n");
      out.write("            <button id=\"resetPwd\"  class=\"btn btn-sm\">重置密码</button>\r\n");
      out.write("            \r\n");
      out.write("            <div id=\"user-search\" class=\"user-search\">\r\n");
      out.write("                <input type=\"text\" placeholder=\"请输入用户姓名\"/>\r\n");
      out.write("                <button id=\"userSearchBtn\" class=\"btn search-btn btn-sm botton-active\">搜索</button>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div>\r\n");
      out.write("            <table id=\"user-list\" class=\"user-list\">\r\n");
      out.write("                <thead>\r\n");
      out.write("                <tr>\r\n");
      out.write("                    <th width=\"3%\"><input type=\"checkbox\" id=\"checkAll\"/><label for=\"checkAll\"></label></th>\r\n");
      out.write("                    <th width=\"10%\">账号</th>\r\n");
      out.write("                    <th width=\"15%\">姓名</th>\r\n");
      out.write("                    <th width=\"15%\">邮箱</th> \r\n");
      out.write("                    <th width=\"15%\">联系电话</th>\r\n");
      out.write("                    <th width=\"17%\">角色</th>\r\n");
      out.write("                    <th width=\"20%\">上次登录时间</th>\r\n");
      out.write("                </tr>\r\n");
      out.write("                </thead>\r\n");
      out.write("                <tbody>\r\n");
      out.write("                \r\n");
      out.write("                </tbody>\r\n");
      out.write("            </table>\r\n");
      out.write("        </div>\r\n");
      out.write("\t\t<div class=\"page-right\">\r\n");
      out.write("\t\t\t<div id=\"userPaging\" class=\"content-page\">\r\n");
      out.write("\t\t\t\t <select  name=\"pageSize\">\r\n");
      out.write("\t\t\t\t\t<option value=\"10\">10条/页</option>\r\n");
      out.write("\t\t\t\t\t<option value=\"20\">20条/页</option>\r\n");
      out.write("\t\t\t\t\t<option value=\"50\">50条/页</option>\r\n");
      out.write("\t\t\t\t</select>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div id=\"userPage\" class=\"tcdPageCode\"></div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("  </body>");
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
