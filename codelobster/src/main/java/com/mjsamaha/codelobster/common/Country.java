package com.mjsamaha.codelobster.common;

import java.util.Arrays;
import java.util.List;

public enum Country {

	// North America
	USA("United States",       Continent.NORTH_AMERICA),
    CANADA("Canada",           Continent.NORTH_AMERICA),
    MEXICO("Mexico",           Continent.NORTH_AMERICA),
    
    // South America
    BRAZIL("Brazil",           Continent.SOUTH_AMERICA),
    ARGENTINA("Argentina",     Continent.SOUTH_AMERICA),
    CHILE("Chile",             Continent.SOUTH_AMERICA),
    COLOMBIA("Colombia",       Continent.SOUTH_AMERICA),
    PERU("Peru",               Continent.SOUTH_AMERICA),
    
    // Europe
    UK("United Kingdom",       Continent.EUROPE),
    GERMANY("Germany",         Continent.EUROPE),
    FRANCE("France",           Continent.EUROPE),
    ITALY("Italy",             Continent.EUROPE),
    SPAIN("Spain",             Continent.EUROPE),
    NETHERLANDS("Netherlands", Continent.EUROPE),
    SWEDEN("Sweden",           Continent.EUROPE),
    SWITZERLAND("Switzerland", Continent.EUROPE),
    RUSSIA("Russia",           Continent.EUROPE),
    NORWAY("Norway",           Continent.EUROPE),
    DENMARK("Denmark",         Continent.EUROPE),
    FINLAND("Finland",         Continent.EUROPE),
    POLAND("Poland",           Continent.EUROPE),
    PORTUGAL("Portugal",       Continent.EUROPE),
    AUSTRIA("Austria",         Continent.EUROPE),
    BELGIUM("Belgium",         Continent.EUROPE),
    GREECE("Greece",           Continent.EUROPE),
    CZECH_REPUBLIC("Czech Republic", Continent.EUROPE),
    HUNGARY("Hungary",         Continent.EUROPE),
    ROMANIA("Romania",         Continent.EUROPE),
    UKRAINE("Ukraine",         Continent.EUROPE),
    
    // Asia
    INDIA("India",             Continent.ASIA),
    CHINA("China",             Continent.ASIA),
    JAPAN("Japan",             Continent.ASIA),
    SOUTH_KOREA("South Korea", Continent.ASIA),
    SINGAPORE("Singapore",     Continent.ASIA),
    INDONESIA("Indonesia",     Continent.ASIA),
    MALAYSIA("Malaysia",       Continent.ASIA),
    THAILAND("Thailand",       Continent.ASIA),
    VIETNAM("Vietnam",         Continent.ASIA),
    PHILIPPINES("Philippines", Continent.ASIA),
    PAKISTAN("Pakistan",       Continent.ASIA),
    BANGLADESH("Bangladesh",   Continent.ASIA),
    SRI_LANKA("Sri Lanka",     Continent.ASIA),
    TAIWAN("Taiwan",           Continent.ASIA),
    HONG_KONG("Hong Kong",     Continent.ASIA),
    SAUDI_ARABIA("Saudi Arabia", Continent.ASIA),
    UAE("United Arab Emirates", Continent.ASIA),
    ISRAEL("Israel",           Continent.ASIA),
    TURKEY("Turkey",           Continent.ASIA),
    IRAN("Iran",               Continent.ASIA),
    IRAQ("Iraq",               Continent.ASIA),
    QATAR("Qatar",             Continent.ASIA),
    KUWAIT("Kuwait",           Continent.ASIA),

    // Africa
    SOUTH_AFRICA("South Africa", Continent.AFRICA),
    NIGERIA("Nigeria",         Continent.AFRICA),
    KENYA("Kenya",             Continent.AFRICA),
    EGYPT("Egypt",             Continent.AFRICA),
    GHANA("Ghana",             Continent.AFRICA),
    ETHIOPIA("Ethiopia",       Continent.AFRICA),
    TANZANIA("Tanzania",       Continent.AFRICA),
    MOROCCO("Morocco",         Continent.AFRICA),
    ALGERIA("Algeria",         Continent.AFRICA),
    ANGOLA("Angola",           Continent.AFRICA),

    // Oceania
    AUSTRALIA("Australia",     Continent.OCEANIA),
    NEW_ZEALAND("New Zealand", Continent.OCEANIA),
    FIJI("Fiji",               Continent.OCEANIA),
    PAPUA_NEW_GUINEA("Papua New Guinea", Continent.OCEANIA);
	

    private final String displayName;
    private final Continent continent;

    Country(String displayName, Continent continent) {
        this.displayName = displayName;
        this.continent = continent;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Continent getContinent() {
        return continent;
    }

    public static List<Country> byContinent(Continent continent) {
        return Arrays.stream(values())
                .filter(c -> c.continent == continent)
                .toList();
    }

    public enum Continent {
        NORTH_AMERICA("North America"),
        SOUTH_AMERICA("South America"),
        EUROPE("Europe"),
        ASIA("Asia"),
        AFRICA("Africa"),
        OCEANIA("Oceania");

        private final String displayName;

        Continent(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

}