package com.zyd.leetcode;

import java.util.List;

/**
 * ����֮��
 * ���������������ʾ��������ÿ���ڵ����һ����λ��
*��Щ��λ�Ƿ����ŵģ�Ҳ���Ǹ�λ���������ײ���
*��д������������������ͣ�����������ʽ���ؽ����
 * @author leshu
 *
 */
public class AddTwoNumbers {
	
	public static void main(String[] args) {
		ListNode l1 = new ListNode(2);
		l1.next = new ListNode(4);
		l1.next.next = new ListNode(2);
		ListNode l2 = new ListNode(5);
		l2.next = new ListNode(6);
		l2.next.next = new ListNode(5);
		System.out.println(10 %10);
		System.out.println(addTwoNumbers(l1, l2));
	}
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    	boolean isAddOne = false;
    	ListNode result = null;
    	ListNode curNode = null;
    	while(l1 != null || l2 != null) {
    		int curNum = 0;
    		if (isAddOne) {
    			curNum +=1;
    			isAddOne = false;
    		}
    		if (l1  != null) {
    			curNum += l1.val;
    			l1=l1.next;

    		}
    		if (l2  != null) {
    			curNum += l2.val;
                l2=l2.next;
    		}
    		if (curNum >= 10) {
    			curNum = (curNum)%10;
    			isAddOne = true;
    		}
    		if (result == null) {
    			result = curNode = new ListNode(curNum);
    		} else {
    			ListNode node = new ListNode(curNum);
    			curNode = curNode.next =node;
    		}

    	}
    	if (isAddOne) {
    		curNode.next = new ListNode(1);
    	}
		return result;
    }	
}
class ListNode {
     int val;
     ListNode next;
     ListNode(int x) { val = x; }
}