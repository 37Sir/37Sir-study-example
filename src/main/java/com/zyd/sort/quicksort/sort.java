package com.zyd.sort.quicksort;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class sort {
	/**
	 * 快速排序算法
	 * @param a
	 * @param s
	 * @param e
	 */
	public void quickSort(int[] a,int s, int e) {
		int i = s;
		int j =e;
		int temp  = a[s],t;
		while (i < j) {
			while (i < j &&i< j && a[j] >= temp) {
				j--;
			}
			while(i < j && a[j] <= temp) {
				i++;
			}
			//满足条件则交换
			if(i < j) {
				t = a[i];
				a[i] =a[j];
				a[j] = t;
			}
		}
		//交换基准
		a[s] = a[i];
		a[i] = temp;
		quickSort(a, s, j-1);
		quickSort(a, j+1, e);
	}
	
	public static void main(String[] args) {
		ScheduledExecutorService updateTask = Executors.newSingleThreadScheduledExecutor();
		updateTask.execute(() ->{
			System.out.println("111111111111");
		});
	}
}
