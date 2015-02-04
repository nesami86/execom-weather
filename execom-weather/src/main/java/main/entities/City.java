package main.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Cities")
public class City {

	@Id
	private Integer cityId;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "city")
	private List<Weather> weatherList = new ArrayList<Weather>();
	
	public List<Weather> getWeatherList() {
		return weatherList;
	}

	public City() {
	    
	}

	public City(Integer id, Integer cod) {
		super();
		this.cityId = id;
	}
	
	public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public void addWeather(Weather weather) {
        weather.setCity(this);
        weatherList.add(weather);
    }

	public Integer getId() {
		return cityId;
	}

	public void setId(Integer id) {
		this.cityId = id;
	}

	public String toString() {
		return "City code: " + cityId;
	}
}
