package data.requests;

import data.types.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateSalesReportRequest {

    private String salesForDate;

    public CreateSalesReportRequest() {

    }

    public CreateSalesReportRequest(Builder builder) {
        this.salesForDate = builder.salesForDate;

    }

    public String getSalesForDate() {
        return salesForDate;
    }

    public void setSalesForDate(String salesForDate) {
        this.salesForDate = salesForDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateSalesReportRequest that = (CreateSalesReportRequest) o;
        return getSalesForDate().equals(that.getSalesForDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSalesForDate());
    }

    @Override
    public String toString() {
        return "CreateSalesReportRequest{" +
                "salesForDate='" + salesForDate + '\'' +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String salesForDate;


        public Builder withSalesForDate(String withSalesForDate) {
            this.salesForDate = withSalesForDate;
            return this;
        }

        public CreateSalesReportRequest build() {
            return new CreateSalesReportRequest(this);
        }
    }
}
