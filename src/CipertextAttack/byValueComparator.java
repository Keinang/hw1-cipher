package CipertextAttack;

import java.util.*;

public class byValueComparator implements Comparator<Object> {
  Map<String,Integer> base_map;
	
  public byValueComparator(Map<String,Integer> base_map) {
    this.base_map = base_map;
  }
	
  public int compare(Object arg0, Object arg1) {
    if(!base_map.containsKey(arg0) || !base_map.containsKey(arg1)) {
      return 0;
    }
	
    if(base_map.get(arg0) < base_map.get(arg1)) {
      return 1;
    } else if(base_map.get(arg0) == base_map.get(arg1)) {
      return 0;
    } else {
      return -1;
    }
  }
}
