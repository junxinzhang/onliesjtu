package com.onlinesjtu;

class A {
	int m = 5;
	static int n = 3;

	public static void main(String[] args) {
		A obj1 = new A();
		A obj2 = new A();
		obj1.m *= 2;
		n *= 4;
		obj2.m += 1;
		n += 6;
		System.out.println("obj 1.m=" + obj1.m);
		System.out.println("obj 1.n=" + obj1.n);
		System.out.println("obj2.m=" + obj2.m);
		System.out.println("obj2.n=" + obj2.n);
		System.err.println("obj1 toString: " + obj1);
		System.err.println("obj2 toString: " + obj2);

	}
}

class B {
	double x, y;

	B(int a, int b) {
		x = a;
		y = b;
	}
}

public class Sample {
	public static void main(String args[]) {
		//        System.err.println(args[0] + " \t " + args[1]);
		B p1, p2;
		p2 = new B(12, 15);
		p1 = p2;
		p2.x++;
		p2.y = p2.y + p2.y;
		System.out.println("p1.x=" + p1.x);
		System.out.println("p1.y=" + p1.y);
		System.err.println("p1 toString: " + p1);
		System.err.println("p2 toString: " + p2);


		// 1 2 3
		int totalNum = 0;
		for (int i = 1; i < 4; i++) {
			for (int j = 1; j < 4; j++) {
				if (i != j) {
					int num = 10 * i + j;
					System.err.println(num);
					if (num > 50) {
						totalNum++;
					}
				}
			}
		}
		System.err.println(totalNum);
	}
}

class Cylinder {
	private int radius;
	private int height;
	private final double cPI = 3.141592653D;

	public Cylinder(int radius, int height) {
		this.radius = radius;
		this.height = height;
	}

	public Cylinder() {
		this.radius = 10;
		this.height = 10;
	}

	public double surfaceArea() {
		return 2 * cPI * radius * radius + 2 * cPI * radius * height;
	}

	public double volumn() {
		return cPI * radius * radius * height;
	}

	public static void main(String[] args) {
		System.err.println("suraceArea: " + new Cylinder().surfaceArea());
		System.err.println("volumn: " + new Cylinder().volumn());
	}
}
