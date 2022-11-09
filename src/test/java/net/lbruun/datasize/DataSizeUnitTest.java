package net.lbruun.datasize;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataSizeUnitTest {

    @Test
    public void testFindUnitForValue() {

        assertEquals(DataSizeUnit.BYTE, DataSizeUnit.findUnitForValue(3, true));
        assertEquals(DataSizeUnit.BYTE, DataSizeUnit.findUnitForValue(1023L, true));
        assertEquals(DataSizeUnit.KILO, DataSizeUnit.findUnitForValue(1024L, true));
        assertEquals(DataSizeUnit.MEGA, DataSizeUnit.findUnitForValue(1024L*1024L, true));
        assertEquals(DataSizeUnit.MEGA, DataSizeUnit.findUnitForValue((1024L*1024L)+2, true));
        assertEquals(DataSizeUnit.TERA, DataSizeUnit.findUnitForValue((1024L*1024L*1024L*1024L*1024L)-1, true));
        assertEquals(DataSizeUnit.PETA, DataSizeUnit.findUnitForValue((1024L*1024L*1024L*1024L*1024L), true));
        assertEquals(DataSizeUnit.EXA, DataSizeUnit.findUnitForValue((1024L*1024L*1024L*1024L*1024L*1024)*3L, true));
        assertEquals(DataSizeUnit.EXA, DataSizeUnit.findUnitForValue(Long.MAX_VALUE, true));

        assertEquals(DataSizeUnit.BYTE, DataSizeUnit.findUnitForValue(3, false));
        assertEquals(DataSizeUnit.BYTE, DataSizeUnit.findUnitForValue(999L, false));
        assertEquals(DataSizeUnit.KILO, DataSizeUnit.findUnitForValue(1000L, false));
        assertEquals(DataSizeUnit.MEGA, DataSizeUnit.findUnitForValue((1000L*1000L), false));
    }

}