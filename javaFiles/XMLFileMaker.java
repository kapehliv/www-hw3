import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.imageio.ImageIO;



public class XMLFileMaker extends HttpServlet 
{
    
    int h =0;
    int w =0;
    String pic = null;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException 
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        pic = request.getParameter("xmlName");

        BufferedImage bi = null;
        String myPhoto = "/usr/share/apache-tomcat-7.0.32/webapps/hw3/images/"+pic;
        File file = new File(myPhoto);
    
        try
        {
            bi = ImageIO.read(file);
            h = bi.getHeight();
            w = bi.getWidth();
        } catch (Exception e) { bi = null; }
         
         
        out.println("<html>");
        out.println("<link rel='stylesheet' href='web/xmlup.css' type='text/css'>"); 
        out.println("<body>");        
        out.println("<head>");
        out.println("<title>Xml upload</title>");  
        out.println("<h1>"+"Comment Your Photo And Let Others see this!!!"+"</h1>");
        out.println("<h4>"+"..write your comment in the textarea under the photo and press the button.."+"</h4>");
        out.println("<img src='images/"+pic+"' alt='image' />"+"</a>");
        out.println("<h4>Here is a field to add comment for your pic</h4>");  
        out.println("<form action=\"XMLFileMaker\" method=\"post\">");
        out.println("<textarea rows=\"2\" name=\"photoComment\" cols=\"50\">"+pic+"_is_beautiful"+"</textarea><br />");     
        out.println("<input type=\"submit\" value=\"Add My CoMMenT\" />");
        out.println("</form>"); 
        out.println("</head>");
        out.println("</body>");
        out.println("</html>");
} 
    
    
public void doPost(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException 
{
      
            Writer writer = null;     
            String photoComment = requset.getParameter("photoComment");     
            
            try 
            {
                    String xmlForm = "<PICTURES>"+"\n"+"<PICTURE>"+"\n"+"<COMMENT> "+photoComment+"</COMMENT>" +"\n"
                    +"<HEIGHT>"+h+"</HEIGHT>\n<WIDTH>"+w+"</WIDTH>\n"
                    +"<ROTATE>"+0+"</ROTATE>\n"+"</PICTURE>"+"\n"+"</PICTURES>";
                    String filePath = "/usr/share/apache-tomcat-7.0.32/webapps/hw3/xmlPhoto/"+pic+".xml";
                    File myPhotoFile = new File(filePath);
                    writer = new BufferedWriter(new FileWriter(myPhotoFile));
                    writer.write(xmlForm);
            } catch (FileNotFoundException e) 
            {
            e.printStackTrace();
            } catch (IOException e) 
            {
            e.printStackTrace();
            } finally 
            {
                try 
                {
                    if (writer != null) 
                    {
                        writer.close();
                    }
                } catch (IOException e) 
                {
                e.printStackTrace();
                }
            }
            
                try 
                {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) 
                {
                    Logger.getLogger(XMLFileMaker.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.sendRedirect("afterLogin.jsp");
        
  } 
}
