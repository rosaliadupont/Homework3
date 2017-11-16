
package core.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;

public class TestInstructor {
	private IAdmin admin;
    private IInstructor instructor;

    //@before ensures that it gets tested before anything runs, global state
    @Before
    public void setup() {
        this.admin = new Admin();
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor = new Instructor();
    }

    @Test
    public void testAddHomework() { 
        instructor.addHomework("Instructor","Test",2017,"hw");
        assertTrue(instructor.homeworkExists("Test", 2017, "hw"));
        /*
         * this is just to test if add homework works
         */
    }
    
    @Test
    public void testAddHomeworkToNillClass() { 
        instructor.addHomework("Instructor", "Test2", 2017, "hw");
        assertFalse(instructor.homeworkExists("Test2",2017,"hw"));
        /*
         * add homework should not be able to add homework to a class that does not exist
         */
    }
    
    
    
    @Test
    public void testAddHomeworkWithInvalidYear() { 
        instructor.addHomework("Instructor", "Test", 2016, "hw");
        assertFalse(instructor.homeworkExists("Test",2016,"hw"));
        /*
         * add homework should not be able to add a homework of a class from the past
         */
    }
    
    @Test
    public void testAddHomeworkToWrongInstructor() { 
        instructor.addHomework("Instructor2", "Test", 2017, "hw");
        assertFalse(instructor.homeworkExists("Test", 2017, "hw"));
        /*
         * add homework should not be able to add homework to an unassigned instructor
         */
    }
    
    @Test
    public void testAssignGrade() { 
        IStudent student = new Student();
        student.registerForClass("stud","Test",2017);
        instructor.addHomework("Instructor","Test",2017,"hw");
        student.submitHomework("stud","hw","answer","Test",2017);
        instructor.assignGrade("Instructor","Test",2017,"hw","stud",1);

        assertEquals(new Integer(1), instructor.getGrade("Test",2017,"hw","stud"));
        /*
         * just testing if the function AssignGrade works
         */
    }
    
    @Test
    public void testAssignGradeNillStudent() { 
        instructor.addHomework("Instructor","Test",2017,"hw");
        instructor.assignGrade("Instructor","Test",2017,"hw","stud",1);

        assertNull(instructor.getGrade("Test",2017,"hw","stud"));
        
        /*
         * AssignGrade should not be able to assign grade to a student that does not exist
         */
    }
    
    @Test
    public void testAssignGradeWithoutStudentSubmission() { 
        IStudent student = new Student();
        student.registerForClass("stud","Test",2017);
        instructor.addHomework("Instructor","Test",2017,"hw");
        instructor.assignGrade("Instructor","Test",2017,"hw","stud",1);

        assertNull(instructor.getGrade("Test",2017,"hw","stud"));
        
        /*
         * this should be null as assigning grade to a student that didn't submit a homework
         * should not work
         */
    }
    
    @Test
    public void testAssignGradeButNoHomeworkSubmission() { 
        IStudent student = new Student();
        student.registerForClass("stud","Test",2017);
        instructor.assignGrade("Instructor","Test",2017,"hw","stud",1);
        assertNull(instructor.getGrade("Test",2017,"hw","stud"));
        
        /*
         * the grade should be null because the assign grade function should
         * not be able to assign grade if student hasn't submitted homework
         */
    }
    
    
    
    
    
    
}