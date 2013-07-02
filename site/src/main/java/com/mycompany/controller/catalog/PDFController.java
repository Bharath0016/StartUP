/*
 * Copyright 2008-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mycompany.controller.catalog;

import org.broadleafcommerce.cms.page.dto.PageDTO;
import org.broadleafcommerce.cms.page.service.PageService;
import org.broadleafcommerce.common.web.BroadleafRequestContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

 
@Controller
@RequestMapping("")
public class PDFController {
     
    /**
     * Size of a byte buffer to read/write file
     */
    private static final int BUFFER_SIZE = 4096;
             
    /**
     * Path of the file to be downloaded, relative to application's directory
     */
    private String filePath = "/downloads/SpringProject.zip";
     
    /**
     * Method for handling file download request from client
     */
    
    @Resource(name = "blPageService")
    private PageService pageService;
    
    @RequestMapping(value=("/createPDF"),method = RequestMethod.GET)
    public void doDownload(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
 
    	try{
    	
    	System.out.println("1");
    	// get absolute path of the application
    	BroadleafRequestContext context = BroadleafRequestContext.getBroadleafRequestContext();
        //String appPath = context.getRealPath("");
        //System.out.println("appPath = " + appPath);
    	System.out.println("converting to HTML");
		Document document = new Document(PageSize.A4, 36, 72, 108, 180);
	    PdfWriter.getInstance(document,new FileOutputStream("C:\\Users\\Bharath\\Desktop\\bharath1.pdf"));
	    document.open();
	    
    	Map<String, String[]> map = (Map<String, String[]>)context.getRequest().getParameterMap();
    	Iterator<Map.Entry<String, String[]>> it = map.entrySet().iterator();
    	System.out.println("empty="+context.getRequest().getParameterMap().isEmpty());
    	while (it.hasNext()) {
            Map.Entry<String, String[]> pairs = (Map.Entry<String, String[]>)it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue()+"pppppp"+Arrays.toString(pairs.getValue()));
            document.add(new Paragraph(Arrays.toString(pairs.getValue())));
            it.remove(); // avoids a ConcurrentModificationException
        }
    	    document.close();
    	    System.out.println("converted to HTML");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	// construct the complete absolute path of the file
        //String fullPath = appPath + filePath;      
    	System.out.println("3");
    	File downloadFile = new File("C:\\Users\\Bharath\\Desktop\\bharath1.pdf");
    	System.out.println("4");
    	FileInputStream inputStream = new FileInputStream(downloadFile);
         
        // get MIME type of the file
        //String mimeType = context.getMimeType(fullPath);
        //if (mimeType == null) {
            // set to binary type if MIME mapping not found
            //mimeType = "application/octet-stream";
        //}
        //System.out.println("MIME type: " + mimeType);
 
        // set content attributes for the response
    	System.out.println("5");
    	response.setContentType("application/pdf");
    	System.out.println("6");
        response.setContentLength((int) downloadFile.length());
 
        // set headers for the response
        System.out.println("7");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);
        
        System.out.println("8");
        // get output stream of the response
        OutputStream outStream = response.getOutputStream();
 
        System.out.println("9");
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
 
        System.out.println("10");
        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
 
        System.out.println("11");
        inputStream.close();
        outStream.close();
        System.out.println("12");
        response.flushBuffer();
        boolean success = (new File("C:\\Users\\Bharath\\Desktop\\bharath1.pdf")).delete();
        System.out.println(success);
 
    }
}