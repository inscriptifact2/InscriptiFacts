<%-- 
    Document   : userRegbk
    Created on : Jan 18, 2013, 1:05:50 PM
    Author     : raok1
--%>

<%@page import="com.isf.beans.UserRegistration"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.isf.dao.UserDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
       <jsp:useBean id="userReg"  scope="session" class="com.isf.beans.UserRegistration" />
       <jsp:setProperty  property="*" name="userReg"/>
        <%
       ///// UserRegistration userReg =(UserRegistration) session.getAttribute("userReg");
         String nextPage = "workspace.jsp";
        //validate the mandatory fields 
        String msg = userReg.validate();
        String loggedUser = (String) session.getAttribute("loggedUser");
        String action = userReg.getAction().trim();
        boolean saved = false;

        if (msg.equalsIgnoreCase("VALID")) {
            try {
                if(loggedUser !=null && !action.equalsIgnoreCase("UPDATE"))
                    userReg.setCreatedBy(loggedUser);
                 else if(!action.equalsIgnoreCase("UPDATE"))
                    userReg.setCreatedBy("SELF");
                msg = userReg.saveRecord();

                if (loggedUser != null && (action.equalsIgnoreCase("ADD") || action.equalsIgnoreCase("EDIT") || action.equalsIgnoreCase("UPDATE"))) {
                    request.setAttribute("showpage", "instructions.jsp");
                } else if (loggedUser == null && action.equalsIgnoreCase("REG")) {
                    nextPage = "../confirmRegistration.jsp";
                }
                saved = true;
                request.setAttribute("message", "User details updated successfully");
                //add to the cached names
                ArrayList names = (ArrayList) application.getAttribute("uniqueIDs");
                if (names == null) {
                    UserDAO dao = new UserDAO();
                    names = dao.getUniqueIdentities();
                }
                names.add(userReg.getUserIdentity().trim());
                application.setAttribute("uniqueIDs", names);



            } catch (Exception exp) {
                msg = exp.getMessage();
                 if(msg.indexOf("unique constraint")>-1)
                msg = "The UserID already exists. Please provide a different UserID.";
                request.setAttribute("error", msg);
                if (loggedUser != null && action.equalsIgnoreCase("ADD")) {
                    request.setAttribute("showpage", "addUser.jsp");
                }
                if (loggedUser != null && action.equalsIgnoreCase("EDIT")) {
                    request.setAttribute("showpage", "editUser.jsp");
                } else if (loggedUser != null && action.equalsIgnoreCase("UPDATE")) {
                    request.setAttribute("showpage", "register.jsp");
                } else if (loggedUser == null) {
                    nextPage = "../userRegistration.jsp";
                }
            }
            if (saved) {
                session.removeAttribute("userReg");

            }

        } else {
            request.setAttribute("error", msg);

            if (loggedUser != null && action.equalsIgnoreCase("ADD")) {
                request.setAttribute("showpage", "addUser.jsp");
            } else if (loggedUser != null && action.equalsIgnoreCase("EDIT")) {
                request.setAttribute("showpage", "editUser.jsp");
            } else if (loggedUser != null && action.equalsIgnoreCase("UPDATE")) {
                request.setAttribute("showpage", "register.jsp");
            } else if (loggedUser == null) {
                nextPage = "userRegistration.jsp";
            }
            session.setAttribute("userReg", userReg);
        }
        

    %>           
    <jsp:forward page="<%= nextPage%>" />    

</html>
