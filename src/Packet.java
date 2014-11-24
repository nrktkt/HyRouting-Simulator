
public class Packet {
	
	public int id, ttl;
	public int cost = 0;
	public int destination;
	public String name;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Packet [id=" + id + ", ttl=" + ttl + ", cost=" + cost
				+ ", destination=" + destination + ", name=" + name + "]";
	}


}
