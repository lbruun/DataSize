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

import java.util.Objects;


/**
 * Defines the unit suffixes to use when formatting a data size value. For example, in the output string
 * {@code "2.56 MiB"}, the value {@code " MiB"} is the suffix.
 *
 * <p>
 * Use the {@link #builder() builder} to create an instance.
 *
 * @see DataSize#asString(long, boolean, DataSizeUnitSuffixes, char, DataSizeUnitDecimals)
 */
public class DataSizeUnitSuffixes {


    /**
     * Unit suffixes according to International System of Units (SI). These are recommended for use with decimal
     * calculation.
     *
     * <p>
     * Intended usage: DECIMAL calculation.
     *
     * <p>
     * <table class="striped" style="text-align:left; margin-left:2em">
     * <caption style="display:none">SI Suffixes</caption>
     * <thead>
     * <tr>
     *   <th scope="col">Unit type<br>name
     *   <th scope="col">Suffix
     * </tr>
     * </thead>
     * <tbody>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#BYTE BYTE}</th>
     *     <td>{@code " B"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#KILO KILO}</th>
     *     <td>{@code " kB"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#MEGA MEGA}</th>
     *     <td>{@code " MB"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#GIGA GIGA}</th>
     *     <td>{@code " GB"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#TERA TERA}</th>
     *     <td>{@code " TB"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#PETA PETA}</th>
     *     <td>{@code " PB"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#EXA EXA}</th>
     *     <td>{@code " EB"}</td>
     *   </tr>
     * </tbody>
     * </table>
     */
    public static final DataSizeUnitSuffixes SUFFIXES_SI = new DataSizeUnitSuffixes(
            " B",
            " kB",
            " MB",
            " GB",
            " TB",
            " PB",
            " EB");

    /**
     * Unit suffixes according to ISO 80000 / International Electrotechnical Commission (IEC). These are recommended for
     * use with binary calculation.
     *
     * <p>
     * Intended usage: BINARY calculation.
     *
     * <p>
     * <table class="striped" style="text-align:left; margin-left:2em">
     * <caption style="display:none">IEC suffixes</caption>
     * <thead>
     * <tr>
     *   <th scope="col">Unit type<br>name
     *   <th scope="col">Suffix
     * </tr>
     * </thead>
     * <tbody>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#BYTE BYTE}</th>
     *     <td>{@code " B"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#KILO KILO}</th>
     *     <td>{@code " KiB"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#MEGA MEGA}</th>
     *     <td>{@code " MiB"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#GIGA GIGA}</th>
     *     <td>{@code " GiB"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#TERA TERA}</th>
     *     <td>{@code " TiB"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#PETA PETA}</th>
     *     <td>{@code " PiB"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#EXA EXA}</th>
     *     <td>{@code " EiB"}</td>
     *   </tr>
     * </tbody>
     * </table>
     */
    public static final DataSizeUnitSuffixes SUFFIXES_ISO80000 = new DataSizeUnitSuffixes(
            " B",
            " KiB",
            " MiB",
            " GiB",
            " TiB",
            " PiB",
            " EiB");

    /**
     * Unit suffixes known as <i>customary</i>. These are used for example by the Microsoft Windows operating system
     * together with binary calculation.
     *
     * <p>
     * Intended usage: BINARY calculation.
     *
     * <p>
     * <table class="striped" style="text-align:left; margin-left:2em">
     * <caption style="display:none">Customary suffixes</caption>
     * <thead>
     * <tr>
     *   <th scope="col">Unit type<br>name
     *   <th scope="col">Suffix
     * </tr>
     * </thead>
     * <tbody>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#BYTE BYTE}</th>
     *     <td>{@code " B"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#KILO KILO}</th>
     *     <td>{@code " KB"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#MEGA MEGA}</th>
     *     <td>{@code " MB"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#GIGA GIGA}</th>
     *     <td>{@code " GB"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#TERA TERA}</th>
     *     <td>{@code " TB"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#PETA PETA}</th>
     *     <td>{@code " PB"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#EXA EXA}</th>
     *     <td>{@code " EB"}</td>
     *   </tr>
     * </tbody>
     * </table>
     */
    public static final DataSizeUnitSuffixes SUFFIXES_CUSTOMARY = new DataSizeUnitSuffixes(
            " B",
            " KB",
            " MB",
            " GB",
            " TB",
            " PB",
            " EB");

    /**
     * Unit suffixes used by GNU/Linux {@code ls -h} command together with binary calculation. This is a very dense
     * format with no space between the digits and the suffix.
     *
     * <p>
     * Intended usage: BINARY calculation.
     *
     * <p>
     * <table class="striped" style="text-align:left; margin-left:2em">
     * <caption style="display:none">GNU ls suffixes</caption>
     * <thead>
     * <tr>
     *   <th scope="col">Unit type<br>name
     *   <th scope="col">Suffix
     * </tr>
     * </thead>
     * <tbody>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#BYTE BYTE}</th>
     *     <td>{@code ""} (empty)</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#KILO KILO}</th>
     *     <td>{@code "K"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#MEGA MEGA}</th>
     *     <td>{@code "M"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#GIGA GIGA}</th>
     *     <td>{@code "G"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#TERA TERA}</th>
     *     <td>{@code "T"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#PETA PETA}</th>
     *     <td>{@code "P"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#EXA EXA}</th>
     *     <td>{@code "E"}</td>
     *   </tr>
     * </tbody>
     * </table>
     */
    public static final DataSizeUnitSuffixes SUFFIXES_GNU = new DataSizeUnitSuffixes(
            "",
            "K",
            "M",
            "G",
            "T",
            "P",
            "E");

    /**
     * Unit suffixes used by GNU/Linux {@code ls --si} command together with decimal calculation. This is a very dense
     * format with no space between the digits and the suffix.
     *
     * <p>
     * Intended usage: DECIMAL calculation.
     *
     * <p>
     * <table class="striped" style="text-align:left; margin-left:2em">
     * <caption style="display:none">GNU ls suffixes (SI)</caption>
     * <thead>
     * <tr>
     *   <th scope="col">Unit type<br>name
     *   <th scope="col">Suffix
     * </tr>
     * </thead>
     * <tbody>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#BYTE BYTE}</th>
     *     <td>{@code ""} (empty)</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#KILO KILO}</th>
     *     <td>{@code "k"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#MEGA MEGA}</th>
     *     <td>{@code "M"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#GIGA GIGA}</th>
     *     <td>{@code "G"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#TERA TERA}</th>
     *     <td>{@code "T"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#PETA PETA}</th>
     *     <td>{@code "P"}</td>
     *   </tr>
     *   <tr>
     *     <th scope="row">{@link DataSizeUnit#EXA EXA}</th>
     *     <td>{@code "E"}</td>
     *   </tr>
     * </tbody>
     * </table>
     */
    public static final DataSizeUnitSuffixes SUFFIXES_GNU_SI = new DataSizeUnitSuffixes(
            "",
            "k",
            "M",
            "G",
            "T",
            "P",
            "E");


    private final String[] suffixesArray;

    private DataSizeUnitSuffixes(String byteSuffix, String kilobyteSuffix, String megabyteSuffix, String gigabyteSuffix, String terabyteSuffix, String petabyteSuffix, String exabyteSuffix) {
        suffixesArray = new String[]{byteSuffix, kilobyteSuffix, megabyteSuffix, gigabyteSuffix, terabyteSuffix, petabyteSuffix, exabyteSuffix};
    }

    /**
     * Builder for {@code DataSizeUnitSuffixes}.
     */
    public static DataSizeUnitSuffixes.Builder builder() {
        return new DataSizeUnitSuffixes.Builder();
    }

    /**
     * Gets the suffixes as an array, always of length 7. The elements are ordered: The first element
     * is the suffix to use for {@code BYTE} unit and the last element is the suffix to use {@code EXA/EXBI} unit.
     */
    public String[] getSuffixesArray() {
        return suffixesArray;
    }

    /**
     * Builder for {@code DataSizeUnitSuffixes}.
     *
     * <p>
     * Before creating a new suffixes set it may be worth exploring the pre-defined ones:  {@link #SUFFIXES_SI},
     * {@link #SUFFIXES_ISO80000}, {@link #SUFFIXES_CUSTOMARY}, etc.
     */
    public static class Builder {

        private String byteSuffix = SUFFIXES_CUSTOMARY.getSuffixesArray()[0];
        private String kilobyteSuffix = SUFFIXES_CUSTOMARY.getSuffixesArray()[1];
        private String megabyteSuffix = SUFFIXES_CUSTOMARY.getSuffixesArray()[2];
        private String gigabyteSuffix = SUFFIXES_CUSTOMARY.getSuffixesArray()[3];
        private String terabyteSuffix = SUFFIXES_CUSTOMARY.getSuffixesArray()[4];
        private String petabyteSuffix = SUFFIXES_CUSTOMARY.getSuffixesArray()[5];
        private String exabyteSuffix = SUFFIXES_CUSTOMARY.getSuffixesArray()[6];

        private Builder() {
        }

        /**
         * Sets suffix to use for {@code BYTE}.
         *
         * <p>
         * Default is {@link DataSizeUnitSuffixes#SUFFIXES_CUSTOMARY}
         */
        public DataSizeUnitSuffixes.Builder withByteSuffix(String suffix) {
            Objects.requireNonNull(suffix, "suffix cannot be null");
            this.byteSuffix = suffix;
            return this;

        }

        /**
         * Sets suffix to use for {@code KILO/KIBI unit}.
         *
         * <p>
         * Default is {@link DataSizeUnitSuffixes#SUFFIXES_CUSTOMARY}
         */
        public DataSizeUnitSuffixes.Builder withKilobyteSuffix(String suffix) {
            Objects.requireNonNull(suffix, "suffix cannot be null");
            this.kilobyteSuffix = suffix;
            return this;
        }

        /**
         * Sets suffix to use for {@code MEGABYTE}.
         *
         * <p>
         * Default is {@link DataSizeUnitSuffixes#SUFFIXES_CUSTOMARY}
         */
        public DataSizeUnitSuffixes.Builder withMegabyteSuffix(String suffix) {
            Objects.requireNonNull(suffix, "suffix cannot be null");
            this.megabyteSuffix = suffix;
            return this;
        }

        /**
         * Sets suffix to use for {@code GIGABYTE}.
         *
         * <p>
         * Default is {@link DataSizeUnitSuffixes#SUFFIXES_CUSTOMARY}
         */
        public DataSizeUnitSuffixes.Builder withGigabyteSuffix(String suffix) {
            Objects.requireNonNull(suffix, "suffix cannot be null");
            this.gigabyteSuffix = suffix;
            return this;
        }

        /**
         * Sets suffix to use for {@code TERABYTE}.
         *
         * <p>
         * Default is {@link DataSizeUnitSuffixes#SUFFIXES_CUSTOMARY}
         */
        public DataSizeUnitSuffixes.Builder withTerabyteSuffix(String suffix) {
            Objects.requireNonNull(suffix, "suffix cannot be null");
            this.terabyteSuffix = suffix;
            return this;
        }

        /**
         * Sets suffix to use for {@code PETABYTE}.
         *
         * <p>
         * Default is {@link DataSizeUnitSuffixes#SUFFIXES_CUSTOMARY}
         */
        public DataSizeUnitSuffixes.Builder withPetabyteSuffix(String suffix) {
            Objects.requireNonNull(suffix, "suffix cannot be null");
            this.petabyteSuffix = suffix;
            return this;
        }

        /**
         * Sets suffix to use for {@code EXABYTE}.
         *
         * <p>
         * Default is {@link DataSizeUnitSuffixes#SUFFIXES_CUSTOMARY}
         */
        public DataSizeUnitSuffixes.Builder withExabyteSuffix(String suffix) {
            Objects.requireNonNull(suffix, "suffix cannot be null");
            this.exabyteSuffix = suffix;
            return this;
        }

        public DataSizeUnitSuffixes build() {
            return new DataSizeUnitSuffixes(byteSuffix, kilobyteSuffix, megabyteSuffix, gigabyteSuffix, terabyteSuffix, petabyteSuffix, exabyteSuffix);
        }

    }
}




