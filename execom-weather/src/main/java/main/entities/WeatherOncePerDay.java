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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="weatheronceperday")
public class WeatherOncePerDay {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer WeatherOnceADayId;
	
	private Long date;
	private Double tempMin;
	private Double tempMax;
	

	@ManyToOne(fetch=FetchType.LAZY, cascade={ CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name="city_id")
    @JsonBackReference
    private City city;

	
	public Integer getWeatherOnceADayId() {
		return WeatherOnceADayId;
	}

	public void setWeatherOnceADayId(Integer weatherOnceADayId) {
		WeatherOnceADayId = weatherOnceADayId;
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
	
	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public WeatherOncePerDay(){}
	
	public WeatherOncePerDay( Double tempMin,
			Double tempMax, Long date) {
		super();
		this.tempMin = tempMin;
		this.tempMax = tempMax;
		this.date = date;
	}
	
	
	

}
