package com.zyd.leetcode;

public class Maximum {
    public int maximum(int a, int b) {
    	int[] arr = {a,b};
		return arr[(int)(((long)a-b)>>> 63)];        
    } 
}
