package com.marcosdiez.extratocartao.datamodel;

import com.orm.SugarRecord;

/**
 * Created by Marcos on 2015-08-02.
 */
public class Card extends SugarRecord<Card> {
    Bank bank;
    String name;

    public Card() {
    }

    public Card(String name, Bank bank) {
        this.name = name;
        this.bank = bank;
    }

    public static Card getOrCreate(String name, Bank theBank) {

        for (Card c : Card.find(Card.class, "name = ? and bank = ?", name, theBank.getId().toString())) {
            return c;
        }
        // if we are here, no Card were found.
        Card c = new Card(name, theBank);
        c.save();
        return c;
    }


    public Bank getBank() {
        return bank;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String toShortString(){
        return getInitials(name);
    }

    private static String getInitials(String input){
        // returns the initials of a string with more then 5 chars, the whole string otherwise

        if(input == null){
            return input;
        }
        int len = input.length();
        if(len < 5){
            return input;
        }
        String output = "";
        for(int i = -1 ; i < len ; i++ ){
            if(i == -1 || input.charAt(i) == ' '){
                if(i+1 < len){
                    char nextChar = input.charAt(i+1);
                    if(nextChar != ' '){
                        output += nextChar;
                    }
                }
            }
        }
        return output;
    }
}
