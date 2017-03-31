//Tom Grossman
//CS4342 - Intro to Data Mining
//Simulated Annealing Clusting
//04/28/2016
//Copyright © 2016 Tom Grossman. All Rights Reserved.

package saclustering; 

import java.util.ArrayList; 
import java.util.List; 
import java.util.Random; 

public class Point { 
	private int x; 
	private int y; 

	public Point(int x, int y) { 
		this.x = x; 
		this.y = y; 
	} 

	public int getCoordX() { 
		return this.x; 
	} 

	public int getCoordY() { 
		return this.y; 
	} 

	public static Point createPoint(int x, int y) { 
		return new Point(x,y); 
	} 

	public static List createPoints(long seed) { 
		List points = new ArrayList(100); 
		Random generator = new Random(seed); 

		for(int c = 0; c < 100; c++) { 
			int x = generator.nextInt(101); 
			int y = generator.nextInt(101); 
			points.add(createPoint(x,y)); 
		} 
	
		return points; 
	} 

	public void printPoint() { 
		int x = this.getCoordX(); 
		int y = this.getCoordY(); 
		System.out.format("\nPoint: (%d, %d)", x, y); 
	} 

	public void updatePoint(int x, int y) { 
		this.x = x; 
		this.y = y; 
	} 

	public boolean equals(Point a) { 
		int x1 = this.getCoordX(); 
		int y1 = this.getCoordY(); 
		int x2 = a.getCoordX(); 
		int y2 = a.getCoordY(); 

		return (((x1 == x2) && (y1 == y2))); 
	} 
} 