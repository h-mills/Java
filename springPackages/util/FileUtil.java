package project.common.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.io.FileUtils;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;

public class FileUtil {

	public static void copyFile(InputStream inputStream, String path, String fileName) throws IOException 
	{
		
		File file = new File(path+"/"+fileName);
	    OutputStream outStream = new FileOutputStream(file);
	    byte[] buf = new byte[1024 * 4];
	    int len = 0;
	    while ((len = inputStream.read(buf)) > 0)
	    {
	    	outStream.write(buf, 0, len);
	    }
	    
	    file = null;
	    buf = null;
	    outStream.flush();
	    outStream.close();
	    outStream = null;
	}

	public static byte[] file2Byte(File file) 
	{
		FileInputStream fin = null;
	    FileChannel ch = null;
	    try
	    {
	        fin = new FileInputStream(file);
	        ch = fin.getChannel();
	        int size = (int) ch.size();
	        MappedByteBuffer buf = ch.map(MapMode.READ_ONLY, 0, size);
	        byte[] bytes = new byte[size];
	        buf.get(bytes);
	        return bytes;
	    } 
	    catch (IOException e) 
	    {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        return null;
	    } 
	    finally 
	    {
	        try 
	        {
	            if (fin != null) 
	            {
	                fin.close();
	                fin = null;
	            }
	            if (ch != null) 
	            {
	                ch.close();
	                ch = null;
	            }
	        } 
	        catch (IOException e) 
	        {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	}
	
	public static File makeFile(InputStream inputStream, String path, String fileName)
	{
		try 
		{
		      File file = new File(path+"/"+fileName);
		      OutputStream outStream = new FileOutputStream(file);
		      byte[] buf = new byte[1024 * 4];
		      int len = 0;
		      while ((len = inputStream.read(buf)) > 0)
		      {
		         outStream.write(buf, 0, len);
		         System.out.println(len);
		      }
		      buf = null;
		      outStream.close();
		      inputStream.close();
		      outStream = null;
		      		                   
		      return file;
		    } 
		catch (FileNotFoundException e) 
		{
		      e.printStackTrace();
		      return null;
		} 
		catch (IOException e) 
		{
		      e.printStackTrace();
		      return null;
		}
	}
	
	public static void makeFileFromFile(File fromFile, String path, String fileName) {
		File file = new File(path+"/"+fileName);
		try 
		{
			FileUtils.copyFile(fromFile, file);
		} 
		catch (IOException e) 
		{
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * String으로 넘겨진 파일명의 확장자를 가져온다.
	 * @param fileName
	 * @return
	 */
	public static String getFileExtension(String fileName) {
		return fileName.substring( fileName.lastIndexOf( "." ) + 1 );
	}
	
	/**
	 * file의 확장자를 가져온다.
	 * @param file
	 */
	public static String getFileExtension(File file){
		if(!file.exists()) return null;
		String fileName = file.getName();
		
		return getFileExtension(fileName);
	}	
	
	public static String mkdirList(String rootPath, List<String> dirList ) 
	{
		String path = rootPath;
		
		for(int i = 0;i<dirList.size();i++) 
		{
			path = path + dirList.get(i);
			File subDirectory = new File(path);	
			if(!subDirectory.exists()) 
			{
				subDirectory.mkdir();
			}
			subDirectory = null;
		}	
		
		return path;
	}
	
	public static HashMap<String, Object> copyFileToMonth(InputStream inputStream, String rootPath, List<String> dirList, String fileName) throws IOException 
	{
		
		HashMap<String, Object> fileInfo = new HashMap<String, Object>();
		
		String filePath = mkdirList(rootPath, dirList);
		
		File file = new File(filePath+"/"+fileName);
	    OutputStream outStream = new FileOutputStream(file);

	    byte[] buf = new byte[1024 * 4];
	    int len = 0;

	    while ((len = inputStream.read(buf)) > 0)
	    {
	    	outStream.write(buf, 0, len);
	    }

	    outStream.flush();
	    outStream.close();
	    outStream = null;
	    file = null;
	    buf = null;
	    
	    fileInfo.put("saveFilePath", filePath.replaceAll(rootPath, "") + "/");
	    fileInfo.put("saveFileName", fileName);
	    
	    return fileInfo;
	}	
	
	/**
	 * source 파일을 target으로 옮긴 뒤 삭제한다.
	 * @param source
	 * @param target
	 */
	public static void moveFile(File source, File target) throws Exception 
	{
		if(!source.exists()) return;
		copyFile(source, target);
		source.delete();
	}
	
	/**
	 * 파일을 복사한다.
	 * @param source
	 * @param target
	 * @throws Exception
	 */
	public static void copyFileWithChannel(File source, File target) throws Exception 
	{
		if(!source.exists()) return;
		FileChannel in = null, out = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		try
		{
			fis = new FileInputStream(source);
			fos = new FileOutputStream(target);
			
			in = fis.getChannel();
			out = fos.getChannel();
			in.transferTo(0, in.size(), out);
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			if(in != null) in.close(); in = null;
			if(out != null) out.close(); out = null;
			fis.close(); fis = null;
			fos.flush(); fos.close(); fos = null;
		}
	}
	
	/**
	 * 파일을 복사한다.
	 * @param source
	 * @param target
	 * @throws Exception
	 */
	public static void copyFile(File source, File target) throws Exception
	{
		if(!source.exists()) return;
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		try
		{
			fis = new FileInputStream(source);
			fos = new FileOutputStream(target);
			
			int cnt = -1;
			byte[] buffer = new byte[4096];
			
			while((cnt = fis.read(buffer))!=-1)
			{
				fos.write(buffer, 0, cnt);
			}
			fos.flush();
			buffer = null;
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			if(fos != null) fos.close(); fos = null;
			if(fis != null) fis.close(); fis = null;
			
		}
	}
	

	/**
	 * 이미지 크기을 줄여 지정되 이름으로 저장한다.
	 * @param soruce
	 * @param target
	 * @param targetW
	 * @return
	 */
	public static void createThumbnail(String source, String target, int targetW) throws Exception
	{
        Image imgSource = new ImageIcon(source).getImage();
		int pos = target.lastIndexOf(".");
		String extension = target.substring(pos+1);
        if(!extension.equals("jpg") && !extension.equals("bmp") && !extension.equals("png") && !extension.equals("gif")) 
        {
    		extension = "jpg";
    	}
        int oldW = imgSource.getWidth(null);
        int oldH = imgSource.getHeight(null);

        int newW = targetW;
        int newH = (int)((float)(targetW * oldH) / oldW + 0.5f);
        
        imgSource.flush();
        imgSource = null;
        
		try
		{
			File inf = new File(source);
			BufferedImage srcImage = ImageIO.read(inf);
			
			ResampleOp resampleOp = new ResampleOp((int)(newW+0.5f), (int)(newH+0.5f));
			resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Soft);
			BufferedImage rescaledImage = resampleOp.filter(srcImage, null);
			File thumbFile = new File(target);
			ImageIO.write(rescaledImage, extension, thumbFile);
			rescaledImage.flush(); rescaledImage = null;
			srcImage.flush(); srcImage = null;
			thumbFile = null;
			inf = null;
			resampleOp = null;
		}
		catch(IOException ex)
		{
			System.out.println("createThumbnail error : " + ex.getMessage());
			ex.printStackTrace();
		}
    }
	
	/*
     * delete a file
     * @param delFile file name for deleting
     */
	public static void deleteFile (String saveDir, String delFile) throws Exception
	{
        File f;

		String realdelfile = saveDir + System.getProperty("file.separator") + delFile;

        // delete the files to be already moved.
        if (realdelfile != null && !realdelfile.equals("")) {
            f = new File(realdelfile);
            if (f.exists()) {
                f.delete();
            }
        }
	}	

	public static void createDir(String s)throws Exception
	{
        int i = 0;
        int j = s.lastIndexOf(System.getProperty("file.separator"));
        for(i = s.indexOf(System.getProperty("file.separator"), i); i > -1 && i <= j; i = s.indexOf(System.getProperty("file.separator"), i + 1))
            if(s.substring(0, i).compareTo("") != 0)
            {
                File dir = new File(s.substring(0, i));
                if(!dir.isDirectory())
                {
                    dir.mkdir();
                }
                dir = null;
       	    }
	} // end createdir()

    /*
     * move file in temporary directory to real directory
     * @return success or fail
     */
    public static boolean moveFile (String org, String target) throws Exception 
    {
        File sf, tf;
        boolean isSuccess = true;

        for (int i = 0; i < org.length(); i++) 
        {
            sf = new File(org);
            if (sf.exists()) 
            { 			// if file existed
                tf = new File(target);
                if (!sf.renameTo (tf)) 
                {
                    isSuccess = false;
                    break;
                }
                tf = null;
            }
            else 
            {
                isSuccess = false;
                break;
            }
            sf = null;
        }
        return isSuccess;
    }
	 
	public static boolean deleteFolder(File targetFolder)
	{
		 
	      File[] childFile = targetFolder.listFiles();
	      int size = childFile.length;
	 
	      if (size > 0) 
	      {
	          for (int i = 0; i < size; i++) 
	          {
	              if (childFile[i].isFile()) 
	              {
	                  childFile[i].delete();
	              } 
	              else 
	              {
	                  deleteFolder(childFile[i]);
	              }
	          }
	      }
	 
	      targetFolder.delete();
	 
	      return (!targetFolder.exists());
	 }	
	
	public static void main(String args[]){
//		try{
//			File source = new File("c:/backup/INTROMOBILE_TOP_LOGO.jpg");
//			File target = new File("c:/backup/test.jpg");
//				
//			copyFile(source, target);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		
		
		
		
        String message = null;
        String filePath = "c:/windows";
        File path = new File( filePath );
        
        if( path.exists() == false ){
            System.out.println("경로가 존재하지 않습니다");
        }else{

	        File[] fileList = path.listFiles();
	        for( int i = 0 ; i < fileList.length ; i++ ){
	            if( fileList[ i ].isDirectory() )
	                message = "[디렉토리] ";
	            else
	                message = "[파일] ";
		            message += fileList[ i ].getName();
		            System.out.println( message );
	        }
        }



	}	
	
		
	
}
