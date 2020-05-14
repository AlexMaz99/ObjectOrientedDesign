package pl.edu.agh.to.lab4;

import pl.edu.agh.to.lab4.dataProviders.PersonDataProvider;
import pl.edu.agh.to.lab4.dataProviders.PrisonersDatabase;
import pl.edu.agh.to.lab4.dataProviders.StudentDataProvider;
import pl.edu.agh.to.lab4.searching.AgeSearchStrategy;
import pl.edu.agh.to.lab4.searching.CompositeSearchStrategy;
import pl.edu.agh.to.lab4.searching.NameSearchStrategy;

public class Application {

    public static void main(String[] args) {
        Finder suspects = new Finder(new PersonDataProvider().iterator(), new PrisonersDatabase().iterator(), new StudentDataProvider().iterator());
        CompositeSearchStrategy compositeSearchStrategy = new CompositeSearchStrategy();
        compositeSearchStrategy.addStrategy(new AgeSearchStrategy(20));
        compositeSearchStrategy.addStrategy(new NameSearchStrategy("Janusz"));
        suspects.display(compositeSearchStrategy);
    }
}
