package org.example;

import java.util.List;

public class Route{
    private List<Flight> list;
    public Route(List<Flight> list) throws Exception{
        if(list.isEmpty()){
            throw new Exception("U can't buy null ticket");
        }
        for(int i = 0; i < list.size() - 1; i++){
            Flight current = list.get(i);
            Flight next = list.get(i + 1);


            if(!current.getPlaceIn().getCiti().equals(next.getPlaceOut().getCiti())){
                throw new Exception("You can't get flight in another city!");
            }
            if(!current.getTimeIn().isBefore(next.getTimeOut())){
                throw new Exception("You can't get flight after flight is gone!");
            }
        }
        this.list = list;
    }
    public List<Flight> getList() { return list; }


}
