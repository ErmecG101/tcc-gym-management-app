package br.com.matotvron.tccgymmanagementapp.background.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.matotvron.tccgymmanagementapp.background.dtos.MaintenanceDTO;
import br.com.matotvron.tccgymmanagementapp.background.dtos.UserDTO;

public class MaintenanceRequest {

    private String id;
    private Long requestNumber;
    private String description;
    private String observation;
    private String createdAt;
    private Date updatedAt;
    private Date closedAt;
    private MaintenanceDTO maintenanceDTO;
    private UserDTO userDTO;

    private List<Equipment> equipments = new ArrayList<>();
    private List<MaintenanceDTO> maintenances = new ArrayList<>();
    private List<MaintenanceRepairService> services = new ArrayList<>();
    private List<String> conditions = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(Long requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    public MaintenanceDTO getMaintenanceDTO() {
        return maintenanceDTO;
    }

    public void setMaintenanceDTO(MaintenanceDTO maintenanceDTO) {
        this.maintenanceDTO = maintenanceDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public List<MaintenanceDTO> getMaintenances() {
        return maintenances;
    }

    public void setMaintenances(List<MaintenanceDTO> maintenances) {
        this.maintenances = maintenances;
    }

    public List<MaintenanceRepairService> getServices() {
        return services;
    }

    public void setServices(List<MaintenanceRepairService> services) {
        this.services = services;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }
}
