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
//@NamedQuery(name = WeatherOnceOerDay)
@Table(name = "weatheronceperday", uniqueConstraints = @UniqueConstraint(columnNames = {
		"date", "city_id" }))
public class WeatherOncePerDay {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer WeatherOnceADayId;

	private Long date;
	private int tempMin;
	private int tempMax;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "city_id")
	@JsonBackReference
	private City city;

	public Integer getWeatherOnceADayId() {
		return WeatherOnceADayId;
	}

	public void setWeatherOnceADayId(Integer weatherOnceADayId) {
		WeatherOnceADayId = weatherOnceADayId;
	}

	public int getTempMin() {
		return tempMin;
	}

	public void setTempMin(int tempMin) {
		this.tempMin = tempMin;
	}

	public int getTempMax() {
		return tempMax;
	}

	public void setTempMax(int tempMax) {
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

	public WeatherOncePerDay() {
	}

	public WeatherOncePerDay(int tempMin, int tempMax, Long date) {
		super();

		this.tempMin = tempMin;
		this.tempMax = tempMax;
		this.date = date;
	}

}
