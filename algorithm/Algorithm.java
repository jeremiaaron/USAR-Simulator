package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Algorithm {
	
	private final Map<Integer, List<AlgNode>> vertices;
	
	public Algorithm() {
		this.vertices = new HashMap<Integer, List<AlgNode>>();
	}
	
	public void addAlgNode(Integer i, List<AlgNode> AlgNode) {
		this.vertices.put(i, AlgNode);
	}
	
	public List<Integer> getShortestPath(Integer start, Integer finish) {
		
		final Map<Integer, AlgNode> previous = new HashMap<Integer, AlgNode>();
		final Map<Integer, Integer> dist = new HashMap<Integer, Integer>();
		PriorityQueue<AlgNode> nodes = new PriorityQueue<AlgNode>();
		
		for(Integer AlgNode : vertices.keySet()) {
			
			if (AlgNode == start) {
				dist.put(AlgNode, 0);
				nodes.add(new AlgNode(AlgNode, 0));
			}
			else {
				dist.put(AlgNode, Integer.MAX_VALUE);
				nodes.add(new AlgNode(AlgNode, Integer.MAX_VALUE));
			}
			
			previous.put(AlgNode, null);
		}
		
		while (!nodes.isEmpty()) {
			
			AlgNode smallest = nodes.poll();
			
			if (smallest.getId() == finish) {
				
				final List<Integer> path = new ArrayList<Integer>();
				while (previous.get(smallest.getId()) != null) {
					path.add(smallest.getId());
					smallest = previous.get(smallest.getId());
				}
				
				return path;
			}

			if (dist.get(smallest.getId()) == Integer.MAX_VALUE) {
				break;
			}
						
			for (AlgNode neighbor : vertices.get(smallest.getId())) {
				
				Integer alt = dist.get(smallest.getId()) + neighbor.getDistance();
				
				if (alt < dist.get(neighbor.getId())) {
					dist.put(neighbor.getId(), alt);
					previous.put(neighbor.getId(), smallest);
					
					forloop:
					for(AlgNode n : nodes) {
						if (n.getId() == neighbor.getId()) {
							nodes.remove(n);
							n.setDistance(alt);
							nodes.add(n);
							break forloop;
						}
					}
				}
			}
		}
		
		return new ArrayList<Integer>(dist.keySet());
	}

}
