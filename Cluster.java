//Tom Grossman
//CS4342 - Intro to Data Mining
//Simulated Annealing Clusting
//04/28/2016
//Copyright © 2016 Tom Grossman. All Rights Reserved.

package saclustering; 

import java.util.ArrayList; 
import java.util.List; 

public class Cluster { 
	private List points; 
	private int pointCount; 
	private int clusterID; 

	public Cluster(int c) { 
		this.clusterID = c; 
		this.points = new ArrayList(); 
		this.pointCount = 0; 
	} 

	public void addPoint(Point x) { 
		this.points.add(x); 
		this.pointCount = this.pointCount + 1; 
	} 

	public static Cluster createCluster(int c) { 
		return new Cluster(c); 
	} 

	public void removePoint(int x) { 
		this.points.remove(x); 
		this.pointCount = this.pointCount - 1; 
	} 

	public int getCount() { 
		return this.pointCount; 
	} 

	public Point getPoint(int x) { 
		return (Point) this.points.get(x); 
	} 

	public static double euclideanDistance(Point a, Point b) { 
		return Math.sqrt(Math.pow(a.getCoordX() - b.getCoordX(), 2) + 
		Math.pow(a.getCoordY() - b.getCoordY(), 2)); 
	} 

	public static double intraClusterDistance(Cluster cluster) { 
		double sum = 0; 
		for(int c = 1; c < cluster.getCount(); c++) { 
			Point alpha = (Point) cluster.getPoint(c - 1); 
			Point beta = (Point) cluster.getPoint(c); 
			sum += euclideanDistance(alpha, beta); 
		} 
		
		return sum; 
	} 

	public double intraClusterDistance() { 
		double sum = 0; 
		for(int c = 1; c < this.getCount(); c++) { 
			Point alpha = (Point) this.getPoint(c - 1); 
			Point beta = (Point) this.getPoint(c); 
			sum += euclideanDistance(alpha, beta); 
		} 
		
		return sum; 
	} 

	public double interClusterDistance() { 
		double sum = 0; 
		int xCentroid = 0; 
		int yCentroid = 0; 

		for(int c = 0; c < this.pointCount; c++) { 
			Point point = (Point) this.getPoint(c); 
			xCentroid = xCentroid + point.getCoordX(); 
			yCentroid = yCentroid + point.getCoordY(); 
		} 

		xCentroid = xCentroid / pointCount; 
		yCentroid = yCentroid / pointCount; 
		Point centroid = new Point(xCentroid, yCentroid); 

		for(int c = 0; c < this.getCount(); c++) { 
			Point alpha = (Point) this.getPoint(c); 
			sum += euclideanDistance(centroid, alpha); 
		}
		
		return sum; 
	} 

	public void printPoints(int c) { 
		for(int i = 0; i < this.pointCount; i++) { 
			Point beta = (Point) this.getPoint(i); 
			int x = beta.getCoordX(); 
			int y = beta.getCoordY(); 
			System.out.format("\nCluster %d - Point %d: (%d, %d)",(c + 1), (i + 1), x, y); 
		}
		
		System.out.println(); 
	} 
	
/*	public void printPoints(int c) { 
		for(int i = 0; i < this.pointCount; i++) { 
			Point beta = (Point) this.getPoint(i); 
			int x = beta.getCoordX(); 
			int y = beta.getCoordY(); 
			System.out.format("%d,%d\n", x, y); 
		}	 
		
		System.out.println(); 
} */ 
}