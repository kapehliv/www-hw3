import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 

public class DisplayImages extends HttpServlet 
{
   public void init( ){}
   
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException 
   {
      response.setContentType("text/html");
     
      PrintWriter out = response.getWriter( );
      
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet upload</title>");  
      out.println("</head>");
      out.println("<body>");
      
      String path = "/usr/share/apache-tomcat-7.0.32/webapps/hw3/images/"; 
 
      String files;
      File folder = new File(path);
      File[] listOfFiles = folder.listFiles(); 
 
      out.println("<h1>PhotoAlbum</h1> ");
  
      for (int i = 0; i < listOfFiles.length; i++) 
      {
            if (listOfFiles[i].isFile()) 
            {
                files = listOfFiles[i].getName();
                response.setContentType("text/html");
                out.println("<link rel='stylesheet' href='web/photoalbum.css' type='text/css'> ");      
                out.println("<form action=\"Edit?userName="+files+"\" method=\"post\">"+"</form>"); 
                out.print("<a href=\"javascript:document.forms["+i+"].submit()\"> <img src='images/"+files+"' alt='image' />"+"</a>");       
            }
      }
      
     out.println("</body>");
     out.println("</html>");
      
  }
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException 
   {
        
        throw new ServletException("GET method used with " + getClass( ).getName( )+": POST method required.");
   } 
   
    public void filelist() {}
}




 

