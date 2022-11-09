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
 * Defines the number of decimals to use when formatting
 * a data size value. Use the {@link #builder() builder} to create an instance.
 *
 * @see DataSize#asString(long, boolean, DataSizeUnitSuffixes, char, DataSizeUnitDecimals) 
 */
public class DataSizeUnitDecimals {
    public static final int DEFAULT_DECIMALS_KILO = 0;
    public static final int DEFAULT_DECIMALS_MEGA = 1;
    public static final int DEFAULT_DECIMALS_GIGA = 2;
    public static final int DEFAULT_DECIMALS_TERA = 3;
    public static final int DEFAULT_DECIMALS_PETA = 3;
    public static final int DEFAULT_DECIMALS_EXA = 1;

    /**
     * Default set of decimals.
     *
     * <table class="striped" style="text-align:left; margin-left:2em">
     * <caption style="display:none">Decimals</caption>
     * <thead>
     * <tr>
     *   <th scope="col">Unit type<br>name
     *   <th scope="col">Decimals
     * </tr>
     * </thead>
     * <tbody>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#BYTE BYTE (one)}</th>
     *     <td>(always {@code 0}, cannot be set)</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#KILO KILO/KIBI}</th>
     *     <td>{@link #DEFAULT_DECIMALS_KILO}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#MEGA MEGA/MEBI}</th>
     *     <td>{@link #DEFAULT_DECIMALS_MEGA}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#GIGA GIGA/GIBI}</th>
     *     <td>{@link #DEFAULT_DECIMALS_GIGA}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#TERA TERA/TEBI}</th>
     *     <td>{@link #DEFAULT_DECIMALS_TERA}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#PETA PETA/PEBI}</th>
     *     <td>{@link #DEFAULT_DECIMALS_PETA}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#EXA EXA/EXBI}</th>
     *     <td>{@link #DEFAULT_DECIMALS_EXA}</td>
     *   </tr>
     * </tbody>
     * </table>
     *
     */
    public static final DataSizeUnitDecimals DEFAULT = builder().build();

    private final int[] decimalsArray;

    private DataSizeUnitDecimals(int kilobyteDecimals, int megabyteDecimals, int gigabyteDecimals, int terabyteDecimals, int petabyteDecimals, int exabyteDecimals) {
        decimalsArray = new int[]{
                0,
                kilobyteDecimals,
                megabyteDecimals,
                gigabyteDecimals,
                terabyteDecimals,
                petabyteDecimals,
                exabyteDecimals};
    }

    public static DataSizeUnitDecimals.Builder builder() {
        return new DataSizeUnitDecimals.Builder();
    }

    public int[] getDecimalsArray() {
        return decimalsArray;
    }

    /**
     * Builder for {@code DataSizeUnitDecimals}.
     */
    public static class Builder {

        private int kilobyteDecs = DEFAULT_DECIMALS_KILO;
        private int megabyteDecs = DEFAULT_DECIMALS_MEGA;
        private int gigabyteDecs = DEFAULT_DECIMALS_GIGA;
        private int terabyteDecs = DEFAULT_DECIMALS_TERA;
        private int petabyteDecs = DEFAULT_DECIMALS_PETA;
        private int exabyteDecs = DEFAULT_DECIMALS_EXA;

        private Builder() {
        }

        /**
         * Sets number of decimals to use for {@code KILO/KIBI unit}.
         *
         * <p>
         * Default is {@link DataSizeUnitDecimals#DEFAULT_DECIMALS_KILO}.
         *
         * @param decimals allowed values: 0 to 6
         */
        public DataSizeUnitDecimals.Builder withKilobyteDecimals(int decimals) {
            validateDecimals(decimals, 0, 6);
            this.kilobyteDecs = decimals;
            return this;
        }

        /**
         * Sets number of decimals to use for {@code MEGA/MEBI unit}.
         *
         * <p>
         * Default is {@link DataSizeUnitDecimals#DEFAULT_DECIMALS_MEGA}.
         *
         * @param decimals allowed values: 0 to 6
         */
        public DataSizeUnitDecimals.Builder withMegabyteDecimals(int decimals) {
            validateDecimals(decimals, 0, 6);
            this.megabyteDecs = decimals;
            return this;
        }

        /**
         * Sets number of decimals to use for {@code GIGA/GIBI unit}.
         *
         * <p>
         * Default is {@link DataSizeUnitDecimals#DEFAULT_DECIMALS_GIGA}.
         *
         * @param decimals allowed values: 0 to 6
         */
        public DataSizeUnitDecimals.Builder withGigabyteDecimals(int decimals) {
            validateDecimals(decimals, 0, 6);
            this.gigabyteDecs = decimals;
            return this;
        }

        /**
         * Sets number of decimals to use for {@code TERA/TEBI unit}.
         *
         * <p>
         * Default is {@link DataSizeUnitDecimals#DEFAULT_DECIMALS_TERA}.
         *
         * @param decimals allowed values: 0 to 6
         */
        public DataSizeUnitDecimals.Builder withTerabyteDecimals(int decimals) {
            validateDecimals(decimals, 0, 6);
            this.terabyteDecs = decimals;
            return this;
        }

        /**
         * Sets number of decimals to use for {@code PETA/PEBI unit}.
         *
         * <p>
         * Default is {@link DataSizeUnitDecimals#DEFAULT_DECIMALS_PETA}.
         *
         * @param decimals allowed values: 0 to 3
         */
        public DataSizeUnitDecimals.Builder withPetabyteDecimals(int decimals) {
            validateDecimals(decimals, 0, 3);
            this.petabyteDecs = decimals;
            return this;
        }

        /**
         * Sets number of decimals to use for {@code EXA/EXBI unit}.
         *
         * <p>
         * Default is {@link DataSizeUnitDecimals#DEFAULT_DECIMALS_EXA}.
         *
         * <p>
         * Note that due to the risk of integer overflow during calculations, the maximum allowed
         * number of decimals for the {@code EXABYTE} unit is 1 (one).
         *
         * @param decimals allowed values: 0 or 1
         */
        public DataSizeUnitDecimals.Builder withExabyteDecimals(int decimals) {
            validateDecimals(decimals, 0, 1);
            this.exabyteDecs = decimals;
            return this;
        }

        public DataSizeUnitDecimals build() {
            return new DataSizeUnitDecimals(
                    kilobyteDecs,
                    megabyteDecs,
                    gigabyteDecs,
                    terabyteDecs,
                    petabyteDecs,
                    exabyteDecs);
        }

        private void validateDecimals(int decimals, int min, int max) {
            if (!(decimals >= min && decimals <= max)) {
                throw new IllegalArgumentException("decimals value must be in the range [" + min + "," + max + "]");
            }
        }
    }
}



