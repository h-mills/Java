package logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;


public class Thumbnail {
	/*
	public boolean thumbConvert(String in, String out, int width, int height, String format)
	{
		  File saveFile = new File(out);

		  RenderedOp rOp = JAI.create("fileload", in);
		  BufferedImage im = rOp.getAsBufferedImage();

		  float cvtWidth = 0.5f;
		  float cvtHeight = 0.5f;

		  if(im.getWidth() > im.getHeight())
		  {
			  cvtHeight = (float)height * ((float)im.getHeight() / (float)im.getWidth());
			  cvtWidth = width;
		  }
		  else if(im.getWidth() < im.getHeight())
		  {
			  cvtWidth = (float)width * ((float)im.getWidth() / (float)im.getHeight());
			  cvtHeight = height;
		  }
		  else
		  {
			  cvtWidth = width;
			  cvtHeight = height;
		  }

		  BufferedImage thumb = new BufferedImage((int)cvtWidth, (int)cvtHeight, BufferedImage.TYPE_INT_RGB);
		  Graphics2D g2 = thumb.createGraphics();
		  g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		  g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		  g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		  g2.drawImage(im, 0, 0, (int)cvtWidth, (int)cvtHeight, null);
		  g2.dispose();

		  try
		  {
			  return ImageIO.write(thumb, format, saveFile);
		  }
		  catch(IOException io)
		  {
			   System.out.println("예외 : " + io);
			   return false;
		  }
		  finally
		  {
			  saveFile = null;
		  }
	}
	*/
	public boolean thumbConvert(String in, String out, int width, int height, String format)
	{
		boolean result = true;
		try
		{			
			File inf = new File(in);
			BufferedImage srcImage = ImageIO.read(inf);
			float cvtHeight = 0.5f, cvtWidth = 0.5f;
			
			cvtHeight = (float)height * ((float)srcImage.getHeight() / (float)srcImage.getWidth());
			cvtWidth = width;
			
			ResampleOp resampleOp = new ResampleOp((int)(cvtWidth+0.5f), (int)(cvtHeight+0.5f));
			resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Soft);
			BufferedImage rescaledImage = resampleOp.filter(srcImage, null);
			File destFile = new File(out);
			ImageIO.write(rescaledImage, format, destFile);
			destFile = null;
			inf = null;
			resampleOp = null;
			return result;
		}
		catch(IOException ex)
		{
			System.out.println("thumbConvert error : " + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}
}
