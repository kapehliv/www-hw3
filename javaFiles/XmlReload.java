import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class XmlReload extends HttpServlet
{
     @Override
       public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException 
        {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            Writer writer = null;

          String pic = request.getParameter("userName");
          String comment = request.getParameter("comment");
          String width = request.getParameter("width");
          String height = request.getParameter("height");
          String rotation = request.getParameter("rotation");
          String xmlForm = null;
          
         if(!(width==null || height==null || rotation==null || pic==null)){
           try {
                    xmlForm = "<PICTURES>"+"\n"+"<PICTURE>"+"\n"+"<COMMENT> "+comment+"</COMMENT>" +"\n"
                    +"<HEIGHT>"+height+"</HEIGHT>\n<WIDTH>"+width+"</WIDTH>\n"
                    +"<ROTATE>"+rotation+"</ROTATE>\n"+"</PICTURE>"+"\n"+"</PICTURES>";
                    String myFileName = "/usr/share/apache-tomcat-7.0.32/webapps/hw3/xmlPhoto/"+pic+".xml";
                    File photoXmlFile = new File(myFileName);
                    writer = new BufferedWriter(new FileWriter(photoXmlFile));
                    writer.write(xmlForm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                        if (writer != null) {
                            writer.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
             }
            out.println(xmlForm);   
            out.println(pic);
         }    
        RequestDispatcher rd = request.getRequestDispatcher("Edit");
        rd.forward(request, response);         
    }   
}

