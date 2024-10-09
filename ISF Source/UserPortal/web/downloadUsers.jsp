<%-- 
    Document   : downloadUsers
    Created on : Jul 26, 2013, 10:22:10 AM
    Author     : raok1
--%>

<%@page import="jxl.format.BorderLineStyle"%>
<%@page import="jxl.format.Border"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.util.Date"%>
<%@page import="jxl.write.Label"%>
<%@page import="jxl.write.WritableCellFormat"%>
<%@page import="jxl.format.Colour"%>
<%@page import="jxl.format.UnderlineStyle"%>
<%@page import="jxl.write.WritableFont"%>
<%@page import="jxl.write.WritableSheet"%>
<%@page import="jxl.write.WritableWorkbook"%>
<%@page import="jxl.Workbook"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Locale"%>
<%@page import="jxl.WorkbookSettings"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.isf.beans.UserRegistration"%>
<%@page import="com.isf.dao.UserDAO"%>
<%
    response.reset();
    response.setHeader("Content-type", "application/vnd.ms-excel");
    response.setHeader("Content-disposition", "inline; filename=users.xls");

    UserDAO userdao = new UserDAO();
    WorkbookSettings ws = new WorkbookSettings();
    String[] columns = new String[]{"User ID", "First Name", "Last Name", "Middle Name", "Suffix", "Salutation", "User Role", "Primary Email", "Primary Mobile",
        "Address Line1", "Address Line2", "City", "State", "Country", "ZipCode", "Primary Institution", "Primary Inst. Country",
        "Alternate Email1", "Alternate Email2", "Alternate Email 3", "Alternate Email4", "Alternate City", "Alternate State",
        "Alternate Country", "Alternate Institution", "Alternate Inst. Country", "Alternate Mobile", "UserAgreement Status", "UserAgreement Date"};

    ws.setLocale(new Locale("en", "EN"));
    File f = new File("users.xls");
    WritableWorkbook workbook = Workbook.createWorkbook(f, ws);
    WritableSheet sheet = workbook.createSheet("Users", 0);

    WritableFont header = new WritableFont(WritableFont.COURIER,
            12,
            WritableFont.BOLD,
            false,
            UnderlineStyle.NO_UNDERLINE,
            Colour.WHITE);
    WritableCellFormat headerFormat = new WritableCellFormat(header);
    headerFormat.setBackground(Colour.DARK_BLUE);
    headerFormat.setWrap(true);
    headerFormat.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);
    Label lr = null;
    for (int i = 0; i < columns.length; i++) {
        lr = new Label(i , 0, columns[i], headerFormat);
        sheet.addCell(lr);
    }
    WritableFont body = new WritableFont(WritableFont.COURIER,
            WritableFont.DEFAULT_POINT_SIZE,
            WritableFont.NO_BOLD,
            false,
            UnderlineStyle.NO_UNDERLINE,
            Colour.BLACK);
    WritableCellFormat bodyFormat = new WritableCellFormat(body);
    bodyFormat.setWrap(true);
    bodyFormat.setBorder(Border.ALL,BorderLineStyle.THIN);

    int c = 0, r = 1;
    ArrayList<UserRegistration> list = userdao.getAllUsers();
    for (UserRegistration user : list) {
        lr = new Label(c++,r,user.getUserIdentity(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getFirstName(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getLastName(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getMiddleName(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getNameSuffix(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getSalutation(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getUserRole(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getPrimaryEmail(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getPrimaryMobile(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getAdd1(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getAdd2(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getUserCity(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getUserState(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getUserCountry(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getZip(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getPriInstitution(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getPriInstCountry(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getAltEmail1(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getAltEmail2(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getAltEmail3(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getAltEmail4(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getAltCity(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getAltState(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getAltCountry(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getAltInstitution(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getAltInstCountry(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getAltMobile(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getUserAgreementStatus(),bodyFormat);
        sheet.addCell(lr);
        lr = new Label(c++,r,user.getUserAgreementDate(),bodyFormat);
        sheet.addCell(lr);
       
       ++r;
       c=0;

    }
    workbook.write();
    workbook.close();
    
   OutputStream os = (response.getOutputStream());
    FileInputStream fis = new FileInputStream("users.xls");
                   int buflen = 10 * 1024 ;
                byte[] data = new byte[buflen];
                int len = 0;
                BufferedInputStream bis = new BufferedInputStream(fis, buflen);
                while ((len = bis.read(data, 0, buflen)) != -1) {
                    os.write(data, 0, len);
                 }
                 os.flush();
                bis.close();
                fis.close();
                f.delete();
%>
