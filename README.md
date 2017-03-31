# Simulated-Annealing-Clustering-CS4342

## Neighbor Selection 
  It just takes the solution, takes a random point from a cluster, removes it, and adds it to another cluster. 

## Cost Function  
  The cost function is just the SSE of all the clusters combined.  

## Acceptance Probability 
  a = exp((neighborCost – solutionCost) / temp)   
  If a > randomly generated number, it accepts the worst solution. 

## Annealing Schedule 
  The temperature: temp = 10000   
  The cooling rate: coolRate = 0.003   
  The formula: temp = temp – coolRate   
  Each execution takes approximately 5 seconds on my computer.  
