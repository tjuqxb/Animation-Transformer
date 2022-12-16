package model.intervallist;

/**
 * This class represents a concrete element in IntervalList.
 */
public class SimpleElement<K, V> implements Element<K, V> {
  private K key;
  private V val;
  
  /**
   * The constructor of simple element.
   * 
   * @param key the key
   * @param val the value
   */
  public SimpleElement(K key, V val) {
    this.key = key;
    this.val = val;
  }
  
  @Override
  public K getKey() {
    return this.key;
  }
  
  @Override
  public V getVal() {
    return this.val;
  }
  
  @Override
  public int hashCode() {
    return this.getKey().hashCode() * 37 + this.getVal().hashCode();
  }
  
  @Override
  public boolean equals(Object other) {
    if (! (other instanceof SimpleElement<?, ?>)) {
      return false;
    }
    SimpleElement<K, V> otherEle = (SimpleElement<K, V>)other;
    return otherEle.getKey().equals(key) && otherEle.getVal().equals(val);
  }
}