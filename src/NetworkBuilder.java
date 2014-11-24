import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NetworkBuilder {
	
	public static final double ln2 = Math.log(2);

	public static void main(String[] args) {
		List<Node> network = buildNetwork(255);
		System.out.println("net built");
		Packet p = new Packet();
		int source = 0b0;
		p.destination = 0x7f;
		p.ttl = Node.bitCount((byte) (p.destination ^ source));
		System.out.println ("sending from " + source + " to " + p.destination);
		network.get(source).recieve(p);
	}
	
	public static List<Node> buildNetwork(int size){
		if(size > Byte.MAX_VALUE + Math.abs(Byte.MIN_VALUE))
			throw new RuntimeException("NO, stop trying to make stuff bigger than an unsigned byte");
		//int degree = (int) Math.ceil( Math.log(size)/ln2);
		ArrayList<Node> network = new ArrayList<Node>();
		for(int i = 0; i < size; i++){
			network.add(new Node(i));
			//System.out.println(i);
		}
		Random r = new Random(9);
		for(Node n : network){
			for(int d = 0b1; d < size; d = d << 0b1){
				//try{
				if((d^n.number) < size){
					Edge e = network.get((d^n.number)).getEdge(n.number);
					if(e != null){
						int cost = e.cost;
						e = new Edge();
						e.target = network.get(d ^ n.number);
						e.cost = cost;
						n.edges.add(e);
					}else{	
						e = new Edge();
						e.target = network.get(d ^ n.number);
						e.cost = r.nextInt(10) + 1;
						n.edges.add(e);
					}
				}
				//}catch(IndexOutOfBoundsException e){
					//e.printStackTrace();
				//}
			}
		}
		return network;
	}

}
