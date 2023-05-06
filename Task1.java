//Alie Ablaeva
//a.ablaeva@innopolis.university

import java.util.ArrayList;
import java.util.List;

interface IMenu{
    void print();
    String getName();
    double getPrice();
    void setPrice(double price);
}

class CompositeMenu implements IMenu{
    String name;
    double price;
    List<IMenu> menuItems;

    public CompositeMenu(String name) {
        this.name = name;
        menuItems = new ArrayList<>();
        price  = 0;
    }

    public void add(IMenu newItem){
        menuItems.add(newItem);
        price+=newItem.getPrice();
    }

    public void delete(IMenu item){
        menuItems.remove(menuItems.indexOf(item));
        price -= item.getPrice();
    }
    @Override
    public void print() {
        System.out.println(name + " [" + price + "]");
        System.out.println("_______________________");
        for(IMenu items: menuItems){
            items.print();
        }
        System.out.println();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }
}

class MenuItem implements IMenu{
    String name;
    double price;
    double initialPrice;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
        this.initialPrice = price;
    }

    @Override
    public void print() {
        System.out.println(name+", $" + initialPrice);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }
}

abstract class MenuItemDecorator implements IMenu{
    protected IMenu menuItem;
    public MenuItemDecorator(IMenu newMenuItem){
        menuItem = newMenuItem;
    }
    public void setPrice(double price){
        menuItem.setPrice(price);
    }
}

class SpicyDecorator extends MenuItemDecorator{
    public SpicyDecorator(IMenu newMenuItem) {
        super(newMenuItem);
        menuItem.setPrice(menuItem.getPrice()+2);
    }

    public void print(){
        menuItem.print();
        System.out.println("-- This item is spicy (+ $2)");
    }

    @Override
    public String getName() {
        return menuItem.getName();
    }

    @Override
    public double getPrice() {
        return menuItem.getPrice();
    }

    @Override
    public void setPrice(double price) {menuItem.setPrice(price);}
}

class VegetarianDecorator extends MenuItemDecorator{
    public VegetarianDecorator(IMenu newMenuItem) {
        super(newMenuItem);
        double pr = menuItem.getPrice()+4;
        menuItem.setPrice(pr);
    }

    public void print(){
        menuItem.print();
        System.out.println("-- This item is vegeterian (+ $4)");
    }

    @Override
    public String getName() {
        return menuItem.getName();
    }

    @Override
    public double getPrice() {
        return menuItem.getPrice();
    }

    @Override
    public void setPrice(double price) {}
}

public class Main {
    public static void main(String[] args) {
        IMenu bread = new VegetarianDecorator(new MenuItem("Garlic bread", 5.5));
        IMenu wings = new SpicyDecorator(new MenuItem("Chicken wings", 12.5));
        IMenu soup = new VegetarianDecorator(new SpicyDecorator(new MenuItem("Tomato soup", 10.5)));
        IMenu pie = new MenuItem("Pie", 4.5);
        IMenu pieS = new SpicyDecorator(new MenuItem("Pie", 4.5));
        IMenu iceCream = new MenuItem("Ice cream", 3.0);
        CompositeMenu appetizer = new CompositeMenu("Appetizer Menu");
        appetizer.add(bread);
        appetizer.add(wings);
        appetizer.add(soup);

        CompositeMenu dessert = new CompositeMenu("Dessert Menu");
        dessert.add(pie);
        dessert.add(pieS);
        dessert.add(iceCream);

        CompositeMenu main = new CompositeMenu("Main Menu");
        main.add(appetizer);
        main.add(dessert);
        main.print();
    }
}
