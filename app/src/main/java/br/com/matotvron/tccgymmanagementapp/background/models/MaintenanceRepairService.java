package br.com.matotvron.tccgymmanagementapp.background.models;

import java.util.ArrayList;
import java.util.List;

import br.com.matotvron.tccgymmanagementapp.background.dtos.MaintenanceRequestDTO;

public class MaintenanceRepairService {

    private String id;
    private Maintenance maintenance;
    private MaintenanceRequestDTO maintenanceRequestDTO;
    private String description;
    private Double subTotal;
    private Double finalPrice;
    private List<Equipment> equipmentList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Maintenance getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(Maintenance maintenance) {
        this.maintenance = maintenance;
    }

    public MaintenanceRequestDTO getMaintenanceRequestDTO() {
        return maintenanceRequestDTO;
    }

    public void setMaintenanceRequestDTO(MaintenanceRequestDTO maintenanceRequestDTO) {
        this.maintenanceRequestDTO = maintenanceRequestDTO;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }
}
