package main.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import main.entities.City;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

@Entity										
@Table(name = "WETHER" , uniqueConstraints = @UniqueConstraint(columnNames={"ID_REC_DATE","CITYWETHER_ID"}))
public class Weather {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "w_id", unique = true, nullable = false)
	private Integer w_id;
	
	
	@Column(name = "ID_REC_DATE")
	private Long date;
	
	@Column(name = "TEMPERATURE")
	private Double temperature; // temperatura  temp
	@Column(name = "HUMIDITY")
	private Double humidity;
	@Column(name = "PRESSURE")
	private Double pressure;
	@Column(name = "TEMP_MIN")
	private Double temp_min;
	@Column(name = "TEMP_MAX")
	private Double temp_max;
	//@ManyToOne
	@ManyToOne//moze se zvati isto 
	@JoinColumn(name="CITYWETHER_ID", referencedColumnName="CITY_ID", nullable=false)
	private City city;
	
	public Weather(){}
	
	public Weather(Long date, Double temperature, Double humidity,
			Double pressure, Double temp_min, Double temp_max) {
		super();
		this.date = date;
		this.temperature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		this.temp_min = temp_min;
		this.temp_max = temp_max;
	}
	/*
	public Wether(Integer date, Double temperature, Double humidity,
			Double pressure, Double temp_min, Double temp_max, City city) {
		super();
		this.date = date;
		this.temperature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		this.temp_min = temp_min;
		this.temp_max = temp_max;
		this.city = city;
	}
	*/
	
	
	
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
	public Double getTemp_min() {
		return temp_min;
	}
	public void setTemp_min(Double temp_min) {
		this.temp_min = temp_min;
	}
	public Double getTemp_max() {
		return temp_max;
	}
	public void setTemp_max(Double temp_max) {
		this.temp_max = temp_max;
	}

	
	
}
