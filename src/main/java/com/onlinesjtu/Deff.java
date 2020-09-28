package com.onlinesjtu;

public class Deff {

	public static void main(String[] args) {
		//
		//        Scanner scanner = new Scanner(System.in);
		//        System.err.println("请输入x ");
		//        int i = scanner.nextInt();
		//        System.err.println("请输入x ： " + i);
		//
		//        System.err.println("请输入y ：");
		//        int j = scanner.nextInt();
		//        System.err.println("请输入y ： " + j);
		//
		//        System.err.println("最大公约数= " + deff(i, j));
		//        System.err.println("最小公倍数= " + (i * j) / deff(i, j));
		//
		//        System.err.println(72 >> 1);
		//        String s = " 0123456789";
		//        String s1, s2;
		//        s1 = s.substring(2);
		//        s2 = s.substring(2, 5);
		//        System.out.println(s1 + s2);

		int i, j, a[] = {5, 9, 6, 8, 7};
		for (i = 0; i < a.length - 1; i++) {
			int k = i;
			for (j = i; j < a.length; j++) {
				if (a[j] < a[k]) {
					k = j;

					int temp = a[i];
					a[i] = a[k];
					a[k] = temp;
				}
			}
			for (i = 0; i < a.length; i++) {
				System.out.print(a[i] + "  ");
			}
			System.out.println();


		}


	}

	static int[][] transpose(int[][] a) {
		int[][] b = new int[a.length][a.length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				b[i][j] = a[j][i];
			}
		}
		return b;
	}

	static int searchMacNumIndex(int[] a) {
		int maxIndex = 0;
		for (int i = 1; i < a.length; i++) {
			if (a[maxIndex] < a[i]) {
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	static int deff(int x, int y) {
		if (x == y) {
			return x;
		} else if (x < y) {
			int t = x;
			x = y;
			y = t;
		}
		for (int k = x % y; k != 0; ) {
			k = x % y;
			x = y;
			y = k;
		}
		//        int k = x % y;
		//        while (k != 0) {
		//            k = x % y;
		//            x = y;
		//            y = k;
		//        }
		return x;
	}


}
