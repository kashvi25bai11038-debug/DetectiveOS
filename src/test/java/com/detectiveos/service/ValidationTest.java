package com.detectiveos.service;
import com.detectiveos.util.ValidationUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ValidationTest {
    @Test void blankTitleFails(){assertThrows(IllegalArgumentException.class,()->ValidationUtil.required("  ","Title"));}
    @Test void invalidReliabilityFails(){assertThrows(IllegalArgumentException.class,()->ValidationUtil.reliability(101));}
}
