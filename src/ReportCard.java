/**
 * The class assumes the existence of Student and Teacher class. The constructor takes in arguments from the student
 * and/or teacher class. Report card is a final document that officially communicates the student's performance over the
 * course of the past academic year. So, the class is built with the finality and there are no setters for changing the
 * arguments initialized via the class' constructor (only one provided). Consequently, any modifications to the student's
 * scores or adjustments are handled at the student class object level. Further, the class also overrides the object's
 * toString() method and provides a better one to present the contents of the class in an appropriately formatted human-
 * readable string. Alternatively, the class also has a display method that presents the contents of the class per the
 * standard real-world report card format.
 */

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings({"WeakerAccess", "unused"})
class ReportCard {

    /**
     * Class level relevant constants
     */

    /* School Name */
    private static final String SCHOOL_NAME = "TRUE KNOWLEDGE QUEST MIDDLE SCHOOL";
    /* Current Academic Year */
    private static final String ACADEMIC_YEAR = "2016 - 2017";
    /* Academic scale per which the student are graded */
    private static final String ACADEMIC_SCALE = "A = 90 - 100 \nB = 80 - 89 \nC = 70 - 79 \nD = 65 - 69 \nF = Below 65";
    /* Student's name */
    private String name;
    /* Student grade */
    private String grade;
    /* Student's enrolled courses as an ArrayList */
    private ArrayList<String> courses;
    /* Scores scored on each subject in an ordered list where the order corresponds to the courses list */
    private ArrayList<ArrayList<Float>> scores;
    /* Days absent information of student */
    private int[] daysAbsent;
    /* Times tardy information of student */
    private int[] timesTardy;

    /**
     * Get the student's name
     *
     * @return a String for the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the student's grade
     *
     * @return a String for the grade
     */
    public String getGrade() {
        return grade;
    }

    /**
     * Display the list of enrolled courses in line-separated format
     */
    public ArrayList<String> getCourses() {
        return courses;
    }

    /**
     * Get the scores of a student
     *
     * @return a list of list of scores
     */
    public ArrayList<ArrayList<Float>> getScores() {
        return scores;
    }

    public String getDaysAbsent() {
        return Arrays.toString(daysAbsent);
    }

    public String getTimesTardy() {
        return Arrays.toString(timesTardy);
    }

    /**
     * Create a ReportCard object
     *
     * @param studentName    a String for the student's name
     * @param studentGrade   a String for the student's grade
     * @param studentCourses a String list that contains student enrolled courses
     */
    ReportCard(String studentName, String studentGrade, ArrayList<String> studentCourses, ArrayList<ArrayList<Float>> studentCourseScores,
               int[] studentAbsentDays, int[] studentTimesTardy) {

        /* Check for valid name entry. Ideally, this should be handled at the student class level */
        if (containsOnlyAlphabets(studentName)) {
            this.name = studentName;

        } else {
            System.out.println("Illegal value for student's name");
        }
        // Store student grade information
        this.grade = studentGrade;

        // Store student enrolled courses list
        this.courses = studentCourses;

        // Store student individual course scores over 4 quarters
        // Test whether number of scores entered by a course teacher matches the number of quarters in an academic year
        // Ideally, the below validation must be handled at the student class level
        for (ArrayList<Float> aList : studentCourseScores) {
            int numberOfScores = aList.size();
            if (numberOfScores != 4) {
                System.out.println("The number of scores does not match the number of quarters in an academic year!" +
                        " The last score has been ignored.");
            } else {
                this.scores = studentCourseScores;
            }
        }

        // Store days absent information of student
        this.daysAbsent = studentAbsentDays;

        // Store times tardy information of student
        this.timesTardy = studentTimesTardy;
    }

    /**
     * Maps the student's individual course score to the corresponding course per course title
     *
     * @param courseTitle the title of the course to map scores for
     * @return a float number array containing the scores for all quarters of the academic year
     */
    float[] mappedScores(String courseTitle) {
        int getPosition = courses.indexOf(courseTitle);
        float[] result = new float[4];
        ArrayList<Float> target = scores.get(getPosition);
        for (int i = 0; i < result.length; i++) {
            // Traverse result array in place of object's scores field
            // Prevents throwing an OutOfBoundsException when number of scores within the inner ArrayList exceeds
            // the number of quarters in an academic year
            result[i] = target.get(i);
        }
        return result;
    }

    /**
     * Get the average score for the individual course scores rounded to the nearest integer
     *
     * @param courseScores the float number array containing the individual course scores of all quarters for the academic year
     * @return an integer average value
     */
    int courseAverage(float[] courseScores) {
        float sum = 0f;
        for (float f : courseScores) {
            sum += f;
        }
        return Math.round(sum / courseScores.length);
    }

    /**
     * Get the letter grade for an individual course per student's quarterly performance
     *
     * @param courseTitle the title of the course
     * @return letter grade is returned per the academic scale
     */
    String letterGrade(String courseTitle) {
        float[] courseScore = mappedScores(courseTitle);
        int courseAverage = courseAverage(courseScore);
        if (courseAverage > 89) {
            return "A";
        } else if (courseAverage > 79) {
            return "B";
        } else if (courseAverage > 69) {
            return "C";
        } else if (courseAverage > 64) {
            return "D";
        } else {
            return "F";
        }
    }

    /**
     * Compute Grade Point Average where each grade has different weights
     * A = 4; B = 3; C = 2; D = 1: F = 0
     *
     * @return a floating point decimal as GPA is returned
     */
    float gradePointAverage() {
        int sum = 0;
        for (String s : courses) {
            switch (letterGrade(s)) {
                case "A":
                    sum += 4;
                    break;
                case "B":
                    sum += 3;
                    break;
                case "C":
                    sum += 2;
                    break;
                case "D":
                    sum += 1;
                    break;
                default:
                    // No points for letter grade E
                    break;
            }
        }
        return sum / (float) courses.size();
    }

    /**
     * Get the decision on whether the student is promoted or retained
     *
     * @param gpa The GPA of student is the sole criterion for deciding whether the student is promoted or not
     * @return "Promoted" when the GPA of the student is greater than 1 and "Retained" if not
     */
    String promotionDecision(float gpa) {
        if (gpa > 1) {
            return "Promoted";
        } else {
            return "Retained";
        }
    }

    /**
     * Returns the contents of the class in a human-readable string
     *
     * @return a formatted String with all the contents of the class
     */
    @SuppressWarnings("UnnecessaryLocalVariable")
    @Override
    public String toString() {
        String header = ReportCard.SCHOOL_NAME + "\n" + ReportCard.ACADEMIC_YEAR + "\n";
        String studentDetails = "Name: " + this.getName() + "\nGrade: " + this.getGrade() + "\n\n";

        // Get enrolled courses and scores side-by-side
        StringBuilder subjectAndScores = new StringBuilder("SCORES:\n-------\n");
        // Get individual course's average and letter grade
        StringBuilder averageAndLetter = new StringBuilder("AVERAGE AND LETTER GRADE:\n-------------------------\n");
        for (String courseTitle : courses) {
            subjectAndScores.append(courseTitle).append(": ");
            float[] courseScores = mappedScores(courseTitle);
            for (float f : courseScores) {
                subjectAndScores.append(f).append(" ");
            }
            subjectAndScores.append("\n");
            averageAndLetter.append(courseTitle)
                    .append(": ")
                    .append(String.valueOf(courseAverage(courseScores)))
                    .append(" ")
                    .append(letterGrade(courseTitle))
                    .append("\n");
        }
        // Get attendance information
        // Get number of days absent
        StringBuilder attendance = new StringBuilder("ATTENDANCE:\n-----------\nDays Absent: ");
        for (int i : daysAbsent) {
            if (i > 9) {
                attendance.append(i).append(" ");
            } else {
                attendance.append("0").append(i).append(" ");
            }
        }
        // Get number of times tardy
        attendance.append("\nTimes Tardy: ");
        for (int i : timesTardy) {
            if (i > 9) {
                attendance.append(i).append(" ");
            } else {
                attendance.append("0").append(i).append(" ");
            }
        }

        // Get GPA
        String gpa = "GPA: " + this.gradePointAverage();

        // Get promotion decision
        String promotedOrRetained = "Promoted or Retained: " + this.promotionDecision(this.gradePointAverage()).toUpperCase();

        // final string
        String readable = header + studentDetails + subjectAndScores + "\n\n" + averageAndLetter + "\n\n" + attendance +
                "\n\n" + gpa + "\n\n" + promotedOrRetained + "\n\n\n" + "ACADEMIC SCALE\n" + "--------------\n"
                + ReportCard.ACADEMIC_SCALE;

        return readable;
    }

    /**
     * Official display for the Report Card class for a student presented in a real-world report card format
     * The method prints the report card styled display in the standard console
     */
    void display() {
        System.out.printf("%55s", ReportCard.SCHOOL_NAME);
        System.out.println();
        System.out.printf("%42s", ReportCard.ACADEMIC_YEAR);
        System.out.println();
        System.out.println("Student: " + this.getName());
        System.out.print("  Grade: " + this.getGrade());
        System.out.println("\n\n");
        System.out.printf("%20s", "Subject");
        System.out.printf("%6s", "1Q");
        System.out.printf("%7s", "2Q");
        System.out.printf("%7s", "3Q");
        System.out.printf("%7s", "4Q");
        System.out.printf("%10s", "Average");
        System.out.printf("%14s", "Letter Grade");
        System.out.println();
        System.out.printf("%44s", "-------------------------------------------------------------------------");
        System.out.println();
        for (String courseTitle : courses) {
            float[] courseScores = mappedScores(courseTitle);
            System.out.printf("%20s:", courseTitle);
            for (float f : courseScores) {
                System.out.printf(" %6.2f", f);
            }
            System.out.printf("%6d", courseAverage(courseScores));
            System.out.printf("%10s", letterGrade(courseTitle));
            System.out.println();
        }
        System.out.printf("%44s", "-------------------------------------------------------------------------");
        System.out.println("\n");


        // Displays Attendance information of the student
        System.out.println("ATTENDANCE");
        System.out.print("Days Absent:");
        for (int i : daysAbsent) {
            System.out.printf("\t%02d", i);
        }
        System.out.println();
        System.out.print("Times Tardy: ");
        for (int i : timesTardy) {
            System.out.printf("\t%02d", i);
        }

        // Calculate GPA
        System.out.println("\n\n");
        float gpa = this.gradePointAverage();
        System.out.println("GPA: " + this.gradePointAverage() + "\n");

        // Compute promotion decision
        System.out.print("Promoted or Retained: " + promotionDecision(gpa).toUpperCase());

        // Display academic scale for reference
        System.out.println("\n\n\n\n\n\n");
        System.out.println(ReportCard.ACADEMIC_SCALE);
    }

    /**
     * Tests if a String contains alphabets only
     *
     * @param s the String to be tested
     * @return true if the character sequence represented by the argument contains only alphabets; false otherwise.
     * Note that false is returned even when an empty string is passed as argument
     */
    boolean containsOnlyAlphabets(String s) {
        boolean result = true;
        if (!s.isEmpty()) {
            // When s is not null String object
            // Remove all whitespace and make copy of String argument
            String copy = String.copyValueOf(s.replaceAll("\\s+", "").toCharArray());
            // Traverse over copied string object
            for (int i = 0; i < copy.length(); i++) {
                // Get char at current position
                char c = copy.charAt(i);
                // Check whether the char is a alphabetic letter
                if (!Character.isLetter(c)) {
                    // Contains non alphabet character
                    // Terminate traversing over the copied string object
                    i = copy.length();
                    // Set return value to false due to non-alphabet character detection
                    result = false;
                }
            }
        } else {
            // s is a null String object
            result = false;
        }
        return result;
    }

    public static void main(String[] args) {

        ArrayList<String> studentCourses = new ArrayList<>();
        studentCourses.add("Science");
        studentCourses.add("Mathematics");
        studentCourses.add("Computer Science");

        ArrayList<ArrayList<Float>> studentCourseScores = new ArrayList<>();

        ArrayList<Float> science = new ArrayList<>();
        science.add(95.75f);
        science.add(100f);
        science.add(98.85f);
        science.add(99.5f);
        studentCourseScores.add(science);

        ArrayList<Float> math = new ArrayList<>();
        math.add(100f);
        math.add(98f);
        math.add(100f);
        math.add(100f);
        studentCourseScores.add(math);

        ArrayList<Float> cs = new ArrayList<>();
        cs.add(100f);
        cs.add(100f);
        cs.add(100f);
        cs.add(100f);
        studentCourseScores.add(cs);

        ReportCard test = new ReportCard(
                "Robert",
                "7",
                studentCourses,
                studentCourseScores,
                new int[]{0, 0, 0, 0},
                new int[]{0, 1, 0, 0}
                );
        System.out.println();
        // Present contents via toString()
        System.out.println(test);
        // Present report card styled format
        System.out.println("\n\n");
        test.display();
    }
}
