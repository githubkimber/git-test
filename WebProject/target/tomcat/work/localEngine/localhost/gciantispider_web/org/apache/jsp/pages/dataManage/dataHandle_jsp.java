package org.apache.jsp.pages.dataManage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class dataHandle_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
      out.write("<html>\r\n");
      out.write("  <head>\r\n");
      out.write(" \t\t<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/dataManage/dataHandle.js\"></script> \r\n");
      out.write("  </head>\r\n");
      out.write("  \r\n");
      out.write("  <body>\r\n");
      out.write("\t<article class=\"dataHandle\">\r\n");
      out.write("\t\t<div class=\"dataHandle-header\">\r\n");
      out.write("\t\t\t<input id=\"bookOrQuery\" type=\"checkbox\" value=\"0\" style=\"display: none;\">\r\n");
      out.write("\t\t\t<ul>\r\n");
      out.write("\t\t\t\t<li id = \"query\" onclick=\"dataHandle.query()\" class=\"on\">\r\n");
      out.write("\t\t\t\t\t<p>查询</p>\r\n");
      out.write("\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t<li  id = \"book\" onclick=\"dataHandle.book()\">\r\n");
      out.write("\t\t\t\t\t<p>预定</p>\r\n");
      out.write("\t\t\t\t</li>\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"dataHandle-list\">\r\n");
      out.write("\t\t\t<input id=\"wayInfo\" type=\"checkbox\" value=\"0\" style=\"display: none;\">\r\n");
      out.write("\t\t\t<ul>\r\n");
      out.write("\t\t\t\t<li id=\"inland\"  class=\"on\">\r\n");
      out.write("\t\t\t\t\t<p>国内</p>\r\n");
      out.write("\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t<li id=\"international\">\r\n");
      out.write("\t\t\t\t\t<p>国际</p>\r\n");
      out.write("\t\t\t\t</li>\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"dataHandle-content\">\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<div class=\"dataHandle-content-one\">\r\n");
      out.write("\t\t\t<input id=\"jsonOrXmlDataType\" value=\"0\" type=\"checkbox\" style=\"display: none;\"> \r\n");
      out.write("\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t\t<p>数据类型：JSON</p>\r\n");
      out.write("\t\t\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div id = \"applicationJson\" class=\"on\">Application / JSON</div>\r\n");
      out.write("\t\t\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div id = \"formJson\" >Form表单中JSON</div>\r\n");
      out.write("\t\t\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t\t<p class=\"on\">数据类型：XML</p>\r\n");
      out.write("\t\t\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div id = \"textXml\">Text / XML</div>\r\n");
      out.write("\t\t\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div id = \"formXml\">Form表单中XML</div>\r\n");
      out.write("\t\t\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t\t<div onclick= \"dataHandle.formClickEvent()\">数据类型：Form</div>\r\n");
      out.write("\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t\t<div onclick= \"dataHandle.getClickEvent()\">数据类型：Get</div>\r\n");
      out.write("\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t</ul>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"dataHandle-content-two\">\r\n");
      out.write("\t\t\t\t<form id=\"content\" class=\"form-horizontal\">\r\n");
      out.write("\t\t\t\t\t<div id= \"url\" class=\"checkbox theFirst\" style=\"float: left;\">\r\n");
      out.write("\t\t\t\t\t\t<label>解析规则url匹配的正则表达式</label>\r\n");
      out.write("\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t<input id=\"requestMatchExpression\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div id = \"formKey\" style=\"float: left;\">\r\n");
      out.write("\t\t\t\t\t\t\t<label id = \"formKeyName\" style=\"width: 110px;text-align: right;font-weight: 400;\">hi</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"formDataField\" type=\"text\" style=\"margin-left: 10px;padding: 0 10px;height: 38px;border: 1px solid #ddd;\">\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div style=\"clear: both;border-bottom: 2px dashed #e6e6e6;margin-bottom: 20px;\"></div>\r\n");
      out.write("\t\t\t\t\t<div class=\"dataHandle-checkbox\">\r\n");
      out.write("\t\t\t\t\t\t<div id=\"query_adultNum\" class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>成人乘机人数</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"queryAdultNum\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"query_childNum\" class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>儿童乘机人数</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"queryChildNum\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"query_infantNum\" class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>婴儿乘机人数</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"queryInfantNum\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"book_contractPhone\" class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>联系人手机号</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"bookContractPhone\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"book_psgName\" class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>乘机人名</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"bookPsgName\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"query_travelType\" class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>是否往返</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"queryTravelType\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"book_idCard\" class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>乘机人证件号</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"bookIdCard\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"book_idType\" class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>证件类型</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"bookIdType\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"query_country\" class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>国家</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"queryCountry\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"book_bookUserId\" class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>购票人ID</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"bookBookUserId\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"book_cabin\" class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>舱位级别</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"bookCabin\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"book_psgType\" class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>乘机人类型</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"bookPsgType\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"dataHandle-checkbox dataHandle-checkbox-media\">\r\n");
      out.write("\t\t\t\t\t\t<div id=\"book_contractName\" class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>联系人名</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"bookContractName\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"book_psgFirName\" class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>乘机人的姓</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"bookPsgFirName\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>始发地</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"depCity\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>目的地</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"arrCity\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>起飞时间</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"flightDate\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"request_method\" class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>请求方法</label>\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<select id = \"requestMethod\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value = \"post\">post</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value = \"get\">get</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t<div style=\"clear: both;\"></div>\r\n");
      out.write("\t\t\t\t<div class=\"dataHandle-btn\">\r\n");
      out.write("\t\t\t\t\t<button id=\"dataEdit\" type=\"button\" class=\"btn btn-primary\">编辑</button>\r\n");
      out.write("\t\t\t\t\t<button id=\"dataSure\" type=\"button\" class=\"btn btn-primary\" style=\"display: none;\" onclick = \"dataHandle.saveOrUpdate()\">确认</button>\r\n");
      out.write("\t\t\t\t\t<button id=\"dataCancel\" type=\"button\" class=\"btn btn-default\" style=\"display: none;\" onclick = \"dataHandle.cancel()\">取消</button>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div style=\"clear: both;\"></div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</article>\r\n");
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
