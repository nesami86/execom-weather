package main.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="cities")
public class City {

    @Id
    private Integer cityId;
    
    @OneToMany(mappedBy="city", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<Weather> weatherList = new ArrayList<Weather>();

    public City() {
        
    }

    public City(Integer id, Integer cod) {
        super();
        this.cityId = id;
    }
    
    public Integer getId() {
        return cityId;
    }

    public void setId(Integer id) {
        this.cityId = id;
    }
    
    public List<Weather> getWeatherList() {
        return weatherList;
    }
    
    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public void addWeather(Weather weather) {
        weather.setCity(this);
        weatherList.add(weather);
    }

    @Override
    public String toString() {
        return "City [cityId=" + cityId + "]";
    }
}
