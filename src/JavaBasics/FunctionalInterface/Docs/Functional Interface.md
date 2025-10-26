# Functional Interfaces in Java

## 1. Introduction
A **Functional Interface** is an interface with exactly **one abstract method (SAM - Single Abstract Method)**.  
It forms the foundation for lambda expressions and method references in Java.

---

## 2. Defining a Functional Interface

```java
@FunctionalInterface
interface Printer {
    void print(String message);
}
```

- The `@FunctionalInterface` annotation is optional but recommended.
- Can have **default** and **static** methods alongside the single abstract method.

---

## 3. Built-in Functional Interfaces (java.util.function)

| Category | Interface | Abstract Method | Example |
|-----------|------------|----------------|----------|
| Supplier | `Supplier<T>` | `T get()` | `() -> Math.random()` |
| Consumer | `Consumer<T>` | `void accept(T t)` | `x -> System.out.println(x)` |
| Predicate | `Predicate<T>` | `boolean test(T t)` | `n -> n > 0` |
| Function | `Function<T, R>` | `R apply(T t)` | `s -> s.length()` |
| BiFunction | `BiFunction<T, U, R>` | `R apply(T t, U u)` | `(a,b) -> a+b` |
| UnaryOperator | `UnaryOperator<T>` | `T apply(T t)` | `x -> x*x` |
| BinaryOperator | `BinaryOperator<T>` | `T apply(T t1, T t2)` | `(a,b) -> a*b` |

---

## 4. Custom Functional Interface Example

```java
@FunctionalInterface
interface Calculator {
    int compute(int a, int b);
}

public class Example {
    public static void main(String[] args) {
        Calculator add = (x, y) -> x + y;
        Calculator multiply = (x, y) -> x * y;

        System.out.println(add.compute(3, 5));      // 8
        System.out.println(multiply.compute(3, 5)); // 15
    }
}
```

---

## 5. Combining Functional Interfaces

### Predicate Chaining
```java
Predicate<Integer> isPositive = x -> x > 0;
Predicate<Integer> isEven = x -> x % 2 == 0;
Predicate<Integer> combined = isPositive.and(isEven);
System.out.println(combined.test(4)); // true
```

### Function Chaining
```java
Function<Integer, Integer> doubleIt = x -> x * 2;
Function<Integer, Integer> squareIt = x -> x * x;
Function<Integer, Integer> combinedFunc = doubleIt.andThen(squareIt);
System.out.println(combinedFunc.apply(3)); // 36
```

---

## 6. Use Cases
- Stream API operations (`filter`, `map`, `reduce`)
- Optional API (`ifPresent`, `orElseGet`)
- Executors and concurrency
- Event listeners

---

## 7. Summary Table

| Interface | Use Case | Example |
|------------|-----------|----------|
| Supplier | Provides a value | `() -> new Date()` |
| Consumer | Consumes a value | `x -> System.out.println(x)` |
| Predicate | Tests condition | `x -> x > 10` |
| Function | Transforms data | `x -> x.length()` |
| Operator | Performs operation | `(a,b) -> a+b` |
