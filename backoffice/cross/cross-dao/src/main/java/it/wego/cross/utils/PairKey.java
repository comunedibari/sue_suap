package it.wego.cross.utils;
import java.util.Objects;

public class PairKey <U,V> {

	protected U key1;
	protected V key2;
	
	public PairKey() {}
	
	public PairKey(U key1, V key2) {
		this.key1 = key1;
		this.key2 = key2;
	}
	
	public U getKey1() {
		return key1;
	}
	
	public void setKey1(U key1) {
		this.key1 = key1;
	}
	
	public V getKey2() {
		return key2;
	}
	
	public void setKey2(V key2) {
		this.key2 = key2;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(key1, key2);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PairKey other = (PairKey) obj;
		return Objects.equals(key1, other.key1) && Objects.equals(key2, other.key2);
	}

	@Override
	public String toString() {
		return "PairKey [key1=" + key1 + ", key2=" + key2 + "]";
	}
}
