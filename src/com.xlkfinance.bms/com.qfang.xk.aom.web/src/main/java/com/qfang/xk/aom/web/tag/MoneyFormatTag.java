package com.qfang.xk.aom.web.tag;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年8月1日下午4:28:06 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 金额格式化<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
public class MoneyFormatTag extends SimpleTagSupport {
   private Object object;

   @Override
   public void doTag() throws JspException, IOException {
      double money = (double) object;
      DecimalFormat format = new DecimalFormat();
      format.applyPattern("##,###.00");
      String formatMoney = format.format(money);
      if (".00".equals(formatMoney)) {
         formatMoney="0";
      }
      JspWriter out = getJspContext().getOut();
      out.print(formatMoney);

   }

   public Object getObject() {
      return object;
   }

   public void setObject(Object object) {
      this.object = object;
   }

}
