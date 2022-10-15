package com.rahul.functionalprogramming;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bouncycastle.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
//Antoine de Saint-Exupéry: “Perfection is achieved not when there is nothing more to add, but when there is nothing left to take away.”

/**
 * HOW and What
 * How -> is implementation
 * What -> is use the implementation and not need to know how it implemented.
 * These candidate lambda expressions are much like Tom Smykowski, in the movie Office Space,a whose job is to
 * “take specifications from the customers and bring them down to the software engineers.”
 * For this reason, I call the refactoring of lambdas to method references the office-space pattern
 */
public class FunctionalProgrammingTest {

    private Boolean findCity(String name) {
        List<String> cities = List.of("Delhi", "UP", "MP");
        for (var city :
                cities) {
            if (city.equals(name))
                return true;
        }

        return false;
    }

    @Test
    @DisplayName("Imperative Style City Found")
    void imperativeStyleCityFound() {
        Assertions.assertEquals(true, findCity("Delhi"));
        Assertions.assertEquals(false, findCity("Delhi1"));
    }

    @Test
    @DisplayName("DeclarativeStyleCityFound")
    void declarativeStyleCityFound() {
        List<String> cities = List.of("Delhi", "UP", "MP");
        Assertions.assertTrue(cities.contains("UP"));
        Assertions.assertFalse(cities.contains("U1P"));
    }


    private BigDecimal totalPriceGreaterThan20Discounted10() {
         final List<BigDecimal> prices = Arrays.asList(
                new BigDecimal("10"), new BigDecimal("30"),
                new BigDecimal("17"), new BigDecimal("20"),
                new BigDecimal("15"), new BigDecimal("18"),
                new BigDecimal("45"), new BigDecimal("12"));

       // prices.add(new BigDecimal(100)); not able to add more wll throw UnsupportedOperationException
        BigDecimal totalOfDiscountedPrices = BigDecimal.ZERO;
        for (var price :
                prices) {
            if (price.compareTo(BigDecimal.valueOf(20)) > 0) {
                totalOfDiscountedPrices = totalOfDiscountedPrices.add(price.multiply(BigDecimal.valueOf(0.9)));
            }
        }

        return totalOfDiscountedPrices;
    }

    @Test
    @DisplayName("ImperativeStyleTotalPriceDiscounted")
    void imperativeStyleTotalPriceDiscounted() {
        assertEquals(BigDecimal.valueOf(67.5), totalPriceGreaterThan20Discounted10());
    }

    @Test
    @DisplayName("DeclarativeStyleDiscountedPrice")
    void declarativeStyleDiscountedPrice() {
        final List<BigDecimal> prices = Arrays.asList(
                new BigDecimal("10"), new BigDecimal("30"),
                new BigDecimal("17"), new BigDecimal("20"),
                new BigDecimal("15"), new BigDecimal("18"),
                new BigDecimal("45"), new BigDecimal("12"));

        assertEquals(BigDecimal.valueOf(67.5), prices.stream()
                .filter(price -> price.compareTo(BigDecimal.valueOf(20)) > 0)
                .map(price -> price.multiply(BigDecimal.valueOf(0.9)))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }


    @Test
    public void iteratingListImmutableCollectionOfNameList() {
        final List<String> friends =
                Arrays.asList("Brian", "Nate"/*, "Neal", "Raju", "Sara", "Scott"*/);
        // friends.add("r"); throw (UnsupportedOperationException)
        // old style
        for (String friend : friends) {
            System.out.println("for: " + friend);
        }

        //alternative: Under the hood this form of iteration uses the Iterator interface
        // and calls into its hasNext() and next() methods.
        // both define how(implementation) and what(intention) to do
        for (String name : friends) {
            System.out.println("foreach :" + name);
        }
        //As for the benefit, we went from specifying how to iterate to focusing on
        // what we want to do for each element. It is more verbose.
        //This version has a limitation, however. Once the forEach method starts,
        // unlike in the other two versions, we can’t break out of the iteration
        friends.forEach(new Consumer<String>() {
            //anonymous inner class syntax.
            @Override
            public void accept(String s) {
                System.out.println("inside consumer foreach consumer: " + s);
            }
        });

        //using lambda.
        //This version has a limitation, however. Once the forEach method starts,
        //unlike in the other two versions, we can’t break out of the iteration
        //when we use it we’re able to focus our attention on what we want to achieve for each element rather
        // than how to sequence through the iteration—it’s declarative
        friends.forEach((final String name) -> System.out.println("inside lambda foreach consumer: " + name));

        // inferred parameters are non-final
        friends.forEach(name -> System.out.println("inside lambda foreach consumer: " + name));

        //a method reference example
        friends.forEach(System.out::println);

//        List<String> friendsViaCollections = Collections
//                .unmodifiableList(Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott"));
    }

    @Test
    @DisplayName("Transforming List")
    void transformingList() {
        final List<String> friends = Arrays.asList("rahul", "navi");

        System.out.println("--- imperative old for loop ----");
        List<String> uppercaseFriends = new ArrayList<>();
        for (int i=0; i < friends.size(); i++) {
            uppercaseFriends.add(friends.get(i).toUpperCase(Locale.ROOT));
        }
        uppercaseFriends.forEach(System.out::println);

        System.out.println("--- imperative  for loop as foreach ----");
        uppercaseFriends = new ArrayList<>();
        for (var friend:
             friends) {
            uppercaseFriends.add(friend.toUpperCase(Locale.ROOT));
        }
        uppercaseFriends.forEach(System.out::println);

        System.out.println("--- List foreach function  ----");
        // mutable we are adding objects and using empty list
        final List<String> declarativeUppercaseFriends = new ArrayList<>();
        friends.forEach(friend -> declarativeUppercaseFriends.add(friend.toUpperCase(Locale.ROOT)));
        declarativeUppercaseFriends.forEach(System.out::println);

        System.out.println("--- Stream  ----");
        //The versions using the lambda expressions have no explicit mutation; they’re concise
        /*
        The map() method is quite unlike the forEach() method, which simply runs the block in the context of each element in the collection.
        In addition, the map() method collects the result of running the lambda expression and returns the result collection.
        The map() method is quite useful to map or transform an input collection into a new output collection.
        This method will ensure that the same number of elements exists in the input and the output sequence.
        However, element types in the input don’t have to match the element types in the output collection
         */
        friends.stream()
                .map(Strings::toUpperCase)
                .forEach(System.out::println);



    }

    final List<String> friends = Arrays.asList("Rahul", "Navi", "Kiaan");

    @Test
    @DisplayName("Finding Elements")
    void findingElements() {

        //Imperative
        final List<String> startsWithN = new ArrayList<>();
        for(String name : friends) {
            if(name.startsWith("N")) {
                startsWithN.add(name);
            }
        }

        assertEquals(Arrays.asList("navi"), startsWithN);
        startsWithN.forEach(System.out::println);

        Predicate<String> predicateStartsWithN = name -> name.startsWith("N");
        //Declarative
        friends.stream()
                //The filter() method returns an iterator just like the map() method does
                //the elements in the result collection that filter() returned are a subset of the elements in the input collection.
                .filter(predicateStartsWithN)
                .collect(toList())
                .forEach(System.out::println);

    }

    @Test
    @DisplayName("Removing Duplication by Lexical scoping")
    void removingDuplicationByLexicalScoping() {
        final Predicate<String> startsWithN = name -> name.startsWith("N");

        final long countFriendsStartN = friends.stream()
                .filter(startsWithN).count();

        assertEquals(1, countFriendsStartN);

        final Predicate<String> startsWithB = name -> name.startsWith("B");
        final long countFriendsStartB = friends.stream()
                        .filter(startsWithB).count();

        assertEquals(0, countFriendsStartB);

        final long countFromFunctionFriendsStartN = friends.stream()
                .filter(checkIfStartsWith("N")).count();
        assertEquals(1, countFromFunctionFriendsStartN);

        final long countFromFunctionFriendsStartB = friends.stream()
                .filter(checkIfStartsWith("B")).count();
        assertEquals(0, countFromFunctionFriendsStartB);

        assertEquals(1, friends.stream()
                .filter(startsWithLetter.apply("R"))
                .count());
    }

    /**
     * Lexical scoping: Java reaches over to the scope of the definition of this lambda expression
     * and finds the variable letter in that scope.
     * This is called lexical scoping.
     * Lexical scoping is a powerful technique that lets us cache values provided in one context for
     * use later in another context.
     * Since this lambda expression closes over the scope of its definition,
     * it’s also referred to as a closure.
     * @param letter
     * @return
     */
    public static Predicate<String>     checkIfStartsWith(final String letter)
    {
        //name is: it’s the parameter passed to this lambda expression
        return name -> name.startsWith(letter);
    }

    /**
     * In the preceding (smelly) example we used a static method, but we don’t want to
     * pollute the class with static methods to cache each variable in the future.
     * It would be nice to narrow the function’s scope to where it’s needed.
     * We can do that using a Function interface
     */
    public Function<String, Predicate<String>>  startsWithLetter =  letter -> name -> name.startsWith(letter);


    @Test
    @DisplayName("Picking An Element imperative style")
    void pickingAnElementImperativeStyle() {
        /**
         * This method’s odor can easily compete with passing garbage trucks.
         * We first created a foundName variable and initialized it to null—that’s the source of our first bad smell.
         * This will force a null check,
         */
        String foundName = null;
        for (var friend:friends
             ) {
            if (friend.startsWith("R")) {
                 foundName = friend;
            }
        }
        assertEquals("Rahul", foundName);
        System.out.print(String.format("A name starting with R:  "));
        if(foundName != null) { System.out.println(foundName);
        } else {
            System.out.println("No name found");
        }
    }

    @Test
    @DisplayName("Picking An Element Declarative style")
    void pickingAnElementDeclarativeStyle() {
        assertEquals("Rahul", friends.stream()
                .filter(startsWithLetter.apply("R"))
                .findFirst().orElse("no found name"));

    }

    @Test
    @DisplayName("Reducing Collection to Single value")
    void reducingCollectionToSingleValue() {
        var friends = List.of("rahul","nijhawan", "rohit-nijhawan", "Nivedita sen");
        System.out.println("friends = " + friends);
        System.out.println("total characters in friends = " +friends.stream()
                .mapToInt(name -> name.length())
                .sum());

        /**
         * the reduce() method to compare two elements against each other and pass along the
         * result for further comparison with the remaining elements in the collection.
         * Much like the other higher-order functions on collections.
         * it carries forward the result of the computation that the lambda expression returned.
         *
         * the reduce() method iterates over the collection.
         *
         * The lambda expression we’re passing to the reduce() method takes two parameters, name1 and name2,
         * and returns one of them based on the length. The reduce() method has no clue about our specific intent.
         * That concern is separated from this method into the lambda expression that we pass to it— this is a lightweight
         * application of the strategy pattern.
         *
         * This lambda expression conforms to the interface of an apply() method of a JDK functional interface named BinaryOperator.
         * This is the type of the parameter the reduce() method receives.
         *
         * As the reduce() method iterated through the collection, it called the lambda expression first,
         * with the first two elements in the list. The result from the lambda expression is used for the subsequent call.
         * In the second call name1 is bound to the result from the previous call to the lambda expression,
         * and name2 is bound to the third element in the collection.
         *
         * The result from the final call is returned as the result of the reduce() method call.
         */
        final Optional<String> aLongName = friends.stream()
                .reduce((name1, name2) -> {
                          /*  System.out.println("name1 = " + name1);
                            System.out.println("name2 = " + name2);*/
                            return name1.length() >= name2.length() ? name1 : name2;
                        }
                        );
        aLongName.ifPresent(System.out::println);
        System.out.println("longest name in friend list = " + aLongName.orElse("no longest name found"));

        /**
         *  If the list had only one element, then reduce() would return that element and the
         *  lambda expression we pass would not be invoked
         */
        assertEquals("rahuul", List.of("rahuul" )
                .stream()
                        .reduce((name1, name2) -> {
                            System.out.println("list has one element and it will not invoke lambda.");
                            return name1.length() >= name2.length() ? name1 : name2;
                        }).orElse(""));



        assertEquals(39, friends.stream()
                .map(name -> name.length())
                .reduce((len1, len2) ->  len1 + len2).orElse(0));

        /**
         * If we want to set a default or a base value, we can pass that value as an
         * extra parameter to an overloaded variation of the reduce() method.
         * For example, if the shortest name we want to pick is Kiaan, we can pass that
         * to the reduce() method, like so
         *
         * This version of reduce() does not return an Optional since if the collection is empty,
         * the default will be returned; there’s no concern of an absent or nonexistent value.
         */
        String kiaanOrLonger = friends.stream()
                .reduce("Kiaan Nijhawan", (name1, name2) -> {
                    System.out.println("extra parameter name1 = " + name1);
                    System.out.println("extra parameter name2 = " + name2);

                    return name1.length() >= name2.length() ? name1 : name2;
                });
        System.out.println("kiaanOrLonger = " + kiaanOrLonger);

    }


    @Test
    @DisplayName("String Join Function")
    void stringJoinFunction () {

        // new for loop
        for (var friend:friends
             ) {
            System.out.print( friend + ",");
        }
        System.out.println("");

        //to remove comma
        for(int i = 0; i < friends.size() - 1; i++) {
            System.out.print(friends.get(i) + ", ");
        }
        if(friends.size() > 0) System.out.println(friends.get(friends.size() - 1));

        /**
         * instead of above we can use string.join()
         */
        //reduce operation.
        System.out.println("--- joinFriends = " + String.join(",", friends));

        StringJoiner stringJoiner = new StringJoiner(",");
        stringJoiner.add("ra");
        stringJoiner.add("ka");

        System.out.println("--- stringJoiner = " + stringJoiner);

        /**
         *The collect() method does the reduction but delegates the actual implementation or target to a collector.

         * joining( return new CollectorImpl<>(
         *                 () -> new StringJoiner(delimiter, prefix, suffix),
         *                 StringJoiner::add, StringJoiner::merge,
         *                 StringJoiner::toString, CH_NOID);)
         *
         *  CollectorImpl(Supplier<A> supplier,
         *                       BiConsumer<A, T> accumulator,
         *                       BinaryOperator<A> combiner,
         *                       Function<A,R> finisher,
         *                       Set<Characteristics> characteristics) {
         *             this.supplier = supplier;
         *             this.accumulator = accumulator;
         *             this.combiner = combiner;
         *             this.finisher = finisher;
         *             this.characteristics = characteristics;
         *         }
         *
         *  A collector acts as a sink object to receive elements passed by the collect() method and
         *  stores it in a desired format: ArrayList, String, and so on.
         */
        System.out.println("collect = " + friends.stream()
                .collect(Collectors.joining("/")));

        System.out.println("--- join by reduce ---");
        friends.stream()
                .reduce((f1, f2)->(new StringJoiner(",")).add(f1).add(f2).toString())
                .ifPresent(System.out::println);
    }


    @Test
    @DisplayName("IteratingStrings")
    void iteratingStrings() {

        /**
         * The chars() method is a new one in the String class from the CharSequence interface.
         *
         * We get direct read access to the characters in the String within the iterator
         *
         *  the chars() method returns a stream of Integers repre- senting the letters instead of a stream of Characters
         */
        String s = "xvb76nz";

        s.chars().forEach(System.out::println);
        System.out.println(" = ");

        System.out.println("Character.valueOf((char) value) = " + Character.valueOf('s'));
        System.out.println(" = ");

        s.chars()
                .mapToObj(value -> Character.valueOf((char) value))
                .forEach(System.out::println);
        System.out.println(" = ");
        s.chars()
                .filter(Character::isDigit)
                .mapToObj(value -> Character.valueOf((char) value))
                .forEach(System.out::println);


        Integer i = 0;
        Boolean b = i > 0;

        var instanceV = new StaticRef();
        instanceV.a = "rah";
       List.of(instanceV).stream()
               .map(StaticRef::upper)
                .forEach( System.out::println   );

       /* won't compile as  there is ambiguity call between toString(StaticRef) and inherit toString()*/
      /* List.of(instanceV).stream()
                .map(StaticRef::toString);*/
    }

    class StaticRef {
        public String a;
        public  static String upper(StaticRef a) {
            return a.a.toUpperCase(Locale.ROOT);
        }

        /**
         *  If there’s both a matching instance method and a static method,
         *  we’ll get a compilation error due to the reference’s ambiguity.
         *  For example, if we write Double::toString to convert an instance of Double to a String,
         *the compiler would get confused whether to use the public String toString() instance method or
         * the static method public static String toString(double value), both from the Double class.
         * If we run into this, no sweat; we simply switch back to using the appropriate
         * lambda-expression version to move on.
         *
         * If it’s an instance method, then the synthesized method’s parameter becomes the call’s target,
         * like in parameter.toUppercase(); (the exception to this rule is if the target is already specified
         * like in System.out::println). On the other hand, if the method is static,
         * then the parameter to the synthesized method is routed as an argument to this method,
         * like in Character.isDigit(parameter);
         * @param a
         * @return
         */
        public  static String toString(StaticRef a) {
            return a.a;
        }
    }


    @Test
    @DisplayName("ComparatorInterfaceUsingStream")
    public void comparatorInterfaceUsingStream() {
        List<Person> people = Arrays.asList(
                new Person("Nivedita", 37),
                new Person("Kiaan Nijhawan", 5),
                new Person("Gugu Nijhawan", 5),
                new Person("Rahul", 39)
        );


        System.out.println(" ------------------ Sort by Age ------------------- ");
        System.out.println();
        //Comparator is a functional interface
        System.out.println("--- Ascending by age= ");
        // Ascending
        people.stream()
                .sorted((p1, p2) -> p1.ageDifference(p2))
                .toList().forEach(System.out::println);

        System.out.println();
        System.out.println("--- Descending by age= ");
        // Descending
        people.stream()
                .sorted((p1, p2) -> p2.ageDifference(p1))
                .toList().forEach(System.out::println);

        /**
         *The code is fantastically concise, thanks to the method-reference convenience the Java compiler offers.
         * The compiler took the parameters, the two person instances being compared, and made the first the ageDifference()
         * method’s target and the second the parameter. Rather than explicitly connecting these, we let the compiler work
         * a little extra for us. When using this conciseness, we must be careful to ensure that the first parameter is really
         * the intended target of the method referenced and the remaining parameters are its arguments.
         */
        System.out.println();
        System.out.println("Office-space pattern --- Ascending by age= ");
        // Ascending
        people.stream()
                .sorted(Person::ageDifference)
                .toList().forEach(System.out::println);

        Comparator<Person> comparatorAscending = (p1, p2) -> p1.ageDifference(p2);
        /**
         * Under the hood the reversed() creates a comparator that swaps its parameters’ order of comparison.
         * This makes the reversed() method a higher- order method—this function creates and returns another functional expression
         * with no side effect.
         */

        Comparator<Person> comparatorDescending= comparatorAscending.reversed();

        System.out.println();
        System.out.println("comparatorAscending by age= ");
        people.stream()
                .sorted(comparatorAscending)
                .toList().forEach(System.out::println);

        System.out.println();
        System.out.println("comparatorDescending by age= ");
        people.stream()
                .sorted(comparatorDescending)
                .toList().forEach(System.out::println);


        System.out.println();
        System.out.println(" ------------------ Sort by Name ------------------- ");
        System.out.println(" Ascending by name= ");
        people.stream()
                .sorted(Comparator.comparing(Person::getName))
                .toList().forEach(System.out::println);

        System.out.println(" Descending by name= ");
        people.stream()
                .sorted((p1, p2) -> p2.getName().compareTo(p1.getName()))
                .toList().forEach(System.out::println);

        System.out.println();
        System.out.println("--- Youngest person by age= ");
        people.stream()
                .min(Person::ageDifference)
                .ifPresent(System.out::println);

        System.out.println();
        System.out.println("--- Oldest person by age= ");
        people.stream()
                .max(Person::ageDifference)
                .ifPresent(System.out::println);


        /**
         * The comparing() method uses the logic embedded in the provided lambda expression to create a Comparator.
         * In other words, it’s a higher-order function that takes in one function (Function) and returns another (Comparator).
         */
        System.out.println();
        System.out.println("--- Function<Person, String> compareByName by name= ");
        Function<Person, String> compareByName = person -> person.getName();
        people.stream()
                .sorted(Comparator.comparing(compareByName))
                .forEach(System.out::println);

        System.out.println();
        System.out.println("--- Function<Person, String> compareByAge by name= ");
        Function<Person, Integer> compareByAge = person -> person.getAge();
        people.stream()
                .sorted(Comparator.comparing(compareByAge))
                .forEach(System.out::println);


        System.out.println();
        System.out.println("--- Function<Person, String> compare by name then by age= ");
        people.stream()
                .sorted(Comparator.comparing(compareByName).thenComparing(compareByAge))
                .forEach(System.out::println);



    }
    @RequiredArgsConstructor
    @Setter
    @Getter
    @ToString
    class Person {
        private final String name;
        private final int age;

        public int ageDifference(final Person other) {
            return age - other.age;
        }
    }

    @Test
    @DisplayName("Comparator Interface using Anonymous Class")
    void comparatorInterfaceUsingAnonymousClass() {
        List<Person> people = Arrays.asList(
                new Person("Nivedita", 37),
                new Person("Kiaan Nijhawan", 5),
        new Person("Rahul", 39)
        );
        // anonymous class
        Comparator peopleComparator = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof Person p1 && o2 instanceof Person p2) {
                    if (p1.age > p2.age)
                        return -1;
                    else if (p1.age < p2.age) {
                        return 1;
                    }
                }
                return 0;
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        };

        Collections.sort(people,peopleComparator);
        people.stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("collect test")
    void collectTest() {
        /**
         * stream.collect is a reduce operation that’s useful for transforming the collection into another form,
         * often a mutable collection
         *
         * The collect() function, when combined with the utility methods of the Collectors class,
         * provides a wealth of conveniences
         *
         *  mapping(), collectingAndThen(), minBy(), maxBy(), and groupingBy().
         */

        List<Person> people = Arrays.asList(
                new Person("Nivedita", 37),
                new Person("Kiaan Nijhawan", 5),
                new Person("Gugu Nijhawan", 5),
                new Person("Rahul", 39)
        );

        /**
         * The code produced the desired result, but there are a few issues. First, the operation of adding an element
         * into the target collection is pretty low level— imperative rather than declarative.
         * If we decide to make the iteration concurrent, we immediately have to deal with thread-safety concerns—the
         * mutability makes it hard to parallelize. Fortunately, we can easily alleviate these concerns using the collect() method.
         */

        System.out.println("--- use foreach add to list ---");
        List<Person> olderThan20 = new ArrayList<>();
        people.stream()
                .filter(person -> person.getAge() > 20)
                .forEach(person -> olderThan20.add(person));
        System.out.println("People older than 20: " + olderThan20);


        /**
         * The collect() method takes a stream of elements and collects or gathers them into a result container.
         * To do that, the method needs to know three things:
         * 1. How to make a result container (for example, using the ArrayList::new method)
         * 2. How to add a single element to a result container (for example, using the ArrayList::add method)
         * 3. How to merge one result container into another (for example, using the ArrayList::addAll method)
         * The last item may not be necessary for purely sequential operations; the code
         * is designed to work for both sequential and parallel execution.
         */
        System.out.println();
        System.out.println("--- use collect() 3 parameters (supplier, accumulator, and combiner)    ---");
        people.stream()
                .filter(person -> person.getAge() > 20)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll)
                .forEach(System.out::println);

        System.out.println();
        System.out.println("--- use collect() ---");
        people.stream()
                .filter(person -> person.getAge() > 20)
                .collect(toList()).forEach(System.out::println);


        /**
         * Returns a Collector implementing a "group by" operation on input elements of type T, grouping elements according
         * to a classification function, and returning the results in a Map.
         * The classification function maps elements to some key type K. The collector produces a Map<K, List<T>> whose keys
         * are the values resulting from applying the classification function to the input elements, and whose corresponding values
         * are Lists containing the input elements which map to the associated key under the classification function.
         *
         * The groupingBy() method takes a lambda expression or a method reference— called the classifier
         * function that returns the value of the property on which we want to do the grouping.
         */

        System.out.println();
        System.out.println("grouping() ----");
        people.stream()
                .collect(Collectors.groupingBy(Person::getAge))
                .forEach((i, l)-> System.out.printf("%d=>%s\n", i, l.toString()));

        System.out.println();
        System.out.println("grouping() with mapping ----");
        people.stream()
                .collect(Collectors.groupingBy(Person::getAge, Collectors.mapping(Person::getName, toList())))
                .forEach((i, l)-> System.out.printf("%d=>%s\n", i, l));


        System.out.println();
        System.out.println("grouping() with reducing(maxBy) ----");
        people.stream()
                .collect(Collectors.groupingBy(Person::getAge,
                                               Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(person ->person.getAge())))))
                .forEach((i, l)-> System.out.printf("%d=>%s\n", i, l));

    }

    @Test
    void fileFunctions() throws IOException {
        Files.list(Paths.get("."))
                .forEach(path -> System.out.println("path.getFileName() = " + path.getFileName()));
    }
}
