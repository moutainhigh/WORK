/**
 * @Title: WebConfigurerTest.java
 * @Package org.activiti.explorer
 * @Description: TODO
 * Copyright: Copyright (c) 2017
 * Company: 量度金服
 * 
 * @author: dulin
 * @date: 2017年6月12日 下午5:19:22
 * @version V1.0
 */
package org.activiti.explorer;

import org.activiti.explorer.conf.ApplicationConfiguration;
import org.junit.Test;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.xk.hlc.system.service.ProductService;

/**
  * @ClassName: WebConfigurerTest
  * @Description: TODO
  * @author: dulin
  * @date: 2017年6月12日 下午5:19:22
  */
public class WebConfigurerTest {

	/**
	 * Test method for {@link org.activiti.explorer.servlet.WebConfigurer#contextInitialized(javax.servlet.ServletContextEvent)}.
	 */
	@Test
	public void testContextInitialized() {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationConfiguration.class);
        rootContext.refresh();
        ProductService productService = rootContext.getBean("productService", ProductService.class);
        productService.findProductByProjectId("");
	}

}
