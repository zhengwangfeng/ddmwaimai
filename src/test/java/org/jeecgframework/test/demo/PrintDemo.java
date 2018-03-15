package org.jeecgframework.test.demo;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;  
  
public class PrintDemo {  
  
    public static void main(String[] args) throws PrintException {  
    	String aaa = "1";
    	String bbb = "1,";
    	String ccc = "1,2";
    	
    	String[] ar = aaa.split(",");
    	
    	String[] br = bbb.split(",");
    	
    	String[] cr = ccc.split(",");
    	
    	System.out.println("a:"+ar.length + "--"+ ar[0]);
    	System.out.println("a:"+br.length+ "--"+ br[0]);
    	System.out.println("a:"+cr.length);
    	
    	//printTxt("");
    	
    	
		/*HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
		String htmlstr = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>"
                  +" <html xmlns='http://www.w3.org/1999/xhtml'>"
                  +"<style>.p1 {font-size:36px;font-weight:200;color:#0000FF;}</style>"
                  +"<style>.p2 { font-size:24px;font-weight:300;color: #FF0000;}</style>"
                  +"<head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><title>CSS外联样式表</title></head>"
                  +"<body><p class='p1'>CSS外联样式表</p><p class='p2'>CSS外联样式表</p><p class='p3'>CSS外联样式表</p><p class='p4'>CSS外联样式表 </p>"
                  + "</body></html>";
		imageGenerator.loadHtml(htmlstr);
		imageGenerator.getBufferedImage();
		imageGenerator.saveAsImage("D:/hello-world.png");
*/
        
		
    	
    	
        
    	
    }  
    
    
    public void print() throws PrintException {  
    	PrintDemo dp = new PrintDemo();  
    	String fileName = "D:/print_test.png";
    	dp.drawImage(fileName);  
    	dp.drawImage("D:\\workspace\\jeecgos\\src\\main\\webapp\\images\\1496996216.png");  
    	
    }
    
    

    
    
    /** 
     * 画图片的方法 
     *  
     * @param fileName 
     *            [图片的路径] 
     * @throws PrintException 
     */  
    public void drawImage(String fileName) throws PrintException {  
        try {  
           
	 		DocFlavor dof = null;  
            // 根据用户选择不同的图片格式获得不同的打印设备  
            if (fileName.endsWith(".gif")) {  
                // gif  
                dof = DocFlavor.INPUT_STREAM.GIF;  
            } else if (fileName.endsWith(".jpg")) {  
                // jpg  
                dof = DocFlavor.INPUT_STREAM.JPEG;  
            } else if (fileName.endsWith(".png")) {  
                // png  
                dof = DocFlavor.INPUT_STREAM.PNG;  
            }  
            // 字节流获取图片信息  
            FileInputStream fin = new FileInputStream(fileName);  
	 		
	 		
            // 获得打印属性  
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();  
            // 每一次默认打印一页  
            pras.add(new Copies(1));  
            // 获得打印设备 ，字节流方式，图片格式  
            PrintService pss[] = PrintServiceLookup.lookupPrintServices(dof,  
                    pras);  
            // 如果没有获取打印机  
            if (pss.length == 0) {  
                // 终止程序  
                return;  
            }  
            // 获取第一个打印机  
            PrintService ps = pss[0];  
            System.out.println("Printing image..........." + ps);  
            // 获得打印工作  
            DocPrintJob job = ps.createPrintJob();  
            // 设置打印内容  
            Doc doc = new SimpleDoc(fin, dof, null);  
            // 出现设置对话框  
            MediaPrintableArea area = new MediaPrintableArea(0, 0, 50, 50, MediaPrintableArea.MM);
            pras.add(area);
            job.print(doc, pras);  
            fin.close();  

            
        } catch (IOException ie) {  
            // 捕获io异常  
            ie.printStackTrace();  
        }  
    }  
    
    
    
    
    
    public static void printTxt(String fileName){
    	
    	//JFileChooser fileChooser = new JFileChooser(); // 创建打印作业  
        // File file = new File("D:\\workspace\\jeecgos\\src\\main\\webapp\\webpage\\com\\tcsb\\order\\print.html");// 获取选择的文件  
        // File file2 = new File("D:/111.txt");
         // 构建打印请求属性集  
         HashPrintRequestAttributeSet pras2 = new HashPrintRequestAttributeSet();  

         // 设置打印格式，因为未确定类型，所以选择autosense  
         DocFlavor flavor2 = DocFlavor.INPUT_STREAM.AUTOSENSE;  
         
         // 定位默认的打印服务  
         PrintService defaultService2 = PrintServiceLookup.lookupDefaultPrintService();  

         
         try {  
             DocPrintJob jobs = defaultService2.createPrintJob(); // 创建打印作业  
            
             //创建打印数据
             String str = "\t 标题  \n" +
					 "*******************************\n" +
						 "订单号:134564654\n" +
					 "桌号:001  开始时间:2017-06-09\n" +
					 "*******************************\n" + 
					 "菜单名称 \t数量\t金额\n" +
					 "海西\t1\t0.01\n" +
					 "pijiu\t1\t0.01\n" +
					 "我不知道是什么 \t1\t0.01\n" +
					 "\n\n\n";
             
             
             InputStream inputStream = new ByteArrayInputStream(str.getBytes("GBK")); 

             //file2.createNewFile();
             
            // fis2 = new FileInputStream(file2); // 构造待打印的文件流  
             DocAttributeSet das = new HashDocAttributeSet();  
             Doc doc2 = new SimpleDoc(inputStream, flavor2, das);  
           
             jobs.print(doc2, pras2);  
            // fis2.close(); 
         } catch (Exception e) {  
             e.printStackTrace();  
         } 
    }
    
}