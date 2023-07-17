package com.example.lastestcar01.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @TableName car
 */
@TableName(value ="car")
public class Car implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer carId;

    /**
     * 
     */
    private BigDecimal displacement;

    /**
     * 
     */
    private Date registrationDate;

    /**
     * 
     */
    private BigDecimal price;

    /**
     * 
     */
    private BigDecimal mileage;

    /**
     * 
     */
    private String clutchType;

    /**
     * 
     */
    private Date releaseDate;

    /**
     * 
     */
    private Integer brandId;

    /**
     * 
     */
    private Integer modelId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getCarId() {
        return carId;
    }

    /**
     * 
     */
    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    /**
     * 
     */
    public BigDecimal getDisplacement() {
        return displacement;
    }

    /**
     * 
     */
    public void setDisplacement(BigDecimal displacement) {
        this.displacement = displacement;
    }

    /**
     * 
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * 
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * 
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 
     */
    public BigDecimal getMileage() {
        return mileage;
    }

    /**
     * 
     */
    public void setMileage(BigDecimal mileage) {
        this.mileage = mileage;
    }

    /**
     * 
     */
    public String getClutchType() {
        return clutchType;
    }

    /**
     * 
     */
    public void setClutchType(String clutchType) {
        this.clutchType = clutchType;
    }

    /**
     * 
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * 
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * 
     */
    public Integer getBrandId() {
        return brandId;
    }

    /**
     * 
     */
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    /**
     * 
     */
    public Integer getModelId() {
        return modelId;
    }

    /**
     * 
     */
    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Car other = (Car) that;
        return (this.getCarId() == null ? other.getCarId() == null : this.getCarId().equals(other.getCarId()))
            && (this.getDisplacement() == null ? other.getDisplacement() == null : this.getDisplacement().equals(other.getDisplacement()))
            && (this.getRegistrationDate() == null ? other.getRegistrationDate() == null : this.getRegistrationDate().equals(other.getRegistrationDate()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getMileage() == null ? other.getMileage() == null : this.getMileage().equals(other.getMileage()))
            && (this.getClutchType() == null ? other.getClutchType() == null : this.getClutchType().equals(other.getClutchType()))
            && (this.getReleaseDate() == null ? other.getReleaseDate() == null : this.getReleaseDate().equals(other.getReleaseDate()))
            && (this.getBrandId() == null ? other.getBrandId() == null : this.getBrandId().equals(other.getBrandId()))
            && (this.getModelId() == null ? other.getModelId() == null : this.getModelId().equals(other.getModelId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCarId() == null) ? 0 : getCarId().hashCode());
        result = prime * result + ((getDisplacement() == null) ? 0 : getDisplacement().hashCode());
        result = prime * result + ((getRegistrationDate() == null) ? 0 : getRegistrationDate().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getMileage() == null) ? 0 : getMileage().hashCode());
        result = prime * result + ((getClutchType() == null) ? 0 : getClutchType().hashCode());
        result = prime * result + ((getReleaseDate() == null) ? 0 : getReleaseDate().hashCode());
        result = prime * result + ((getBrandId() == null) ? 0 : getBrandId().hashCode());
        result = prime * result + ((getModelId() == null) ? 0 : getModelId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", carId=").append(carId);
        sb.append(", displacement=").append(displacement);
        sb.append(", registrationDate=").append(registrationDate);
        sb.append(", price=").append(price);
        sb.append(", mileage=").append(mileage);
        sb.append(", clutchType=").append(clutchType);
        sb.append(", releaseDate=").append(releaseDate);
        sb.append(", brandId=").append(brandId);
        sb.append(", modelId=").append(modelId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}