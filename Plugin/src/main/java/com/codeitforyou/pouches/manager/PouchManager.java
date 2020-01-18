package com.codeitforyou.pouches.manager;

import com.codeitforyou.pouches.api.Pouch;

import java.util.List;

public class PouchManager {
    private List<Pouch> pouches;

    public PouchManager(List<Pouch> pouches) {
        this.pouches = pouches;
    }

    public List<Pouch> getPouches() {
        return pouches;
    }

    public Pouch getPouch(String id) {
        return pouches.stream().filter(pouch -> pouch.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
}
