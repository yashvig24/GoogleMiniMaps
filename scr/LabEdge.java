package hw5;

/**
 * <b>LabEdge<b/> represents a mutable comparable outgoing edge landing on a destination node and 
 * having label for itself. 
 * <p>
 * @specfield dest: Generic type T // destination of the edge
 * @specfield label : Generic and Comparable type L // label of the edge
 * @author Yash Vig
 */
public class LabEdge<T, L extends Comparable<L>> implements Comparable<LabEdge<T, L>> {
	
	// Rep Invariant: 
	// dest != null && label != null
	
	// Abstract Function: 
	// AF(l) = an edge 'l' which is labeled and has a destination
	// 		   but no origin such that
	//		   l.destination = dest
	//         l.label = label;
	

	public T dest; // destination of this edge
	public L label; // label of this edge
	
	/**
	 * Creates a new labeled edge
	 * 
	 * @param dest : The destination on which the labeled edge falls
	 * @param label : The label of the labeled edge
	 * @requires dest != null and label != null
	 * @effects constructs a labeled edge with destination dest
	 * and with label label
	 */
	public LabEdge(T dest, L label) {
		if(dest == null) {
			throw new IllegalArgumentException("Argument dest " + dest + " cannot be null");
		}
		
		if(label == null) {
			throw new IllegalArgumentException("Argument label " + label + " cannot be null");
		}
		
		this.dest = dest;
		this.label = label;
		checkRep();
	}
	
	/**
	 * returns the destination of the edge
	 * 
	 * @return destination of this edge
	 */
	public T getDest() {
		checkRep();
		return dest;
	}

	/**
	 * returns the label of the edge
	 * 
	 * @return label of this edge
	 */
	public L getLabel() {
		checkRep();
		return label;
	}
	
	/**
	 * returns the string representation of the edge
	 * 
	 * @return string representation of this edge
	 */
	@Override
	public String toString() {
		checkRep();
		return (dest.toString() + "(" + label.toString() + ")");
	}
 	
	/**
	 * returns the label of the edge
	 * 
	 * @return label of this edge
	 */
	@Override
	public boolean equals(/*@Nullable*/Object o) {
		if(!(o instanceof LabEdge)) {
			return false;
		}
		LabEdge<?, ?> l = (LabEdge<?, ?>) o;
		checkRep();
		return dest.equals(l.getDest()) && label.equals(l.getLabel());
	}
	
	/**
	 * compares the current object to another object, passed as a parameter
	 * and returns a positive integer if this object is greater than other object,
	 * returns a negative integer if other object is greater than this object,
	 * returns 0 if they both are equal.
	 * 
	 * @param other: object to be compared with this object
	 * @return positive int , negative int or 0 if other object is
	 * less than, greater than or equal to this object respectively
	 */
	public int compareTo(LabEdge<T, L> other) {
		checkRep();
		if (!(label.equals(other.label))) {
			return label.compareTo(other.label);
		}
		if (!(dest.equals(other.dest))) {
			return dest.hashCode() - other.dest.hashCode();
		}
		return 0;
	}
	
	/**
	 * returns a hash code for this edge
	 * 
	 * @return hash code for this edge
	 */
	@Override
	public int hashCode() {
		checkRep();
		return dest.hashCode() + label.hashCode();
	}
	
	/**
	 * check if representation invariant is true
	 */
	private void checkRep() {
		if (dest == null) {
			throw new RuntimeException("destination cannot be null");
		}
		
		if (label == null) {
			throw new RuntimeException("label cannot be null");
		}
	}

}
