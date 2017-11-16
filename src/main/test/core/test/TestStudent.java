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

public class TestStudent {
	private IAdmin admin;
    private IStudent student;
    private IInstructor instructor;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.admin.createClass("Test",2017,"Instructor",1);
        this.student = new Student();
        this.instructor = new Instructor();
    }
    
    @Test
    public void testRegisterForClass() { 
        this.student.registerForClass("student","Test",2017);
        assertTrue(this.student.isRegisteredFor("student","Test",2017));
        /*
         * just testing that registerForClass works correctly
         */
    }
   
    
    @Test
    public void testRegisterForClassWithNoOpenSeats() { 
        IStudent student2 = new Student();
        this.student.registerForClass("student1", "Test", 2017);
        student2.registerForClass("student2", "Test", 2017);
        assertFalse(student2.isRegisteredFor("student2", "Test", 2017));
        /*
         * this test is testing that the student shouldn't register for
         * a class that is full
         */
    }
    
  
    
    @Test
    public void testSubmitHomework() { 
        this.student.registerForClass("student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "hw");
        this.student.submitHomework("student", "hw", "answer", "Test", 2017);
        assertTrue(this.student.hasSubmitted("student", "hw", "Test", 2017));
        /*
         * just testing that submitHomework works correctly
         */
    }
    
    @Test
    public void testSubmitHomeworkDiffYear() { 
        this.admin.createClass("Test2",2018,"Instructor",1);
        this.instructor.addHomework("Instructor", "Test2", 2018, "hw");
        this.student.registerForClass("student", "Test2", 2018);
        this.student.submitHomework("student", "hw", "answer", "Test2", 2018);
        assertFalse(this.student.hasSubmitted("student", "hw", "Test2", 2018));
        /*
         * SubmitHomework shouldn't work if the submission is being done
         * on a class of a different year other than right now therefore
         * should throw error
         */
    }
    
    @Test
    public void testSubmitHomeworkStudentHasNotBeenAssigned() { 
        this.student.submitHomework("student", "hw", "answer", "Test", 2017);
        assertFalse(this.student.hasSubmitted("student", "hw", "Test", 2017));
        /*
         * SubmitHomwork shouldn't be able to submit to homework
         * that was never assigned therefore should assert false
         */
    }
    
    @Test
    public void testSubmitHomeworkToClassNotEnrolledIn() { 
        this.instructor.addHomework("Instructor", "Test", 2017, "hw");
        this.student.submitHomework("student", "hw", "answer", "Test", 2017);
        assertFalse(this.student.hasSubmitted("student", "hw", "Test", 2017));
        
        /*
         * SubmitHomework shouldn't work if the student submitting
         * homework is not registered for the class
         */
    }
    
    @Test
    public void testDropClass() { 
        this.student.registerForClass("student", "Test",2017);
        assertTrue(this.student.isRegisteredFor("student","Test",2017));

        this.student.dropClass("student", "Test", 2017);
        assertFalse(this.student.isRegisteredFor("student", "Test", 2017));
        /*
         * just testing that dropClass works correctly as expected
         */
    }
    
    
    
}


