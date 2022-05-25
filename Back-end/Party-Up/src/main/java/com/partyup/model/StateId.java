package com.partyup.model;

import java.io.Serializable;
import java.util.Objects;

public class StateId implements Serializable {

    private String name;

    private Country country;

    public StateId(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StateId stateId = (StateId) o;

        if (!Objects.equals(name, stateId.name)) return false;
        return Objects.equals(country, stateId.country);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}
