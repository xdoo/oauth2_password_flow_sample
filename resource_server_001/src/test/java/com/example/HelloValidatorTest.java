package com.example;

import java.util.Set;
import javax.validation.ConstraintViolation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author straubec
 */
public class HelloValidatorTest {
    
    private Validator validator;
    
    @Before
    public void init() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();
    }
    
    @Test
    public void testNamePatternValid() {
        // valid
        Set<ConstraintViolation<Hello>> r1 = this.validator.validate(new Hello("Hans", "Munich"));
        assertThat(r1, empty());
        
        r1 = this.validator.validate(new Hello("Hans Peter", "Munich"));
        assertThat(r1, empty());
        
        r1 = this.validator.validate(new Hello("Hans-Peter", "Munich"));
        assertThat(r1, empty());
    }
    
    @Test
    public void testNamePattternInvalid() {
        // invalid
        Set<ConstraintViolation<Hello>> r1 = this.validator.validate(new Hello("<Script>", "Munich"));
        assertThat(r1, hasSize(1));
        
        r1 = this.validator.validate(new Hello("Jörg", "Munich"));
        assertThat(r1, hasSize(1));
    }
    
    @Test
    public void testNameSizeValid() {
        Set<ConstraintViolation<Hello>> r1 = this.validator.validate(new Hello("Hans", "Munich"));
        assertThat(r1, empty());
        
        //2
        this.validator.validate(new Hello("Bo", "Munich"));
        assertThat(r1, empty());
        
        // 50
        this.validator.validate(new Hello("Booooooooooooooooooooooooooooooooooooooooooooooooo", "Munich"));
        assertThat(r1, empty());
        
    }
    
    @Test
    public void testNameSizeInvalid() {
        // 1
        Set<ConstraintViolation<Hello>> r1 = this.validator.validate(new Hello("B", "Munich"));
        assertThat(r1, hasSize(1));
        
        // 51
        r1 = this.validator.validate(new Hello("Boooooooooooooooooooooooooooooooooooooooooooooooooo", "Munich"));
        assertThat(r1, hasSize(1));
    }
    
    @Test
    public void testCityPatternValid() {
        Set<ConstraintViolation<Hello>> r1 = this.validator.validate(new Hello("Hans", "Munich"));
        assertThat(r1, empty());
        
        r1 = this.validator.validate(new Hello("Hans", "Castrop-Rauxel"));
        assertThat(r1, empty());
    }
    
    @Test
    public void testCityPatternInvalid() {
        Set<ConstraintViolation<Hello>> r1 = this.validator.validate(new Hello("Hans", "München"));
        assertThat(r1, hasSize(1));
        
        r1 = this.validator.validate(new Hello("Hans", "Castrop Rauxel"));
        assertThat(r1, hasSize(1));
    }
    
    @Test
    public void testCitySizeValid() {
        Set<ConstraintViolation<Hello>> r1 = this.validator.validate(new Hello("Hans", "Munich"));
        assertThat(r1, empty());
        
        r1 = this.validator.validate(new Hello("Hans", "Foo"));
        assertThat(r1, empty());
        
        r1 = this.validator.validate(new Hello("Hans", "Fooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo"));
        assertThat(r1, empty());
    }
    
    @Test
    public void testCitySizeInvalid() {
        Set<ConstraintViolation<Hello>> r1 = this.validator.validate(new Hello("Hans", "Mu"));
        assertThat(r1, hasSize(1));
        
        r1 = this.validator.validate(new Hello("Hans", "Foooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo"));
        assertThat(r1, hasSize(1));
    }
    
}
