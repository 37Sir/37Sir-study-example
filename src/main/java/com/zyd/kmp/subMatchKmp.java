package com.zyd.kmp;

public class subMatchKmp {		
		public static void main(String[] args) {
			String s = "abababbcdfgr";
			String t = "cdfl";
			System.out.println(matchSubStr(s,t));
		}
		/**
		 * kmp算法主逻辑
		 * @param s
		 * @param t
		 * @return
		 */
		public static int matchSubStr(String s, String t) {
			char[] s_arr = s.toCharArray();
			char[] t_arr =t.toCharArray();
			int j =0,i=0;
			int[] next =getNext(t);
			while (i < s.length() && j < t.length()) {
				//如果j是初始位置或者两字符相等，往后移一位
				if (j == -1 || s_arr[i] == t_arr[j]) {
					i++;
					j++;
				} else {
					//不相等，则取出偏移量
					j = next[j];
				}
			}
			if (j>= t.length()) {
				return (i -j);
			}
			return 0;			
		}
		
		/**
		 * kmp算法Next数组的求取
		 * @param t
		 * @return
		 */
		public static int[] getNext(String t) {
			int[] next = new int[t.length()];
			char[] t_arr = t.toCharArray();
			next[0] = -1;
			next[1] = 0;
			int k;
			for (int j=2;j<t.length(); j++) {
				k = next[j-1];
				//判断到最开始了说明没有了
				while (k!=-1) {
					//k表示之前最大的匹配串，所以现在最大为+1，直接结束
					if (t_arr[j-1] == t_arr[k]) {
						next[j] = k+1;
						break;
					} else {
						//得到更小的字串
						k = next[k];
					}
					next[j] = 0;
				}
			}
			return next;
		}
}
