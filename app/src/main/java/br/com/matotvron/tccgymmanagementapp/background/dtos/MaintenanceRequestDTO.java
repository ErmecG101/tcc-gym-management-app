package br.com.matotvron.tccgymmanagementapp.background.dtos;

import br.com.matotvron.tccgymmanagementapp.background.models.MaintenanceRequest;

public class MaintenanceRequestDTO {

    private String id;
    private Long requestNumber;
    private String createdAt;

    public MaintenanceRequestDTO() {

    }

    public MaintenanceRequestDTO(MaintenanceRequest maintenanceRequest) {
        this.id = maintenanceRequest.getId();
        this.requestNumber = maintenanceRequest.getRequestNumber();
        this.createdAt = maintenanceRequest.getCreatedAt();
    }

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
