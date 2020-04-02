package pl.edu.agh.dronka.shop.model.provider;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.edu.agh.dronka.shop.model.Category;
import pl.edu.agh.dronka.shop.model.Index;
import pl.edu.agh.dronka.shop.model.ItemTypes.*;
import pl.edu.agh.dronka.shop.model.Shop;
import pl.edu.agh.dronka.shop.model.User;

public class ShopProvider {

    public static Shop getExampleShop() {
        Shop shop = new Shop();

        shop.addUser(getExampleUser());

        Index itemsIndex = new Index();

        for (Item item : getExampleItems()) {
            itemsIndex.addItem(item);
        }

        registerExampleCategories(itemsIndex);
        shop.setItemsIndex(itemsIndex);

        return shop;
    }

    public static User getExampleUser() {
        return new User("Jan", "Rejnor");
    }

    public static List<Item> getExampleItems() {
        List<Item> items = new ArrayList<>();

        CSVReader booksReader = new CSVReader("resources/books.csv");
        items.addAll(readItems(booksReader, Category.BOOKS));

        CSVReader electronicsReader = new CSVReader("resources/electronics.csv");
        items.addAll(readItems(electronicsReader, Category.ELECTRONICS));

        CSVReader foodReader = new CSVReader("resources/food.csv");
        items.addAll(readItems(foodReader, Category.FOOD));

        CSVReader musicReader = new CSVReader("resources/music.csv");
        items.addAll(readItems(musicReader, Category.MUSIC));

        CSVReader sportReader = new CSVReader("resources/sport.csv");
        items.addAll(readItems(sportReader, Category.SPORT));

        return items;
    }

    public static void registerExampleCategories(Index index) {
        for (Category category : Category.values()) {
            index.registerCategory(category);
        }
    }

    private static List<Item> readItems(CSVReader reader, Category category) {
        List<Item> items = new ArrayList<>();

        try {
            reader.parse();
            List<String[]> data = reader.getData();

            for (String[] dataLine : data) {

                String name = reader.getValue(dataLine, "Nazwa");
                int price = Integer.parseInt(reader.getValue(dataLine, "Cena"));
                int quantity = Integer.parseInt(reader.getValue(dataLine,
                        "Ilość"));

                boolean isPolish = Boolean.parseBoolean(reader.getValue(
                        dataLine, "Tanie bo polskie"));
                boolean isSecondhand = Boolean.parseBoolean(reader.getValue(
                        dataLine, "Używany"));
                //BOOKS("Książki"), ELECTRONICS("Elektronika"), FOOD("Żywność"), MUSIC("Muzyka"), SPORT("Sport");
                Item item;
                switch (category) {
                    case BOOKS:
                        item = new Books(name, category, price, quantity, (int)
                                Integer.parseInt(reader.getValue(dataLine, "Liczba stron")),
                                Boolean.parseBoolean(reader.getValue(dataLine, "Twarda oprawa")));
                        break;
                    case ELECTRONICS:
                        item = new Electronics(name, category, price, quantity,
                                Boolean.parseBoolean(reader.getValue(dataLine, "Mobilny")),
                                Boolean.parseBoolean(reader.getValue(dataLine, "Gwarancja")));
                        break;
                    case FOOD:
                        String dateString = reader.getValue(dataLine, "Data przydatności");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = formatter.parse(dateString);
                        item = new Food(name, category, price, quantity, date);
                        break;
					case MUSIC:
						item = new Music(name, category, price, quantity,
								reader.getValue(dataLine, "Gatunek muzyczny"),
								Boolean.parseBoolean(reader.getValue(dataLine, "Czy ma wideo")));
                        break;
					case SPORT:
						item = new Sport(name, category, price, quantity);
                        break;

					default:
						throw new IllegalArgumentException("Error parsing data from csv");
                }


                item.setPolish(isPolish);
                item.setSecondhand(isSecondhand);

                items.add(item);

            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return items;
    }

}
