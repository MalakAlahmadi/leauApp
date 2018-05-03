package com.collegeproject.watertank;

import android.support.test.runner.AndroidJUnit4;

import com.collegeproject.watertank.Network.validator;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class emailmobiletests {


    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertThat(validator.isValidEmail("name@email.com"), is(true));
    }
    @Test
    public void emailValidator_WrongEmailSimple_ReturnsFalse() {
        assertThat(validator.isValidEmail("name.email@com"), is(false));
    }
    @Test
    public void mobileValidator_CorrectSimple_ReturnsTrue() {
        assertThat(validator.isValidmobile("0555545545"), is(true));
    }
    @Test
    public void mobileValidator_WrongMobileSimple_ReturnsFalse() {
        assertThat(validator.isValidmobile("5566554430"), is(false));
    }

    @Test
    public void mobileValidator_WrongMobileSimple2_ReturnsFalse() {
        assertThat(validator.isValidmobile("056655430"), is(false));
    }
}
