import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class Principal {
    final private Map<Integer, List<Pair<Teacher, Boolean>>> allYearsTeachers = Map.ofEntries(
            getIntegerListSimpleImmutableEntry2020(),
            getIntegerListSimpleImmutableEntry2019()
    );

    @NotNull
    private AbstractMap.SimpleImmutableEntry<Integer, List<Pair<Teacher, Boolean>>> getIntegerListSimpleImmutableEntry2019() {
        return new AbstractMap.SimpleImmutableEntry<>(
                2019,
                List.of(
                        new Pair<>(new Teacher(1, "Eduarda"), false),
                        new Pair<>(new Teacher(1, "Abelardo"), false),
                        new Pair<>(new Teacher(1, "Francisca"), false)
                )
        );
    }

    @NotNull
    private AbstractMap.SimpleImmutableEntry<Integer, List<Pair<Teacher, Boolean>>> getIntegerListSimpleImmutableEntry2020() {
        return new AbstractMap.SimpleImmutableEntry<>(
                2020,
                List.of(
                        new Pair<>(new Teacher(1, "Josefina"), true),
                        new Pair<>(new Teacher(1, "Edonisio"), true),
                        new Pair<>(new Teacher(1, "Edufasio"), false)
                )
        );
    }

    private final int yearToCalculate;

    public Principal(int yearToCalculate) {
        this.yearToCalculate = yearToCalculate;
    }
    public float calculateGrades(final List<Pair<Integer, Float>> examsStudents, final boolean hasReachedMinimumClasses) {
        if (!examsStudents.isEmpty() && hasReachedMinimumClasses) {
            boolean hasToIncreaseOneExtraPoint = false;

            for (Map.Entry<Integer, List<Pair<Teacher, Boolean>>> yearlyTeachers : allYearsTeachers.entrySet()) {
                hasToIncreaseOneExtraPoint = asistencias_minimas(hasToIncreaseOneExtraPoint, yearlyTeachers);
            }
            float gradesSum       = 0f;
            int   gradesWeightSum = 0;

            for (Pair<Integer, Float> examGrade : examsStudents) {
                gradesSum = getGradesSum(gradesSum, examGrade);
                gradesWeightSum += examGrade.first();

            }
            return _Condicion(hasToIncreaseOneExtraPoint, gradesSum, gradesWeightSum);
        } else {
            return 0f;
        }
    }

    private boolean asistencias_minimas(boolean hasToIncreaseOneExtraPoint, Map.Entry<Integer, List<Pair<Teacher, Boolean>>> yearlyTeachers) {
        if (!(yearToCalculate != yearlyTeachers.getKey())) {
            List<Pair<Teacher, Boolean>> teachers = yearlyTeachers.getValue();
            for (Pair<Teacher, Boolean> teacher : teachers) {
                if (teacher.second()) {
                    hasToIncreaseOneExtraPoint = true;
                }
            }
        }
        return hasToIncreaseOneExtraPoint;
    }

    private float _Condicion(boolean hasToIncreaseOneExtraPoint, float gradesSum, int gradesWeightSum) {
        if (gradesWeightSum == 100) {
            if (hasToIncreaseOneExtraPoint) {
                return Float.min(10f, gradesSum + 1);
            }
            return gradesSum;
        } else if (gradesWeightSum > 100) {
            return -1f;
        } else {
            return -2f;
        }
    }

    private float getGradesSum(float gradesSum, Pair<Integer, Float> examGrade) {
        gradesSum += (examGrade.first() * examGrade.second() / 100);
        return gradesSum;
    }

    public static void main(String[] args) {
        System.out.println("Hola");
    }

}
