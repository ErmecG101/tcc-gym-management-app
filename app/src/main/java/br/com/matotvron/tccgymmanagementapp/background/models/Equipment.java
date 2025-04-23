package br.com.matotvron.tccgymmanagementapp.background.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import br.com.matotvron.tccgymmanagementapp.background.dtos.GymDTO;


@Entity(tableName = "equipments")
public class Equipment implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "propertyNumber")
    private String propertyNumber;
    @ColumnInfo(name = "purchaseDate")

    private Date purchaseDate;
    @ColumnInfo(name = "originalValue")
    private Double originalValue;
    @ColumnInfo(name = "currentValue")
    private Double currentValue;
    @ColumnInfo(name = "depreciationPercentage")
    private Double depreciationPercentage;
    @ColumnInfo(name = "durability")
    private Double durability;
    @ColumnInfo(name = "equipmentType")
    private EquipmentType equipmentType;
    @ColumnInfo(name = "gymDTO")
    private GymDTO gymDTO;

    public Equipment() {
        id = "";
    }

    @Ignore
    public Equipment(@NonNull String id, String name, String description, String propertyNumber, Date purchaseDate, Double originalValue, Double currentValue, Double depreciationPercentage, Double durability, EquipmentType equipmentType, GymDTO gymDTO) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.propertyNumber = propertyNumber;
        this.purchaseDate = purchaseDate;
        this.originalValue = originalValue;
        this.currentValue = currentValue;
        this.depreciationPercentage = depreciationPercentage;
        this.durability = durability;
        this.equipmentType = equipmentType;
        this.gymDTO = gymDTO;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPropertyNumber() {
        return propertyNumber;
    }

    public void setPropertyNumber(String propertyNumber) {
        this.propertyNumber = propertyNumber;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Double getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(Double originalValue) {
        this.originalValue = originalValue;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public Double getDepreciationPercentage() {
        return depreciationPercentage;
    }

    public void setDepreciationPercentage(Double depreciationPercentage) {
        this.depreciationPercentage = depreciationPercentage;
    }

    public Double getDurability() {
        return durability;
    }

    public void setDurability(Double durability) {
        this.durability = durability;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public GymDTO getGymDTO() {
        return gymDTO;
    }

    public void setGymDTO(GymDTO gymDTO) {
        this.gymDTO = gymDTO;
    }
}
