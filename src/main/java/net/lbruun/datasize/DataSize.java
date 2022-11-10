/*
 * Copyright (c) 2022  lbruun.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.lbruun.datasize;

import java.text.DecimalFormatSymbols;
import java.util.Objects;

/**
 * Provides means to format a data size value into a human-readable string.
 *
 * <p>
 * There are two ways to use this class:
 * <ul>
 *     <li>By using one of the static methods ({@code asString()}).</li>
 *     <li>By using it as an object, in which case it wraps a {@code long} and its associated
 *         pre-rendered human-readable string. The object presents itself by the human-readable
 *         string but sorts itself via the {@code long} value.</li>
 * </ul>
 *
 */
public class DataSize implements Comparable<DataSize> {

    protected static final long KILOBYTE_BIN = 1024;
    protected static final long MEGABYTE_BIN = 1024 * KILOBYTE_BIN;
    protected static final long GIGABYTE_BIN = 1024 * MEGABYTE_BIN;
    protected static final long TERABYTE_BIN = 1024 * GIGABYTE_BIN;
    protected static final long PETABYTE_BIN = 1024 * TERABYTE_BIN;
    protected static final long EXABYTE_BIN = 1024 * PETABYTE_BIN;
    private static final char DEFAULT_DEC_SEPARATOR = DecimalFormatSymbols.getInstance().getDecimalSeparator();
    private static final long[] POWERS_OF_TEN = {
            0L,
            10L,
            100L,
            1_000L,  // Kilobyte
            10_000L,
            100_000L,
            1_000_000L,  // Megabyte
            10_000_000L,
            100_000_000L,
            1_000_000_000L,  // Gigabyte
            10_000_000_000L,
            100_000_000_000L,
            1_000_000_000_000L,  // Terabyte
            10_000_000_000_000L,
            100_000_000_000_000L,
            1_000_000_000_000_000L,  // Petabyte
            10_000_000_000_000_000L,
            100_000_000_000_000_000L,
            1_000_000_000_000_000_000L  // Exabyte
    };
    private final long value;
    private final String valueStr;


    /**
     * Creates a wrapper for a pre-rendered data size value. Pre-rendering trades memory for speed.
     *
     * @see #asString(long, boolean, DataSizeUnitSuffixes, char, DataSizeUnitDecimals)
     */
    public DataSize(final long value,
                    final boolean useBinary,
                    final DataSizeUnitSuffixes suffixes,
                    char decimalSeparator,
                    DataSizeUnitDecimals decimals) {
        this.value = value;
        this.valueStr = asString(value, useBinary, suffixes, decimalSeparator, decimals);
    }

    /**
     * Creates a wrapper for a pre-rendered data size value. Pre-rendering trades memory for speed.
     *
     * <p>
     * Uses the default locale's decimal separator and
     * decimals settings as per {@link DataSizeUnitDecimals#DEFAULT DEFAULT}.
     *
     * @see #asString(long, boolean, DataSizeUnitSuffixes, char, DataSizeUnitDecimals)
     */
    public DataSize(final long value,
                    final boolean useBinary,
                    final DataSizeUnitSuffixes suffixes) {
        this(value, useBinary, suffixes, '\0', null);
    }

    /**
     * Formats a byte size value into a human-readable string.
     *
     * <p>
     * <b>{@code suffixes}:</b>
     * Note that due to the method's flexibility it is entirely possible to give it parameters which produce
     * non-conforming results, for example by using binary calculation with a {@code suffixes} set meant for decimal
     * calculation.
     *
     * <p>
     * The method is optimized for extreme speed rather than accuracy. It uses truncation, not rounding. This means, as an
     * example, that if 1099 is to be presented using parameters useBinary = false, with {@link DataSizeUnitSuffixes#SUFFIXES_SI
     * SI unit suffixes}, with {@link DataSizeUnitDecimals#DEFAULT default number of decimals} and with
     * decimalSeparator = '.', then it will be presented as {@code "1.0 KB"} rather than as {@code "1.1 KB"}.
     *
     * @param value            input value, must be positive or zero.
     * @param useBinary        {@code true} to use binary calculation (1 kibibyte = 1024 bytes). {@code false} to use
     *                         decimal calculation (1 kilobyte = 1000 bytes).
     * @param suffixes         suffixes to use, for example for kibibyte : {@code ' KiB'}.
     * @param decimalSeparator decimal separator char, usually either dot or comma. If {@code '\0'}, the decimal
     *                         separator for the default locale will be used.
     * @param decimals         settings for decimals, if {@code null} the {@link DataSizeUnitDecimals#DEFAULT DEFAULT}
     *                         is used.
     * @return human-readable string, never {@code null}.
     * @throws IllegalArgumentException if {@code value} is less than zero.
     */
    public static String asString(final long value, final boolean useBinary, final DataSizeUnitSuffixes suffixes, char decimalSeparator, DataSizeUnitDecimals decimals) {
        if (value == 0) {
            return "0" + suffixes.getSuffixesArray()[0];
        }
        if (value < 0) {
            throw new IllegalArgumentException("value must be >= 0");
        }
        Objects.requireNonNull(suffixes, "suffixes must be supplied");
        DataSizeUnit unit = DataSizeUnit.findUnitForValue(value, useBinary);
        final long divider = (useBinary) ? unit.getBinarySize() : unit.getDecimalSize();
        String suffix = suffixes.getSuffixesArray()[unit.ordinal()];
        long major = value / divider;
        int[] decimalsArr = (decimals == null) ? DataSizeUnitDecimals.DEFAULT.getDecimalsArray() : decimals.getDecimalsArray();
        int noOfDecimals = decimalsArr[unit.ordinal()];
        if (noOfDecimals == 0) {
            return major + suffix;
        }
        long remainder = value - (major * divider);
        long minor = (unit != DataSizeUnit.EXA) ?
                ((remainder * POWERS_OF_TEN[noOfDecimals]) / divider) :
                getMinorForExabyte(remainder, useBinary);

        StringBuilder sb = new StringBuilder(8);
        sb.append(major).append((decimalSeparator == '\0') ? DEFAULT_DEC_SEPARATOR : decimalSeparator);
        for (int z = 0; z < (noOfDecimals - noOfDigits(minor)); z++) {
            sb.append('0');
        }
        sb.append(minor).append(suffix);
        return sb.toString();
    }

    /**
     * Formats a byte size value into a human-readable string using binary calculation (1 kibibyte = 1024),
     * with dot character ({@code '.'}) as decimal separator, with {@link DataSizeUnitSuffixes#SUFFIXES_ISO80000
     * ISO-80000 unit suffixes} and with {@link DataSizeUnitDecimals#DEFAULT default number of decimals}.
     *
     * @see #asString(long, boolean, DataSizeUnitSuffixes, char, DataSizeUnitDecimals)
     * @param value            input value, must be positive or zero.
     * @return human-readable string, never {@code null}.
     * @throws IllegalArgumentException if {@code value} is less than zero.
     */
    public static String asStringBinary(final long value) {
        return asString(value, true, DataSizeUnitSuffixes.SUFFIXES_ISO80000, '.', null);
    }

    /**
     * Formats a byte size value into a human-readable string using decimal calculation (1 kilobyte = 1000),
     * with dot character ({@code '.'}) as decimal separator, with {@link DataSizeUnitSuffixes#SUFFIXES_SI
     * SI unit suffixes} and with {@link DataSizeUnitDecimals#DEFAULT default number of decimals}.
     *
     * @see #asString(long, boolean, DataSizeUnitSuffixes, char, DataSizeUnitDecimals)
     * @param value            input value, must be positive or zero.
     * @return human-readable string, never {@code null}.
     * @throws IllegalArgumentException if {@code value} is less than zero.
     */
    public static String asStringDecimal(final long value) {
        return asString(value, false, DataSizeUnitSuffixes.SUFFIXES_SI, '.', null);
    }

    /**
     * Gets the number of digits in the provided {@code long} value.
     *
     * <p>
     * Examples:
     * <pre>{@code
     *   123     ==>   3
     *  -123     ==>   3
     *   123456  ==>   6
     * }</pre>
     *
     * <p>
     * The method is optimized for speed. It solely uses integer comparison rather than division or any other
     * mathematical operation. It uses a divide-and-conquer algorithm which is skewed towards lower numbers because the
     * expectation is that lower numbers will be more likely than higher numbers.
     *
     * @param value any value between -10<sup>12</sup> (excluding) and 10<sup>12</sup> (excluding)
     * @return no of digits in {@code value}
     * @throws IllegalArgumentException if the {@code value} has more than 12 digits.
     */
    private static int noOfDigits(long value) {
        long x = (value < 0) ? (value * -1) : value;
        if (x < 1_000_000L) { // 6 or less
            if (x < 1_000L) { // 3 or less
                if (x < 10L) {
                    return 1;
                } else if (x < 100L) {
                    return 2;
                } else {
                    return 3;
                }
            } else { // 4 to 6
                if (x < 10_000L) {
                    return 4;
                } else if (x < 100_000L) {
                    return 5;
                } else {
                    return 6;
                }
            }
        } else { // 7 or more
            if (x < 1_000_000_000_000L) { // 7 to 12
                if (x < 1_000_000_000L) { // 7 to 9
                    if (x < 10_000_000L) {
                        return 7;
                    } else if (x < 100_000_000L) {
                        return 8;
                    } else {
                        return 9;
                    }
                } else { // 10 to 12
                    if (x < 10_000_000_000L) {
                        return 10;
                    } else if (x < 100_000_000_000L) {
                        return 11;
                    } else {
                        return 12;
                    }
                }
            } else {
                throw new IllegalArgumentException("x is too large");
            }
        }
    }

    /**
     * Gets minor value (the value after the decimal point) when in the exabyte range. This method exists because we
     * cannot use standard integer multiply-and-divide to accomplish digit truncation when we are dealing with numbers
     * this big, because the multiply would cause overflow. Instead, the method below is used. The downside is that it
     * will only return a value between 0 and 9, hence the consequence is that exabyte values cannot be represented with
     * more than one decimal.
     */
    private static long getMinorForExabyte(long remainder, boolean useBinary) {
        if (remainder == 0) {
            return 0;
        }
        if (useBinary) {
            if (remainder < 114841790497947648L) {         // 102 pebibytes
                return 0;
            } else if (remainder < 230809480902737920L) {  // 205 pebibytes
                return 1;
            } else if (remainder < 345651271400685568L) {  // 307 pebibytes
                return 2;
            } else if (remainder < 461618961805475840L) {  // 410 pebibytes
                return 3;
            } else if (remainder < 576460752303423488L) {  // 512 pebibytes
                return 4;
            } else if (remainder < 691302542801371136L) {  // 614 pebibytes
                return 5;
            } else if (remainder < 807270233206161408L) {  // 717 pebibytes
                return 6;
            } else if (remainder < 922112023704109056L) {  // 819 pebibytes
                return 7;
            } else if (remainder < 1038079714108899328L) { // 922 pebibytes
                return 8;
            } else {
                return 9;
            }
        } else {
            if (remainder < 100_000_000_000_000_000L) {
                return 0;
            } else if (remainder < 200_000_000_000_000_000L) {
                return 1;
            } else if (remainder < 300_000_000_000_000_000L) {
                return 2;
            } else if (remainder < 400_000_000_000_000_000L) {
                return 3;
            } else if (remainder < 500_000_000_000_000_000L) {
                return 4;
            } else if (remainder < 600_000_000_000_000_000L) {
                return 5;
            } else if (remainder < 700_000_000_000_000_000L) {
                return 6;
            } else if (remainder < 800_000_000_000_000_000L) {
                return 7;
            } else if (remainder < 900_000_000_000_000_000L) {
                return 8;
            } else {
                return 9;
            }
        }
    }

    /**
     * Gets the wrapped value.
     */
    public long getValue() {
        return value;
    }

    /**
     * Gets pre-rendered human-readable string for {@link #getValue() value}.
     *
     * @return human-readable string
     */
    @Override
    public String toString() {
        return valueStr;
    }

    /**
     * Compares one {@code DataSize} to another by comparing their {@link #getValue() wrapped values}.
     */
    @Override
    public int compareTo(DataSize o) {
        return Long.compare(value, o.getValue());
    }

}
