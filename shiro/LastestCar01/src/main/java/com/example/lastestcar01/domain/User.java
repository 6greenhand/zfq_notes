package com.example.lastestcar01.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
public class User implements Serializable {
    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String password;

    /**
     * 
     */
    private BigDecimal amount;

    /**
     * 
     */
    private String favoriteCars;

    /**
     * 
     */
    private String comparedCars;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     */
    public String getFavoriteCars() {
        return favoriteCars;
    }

    /**
     * 
     */
    public void setFavoriteCars(String favoriteCars) {
        this.favoriteCars = favoriteCars;
    }

    /**
     * 
     */
    public String getComparedCars() {
        return comparedCars;
    }

    /**
     * 
     */
    public void setComparedCars(String comparedCars) {
        this.comparedCars = comparedCars;
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
        User other = (User) that;
        return (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getFavoriteCars() == null ? other.getFavoriteCars() == null : this.getFavoriteCars().equals(other.getFavoriteCars()))
            && (this.getComparedCars() == null ? other.getComparedCars() == null : this.getComparedCars().equals(other.getComparedCars()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getFavoriteCars() == null) ? 0 : getFavoriteCars().hashCode());
        result = prime * result + ((getComparedCars() == null) ? 0 : getComparedCars().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", amount=").append(amount);
        sb.append(", favoriteCars=").append(favoriteCars);
        sb.append(", comparedCars=").append(comparedCars);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}