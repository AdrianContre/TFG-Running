package com.example.API_Running.dtos;

import java.util.ArrayList;

public class UserZonesDTO {
    private Integer maxZ1;
    private Integer maxZ2;
    private Integer maxZ3;
    private Integer maxZ4;
    private Integer maxZ5;

    public UserZonesDTO(ArrayList<Integer> zones) {
        this.maxZ1 = zones.get(0);
        this.maxZ2 = zones.get(1);
        this.maxZ3 = zones.get(2);
        this.maxZ4 = zones.get(3);
        this.maxZ5 = zones.get(4);
    }

    public Integer getMaxZ1() {
        return maxZ1;
    }

    public void setMaxZ1(Integer maxZ1) {
        this.maxZ1 = maxZ1;
    }

    public Integer getMaxZ2() {
        return maxZ2;
    }

    public void setMaxZ2(Integer maxZ2) {
        this.maxZ2 = maxZ2;
    }

    public Integer getMaxZ3() {
        return maxZ3;
    }

    public void setMaxZ3(Integer maxZ3) {
        this.maxZ3 = maxZ3;
    }

    public Integer getMaxZ4() {
        return maxZ4;
    }

    public void setMaxZ4(Integer maxZ4) {
        this.maxZ4 = maxZ4;
    }

    public Integer getMaxZ5() {
        return maxZ5;
    }

    public void setMaxZ5(Integer maxZ5) {
        this.maxZ5 = maxZ5;
    }
}
