package core.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Student;

public class TestAdmin {
	private IAdmin admin;

    //@before ensures that it gets tested before anything runs, global state
    @Before
    public void setup() {
        this.admin = new Admin();
    }

    //anything that you put with @Test will be tested
    @Test
    public void testMakeInvalidClass() {
        this.admin.createClass("Test", 2016, "Instructor", 15); //here there 
        //is one bug that needs to be fixed, year is in the past
        assertFalse(this.admin.classExists("Test", 2016));
    }
    
    @Test
    public void testMakeValidClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }
   
    
    @Test
    public void testMakeClassThreeInstructorsSameYear() {
    		this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.admin.createClass("Test2", 2017, "Instructor", 15);
        this.admin.createClass("Test3", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("Test3", 2017));
        //the above test3 shouldn't exist because the same instructor
        //shouldn't teach more than 2 classes
    }
    
    @Test
    public void testMakeClassSameInstructor() { 
        this.admin.createClass("Test", 2017, "Instructor1", 15);
        this.admin.createClass("Test", 2017, "Instructor2", 15);
        assertEquals("Instructor1", this.admin.getClassInstructor("Test", 2017));
        /*
         * same class should not have multiple instructors, therefore
         * assert equals should throw error
         */
    }
    
    @Test
    public void testChangeCapacity() { 
        this.admin.createClass("Test", 2017, "Instructor", 1);
        this.admin.changeCapacity("Test", 2017, 2);
        assertEquals(2, this.admin.getClassCapacity("Test", 2017));
        /*
         * just testing if changing the capacity works
         */
    }
    
    @Test
    public void testChangeCapacityToZero() { 
        this.admin.createClass("Test",2017,"Instructor",2);
        this.admin.changeCapacity("Test",2017,0);
        assertEquals(2, this.admin.getClassCapacity("Test",2017));
        /*
         * program shouldn't be able to change capacity
         * to 0
         */
    }
    
    @Test
    public void testMakeClassCapacityZero() { 
        this.admin.createClass("Test",2017,"Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
        /*
         * class with 0 capacity should not be created, throw error
         */
    }
    
    @Test
    public void testCreateClassCapacityLessZero() { 
        this.admin.createClass("Test",2017,"Instructor",-1);
        assertFalse(this.admin.classExists("Test",2017));
        /*
         * class with negative capacity should not be created, throw error
         * if it does
         */
    }
   
    
    @Test
    public void testChangeCapacityLessThanCurrentStudentsAmount() { 
        this.admin.createClass("Test",2017,"Instructor",2);
        IStudent student = new Student();
        student.registerForClass("stud", "Test", 2017);
        IStudent student2 = new Student();
        student2.registerForClass("stud", "Test", 2017);
        /*
         * we have registered two students already, but when we change the capacity
         * the capacity should not be changed as it is less
         * than the number of current students there
         */
        this.admin.changeCapacity("Test",2017,1);
        assertEquals(2, this.admin.getClassCapacity("Test",2017));
        //this should throw error
    }
    @Test
    public void testChangeCapacityClassIfDoesNotExist() { 
        this.admin.changeCapacity("Test",2017,20);
        assertEquals(-1, this.admin.getClassCapacity("Test",2017));
    }
    /*
     * should not change capacity of a non-existent class
     */
    
    @Test
    public void testChangeCapacityLessThanZero() { 
        this.admin.createClass("Test",2017,"Instructor",2);
        this.admin.changeCapacity("Test",2017,-1);
        assertEquals(2, this.admin.getClassCapacity("Test",2017));
        
        /*
         * shouldn't create capacity to less than zero
         */
    }
    
    
    
    
    
}

