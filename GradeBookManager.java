import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.List;

public class GradeBookManager {

    // =========================
    // Program Constants
    // =========================

    private static final int MIN_GRADE = 0;
    private static final int MAX_GRADE = 100;
    private static final int PASSING_MARK = 60;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        displayMenu(
                initializeSections(scanner),
                scanner
        );

        scanner.close();
    }

    // -----------------------------
    // Input Validation Methods
    // -----------------------------

    /**
     * Prompts the user until one of the valid options in the provided
     * list is entered.
     *
     * @param prompt      message displayed to the user
     * @param optionsList list of valid input options
     * @param scanner     Scanner used to read user input
     * @return a validated option from the provided list
     */
    private static String getListValidatedInput(
            String prompt,
            List<String> optionsList,
            Scanner scanner
    ) {

        String input;

        while (true) {
            System.out.print(prompt);

            input = scanner.nextLine().trim();

            for (String option : optionsList) {
                if (input.equalsIgnoreCase(option)) {
                    return option;
                }
            }

            System.out.printf(
                    "Invalid input. Please enter one of the available options (e.g., %s).%n%n",
                    optionsList.getFirst()
            );
        }
    }

    /**
     * Prompts the user until a valid whole number greater than or equal to
     * the specified minimum value is entered.
     *
     * @param prompt   message displayed to the user
     * @param minValue minimum acceptable value
     * @param scanner  Scanner used to read user input
     * @return a validated integer greater than or equal to minValue
     */
    private static int getMinValidatedInput(
            String prompt,
            int minValue,
            Scanner scanner
    ) {

        int input;

        while (true) {
            try {
                System.out.print(prompt);

                input = scanner.nextInt();
                scanner.nextLine();

                if (input >= minValue) {
                    return input;
                }
                System.out.printf(
                        "Value must be at least %d.%n%n",
                        minValue
                );

            } catch (InputMismatchException e) {
                System.out.println(
                        "Invalid input. Please enter a whole number.\n"
                );
                scanner.nextLine();

            } catch (Exception e) {
                System.out.println(
                        "An unexpected error occurred. Please try again.\n"
                );
                scanner.nextLine();
            }
        }
    }

    /**
     * Prompts the user until a valid whole number within the
     * specified range is entered.
     *
     * @param prompt   message displayed to the user
     * @param minValue minimum acceptable value
     * @param maxValue maximum acceptable value
     * @param scanner  Scanner used to read user input
     * @return a validated integer within the specified range
     */
    private static int getRangeValidatedInput(
            String prompt,
            int minValue,
            int maxValue,
            Scanner scanner
    ) {

        int input;

        while (true) {
            try {
                System.out.print(prompt);

                input = scanner.nextInt();
                scanner.nextLine();

                if (input >= minValue && input <= maxValue) {
                    return input;
                }
                System.out.printf(
                        "Value must be between %d and %d.%n%n",
                        minValue,
                        maxValue
                );

            } catch (InputMismatchException e) {
                System.out.println(
                        "Invalid input. Please enter a whole number.\n"
                );
                scanner.nextLine();

            } catch (Exception e) {
                System.out.println(
                        "An unexpected error occurred. Please try again.\n"
                );
                scanner.nextLine();
            }
        }
    }

    // -----------------------------
    // Initialization Methods
    // -----------------------------

    /**
     * Creates and populates the collection of sections by prompting
     * the user to enter the number of sections, students, and grades.
     *
     * @param scanner Scanner used to read user input
     * @return collection of section grade lists
     */
    private static List<List<Integer>> initializeSections(
            Scanner scanner
    ) {

        List<List<Integer>> sections = new ArrayList<>();

        int sectionCount = getMinValidatedInput(
                "How many sections do you want? ",
                1,
                scanner
        );

        for (int sectionNumber = 1; sectionNumber <= sectionCount; sectionNumber++) {
            List<Integer> sectionGrades = new ArrayList<>();

            String studentCountPrompt = String.format(
                    "How many students do you want in section #%d? ",
                    sectionNumber
            );

            int studentCount = getMinValidatedInput(
                    studentCountPrompt,
                    1,
                    scanner
            );

            for (int studentNumber = 1; studentNumber <= studentCount; studentNumber++) {
                String gradePrompt = String.format(
                        "Enter the grade for student #%d: ",
                        studentNumber
                );

                int grade = getRangeValidatedInput(
                        gradePrompt,
                        MIN_GRADE,
                        MAX_GRADE,
                        scanner
                );

                sectionGrades.add(grade);
            }

            sections.add(sectionGrades);
        }

        return sections;
    }

    /**
     * Displays the program menu and executes the selected
     * grade analysis operation until the user chooses to exit.
     *
     * @param sections collection of section grade lists
     * @param scanner  Scanner used to read user input
     */
    private static void displayMenu(
            List<List<Integer>> sections,
            Scanner scanner
    ) {

        List<String> menuOptions = List.of(
                "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10", "11"
        );

        String choice;

        do {

            System.out.println(
                    """
                    Choose one of the options below:
                    1- Highest mark in a section
                    2- Highest mark overall
                    3- Lowest mark in a section
                    4- Lowest mark overall
                    5- Average for a section
                    6- Overall average
                    7- Passing students
                    8- Failing students
                    9- Display section marks
                    10- Display a student's mark
                    11- Exit
                    """
            );

            choice = getListValidatedInput(
                    "Enter your choice: ",
                    menuOptions,
                    scanner
            );

            switch (choice) {
                case "1" -> displayHighestMarkInSection(sections, scanner);
                case "2" -> displayHighestMarkOverall(sections);
                case "3" -> displayLowestMarkInSection(sections, scanner);
                case "4" -> displayLowestMarkOverall(sections);
                case "5" -> displaySectionAverage(sections, scanner);
                case "6" -> displayOverallAverage(sections);
                case "7" -> displayPassingStudents(sections);
                case "8" -> displayFailingStudents(sections);
                case "9" -> displaySectionMarks(sections, scanner);
                case "10" -> displayStudentMark(sections, scanner);
                case "11" -> System.out.println("Goodbye!");
            }

            System.out.println();
        } while (!choice.equals("11"));
    }

    // -----------------------------
    // Grade Analysis Methods
    // -----------------------------

    /**
     * Displays the highest mark and the corresponding student
     * in a user-selected section.
     *
     * @param sections collection of section grade lists
     * @param scanner  Scanner used to read user input
     */
    private static void displayHighestMarkInSection(
            List<List<Integer>> sections,
            Scanner scanner
    ) {

        int selectedSection = getRangeValidatedInput(
                "Select a section: ",
                1,
                sections.size(),
                scanner
        );

        List<Integer> sectionGrades = sections.get(selectedSection - 1);

        int highestMark = sectionGrades.getFirst();
        int studentNumber = 1;
        int highestStudentNumber = 1;

        for (int grade : sectionGrades) {
            if (grade > highestMark) {
                highestMark = grade;
                highestStudentNumber = studentNumber;
            }

            studentNumber++;
        }

        System.out.printf(
                "Section %d - Highest Mark: Student #%d scored %d.%n%n",
                selectedSection,
                highestStudentNumber,
                highestMark
        );
    }

    /**
     * Displays the highest mark and the corresponding student
     * across all sections.
     *
     * @param sections collection of section grade lists
     */
    private static void displayHighestMarkOverall(
            List<List<Integer>> sections
    ) {

        int highestMark = sections.getFirst().getFirst();

        int sectionNumber = 1;
        int highestSectionNumber = 1;

        int highestStudentNumber = 1;

        for (List<Integer> sectionGrades : sections) {
            int studentNumber = 1;

            for (int grade : sectionGrades) {
                if (grade > highestMark) {
                    highestMark = grade;
                    highestSectionNumber = sectionNumber;
                    highestStudentNumber = studentNumber;
                }

                studentNumber++;
            }

            sectionNumber++;
        }

        System.out.printf(
                "Overall Highest Mark: Section %d - Student #%d scored %d.%n%n",
                highestSectionNumber,
                highestStudentNumber,
                highestMark
        );
    }

    /**
     * Displays the lowest mark and the corresponding student
     * in a user-selected section.
     *
     * @param sections collection of section grade lists
     * @param scanner  Scanner used to read user input
     */
    private static void displayLowestMarkInSection(
            List<List<Integer>> sections,
            Scanner scanner
    ) {

        int selectedSection = getRangeValidatedInput(
                "Select a section: ",
                1,
                sections.size(),
                scanner
        );

        List<Integer> sectionGrades = sections.get(selectedSection - 1);

        int lowestMark = sectionGrades.getFirst();
        int studentNumber = 1;
        int lowestStudentNumber = 1;

        for (int grade : sectionGrades) {
            if (grade < lowestMark) {
                lowestMark = grade;
                lowestStudentNumber = studentNumber;
            }

            studentNumber++;
        }

        System.out.printf(
                "Section %d - Lowest Mark: Student #%d scored %d.%n%n",
                selectedSection,
                lowestStudentNumber,
                lowestMark
        );
    }

    /**
     * Displays the lowest mark and the corresponding student
     * across all sections.
     *
     * @param sections collection of section grade lists
     */
    private static void displayLowestMarkOverall(
            List<List<Integer>> sections
    ) {

        int lowestMark = sections.getFirst().getFirst();

        int sectionNumber = 1;
        int lowestSectionNumber = 1;

        int lowestStudentNumber = 1;

        for (List<Integer> sectionGrades : sections) {
            int studentNumber = 1;

            for (int grade : sectionGrades) {
                if (grade < lowestMark) {
                    lowestMark = grade;
                    lowestSectionNumber = sectionNumber;
                    lowestStudentNumber = studentNumber;
                }

                studentNumber++;
            }

            sectionNumber++;
        }

        System.out.printf(
                "Overall Lowest Mark: Section %d - Student #%d scored %d.%n%n",
                lowestSectionNumber,
                lowestStudentNumber,
                lowestMark
        );
    }

    /**
     * Displays the average mark for a user-selected section.
     *
     * @param sections collection of section grade lists
     * @param scanner  Scanner used to read user input
     */
    private static void displaySectionAverage(
            List<List<Integer>> sections,
            Scanner scanner
    ) {

        int selectedSection = getRangeValidatedInput(
                "Select a section: ",
                1,
                sections.size(),
                scanner
        );

        List<Integer> sectionGrades = sections.get(selectedSection - 1);

        int totalMarks = 0;

        for (int grade : sectionGrades) {
            totalMarks += grade;
        }

        double average = (double) totalMarks / sectionGrades.size();

        System.out.printf(
                "Section %d - Average: %.2f%n%n",
                selectedSection,
                average
        );
    }

    /**
     * Displays the average mark across all sections.
     *
     * @param sections collection of section grade lists
     */
    private static void displayOverallAverage(
            List<List<Integer>> sections
    ) {

        int totalMarks = 0;
        int totalStudentCount = 0;

        for (List<Integer> sectionGrades : sections) {
            totalStudentCount += sectionGrades.size();

            for (int grade : sectionGrades) {
                totalMarks += grade;
            }
        }

        double average = (double) totalMarks / totalStudentCount;

        System.out.printf(
                "Overall Average: %.2f%n%n",
                average
        );
    }

    /**
     * Displays the students who passed
     * in each section.
     *
     * @param sections collection of section grade lists
     */
    private static void displayPassingStudents(
            List<List<Integer>> sections
    ) {

        int sectionNumber = 1;

        for (List<Integer> sectionGrades : sections) {
            int studentNumber = 1;
            boolean firstStudent = true;

            System.out.printf(
                    "Section %d - Passing Students: ",
                    sectionNumber
            );

            for (int grade : sectionGrades) {
                if (grade >= PASSING_MARK) {
                    if (!firstStudent) {
                        System.out.print(", ");
                    }

                    System.out.printf("Student #%d", studentNumber);
                    firstStudent = false;
                }

                studentNumber++;
            }

            System.out.println();
            sectionNumber++;
        }
    }

    /**
     * Displays the students who failed
     * in each section.
     *
     * @param sections collection of section grade lists
     */
    private static void displayFailingStudents(
            List<List<Integer>> sections
    ) {

        int sectionNumber = 1;

        for (List<Integer> sectionGrades : sections) {
            int studentNumber = 1;
            boolean firstStudent = true;

            System.out.printf(
                    "Section %d - Failing Students: ",
                    sectionNumber
            );

            for (int grade : sectionGrades) {
                if (grade < PASSING_MARK) {
                    if (!firstStudent) {
                        System.out.print(", ");
                    }

                    System.out.printf("Student #%d", studentNumber);
                    firstStudent = false;
                }

                studentNumber++;
            }

            System.out.println();
            sectionNumber++;
        }
    }

    /**
     * Displays the marks for every student
     * in a user-selected section.
     *
     * @param sections collection of section grade lists
     * @param scanner  Scanner used to read user input
     */
    private static void displaySectionMarks(
            List<List<Integer>> sections,
            Scanner scanner
    ) {

        int selectedSection = getRangeValidatedInput(
                "Select a section: ",
                1,
                sections.size(),
                scanner
        );

        List<Integer> sectionGrades = sections.get(selectedSection - 1);

        int studentNumber = 1;

        System.out.printf(
                "Section %d - Student Marks:%n%n",
                selectedSection
        );

        for (int grade : sectionGrades) {
            System.out.printf(
                    "Student #%d: %d%n",
                    studentNumber,
                    grade
            );

            studentNumber++;
        }

        System.out.println();
    }

    /**
     * Displays the mark for a user-selected student
     * in a user-selected section.
     *
     * @param sections collection of section grade lists
     * @param scanner  Scanner used to read user input
     */
    private static void displayStudentMark(
            List<List<Integer>> sections,
            Scanner scanner
    ) {

        int selectedSection = getRangeValidatedInput(
                "Select a section: ",
                1,
                sections.size(),
                scanner
        );

        List<Integer> sectionGrades = sections.get(selectedSection - 1);

        int selectedStudent = getRangeValidatedInput(
                "Select a student: ",
                1,
                sectionGrades.size(),
                scanner
        );

        int studentMark = sectionGrades.get(selectedStudent - 1);

        System.out.printf(
                "Section %d - Student #%d: %d%n%n",
                selectedSection,
                selectedStudent,
                studentMark
        );
    }
}

