package net.lbruun.datasize;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class DataSizeTest {

    @Test
    public void testAsStringBinary() {
        assertEquals("0 B", DataSize.asStringBinary(0));
        assertEquals("950 B", DataSize.asStringBinary(950));
        assertEquals("1 KiB", DataSize.asStringBinary(1256));
        assertEquals("1.9 MiB", DataSize.asStringBinary(2_000_000L));
        assertEquals("1.9 MiB", DataSize.asStringBinary((2 * 1024 * 1024) - (2 * 1024)));
        assertEquals("9.00 GiB", DataSize.asStringBinary(9L * 1024L * 1024L * 1024L));
        assertEquals("8.50 GiB", DataSize.asStringBinary((9L * 1024L * 1024L * 1024L) - (512L * 1024L * 1024L)));
        assertEquals("2.500 TiB", DataSize.asStringBinary((3L * 1024L * 1024L * 1024L * 1024L) - (512L * 1024L * 1024L * 1024L)));
        assertEquals("3.000 PiB", DataSize.asStringBinary((3L * 1024L * 1024L * 1024L * 1024L * 1024L)));

        assertEquals("7.0 EiB", DataSize.asStringBinary(7L * 1024L * 1024L * 1024L * 1024L * 1024L * 1024L));
        assertEquals("7.0 EiB", DataSize.asStringBinary((7L * 1024L * 1024L * 1024L * 1024L * 1024L * 1024L) + (100L * 1024L * 1024L * 1024L * 1024L * 1024L)));
        assertEquals("7.0 EiB", DataSize.asStringBinary((7L * 1024L * 1024L * 1024L * 1024L * 1024L * 1024L) + (102L * 1024L * 1024L * 1024L * 1024L * 1024L) - 1));
        assertEquals("7.1 EiB", DataSize.asStringBinary((7L * 1024L * 1024L * 1024L * 1024L * 1024L * 1024L) + (103L * 1024L * 1024L * 1024L * 1024L * 1024L)));
        assertEquals("7.9 EiB", DataSize.asStringBinary(Long.MAX_VALUE - 1));

    }

    @Test
    public void testAsStringDecimal() {
        assertEquals("0 B", DataSize.asStringDecimal(0));
        assertEquals("950 B", DataSize.asStringDecimal(950));
        assertEquals("1 kB", DataSize.asStringDecimal(1000));
        assertEquals("1.9 MB", DataSize.asStringDecimal((2 * 1000 * 1000) - (2 * 1000)));
        assertEquals("2.0 MB", DataSize.asStringDecimal  (2_000_000L));
        assertEquals("9.00 GB", DataSize.asStringDecimal(9L * 1000L * 1000L * 1000L));
        assertEquals("8.50 GB", DataSize.asStringDecimal((9L * 1000L * 1000L * 1000L) - (500L * 1000L * 1000L)));
        assertEquals("9.2 EB", DataSize.asStringDecimal(Long.MAX_VALUE - 1));
    }

    @Test
    public void testAsStringBinaryWithDecimalSuffix() {
        DataSizeUnitSuffixes suffixes = DataSizeUnitSuffixes.SUFFIXES_SI;
        assertEquals("0 B", DataSize.asString(0, true, suffixes, '.', null));
        assertEquals("950 B", DataSize.asString(950, true, suffixes, '.', null));
        assertEquals("1 kB", DataSize.asString(1024, true, suffixes, '.', null));
        assertEquals("1.9 MB", DataSize.asString((2 * 1024L * 1024L) - (2 * 1024L), true, suffixes, '.', null));
    }

    @Test
    public void testAsStringBinaryWithCustomDecimals() {

        DataSizeUnitDecimals decimals = DataSizeUnitDecimals.builder()
                .withKilobyteDecimals(0)
                .withMegabyteDecimals(3)
                .withGigabyteDecimals(2)
                .withTerabyteDecimals(3)
                .withPetabyteDecimals(3)
                .withExabyteDecimals(1)
                .build();
        DataSizeUnitSuffixes suffixes = DataSizeUnitSuffixes.SUFFIXES_ISO80000;
        assertEquals("0 B", DataSize.asString(0, true, suffixes, '.', decimals));
        assertEquals("950 B", DataSize.asString(950, true, suffixes, '.', decimals));
        assertEquals("1 KiB", DataSize.asString(1024, true, suffixes, '.', decimals));
        assertEquals("1.907 MiB", DataSize.asString(2_000_000L, true, suffixes, '.', decimals));
        assertEquals("1.998 MiB", DataSize.asString((2 * 1024L * 1024L) - (2 * 1024L), true, suffixes, '.', decimals));
        assertEquals("8.50 GiB", DataSize.asString((9L*1024L*1024L*1024L) -(512L*1024L*1024L), true, suffixes, '.', decimals));
        assertEquals("2.500 TiB", DataSize.asString((3L*1024L*1024L*1024L*1024L) -(512L*1024L*1024L*1024L), true, suffixes, '.', decimals));
        assertEquals("3.000 PiB", DataSize.asString((3L*1024L*1024L*1024L*1024L*1024L), true, suffixes, '.', decimals));
        assertEquals("7.9 EiB", DataSize.asString(Long.MAX_VALUE-1, true, suffixes, '.', decimals));


    }

    @Test
    public void testNoOfDigits() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        final Method method = getNoOfDigitsMethod();
        assertEquals(1, invokeNoOfDigitsMethod(method,1L));

        assertEquals(1,invokeNoOfDigitsMethod(method,1L));
        assertEquals(1,invokeNoOfDigitsMethod(method,9L));

        assertEquals(2,invokeNoOfDigitsMethod(method,10L));
        assertEquals(2,invokeNoOfDigitsMethod(method,12L));
        assertEquals(2,invokeNoOfDigitsMethod(method,99L));

        assertEquals(3,invokeNoOfDigitsMethod(method,100L));
        assertEquals(3,invokeNoOfDigitsMethod(method,123L));
        assertEquals(3,invokeNoOfDigitsMethod(method,999L));

        assertEquals(4,invokeNoOfDigitsMethod(method,1000L));
        assertEquals(4,invokeNoOfDigitsMethod(method,1234L));
        assertEquals(4,invokeNoOfDigitsMethod(method,9999L));

        assertEquals(5,invokeNoOfDigitsMethod(method,10000L));
        assertEquals(5,invokeNoOfDigitsMethod(method,12345L));
        assertEquals(5,invokeNoOfDigitsMethod(method,99999L));

        assertEquals(6,invokeNoOfDigitsMethod(method,100000L));
        assertEquals(6,invokeNoOfDigitsMethod(method,123456L));
        assertEquals(6,invokeNoOfDigitsMethod(method,999999L));

        assertEquals(7,invokeNoOfDigitsMethod(method,1000000L));
        assertEquals(7,invokeNoOfDigitsMethod(method,1234567L));
        assertEquals(7,invokeNoOfDigitsMethod(method,9999999L));

        assertEquals(8,invokeNoOfDigitsMethod(method,10000000L));
        assertEquals(8,invokeNoOfDigitsMethod(method,12345678L));
        assertEquals(8,invokeNoOfDigitsMethod(method,99999999L));

        assertEquals(9,invokeNoOfDigitsMethod(method,100000000L));
        assertEquals(9,invokeNoOfDigitsMethod(method,123456789L));
        assertEquals(9,invokeNoOfDigitsMethod(method,999999999L));

        assertEquals(10,invokeNoOfDigitsMethod(method,1000000000L));
        assertEquals(10,invokeNoOfDigitsMethod(method,1234567890L));
        assertEquals(10,invokeNoOfDigitsMethod(method,9999999999L));

        assertEquals(11,invokeNoOfDigitsMethod(method,10000000000L));
        assertEquals(11,invokeNoOfDigitsMethod(method,12345678901L));
        assertEquals(11,invokeNoOfDigitsMethod(method,99999999999L));

        assertEquals(12,invokeNoOfDigitsMethod(method,100000000000L));
        assertEquals(12,invokeNoOfDigitsMethod(method,123456789012L));
        assertEquals(12,invokeNoOfDigitsMethod(method,999999999999L));

        assertThrows(IllegalArgumentException.class,() -> invokeNoOfDigitsMethod(method,1000000000000L));

    }




    // ---------------------------------------------------------------------------------------
    // Utility methods for testing private static methods
    // (ugly !!)

    private Method getNoOfDigitsMethod() throws NoSuchMethodException {
        Method method = DataSize.class.getDeclaredMethod("noOfDigits", Long.TYPE);
        method.setAccessible(true);
        return method;
    }

    private Method getMinorForExabyteMethod() throws NoSuchMethodException {
        Method method = DataSize.class.getDeclaredMethod("getMinorForExabyte", Long.TYPE, Boolean.TYPE);
        method.setAccessible(true);
        return method;
    }


    private int invokeNoOfDigitsMethod(Method method, long value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        try {
            return (Integer) method.invoke(null, value);
        } catch (InvocationTargetException ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            throw ex;
        }
    }

    private int invokeGetMinorForExabyteMethod(Method method, long remainder, boolean useBinary) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        try {
            return (Integer) method.invoke(null, remainder, useBinary);
        } catch (InvocationTargetException ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            throw ex;
        }
    }

}