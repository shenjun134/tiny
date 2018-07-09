package com.tiny.common.util;

import java.util.ArrayList;
import java.util.List;

public class Arrange {
	private List<String>	arrangedList	= new ArrayList<String>();
	private String			interval		= "-";

	public Arrange() {
	}

	public Arrange(String interval) {
		this.interval = interval;
	}

	private void swap(String originalArrs[], int k, int i) {
		String c3 = originalArrs[k];
		originalArrs[k] = originalArrs[i];
		originalArrs[i] = c3;
	}

	public void perm(String list[]) {
		perm(list, 0, list.length - 1);
	}

	public void perm(String originalArrs[], int startAt, int endAt) {
		if (startAt > endAt) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i <= endAt; i++) {
				sb.append(originalArrs[i]).append(interval);
			}
			if (sb.length() > 0) {
				sb.setLength(sb.length() - 1);
			}
			arrangedList.add(sb.toString());
		} else {
			for (int i = startAt; i <= endAt; i++) {
				swap(originalArrs, startAt, i);
				perm(originalArrs, startAt + 1, endAt);
				swap(originalArrs, startAt, i);
			}
		}
	}

	public int getTotal() {
		return arrangedList.size();
	}

	public List<String> getArrangeList(String[] args) {
		perm(args);
		return arrangedList;
	}

	public List<String> getArrangeList() {
		return arrangedList;
	}

}
