package project.pc.generator;

public class GenClass {
	private static native int QRGen(long jLTColor, long jRTColor, long jLBColor, long jBGColor, long jFGColor, int jCellType, byte []jMessage, byte []jSavePath, byte []jFrameDir);

	private static boolean isLoadLibrary = false;

	public static int LoadLibrary(String rootPath)
	{
		try
		{
			if (!isLoadLibrary)
			{
				System.load(rootPath + "CKnB_JNI.dll");
				isLoadLibrary = true;
			}
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + "LoadLibrary Error");
			return 0;
		}
		return 1;
	}
	
	public static int QRCreater(long LTColor, long RTColor, long LBColor, long BGColor, long FGColor, String strMessage, String strSavePath, String strWorkDir)
	{
		int Ret = 0;
		try
		{
			byte []Message = strMessage.getBytes("KSC5601");
			byte []SavePath= strSavePath.getBytes("KSC5601");
			byte []WorkDir= strWorkDir.getBytes("KSC5601");

			Ret = QRGen(LTColor, RTColor, LBColor, BGColor, FGColor, 1, Message, SavePath, WorkDir);
			Message = null; SavePath = null; WorkDir = null;
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + "QRCreater Error");
		}
		return Ret;
	}
}