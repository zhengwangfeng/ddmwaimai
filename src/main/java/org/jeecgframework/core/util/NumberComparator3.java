package org.jeecgframework.core.util;

import java.util.Comparator;

import com.tcsb.tcsbfoodtastefunction.entity.TcsbFoodTasteFunctionEntity;



/**
* @ClassName: NumberComparator 
* @Description: TODO(字符串比较器) 
* @author jeecg 
* @date 2013-1-31 下午06:18:35 
*
 */
public class NumberComparator3 implements Comparator<Object> {
	private boolean ignoreCase = true;

	public NumberComparator3() {
	}

	public NumberComparator3(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		String o1 = "";
		String o2 = "";
		if (ignoreCase) {
			TcsbFoodTasteFunctionEntity c1 = (TcsbFoodTasteFunctionEntity) obj1;
			TcsbFoodTasteFunctionEntity c2 = (TcsbFoodTasteFunctionEntity) obj2;
			o1 = c1.getStateorder();
			o2 = c2.getStateorder();
		}
		if (o1 != null && o2 != null) {
			for (int i = 0; i < o1.length(); i++) {
				if (i == o1.length() && i < o2.length()) {
					return -1;
				} else if (i == o2.length() && i < o1.length()) {
					return 1;
				}
				char ch1 = o1.charAt(i);
				char ch2 = o2.charAt(i);
				if (ch1 >= '0' && ch2 <= '9') {
					int i1 = getNumber(o1.substring(i));
					int i2 = getNumber(o2.substring(i));
					if (i1 == i2) {
						continue;
					} else {
						return i1 - i2;
					}
				} else if (ch1 != ch2) {
					return ch1 - ch2;
				}
			}
		}
		return 0;
	}

	private int getNumber(String str) {
		int num = Integer.MAX_VALUE;
		int bits = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
				bits++;
			} else {
				break;
			}
		}
		if (bits > 0) {
			num = Integer.parseInt(str.substring(0, bits));
		}
		return num;
	}
}
