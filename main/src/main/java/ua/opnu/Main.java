package ua.opnu;

import java.util.*;
import java.util.function.*;

public class Main {

    // Predicate: чи число просте
    public static final Predicate<Integer> IS_PRIME = n -> {
        if (n == null || n < 2) return false;
        if (n % 2 == 0) return n == 2;
        for (int d = 3; d * d <= n; d += 2) {
            if (n % d == 0) return false;
        }
        return true;
    };

    // Фільтр для int[]
    public static int[] filterIntArray(int[] input, Predicate<Integer> p) {
        int[] tmp = new int[input.length];
        int k = 0;
        for (int v : input) if (p.test(v)) tmp[k++] = v;
        return Arrays.copyOf(tmp, k);
    }

    // Фільтр для масиву Student[]
    public static Student[] filterStudents(Student[] input, Predicate<Student> p) {
        Student[] tmp = new Student[input.length];
        int k = 0;
        for (Student s : input) if (p.test(s)) tmp[k++] = s;
        return Arrays.copyOf(tmp, k);
    }

    // Фільтр за двома умовами: елемент проходить, якщо задовольняє обом Predicate
    public static <T> T[] filterAnd(T[] input, Predicate<T> p1, Predicate<T> p2, IntFunction<T[]> arrayFactory) {
        List<T> acc = new ArrayList<>();
        for (T t : input) if (p1.test(t) && p2.test(t)) acc.add(t);
        return acc.toArray(arrayFactory.apply(acc.size()));
    }

    // Предикат: студент має >= 1 заборгованість (оцінка < 60)
    public static final Predicate<Student> HAS_DEBT = s -> {
        for (int m : s.getMarks()) if (m < 60) return true;
        return false;
    };

    // Consumer<Student> ПРІЗВИЩЕ + ІМ'Я
    public static final Consumer<Student> PRINT_SURNAME_NAME = s ->
            System.out.println(s.getLastName() + " " + s.getFirstName());

    // forEach для масиву Student
    public static void forEach(Student[] input, Consumer<Student> action) {
        for (Student s : input) action.accept(s);
    }

    // Метод, що приймає Predicate та Consumer для int[]
    public static void doIf(int[] input, Predicate<Integer> cond, Consumer<Integer> action) {
        for (int v : input) if (cond.test(v)) action.accept(v);
    }

    // Обробка int[] через Function<Integer, Integer>
    public static int[] processIntArray(int[] input, Function<Integer, Integer> f) {
        int[] out = new int[input.length];
        for (int i = 0; i < input.length; i++) out[i] = f.apply(input[i]);
        return out;
    }

    // Function: повертає 2^n
    public static final Function<Integer, Integer> POW2 = n -> {
        if (n == null) return 0;
        if (n < 0) throw new IllegalArgumentException("n має бути невід’ємним");
        return 1 << n;
    };

    // stringify() для чисел від 0 до 9
    public static String[] stringify(int[] input, Function<Integer, String> f) {
        String[] out = new String[input.length];
        for (int i = 0; i < input.length; i++) out[i] = f.apply(input[i]);
        return out;
    }

    public static final Function<Integer, String> DIGIT_UA = d -> {
        switch (d) {
            case 0: return "нуль";
            case 1: return "один";
            case 2: return "два";
            case 3: return "три";
            case 4: return "чотири";
            case 5: return "п’ять";
            case 6: return "шість";
            case 7: return "сім";
            case 8: return "вісім";
            case 9: return "дев’ять";
            default: throw new IllegalArgumentException("Число не належить діапазону [0..9]");
        }
    };

    /* ===================================== MAIN ===================================== */
    static void main(String[] args) {
        int[] test = {1,2,3,4,5,6,7,8,9,10};

        // Завдання 1: прості числа
        System.out.println("Прості числа з test: " + Arrays.toString(
                filterIntArray(test, IS_PRIME)));

        // Завдання 2: Student + фільтрація за боргами
        Student[] group = {
                new Student("Іван", "Петренко", "AI-244", new int[]{95, 81, 74, 100}),
                new Student("Олена", "Сидоренко", "AI-244", new int[]{59, 60, 88, 91}),
                new Student("Марія", "Коваль", "AI-245", new int[]{60, 60, 60, 60}),
                new Student("Андрій", "Мельник", "AI-245", new int[]{45, 77, 83, 90})
        };
        Student[] withDebt = filterStudents(group, HAS_DEBT);
        System.out.println("Студенти з заборгованостями: " + Arrays.toString(withDebt));

        // Завдання 3: фільтр за двома умовами
        Predicate<Student> inGroupAI244 = s -> "AI-244".equals(s.getGroup());
        Predicate<Student> noDebt = HAS_DEBT.negate();
        Student[] okAI244 = filterAnd(group, inGroupAI244, noDebt, Student[]::new);
        System.out.println("AI-244 без боргів: " + Arrays.toString(okAI244));

        // Завдання 4: Consumer<Student> ПРІЗВИЩЕ + ІМ'Я + forEach()
        System.out.println("ПРІЗВИЩЕ + ІМ'Я для всіх студентів:");
        forEach(group, PRINT_SURNAME_NAME);

        // Завдання 5: Метод, що приймає Predicate та Consumer
        System.out.println("Виводимо лише парні з test:");
        doIf(test,
                x -> x % 2 == 0,
                x -> System.out.print(x + " "));
        System.out.println();

        System.out.println("Виводимо лише кратні 3 з test:");
        doIf(test,
                x -> x % 3 == 0,
                x -> System.out.print(x + " "));
        System.out.println();

        // Завдання 6: Function<Integer,Integer> -> 2^n
        int[] n10 = {0,1,2,3,4,5,6,7,8,9};
        int[] pow2 = processIntArray(n10, POW2);
        System.out.println("2^n для n=0..9: " + Arrays.toString(pow2));

        // Завдання 7: stringify() + Function<Integer,String> для 0..9
        String[] words = stringify(n10, DIGIT_UA);
        System.out.println("Цифри словами: " + Arrays.toString(words));
    }
}
