package project.pc.generator;

import java.io.File;
import java.util.HashMap;

public class QRGenerator {

	public QRGenerator()
	{
		super();
	}

	public int createQR(HashMap<String, Object> paramMap)
	{
		int	Ret = 0;
		String workDir = paramMap.get("workDir").toString();
		String transValue = paramMap.get("qr_master_no").toString();
		String url = paramMap.get("landingUrl").toString() + "?idx="+transValue;
		String imagePath = paramMap.get("imgPath").toString();
		String saveDir = paramMap.get("saveDir").toString();

		//이미지 저장 디렉토리 생성
		File f = new File(saveDir);
		File imgf = new File(imagePath);
		if(f.exists() == false)
		{
			f.mkdirs();
		}
		f = null;
		if(imgf.exists()) imgf.delete();
		imgf = null;

		//bg color
		long bgcr = Long.parseLong(paramMap.get("bgcolor").toString().substring(1, 3), 16);
		long bgcg = Long.parseLong(paramMap.get("bgcolor").toString().substring(3, 5), 16);
		long bgcb = Long.parseLong(paramMap.get("bgcolor").toString().substring(5, 7), 16);
		//fg color
		long fgcr = Long.parseLong(paramMap.get("fgcolor").toString().substring(1, 3), 16);
		long fgcg = Long.parseLong(paramMap.get("fgcolor").toString().substring(3, 5), 16);
		long fgcb = Long.parseLong(paramMap.get("fgcolor").toString().substring(5, 7), 16);
		//lt color
		long ltcr = Long.parseLong(paramMap.get("ltcolor").toString().substring(1, 3), 16);
		long ltcg = Long.parseLong(paramMap.get("ltcolor").toString().substring(3, 5), 16);
		long ltcb = Long.parseLong(paramMap.get("ltcolor").toString().substring(5, 7), 16);
		//lb color
		long lbcr = Long.parseLong(paramMap.get("lbcolor").toString().substring(1, 3), 16);
		long lbcg = Long.parseLong(paramMap.get("lbcolor").toString().substring(3, 5), 16);
		long lbcb = Long.parseLong(paramMap.get("lbcolor").toString().substring(5, 7), 16);
		//rt color
		long rtcr = Long.parseLong(paramMap.get("rtcolor").toString().substring(1, 3), 16);
		long rtcg = Long.parseLong(paramMap.get("rtcolor").toString().substring(3, 5), 16);
		long rtcb = Long.parseLong(paramMap.get("rtcolor").toString().substring(5, 7), 16);

		try
		{
			Ret = GenClass.LoadLibrary(workDir);

			if(Ret == 1)
			{
				long bgc = (bgcr<<16)|(bgcg<<8)|bgcb;
				long fgc = (fgcr<<16)|(fgcg<<8)|fgcb;
				long ltc = (ltcr<<16)|(ltcg<<8)|ltcb;
				long lbc = (lbcr<<16)|(lbcg<<8)|lbcb;
				long rtc = (rtcr<<16)|(rtcg<<8)|rtcb;
				try
				{
					Ret = GenClass.QRCreater(ltc, rtc, lbc, bgc, fgc, url, imagePath, workDir);
				}
				catch (Exception e)
				{
					System.out.println("QRGenerator createQR error : " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("QRGenerator createQR error : " + e.getMessage());
			e.printStackTrace();
		}
		
		return Ret;
	}
}
