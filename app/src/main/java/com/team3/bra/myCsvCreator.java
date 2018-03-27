package com.team3.bra;

/**
 * A csv creator class
 */

public class myCsvCreator
{
    String csv;
    /**
     * Constructor of the object
     */
    public myCsvCreator(){
        csv="";
    }
    /**
     * Add a value to the csv
     * @param value the value to be added
     */
    public void addValue(String value) {
        csv+=value;
        addSpace(1);
    }
    /**
     * Add a number of spaces to the csv
     * @param num the number of spaces to be added
     */
    public void addSpace(int num){
        for (int i=0;i<num;i++)
            csv+=',';
    }
    /**
     * Change a number of lines to the csv
     * @param num the number of lines to be changed
     */
    public void changeLines(int num){
        for (int i=0;i<num;i++)
            csv+='\n';
    }

    @Override
    public String toString() {
        return csv;
    }
}