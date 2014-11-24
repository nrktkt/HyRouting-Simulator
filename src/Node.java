import java.util.ArrayList;
import java.util.List;


public class Node {
	
	public List<Edge> edges;
	public int number;
	
	public Node(int number){
		if(number > Byte.MAX_VALUE + Math.abs(Byte.MIN_VALUE))
			throw new RuntimeException("NO, stop trying to make stuff bigger than an unsigned byte");
		this.number = number;
		edges = new ArrayList<Edge>();
	}
	
	public Edge getEdge(int target){
		for(Edge e : edges){
			if(e.target.number == target)
				return e;
		}
		return null;
	}
	
	public void recieve(Packet p){
		
		System.out.println("packet: " + p + " arrived at node " + number);
		if(p.destination == number){
			System.out.println("packet arrived at dest");
			return;
		}
		if(p.ttl == 0)
			throw new RuntimeException("packet timed out");
		int shortestDistance = Integer.MAX_VALUE;
		int lowestCost = Integer.MAX_VALUE;
		List<Edge> shortestEdges = new ArrayList<Edge>();
		for(Edge e : edges){
			int d = bitCount((byte) (e.target.number ^ p.destination));
			if(d < shortestDistance)
				shortestDistance = d;
		}
		for(Edge e : edges){
			int d = bitCount((byte) (e.target.number ^ p.destination));
			if(d == shortestDistance && e.cost < lowestCost){
				lowestCost = e.cost;
				shortestEdges.add(e);
			}
		}
		for(Edge e : shortestEdges){
			if(e.cost == lowestCost){
				p.ttl--;
				p.cost += e.cost;
				e.target.recieve(p);
				return;
			}
		}
		
		
	}
	
	public static int bitCount(byte i) {
		// HD, Figure 5-2
		i = (byte) (i - ((i >>> 1) & 0x55555555));
		i = (byte) ((i & 0x33333333) + ((i >>> 2) & 0x33333333));
		i = (byte) ((i + (i >>> 4)) & 0x0f0f0f0f);
		i = (byte) (i + (i >>> 8));
		i = (byte) (i + (i >>> 16));
		return i & 0x3f;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Node [edges=" + edges + ", number=" + number + "]";
	}
	

}
