package br.com.matotvron.tccgymmanagementapp.background.models.converters;

import androidx.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import br.com.matotvron.tccgymmanagementapp.background.dtos.GymDTO;
import br.com.matotvron.tccgymmanagementapp.background.models.EquipmentType;

public class DateTypeConverter {
    @TypeConverter
    public Long fromTimestamp(Date date) {
        return date.getTime();
    }
    @TypeConverter
    public Date toTimestamp(Long date) {
        Date newDate = new Date();
        newDate.setTime(date);
        return newDate;
    }

    @TypeConverter
    public String getIdentifier(EquipmentType equipmentType){
        return equipmentType.getId();
    }

    @TypeConverter
    public EquipmentType fromIdentifier(String id){
        EquipmentType type = new EquipmentType();
        type.setId(id);
        return type;
    }

    @TypeConverter
    public String getIdentifierGymDTO(GymDTO gymDTO){
        return gymDTO.getId();
    }

    @TypeConverter
    public GymDTO getIdentifierGymDTO(String id){
        GymDTO gymDTO = new GymDTO();
        gymDTO.setId(id);
        return gymDTO;
    }
}
