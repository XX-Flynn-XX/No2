package ru.aston.homework;

import java.util.*;

class MyHashSet<T> {
    private static final int CAPACITY = 16;
    private LinkedList<T>[] buckets;

    @SuppressWarnings("unchecked")
    public MyHashSet() {
        buckets = new LinkedList[CAPACITY];
        for (int i = 0; i < CAPACITY; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    private int getBucketIndex(T value) {
        return (value.hashCode() & 0x7FFFFFFF) % CAPACITY;
    }

    public void insert(T value) {
        int index = getBucketIndex(value);
        if (!buckets[index].contains(value)) {
            buckets[index].add(value);
        }
    }

    public void remove(T value) {
        int index = getBucketIndex(value);
        buckets[index].remove(value);
    }
}

class MyArrayList<T> {
    private Object[] elements;
    private int size;

    public MyArrayList() {
        elements = new Object[10];
        size = 0;
    }

    public void add(T value) {
        ensureCapacity(size + 1);
        elements[size++] = value;
    }

    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return (T) elements[index];
    }

    public void remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null;
    }

    public void addAll(T[] array) {
        ensureCapacity(size + array.length);
        for (T item : array) {
            elements[size++] = item;
        }
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCap = Math.max(elements.length * 2, minCapacity);
            elements = Arrays.copyOf(elements, newCap);
        }
    }

    public int size() {
        return size;
    }
}

class Book {
    private String title;
    private int pages;
    private int year;

    public Book(String title, int pages, int year) {
        this.title = title;
        this.pages = pages;
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Book{" + "title='" + title + '\'' + ", pages=" + pages + ", year=" + year + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return title.equals(book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}

class Student {
    private String name;
    private List<Book> books;

    public Student(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "Student{" + "name='" + name + '\'' + ", books=" + books + '}';
    }
}

public class Main {
    public static void main(String[] args) {
        Book b1 = new Book("Java Basics", 300, 1999);
        Book b2 = new Book("Advanced Java", 500, 2005);
        Book b3 = new Book("Java Concurrency", 400, 2010);
        Book b4 = new Book("Java Streams", 250, 2015);
        Book b5 = new Book("Design Patterns", 350, 1998);
        Book b6 = new Book("Effective Java", 416, 2008);
        Book b7 = new Book("Clean Code", 464, 2008);
        Book b8 = new Book("Refactoring", 431, 1999);
        Book b9 = new Book("Java Performance", 290, 2021);
        Book b10 = new Book("Java for Dummies", 150, 2001);

        List<Student> students = List.of(
                new Student("Nikita", List.of(b1, b2, b3, b4, b5)),
                new Student("Dima", List.of(b6, b7, b8, b9, b10)),
                new Student("Vika", List.of(b1, b3, b5, b7, b9))
        );

        students.stream()
                .forEach(System.out::println);

        students.stream()
                .map(Student::getBooks)
                .forEach(System.out::println);

        List<Book> allBooks = students.stream()
                .flatMap(s -> s.getBooks().stream())
                .toList();

        List<Book> sortedBooks = allBooks.stream()
                .distinct()
                .sorted(Comparator.comparingInt(Book::getPages))
                .toList();

        List<Book> uniqueBooks = allBooks.stream()
                .distinct()
                .toList();

        List<Book> booksAfter2000 = allBooks.stream()
                .filter(b -> b.getYear() > 2000)
                .toList();

        List<Book> firstThreeBooks = allBooks.stream()
                .limit(3)
                .toList();

        List<Integer> years = allBooks.stream()
                .map(Book::getYear)
                .toList();

        Optional<Book> bookAfter2010 = allBooks.stream()
                .filter(b -> b.getYear() > 2010)
                .findFirst();

        System.out.println("Year of the found book: " + bookAfter2010
                .map(Book::getYear)
                .orElse(-1));
    }
}
