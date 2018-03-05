package com.team3.bra;

/**
 * Created by condiom on 05/03/2018.
 */

public class myCsvCreator
{
    String csv;

    public myCsvCreator(){
        csv="";
    }
    public void addValue(String value) {
        csv+=value;
        addSpace(1);
    }
    public void addSpace(int num){
        for (int i=0;i<num;i++)
            csv+=',';
    }
    public void changeLines(int num){
        for (int i=0;i<num;i++)
            csv+='\n';
    }

    @Override
    public String toString() {
        return csv;
    }
}
