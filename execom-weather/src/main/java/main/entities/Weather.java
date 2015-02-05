package main.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity                                        
@Table(name="weather", uniqueConstraints = @UniqueConstraint(columnNames={"date","city_id"}))
public class Weather {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer weatherId;
    
    private Long date;
    private Double temperature;
    private Double humidity;
    private Double pressure;
    private Double tempMin;
    private Double tempMax;
    
    @ManyToOne(fetch=FetchType.LAZY, cascade={ CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name="city_id")
    @JsonBackReference
    private City city;
    
    public Weather() {
        
    }
    
    public Weather(Long date, Double temperature, Double humidity, Double pressure, Double temp_min, Double temp_max) {
        super();
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.tempMin = temp_min;
        this.tempMax = temp_max;
    }

    public Integer getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(Integer weatherId) {
        this.weatherId = weatherId;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Weather [weatherId=" + weatherId + ", date=" + date + ", temperature=" + temperature + ", humidity=" + humidity
                + ", pressure=" + pressure + ", tempMin=" + tempMin + ", tempMax=" + tempMax + ", city=" + city + "]";
    }
}