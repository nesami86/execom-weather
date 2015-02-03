package main.entities;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;



@Entity
@Table(name = "City")
public class City implements Serializable {

	@Id
	@Column(name = "CITY_ID", unique = true, nullable = false)
	private Integer id;
	
	
	@Column(name = "COD")
	private Integer cod; // weather code
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "city")
	private List<Weather> weatherList = new ArrayList<Weather>();

	
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

	public City() {
	}



	public City(Integer id, Integer cod) {
		super();
		this.id = id;

		this.cod = cod;
	}

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String toString() {
		return "City name : sifra vremenskih uslova" + cod + " Sifra grada "
				+ id + " Country ";
	}
}
