package data.responses;

import data.requests.DeleteOrderRequest;
import exceptions.OrderDoesNotExistException;

import java.util.Objects;

public class DeleteOrderResponse {
    private boolean didItWork;
    private DeleteOrderResponse(Builder builder){
        this.didItWork = builder.didItWork;
    }

    public boolean isDidItWork() {
        return didItWork;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteOrderResponse that = (DeleteOrderResponse) o;
        return isDidItWork() == that.isDidItWork();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isDidItWork());
    }

    @Override
    public String toString() {
        return "DeleteOrderResponse{" +
                "didItWork=" + didItWork +
                '}';
    }



    public static final class Builder {
        private boolean didItWork;


        private Builder() {

        }

        public Builder willItWork(boolean didItWork) {
            this.didItWork = didItWork;
            return this;
        }
        public DeleteOrderResponse build() {
            return new DeleteOrderResponse(this);}
    }
}
