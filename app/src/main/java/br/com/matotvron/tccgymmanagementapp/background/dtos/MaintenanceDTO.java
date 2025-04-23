package br.com.matotvron.tccgymmanagementapp.background.dtos;

import br.com.matotvron.tccgymmanagementapp.background.models.Maintenance;

public class MaintenanceDTO {

    private String id;
    private String name;
    private String phone;
    private String document;

    public MaintenanceDTO(){

    }

    public MaintenanceDTO(Maintenance maintenance){
        this.id = maintenance.getId();
        this.name = maintenance.getName();
        this.phone = maintenance.getPhoneNumber();
        this.document = maintenance.getDocument();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
