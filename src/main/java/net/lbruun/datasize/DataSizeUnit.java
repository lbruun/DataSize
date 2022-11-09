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

/**
 * Units for measuring data sizes, for example file sizes.
 *
 *
 * <table class="striped" style="text-align:left; margin-left:2em">
 * <caption style="display:none">Units</caption>
 * <thead>
 * <tr>
 *   <th scope="col">Unit type<br>name
 *   <th scope="col">Value decimal
 *   <th scope="col">Value binary
 * </tr>
 * </thead>
 * <tbody>
 *   <tr>
 *     <th scope="row">{@link #BYTE}</th>
 *     <td>1</td>
 *     <td>1</td>
 *   </tr>
 *   <tr>
 *     <th scope="row">{@link #KILO}</th>
 *     <td>10<sup>3</sup></td>
 *     <td>1024</td>
 *   </tr>
 *   <tr>
 *     <th scope="row">{@link #MEGA}</th>
 *     <td>10<sup>6</sup></td>
 *     <td>1024<sup>2</sup></td>
 *   </tr>
 *   <tr>
 *     <th scope="row">{@link #GIGA}</th>
 *     <td>10<sup>9</sup></td>
 *     <td>1024<sup>3</sup></td>
 *   </tr>
 *   <tr>
 *     <th scope="row">{@link #TERA}</th>
 *     <td>10<sup>12</sup></td>
 *     <td>1024<sup>4</sup></td>
 *   </tr>
 *   <tr>
 *     <th scope="row">{@link #PETA}</th>
 *     <td>10<sup>15</sup></td>
 *     <td>1024<sup>5</sup></td>
 *   </tr>
 *   <tr>
 *     <th scope="row">{@link #EXA}</th>
 *     <td>10<sup>18</sup></td>
 *     <td>1024<sup>6</sup></td>
 *   </tr>
 * </tbody>
 * </table>
 *
 *
 */
public enum DataSizeUnit {

    // The order of declaration matters !!! (smaller to bigger)
    /**
     * Byte. By definition, this unit cannot have decimals.
     */
    BYTE(1, 1, 0),
    /**
     * Kilobyte (1000 bytes) or kibibyte (1024 bytes)
     */
    KILO(1_000L,  DataSize.KILOBYTE_BIN,  3),
    /**
     * Megabyte (1000<sup>2</sup> bytes) or mebibyte (1024<sup>2</sup> bytes)
     */
    MEGA(1_000_000L, DataSize.MEGABYTE_BIN, 6),
    /**
     * Gigabyte (1000<sup>3</sup> bytes) or gibibyte (1024<sup>3</sup> bytes)
     */
    GIGA(1_000_000_000L, DataSize.GIGABYTE_BIN, 9),
    /**
     * Terabyte (1000<sup>4</sup> bytes) or tebibyte (1024<sup>4</sup> bytes)
     */
    TERA(1_000_000_000_000L, DataSize.TERABYTE_BIN, 12),
    /**
     * Petabyte (1000<sup>5</sup> bytes) or pebibyte (1024<sup>5</sup> bytes)
     */
    PETA(1_000_000_000_000_000L, DataSize.PETABYTE_BIN, 15),
    /**
     * Exabyte (1<sup>6</sup> bytes) or exbibyte (1024<sup>6</sup> bytes)
     */
    EXA( 1_000_000_000_000_000_000L, DataSize.EXABYTE_BIN, 18)
    ;


    private final long decimalSize;
    private final long binarySize;
    private final int decimalExponentBase10;



    DataSizeUnit(long decimalSize, long binarySize, int decimalExponentBase10) {
        this.decimalSize = decimalSize;
        this.binarySize = binarySize;
        this.decimalExponentBase10 = decimalExponentBase10;
    }

    /**
     * Gets this unit's size in the decimal system. For example, for
     * {@code MEGABYTE} the returned value is {@code 1000000}.
     * @return size in decimal system.
     */
    public long getDecimalSize() {
        return decimalSize;
    }

    /**
     * Gets this unit's size in the binary system. For example, for
     * {@code MEGABYTE} the returned value is {@code 1048576} (1024*1024).
     * @return size in binary system.
     */
    public long getBinarySize() {
        return binarySize;
    }

    /**
     * Gets the exponent, {@code x}, for the expression 10<sup>x</sup> = {@link #getDecimalSize() decimalSize}.
     *
     * <p>
     * For example, if {@link #getDecimalSize() decimalSize} is 1000 then the returned value is 3.
     * @return exponent in base 10 expression
     */
    public int getDecimalExponentBase10() {
        return decimalExponentBase10;
    }

    /**
     * Finds and returns the appropriate measurement unit for a size value.
     * @param value
     * @param useBinary {@code true} to use binary calculation (1 kilobyte = 1024 bytes).
     *              {@code false} to use decimal calculation (1 kilobyte = 1000 bytes).
     * @return data size unit which is appropriate for measuring {@code value} in.
     */
    public static DataSizeUnit findUnitForValue(long value, boolean useBinary) {
        int len = DataSizeUnit.values().length;
        for (int i = 0; i < len - 1; i++) {
            long size = (useBinary) ? DataSizeUnit.values()[i + 1].getBinarySize() : DataSizeUnit.values()[i + 1].getDecimalSize();
            if (value < size) {
                return DataSizeUnit.values()[i];
            }
        }
        return DataSizeUnit.values()[len - 1];
    }
}
