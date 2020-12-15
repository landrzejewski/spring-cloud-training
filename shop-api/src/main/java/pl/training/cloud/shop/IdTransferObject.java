package pl.training.cloud.shop;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class IdTransferObject {

    @Min(1)
    @NotNull
    private Long id;

}
