/**
 */
package com.tiny.core.model;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class SystemContant {

	/** Application */
	public final static String		APPLICATION_INTRANET_IP		= "INTRANET_IP";								// ����IP

	/**Ĭ�ϵĴ�����id*/
	public final static Integer		DEFAULT_CREATE_ID			= 1;

	/** �ŵ�Request�е��û���Ϣ */
	public final static String		SSO_USER_IP					= "remoteip";
	public final static String		SSO_USER_ID					= "USER_ID";
	public final static String		SSO_USER_NAME				= "USER_NAME";
	public final static String		SSO_USER					= "user";

	/** �ŵ�session�е���֤�� */
	public final static String		VALIDATE_CODE_KEY			= "RANDOMVALIDATECODEKEY";

	public final static String[]	WORK_DAY					= { "һ", "��", "��", "��", "��" };
	public final static String[]	EVERY_DAY					= { "һ", "��", "��", "��", "��", "��", "��" };

	public final static String		USER_OPEN_PAGE				= "��ҳ��";
	public final static String		USER_QUERY_LIST				= "�б��ѯ";
	public final static String		USER_QUERY_DATA				= "��ѯ���";
	public final static String		USER_CHECK_DATA				= "У�����";
	public final static String		USER_SUBMIT_DATA			= "�ύ���";
	public final static String		USER_EXPORT_DATA			= "�������";
	public final static String		USER_DASHBOARD_DATA			= "ͼ�����";
	public final static String		USER_SECURITY_DATA			= "��ȫ���";
	public final static String		USER_MESSAGE_DATA			= "��Ϣ����";

	// �ſ����ǰ�
	public final static String		LOAN_ORDER_NO_PRE			= "94";

	// ���������붩����ǰ�
	public final static String		BALANCE_ACTION_ORDER_NO_PRE	= "B95";

	/** ���ɴ���� ,Ĭ�� 0.8*/
	public static final double		lendScale					= 0.8;

	/** ������� ,Ĭ�� 0.002*/
	public static final double		serviceRate					= 0.002;

}
