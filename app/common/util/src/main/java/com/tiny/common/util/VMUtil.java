package com.tiny.common.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

public class VMUtil {

	/**
	 * �����ֻ��չʾ
	 * @param mobile
	 * @return
	 */
	public static String hideMobile(String mobile) {
		if (StringUtils.isBlank(mobile))
			return "";

		return mobile.substring(0, 4) + "****" + mobile.substring(8);
	}

	/**
	 * ��ʽ����
	 * @param num
	 * @param format
	 * @return
	 */
	public static String formatNum(Number num, int type) {
		String format = "#,##0.00";
		if (type == 1) {
			format = "#,##0.00";
		} else if (type == 2) {
			format = "###0";
		} else if (type == 3) {
			format = "###0.#";
		} else if (type == 4) {
			format = "#,##0";
		}
		return new DecimalFormat(format).format(num);
	}

	/*    *//**
			* ��ʽ����
			* @param num
			* @param format
			* @return
			*/
	/*
	 * public static String formatNum(Money num, int type) { return formatNum(num.getAmount(), type); }
	 */
	public static String formatNum(String num, int type) {
		return formatNum(Double.valueOf(num), type);
	}

	public static Double parseDouble(String num) {
		if (StringUtils.isBlank(num))
			return 0.00;
		return Double.valueOf(num);
	}

	public static String pageBean(int pageNumber, int pageSum) {
		// 0.ҳ����Ҫ�õ��ı���
		String confirm = "document.getElementById('turnPage').click();";
		String style = "onmouseover=\"this.className='cur-s-page'\" onmouseout=\"this.className=''\"";

		// 1.��ʼ�����
		StringBuilder bfString = new StringBuilder("");

		// 2.��װ���ص�Ԫ�أ��ύ��ť �� ��������
		bfString.append("<input type='submit' style='display:none' name='turnPage' id='turnPage'>");
		bfString.append("<input type='hidden' id='pageNum' name='pageNum' value='" + pageNumber + "'/>");

		// 2.��װ����һҳ����ť
		if (pageNumber == 1) {
			bfString.append("<span " + style + "><</span>");
		} else {
			bfString.append("<span onclick=\"document.getElementById('pageNum').value=" + (pageNumber - 1) + ";"
					+ confirm + "\" " + style + "><</span>");
		}

		// 4.��װ����һҳ����ť
		if (pageNumber != 1) {
			bfString.append("<span onclick=\"document.getElementById('pageNum').value=1;" + confirm + "\" " + style
					+ ">1</span>");
		}

		// 5.�жϵ�ǰҳ�����һҳ�Ƿ����5ҳ��������5ҳ����һҳ�͵�ǰҳ֮ǰ���� ʡ�Ժ�
		if (pageNumber > 4) {
			bfString.append("...&nbsp;&nbsp;&nbsp;");
		}

		// 6.��װ����ǰҳ��ǰ2��ҳ��
		for (int i = pageNumber - 2, c = 2; c > 0; i++, c--) {
			if (i < 2)
				continue;
			bfString.append("<span onclick=\"document.getElementById('pageNum').value=" + i + ";" + confirm + "\" "
					+ style + ">" + i + "</span>");
		}

		// 7.��װ����ǰҳ��
		bfString.append("<span class='cur-s-page'>" + pageNumber + "</span>");

		// 8.��װ����ǰҳ����2��ҳ��
		for (int i = pageNumber + 1, c = 2; i < pageSum && c > 0; i++, c--) {
			bfString.append("<span onclick=\"document.getElementById('pageNum').value=" + i + ";" + confirm + "\" "
					+ style + ">" + i + "</span>");
		}

		// 9.�жϵ�ǰҳ�������һҳ�Ƿ񳬹�5ҳ��������5ҳ�������ʡ�Ժ�
		if (pageNumber + 3 < pageSum) {
			bfString.append("...&nbsp;&nbsp;&nbsp;");
		}

		// 10.��װ�����һҳ��
		if (pageNumber != pageSum) {
			bfString.append("<span onclick=\"document.getElementById('pageNum').value=" + pageSum + ";" + confirm
					+ "\" " + style + ">" + pageSum + "</span>");
		}

		// 11.��װ����һҳ��
		if (pageNumber == pageSum) {
			bfString.append("<span " + style + ">></span>");
		} else {
			bfString.append("<span onclick=\"document.getElementById('pageNum').value=" + (pageNumber + 1) + ";"
					+ confirm + "\" " + style + ">></span>");
		}

		return bfString.toString();
	}

	public static String cutoutStr(String str, int beginIndex, int endIndex) {
		if (StringUtils.isBlank(str))
			return "";

		if (endIndex > str.length()) {
			endIndex = str.length();
			return str.substring(beginIndex, endIndex);
		} else {
			return str.substring(beginIndex, endIndex) + "...";
		}
	}

	public static String substring(String str, int beginIndex, int endIndex) {
		if (StringUtils.isBlank(str))
			return "";

		if (endIndex > str.length())
			endIndex = str.length();

		return str.substring(beginIndex, endIndex);
	}

	public static String getNewsType(String newsType) {
		if (newsType.equals("NEW")) {
			return "���¶�̬";
		} else if (newsType.equals("MEDIA")) {
			return "ý������";
		}
		return "";
	}

	public static String addLink(String content, boolean succ, String errMsg) {
		if (!succ) {
			return "<b>" + errMsg + "</b>";
		}
		if (StringUtils.isBlank(content)) {
			return "";
		}
		int index = content.indexOf("http://");

		if (index == -1) {
			return content;
		}
		// TODO
		int finish1 = content.indexOf("", index);
		int finish2 = content.indexOf("", index);
		int finish3 = content.indexOf(",", index);
		int finish4 = content.indexOf(' ', index);

		int finish = content.length();
		if (finish1 != -1) {
			finish = finish1;
		} else if (finish2 != -1 && finish2 < finish) {
			finish = finish2;
		} else if (finish3 != -1 && finish3 < finish) {
			finish = finish3;
		} else if (finish4 != -1 && finish4 < finish) {
			finish = finish4;
		}

		String link = content.substring(index, finish);
		String newContent = content.substring(0, index) + "<a href='" + link + "'>" + content.substring(index, finish)
				+ "</a>" + content.substring(finish);
		return newContent;
	}

	public String generateProductDetailUrl(String fundCode) {
		if (StringUtils.isBlank(fundCode))
			return "";
		StringBuilder builder = new StringBuilder("/detail/");
		builder.append(fundCode.substring(3)).append("-").append("26").append(RandomUtils.nextInt(2)).append("-")
				.append(fundCode.substring(0, 3)).append("-").append(RandomStringUtils.randomNumeric(3)).append("-")
				.append(RandomStringUtils.randomNumeric(4)).append(".htm");
		return builder.toString();
	}

	public String generateProductInvestListUrl(String fundCode) {
		if (StringUtils.isBlank(fundCode))
			return "";
		StringBuilder builder = new StringBuilder("/investRecord/");
		builder.append("26").append(RandomUtils.nextInt(2)).append("-").append(fundCode.substring(3)).append("-")
				.append(RandomStringUtils.randomNumeric(3)).append("-").append(RandomStringUtils.randomNumeric(4))
				.append("-").append(fundCode.substring(0, 3)).append(".htm");
		return builder.toString();
	}

	public static String getDate() {
		Date date = new Date();
		return Long.toString(date.getTime());
	}

	public static String getWebDate() {
		return DateUtils.getWebTodayString();
	}

	public static boolean canModify() {
		boolean result = true;
		if (DateUtils.getHourInt(new Date()) > 22) {
			result = false;
		}
		return result;
	}

	public String getDate(Date date) {
		SimpleDateFormat formart = new SimpleDateFormat("yyyy-MM-dd");
		return formart.format(date);
	}

	public boolean contain(String str, String substr) {
		return str.indexOf(substr) != -1;
	}

	public String getRemainingMoney(String totalMoney, String percent) {
		double totalMoneyDouble = Double.valueOf(totalMoney);
		double percentDouble = Double.valueOf(percent);

		return formatNum(totalMoneyDouble * (100 - percentDouble) / 100.00, 2);
	}

	public static List<String> spliteJJZQ(String jjzq) {
		String reg = "��([0-9]*)([\u4e00-\u9fa5]*)";
		String limitTime = null, limitType = null;
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(jjzq);
		if (m.find()) {
			limitTime = m.group(1);
			limitType = m.group(2);
		}

		List<String> resultList = new ArrayList<String>();
		resultList.add(limitTime);
		resultList.add(limitType);
		return resultList;
	}

	/**
	 * 
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isAfterNow(Date date) {
		if (date == null) {
			return false;
		}
		return date.after(new Date());
	}
}
