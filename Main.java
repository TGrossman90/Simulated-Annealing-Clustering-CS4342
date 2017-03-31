//Tom Grossman
//CS4342 - Intro to Data Mining
//Simulated Annealing Clusting
//04/28/2016
//Copyright © 2016 Tom Grossman. All Rights Reserved.

package saclustering; 

import java.io.IOException; 
import java.util.ArrayList; 
import java.util.List; 
import java.util.Random; 

import javax.swing.JOptionPane; 

import com.sun.java_cup.internal.runtime.Scanner; 

public class Main { 
	public static void main(String[] args) { 

		List points = new ArrayList(); 
		List solution = new ArrayList(); 
		List neighbor = new ArrayList(); 

		double temp = 10000; 
		double coolRate = 0.003; 
		double minTemp = 0.00001; 
		int x = 0; 

		String seedStr = JOptionPane.showInputDialog("Please enter the seed string", JOptionPane.PLAIN_MESSAGE); 
		long seed = Integer.parseInt(seedStr); 

		Random generator = new Random(seed); 

		points = Point.createPoints(seed); 
		solution.add(Cluster.createCluster(1)); 
		solution.add(Cluster.createCluster(2)); 
		solution.add(Cluster.createCluster(3)); 
		neighbor.add(Cluster.createCluster(1)); 
		neighbor.add(Cluster.createCluster(2)); 
		neighbor.add(Cluster.createCluster(3)); 


		int size = points.size(); 
		for(int c = 0; c < size; c++) { 
			x = generator.nextInt(3); 
			generateInitialSolution(solution, points, seed, x, c); 
		} 
		
		double solutionCost = calculateCost(solution); 
		neighbor = solution; 
		double neighborCost = calculateCost(neighbor); 

		for(int c = 0; c < solution.size(); c++) { 
			Cluster alpha = (Cluster) solution.get(c); 
			int count = alpha.getCount(); 
			//System.out.printf("\nSolution Cluster %d, PointCount = %d", c, count);  
		}  

		int iterations = 0; 
		while (temp > 1) { 
			for(int c = 0; c < neighbor.size(); c++) { 
				Cluster alpha = (Cluster) solution.get(c); 
				int count = alpha.getCount(); 
				// System.out.printf("\nNeighbor Pre-Cluster %d, PointCount = %d", c, count);  
			}
			
			x = generator.nextInt(100); 
			int y = generator.nextInt(3); 
			
			int yy = generator.nextInt(3); 
			while(yy == y) 
				yy = generator.nextInt(3); 
		
			generateNeighbor(neighbor, points, seed, x, y, yy); 
			neighborCost = calculateCost(neighbor); 

			for(int c = 0; c < neighbor.size(); c++) { 
				Cluster alpha = (Cluster) solution.get(c); 
				int count = alpha.getCount(); 
				// System.out.printf("\nNeighbor Post-Cluster %d, PointCount = %d", c, count);  
			}  

			// System.out.printf("\n\nSolution Cost: %f", solutionCost); 
			// System.out.printf("\nNeighbor Cost: %f", neighborCost); 


			if(neighborCost < solutionCost) { 
				solutionCost = neighborCost; 
				solution = neighbor; 
			} 

			if(neighborCost > solutionCost) { 
				double acceptanceProbability = acceptanceProbability(neighborCost, solutionCost, temp); 

				// System.out.printf("\nAcceptance Probability: %f", acceptanceProbability); 

				float result = (float) ((generator.nextInt(21) - 10) / 10.0); 
				while(Math.abs(result) >= 1) 
					result = (float) ((generator.nextInt(21) - 10) / 10.0); 
				
				result = Math.abs(result); 

				// System.out.printf("\nRandom Number: %f\n\n", result); 

				if( acceptanceProbability > result) { 
					solutionCost = neighborCost; 
					solution = neighbor; 
				} 
			}
			
			temp = temp - coolRate; 
			iterations ++; 

			// System.out.println(iterations); 
		} 
		
		// System.out.printf("\n\nSolution Cost: %f\n", solutionCost); 
		for(int c = 0; c < solution.size(); c++) { 
			Cluster alpha = (Cluster) solution.get(c); 
			alpha.printPoints(c); 
			System.out.println(); 
			System.out.println(); 
			System.out.println(); 
		} 

		Cluster alpha = (Cluster) solution.get(0); 
		double clusterone = alpha.interClusterDistance(); 
		
		Cluster beta = (Cluster) solution.get(1); 
		double clustertwo = beta.interClusterDistance(); 
		
		Cluster gamma = (Cluster) solution.get(2); 
		double clusterthree = gamma.interClusterDistance();  

		System.out.printf("\nCluster 1 InnerCluster Distance = %f", clusterone); 
		System.out.printf("\nCluster 2 InnerCluster Distance = %f", clustertwo); 
		System.out.printf("\nCluster 3 InnerCluster Distance = %f", clusterthree); 

		} 

		private static double acceptanceProbability(double neighborCost, double solutionCost, double temp) { 
			double exp = ((solutionCost - neighborCost) / (temp)); 
			double ap = Math.exp(exp); 
			return ap; 
		} 

		private static void generateInitialSolution(List solution, List points, long seed, int x, int y) { 
			Cluster alpha = (Cluster) solution.get(x); 
			Point beta = (Point) points.get(y); 
			alpha.addPoint(beta); 
		} 

		private static void generateNeighbor(List neighbor, List points, long seed, int x, int y, int yy) { 
			Point alpha = (Point) points.get(x); 
			int cluster = 0; 
			int pointNum = 0; 

			for(int c = 0; c < neighbor.size(); c++) { 
				Cluster beta = (Cluster) neighbor.get(c); 

				for(int i = 0; i < beta.getCount(); i++) { 
					Point gamma = (Point) beta.getPoint(i); 
					if(gamma.equals(alpha)) { 
						cluster = c; 
						pointNum = i; 
					} 
				} 
			} 

			Cluster theta = (Cluster) neighbor.get(cluster); 
			theta.removePoint(pointNum); 

			Cluster omicron = (Cluster) neighbor.get(yy); 
			omicron.addPoint(alpha); 
		} 

		private static double calculateCost(List clusters) { 
			double cost = 0; 
			List centroids = new ArrayList(); 

			int size = clusters.size(); 
			for (int c = 0; c < size; c++) { 
				Cluster alpha = (Cluster) clusters.get(c); 
				int pointCount = alpha.getCount(); 
				int xCentroid = 0; 
				int yCentroid = 0; 

				for(int d = 0; d < pointCount; d++) { 
					Point point = (Point) alpha.getPoint(d); 
					xCentroid = xCentroid + point.getCoordX(); 
					yCentroid = yCentroid + point.getCoordY(); 
				} 

				xCentroid = xCentroid / pointCount; 
				yCentroid = yCentroid / pointCount; 
				Point centroid = new Point(xCentroid, yCentroid); 
				centroids.add(centroid); 
			}	 

			for (int c = 0; c < size; c++) { 
				Point alpha = (Point) centroids.get(c); 
				Cluster beta = (Cluster) clusters.get(c); 
				cost += sumSquaredError(alpha, beta); 
			} 
		
			centroids.clear(); 
			return cost; 
		} 

		private static double sumSquaredError(Point centroid, Cluster cluster){ 
			double sse = 0; 
			int size = cluster.getCount(); 
			for(int c = 0; c < size; c++) { 
				Point alpha = (Point) cluster.getPoint(c); 
				sse += Math.pow((Cluster.euclideanDistance(centroid, alpha)), 2); 
			} 
			
			return sse; 
		} 

		public static void promptEnterKey(){ 
			System.out.println("Press \"ENTER\" to continue..."); 
			try { 
				int read = System.in.read(new byte[2]); 
			} catch (IOException e) { 
				e.printStackTrace(); 
			} 
		} 
} 