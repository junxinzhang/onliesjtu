package com.onlinesjtu;

public class ListNode {
	int val;
	ListNode next;

	ListNode() {
	}

	ListNode(int val) {
		this.val = val;
	}

	ListNode(int val, ListNode next) {
		this.val = val;
		this.next = next;
	}

	@Override
	public String toString() {
		return "ListNode{" + "val=" + val + ", next=" + next + '}';
	}
}

class Solution {
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode dummyHead = new ListNode(0);
		ListNode p = l1, q = l2, curr = dummyHead;
		int carry = 0;
		while (p != null || q != null) {
			int x = (p != null) ? p.val : 0;
			int y = (q != null) ? q.val : 0;
			int sum = carry + x + y;
			carry = sum / 10;
			curr.next = new ListNode(sum % 10);
			curr = curr.next;
			if (p != null)
				p = p.next;
			if (q != null)
				q = q.next;
		}
		if (carry > 0) {
			curr.next = new ListNode(carry);
		}
		return dummyHead.next;
	}

	public int maxArea(int[] height) {

		int max = 0;
		int left = 0, right = height.length - 1;
		while (left < right) {
			int res = Math.min(height[left], height[right]) * (right - left);
			max = Math.max(max, res);
			if (height[left] < height[right]) {
				left++;
			} else {
				right--;
			}
		}
		return max;
	}

	;

	public static void main(String[] args) {
		System.err.println(new Solution().addTwoNumbers(new ListNode(2, new ListNode(4, new ListNode(3))),
				new ListNode(5, new ListNode(6, new ListNode(4)))));

		System.out.println(new Solution().maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
	}
}



