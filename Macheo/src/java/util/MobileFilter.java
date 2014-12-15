/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author marcos
 */
public class MobileFilter implements Filter{

   @Override
   public void destroy() {
   }

   @Override
   public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) arg0;
        String userAgent = req.getHeader("user-agent");
        String accept = req.getHeader("Accept");        
        if (userAgent != null && accept != null) {
            UserAgentInfo agent = new UserAgentInfo(userAgent, accept);
            HttpServletResponse res = (HttpServletResponse) arg1;
            if (agent.isMobileDevice()) {               
               res.sendRedirect("/sigmec/faces/mobile/login.xhtml");
               return;
            }
            else {
               
               arg2.doFilter(arg0, arg1);
               return;
            }
            
        }
        
      
   }

   @Override
   public void init(FilterConfig arg0) throws ServletException {
   }

}
