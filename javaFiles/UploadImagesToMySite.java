import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadImagesToMySite extends HttpServlet 
{
   
   private boolean isMultipart;
   private String filePath;
   private int maxFileSize = 5000 * 1024;
   private int maxMemSize = 4 * 1024;
   private File file ;

   public void init( )
   {      
      filePath = "/usr/share/apache-tomcat-7.0.32/webapps/hw3/images/";         
   }
   
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException 
   {
      isMultipart = ServletFileUpload.isMultipartContent(request);
      response.setContentType("text/html");
      PrintWriter out = response.getWriter( );
      
      if( !isMultipart )
      {
         out.println("<html>");
         out.println("<head>");
         out.println("<title>Servlet upload</title>");  
         out.println("</head>");
         out.println("<body>");
         out.println("<p>No file uploaded</p>"); 
         out.println("</body>");
         out.println("</html>");
         return;
      }
      
      DiskFileItemFactory factory = new DiskFileItemFactory();
      factory.setSizeThreshold(maxMemSize);
      factory.setRepository(new File("/usr/share/apache-tomcat-7.0.32/webapps/hw3/temp/"));
      ServletFileUpload upload = new ServletFileUpload(factory);
      upload.setSizeMax( maxFileSize );
      try
      { 
      List fileItems = upload.parseRequest(request);
     
      Iterator i = fileItems.iterator();

      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet upload</title>");  
      out.println("</head>");
      out.println("<body>");
      String fileName = null;
      
      while ( i.hasNext () ) 
      {
         FileItem fi = (FileItem)i.next();
         if ( !fi.isFormField () )	
         {
            
            String fieldName = fi.getFieldName();
            fileName = fi.getName();
            String contentType = fi.getContentType();
            boolean isInMemory = fi.isInMemory();
            long sizeInBytes = fi.getSize();
            
            if( fileName.lastIndexOf("\\") >= 0 )
            {
               file = new File( filePath + 
               fileName.substring( fileName.lastIndexOf("\\"))) ;
            }
            else
            {
               file = new File( filePath + 
               fileName.substring(fileName.lastIndexOf("\\")+1)) ;
            }
            fi.write( file ) ;
            
         }
      }
      out.println("</body>");
      out.println("</html>");
      response.sendRedirect("XMLFileMaker?xmlName=" + fileName);
      
   }catch(Exception ex) { System.out.println(ex); }
 }
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException 
   {  
        throw new ServletException("GET method used with " + getClass( ).getName( )+": POST method required.");
   } 
}
