package io.felux.pouches.manager;

import io.felux.pouches.api.Pouch;

import java.util.ArrayList;
import java.util.List;

public class PouchManager {

    private List<Pouch> pouches = new ArrayList<>();

    public PouchManager() {
    }

    public PouchManager(List<Pouch> pouches) {
        this.pouches = pouches;
    }

    public List<Pouch> getPouches() {
        return pouches;
    }

    public Pouch getPouch(String id) {
        for (Pouch pouch : pouches) {
            if (pouch.getId().equalsIgnoreCase(id)) {
                return pouch;
            }
        }

        return null;
    }
}
