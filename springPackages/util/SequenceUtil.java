package project.common.util;

import java.util.concurrent.atomic.AtomicLong;


/**
 * ����Ű(Ʈ����� Ű) �� �ϴ� ��
 * @author  �����
 */
public class SequenceUtil {

	private final int sessionKeyLength 		= 30;
	private final String dateFormat 		= "yyyyMMddHHmmss";
	private final static SequenceUtil instance 	= new SequenceUtil();
	private final AtomicLong sequence = new AtomicLong();
	
	public static SequenceUtil getInstance() {
		return instance;
	}
	
	public synchronized String getSessionKey() {
		return StringUtil.getCurrentTime(dateFormat) + StringUtil.long2ZeroFillString(nextVal(), sessionKeyLength - dateFormat.length() - 2 );
	}
	
	public synchronized long nextVal() {		
		sequence.incrementAndGet();
		return sequence.get();
	}
}
