import java.io.*;
import org.w3c.dom.*;
import javax.servlet.*;
import javax.xml.parsers.*;
import javax.servlet.http.*;

public class Edit extends HttpServlet 
{ 
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        
          response.setContentType("text/html");
          PrintWriter out = response.getWriter();
          String pic = request.getParameter("userName");

          String comment = null;
          String width = null;
          String height = null;
          String rotation = null;

   
      try
      {
            DocumentBuilderFactory docFactory =  DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse("/usr/share/apache-tomcat-7.0.32/webapps/hw3/xmlPhoto/"+pic+".xml");
            Element  element = doc.getDocumentElement(); 
            NodeList personNodes = element.getChildNodes(); 
            
            for (int i=1; i<2; i++)
            {
                Node emp = personNodes.item(i);
                if (isTextNode(emp)) continue;
                NodeList NameDOBCity = emp.getChildNodes(); 

                comment= NameDOBCity.item(1).getFirstChild().getNodeValue();
                height= NameDOBCity.item(3).getFirstChild().getNodeValue();
                width=NameDOBCity.item(5).getFirstChild().getNodeValue();
                rotation=NameDOBCity.item(7).getFirstChild().getNodeValue();

            }
        } catch(Exception e){ System.out.println(e); }

        
        out.println("<link rel='stylesheet' href='web/onepic.css' type='text/css'> ");  
        out.println("<h1>Photo Editor</h1> ");
        out.println("<h2>"+"Edit photo named: "+pic+"<br> "+"Every cell under the photo has to be filled so that you can see your results"+"</h2> ");
        out.println("<style>h2{"+"color:#ff1493;"+"}</style><br>");    
        out.println("<img src='images/"+pic+"' alt='image' />"+"</a>");
        out.println("<style>form{position:relative; text-align:center; color:black;}</style>");
        out.println("<style>img{-moz-transform: rotate("+rotation+"deg); width:"+width+"; height:"+height+";}</style>");
        out.println("<form action=\"XmlReload?userName="+pic+"\" method=\"post\">");
        out.println("<h5>Change comment:</h5> ");
        out.println("<textarea required=\"required\" rows=\"2\" name=\"comment\" cols=\"50\">"+comment+"</textarea>");
        out.println("<h5>Change height:</h5> ");       
        out.println("<textarea required=\"required\" rows=\"1\" name=\"height\" cols=\"10\">"+height+"</textarea>");
        out.println("<h5>Change width:</h5> ");
        out.println("<textarea required=\"required\" rows=\"1\" name=\"width\" cols=\"10\">"+width+"</textarea>");
        out.println("<h5>Change rotation degrees:</h5> ");
        out.println("<textarea required=\"required\" rows=\"1\" name=\"rotation\" cols=\"10\">"+rotation+"</textarea><br>");
        out.println("<input type=\"submit\" value=\"SeE ReSulTs\" />");
        out.println("</form>");
        out.println("<form action=\"afterLogin.jsp\">");
        out.println("<input type=\"submit\" value=\"Go to Main\" />");
        out.println("</form>");
        out.println("</html>");
  }  

    public boolean isTextNode(Node n){ return n.getNodeName().equals("#text"); }

}
