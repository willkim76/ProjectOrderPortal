package data.responses;

import data.types.Order;
import data.types.models.OrderModel;

import java.util.List;

public class CreateSalesReportResponse {

    private List<OrderModel> dateSales;

    public CreateSalesReportResponse() {

    }

    private CreateSalesReportResponse(Builder builder) {
        this.dateSales = builder.sales;
    }

    public List<OrderModel> getDateSales() {
        return dateSales;
    }

    public void setDateSales(List<OrderModel> dateSales) {
        this.dateSales = dateSales;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<OrderModel> sales;

        public Builder withSales(List<OrderModel> withDateSales) {
            this.sales = withDateSales;
            return this;
        }

        public CreateSalesReportResponse build() {
            return new CreateSalesReportResponse(this);
        }
    }
}
