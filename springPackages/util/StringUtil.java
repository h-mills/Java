package project.common.util;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * @class StringUtil.java
 * @Description 	
 * @see
 *
 * 2005. 11. 3. ó�� �ۼ�
 * 2005. 11. 3. ����ȭ �ۼ�
 */
public class StringUtil {
	public static final String PRE = "PRE";
	public static final String POST = "POST";
	private static final String ZERO_SPACE[] = {"00000","0000","000","00","0",""};	
	/**
	 * <P>
	 * �Էµ� ���ڰ��� �տ� 0�� �ٿ��� ��Ʈ������ ����
	 * </p>
	 * @param a int
	 * @param size int
	 * @return String
	 */
	public static String getSizeString(int a, int size) {
		return getSizeString(a, size, "0");
	}

	/**
	 * <P>
	 * �Էµ� ���ڰ��� �տ� 0�� �ٿ��� ��Ʈ������ ����
	 * </p>
	 * @param a long
	 * @param size int
	 * @return String
	 */
	public static String getSizeString(long a, int size) {
		return getSizeString(a, size, "0");
	}

	/**
	 * Method getSizeString.
	 * 
	 * <pre>
	 *  �Էµ� ���� �տ� preFix�� �ٿ��� ��Ʈ������ ����
	 * <br>
	 * </pre>
	 * 
	 * @param a
	 * @param size
	 * @param preFix
	 * @return String
	 */
	public static String getSizeString(int a, int size, String preFix) {
		StringBuffer rvalue = new StringBuffer();

		for (double i = Math.pow(10, (size - 1)); i >= 1; i = i / 10) {
			if (a < i) {
				rvalue.append(preFix);
			}
			else {
				rvalue.append(a);
				break;
			}

		}
		return rvalue.toString();
	}

	/**
	 * Method getSizeString.
	 * 
	 * <pre>
	 *  �Էµ� ���� �տ� preFix�� �ٿ��� ��Ʈ������ ����
	 * <br>
	 * </pre>
	 * 
	 * @param a
	 * @param size
	 * @param preFix
	 * @return String
	 */
	public static String getSizeString(long a, int size, String preFix) {
		StringBuffer rvalue = new StringBuffer();

		for (double i = Math.pow(10, (size - 1)); i >= 1; i = i / 10) {
			if (a < i) {
				rvalue.append(preFix);
			}
			else {
				rvalue.append(a);
				break;
			}
		}
		return rvalue.toString();
	}

	/**
	 * <P>
	 *
	 * </p>
	 * @param a
	 * @param size
	 * @param preFix
	 * @param preflg
	 * @return
	 */
	public static byte[] getSizeString(byte[] a, int size, String preFix, String preflg) {
		byte[] rvalue = new byte[size];
		int index = 0;
		//byte[] tmp = a.getBytes();

		if (PRE.equals(preflg)) {
			for (int s = size; s > a.length; s--) {
				System.arraycopy(preFix.getBytes(), 0, rvalue, index, 1);
				index++;
			}
			System.arraycopy(a, 0, rvalue, index, a.length);
		}
		else {
			System.arraycopy(a, 0, rvalue, index, a.length);
			index += a.length;

			for (int s = size; s > a.length; s--) {
				System.arraycopy(preFix.getBytes(), 0, rvalue, index, 1);
				index++;
			}
		}

		return rvalue;
	}

	/**
	 * <P>
	 *
	 * </p>
	 * @param a
	 * @return
	 */
	public static String getNextNumString(String a) {
		int num = Integer.parseInt(a);
		num++;
		return getSizeString(num, a.length());
	}

	/**
	 * <P>
	 *
	 * </p>
	 * @param a
	 * @param size
	 * @return
	 */
	public static String getSizeString(String a, int size) {
		return getSizeString(a, size, "0", "PRE");
	}

	/**
	 * Method getSizeString.
	 * 
	 * <pre>
	 *  �Էµ� ���� �տ� preFix�� �ٿ��� ��Ʈ������ ����
	 * <br>
	 * </pre>
	 * 
	 * @param a
	 * @param size
	 * @param preFix
	 * @param preflg ���� ���ڸ� ����ʿ� ��ġ�ϴ����� ���� Flag ("PRE","POST")
	 * @return String
	 */
	public static String getSizeString(String a, int size, String preFix, String preflg) {
		StringBuffer rvalue = new StringBuffer();
		byte[] tmp = a.getBytes();
		if ("PRE".equals(preflg)) {
			for (int s = size; s > tmp.length; s--) {
				rvalue.append(preFix);
			}
			rvalue.append(a);
		}
		else {
			rvalue.append(a);
			for (int s = size; s > tmp.length; s--) {
				rvalue.append(preFix);
			}
		}

		return rvalue.toString();
	}

	/**
	 * <P>
	 *
	 * </p>
	 * @param FieldName
	 * @return
	 */
	public static String makeMethodName(String FieldName) {
		String methodName = new String();
		String[] token = FieldName.toLowerCase().split("_");

		//methodName = token[0];
		for (int i = 0; i < token.length; i++) {
			methodName += (token[i].substring(0, 1).toUpperCase() + token[i].substring(1));
		}
		return methodName;
	}

	
	/**
	 * �����׸��� table field name������� ����
	 * @param orderByType
	 * @return
	 */
	public static String getConvertFieldName(String orderByType){
		int i=0, byLen = orderByType.length();
		String ss = "", rtString = "";
		for ( i=0;i<byLen;i++) {
			ss = orderByType.substring(i,i+1);
			if ( ss == ss.toUpperCase() && ss != ss.toLowerCase()) {
				rtString = rtString + "_" + ss;
			} else {
				rtString = rtString + ss;	
			}
		}
		return rtString.toUpperCase();
	}
		

	/**
	 * Token���� �и��� ���ڿ��� String array�� �����
	 * 
	 * @param str token���� �и��� ���ڿ� 
	 * @param token ����
	 * @return String �迭
	 */
	public static String[] splitString(String str, String token){
		StringTokenizer tokenizer;
		String[] arrRtn = null;
		int cnt, idx;
		if(null != str){
			tokenizer = new StringTokenizer(str, token);
			cnt = tokenizer.countTokens();
			arrRtn = new String[cnt];
	        idx = 0;
	        while(tokenizer.hasMoreElements()){
				arrRtn[idx++] = tokenizer.nextToken().trim();
	        }
		}
		return arrRtn;
	}
	
	
	/**
	 * <P>
	 *
	 * </p>
	 * @param src
	 * @param len
	 * @return
	 */
	public static byte[] hexstr2ascii(String src, int len) {
		int i = 0;

		byte[] ret = new byte[len / 2];
		String temp = new String();

		for (i = 0; i < len / 2; i++) {
			temp = src.substring(i * 2, i * 2 + 1);
			temp += src.substring(i * 2 + 1, i * 2 + 2);
			ret[i] = (byte) Integer.parseInt(temp, 16);
			temp = null;
		}

		return ret;
	}

	/**
	 * <P>
	 *
	 * </p>
	 * @param ascii
	 * @return
	 */
	public static String ascii2hexstr(byte[] ascii) {
		String ret = new String();
		//System.out.println("ascii.length : "+ascii.length);
		int i = 0;

		for (i = 0; i < ascii.length; i++) {
			ret += printHex(ascii[i]);
		}
		return ret;
	}

	/**
	 * <P>
	 *
	 * </p>
	 * @param data
	 * @return
	 */
	public static String printHex(byte data) {
		String ret = new String();
		String temp = new String();

		temp = Integer.toHexString((int) data);
		if (temp.length() == 2) {
			return temp;
		}
		if (temp.length() < 2) {
			ret = "0" + temp;
			return ret;
		}
		if (temp.length() > 2) {
			ret = temp.substring(temp.length() - 2, temp.length());
		}
		return ret;
	}

	/**
	 * <P>
	 *
	 * </p>
	 * @param english
	 * @return
	 */
	public static String E2K(String english) {
		String korean = null;

		if (english == null)
			return null;
		//if (english == null ) return "";

		try {
			korean = new String(english.getBytes("8859_1"), "KSC5601");
		}
		catch (UnsupportedEncodingException e) {
			korean = new String(english);
		}
		return korean;
	}

	/**
	 * <P>
	 *
	 * </p>
	 * @param english
	 * @return
	 */
	public static String UTF82K(String english) {
		String korean = null;

		if (english == null)
			return null;
		//if (english == null ) return "";

		try {
			korean = new String(english.getBytes("utf-8"), "KSC5601");
		}
		catch (UnsupportedEncodingException e) {
			korean = new String(english);
		}
		return korean;
	}
	
	/**
	 * <P>
	 *
	 * </p>
	 * @param english
	 * @return
	 */
	public static String UTF82EUCKR(String english) {
		String korean = null;

		if (english == null)
			return null;
		//if (english == null ) return "";

		try {
			korean = new String(english.getBytes("utf-8"), "euc-kr");
		}
		catch (UnsupportedEncodingException e) {
			korean = new String(english);
		}
		return korean;
	}
	
	/**
	 * <P>
	 *
	 * </p>
	 * @param korean
	 * @return
	 */
	public static String K2E(String korean) {
		String english = null;

		if (korean == null)
			return null;
		//if (korean == null ) return "";

		english = new String(korean);
		try {
			english = new String(korean.getBytes("KSC5601"), "8859_1");
		}
		catch (UnsupportedEncodingException e) {
			english = new String(korean);
		}
		return english;
	}

	/**
	 * <P>
	 *
	 * </p>
	 * @param strDate
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String strDate, String format) throws ParseException {
		SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);
		Date date = formatter.parse(strDate);
		return date;
	}

	/**
	 * <P>
	 *
	 * </p>
	 * @param date
	 * @return
	 */
	public static java.sql.Date getSqlDate(Date date) {
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

	/**
	 * Method cutString.
	 * �Էµ�str�� size��ŭ �ڸ� �� '...'�� ���ٿ� ������
	 * @param str - String
	 * @param size - int
	 * @return String - 
	 */
	public static String cutString(String str, int size) {
		String result = null;
		try {
			if (str == null || size == 0) {
				result = "";
			}
			else {
				String subKor = null;
				String resultKor = null;
				String kscKor = null;
				//subKor = str.substring(0,size);
				byte[] tmp = str.getBytes();
				if (tmp.length <= size) {
					result = str;
				}
				else {

					byte[] tt = new byte[size];
					for (int i = 0; i < size; i++)
						tt[i] = tmp[i];

					subKor = new String(tt);
					try {
						kscKor = new String(subKor.getBytes("8859_1"), "euc-kr");
					}
					catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					try {
						resultKor = new String(kscKor.getBytes("euc-kr"), "8859_1");
					}
					catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if (resultKor == null) {
						resultKor = subKor;
					}
					if (resultKor.equals(""))
						subKor = str.substring(0, size - 1);

					result = subKor + "...";
				}
			}
		}
		catch (IndexOutOfBoundsException e) {
			result = str;
		}
		return result;
	}

	/**
	 * MethodName: isNull<br>
	 * ���̰ų� blank�̸� true, �׷��� ������ false���� ����<br>
	 * @param String value
	 * @return boolean
	 */
	public static boolean isNull(String value) {
		if (value == null || value.trim().equals(""))
			return true;

		return false;
	}

	/**
	 * MethodName: replaceNull<br>
	 * �ΰ��̸� ''����, �׷��������� trim()�� ���� ����<br>
	 * @param String value
	 * @return String
	 */
	public static String replaceNull(String value) {
		return replaceNull(value, "");
	}

	/**
	 * MethodName: replaceNull<br>
	 * �ΰ��̸� repStr ����, �׷��������� trim()�� ���� ����<br>
	 * @param String value
	 * @param String repStr
	 * @return String
	 */
	public static String replaceNull(String value, String repStr) {
		if (isNull(value))
			return repStr;

		return value.trim();
	}

	/**
	 * <P>
	 * ��Ʈ�� ���ڸ� int������ �����.
	 * </p>
	 * @param value String
	 * @return int
	 */
	public static int parseInt(String value) {
		int rvalue = 0;
		if (value == null || "".equals(value)) {
			rvalue = 0;
		}
		else {
			try{
				rvalue = Integer.parseInt(value);
			}catch(Exception e){
				rvalue = 0;
			}
		}
		return rvalue;
	}
	
	/**
	 * <P>
	 * ��Ʈ�� ���ڸ� long������ �����.
	 * </p>
	 * @param value String
	 * @return long
	 */
	public static long parseLong(String value) {
		long rvalue = 0;
		if (value == null || "".equals(value)) {
			rvalue = 0;
		}
		else {
			try{
				rvalue = Long.parseLong(value);
			}catch(Exception e){
				rvalue = 0;
			}
		}
		return rvalue;
	}

	/**
	 * <P>
	 * 스트링의 특정 문자열을 다른 문자열로 치환한다.
	 * </p>
	 * @param buffer String
	 * @param src String
	 * @param dst String
	 * @return String
	 */
	public static String ReplaceAll(String buffer, String src, String dst)
	{
		if (buffer == null)
			return null;
		if (src == null || buffer.indexOf(src) < 0)
			return buffer;

		int bufLen = buffer.length();
		int srcLen = src.length();
		StringBuffer result = new StringBuffer();

		int i = 0;
		int j = 0;
		for (; i < bufLen;)
		{
			j = buffer.indexOf(src, j);
			if (j >= 0)
			{
				result.append(buffer.substring(i, j));
				result.append(dst);

				j += srcLen;
				i = j;
			}
			else
				break;
		}
		result.append(buffer.substring(i));
		return result.toString();
	}

	/**
	 * ���ڿ��� ���̸� ���Ѵ�.(�ѱ� 2����Ʈ ó��)
	 * 
	 * @param str
	 * @return
	 */
	public static int getLength(String str) {
		return str.getBytes().length;
	}
	
	
	/**
	 * string �ڸ���
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static String getSubstring(String str, int start, int end, boolean bl) {
		if (null == str) {
			return null;
		}

		int srcLen = getLength(str);
		if (srcLen <= start) {
			return str;
		}

		// ���� Index ã��
		char chr;
		int sIdx = 0;
		int eIdx = 0;
		int readLen = 0;

		//DB�� ���� binary ���ڰ� �Էµ� ��� ���� �߻�
		String readStr = str;
		try {
			
			for (sIdx = 0; sIdx < start && readLen < start; sIdx++) {
				chr = str.charAt(sIdx);
				if (0 == (chr & 0xFF00)) {
					readLen++;
				} else {
					readLen += 2;
				}
			}
	
			// ���� Index ã��
			readLen = 0;
			for (eIdx = 0; eIdx < end && readLen < end && readLen < srcLen; eIdx++) {
				chr = str.charAt(eIdx);
				if (0 == (chr & 0xFF00)) {
					readLen++;
				} else {
					readLen += 2;
				}
			}
			// �ѱ��� endIdex �߰��� �ɸ���� ������ ����(�ѱ�) ����
			if (end < readLen) {
				eIdx--;
			}
			readStr = str.substring(sIdx, eIdx);

		}catch (Exception e) {
			
		}
		if ( srcLen > readLen ) {
			readStr = readStr + "...";
		}
		return readStr;
	}
	
	
    /**
     * ���ʿ� �������� �߰�
     * 
     * @param src ��
     * @param pad padding char
     * @param size ���� ���ڿ� ����
     * @return Padding ���
     */
    public static String getLeftPadding(String src, String pad, int size) {
            StringBuffer sb = new StringBuffer(10);
            if (null == src) {
                    return null;
            }
            for (int i = 0; i < size - src.length(); i++) {
                    sb.append(pad);
            }
            sb.append(src);
            return sb.toString();
    }
    
	
    /**
	 * 
	 * @param format
	 * 			ex> format : yyyyMMddHHmmssSSS
	 * @return
	 */
	public static String getCurrentTime(String format) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(calendar.getTime());
	}
	
	public synchronized static String getCurrentTimeString() {
			
		String currentTimeString = new String();
		int tempint = 0;
		String tempString = new String();

		Calendar calendar = Calendar.getInstance();

		currentTimeString += calendar.get(Calendar.YEAR);		
		
		tempint = calendar.get(Calendar.MONTH) + 1;
		tempString = int2ZeroFillString(tempint, 2);
		currentTimeString += tempString;
		
		tempint = calendar.get(Calendar.DAY_OF_MONTH);
		tempString = int2ZeroFillString(tempint, 2);
		currentTimeString += tempString;
						
		tempint = calendar.get(Calendar.HOUR_OF_DAY);
		tempString = int2ZeroFillString(tempint, 2);
		currentTimeString += tempString;
		
		tempint = calendar.get(Calendar.MINUTE);
		tempString = int2ZeroFillString(tempint, 2);
		currentTimeString += tempString;
		
		tempint = calendar.get(Calendar.SECOND);
		tempString = int2ZeroFillString(tempint, 2);
		currentTimeString += tempString;

//		currentTimeString += calendar.get(13);
		tempint = calendar.get(Calendar.MILLISECOND);
		tempString = int2ZeroFillString(tempint, 3);
		currentTimeString += tempString;
		
		calendar = null;

		return currentTimeString;
	}
	
	public static String int2ZeroFillString(int value, int length) {
		String returnString = new String();
		String valueString = new String();
		Integer m_Integer;
		int i = 0;

		m_Integer = new Integer(value);
		valueString = m_Integer.toString();
		if (valueString.length() > length) {
			return null;
		}

		if (valueString.length() == length) {
			return valueString;
		}

		for (i = 0; i < length - valueString.length(); i++) {
			returnString += "0";
		}

		returnString += valueString;
		return returnString;
	}
	
	public static String long2ZeroFillString(long value, int length) {
		String returnString = new String();
		String valueString = new String();
		Long m_Long;
		int i = 0;

		m_Long = new Long(value);
		valueString = m_Long.toString();
		if (valueString.length() > length) {
			return null;
		}

		if (valueString.length() == length) {
			return valueString;
		}

		for (i = 0; i < length - valueString.length(); i++) {
			returnString += "0";
		}

		returnString += valueString;
		return returnString;
	}
	
 /**
    * 적당한 길이로 자른다.
    * 
    * @param size	
    * @param value
    * @return
    */
   public static String longDouble2String(int size, double value) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(size);
//		nf.setMinimumFractionDigits(size);
		nf.setGroupingUsed(false);
		return nf.format(value);
	}
   
   public static String makeListToString(String[] list, String token) {
	   
	   StringBuffer buffer = new StringBuffer();
	   
	   for(int i = 0;i<list.length;i++) {
		   buffer.append(list[i]);
		   
		   if(i < (list.length-1))
		   buffer.append(token);
	   }
	   return buffer.toString();
   }

	
	
   /**
    * 가격단위 표시 (콤마)
    * @param value
    * @return
    */
   public static String makeNumberPriceComma(String value) {
		NumberFormat nf = NumberFormat.getNumberInstance();

		return nf.format(Long.parseLong(value));
	}   
   

   /**
    * 상품 타입명 변환
    * @param value
    * @return
    */
   public static String makeContentsNameType(String strTarget) {
			
	   if (strTarget.equals("0100")){
		   strTarget = "테마";
	   }else if (strTarget.equals("0101")){
		   strTarget = "락커";
	   }else if (strTarget.equals("0102")){
		   strTarget = "위젯";
	   }else if (strTarget.equals("0103")){
		   strTarget = "아이콘팩";
	   }

		return strTarget;
	}
	 
   /**
    * 포털사이트별 이미지 변환
    * @param value
    * @return
    */
   public static String makePortalSiteImage(String strTarget) {

	   if (strTarget.equals("D")){
		   strTarget = "<img src='../common/images/common/icon_Daum.jpg' alt='다음' width='40' height='40' border='0' align='absmiddle'>";
	   }else if (strTarget.equals("N")){
		   strTarget = "<img src='../common/images/common/icon_Naver.jpg' alt='네이버' width='40' height='40' border='0' align='absmiddle'>";
	   }else if (strTarget.equals("G")){
		   strTarget = "<img src='../common/images/common/icon_Google.jpg' alt='구글' width='40' height='40' border='0' align='absmiddle'>";
	   }else {
		   strTarget = "이미지없음";
	   }
	   
		return strTarget;
	}   

   /**
    * 날짜 포맷 변환
    * @param value
    * @return
    */
   public static String makeReplaceDay(String strTarget) {
			
	   strTarget = strTarget.substring(0, 10);
	   strTarget = strTarget.replace("-", ".");

		return strTarget;
	}

   /**
    * 특정 문자열 문자 변환
    * @param value
    * @return
    */
   public static String makeReplaceStr(String strTarget, String strFront, String strBack) {
			
	   strTarget = strTarget.replace(strFront, strBack);
	   
		return strTarget;
	}   
   
   /**
    * 키워드 포맷 변환
    * @param value
    * @return
    */
   public static String makeReplaceToken(String strTarget) {
			
	   strTarget = strTarget.replace("|", ",");

		return strTarget;
	}   
      
   
   /**
    * 문자 길이 포멧 변환
    * @param value
    * @return
    */
   public static String makeStringLengthFormat(String strTarget) {
			
	   int strTargetLen = strTarget.length();

	   if (strTargetLen >= 25){
		   strTarget = strTarget.substring(0, 25);
		   strTarget = strTarget + "...";		   
	   }else{
		   strTarget = strTarget;
	   }

	   
		return strTarget;
	}     

   /**
    * 문자 길이 포멧 변환
    * @param value
    * @return
    */
   public static String cutStringByLength(String strTarget, int length) {
		
	   int strTargetLen = strTarget.length();

	   if (strTargetLen >= length){
		   strTarget = strTarget.substring(0, length);
		   strTarget = strTarget + "...";		   
	   }
	   
		return strTarget;
	} 

   /**
    * 문자 길이 조절 포멧 변환
    * @param value
    * @return
    */
   public static String cutStringByLength01(String strTarget, int frontLength, int backLength) {

	   strTarget = strTarget.substring(frontLength, backLength);
	   
	   return strTarget;
	}   
   
   /**
    * 가격단위 표시 (콤마 제거) 
    * @param value
    * @return
    */
   public static String makeCommaRemoval(String strTarget) {
			
	   strTarget = strTarget.replace(",", "");

		return strTarget;
	}        
   
   /**
    * 전화번호 변환 
    * @param value
    * @return
    */   
   public static String makePhoneNumber(String phoneNumber) {
	   String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
	   
	   if(!Pattern.matches(regEx, phoneNumber)) return phoneNumber;
	   
	   return phoneNumber.replaceAll(regEx, "$1-$2-$3");
	   
   }
   
	   
   /**
    * 날짜 포맷 변환_스케줄
    * @param value
    * @return
    */
   public static String makeReplaceDaySchedule(String strTarget) {
			
	   strTarget = strTarget.substring(0, 10);
	   strTarget = strTarget.replace("-", ".");

		return strTarget;
	}	   
	 
   /**
    * 시간 포맷 변환_스케줄
    * @param value
    * @return
    */
   public static String makeReplaceTimeSchedule(String strTarget) {
			
	   strTarget = strTarget.substring(11, 16);
	   
		return strTarget;
	}   

   /**
    * 코드 치환
    * @param str
    * @return
    */
   public static String codeReplace(String str) {
	   String code = null;
	   if(str != null){
	   code = str.replaceAll("&", "&amp;");	   
	   code = code.replaceAll("<", "&lt;").replaceAll(">", "&gt;");   
	   code = code.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");   
	   code = code.replaceAll("'", "&#39;");             
	   code = code.replaceAll("eval\\((.*)\\)", "");   
	   code = code.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");   
	   code = code.replaceAll("script", "");
	   code = code.replaceAll("\"", "&quot;");
	   }
	   else {
	   code = "";
	   }
	   
	   return code;
   }
  
   
   /**
    * 한글 깨어짐 방지를 위해
    * @param asAscii
    * @return
    */
	public static String asc2ksc(String asAscii)
   {
       try{//KSC5601
           return new String(asAscii.getBytes("8859_1"), "utf-8");
       }
       catch(UnsupportedEncodingException e)
       {
           return asAscii;
       }
   }	
	
	/** num 숫자 크기 만큼 str앞에 0을 채워줌. 
	 * @param num
	 * @param str
	 * @return
	 */
	public static String getFrontSpaceZeroPut(int num, String str){
		return ZERO_SPACE[num] + str;
	}	   
   
   
   public static void main(String[] args) {
	   
	   //System.out.println(makePhoneNumber("01012341234"));
	   //System.out.println(makePhoneNumber("0101231234"));
	   //System.out.println(makeReplaceDay("2012-10-25 17:11:39.0"));
	   //System.out.println(replaceNull("", "-"));
	   System.out.println(cutStringByLength01("1234567890",4, 6));
	   
	   
	   
	}   
   
}