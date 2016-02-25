# Held-Karp
This repository contains 2 implementations of the Held-Karp algorithms. This algorithm is a brute search algorithm which belong to the family of the "branch and bound" ones.

An algorithm that belongs to the "Branch and bound" is usually solve in problems where we want to obtain the best solution given a set of possible solutions. The algorithm will evaluate all the possible alternatives, keeping the best one and using it as a threshold to improve.

The problem in which I used this algorithm is the following:

Suppose you have several points on a map, and each point is separated from the other ones a distance, different from each point. We can built a matrix of distances, from each point to the rest of them. Now suppose that this points on the map are locations within a city, and that the problem is to visit every point starting for the first one, without repeating anyone, and returning to the original location at the end. This problem is called "Travel Salesman Problem" for obvious reasons.
* It is important to recall that the the distance A-B could be different from the distance B-A (imagine that in a specific place, there is a one direction road, so is shorter going in one direction that the opposite).
* It does not matter which is the startin point, because at the end the cycle will cover all the locations, the result would be the same starting at any othe location.
* This problem needs big computational capacity, with each new location, the time to obtain a result increase exponentially. Trying to calculate the optimal cycle  with more than 12 locations could be a nightmare.

The repository contains:

  A) An example of a distance matrix.
  B) First implementation of the algorithm, which allow the user to see all the posibilities with their distances.
  C) Second implementation of the algorithm, which do not show all the options, but is quite more efficient.
