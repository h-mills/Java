package project.common.config;

public class MsgConstants {

	/**
	 * 통합 코드 (00)
	 * 총 4자리 (0000) 
	 * 앞두자리 분류코드 : 00
	 * 뒤두자리 순번코드 : 00
	 */
	public final static String SUCCESS 					= "0000";
	public final static String SUCCESS_MSG 				= "Success";
	
	public final static String CP00						= "CP00";		
	public final static String CP01						= "CP01";		
	public final static String CP01_MSG					= "Request map generation error.";
	
	public final static String ERR_SYSTEM 				= "FFFF";
	public final static String ERR_SYSTEM_MSG 			= "System exception";
	
	/**
	 * API 성공 싪패 코드 (01)
	 * 총 4자리 (0100) 
	 * 앞두자리 분류코드 : 01
	 * 뒤두자리 순번코드 : 00
	 */
	public final static String API_SYSTEM 				= "0100";
	public final static String API_SYSTEM_MSG 			= "API Success";
	public final static String API_ERR_SYSTEM01 		= "0101";
	public final static String API_ERR_SYSTEM_MSG01 	= "API와 통신할 수 없습니다.";
	public final static String API_ERR_SYSTEM02 		= "0102";
	public final static String API_ERR_SYSTEM_MSG02 	= "API 연동 데이터 규격 오류 입니다.";
	public final static String API_MSG_SYSTEM01 		= "0103";
	public final static String API_MSG_SYSTEM_MSG01 	= "검색된 데이터가 없습니다.";

	//pc
	//login
	public final static String LOGIN_SYSTEM				= "LOGIN";
	public final static String LOGIN_ERROR				= "0001"; 
	public final static String LOGOUT_ERROR				= "0002";
	public final static String SESSIONCLOSE_ERROR		= "0003";

	//stats
	//controller
	public final static String STATS_SYSTEM							= "STATS";
	//controller error
	public final static String STATS_SCAN_ERROR						= "2110";
	public final static String STATS_LOCAL_ERROR					= "2120";
	public final static String SCANEXCEL_ERROR						= "2130";
	public final static String LOCALEXCEL_ERROR						= "2140";
	//service error
	public final static String GETSCANTOTALCOUNT_ERROR_SERVICE		= "2211";
	public final static String GETSCANTODAYCOUNT_ERROR_SERVICE		= "2212";
	public final static String GETSCANDATA_ERROR_SERVICE			= "2213";
	public final static String GETMONTHDATA_ERROR_SERVICE			= "2214";
	public final static String GETDAILYDATA_ERROR_SERVICE			= "2215";
	public final static String GETHOURDATA_ERROR_SERVICE			= "2216";
	public final static String GETLOCALCNT_ERROR_SERVICE			= "2221";
	public final static String GETNATIONRANK_ERROR_SERVICE			= "2222";
	public final static String GETNATIONDATA_ERROR_SERVICE			= "2223";
	public final static String GETCITYDATA_ERROR_SERVICE			= "2224";
	//dao error
	public final static String GETSCANTOTALCOUNT_ERROR_DAO			= "2311";
	public final static String GETSCANTODAYCOUNT_ERROR_DAO			= "2312";
	public final static String GETSCANDATA_ERROR_DAO				= "2313";
	public final static String GETMONTHDATA_ERROR_DAO				= "2314";
	public final static String GETDAILYDATA_ERROR_DAO				= "2315";
	public final static String GETHOURDATA_ERROR_DAO				= "2316";
	public final static String GETLOCALCNT_ERROR_DAO				= "2321";
	public final static String GETNATIONRANK_ERROR_DAO				= "2322";
	public final static String GETNATIONDATA_ERROR_DAO				= "2323";
	public final static String GETCITYDATA_ERROR_DAO				= "2324";

	//QR
	//controller
	public final static String QR_SYSTEM							= "QR";
	//controller error
	public final static String QR_VIEW_ERROR						= "2110";
	public final static String QR_DEL_ERROR							= "2111";
	public final static String QR_NORMAL_ERROR						= "2112";
	public final static String QR_MAKE_ERROR						= "2120";
	public final static String QR_DETAIL_ERROR						= "2130";
	public final static String QR_UPDATEIMAGE_ERROR					= "2131";
	public final static String QR_UPDATE_ERROR						= "2132";
	public final static String QR_GEN_ERROR							= "2133";
	public final static String QR_DOWNLOAD_ERROR					= "2140";
	public final static String QR_FILEDOWN_ERROR					= "2150";
	public final static String TEMPLATE_ERROR						= "2160";
	public final static String LARGEGEN_ERROR						= "2170";
	public final static String LARGEDOWN_ERROR						= "2180";
	//service error
	public final static String GETQRLIST_ERROR_SERVICE				= "2210";
	public final static String GETQRLISTCOUNT_ERROR_SERVICE			= "2211";
	public final static String UPDATEISDELTE_ERROR_SERVICE			= "2212";
	public final static String UPDATEISDELTE_NORMAL_ERROR_SERVICE	= "2213";
	public final static String GETQRINFO_ERROR_SERVICE				= "2220";
	public final static String UPDATEQRDATA_ERROR_SERVICE			= "2221";
	public final static String GETIMAGELIST_ERROR_SERVICE			= "2230";
	public final static String GEN_ERROR_SERVICE					= "2240";
	public final static String GETFILEPATH_ERROR_SERVICE			= "2250";
	public final static String LARGEGEN_ERROR_SERVICE				= "2260";
	public final static String LARGEDOWN_ERROR_SERVICE				= "2270";
	//dao error
	public final static String GETQRLIST_ERROR_DAO					= "2310";
	public final static String GETQRLISTCOUNT_ERROR_DAO				= "2311";
	public final static String UPDATEISDELTE_ERROR_DAO				= "2312";
	public final static String UPDATEISDELTE_NORMAL_ERROR_DAO		= "2313";
	public final static String GETQRINFO_ERROR_DAO					= "2320";
	public final static String UPDATEQRDATA_ERROR_DAO				= "2321";
	public final static String INSERTQRCONFIG_ERROR_DAO				= "2330";
	public final static String INSERTQRMASTER_ERROR_DAO				= "2331";
	public final static String GETIMAGELIST_ERROR_DAO				= "2340";
	public final static String UPDATEIMGPATH_ERROR_DAO				= "2350";
	public final static String GETFILEPATH_ERROR_DAO				= "2360";
	public final static String INSERTLARGECONFIG_ERROR_DAO			= "2370";
	public final static String INSERTQR_ERROR_DAO					= "2371";
	public final static String UPDATEIMAGE_ERROR_DAO				= "2373";
	public final static String GETIMAGES_ERROR_DAO					= "2380";

	//LANDING
	//controller
	public final static String LANDING_SYSTEM						= "LANDING";
	//controller error
	public final static String LANDING_ERROR						= "3110";
	public final static String CONVERTGEO_ERROR						= "3120";
	//service error
	public final static String DECRIPT_ERROR		 				= "3210";
	public final static String DETINFO_ERROR_SERVICE 				= "3211";
	public final static String INSERTQRDET_ERROR_SERVICE 			= "3212";
	public final static String UPDATEQRCOUNT_ERROR_SERVICE			= "3213";
	public final static String GETLANDINGDATA_ERROR_SERVICE			= "3214";
	//dao error
	public final static String INSERTQRDET_ERROR_DAO				= "3312";
	public final static String UPDATEQRCOUNT_ERROR_DAO 				= "3313";
	public final static String GETLANDINGDATA_ERROR_DAO 			= "3314";

	//member
	//controller
	public final static String MEMBER_SYSTEM					= "MEMBER";
	//controller error
	public final static String MAIN_ERROR						= "6101";
	public final static String ADDMAIN_ERROR					= "6102";
	public final static String ADD_ERROR						= "6103";
	public final static String MODMAIN_ERROR					= "6104";
	public final static String MOD_ERROR						= "6105";
	public final static String DEL_ERROR						= "6106";
	public final static String IDCHECK_ERROR					= "6107";
	//service error
	public final static String GETMEMBERLIST_CNT_ERROR_SERVICE 	= "6201";
	public final static String GETMEMBERLIST_ERROR_SERVICE		= "6202";
	public final static String INSERTMEMBER_ERROR_SERVICE		= "6203";
	public final static String UPDATEMEMBER_ERROR_SERVICE		= "6204";
	public final static String DELETEMEMBER_ERROR_SERVICE		= "6205";
	public final static String GETMEMBER_ERROR_SERVICE			= "6206";
	public final static String GETIDCOUNT_ERROR_SERVICE			= "6207";
	//dao error
	public final static String GETMEMBERLIST_CNT_ERROR_DAO		= "6301";
	public final static String GETMEMBERLIST_ERROR_DAO 			= "6302";
	public final static String INSERTMEMBER_ERROR_DAO 			= "6303";
	public final static String UPDATEMEMBER_ERROR_DAO 			= "6304";
	public final static String DELETEMEMBER_ERROR_DAO 			= "6305";
	public final static String GETMEMBER_ERROR_DAO 				= "6306";
	public final static String GETIDCOUNT_ERROR_DAO				= "6307";
}
