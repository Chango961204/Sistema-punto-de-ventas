/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author AJPDHN
 */
public class TConfiguration {
    private int ID;
    private String TypeMoney;
    private double Interests;

    public TConfiguration() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTypeMoney() {
        return TypeMoney;
    }

    public void setTypeMoney(String TypeMoney) {
        this.TypeMoney = TypeMoney;
    }

    public double getInterests() {
        return Interests;
    }

    public void setInterests(double Interests) {
        this.Interests = Interests;
    }
 
    
    
}
