import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends HttpServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
  {
  
        boolean entry=false;
        Connection con = null;  
        Statement stmt = null;
        ResultSet rs = null;
      
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String account = request.getParameter("userName");
        String password = request.getParameter("pass");
      
        try 
        {
                try 
                {
                    Class.forName("org.gjt.mm.mysql.Driver").newInstance();
                } catch (InstantiationException ex) 
                {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) 
                {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                con =DriverManager.getConnection("jdbc:mysql://localhost:3306/homework","root","mypassword");
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM login");

                while(rs.next())
                {
                    if(rs.getObject(1).toString().compareTo(account)==0&&rs.getObject(2).toString().compareTo(password)==0){entry=true; break;};

                }
        } catch (SQLException e) 
        {
            throw new ServletException("Servlet Could not display records.", e);
        } catch (ClassNotFoundException e) 
        {
            throw new ServletException("JDBC Driver not found.", e);
        } finally 
        {
            try 
            {
                if(rs != null) 
                {
                    rs.close();
                    rs = null;
                }
                if(stmt != null) 
                {
                    stmt.close();
                    stmt = null;
                }
                if(con != null)
                {
                  con.close();
                  con = null;
                }
            } catch (SQLException e) {}
  
      
      
      
            response.setContentType("text/html");
    

        if (!entry) 
        {
            out.println("<HTML><HEAD><TITLE>Access Denied</TITLE></HEAD>");
            out.println("<BODY>Your login and password are invalid.<BR>");
            out.println("You may want to <A HREF=\"/hw3/index.html\">try again</A>");
            out.println("</BODY></HTML>");
        } 
        
        else 
        {
          
              HttpSession session = request.getSession();
              session.setAttribute("logon.isDone", account);
      
              try
              {
                    String target = "afterLogin.jsp";//(String) session.getAttribute("login.target");
                    if (target != null) 
                    {
                        response.sendRedirect(target);
                        return;
                    }
             } catch (Exception ignored) { }

            response.sendRedirect("/");
        }
    }
  }  
}
